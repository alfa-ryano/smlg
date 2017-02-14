package uk.ac.york.cs.es.smlg.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.york.cs.es.smlg.util.SMLGAdapter;

public class DesignLearningTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		boolean isTrue = false;
		String path = ("D:/A-DATA/GoogleDriveYork/git/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/smlg"
				+ "/learning").replace("/", File.separator);
		isTrue = SMLGAdapter.createLearningDesign(path, "My Title", "My Description");
		Assert.assertEquals(true, isTrue);
	}

}
