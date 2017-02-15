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
 * Servlet implementation class CreateModel
 */
@WebServlet(description = "Create Model", urlPatterns = { "/CreateModel" })
public class CreateModel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateModel() {
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

			String metamodel= request.getParameter("metamodel");
			String name = request.getParameter("name");
			String description = request.getParameter("description");

			if (metamodel == null || name == null || name.trim().length() < 0)
				throw new Exception("Error: Metamodel or name is not defined!");

			String path = (getServletContext().getRealPath(".") + "/modelling").replace("/", File.separator);
			boolean result = SMLGAdapter.createModel(path, metamodel, name, description);

			if (result) {
				response.getWriter().append("Success: A model has just been created!");
			} else {
				throw new Exception("Error: Failed creating model!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append(e.getMessage());
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
