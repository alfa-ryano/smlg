package uk.ac.york.cs.es.smlg.util;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class SMLGUtil {

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
					//Files.write(targetDescriptionFile.toPath(), description.getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
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
	
	private static boolean deleteDir(File dir) {
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

	public static boolean deleteLearningDesign(String path, String name) {
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
	
	public static String capitalizeFirstLetter(String text){
		return text.trim().substring(0, 1).toUpperCase() + text.substring(1);
	}
}
