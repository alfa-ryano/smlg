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
 * Servlet implementation class CreateLearningDesign
 */
@WebServlet(description = "Create Learning Design", urlPatterns = { "/CreateLearningDesign" })
public class CreateLearningDesign extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateLearningDesign() {
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

			String name = request.getParameter("name");
			String description = request.getParameter("description");

			if (name == null || name.trim().length() < 0)
				throw new Exception("Error: Name is not defined!");

			String path = (getServletContext().getRealPath(".") + "/learning").replace("/", File.separator);
			boolean result = SMLGAdapter.createLearningDesign(path, name, description);

			if (result) {
				response.getWriter().append("Success: Learning design has just been created!");
			} else {
				throw new Exception("Error: Failed creating learning design!");
			}
		} catch (Exception e) {
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
