package uk.ac.york.cs.es.smlg.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uk.ac.york.cs.es.smlg.ModelPost;
import uk.ac.york.cs.es.smlg.util.MxGraphXMLResource2.EObjectAdapter;

public class MxGraphXMLResource extends ResourceImpl {

	private static final String GSM_GMF_LINK = "gmf.link";
	private static final String GSM_TARGET = "target";
	private static final String GSM_SOURCE = "source";
	private static final String GSM_PARENT = "parent";
	protected static final String GSM_ID = "id";
	protected final HashMap<String, EObjectAdapter> objectList = new HashMap<>();
	protected Element sourceRootElement;
	private EPackage ePackage;

	private EObject eRootObject = null;
	private HashMap<String, Node> edgeNodes = new HashMap();
	private String uriName = null;
	private String prefixName = null;
	private String diagramName = null;
	private String packageName = null;

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
		NodeList sourceItemNodes = sourceRootNode.getChildNodes();

		retrieveGlobalVariablesValues(sourceItemNodes);

		fillObjectList(sourceItemNodes);

		System.out.println("-------------------------");
		RemoveGSMContainerObjectAndRelinkItsChildrenToItsParent();
		constructModel();
		connectEdges();

		// FINAL
		getContents().add(eRootObject);
	}

	private String getParentId(Node node) {
		String parentId = null;
		for (int j = 0; j < node.getChildNodes().getLength(); j++) {
			Node subNode = node.getChildNodes().item(j);
			if (subNode.getNodeType() == Node.ELEMENT_NODE) {
				if (subNode.getNodeName().equals("mxCell")) {
					parentId = subNode.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
				}
			}
		}
		return parentId;
	}

	private void RemoveGSMContainerObjectAndRelinkItsChildrenToItsParent() {
		Iterator it = objectList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			String id = (String) pair.getKey();
			EObjectAdapter eObjectAdapter = (EObjectAdapter) pair.getValue();
			EObject eObject = eObjectAdapter.getObject();

			if (eObject == null) {
				String parentId = eObjectAdapter.getParentId();
				String sfName = eObjectAdapter.getParentStructuralFeatureName();
				EObject eParentObject = findParentEObject(parentId);

				Iterator it2 = objectList.entrySet().iterator();
				while (it2.hasNext()) {
					Map.Entry pair2 = (Map.Entry) it2.next();

					String id2 = (String) pair2.getKey();
					EObjectAdapter eObjectAdapter2 = (EObjectAdapter) pair2.getValue();
					String parentId2 = eObjectAdapter2.getParentId();

					if (parentId2.equals(id)) {
						eObjectAdapter2.setParentId(parentId);
						eObjectAdapter2.setParentStructuralFeatureName(sfName);
					}
				}
				eObjectAdapter.setParentId(null);
				it.remove();
			}
		}
	}

	private void constructModel() {
		Iterator it = objectList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());

			String id = (String) pair.getKey();
			EObjectAdapter eObjectAdapter = (EObjectAdapter) pair.getValue();
			EObject eCurrentObject = eObjectAdapter.getObject();
			String parentId = eObjectAdapter.getParentId();
			String sfName = eObjectAdapter.getParentStructuralFeatureName();

			if (id.equals("1"))
				continue;
			EObject eParentObject = findParentEObject(parentId);

			// bind current object to its parent object
			EClass tempEClass = eCurrentObject.eClass();
			recursivelyFindTypeToBindToParent(tempEClass, sfName, eCurrentObject, eParentObject);
		}
	}

	private void recursivelyFindTypeToBindToParent(EClass tempEClass, String sfName, EObject eCurrentObject,
			EObject eParentObject) {
		while (tempEClass != null) {
			boolean foundByName = false;

			EClass tempParentEClass = eParentObject.eClass();
			while (tempParentEClass != null) {
				for (EStructuralFeature structuralFeature : tempParentEClass.getEStructuralFeatures()) {
					if (structuralFeature.getName().equals(sfName)) {
						Object sfObject = eParentObject.eGet(structuralFeature);
						if (sfObject instanceof EObjectEList) {
							EObjectEList<Object> temp = (EObjectEList<Object>) sfObject;
							temp.add(eCurrentObject);
							foundByName = true;
							continue;
						}
					}
				}
				if (tempParentEClass.getESuperTypes().size() > 0) {
					tempParentEClass = tempParentEClass.getESuperTypes().get(0);
				} else {
					tempParentEClass = null;
				}
			}

			if (foundByName == false) {
				tempParentEClass = eParentObject.eClass();
				while (tempParentEClass != null) {
					for (EStructuralFeature structuralFeature : tempParentEClass.getEStructuralFeatures()) {
						if (structuralFeature.getEType().equals(tempEClass)) {
							Object sfObject = eParentObject.eGet(structuralFeature);
							if (sfObject instanceof EObjectEList) {
								EObjectEList<Object> temp = (EObjectEList<Object>) sfObject;
								temp.add(eCurrentObject);
								continue;
							}
						}
					}
					if (tempParentEClass.getESuperTypes().size() > 0) {
						tempParentEClass = tempParentEClass.getESuperTypes().get(0);
					} else {
						tempParentEClass = null;
					}
				}
			}
			if (tempEClass.getESuperTypes().size() > 0) {
				for (EClass parentClass : tempEClass.getESuperTypes()) {
					tempEClass = parentClass;
					recursivelyFindTypeToBindToParent(tempEClass, sfName, eCurrentObject, eParentObject);
				}
			} else {
				tempEClass = null;
			}
		}
	}

	private void recursivelyFindTypeToBindAttributes(String name, String value, EClass tempEClass,
			EObject eCurrentObject) {

		while (tempEClass != null) {
			for (int n = 0; n < tempEClass.getEStructuralFeatures().size(); n++) {
				EStructuralFeature sf = tempEClass.getEStructuralFeatures().get(n);
				if (sf instanceof EAttributeImpl && sf.getName().equals(name)) {
					System.out.println(eCurrentObject.eClass().getName() + ": " + sf.getName() + ": " + value);
					eCurrentObject.eSet(sf, value);
					continue;
				}
			}
			if (tempEClass.getESuperTypes().size() > 0) {
				for (EClass parentClass : tempEClass.getESuperTypes()) {
					tempEClass = parentClass;
					recursivelyFindTypeToBindAttributes(name, value, tempEClass, eCurrentObject);
				}

			} else {
				tempEClass = null;
			}
		}

	}

	private void connectEdges() {
		Iterator it = edgeNodes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			String id = (String) pair.getKey();
			Node node = (Node) pair.getValue();
			String sourceId = null;
			if (node.getAttributes().getNamedItem(GSM_SOURCE) != null) {
				sourceId = node.getAttributes().getNamedItem(GSM_SOURCE).getNodeValue();
			}
			String targetId = null;
			if (node.getAttributes().getNamedItem(GSM_TARGET) != null) {
				targetId = node.getAttributes().getNamedItem(GSM_TARGET).getNodeValue();
			}

			EObject eCurrentObject = findParentEObject(id);
			EClass eCurrentClass = eCurrentObject.eClass();

			String sourceName = null;
			String targetName = null;
			EObject sourceObject = null;
			EObject targetObject = null;
			for (EAnnotation annotation : eCurrentClass.getEAnnotations()) {
				if (annotation.getSource().equals(GSM_GMF_LINK)) {
					sourceName = annotation.getDetails().get(GSM_SOURCE);
					targetName = annotation.getDetails().get(GSM_TARGET);
				}
			}
			if (sourceId != null) {
				sourceObject = findParentEObject(sourceId);
				eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(sourceName), sourceObject);
			}
			if (targetId != null) {
				targetObject = findParentEObject(targetId);
				eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(targetName), targetObject);
			}
		}
	}

	private void fillObjectList(NodeList sourceItemNodes) {
		for (int i = 0; i < sourceItemNodes.getLength(); i++) {
			Node sourceItemNode = sourceItemNodes.item(i);
			if (sourceItemNode.getNodeType() != Node.ELEMENT_NODE)
				continue;
			String id = sourceItemNode.getAttributes().getNamedItem(GSM_ID).getNodeValue();
			if (id.equals("0") || id.equals("1"))
				continue;

			String parentId = getParentId(sourceItemNode);

			String className = sourceItemNode.getNodeName();

			if (className.equals("GSMContainer")) {
				String sfName = sourceItemNode.getAttributes().getNamedItem("name").getNodeValue();
				objectList.put(id, new EObjectAdapter(id, null, parentId, sfName));
				continue;
			}

			// save to temporary egdeNodes list
			for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
				Node node = sourceItemNode.getChildNodes().item(j);
				if (node.getNodeType() != Node.ELEMENT_NODE || node.getAttributes().getNamedItem(GSM_PARENT) == null) {
					continue;
				}
				parentId = node.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
				if (node.getAttributes().getNamedItem("edge") != null
						&& node.getAttributes().getNamedItem("edge").getNodeValue().equals("1")) {
					edgeNodes.put(id, node);
				}
			}

			EClassifier eCurrentClassifier = ePackage.getEClassifier(className);
			EClass eCurrentClass = (EClass) eCurrentClassifier;
			EObject eCurrentObject = ePackage.getEFactoryInstance().create(eCurrentClass);

			for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
				String name = sourceItemNode.getAttributes().item(k).getNodeName();
				String value = sourceItemNode.getAttributes().item(k).getNodeValue();

				EClass tempEClass = eCurrentObject.eClass();
				recursivelyFindTypeToBindAttributes(name, value, tempEClass, eCurrentObject);
			}

			objectList.put(id, new EObjectAdapter(id, eCurrentObject, parentId, null));
		}
	}

	private void retrieveGlobalVariablesValues(NodeList sourceItemNodes) throws ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// get all the values of global variables
		for (int i = 0; i < sourceItemNodes.getLength(); i++) {
			Node sourceItemNode = sourceItemNodes.item(i);
			if (sourceItemNode.getNodeType() != Node.ELEMENT_NODE)
				continue;
			String id = sourceItemNode.getAttributes().getNamedItem(GSM_ID).getNodeValue();
			if (!id.equals("1"))
				continue;

			String parentId = getParentId(sourceItemNode);

			uriName = sourceItemNode.getAttributes().getNamedItem("uri").getNodeValue();
			prefixName = sourceItemNode.getAttributes().getNamedItem("prefix").getNodeValue();
			packageName = sourceItemNode.getAttributes().getNamedItem("package").getNodeValue();

			String className = sourceItemNode.getNodeName();
			String fullClassName = packageName + "." + packageName.substring(0, 1).toUpperCase()
					+ packageName.substring(1, packageName.length()) + "Package";

			ClassLoader classLoader = ModelPost.class.getClassLoader();
			Class modellingPackage = classLoader.loadClass(fullClassName);

			ePackage = (EPackage) modellingPackage.getField("eINSTANCE").get(modellingPackage);
			getResourceSet().getPackageRegistry().put(ePackage.getNsURI(), ePackage);

			EClassifier eClassifier = ePackage.getEClassifier(className);
			EClass eRootClass = (EClass) eClassifier;
			eRootObject = ePackage.getEFactoryInstance().create(eRootClass);

			objectList.put(id, new EObjectAdapter(id, eRootObject, parentId, null));
			break;
		}
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
}
