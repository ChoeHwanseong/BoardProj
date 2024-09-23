<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/template/Config.jsp"/>
	<title>List</title>
	<link href="${pageContext.request.contextPath}/resources/ListStyle.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/resources/ListNView.js"></script>
    
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->
			<div class="p-3">
				<h4>게시판</h4>
			</div>

			<form class="row mb-3">
				<div class="col">
					<select class="form-select  w-25" id="pageSize">
						<option value="3" ${sessionScope.sessionPageSize=='3'?'selected':''}>3개씩 보기</option>
						<option value="5" ${sessionScope.sessionPageSize=='5'|| empty sessionScope.sessionPageSize ?'selected':''}>5개씩 보기</option>
						<option value="10"${sessionScope.sessionPageSize=='10'?'selected':''}>10개씩 보기</option>
					</select>
				</div>
				<div class="col-auto text-end">
					<a href="<c:url value="/board/Write.chs"/>" class="btn btn-secondary">게시글 등록</a>
				</div>
			</form>
			<table class="table table-hover text-center table-dark">
				<thead class="table-dark">
			    	<tr>
			      		<th class="col-1">번호</th>
			      		<th class="col-auto">제목</th>
			      		<th class="col-2">작성자</th>
			      		<th class="col-1">조회수</th>
			      		<th class="col-1">좋아요</th>
			      		<th class="col-2">작성일</th>
			    	</tr>
			  	</thead>
			  	<tbody>
					<c:if test="${records == null}" var="isRecords">
				  		<tr>
				  			<td colspan="6">등록된 글이 없습니다.${records}</td>
				  			
				  		</tr>
	  		 		</c:if>
	  		 		<c:if test="${!isRecords}">
	  		 			<c:forEach items="${records}" var="record">
					    	<tr>
					      		<td>${record.id}</td>
					      		<td class="text-start"><a href="<c:url value='/board/View.chs'>
					      											<c:param name='id' value='${record.id}'/>
				      											</c:url>">${record.title}
					      								<c:if test="${record.files!=null}">
					      									<i class="fa-solid fa-floppy-disk"></i>
					      								</c:if>
					      								<c:if test="${record.commentscount!=0}">
					      									<span>&nbsp <i class="fa-regular fa-comment-dots"></i> <small>(${record.commentscount})</small></span>
					      								</c:if>
					      								</a></td>			      		
					      		<td>${record.username}</td>
					      		<td>${record.hitcount}</td>
					      		<td>${record.likey}</td>
					      		<td>${record.postDate}</td>
					    	</tr>
				    	</c:forEach>
			  		</c:if>	  	
			  </tbody>
			</table>
			<!-- 페이징 출력 -->
			${paging}
			<!-- 검색 UI -->
			<form action="<c:url value='/board/List.chs'/>" class="row justify-content-center">			
			    <div class="col-2" >
					<select class="form-control" name="searchColumn">
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="username">작성자</option>
					</select>
				</div>
				<div class="col-5" >
					<input type="text" class="form-control mx-2"
				placeholder="검색어를 입력하세요" name="searchWord" />
				</div>
				<div class="col-auto" >
					<button type="submit" class="btn btn-secondary">검색</button>
				</div>
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
	<script>
		const currentUrl = window.location.href; // 현재 URL을 가져옴
		const url = new URL(currentUrl);
		const queryString = '&'+url.search.substring(url.search.indexOf('?')+1);		
		const pageSizeNode = document.querySelector("#pageSize");
		pageSizeNode.onchange=()=>{
			
			const selectPageSize = pageSizeNode.value;
			location.replace(`${pageContext.request.contextPath}/board/List.chs?selectPageSize=`+selectPageSize+queryString);
		};
	
	</script>
</body>
</html>