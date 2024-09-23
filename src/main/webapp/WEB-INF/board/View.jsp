<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/template/Config.jsp"/>
<script src="${pageContext.request.contextPath}/resources/ListNView.js"></script>
<link href="${pageContext.request.contextPath}/resources/ViewStyle.css" rel="stylesheet" type="text/css">
<title>View</title>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->			
			<div class=" p-3">
				<h4>게시글 상세</h4>
			</div>
			
			<table class="table text-center">
				<tbody>
					<tr>
						<th class="bg-dark w-25 text-white">번호</th>
						<td class="text-start">${dto.id}</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">작성자</th>
						<td class="text-start">${dto.username}</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">작성일</th>
						<td class="text-start">${dto.postDate}</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">조회수</th>
						<td class="text-start">${dto.hitcount}</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">좋아요</th>
						<td class="text-start" id="likeyCount">${dto.likey}</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">첨부파일</th>
						<td class="text-start">
							<c:if test="${dto.files!=null}" var="nofile">
								<c:forEach var="file" items="${fn:split(dto.files,',')}">
									<a href="<c:url value='/board/Download.chs?filename=${file}'/>" class="btn btn-secondary mt-1 ">${file} <i class="fa-solid fa-floppy-disk"></i></a>
								</c:forEach>
							</c:if>
							<c:if test="${!nofile}" >
								첨부파일 없음
							</c:if>
						</td>
					</tr>
					<tr>
						<th class="bg-dark w-25 text-white">제목</th>
						<td class="text-start">${dto.title}</td>
					</tr>
					<tr>
						<th class="bg-dark text-white" colspan="2">내용</th>
					</tr>
					<tr>
						<td colspan="2" class="text-start">${dto.content}</td>
					</tr>
					
				</tbody>
			</table>
			<!-- 수정/삭제/목록 컨트롤 버튼 -->
			<div class="clearfix">	
					<c:if test="${dto.username==user.username || user.username=='admin'}">
						<a href="<c:url value="/board/Update.chs">
									<c:param name="id" value="${dto.id}"></c:param>
								</c:url>" class="btn btn-secondary float-start  mx-3">수정</a>
								
						<a href="javascript:isDelete()" class="btn btn-secondary float-start">삭제</a>	
					</c:if>
				<button class="btn btn-secondary float-end" type="button" data-bs-toggle="offcanvas" data-bs-target="#demo">댓글창</button>
				
				<a href="<c:url value='/board/List.chs'>
							<c:param name="nowPage" value="${nowPage}"></c:param>
							</c:url>" class="btn btn-secondary float-end  mx-3">목록</a>
							
				<button type="button" class="btn btn-secondary float-end" onclick="toggleLike()">
					<c:url value="/board/Likey.chs" var="url"><c:param name="id" value="${dto.id}"></c:param></c:url>
				    <span id="likeIcon" class="fa fa-thumbs-up ${isLikey?'text-light':'text-dark'}"></span>
				</button>
			</div>
			<!-- 댓글 -->
			<div class="offcanvas offcanvas-bottom" id="demo">
			  <div class="offcanvas-header ">
			    <form method="post" class="w-100 " id="commentForm">
				  <div class="row ">
				    <div class="col ">
				      <input type="text" class="form-control w-100" placeholder="댓글을 입력하세요." id="comm" name="content">
				    </div>
				    <div class="col-2">
				       <button type="submit" class="btn btn-secondary float-start mx-3" id="commentSubmit">등록</button>
				    </div>
				  </div>
				   <input type="hidden" name="board_id" value="${dto.id}">
				   <input type="hidden" name="id" />
				</form>
			    
			  </div>
			  
			  <div class="offcanvas-body text-center">
					<table class="table table-hover text-center table-dark">
					  	<thead class="table-dark">
						 	<tr>
						   		<th class="col-auto">내용</th>
						   		<th class="col-2">작성자</th>
						   		<th class="col-2">작성일</th>
						   		<th class="col-1"></th>
						 	</tr>
						</thead>
						<tbody id="commentbody">
							<c:forEach items="${comments}" var="comment">
								<tr>
									<td style="display: none;">${comment.id}</td>
								 	<td class="text-start">${comment.content}</td>
						            <td class="text-center">${comment.username}</td>
						            <td class="text-center">${comment.postDate}</td>
						            <td class="text-center">
						           		<c:if test="${comment.username==user.username || user.username=='admin'}">
						            		<a class="btn btn-secondary" href="<c:url value='/comments/Delete.chs'>
						      											<c:param name='commentId' value='${comment.id}'/>
						      											<c:param name='bId' value='${dto.id}'/>
					      											</c:url>">삭제</a>
					    		        </c:if>
						            </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			  </div>
			  
			</div>
			
			<div class="container-fluid my-3">
			  
			  
			</div>
			<!-- 이전글/다음글 -->
			<div>
				<table class="table">
					<tbody>
						<tr>
							<td class="bg-dark text-white w-25 text-center">다음 글</td>
							<td>
								<c:if test="${prevnext.NEXT==null}" var="noNext">
									다음글이 없습니다.							
								</c:if>
								<c:if test="${!noNext}" >
									<a href="<c:url value='/board/View.chs?id='/>${prevnext.NEXT.id}">${prevnext.NEXT.title}
	      								<c:if test="${prevnext.NEXT.files!=null}">
		      								<i class="fa-solid fa-floppy-disk"></i>
	      								</c:if>
	      								<c:if test="${prevnext.NEXT.commentscount!=0}">
	      									<span>&nbsp <i class="fa-regular fa-comment-dots"></i> <small>(${prevnext.NEXT.commentscount})</small></span>
	      								</c:if>
      								</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="bg-dark text-white w-25 text-center">이전 글</td>
							<td>
								<c:if test="${prevnext['PREV']==null}" var="noPrev">
									이전글이 없습니다.							
								</c:if>
								<c:if test="${!noPrev}" >
									<a href="<c:url value='/board/View.chs?id='/>${prevnext.PREV.id}">${prevnext.PREV.title}
	      								<c:if test="${prevnext.PREV.files!=null}">
		      								<i class="fa-solid fa-floppy-disk"></i>
	      								</c:if>
	      								<c:if test="${prevnext.PREV.commentscount!=0}">
	      									<span>&nbsp <i class="fa-regular fa-comment-dots"></i> <small>(${prevnext.PREV.commentscount})</small></span>
	      								</c:if>
      								</a>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>				
			</div>
			<script>
			function isDelete(){
				if(confirm("정말로 삭제하시겠습니까?")){
					
					location.replace("<c:url value="/board/Delete.chs">
							<c:param name="id" value="${dto.id}"></c:param>
							<c:param name="nowPage" value="${nowPage}"></c:param>
						</c:url>");
				}				
			}
			
			//좋아요
			function toggleLike() {
			    fetch(`${url}`, {
			        method: 'POST'
			    })
			    .then(response => response.json())
			    .then(data => {
			        const likeIcon = document.getElementById('likeIcon');
			        const likeyCount = document.getElementById('likeyCount');
			        if (data.isLike) {
			        	likeIcon.classList.remove('text-dark');
			            likeIcon.classList.add('text-light'); // 좋아요 상태일 때 아이콘 변경
			        } else {
			        	likeIcon.classList.remove('text-light');
			            likeIcon.classList.add('text-dark'); // 좋아요 취소 상태일 때 아이콘 변경
			        }
			        likeyCount.textContent = data.likeyCount; // 좋아요 수 업데이트
			        console.log(data.likeyCount);
			    })
			}
			
			const form = document.querySelector("#commentForm");
			
			const writeurl = "${pageContext.request.contextPath}/comments/write";
			const updateurl = "${pageContext.request.contextPath}/comments/update";
			const deleteurl = `${pageContext.request.contextPath}/comments/Delete.chs?bId=${dto.id}&commentId=`;
			const commentButton = document.querySelector("#commentSubmit");
			const commentWritebox = document.querySelector('#comm');
			
			form.onsubmit=(e)=>{
				
				e.preventDefault();
				console.log(e.target.children[0].children[1].textContent.includes('수정'));
				const formData = new FormData(form);
				const json = Object.fromEntries(formData);
				
				if(commentWritebox.value != '' && e.target.children[0].children[1].textContent.includes('등록')){
					fetch(writeurl,{method:"post",body:JSON.stringify(json),headers:{'Content-Type':'application/json'}})
					.then(resp=>resp.json())
					.then(data=>{
						const tbody = document.querySelector("#commentbody");
				        const newRow = document.createElement("tr");
				        newRow.innerHTML = '<td style="display: none;">'+data.board_id+'</td><td class="text-start">'+data.content+'</td><td class="text-center">'+data.username+'</td><td class="text-center">방금</td><td><a class="btn btn-secondary" href='+deleteurl+data.id+'>삭제</a></td>';
				        tbody.prepend(newRow);
						})
				}
				
				if(commentWritebox.value != '' && e.target.children[0].children[1].textContent.includes('수정')){
					fetch(updateurl,{method:"post",body:JSON.stringify(json),headers:{'Content-Type':'application/json'}})
					.then(resp=>resp.json())
					.then(data=>{
				        commentWritebox.value='';
				        commentButton.textContent='등록';
				        const rows = document.querySelectorAll('#commentbody tr');
				        rows.forEach(row => {
				        	if (row.children[0].textContent == data.id) {
				        		row.children[1].textContent = data.content;
				        	}
				        });
					})
				}
			};
			
			
			document.querySelector('#commentbody').onclick=(e)=>{
				if(e.target.nodeName !=='BUTTON'){
					const comments_id = e.target.parentElement.children[0].textContent;
					const titleText = e.target.parentElement.children[1].textContent;
					const isauthor = '${user.username}'==e.target.parentElement.children[2].textContent;

					if(isauthor){//로그인 사용자가 작성한 댓글인 경우
						//댓글 입력상자의 값을 클릭한 행의 제목으로 변경
						commentWritebox.value=titleText;
						//버튼 텍스트를 수정으로 변경
						commentButton.textContent='수정';
						//히든타입인 NAME="comments_id"의 value를 댓글 키(cid)로 설정
						document.querySelector('input[name="id"]').value=comments_id;
						
					}
				}
			}
			</script>
			
			
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>