/**
 * 
 */
package uk.ac.york.cs.es.smlg.test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.internal.EglModule;
import org.eclipse.epsilon.egl.internal.IEglModule;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.york.cs.es.smlg.ModelPost;
import uk.ac.york.cs.es.smlg.util.MxGraphXMLResourceFactory;
import uk.ac.york.cs.es.smlg.util.SMLGTransformer;
import uk.ac.york.cs.es.smlg.util.SMLGUtil;

/**
 * @author Alfa
 *
 */
public class UtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMxGraphXMLtoXMI() throws Exception {

		String path = System.getProperty("user.dir") + "\\resources\\modelFromMx.xml";
		System.out.println(path);

		File file = new File(path);
		boolean isTrue = false;
		if (file.exists() && !file.isDirectory()) {
			isTrue = true;
		}

		try {
			String xmi = SMLGTransformer.convertToXMIfromXmXMLFile(path);
			if (xmi != null && xmi.length() > 0)
				isTrue = true;
		} catch (Exception exe) {
			exe.printStackTrace();
		}

		Assert.assertEquals(true, isTrue);

	}

	@Test
	public void testGenerateGame() throws Exception {
		boolean isTrue = false;
		try {
			String packageName = "eoml";
			String fullClassName = packageName + "." + packageName.substring(0, 1).toUpperCase()
					+ packageName.substring(1, packageName.length()) + "Package";
			ClassLoader classLoader = ModelPost.class.getClassLoader();
			Class modellingPackage = classLoader.loadClass(fullClassName);
			Object eInstance = modellingPackage.getField("eINSTANCE").get(modellingPackage);
			Method method = eInstance.getClass().getMethod("getNsURI", new Class[] {});

			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getPackageRegistry().put(method.invoke(eInstance, new Object[] {}).toString(), eInstance);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
					new XMIResourceFactoryImpl());

			Resource resource = resourceSet
					.createResource(URI.createFileURI("D:/A-DATA/GoogleDriveYork/git/smlg/resources/model.xmi"));
			resource.load(null);
			
			
			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(resource);
			inMemoryEmfModel.setName("eoml");
			
//			IEglModule eglModule = new EglModule();
//			EglTemplate eglTemplate = new EglTemplate(spec, context);
//			eglModule.execute(eglTemplate, arg1);
			
			EgxModule egxModule = new EgxModule(new EglFileGeneratingTemplateFactory());	
			java.net.URL url = new URL("file:/D:/A-DATA/GoogleDriveYork/git/smlg/www/metamodel/eoml/eoml.game.generator.egx");
			java.net.URI binUri = url.toURI();
			egxModule.parse(binUri);
			egxModule.getContext().getModelRepository().addModel(inMemoryEmfModel);
			
			GenerationRule gr = egxModule.getGenerationRules().get(0);
			List<ModuleElement> list = gr.getChildren();
			for(ModuleElement item : list){
				if (item instanceof ExecutableBlock){
					ExecutableBlock eb = (ExecutableBlock) item;
					if (eb.getRole().equals("target")){
						for (ModuleElement element : eb.getChildren()){
							StringLiteral sl = (StringLiteral) element;
							sl.setValue("../../../resources/generated2.txt");
							System.out.println(sl.getValue());
						}
					}
				}
				
			}
			
			egxModule.execute();
			isTrue = true;
		}catch (Exception exe) {
			exe.printStackTrace();
		}
		Assert.assertEquals(true, isTrue);
	}
	
	@Test
	public void testMxGraphXMLtoEMF() throws Exception {

		boolean isTrue = false;
		try {
			ResourceSet resourceSet = new ResourceSetImpl();

			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
					new XMIResourceFactoryImpl());
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
					new MxGraphXMLResourceFactory());
			Resource resource3 = resourceSet
					.createResource(URI.createFileURI("D:/A-DATA/GoogleDriveYork/git/smlg/resources/modelFromMx.xml"));
			resource3.load(null);

			Map<String, String> options = new HashMap<>();
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			Resource resource2 = resourceSet.createResource(URI.createFileURI("D:/test.xmi"));
			for (int i = 0; i < resource3.getContents().size(); i++) {
				EObject item = resource3.getContents().get(i);
				resource2.getContents().add(item);
			}
			resource2.save(options);

			isTrue = true;
		} catch (Exception exe) {
			exe.printStackTrace();
		}

		Assert.assertEquals(true, isTrue);

	}

}
