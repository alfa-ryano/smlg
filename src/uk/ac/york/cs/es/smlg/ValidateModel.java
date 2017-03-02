package uk.ac.york.cs.es.smlg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import uk.ac.york.cs.es.smlg.model.SMLGResult;
import uk.ac.york.cs.es.smlg.util.SMLGAdapter;

/**
 * Servlet implementation class ValidateModel
 */
@WebServlet(description = "Validate Model", urlPatterns = { "/ValidateModel" })
public class ValidateModel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidateModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String metamodel = request.getParameter("metamodel");
		String mode = request.getParameter("mode");
		String model = request.getParameter("model");
		String xml = request.getParameter("xml");
		String game = request.getParameter("game");

		try {
			String path = getServletContext().getRealPath(".");
			SMLGResult smlgResult = SMLGAdapter.validateModel(path, mode, metamodel, model, game, xml);
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(smlgResult);

			response.getWriter().append(json);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append("ERROR: " + e.getMessage());
		}

		response.getWriter().append("1");
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
