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
 * Servlet implementation class SMLGHttpServlet
 */
@WebServlet("/SMLGHttpServlet")
public class SMLGHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected HttpSession session = null;
	protected Logger logger = null;

	public SMLGHttpServlet() {
		super();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//set session
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("Login");
			return;
		}
		String username = session.getAttribute("username").toString();
		request.setAttribute("username", username);

		//log path
		String logPath = getServletContext().getRealPath(".") + File.separator + "log" + File.separator
				+ session.getAttribute("username") + ".txt";
		System.setProperty("logFilename", logPath);

		//set logger
		logger = LogManager.getLogger(this.getClass());
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		context.reconfigure();
		logger.info(this.getClass().getSimpleName());
		
		super.service(request, response);
	}
}
