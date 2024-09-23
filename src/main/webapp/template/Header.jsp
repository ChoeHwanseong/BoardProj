<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="chs" uri="/WEB-INF/tlds/taglib.tld"%>

<style>
    .nav-link {
        display: flex;
        align-items: center;
    }
    .nav-link img {
        margin-right: 5px;
    }
</style>

<!-- 상단 네비게이션 바 -->
<nav class="navbar navbar-expand-sm navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value='/Homepage/Home.chs'/>">
            <i class="fa-solid fa-house-chimney"></i> ChoeHwanseong
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
            <ul class="navbar-nav">
              <c:set var="user_" value="${chs:getUser(pageContext.request)}"/>
              <c:if test="${cookie[pageContext.servletContext.getInitParameter('TOKEN-NAME')].value == null}" var="valid">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/Homepage/Login.chs'/>">
                        <i class="fas fa-sign-in-alt"></i> 로그인</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/Homepage/SignUp.chs'/>">
                        <i class="fa-solid fa-pen-to-square"></i> 회원가입</a>
                    </li>
                </c:if>
                <c:if test="${!valid}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/Homepage/MyPage.chs'/>">
                            <img src="${pageContext.request.contextPath}/profile/${empty user_.profile ? 'default.png' : user_.profile}" 
                                 style="width: 20px; height: 20px; border-radius: 50%;" alt="프로필 사진" />마이페이지
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/Homepage/Login.chs'>
                            <c:param name='log' value='out'/>
                        </c:url>">
                            <i class="fas fa-sign-out-alt"></i> 로그아웃
                        </a>
                    </li>
                </c:if>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/board/List.chs'/>">
                    <i class="fa-solid fa-table-list"></i> 게시판</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
