package uk.ac.york.cs.es.smlg;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GSMUtil {

	private static final String SMLG_ID = "id";
	private static final String SMLG_PARENT = "parent";
	private static final String SMLG_URI = "uri";
	private static final String SMLG_PREFIX = "prefix";

	private static void transformToTargetXML(Node sourceParentNode, Document targetDoc)
			throws ParserConfigurationException, TransformerException {

		if (!sourceParentNode.hasChildNodes()) {
			return;
		}

		NodeList sourceItemNodes = sourceParentNode.getChildNodes();

		ArrayList<Node> edgeNodes = new ArrayList<>();
		String uriName = null;
		String prefixName = null;
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

				Element targetNode = targetDoc.createElement(prefixName + ":" + diagramName);
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xmi", "http://www.omg.org/XMI");
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi",
						"http://www.w3.org/2001/XMLSchema-instance");
				targetNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:myPrefix", uriName);
				targetNode.setAttribute("xmi:version", "2.0");
				targetNode.setAttribute("gsmId", id);
				targetNode.setAttribute("gsmNumber", "-1");

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
						targetChildNode.setAttribute("parentId", id);
						targetChildNode.setAttribute("gsmNumber", "");

						targetNode.appendChild(targetChildNode);
					}
				}

				targetDoc.appendChild(targetNode);
			}
			// create other elements
			else {

				// get parent id, cell type (edge or vertex), source, and target
				String parentId = null;
				String isEdge = "false";
				String source = null;
				String target = null;
				for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
					Node node = sourceItemNode.getChildNodes().item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE
							|| node.getAttributes().getNamedItem(SMLG_PARENT) == null) {
						continue;
					}
					parentId = node.getAttributes().getNamedItem(SMLG_PARENT).getNodeValue();
					if (node.getAttributes().getNamedItem("edge") != null
							&& node.getAttributes().getNamedItem("edge").getNodeValue().equals("1")) {
						isEdge = "true";
						source = node.getAttributes().getNamedItem("source").getNodeValue();
						target = node.getAttributes().getNamedItem("target").getNodeValue();
					} else {
						isEdge = "false";
					}
				}

				// get parent node
				Node targetParentNode = GSMUtil.findNode(targetDoc, parentId);
				if (targetParentNode == null) {
					continue;
				}

				String targetParentId = targetParentNode.getAttributes().getNamedItem("gsmId").getNodeValue();
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
							for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
								targetChildElement.setAttribute(sourceItemNode.getAttributes().item(k).getNodeName(),
										sourceItemNode.getAttributes().item(k).getNodeValue());
							}
							targetChildElement.setAttribute("xsi:type", "");
							targetChildElement.setAttribute("gsmId", id);
							targetChildElement.setAttribute("gsmParentId", parentId);
							targetChildElement.setAttribute("gsmIsEdge", isEdge);
							targetChildElement.setAttribute("gsmSource", source);
							targetChildElement.setAttribute("gsmTarget", target);
							targetChildElement.setAttribute("gsmNumber", "");
							targetParentChildNode.appendChild(targetChildElement);
							continue;
						}
					}
					continue;
				}
				Element targetChildElement = targetDoc.createElement(sourceItemNode.getNodeName());
				for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
					targetChildElement.setAttribute(sourceItemNode.getAttributes().item(k).getNodeName(),
							sourceItemNode.getAttributes().item(k).getNodeValue());
				}
				targetChildElement.setAttribute("xsi:type", "");
				targetChildElement.setAttribute("gsmId", id);
				targetChildElement.setAttribute("gsmParentId", parentId);
				targetChildElement.setAttribute("gsmIsEdge", isEdge);
				targetChildElement.setAttribute("gsmSource", source);
				targetChildElement.setAttribute("gsmTarget", target);
				targetChildElement.setAttribute("gsmNumber", "");
				targetParentNode.appendChild(targetChildElement);
			}
			// ---------------------------------------

		}

		GSMUtil.removeGSMNode(prefixName, targetDoc, targetDoc, edgeNodes);
		GSMUtil.handleEdges(edgeNodes, targetDoc);
		GSMUtil.cleanDocumentFromTemporaryTags(targetDoc);
	}

	private static void cleanDocumentFromTemporaryTags(Node sourceNode) {
		for (int j = sourceNode.getChildNodes().getLength() - 1; j >= 0; j--) {
			Node iNode = sourceNode.getChildNodes().item(j);
			if (iNode.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = iNode.getAttributes();
				if (attributes.getNamedItem("id") != null) {
					attributes.removeNamedItem("id");
				}
				if (attributes.getNamedItem("label") != null) {
					attributes.removeNamedItem("label");
				}
				for (int k = attributes.getLength() - 1; k >= 0; k--) {
					Node attribute = attributes.item(k);
					if (attribute.getNodeName().length() >= 3
							&& attribute.getNodeName().substring(0, 3).toLowerCase().equals("gsm")) {
						String name = attribute.getNodeName().toString();
						attributes.removeNamedItem(name);
					}
				}
			}
		}
		for (int j = sourceNode.getChildNodes().getLength() - 1; j >= 0; j--) {
			Node iNode = sourceNode.getChildNodes().item(j);
			if (iNode.getNodeType() == Node.ELEMENT_NODE) {
				cleanDocumentFromTemporaryTags(iNode);
			}
		}
	}

	private static void removeGSMNode(String prefixName, Document targetDoc, Node sourceNode,
			ArrayList<Node> edgeNodes) {
		for (int j = sourceNode.getChildNodes().getLength() - 1; j >= 0; j--) {
			Node iNode = sourceNode.getChildNodes().item(j);
			if (iNode.getNodeType() == Node.ELEMENT_NODE) {
				if (iNode.getNodeName().equals("GSMRootContainer") || iNode.getNodeName().equals("GSMContainer")) {
					Node parentNode = iNode.getParentNode();
					parentNode.removeChild(iNode);
					String newName = iNode.getAttributes().getNamedItem("name").getNodeValue();

					Integer number = 0;
					for (int k = iNode.getChildNodes().getLength() - 1; k >= 0; k--) {
						Node childNode = iNode.getChildNodes().item(k);

						if (childNode.getAttributes().getNamedItem("gsmIsEdge").getNodeValue().equals("true")) {
							edgeNodes.add(childNode);
							if (childNode.getAttributes().getNamedItem("gsmReference") != null) {
								continue;
							}
						}

						String oldName = childNode.getNodeName();
						childNode.getAttributes().getNamedItem("xsi:type").setNodeValue(prefixName + ":" + oldName);
						childNode.getAttributes().getNamedItem("gsmNumber").setNodeValue(number.toString());
						parentNode.appendChild(childNode);
						targetDoc.renameNode(childNode, null, newName);

						number += 1;
					}
				}
			}
		}
		for (int j = sourceNode.getChildNodes().getLength() - 1; j >= 0; j--) {
			Node iNode = sourceNode.getChildNodes().item(j);
			if (iNode.getNodeType() == Node.ELEMENT_NODE) {
				removeGSMNode(prefixName, targetDoc, iNode, edgeNodes);
			}
		}
	}

	private static String getNodePath(Node iNode) {
		Node parentNode = iNode;
		String path = "";
		while (parentNode != null && parentNode.getAttributes() != null
				&& !parentNode.getAttributes().getNamedItem("gsmNumber").getNodeValue().equals("-1")) {
			String gsmNumber = parentNode.getAttributes().getNamedItem("gsmNumber").getNodeValue();
			String nodeName = parentNode.getNodeName();
			path = "/@" + nodeName + "." + gsmNumber + path;
			parentNode = parentNode.getParentNode();
		}
		path = "/" + path;
		if (path.equals("/"))
			path = "";
		return path;
	}

	private static void handleEdges(ArrayList<Node> edgeNodes, Node docNode) {

		for (Node edgeNode : edgeNodes) {
			String source = edgeNode.getAttributes().getNamedItem("gsmSource").getNodeValue();
			String target = edgeNode.getAttributes().getNamedItem("gsmTarget").getNodeValue();
			String gsmReference = null;
			if (edgeNode.getAttributes().getNamedItem("gsmReference") != null) {
				gsmReference = edgeNode.getAttributes().getNamedItem("gsmReference").getNodeValue();
			}
			Node sourceNode = findNode(docNode, source);
			Node targetNode = findNode(docNode, target);

			String sourcePath = "";
			if (sourceNode != null) {
				sourcePath = getNodePath(sourceNode);
				edgeNode.getAttributes().getNamedItem("gsmSource").setNodeValue(sourcePath);
			}
			String targetPath = "";
			if (targetNode != null) {
				targetPath = getNodePath(targetNode);
				edgeNode.getAttributes().getNamedItem("gsmTarget").setNodeValue(targetPath);
			}
			if (gsmReference != null) {
				for (int k = 0; k < sourceNode.getAttributes().getLength(); k++) {
					sourceNode.getAttributes().getNamedItem(gsmReference).setNodeValue(targetPath);
				}
			}
		}
	}

	private static Node findNode(Node node, String searchedId) {
		Node target = null;
		if (node.getNodeType() == Node.ELEMENT_NODE && node.getAttributes().getNamedItem("gsmId") != null) {
			String targetParentId = node.getAttributes().getNamedItem("gsmId").getNodeValue();
			if (targetParentId.equals(searchedId)) {
				return node;
			}
		}
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node childNode = node.getChildNodes().item(j);
			target = GSMUtil.findNode(childNode, searchedId);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

	public static String convertToXMIfromXmXMLFile(String path) throws Exception {
		String xmlString = new String(Files.readAllBytes(Paths.get(path)));
		String output = transformMxXMLtoXMI(xmlString);
		return output;
	}

	public static String transformMxXMLtoXMI(String xml) throws Exception {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

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
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);
		transformer.transform(new DOMSource(targetDoc), streamResult);
		String output = writer.getBuffer().toString();
		System.out.println(output);

		return output;

	}

}
