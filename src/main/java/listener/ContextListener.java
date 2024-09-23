package listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Context ct = new InitialContext();
			DataSource src = (DataSource)ct.lookup(sce.getServletContext().getInitParameter("JNDI-ROOT")+"/proj");
			sce.getServletContext().setAttribute("DATA-SOURCE", src);
		} catch (NamingException e) {e.printStackTrace();}
	}

}
