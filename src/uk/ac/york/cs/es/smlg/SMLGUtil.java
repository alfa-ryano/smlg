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

	private static final String SMLG_ID = "id";
	private static final String SMLG_PARENT = "parent";
	private static final String SMLG_CELL = "SMLGCell";
	private static final String SMLG_PROPERTIES = "properties";
	private static final String SMLG_URI = "uri";
	private static final String SMLG_DIAGRAM = "diagram";
	private static final String SMLG_PREFIX = "prefix";
	private static final String SMLG_PACKAGE = "package";
	private static final String SMLG_TYPE = "type";
	private static final String SMLG_VALUE = "value";
	private static final String SMLG_NAME = "name";
	private static final String SMLG_CLASS = "class";
	private static final String SMLG_LABEL = "label";
	private static final String SMLG_COMPARTMENT = "compartment";

	Gson gson = new Gson();

	private static void transformToTargetXML(Node sourceNode, Document targetDoc) throws ParserConfigurationException, TransformerException {

		if (sourceNode.hasChildNodes()) {

			NodeList nodes = sourceNode.getChildNodes();

			String name = null;
			String className = null;
			String uriName = null;
			String prefixName = null;
			String packageName = null;
			String diagramName = null;
			String typeName = null;
			String parentId = null;

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == SMLG_CELL) {

					Node smlgCell = node;
					String id = smlgCell.getAttributes().getNamedItem(SMLG_ID).getNodeValue();
					String jsonAttributes = smlgCell.getAttributes().getNamedItem(SMLG_PROPERTIES).getNodeValue();
					JsonArray jsonArray = new JsonParser().parse(jsonAttributes).getAsJsonArray();

					if (id.equals("1")) {

						for (int j = 0; j < jsonArray.size(); j++) {
							name = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
							if (name.equals(SMLG_CLASS)) {
								className = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_PACKAGE)) {
								packageName = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_PREFIX)) {
								prefixName = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_URI)) {
								uriName = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_DIAGRAM)) {
								diagramName = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else {
								break;
							}
						}

						Element targetNode = targetDoc.createElement(prefixName + ":" + className);
						targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xmi",
								"http://www.omg.org/XMI");
						targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi",
								"http://www.w3.org/2001/XMLSchema-instance");
						targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:myPrefix", uriName);
						targetNode.setAttribute("xmi:version", "2.0");
						targetNode.setAttribute("smlgId", id);

						// add diagram's/root's properties
						for (int j = 0; j < jsonArray.size(); j++) {
							name = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
							if (!name.equals(SMLG_CLASS) && !name.equals(SMLG_PACKAGE) && !name.equals(SMLG_URI)
									&& !name.equals(SMLG_DIAGRAM) && !name.equals(SMLG_PREFIX)) {

								typeName = jsonArray.get(j).getAsJsonObject().get(SMLG_TYPE).getAsString();
								Element element = targetDoc.createElement(name);
								element.setAttribute("xsi:type", prefixName + ":" + typeName);
								element.setAttribute("smlgId", "-1");
								targetNode.appendChild(element);
								continue;
							}
						}

						targetDoc.appendChild(targetNode);
					} else {

						
						
						// Get Type
						for (int j = 0; j < jsonArray.size(); j++) {
							name = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
							if (name.equals(SMLG_CLASS)) {
								className = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_TYPE)) {
								typeName = jsonArray.get(j).getAsJsonObject().get(SMLG_TYPE).getAsString();
								continue;
							}
						}

						// Get parent Id
						for (int j = 0; j < smlgCell.getChildNodes().getLength(); j++) {
							Node mxCell = smlgCell.getChildNodes().item(j);
							if (mxCell.getNodeType() == Node.ELEMENT_NODE
									&& mxCell.getAttributes().getNamedItem(SMLG_PARENT) != null) {
								parentId = mxCell.getAttributes().getNamedItem(SMLG_PARENT).getNodeValue();
							}
						}

						if (id.equals("3")){
							System.out.println();
							TransformerFactory tf = TransformerFactory.newInstance();
							Transformer transformer = tf.newTransformer();
							transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
							// transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
							// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
							StringWriter writer = new StringWriter();
							transformer.transform(new DOMSource(targetDoc), new StreamResult(writer));
							String output = writer.getBuffer().toString();// .replaceAll("\n|\r",
																			// "");
							System.out.println(output);
						}
						
						// get parent node
						Node parentNode = SMLGUtil.findTargetParentNode(targetDoc, parentId);

						if (id.equals("3")){
							System.out.println();
						}
						// parent's child with the same type
						if (parentNode != null) {
//							for (int j = 0; j < parentNode.getChildNodes().getLength(); j++) {
//								Node childNode = parentNode.getChildNodes().item(j);
//								if (node.getNodeType() == Node.ELEMENT_NODE
//										&& childNode.getAttributes().getNamedItem("xsi:type") != null) {
//									String childNodeName = childNode.getNodeName();
//									String childNodeType = childNode.getAttributes().getNamedItem("xsi:type")
//											.getNodeValue();
//									if ((prefixName + ":" + className).equals(childNodeType)) {
//										Element element = targetDoc.createElement(childNodeName);
//										for (int k = 0; k < jsonArray.size(); k++) {
//											name = jsonArray.get(k).getAsJsonObject().get(SMLG_NAME).getAsString();
//											String value = jsonArray.get(k).getAsJsonObject().get(SMLG_VALUE).getAsString();
//											String type = jsonArray.get(k).getAsJsonObject().get(SMLG_TYPE).getAsString();
//											boolean compartment = jsonArray.get(k).getAsJsonObject().get(SMLG_COMPARTMENT).getAsBoolean();
//											
//											if (!name.equals(SMLG_CLASS) && !name.equals(SMLG_PACKAGE)
//												&& !name.equals(SMLG_URI) && !name.equals(SMLG_DIAGRAM)
//												&& !name.equals(SMLG_PREFIX) && !name.equals(SMLG_LABEL)
//												&& compartment == false) {
//												element.setAttribute(name, value);
//												element.setAttribute("smlgId", id);
//											}
//										}
//										parentNode.appendChild(element);
//									}
//								}
//							}
							
							
						}
						
						
					}
				}
			}
		}

	}

//	if (node.getNodeType() == Node.ELEMENT_NODE && node.getAttributes().getNamedItem("smlgId") != null) {
//		String targetParentId = node.getAttributes().getNamedItem("smlgId").getNodeValue();
//		if (targetParentId.equals(searchedParentId)) {
//			return node;
//		}
//	}
	private static Node findTargetParentNode(Node node, String searchedParentId) {
		Node target = null;
		if (node.getNodeType() == Node.ELEMENT_NODE && node.getAttributes().getNamedItem("smlgId") != null) {
			String targetParentId = node.getAttributes().getNamedItem("smlgId").getNodeValue();
			System.out.println(node.getNodeName() + ": " + targetParentId);
			if (targetParentId.equals(searchedParentId)) {
				return node;
			}
		}
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node childNode = node.getChildNodes().item(j);
			target = SMLGUtil.findTargetParentNode(childNode, searchedParentId);
			if (target!= null){
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
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		// transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(targetDoc), new StreamResult(writer));
		String output = writer.getBuffer().toString();// .replaceAll("\n|\r",
														// "");
		System.out.println(output);

		return "";

	}

}
