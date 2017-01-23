/**
 * 
 */
package uk.ac.york.cs.es.smlg.test;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.york.cs.es.smlg.GSMUtil;

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
	public void test() throws Exception {
		
		String path = System.getProperty("user.dir") + "\\resources\\modelFromMx.xml";
		System.out.println(path);
		try{
			GSMUtil.convertToXMIfromXmXMLFile(path);
		}catch(Exception exe){
			exe.printStackTrace();
		}
		File file = new File(path);
		boolean isFileExist = false;
		if(file.exists() && !file.isDirectory()) { 
		    isFileExist = true;
		}
		Assert.assertEquals(true, isFileExist);
		
	}

}
