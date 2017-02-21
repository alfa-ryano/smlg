package uk.ac.york.cs.es.smlg.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import uk.ac.york.cs.es.smlg.ModelPost;
import uk.ac.york.cs.es.smlg.model.SMLGResult;

public class SMLGAdapter {

	public static boolean createMxGraphFile(String path, String xml) {
		boolean isSuccess = false;
		try {
			File targetFile = new File(path);
			if (targetFile.createNewFile()) {
				Files.write(targetFile.toPath(), xml.getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
				isSuccess = true;
			} else {
				throw new Exception("ERROR: Fail to create MxGraph file!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public static boolean saveMxGraphFile(String path, String xml) {
		boolean isSuccess = false;
		try {
			File targetFile = new File(path);

			if (!targetFile.exists() || !targetFile.isFile() || xml == null) {
				return false;
			}
			Files.write(targetFile.toPath(), xml.getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
			isSuccess = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public static String readModel(String dirPath, String xmlFilePath) {
		String result = null;
		try {
			File directory = new File(dirPath);
			File xmlFile = new File(xmlFilePath);

			if (directory.exists() && directory.isDirectory() && xmlFile.exists() && xmlFile.isFile()) {
				result = new String(Files.readAllBytes(xmlFile.toPath()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean createModel(String path, String metamodel, String model, String description) {
		boolean isSuccess = false;
		try {
			File metamodelDirectory = new File((path + "/" + metamodel).replace("/", File.separator));
			File modelDirectory = new File((path + "/" + metamodel + "/" + model).replace("/", File.separator));

			File sourceMxGraphFile = new File(
					(path + "/../template/mxgraph.template.xml").replace("/", File.separator));
			File sourceDescriptionFile = new File(
					(path + "/../template/description.template.txt").replace("/", File.separator));
			File targetMxGraphFile = new File(
					(path + "/" + metamodel + "/" + model + "/mxgraph.xml").replace("/", File.separator));
			File targetDescriptionFile = new File(
					(path + "/" + metamodel + "/" + model + "/description.txt").replace("/", File.separator));

			if (!metamodelDirectory.exists()) {
				if (!metamodelDirectory.mkdir()) {
					throw new Exception("Cannot create metamodel directory!");
				}
			}
			if (!modelDirectory.exists()) {
				if (modelDirectory.mkdir()) {
					Files.copy(sourceMxGraphFile.toPath(), targetMxGraphFile.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
					Files.copy(sourceDescriptionFile.toPath(), targetDescriptionFile.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
					List<String> lines = Arrays.asList(description.split("\n"));
					// Files.write(targetDescriptionFile.toPath(),
					// description.getBytes("UTF-8"),
					// StandardOpenOption.TRUNCATE_EXISTING);
					Files.write(targetDescriptionFile.toPath(), lines, Charset.forName("UTF-8"));
				} else {
					throw new Exception("Failed to create model directory!");
				}
			} else {
				throw new Exception("Model already existed!");
			}
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public static boolean createLearningDesign(String path, String name, String description) {
		boolean isSuccess = false;
		try {
			File targetDirectory = new File((path + "/" + name).replace("/", File.separator));
			File sourceMxGraphFile = new File(
					(path + "/../template/mxgraph.template.xml").replace("/", File.separator));
			File sourceDescriptionFile = new File(
					(path + "/../template/description.template.txt").replace("/", File.separator));
			File targetMxGraphFile = new File((path + "/" + name + "/mxgraph.xml").replace("/", File.separator));
			File targetDescriptionFile = new File(
					(path + "/" + name + "/description.txt").replace("/", File.separator));

			if (!targetDirectory.exists()) {
				if (targetDirectory.mkdir()) {
					Files.copy(sourceMxGraphFile.toPath(), targetMxGraphFile.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
					Files.copy(sourceDescriptionFile.toPath(), targetDescriptionFile.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
					List<String> lines = Arrays.asList(description.split("\n"));
					// Files.write(targetDescriptionFile.toPath(),
					// description.getBytes("UTF-8"),
					// StandardOpenOption.TRUNCATE_EXISTING);
					Files.write(targetDescriptionFile.toPath(), lines, Charset.forName("UTF-8"));

					System.out.println("The new directory is successfully created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			} else {
				System.out.println("File is already created!");
				throw new Exception("File is already created!");
			}
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static boolean deleteModel(String path, String name) {
		boolean isSuccess = false;
		try {
			File targetDirectory = new File((path + "/" + name).replace("/", File.separator));

			if (targetDirectory.exists()) {
				deleteDir(targetDirectory);
			} else {
				System.out.println("File does not exist!");
				throw new Exception("File does not exist!");
			}
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public static String capitalizeFirstLetter(String text) {
		return text.trim().substring(0, 1).toUpperCase() + text.substring(1);
	}

	public static String getPackageName(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
		doc.getDocumentElement().normalize();

		Node rootNode = doc.getDocumentElement().getElementsByTagName("root").item(0);

		String packageName = null;
		for (int i = 0; i < rootNode.getChildNodes().getLength(); i++) {
			Node node = rootNode.getChildNodes().item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			String id = node.getAttributes().getNamedItem("id").getNodeValue();
			if (id.equals("1")) {
				packageName = node.getAttributes().getNamedItem("package").getNodeValue();
				break;
			}
		}
		return packageName;
	}

	public static ResourceSet createModelResourceSet(String packageName)
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, NoSuchMethodException, InvocationTargetException {

		String fullClassName = packageName + "." + packageName.substring(0, 1).toUpperCase()
				+ packageName.substring(1, packageName.length()) + "Package";
		ClassLoader classLoader = ModelPost.class.getClassLoader();
		Class modellingPackage = classLoader.loadClass(fullClassName);
		Object eInstance = modellingPackage.getField("eINSTANCE").get(modellingPackage);
		Method method = eInstance.getClass().getMethod("getNsURI", new Class[] {});

		ResourceSet currentResourceSet = new ResourceSetImpl();
		currentResourceSet.getPackageRegistry().put(method.invoke(eInstance, new Object[] {}).toString(), eInstance);
		currentResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
				new MxGraphXMLResourceFactory());
		return currentResourceSet;
	}

	public static Resource createModelResource(ResourceSet resourceSet, String xml, String modelFileName)
			throws IOException {
		Resource currentResourceMxGraphXml = resourceSet.createResource(URI.createURI(modelFileName));
		currentResourceMxGraphXml.load(new ByteArrayInputStream(xml.getBytes()), null);
		return currentResourceMxGraphXml;
	}

	public static String createModelXmi(ResourceSet resourceSet, Resource resource, String xmiPath, String fileName)
			throws IOException {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		Map<String, String> options = new HashMap<>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		Resource currentResourceSaveToXmi = resourceSet.createResource(URI.createFileURI(xmiPath + fileName));
		for (int i = 0; i < resource.getContents().size(); i++) {
			EObject item = resource.getContents().get(i);
			currentResourceSaveToXmi.getContents().add(item);
		}
		currentResourceSaveToXmi.save(options);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		currentResourceSaveToXmi.save(baos, options);
		String text = new String(baos.toByteArray());
		return text;
	}

	public static SMLGResult executeEVL(String metamodel, String packageName, InMemoryEmfModel inMemoryEmfModel) throws Exception {
		SMLGResult smlgResult = new SMLGResult();
		// Load EVL module
		IEvlModule evlModule = new EvlModule();
		String source = "/metamodel/" + metamodel + "/" + packageName + ".evl";
		java.net.URI binUri = SMLGUtil.getFileURI(source);
		evlModule.parse(binUri);
		evlModule.getContext().getModelRepository().addModel(inMemoryEmfModel);

		// excute the module
		evlModule.execute();

		// do the validation
		List<UnsatisfiedConstraint> unsatisfiedConstraints = evlModule.getContext().getUnsatisfiedConstraints();
		if (unsatisfiedConstraints.size() > 0) {
			for (UnsatisfiedConstraint unsatisfied : unsatisfiedConstraints) {
				smlgResult.addUnsatisfiedConstraint(unsatisfied.getConstraint().getName(), unsatisfied.getMessage());
			}
			smlgResult.completed = false;
		} else {
			smlgResult.completed = true;
		}

		// cleaning the module
		evlModule.getContext().getModelRepository().dispose();
		return smlgResult;
	}

	public static SMLGResult validateModel(String path, String mode, String metamodel, String model, String xml) {
		SMLGResult smlgResult = new SMLGResult();
		try {
			String modelFileName = "model";
			String realFilePath = null;
			if (mode.equals("modelling")) {
				realFilePath = (path + "/" + mode + "/" + metamodel + "/" + model + "/").replace("/", File.separator);
			} else if (mode.equals("learning")) {
				realFilePath = (path + "/" + mode + "/" + model + "/").replace("/", File.separator);
			} else if (mode.equals("gaming")) {
				realFilePath = (path + "/" + mode + "/" + model + "/").replace("/", File.separator);
			}

			String packageName = SMLGAdapter.getPackageName(xml);
			ResourceSet resourceSet = SMLGAdapter.createModelResourceSet(packageName);
			Resource modelResource = SMLGAdapter.createModelResource(resourceSet, xml, modelFileName + ".xml");
			SMLGAdapter.createModelXmi(resourceSet, modelResource, realFilePath,
					modelFileName + ".xmi");

			// create in memory Emf Model and add the model to Validation EVL
			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(modelResource);
			inMemoryEmfModel.setName(packageName);
			
			smlgResult = executeEVL(metamodel, packageName, inMemoryEmfModel);

		} catch (Exception e) {
			e.printStackTrace();
			smlgResult.completed = false;
		}
		return smlgResult;
	}
	
	public static SMLGResult generateGame(String storyPath, String xml) {
		SMLGResult smlgResult = new SMLGResult();
		try {
			String modelFileName = "model";
			String metamodel = "eoml";
			String mode = "gaming";
			String realFilePath = (storyPath + "/").replace("/", File.separator);
			
			String packageName = SMLGAdapter.getPackageName(xml);
			ResourceSet resourceSet = SMLGAdapter.createModelResourceSet(packageName);
			Resource modelResource = SMLGAdapter.createModelResource(resourceSet, xml, modelFileName + ".xml");
			String xmiText = SMLGAdapter.createModelXmi(resourceSet, modelResource, realFilePath,
					modelFileName + ".xmi");

			// create in memory Emf Model and add the model to Validation EVL
			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(modelResource);
			inMemoryEmfModel.setName(packageName);
			
			smlgResult = executeEVL(metamodel, packageName, inMemoryEmfModel);

		} catch (Exception e) {
			e.printStackTrace();
			smlgResult.completed = false;
		}
		return smlgResult;
	}

}
