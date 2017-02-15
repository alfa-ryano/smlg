package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ListFiles
 */
@WebServlet(description = "ListFiles", urlPatterns = { "/ListFiles" })
public class ListFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String pathParam = request.getParameter("path");
		
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath((pathParam).replace("/", File.separator));
		
		File file = new File(path);

		if (file.exists() && file.isDirectory()) {

			File[] subfiles = file.listFiles();

			ArrayList<String> fileNames = new ArrayList<>();
			for (File subfile : subfiles) {
				fileNames.add(subfile.getName());
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(fileNames);			
			response.setContentType("application/json");
			response.getWriter().append(json);
		}else{
			response.setContentType("application/json");
			response.getWriter().append("[]");
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
