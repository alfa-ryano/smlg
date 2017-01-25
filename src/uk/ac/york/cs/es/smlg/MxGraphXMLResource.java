package uk.ac.york.cs.es.smlg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MxGraphXMLResource extends ResourceImpl {

	private static final String GSM_PARENT = "parent";
	private static final String GSM_ID = "id";
	protected Element sourceRootElement;
	private EPackage ePackage;

	public MxGraphXMLResource(URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		try {
			doLoadImpl(inputStream, options);
		} catch (IOException ioException) {
			ioException.printStackTrace();
			throw ioException;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public void doLoadImpl(InputStream inputStream, Map<?, ?> options) throws Exception {
		getContents().clear();

		Document doc = initializeDocument(inputStream);
		sourceRootElement = (Element) doc.getDocumentElement().getElementsByTagName("root").item(0);
		transformToTargetXML(sourceRootElement);
	}

	private Document initializeDocument(InputStream inputStream) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(inputStream));

		doc.getDocumentElement().normalize();

		if (doc.getElementsByTagName("mxGraphModel").getLength() == 0) {
			throw new Exception("The XML document does not have mxGraphModel element");
		}
		if (doc.getElementsByTagName("root").getLength() == 0) {
			throw new Exception("The XML document does not have root element");
		}
		return doc;
	}

	private void transformToTargetXML(Element sourceRootElement)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		Node sourceRootNode = (Node) sourceRootElement;

		if (!sourceRootNode.hasChildNodes()) {
			return;
		}

		NodeList sourceItemNodes = sourceRootNode.getChildNodes();

		ArrayList<Node> edgeNodes = new ArrayList<>();
		String uriName = null;
		String prefixName = null;
		String diagramName = null;
		String packageName = null;

		for (int i = 0; i < sourceItemNodes.getLength(); i++) {

			Node sourceItemNode = sourceItemNodes.item(i);
			if (sourceItemNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			String id = sourceItemNode.getAttributes().getNamedItem(GSM_ID).getNodeValue();
			if (id.equals("0")) {
				continue;
			}
			// create the root first
			if (id.equals("1")) {
				// create the root class for the EMF model
				String className = sourceItemNode.getNodeName();

				uriName = sourceItemNode.getAttributes().getNamedItem("uri").getNodeValue();
				prefixName = sourceItemNode.getAttributes().getNamedItem("prefix").getNodeValue();
				packageName = sourceItemNode.getAttributes().getNamedItem("package").getNodeValue();
				String fullClassName = packageName + "." + packageName.substring(0, 1).toUpperCase()
						+ packageName.substring(1, packageName.length()) + "Package";

				ClassLoader classLoader = ModelPost.class.getClassLoader();
				Class modellingPackage = classLoader.loadClass(fullClassName);

				ePackage = (EPackage) modellingPackage.getField("eINSTANCE").get(modellingPackage);
				getResourceSet().getPackageRegistry().put(ePackage.getNsURI(), ePackage);

				EClassifier eClassifier = ePackage.getEClassifier(className);
				EClass eClass = (EClass) eClassifier;
				
				EAttribute e = 
				eClass.getEAttributes().add(e);
				EObject eObject =  ePackage.getEFactoryInstance().create(eClass);
			
				for (EReference item : eClass.getEReferences()){
					System.out.println(item.getName());
				}
				
				EClassifier eClassifier2 = ePackage.getEClassifier("Drive");
				EClass eClass2 = (EClass) eClassifier2;
				EObject eObject2 =  ePackage.getEFactoryInstance().create(eClass2);
				
				Object feature = eObject.eGet(eClass.getEStructuralFeature("drives"));
				System.out.println(feature.getClass().getName());
				EObjectContainmentEList<Object> temp = (EObjectContainmentEList<Object>) feature;
				temp.add(eObject2);
				System.out.println(temp.size());
				
				//eClass.eSet(feature, eObject2); 
				
				
				// EList<EObject> objects = eObject.eContents();
				// eObject.eSet(feature, objects);
				getContents().add(eObject);
				//getContents().add(eObject2);
			} else {
				// get parent id, cell type (edge or vertex), source, and target
//				String parentId = null;
//				String isEdge = "false";
//				String source = null;
//				String target = null;
//				for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
//					Node node = sourceItemNode.getChildNodes().item(j);
//					if (node.getNodeType() != Node.ELEMENT_NODE
//							|| node.getAttributes().getNamedItem(GSM_PARENT) == null) {
//						continue;
//					}
//					parentId = node.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
//					if (node.getAttributes().getNamedItem("edge") != null
//							&& node.getAttributes().getNamedItem("edge").getNodeValue().equals("1")) {
//						isEdge = "true";
//						source = node.getAttributes().getNamedItem("source").getNodeValue();
//						target = node.getAttributes().getNamedItem("target").getNodeValue();
//					} else {
//						isEdge = "false";
//					}
//				}
//
//				// get parent node
//				String className = sourceItemNode.getNodeName();
//				EClassifier eClassifier = ePackage.getEClassifier(className);
//				EClass eClass = eClassifier.eClass();
//				eClass.setName(className);
//				eClass.setInstanceClassName(className);
//				EObject eObject = ePackage.getEFactoryInstance().create(eClass);
//				getContents().add(eObject);

			}
		}

		//

	}

	private Node findNode(Node node, String searchedId) {
		Node target = null;
		if (node.getNodeType() == Node.ELEMENT_NODE && node.getAttributes().getNamedItem(GSM_ID) != null) {
			String targetParentId = node.getAttributes().getNamedItem(GSM_ID).getNodeValue();
			if (targetParentId.equals(searchedId)) {
				return node;
			}
		}
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node childNode = node.getChildNodes().item(j);
			target = findNode(childNode, searchedId);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

}
