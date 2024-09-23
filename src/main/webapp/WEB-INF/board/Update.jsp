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
				<h4>게시글 수정</h4>
			</div>
			<form action="<c:url value="/board/Update.chs">
							<c:param name="nowPage" value="${nowPage}"></c:param>
						</c:url>" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${dto.id}">
				<input type="hidden" name="originalFileName" value="${dto.files}">
			  <div class="mb-3 mt-3">
			    <label for="title" class="form-label">제목</label>
			    <span>${titleError}</span>
			    <input type="text" class="form-control" id="title" placeholder="Enter title" name="title" value='${dto.title}'>
			  </div>
			  <div class="my-3">
			  	<label for="files" class="form-label">파일 첨부</label>
			  	<input type="file" class="form-control" id="files" name="files" multiple>
					<c:if test="${dto.files!=null}" var="nofile">
						${dto.files} <i class="fa-solid fa-floppy-disk"></i>
					</c:if>
					<c:if test="${!nofile}" >
						첨부파일 없음
					</c:if>
			  </div>
			<label for="content" class="form-label">내용</label>
			<span>${contentError}</span>
			<textarea class="form-control" rows="5" id="content" name="content" placeholder="Enter content">${dto.content}</textarea>
			 
			<button type="submit" class="btn btn-secondary mt-2">수정</button>
			</form>
			
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>