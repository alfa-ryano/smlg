package uk.ac.york.cs.es.smlg.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uk.ac.york.cs.es.smlg.ModelPost;

public class MxGraphXMLResource2 extends ResourceImpl {

	private static final String GSM_GMF_LINK = "gmf.link";
	private static final String GSM_TARGET = "target";
	private static final String GSM_SOURCE = "source";
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

	public MxGraphXMLResource2(URI uri) {
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
		
		//Fill object list first
		for (int i = 0; i < sourceItemNodes.getLength(); i++) {
			Node sourceItemNode = sourceItemNodes.item(i);
			if (sourceItemNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			String id = sourceItemNode.getAttributes().getNamedItem(GSM_ID).getNodeValue();
			if (id.equals("0")) continue;
			if (id.equals("1")) {
				packageName = sourceItemNode.getAttributes().getNamedItem("package").getNodeValue();
			}
			String parentId = null;
			for (int j = 0; j < sourceItemNode.getChildNodes().getLength(); j++) {
				Node node = sourceItemNode.getChildNodes().item(j);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getNodeName().equals("mxCell")) {
						parentId = node.getAttributes().getNamedItem(GSM_PARENT).getNodeValue();
					}
				}
			}
			
			String className = sourceItemNode.getNodeName();
			System.out.println(id + ": " + className);
			
			if (className.equals("GSMContainer")) {
				String sfName = sourceItemNode.getAttributes().getNamedItem("name").getNodeValue();
				objectList.put(id, new EObjectAdapter(id, null, parentId, sfName));
				continue;
			}
			
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
		}

		//iterate through all the child elements of root element
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
				boolean isEdge = false;
				String sourceId = null;
				String targetId = null;
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
						isEdge = true;
						sourceId = node.getAttributes().getNamedItem(GSM_SOURCE).getNodeValue();
						targetId = node.getAttributes().getNamedItem(GSM_TARGET).getNodeValue();
					} else {
						isEdge = false;
					}
				}

				if (sourceItemNode.getAttributes().getNamedItem("gsmReference") != null) {
					gsmReference = sourceItemNode.getAttributes().getNamedItem("gsmReference").getNodeValue();
					if (gsmReference != null) {
						EObject sourceObject = findParentEObject(sourceId);
						EObject targetObject = findParentEObject(targetId);
						
						sourceObject.eSet(sourceObject.eClass().getEStructuralFeature(gsmReference), targetObject);
						continue;
					}
				}

				// if parentObject is null the it must be a GSMContainer which is a dummy node
				// so we should take the grandParentObject (or the parent of parentObject) as 
				// as the parent object while keeping parentStructuralFeatureName variable
				// as the name of the attribute that child will be in its grand parent object.
				String parentStructuralFeatureName = null;
				EObjectAdapter parentObjectAdapter = findParentEObjectAdapter(parentId);
				if (parentObjectAdapter.getObject() == null) {
					parentStructuralFeatureName = parentObjectAdapter.getParentStructuralFeatureName();
					String grandParentId = parentObjectAdapter.getParentId();
					parentObjectAdapter = findParentEObjectAdapter(grandParentId);
				}
				EObject eParentObject = parentObjectAdapter.getObject();
				String targetParentId = parentObjectAdapter.getId();

				//
				if (targetParentId.equals("1")) {
					String className = sourceItemNode.getNodeName();

					EClassifier eCurrentClassifier = ePackage.getEClassifier(className);
					EClass eCurrentClass = (EClass) eCurrentClassifier;
					EObject eCurrentObject = ePackage.getEFactoryInstance().create(eCurrentClass);

					if (isEdge == true) {
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
						if (sourceId != null)
							sourceObject = findParentEObject(sourceId);
						if (targetId != null)
							targetObject = findParentEObject(targetId);
						if (sourceObject != null && targetObject != null) {
							eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(sourceName), sourceObject);
							eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(targetName), targetObject);
						}
					}

					for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
						String name = sourceItemNode.getAttributes().item(k).getNodeName();
						String value = sourceItemNode.getAttributes().item(k).getNodeValue();

						EClass tempEClass = eCurrentObject.eClass();
						while (tempEClass != null) {
							for (int n = 0; n < tempEClass.getEStructuralFeatures().size(); n++) {
								EStructuralFeature ef = tempEClass.getEStructuralFeatures().get(n);
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

				if (isEdge == true) {
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
					if (sourceId != null)
						sourceObject = findParentEObject(sourceId);
					if (targetId != null)
						targetObject = findParentEObject(targetId);
					if (sourceObject != null && targetObject != null) {
						eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(sourceName), sourceObject);
						eCurrentObject.eSet(eCurrentClass.getEStructuralFeature(targetName), targetObject);
					}
				}
				
				for (int k = 0; k < sourceItemNode.getAttributes().getLength(); k++) {
					String name = sourceItemNode.getAttributes().item(k).getNodeName();
					String value = sourceItemNode.getAttributes().item(k).getNodeValue();

					EClass tempEClass = eCurrentObject.eClass();
					while (tempEClass != null) {
						for (int n = 0; n < tempEClass.getEStructuralFeatures().size(); n++) {
							EStructuralFeature ef = tempEClass.getEStructuralFeatures().get(n);
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
}
