<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/template/Config.jsp"/>
<title>Write</title>
<style>
	span{
		font-size:0.8em;
		color:red;
	}
	</style>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->			
			<div class=" p-3">
				<h4>게시글 등록</h4>
			</div>
			<form action="<c:url value="/board/Write.chs"/>" method="post" enctype="multipart/form-data">
			  <div class="mb-3 mt-3">
			    <label for="title" class="form-label">제목</label>
			    <span>${titleError}</span>
			    <input type="text" class="form-control" id="title" placeholder="Enter title" name="title" value='${empty param.title?"":param.title}'>
			  </div>
			  <div class="my-3">
			  	<label for="files" class="form-label">파일 첨부</label><span> ${fileError}</span>
			  	<input type="file" class="form-control" id="files" name="files" multiple>
			  </div>
			<label for="content" class="form-label">내용</label>
			<span>${contentError}</span>
			<textarea class="form-control" rows="5" id="content" name="content" placeholder="Enter content">${param.content}</textarea>
			 
			  <button type="submit" class="btn btn-secondary mt-2">등록</button>
			</form>
			
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>