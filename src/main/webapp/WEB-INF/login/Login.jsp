<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/template/Config.jsp"/>
	<title>Login</title>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<jsp:include page="/template/Header.jsp"/>
			<!-- 컨텐츠 시작 -->

			<fieldset class="border rounded-3 p-3">
				<legend class="float-none w-auto px-3">Login</legend>
				<form method="post" action="<c:url value="/Homepage/Login.chs"/>">
					<div class="row">
						<div class="col-4">
							<input type="text" class="form-control" placeholder="Enter username!"
								name="username" 
								value='${param.username}'>
						</div>
						<div class="col-4">
							<input type="password" class="form-control"
								placeholder="Enter password" name="password" value="${param.password}">
						</div>						
						<div class="col-auto">						 
							<button class="btn btn-secondary">로그인</button>
						</div>
						<div class="col-auto">						 
							<button class="btn btn-secondary">회원 가입</button>
						</div>
					</div>
				</form>
				<div class="d-flex d-none"  id="errorMsg">
				  	<div class="alert alert-danger alert-dismissible my-2 w-50" >
						  <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						  <strong>로그인 실패!</strong> ${errorMsg}
					</div>
				</div>
				<script>
					if('${errorMsg}'!==''){					
						const div=document.querySelector("#errorMsg");
						div.classList.add('d-block');
						div.classList.remove('d-none');					
					}
					var signUp = document.querySelector("form > div > div:nth-child(4) > button");
					signUp.onclick=function(e){
						e.preventDefault();
						location.replace('${pageContext.request.contextPath}/Homepage/SignUp.chs');
					};
				</script>
			</fieldset>
			<!-- 컨텐츠 끝 -->
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>