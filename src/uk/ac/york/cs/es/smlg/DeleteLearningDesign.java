package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.york.cs.es.smlg.util.SMLGUtil;

/**
 * Servlet implementation class DeleteLearningDesign
 */
@WebServlet(description = "Delete Learning Design", urlPatterns = { "/DeleteLearningDesign" })
public class DeleteLearningDesign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteLearningDesign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/text");

			String name = request.getParameter("name");

			if (name == null || name.trim().length() < 0)
				throw new Exception("Error: Name is not defined!");

			String path = (getServletContext().getRealPath(".") + "/learning").replace("/", File.separator);
			boolean result = SMLGUtil.deleteLearningDesign(path, name);
			
			if (result) {
				response.getWriter().append("Success: " + SMLGUtil.capitalizeFirstLetter(name) + " has just been deleted!");
			} else {
				throw new Exception("Error: Failed deleting learning design!");
			}
		} catch (Exception e) {
			response.getWriter().append(e.getMessage());
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
