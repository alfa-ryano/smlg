package uk.ac.york.cs.es.smlg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import uk.ac.york.cs.es.smlg.model.GSMResult;

/**
 * Servlet implementation class ModelPost
 */
@WebServlet(description = "Posting Model from Client to Server", urlPatterns = { "/ModelPost" })
public class ModelPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModelPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		// Get json string
		StringBuilder stringBuilder = new StringBuilder();
		String result;
		while ((result = request.getReader().readLine()) != null) {
			stringBuilder.append(result);
		}
		String xml = stringBuilder.toString();

		try {
			String xmi = GSMUtil.transformMxXMLtoXMI(xml);
			
			String packageName = "myPackage";
			String fullClassName = packageName + "." + packageName.substring(0, 1).toUpperCase()
					+ packageName.substring(1, packageName.length()) + "Package";

			ClassLoader classLoader = ModelPost.class.getClassLoader();
			Class modellingPackage = classLoader.loadClass(fullClassName);
			Object eInstance = modellingPackage.getField("eINSTANCE").get(modellingPackage);
			Method method = eInstance.getClass().getMethod("getNsURI", new Class[] {});

			ResourceSet currentResourceSet = new ResourceSetImpl();
			currentResourceSet.getPackageRegistry().put(method.invoke(eInstance, new Object[] {}).toString(),
					eInstance);
			currentResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
					new MxGraphXMLResourceFactory());
			currentResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
					new XMIResourceFactoryImpl());

			Resource currentResourceMxGraphXml = currentResourceSet.createResource(URI.createURI("model.xml"));
			currentResourceMxGraphXml.load(new ByteArrayInputStream(xml.getBytes()), null);

			Map<String, String> options = new HashMap<>();
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			Resource currentResourceSaveToXmi = currentResourceSet.createResource(URI.createFileURI("D:/model.xmi"));
			for (int i = 0; i < currentResourceMxGraphXml.getContents().size(); i++) {
				EObject item = currentResourceMxGraphXml.getContents().get(i);
				currentResourceSaveToXmi.getContents().add(item);
			}
			currentResourceSaveToXmi.save(options);

			// Load EVL module
			IEvlModule evlModule = new EvlModule();
			String source = "/metamodel/filesystem/filesystem.evl";
			java.net.URI binUri = getFileURI(source);
			evlModule.parse(binUri);

			// create in memory Emf Model and add the model to Validation EVL
			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(currentResourceMxGraphXml);
			inMemoryEmfModel.setName(packageName);
			evlModule.getContext().getModelRepository().addModel(inMemoryEmfModel);

			// excute the module
			evlModule.execute();

			// do the validation
			GSMResult gsmResult = new GSMResult();
			List<UnsatisfiedConstraint> unsatisfiedConstraints = evlModule.getContext().getUnsatisfiedConstraints();
			if (unsatisfiedConstraints.size() > 0) {
				for (UnsatisfiedConstraint unsatisfied : unsatisfiedConstraints) {
					gsmResult.addUnsatisfiedConstraint(unsatisfied.getConstraint().getName(), unsatisfied.getMessage());
				}
			}

			// cleaning the module
			evlModule.getContext().getModelRepository().dispose();

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(gsmResult);

			response.getWriter().append(json);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getWriter().append("1");

	}

	protected java.net.URI getFileURI(String fileName) throws URISyntaxException {
		String path = "";
		String temp = ModelPost.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		for (int i = 0; i <= ModelPost.class.getName().split("\\.").length; i++) {
			path = path + "../";
		}
		path = path + fileName;
		java.net.URL url = ModelPost.class.getResource(path);
		java.net.URI binUri = url.toURI();
		java.net.URI uri = null;

		if (binUri.toString().indexOf("bin") > -1) {
			uri = new java.net.URI(binUri.toString().replaceAll("bin", "src"));
		} else {
			uri = binUri;
		}

		return uri;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
