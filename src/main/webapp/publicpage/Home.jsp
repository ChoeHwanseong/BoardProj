<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/template/Config.jsp"/>
	<title>Home</title>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->
			<div class="p-3">
				<h4>Main Home</h4>
			</div>
			<fieldset class="border rounded-3 p-3">
				<h3 class="text-danger">${ErrorMsg}</h3>
			<div class="list-group">
			<h4>이 시각 주요 뉴스</h4>
			<c:forEach var="news" items="${newses}">
			  <a href="${news.newsURL}" class="list-group-item list-group-item-action"><img src="${news.newsimg}"/>&nbsp ${news.headLine}</a>
		 	</c:forEach>	 
			</div>
			</fieldset>
			
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>