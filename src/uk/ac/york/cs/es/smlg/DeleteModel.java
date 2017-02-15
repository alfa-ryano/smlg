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
 * Servlet implementation class DeleteModel
 */
@WebServlet(description = "DeleteModel", urlPatterns = { "/DeleteModel" })
public class DeleteModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteModel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/text");

			String metamodel =request.getParameter("metamodel");
			String model = request.getParameter("model");

			if (model == null || model.trim().length() < 0)
				throw new Exception("Error: Model is not defined!");

			String path = (getServletContext().getRealPath(".") + "/modelling/" + metamodel).replace("/", File.separator);
			boolean result = SMLGAdapter.deleteModel(path, model);
			
			if (result) {
				response.getWriter().append("Success: " + SMLGAdapter.capitalizeFirstLetter(model) + " has just been deleted!");
			} else {
				throw new Exception("Error: Failed deleting model!");
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
