/**
 * 
 */
package uk.ac.york.cs.es.smlg.test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.internal.EglModule;
import org.eclipse.epsilon.egl.internal.IEglModule;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
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
import uk.ac.york.cs.es.smlg.util.SMLGAdapter;
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

		String path = System.getProperty("user.dir") + "\\trash\\modelFromMx.xml";
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
	public void testMxGraphXMLtoEMF() throws Exception {

		boolean isTrue = false;
		try {
			ResourceSet resourceSet = new ResourceSetImpl();

			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
					new XMIResourceFactoryImpl());
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
					new MxGraphXMLResourceFactory());
			Resource resource3 = resourceSet
					.createResource(URI.createFileURI("D:/A-DATA/GoogleDriveYork/git/smlg/trash/modelFromMx.xml"));
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

	@Test
	public void createLearningDesign() {
		boolean isTrue = false;

		File file = new File("D:/A-DATA/GoogleDriveYork/git/smlg/www/temp/My Title");
		SMLGAdapter.deleteDir(file);
		String path = ("D:/A-DATA/GoogleDriveYork/git/smlg/www/temp/").replace("/", File.separator);
		isTrue = SMLGAdapter.createLearningDesign(path, "My Title", "My Description");
		Assert.assertEquals(true, isTrue);
		// Assert.assertEquals(true, true);
	}

	@Test
	public void testGenerateGame() throws Exception {
		boolean isTrue = false;
		String path = "../../trash/output/";
		String targetFileName = path + "index.html";
		try {
			File file = new File(
					"D:/A-DATA/GoogleDriveYork/git/smlg/www/gaming/Introduction to Tree Model/mxgraph.xml");
			String xml = new String(Files.readAllBytes(file.toPath()));

			String packageName = SMLGAdapter.getPackageName(xml);
			ResourceSet resourceSet = SMLGAdapter.createModelResourceSet(packageName);
			Resource resource = SMLGAdapter.createModelResource(resourceSet, xml, "model.xml");

			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(resource);
			inMemoryEmfModel.setName(packageName);

			EgxModule egxModule = new EgxModule(new EglFileGeneratingTemplateFactory());
			java.net.URL url = new URL("file:/D:/A-DATA/GoogleDriveYork/git/smlg/www/generator/game.generator.egx");
			java.net.URI binUri = url.toURI();
			egxModule.parse(binUri);
			egxModule.getContext().getModelRepository().addModel(inMemoryEmfModel);

			//modify the generation rule of game.generator.egx file
			GenerationRule gr = egxModule.getGenerationRules().get(0); // get the first rule
			List<ModuleElement> list = gr.getChildren();
			for (ModuleElement item : list) {
				if (item instanceof ExecutableBlock) {
					ExecutableBlock<?> eb = (ExecutableBlock<?>) item;
					if (eb.getRole().equals("parameters")) {
						for (ModuleElement element : eb.getChildren()) {
							StatementBlock sb = (StatementBlock) element;
							Statement es = sb.getStatements().get(1); //line two or the 'params.put("path", "../../trash/dummy");'
							for (ModuleElement me: es.getChildren()) {
								OperationCallExpression oce = (OperationCallExpression) me;
								StringLiteral sl = (StringLiteral) oce.getChildren().get(3); //fourth expression or the '"../../trash/dummy"'
								sl.setValue(path); //replace the existing path with a new one
							}
						}
					}
					if (eb.getRole().equals("target")) {
						for (ModuleElement element : eb.getChildren()) {
							StringLiteral sl = (StringLiteral) element;
							sl.setValue(targetFileName);
							System.out.println(sl.getValue());
						}
					}
				}
			}

			egxModule.execute();
			isTrue = true;
		} catch (Exception exe) {
			exe.printStackTrace();
		}
		Assert.assertEquals(true, isTrue);
	}

	@Test
	public void testTemplateFactory() {
		boolean isTrue = false;
		try {
			File file = new File(
					"D:/A-DATA/GoogleDriveYork/git/smlg/www/gaming/Introduction to Tree Model/mxgraph.xml");
			String xml = new String(Files.readAllBytes(file.toPath()));

			String packageName = SMLGAdapter.getPackageName(xml);
			ResourceSet resourceSet = SMLGAdapter.createModelResourceSet(packageName);
			Resource resource = SMLGAdapter.createModelResource(resourceSet, xml, "model.xml");

			InMemoryEmfModel inMemoryEmfModel = new InMemoryEmfModel(resource);
			inMemoryEmfModel.setName(packageName);

			EglFileGeneratingTemplateFactory factory = new EglFileGeneratingTemplateFactory();
			factory.getContext().getModelRepository().addModel(inMemoryEmfModel);
			factory.setOutputRoot("D:/A-DATA/GoogleDriveYork/git/smlg/trash/output");

			File templateFile = new File("D:/A-DATA/GoogleDriveYork/git/smlg/www/template/description.template.txt");

			EglFileGeneratingTemplate template = null;
			System.out.println(templateFile.toURI().toString());
			EglTemplate temp = factory.load(templateFile.toURI());
			template = (EglFileGeneratingTemplate) temp;
			template.populate("name", "value");
			template.generate("a/b.txt");

			isTrue = true;
		} catch (Exception e) {
			e.printStackTrace();
			isTrue = false;
		}
		Assert.assertEquals(true, isTrue);
	}

}
