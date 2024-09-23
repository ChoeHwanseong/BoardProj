<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/template/Config.jsp"/>
	<title>MyPage</title>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->
			<div class="p-3">
				<h4>My page</h4>
			</div>
			<fieldset class="border rounded-3 p-3">
				<legend class="float-none w-auto px-3">${token['sub']}님 마이페이지</legend>
				<form action="<c:url value="/Homepage/MyPageUpdate.chs"/>">
				<div class="row">
					<div class="col">
						<img src="${pageContext.request.contextPath}/profile/${empty user.profile?'default.png':user.profile}" style="width: 240px; height: 240px;" alt="프로필 사진" />
					</div>
					<div class="col">
				        <div class="my-2 ">
				            <label for="username" class="form-label w">아이디</label>
				            <input type="text" class="form-control" id="username" name="username" value="${user['username']}" disabled>
				        </div>
				        <div class="my-2 ">
				            <label for="redidate" class="form-label">가입일</label>
	            			<input type="text" class="form-control " id="redidate" name="redidate" value="${user['regiDate']}" disabled>
				        </div>
				        <div class="my-2 ">
				            <label for="name" class="form-label">이름</label>
				            <input type="text" class="form-control " id="name" placeholder="성함을 입력하세요." name="name" value="${user['name']}" disabled>
				        </div>
			        </div>		
		    	</div>
		    	<div class="my-2 ">
		            <label for="addr" class="form-label">주소</label>
		            <input type="text" class="form-control " id="addr" name="addr" value="${user['address']}" disabled>
		        </div>
		        <div class="my-2 ">
		            <label for="tel" class="form-label">전화번호</label>
		            <input type="tel" class="form-control" id="tel" name="tel" value="${user['telNumber']}" disabled>
		        </div>
		    	<div class="my-2 ">
		            <label class="form-label">성별</label>
  		            	<input type="text" class="form-control " id="gender" name="gender" value="${user['gender']}" disabled>
		        </div>
      			<div class="my-2 ">
		            <label for="inter" class="form-label">관심 사항</label>
	            	<input type="text" class="form-control " id="inter"  name="inter" value="${user['interest']}" disabled>
		        </div>
		        <label for="grade" class="form-label">학력사항</label>
       			<input type="text" class="form-control " id="grade" name="grade" value="${user['edu']}" disabled>
	            <label for="self">자기소개</label>
	            <textarea class="form-control" rows="5" id="self" name="self" disabled>${user['introduce']}</textarea>
		        <input type="hidden" name="update" value="update"/>
		        <div class="my-2 text-end">
		        	<a href="javascript:isDelete()" class="btn btn-danger float-start" >회원 탈퇴</a>
		            <button type="submit" class="btn btn-secondary">회원 정보 수정</button>
		            <a class="btn btn-secondary" href="<c:url value='/Homepage/Login.chs'><c:param name='log' value='out'/></c:url>">로그 아웃</a>
		        </div>
		    	</form>
			</fieldset>
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
	<script>
	function isDelete(){
		if(confirm("정말로 탈퇴하시겠습니까?")){
			location.replace("<c:url value="/Homepage/Resign.chs"/>");
		}				
	}
	
	</script>
</body>
</html>