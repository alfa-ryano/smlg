package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoadModel
 */
@WebServlet(description = "Load Model", urlPatterns = { "/LoadModel" })
public class LoadModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadModel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		String metamodel = request.getParameter("metamodel");
		String mode = request.getParameter("mode");
		String model = request.getParameter("model");
		
		String directoryPathParam = "./" + mode +"/" + model;
		String xmlPathParam = "./" + mode +"/" + model + "/mxgraph.xml"; 
		String path = getServletContext().getRealPath((directoryPathParam).replace("/", File.separator));	
		String xmlPath = getServletContext().getRealPath((xmlPathParam).replace("/", File.separator));
		
		File directory = new File(path);
		File xmlFile = new File(xmlPath);
		
		if (directory.exists() && directory.isDirectory() && xmlFile.exists() && xmlFile.isFile()) {
			String xmlString = new String(Files.readAllBytes(xmlFile.toPath()));
			
			response.setContentType("application/xml");
			response.getWriter().append(xmlString);
		}
		}catch(Exception ex){
			response.setContentType("application/text");
			response.getWriter().append("ERROR: Process or model that you choose does not exist!");
		}
		response.getWriter().flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
