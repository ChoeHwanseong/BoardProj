package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Homepage/Home.chs")
public class HomeController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, String>> list = new Vector<>();
		Document doc = Jsoup.connect("https://news.naver.com/main/officeList.naver").get();
		Elements newses = doc.select("#_rankingList0 > li");
		for(Element news : newses){
			String newsURL = news.firstElementChild().absUrl("href");
			String headLine = news.child(1).child(0).child(0).text();
			String newsimg = news.child(0).child(0).attr("src");
			Map<String, String> map = new HashMap<>();
			
			map.put("newsURL", newsURL);
			map.put("headLine", headLine);
			map.put("newsimg", newsimg);
			list.add(map);
			
		}
		req.setAttribute("newses", list);
		req.getRequestDispatcher("/publicpage/Home.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
