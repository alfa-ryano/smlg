package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SMLGUtil {

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

	Gson gson = new Gson();

	private static void transformToTargetXML(Node sourceNode, Document targetDoc, Node targetNode)
			throws ParserConfigurationException {
		if (sourceNode.hasChildNodes()) {
			NodeList nodes = sourceNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == SMLG_CELL) {
					Node smlgCell = node;
					String jsonAttributes = smlgCell.getAttributes().getNamedItem(SMLG_PROPERTIES).getNodeValue();
					JsonArray jsonArray = new JsonParser().parse(jsonAttributes).getAsJsonArray();

					String name = null;
					String classValue = null;
					String uriValue = null;
					String prefixValue = null;
					String packageValue = null;
					for (int j = 0; j < jsonArray.size(); j++) {

						name = jsonArray.get(j).getAsJsonObject().get(SMLG_NAME).getAsString();
						if (name.equals(SMLG_CLASS)) {
							classValue = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
						} else if (name.equals(SMLG_PACKAGE)) {
							packageValue = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
						} else if (name.equals(SMLG_PREFIX)) {
							prefixValue = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
						}else if (name.equals(SMLG_URI)) {
							uriValue = jsonArray.get(j).getAsJsonObject().get(SMLG_VALUE).getAsString();
						}

					}

					for (int k = 0; k < smlgCell.getChildNodes().getLength(); k++) {
						Node mxCell = smlgCell.getChildNodes().item(k);
						if (mxCell.getNodeType() == Node.ELEMENT_NODE
								&& mxCell.getAttributes().getNamedItem(SMLG_PARENT) != null) {
							String parentId = mxCell.getAttributes().getNamedItem(SMLG_PARENT).getNodeValue();
							if (parentId.trim().equals("1")) {
								Element element = targetDoc.createElement(classValue);
								targetNode.appendChild(element);
							}
						}
					}

				}
			}
		}

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
		Element targetNode = targetDoc.createElement("Filesystem");
		targetDoc.appendChild(targetNode);

		// run the xml nodes exploration
		transformToTargetXML(sourceNode, targetDoc, targetNode);

		System.out.println();

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		// transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(targetDoc), new StreamResult(writer));
		String output = writer.getBuffer().toString();// .replaceAll("\n|\r",
														// "");
		System.out.println(output);

		return "";

	}

}
