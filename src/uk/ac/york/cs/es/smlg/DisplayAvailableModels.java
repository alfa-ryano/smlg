package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class DisplayAvailableModels
 */
@WebServlet(description = "Display Available Models", urlPatterns = { "/DisplayAvailableModels" })
public class DisplayAvailableModels extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayAvailableModels() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathParam = request.getParameter("path");

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath((pathParam).replace("/", File.separator));

		File file = new File(path);

		HashMap<String, String> fileNames = new HashMap<>();
		if (file.exists() && file.isDirectory()) {

			File[] subfiles = file.listFiles();

			for (File subfile : subfiles) {
				String descriptionFilePath = (subfile.getAbsolutePath() + "/description.txt").replace("/",
						File.separator);
				File descriptionFile = new File(descriptionFilePath);
				String description = new String(Files.readAllBytes(descriptionFile.toPath()));
				fileNames.put(subfile.getName(), description);
			}

			
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(fileNames);
		response.setContentType("application/json");
		response.getWriter().append(json);
		
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
