package uk.ac.york.cs.es.smlg;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * Servlet implementation class LogActivity
 */
@WebServlet("/LogActivity")
public class LogActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String filename = "SMLG";
		String message = request.getParameter("message");
		
		// set session
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("username") != null) {
			filename = session.getAttribute("username").toString();
		}

		// log path
		String logPath = getServletContext().getRealPath(".") + File.separator + "log" + File.separator + filename
				+ ".txt";
		System.setProperty("logFilename", logPath);

		// set logger
		Logger logger = LogManager.getLogger(this.getClass());
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		context.reconfigure();
		logger.info(message);
		
		response.getWriter().append(message);
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
