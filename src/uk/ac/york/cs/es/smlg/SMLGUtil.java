package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SMLGUtil {

	private static final String SMLG_MXREFERENCE = "reference";
	private static final String SMLG_ID = "id";
	private static final String SMLG_PARENT = "parent";
	private static final String SMLG_CELL = "SMLGCell";
	private static final String SMLG_PROPERTIES = "properties";
	private static final String SMLG_URI = "uri";
	private static final String SMLG_DIAGRAM = "diagram";
	private static final String SMLG_PREFIX = "prefix";
	private static final String SMLG_PACKAGE = "package";
	private static final String SMLG_TYPE = "type";
	private static final String SMLG_ETYPE = "eType";
	private static final String SMLG_VALUE = "value";
	private static final String SMLG_NAME = "name";
	private static final String SMLG_CLASS = "class";
	private static final String SMLG_LABEL = "label";
	private static final String SMLG_COMPARTMENT = "compartment";
	private static final String SMLG_MXCOMPARTMENT = "mxCompartment";

	Gson gson = new Gson();

	private static void transformToTargetXML(Node sourceParentNode, Document targetDoc)
			throws ParserConfigurationException, TransformerException {

		if (!sourceParentNode.hasChildNodes()) {
			return;
		}

		NodeList sourceItemNodes = sourceParentNode.getChildNodes();

		String uriName = null;
		String prefixName = null;
		String packageName = null;
		String diagramName = null;

		for (int i = 0; i < sourceItemNodes.getLength(); i++) {
			Node sourceItemNode = sourceItemNodes.item(i);
			if (sourceItemNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			String id = sourceItemNode.getAttributes().getNamedItem(SMLG_ID).getNodeValue();
			if (id.equals("0")) {
				continue;
			}
			// create the root first
			if (id.equals("1")) {

				diagramName = sourceItemNode.getNodeName();
				prefixName = sourceItemNode.getAttributes().getNamedItem(SMLG_PREFIX).getNodeValue();
				uriName = sourceItemNode.getAttributes().getNamedItem(SMLG_URI).getNodeValue();
				packageName = sourceItemNode.getAttributes().getNamedItem(SMLG_PACKAGE).getNodeValue();

				Element targetNode = targetDoc.createElement(prefixName + ":" + diagramName);
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xmi", "http://www.omg.org/XMI");
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi",
						"http://www.w3.org/2001/XMLSchema-instance");
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:myPrefix", uriName);
				targetNode.setAttribute("xmi:version", "2.0");
				targetNode.setAttribute("smlgId", id);

				NodeList nodes = sourceItemNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					if (!node.getNodeName().equals("mxCell")) {
						String name = node.getAttributes().getNamedItem("name").getNodeValue();
						String type = node.getAttributes().getNamedItem("type").getNodeValue();
						Element targetChildNode = targetDoc.createElement(node.getNodeName());
						targetChildNode.setAttribute("name", name);
						targetChildNode.setAttribute("xsi:type", type);
						targetNode.appendChild(targetChildNode);
					}
				}

				targetDoc.appendChild(targetNode);
			}
			// create other elements
			else {

				// get parent id
				String parentId = null;
				for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
					Node node = sourceItemNode.getChildNodes().item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE
							|| node.getAttributes().getNamedItem(SMLG_PARENT) == null) {
						continue;
					}
					parentId = node.getAttributes().getNamedItem(SMLG_PARENT).getNodeValue();
				}

				// get parent node
				Node targetParentNode = SMLGUtil.findTargetParentNode(targetDoc, parentId);
				if (targetParentNode == null) {
					continue;
				}

				String targetParentId = targetParentNode.getAttributes().getNamedItem("smlgId").getNodeValue();
				if (targetParentId.equals("1")) {

					for (int j = 0; j < targetParentNode.getChildNodes().getLength(); j++) {
						Node targetParentChildNode = targetParentNode.getChildNodes().item(j);
						if (targetParentChildNode.getNodeType() != Node.ELEMENT_NODE
								|| targetParentChildNode.getNodeName().equals("mxCell")) {
							continue;
						}
						String targetType = targetParentChildNode.getAttributes().getNamedItem("xsi:type")
								.getNodeValue();
						String sourceType = sourceItemNode.getNodeName();
						if (sourceType.equals(targetType)) {
							Element targetChildElement = targetDoc.createElement(sourceItemNode.getNodeName());
							for (int k = 0; k < sourceItemNode.getAttributes().getLength();k++){
								targetChildElement.setAttribute(sourceItemNode.getAttributes().item(k).getNodeName(), sourceItemNode.getAttributes().item(k).getNodeValue());
							}
							targetChildElement.setAttribute("xsi:type", "");
							targetChildElement.setAttribute("smlgId", id);
							targetParentChildNode.appendChild(targetChildElement);
							continue;
						}
					}
					continue;
				}
				Element targetChildElement = targetDoc.createElement(sourceItemNode.getNodeName());
				for (int k = 0; k < sourceItemNode.getAttributes().getLength();k++){
					targetChildElement.setAttribute(sourceItemNode.getAttributes().item(k).getNodeName(), sourceItemNode.getAttributes().item(k).getNodeValue());
				}
				targetChildElement.setAttribute("xsi:type", "");
				targetChildElement.setAttribute("smlgId", id);
				targetParentNode.appendChild(targetChildElement);
			}
			// ---------------------------------------

		}

		SMLGUtil.removeGSMNode(prefixName, targetDoc, targetDoc);
	}

	private static void removeGSMNode(String prefixName, Document targetDoc, Node sourceNode) {	
			//for (int j = 0; j < sourceNode.getChildNodes().getLength(); j++) {
			for (int j = sourceNode.getChildNodes().getLength() -1; j >= 0; j--){
				Node iNode = sourceNode.getChildNodes().item(j);
				if (iNode.getNodeType() == Node.ELEMENT_NODE) {
					String id = "";
//					if (iNode.getAttributes().getNamedItem("smlgId") != null) {
//						id = iNode.getAttributes().getNamedItem("smlgId").getNodeValue();
//						System.out.println(id);					
//					}else{
//						System.out.println("");
//					}
					if (iNode.getNodeName().equals("GSMRootContainer") || iNode.getNodeName().equals("GSMContainer")) {
						//System.out.println("(" + iNode.getNodeName() + ": " + id + ") This is a Container");
						
						Node parentNode = iNode.getParentNode();
						parentNode.removeChild(iNode);
						String newName = iNode.getAttributes().getNamedItem("name").getNodeValue();
						
						for (int k = iNode.getChildNodes().getLength() - 1; k >= 0; k--){
							Node childNode = iNode.getChildNodes().item(k);
							String oldName = childNode.getNodeName();
							childNode.getAttributes().getNamedItem("xsi:type").setNodeValue(prefixName+ ":" + oldName);
							childNode.getAttributes().removeNamedItem("smlgId");
							childNode.getAttributes().removeNamedItem("id");
							parentNode.appendChild(childNode);
							targetDoc.renameNode(childNode, null, newName);
						}
					}
				}
			}
			for (int j = sourceNode.getChildNodes().getLength() -1; j >= 0; j--){
				Node iNode = sourceNode.getChildNodes().item(j);
				if (iNode.getNodeType() == Node.ELEMENT_NODE) {
					removeGSMNode(prefixName, targetDoc, iNode);
				}
				
			}
	}

	private static Node findTargetParentNode(Node node, String searchedParentId) {
		Node target = null;
		if (node.getNodeType() == Node.ELEMENT_NODE && node.getAttributes().getNamedItem("smlgId") != null) {
			String targetParentId = node.getAttributes().getNamedItem("smlgId").getNodeValue();
			// System.out.println(node.getNodeName() + ": " + targetParentId);
			if (targetParentId.equals(searchedParentId)) {
				return node;
			}
		}
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node childNode = node.getChildNodes().item(j);
			target = SMLGUtil.findTargetParentNode(childNode, searchedParentId);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

	public static String loadXML(String path) throws Exception {

		// initialise source document
		File fXmlFile = new File(path);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();

		if (doc.getElementsByTagName("mxGraphModel").getLength() == 0) {
			throw new Exception("The XML document does not have mxGraphModel element");
		}
		if (doc.getElementsByTagName("root").getLength() == 0) {
			throw new Exception("The XML document does not have root element");
		}

		Node sourceNode = doc.getDocumentElement().getElementsByTagName("root").item(0);

		// initialise target document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document targetDoc = docBuilder.newDocument();
		targetDoc.setXmlVersion("1.1");
		targetDoc.setXmlStandalone(true);

		// run the xml nodes exploration
		transformToTargetXML(sourceNode, targetDoc);

		System.out.println();

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		// transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);
		transformer.transform(new DOMSource(targetDoc), streamResult);
		String output = writer.getBuffer().toString();// .replaceAll("\n|\r",
														// "");
		System.out.println(output);

		return "";

	}

}
