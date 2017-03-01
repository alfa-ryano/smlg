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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String metamodel = request.getParameter("metamodel");
			String mode = request.getParameter("mode");
			String model = request.getParameter("model");
			String game = request.getParameter("game");
			
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

			String xmlPathParam = directoryPathParam + "/mxgraph.xml";
			String path = getServletContext().getRealPath((directoryPathParam).replace("/", File.separator));
			String xmlStringPath = getServletContext().getRealPath((xmlPathParam).replace("/", File.separator));

			String xmlString = SMLGAdapter.readModel(path, xmlStringPath);
			if (xmlString != null) {
				response.setContentType("application/xml");
				response.getWriter().append(xmlString);
			} else {
				throw new Exception();
			}
		} catch (Exception ex) {
			response.setContentType("application/text");
			response.getWriter().append("ERROR: Process or model that you choose does not exist!");
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
