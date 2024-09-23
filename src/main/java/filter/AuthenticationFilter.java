package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.JWT;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/board/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(jakarta.servlet.ServletRequest req, jakarta.servlet.ServletResponse resp,
			jakarta.servlet.FilterChain chain) throws IOException, jakarta.servlet.ServletException {
		
		HttpServletRequest request =(HttpServletRequest)req;
		if(!JWT.verifyToken(JWT.getToken(request, request.getServletContext().getInitParameter("TOKEN-NAME")))) {
			resp.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = resp.getWriter();
			out.println("<script>");
	            out.println("alert('로그인 후 이용해주세요.');");
	            out.println("window.location.href='" + request.getContextPath() + "/Homepage/Login.chs';");
	        out.println("</script>");
	        out.close();
		}
		//pass the request along the filter chain
		chain.doFilter(req, resp);
	}

}
