package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.york.cs.es.smlg.util.SMLGAdapter;

/**
 * Servlet implementation class SaveFile
 */
@WebServlet(description = "Save File", urlPatterns = { "/SaveFile" })
public class SaveFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("application/text");
			
			String metamodel = request.getParameter("metamodel");
			String mode = request.getParameter("mode");
			String model = request.getParameter("model");
			String game = request.getParameter("game");
			String xml = request.getParameter("xml");
			
			String directoryPathParam = "";
			
			if (mode.equals("learning")) {
				directoryPathParam = "./" + mode + "/" + model;
			}
			else if(mode.equals("modelling")) {
				directoryPathParam = "./" + mode + "/" + metamodel + "/" + model;
			}
			else if(mode.equals("gaming")) {
				directoryPathParam = "./" + mode + "/" + game + "/" + model;
			}

			String xmlPathString = directoryPathParam + "/mxgraph.xml";
			String xmlPath = getServletContext().getRealPath((xmlPathString).replace("/", File.separator));
			
			boolean isSuccess =  SMLGAdapter.saveMxGraphFile(xmlPath, xml);
			if (isSuccess){
				response.getWriter().append("SUCCESS: Model has been saved successfully!");
			}
			else{
				throw new Exception("Cannot save file!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().append("ERROR: " + ex.getMessage());
		}
		response.getWriter().flush();
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
