/**
 * 
 */
package uk.ac.york.cs.es.smlg.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.york.cs.es.smlg.GSMUtil;
import uk.ac.york.cs.es.smlg.MxGraphXMLResourceFactory;

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
			String xmi = GSMUtil.convertToXMIfromXmXMLFile(path);
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

			//resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
					new XMIResourceFactoryImpl());

//			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
//			Resource resource = resourceSet
//					.createResource(URI.createURI(FlexmiResource.class.getResource("sample.xml").toString()));
//			resource.load(null);
//			EObject object = resource.getContents().get(0);

			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml",
					new MxGraphXMLResourceFactory());
			Resource resource3 = resourceSet.createResource(
					URI.createFileURI("D:/A-DATA/GoogleDriveYork/git/smlg/resources/modelFromMx.xml"));
			resource3.load(null);
		
			Map<String, String> options = new HashMap<>();
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			Resource resource2 = resourceSet.createResource(URI.createFileURI("D:/test.xmi"));
			for (int i = 0; i < resource3.getContents().size(); i++){
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
