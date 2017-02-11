package uk.ac.york.cs.es.smlg.util;

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

public class SMLGUtil2 {

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

	private static void transformToTargetXML(Node sourceNode, Document targetDoc)
			throws ParserConfigurationException, TransformerException {

		if (sourceNode.hasChildNodes()) {

			NodeList nodes = sourceNode.getChildNodes();

			String uriName = null;
			String prefixName = null;
			String packageName = null;
			String diagramName = null;

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == SMLG_CELL) {

					Node smlgCell = node;
					String id = smlgCell.getAttributes().getNamedItem(SMLG_ID).getNodeValue();
					String jsonAttributes = smlgCell.getAttributes().getNamedItem(SMLG_PROPERTIES).getNodeValue();
					JsonArray jsonArray = new JsonParser().parse(jsonAttributes).getAsJsonArray();

					if (id.equals("1")) {

						String name = null;
						String nameName = null;
						String className = null;
						String typeName = null;

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

								typeName = jsonArray.get(j).getAsJsonObject().get(SMLG_ETYPE).getAsString();
								Element element = targetDoc.createElement(name);
								element.setAttribute("xsi:type", prefixName + ":" + typeName);
								element.setAttribute("smlgTemporary", "true");
								targetNode.appendChild(element);
								continue;
							}
						}

						targetDoc.appendChild(targetNode);
					} else {

						String name = null;
						String nameName = null;
						String className = null;
						String parentId = null;
						String typeName = null;

						// Get Type
						for (int j = 0; j < jsonArray.size(); j++) {
							name = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
							if (name.equals(SMLG_CLASS)) {
								className = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
								continue;
							} else if (name.equals(SMLG_ETYPE)) {
								typeName = jsonArray.get(j).getAsJsonObject().get(SMLG_ETYPE).getAsString();
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

						// get parent node
						Node parentNode = SMLGUtil2.findTargetParentNode(targetDoc, parentId);

						if (id.equals("6")) {
							System.out.println();
						}
						// parent's child with the same type
						if (parentNode != null) {
							String targetParentId = parentNode.getAttributes().getNamedItem("smlgId").getNodeValue();
							if (!targetParentId.equals("1")) {
								boolean mxCompartment1 = false;
								String refName1 = null;
								String nameName1 = null;
								String className1 = null;
								for (int j = 0; j < jsonArray.size(); j++) {
									String keyName1 = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
									if (keyName1.equals(SMLG_MXREFERENCE)) {
										refName1 = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
									} else if (keyName1.equals(SMLG_MXCOMPARTMENT)) {
										mxCompartment1 = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE)
												.getAsBoolean();
									} else if (keyName1.equals(SMLG_NAME)) {
										nameName1 = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
									} else if (keyName1.equals(SMLG_CLASS)) {
										className1 = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
									}
								}
								if (mxCompartment1 == true) {
									Element element = targetDoc.createElement(refName1);
									element.setAttribute("smlgTemporary", "true");
									element.setAttribute("smlgId", id);
									parentNode.appendChild(element);
								} else {
									Element element = targetDoc.createElement(parentNode.getNodeName());

									for (int j = 0; j < jsonArray.size(); j++) {
										String keyName2 = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME)
												.getAsString();
										boolean compartment = jsonArray.get(j).getAsJsonObject().get(SMLG_COMPARTMENT)
												.getAsBoolean();
										if (!keyName2.equals(SMLG_CLASS) && !keyName2.equals(SMLG_PACKAGE)
												&& !keyName2.equals(SMLG_URI) && !keyName2.equals(SMLG_DIAGRAM)
												&& !keyName2.equals(SMLG_PREFIX) && !keyName2.equals(SMLG_LABEL)
												&& !keyName2.equals(SMLG_MXCOMPARTMENT) && compartment == false) {

											String value = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE)
													.getAsString();
											element.setAttribute(keyName2, value);
										}
									}
									element.setAttribute("xsi:type", prefixName + ":" + className1);
									element.setAttribute("smlgId", id);
									parentNode.appendChild(element);
								}

							} else {
								for (int j = 0; j < parentNode.getChildNodes().getLength(); j++) {
									Node childNode = parentNode.getChildNodes().item(j);
									if (node.getNodeType() == Node.ELEMENT_NODE) {
										if (childNode.getAttributes().getNamedItem("xsi:type") != null) {
											String childNodeName = childNode.getNodeName();
											String childNodeType = childNode.getAttributes().getNamedItem("xsi:type")
													.getNodeValue();
											if ((prefixName + ":" + className).equals(childNodeType)) {
												Element element = targetDoc.createElement(childNodeName);
												for (int k = 0; k < jsonArray.size(); k++) {
													String tempName = jsonArray.get(k).getAsJsonObject().get(SMLG_NAME)
															.getAsString();
													String value = jsonArray.get(k).getAsJsonObject().get(SMLG_VALUE)
															.getAsString();

													boolean compartment = jsonArray.get(k).getAsJsonObject()
															.get(SMLG_COMPARTMENT).getAsBoolean();

													if (!tempName.equals(SMLG_CLASS) && !tempName.equals(SMLG_PACKAGE)
															&& !tempName.equals(SMLG_URI)
															&& !tempName.equals(SMLG_DIAGRAM)
															&& !tempName.equals(SMLG_PREFIX)
															&& !tempName.equals(SMLG_LABEL) && compartment == false) {
														element.setAttribute(tempName, value);
														element.setAttribute("xsi:type", className);
														element.setAttribute("smlgId", id);
													}
												}
												parentNode.appendChild(element);
											}
										}
									}
								}
							}

						}

					}
				}
			}
		}

	}

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
			target = SMLGUtil2.findTargetParentNode(childNode, searchedParentId);
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
