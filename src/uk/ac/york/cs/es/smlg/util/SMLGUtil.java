package uk.ac.york.cs.es.smlg.util;

import java.net.URISyntaxException;

import uk.ac.york.cs.es.smlg.ModelPost;

public class SMLGUtil {

	public static String capitalizeFirstLetter(String text) {
		return text.trim().substring(0, 1).toUpperCase() + text.substring(1);
	}

	public static java.net.URI getFileURI(String fileName) throws URISyntaxException {
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

}
