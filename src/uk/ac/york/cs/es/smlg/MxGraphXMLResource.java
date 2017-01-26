package uk.ac.york.cs.es.smlg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.eclipse.emf.ecore.EcorePackage;
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
	protected static final String GSM_ID = "id";
	protected final HashMap<String, EObjectAdapter> objectList = new HashMap<>();
	protected Element sourceRootElement;
	private EPackage ePackage;

	protected class EObjectAdapter {
		private String id;
		private EObject object;
		private String parentId;
		private String parentStructuralFeatureName;

		public String getParentStructuralFeatureName() {
			return parentStructuralFeatureName;
		}

		public void setParentStructuralFeatureName(String parentStructuralFeatureName) {
			this.parentStructuralFeatureName = parentStructuralFeatureName;
		}

		public EObjectAdapter(String id, EObject object, String parentId, String parentStructuralFeatureName) {
			this.id = id;
			this.object = object;
			this.parentId = parentId;
			this.parentStructuralFeatureName = parentStructuralFeatureName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public EObject getObject() {
			return object;
		}

		public void setObject(EObject object) {
			this.object = object;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

	}

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
		EObject eRootObject = null;

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

				String parentId = null;
				for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
					Node node = sourceItemNode.getChildNodes().item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println(node.getNodeName());
						if (node.getNodeName().equals("mxCell")) {
							parentId = node.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
						}
					}
				}

				ClassLoader classLoader = ModelPost.class.getClassLoader();
				Class modellingPackage = classLoader.loadClass(fullClassName);

				ePackage = (EPackage) modellingPackage.getField("eINSTANCE").get(modellingPackage);
				getResourceSet().getPackageRegistry().put(ePackage.getNsURI(), ePackage);

				EClassifier eClassifier = ePackage.getEClassifier(className);
				EClass eRootClass = (EClass) eClassifier;
				eRootObject = ePackage.getEFactoryInstance().create(eRootClass);

				objectList.put(id, new EObjectAdapter(id, eRootObject, parentId, null));
			} else {

				// get parent id, cell type (edge or vertex), source, and target
				String parentId = null;
				String isEdge = "false";
				String source = null;
				String target = null;
				String gsmReference = null;

				for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
					Node node = sourceItemNode.getChildNodes().item(j);
					if (node.getNodeType() != Node.ELEMENT_NODE
							|| node.getAttributes().getNamedItem(GSM_PARENT) == null) {
						continue;
					}
					parentId = node.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
					if (node.getAttributes().getNamedItem("edge") != null
							&& node.getAttributes().getNamedItem("edge").getNodeValue().equals("1")) {
						isEdge = "true";
						source = node.getAttributes().getNamedItem("source").getNodeValue();
						target = node.getAttributes().getNamedItem("target").getNodeValue();
					} else {
						isEdge = "false";
					}
				}

				if (sourceItemNode.getAttributes().getNamedItem("gsmReference") != null) {
					gsmReference = sourceItemNode.getAttributes().getNamedItem("gsmReference").getNodeValue();
					if (gsmReference != null) {
						continue;
					}
				}

				String parentStructuralFeatureName = null;

				EObjectAdapter parentObjectAdapter = findParentEObjectAdapter(parentId);
				if (parentObjectAdapter.getObject() == null) {
					parentStructuralFeatureName = parentObjectAdapter.getParentStructuralFeatureName();
					String grandParentId = parentObjectAdapter.getParentId();
					parentObjectAdapter = findParentEObjectAdapter(grandParentId);
				}
				EObject eParentObject = parentObjectAdapter.getObject();
				String targetParentId = parentObjectAdapter.getId();

				if (targetParentId.equals("1")) {
					String className = sourceItemNode.getNodeName();

					EClassifier eCurrentClassifier = ePackage.getEClassifier(className);
					EClass eCurrentClass = (EClass) eCurrentClassifier;
					EObject eCurrentObject = ePackage.getEFactoryInstance().create(eCurrentClass);

					for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
						String name = sourceItemNode.getAttributes().item(k).getNodeName();
						String value = sourceItemNode.getAttributes().item(k).getNodeValue();

						EClass tempEClass = eCurrentObject.eClass();
						while (tempEClass != null) {
							for (int n = 0; n < tempEClass.getEStructuralFeatures().size(); n++) {
								EStructuralFeature ef = tempEClass.getEStructuralFeatures().get(n);
								System.out.println(eCurrentClass.getName() + ": " + ef.getName());
								if (ef instanceof EAttribute && ef.getName().equals(name)) {
									eCurrentObject.eSet(ef, value);
								}
							}
							if (tempEClass.getESuperTypes().size() > 0) {
								tempEClass = tempEClass.getESuperTypes().get(0);
							} else {
								tempEClass = null;
							}
						}
					}

					for (EStructuralFeature structuralFeature : eParentObject.eClass().getEStructuralFeatures()) {
						if (structuralFeature.getEType().equals(eCurrentClassifier)) {
							Object sfObject = eParentObject.eGet(structuralFeature);
							if (sfObject instanceof EObjectContainmentEList) {
								EObjectContainmentEList<Object> temp = (EObjectContainmentEList<Object>) sfObject;
								temp.add(eCurrentObject);
							}
						}
					}
					objectList.put(id, new EObjectAdapter(id, eCurrentObject, parentId, null));
					continue;
				}

				String className = sourceItemNode.getNodeName();

				if (className.equals("GSMContainer")) {
					String sfName = sourceItemNode.getAttributes().getNamedItem("name").getNodeValue();
					objectList.put(id, new EObjectAdapter(id, null, parentId, sfName));
					continue;
				}

				EClassifier eCurrentClassifier = ePackage.getEClassifier(className);
				EClass eCurrentClass = (EClass) eCurrentClassifier;
				EObject eCurrentObject = ePackage.getEFactoryInstance().create(eCurrentClass);

				for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
					String name = sourceItemNode.getAttributes().item(k).getNodeName();
					String value = sourceItemNode.getAttributes().item(k).getNodeValue();

					EClass tempEClass = eCurrentObject.eClass();
					while (tempEClass != null) {
						for (int n = 0; n < tempEClass.getEStructuralFeatures().size(); n++) {
							EStructuralFeature ef = tempEClass.getEStructuralFeatures().get(n);
							System.out.println(eCurrentClass.getName() + ": " + ef.getName());
							if (ef instanceof EAttribute && ef.getName().equals(name)) {
								eCurrentObject.eSet(ef, value);
							}
						}
						if (tempEClass.getESuperTypes().size() > 0) {
							tempEClass = tempEClass.getESuperTypes().get(0);
						} else {
							tempEClass = null;
						}
					}
				}

				EClass eParentClass = eParentObject.eClass();
				Object sfObject = eParentObject.eGet(eParentClass.getEStructuralFeature(parentStructuralFeatureName));
				if (sfObject instanceof EObjectContainmentEList) {
					EObjectContainmentEList<Object> list = (EObjectContainmentEList<Object>) sfObject;
					list.add(eCurrentObject);
				}
				objectList.put(id, new EObjectAdapter(id, eCurrentObject, parentId, null));

			}
		}

		getContents().add(eRootObject);
	}

	private EObjectAdapter findParentEObjectAdapter(String searchedId) {
		EObjectAdapter result = null;
		result = objectList.get(searchedId);
		return result;
	}

	private EObject findParentEObject(String searchedId) {
		EObject result = null;
		result = objectList.get(searchedId).getObject();
		return result;
	}

	// private Node findNode(Node node, String searchedId) {
	// Node target = null;
	// if (node.getNodeType() == Node.ELEMENT_NODE &&
	// node.getAttributes().getNamedItem(GSM_ID) != null) {
	// String targetParentId =
	// node.getAttributes().getNamedItem(GSM_ID).getNodeValue();
	// if (targetParentId.equals(searchedId)) {
	// return node;
	// }
	// }
	// for (int j = 0; j < node.getChildNodes().getLength(); j++) {
	// Node childNode = node.getChildNodes().item(j);
	// target = findNode(childNode, searchedId);
	// if (target != null) {
	// return target;
	// }
	// }
	// return null;
	// }

}
