package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
 * Servlet implementation class GenerateGame
 */
@WebServlet(description = "Generate Game", urlPatterns = { "/GenerateGame" })
public class GenerateGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateGame() {
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

			String mode = request.getParameter("mode").trim();
			String model = request.getParameter("model").trim();
			String xml = request.getParameter("xml").trim();

			if (model == null || model.length() < 0 || model == null || model.length() < 0 || xml == null
					|| xml.length() < 0)
				throw new Exception("ERROR: Metamodel, mode, model, or xml is not defined!");

			if (!mode.equals("gaming"))
				throw new Exception("ERROR: Mode is not 'gaming'");

			String storyPath = (getServletContext().getRealPath(".") + "/" + mode + "/" + model).replace("/",
					File.separator);
			File storyDirectory = new File(storyPath);
			if (storyDirectory.exists())
				SMLGAdapter.deleteDir(storyDirectory);
			boolean targetDir = storyDirectory.mkdir();

			boolean isMxGraphSaved = false;
			if (targetDir) {
				String mxGraphPath = (getServletContext().getRealPath(".") + "/" + mode + "/" + model + "/mxgraph.xml")
						.replace("/", File.separator);
				isMxGraphSaved = SMLGAdapter.createMxGraphFile(mxGraphPath, xml);
			}

			// Copy description file from learning to gaming
			String sourceDescriptionFilePath = (getServletContext().getRealPath(".") + "/learning/" + model
					+ "/description.txt").replace("/", File.separator);
			File sourceDescriptionFile = new File(sourceDescriptionFilePath);
			String targetDescriptionFilePath = (getServletContext().getRealPath(".") + "/gaming/" + model
					+ "/description.txt").replace("/", File.separator);
			File targetDescriptionFile = new File(targetDescriptionFilePath);
			Files.copy(sourceDescriptionFile.toPath(), targetDescriptionFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);

			//
			SMLGResult smlgResult = new SMLGResult();
			if (targetDir == true && isMxGraphSaved == true) {
				smlgResult = SMLGAdapter.generateGame(storyPath, xml);
			}

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(smlgResult);
			response.getWriter().append(json);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append(e.getMessage());
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
