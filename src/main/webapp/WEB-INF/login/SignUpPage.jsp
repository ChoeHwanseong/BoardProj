<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/template/Config.jsp"/>
	<title>Sign Up</title>
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
			<div class="p-3 mx-auto">
				<h4>회원 가입</h4>
			</div>
			<fieldset class="border rounded-3 p-3 mx-auto">
			<legend class="float-none w-auto px-3">*은 필수 항목입니다.</legend>
			 <form method="post" action="<c:url value="/Homepage/SignUp.chs"/>" >
		        <div class="my-2 ">
		            <label for="username" class="form-label w">*아이디</label>
		            <input type="text" class="form-control" id="username" placeholder="아이디를 입력하세요." name="username" value='${empty param.username?"":param.username}'>
		            <span>${usernameError}</span>
		        </div>
		        <div class="my-2 ">
		            <label for="password" class="form-label">*비밀번호</label>
		            <input type="password" class="form-control " id="password" placeholder="비밀번호를 입력하세요." name="password" value='${empty param.password?"":param.password}'>
		            <span>${passwordError}</span>
		        </div>
		        <div class="my-2 ">
		            <label for="passwordConfirm" class="form-label">*비밀번호 확인</label>
		            <input type="password" class="form-control " id="passwordConfirm" placeholder="비밀번호를 입력하세요." name="passwordConfirm" value='${empty param.passwordConfirm?"":param.passwordConfirm}'>
		            <span>${passwordConfirmError}</span>
		        </div>
		        <div class="my-2 ">
		            <label for="name" class="form-label">*이름</label>
		            <input type="text" class="form-control " id="name" placeholder="성함을 입력하세요." name="name" value='${empty param.name?"":param.name}'>
		            <span>${nameError}</span>
		        </div>
		        <fieldset>
				  <legend style="font-size:1em">주소</legend>
				  <input type="text" id="sample4_postcode" placeholder="우편번호" name="addrnum" value='${empty param.addrnum?"":param.addrnum}'>
				  <input type="button" onclick="postcode()" value="우편번호 찾기"><br/>
				  <input type="text" id="sample4_roadAddress" name="roadaddr" placeholder="도로명주소" class="w-50" value='${empty param.roadaddr?"":param.roadaddr}'><br/>
				  <input type="text" id="sample4_jibunAddress" placeholder="지번주소" hidden>
				  <span id="guide" style="color:#999;display:none"></span>
				  <input type="text" id="sample4_detailAddress" name="detailaddr" placeholder="상세주소" class="w-50" value='${empty param.detailaddr?"":param.detailaddr}'>
				  <input type="text" id="sample4_extraAddress" placeholder="참고항목" hidden>
				</fieldset>
      
		        <div class="my-2 ">
		            <label for="tel" class="form-label">전화번호</label>
		            <input type="tel" class="form-control" id="tel" name="tel" placeholder="000-0000-0000" pattern="[0-9]{0,3}-[0-9]{0,4}-[0-9]{0,4}" name="tel" value='${empty param.tel?"":param.tel}'>
		        </div>
		        <div class="my-2 ">
		            <label class="form-label">*성별</label>
		            <div class="d-flex ">
		                <input type="radio" class="btn-check" name="gender" id="radio1" value="남자"  ${param.gender == '남자' ? 'checked' : ''}>
		                <label class="btn btn-light " for="radio1">남성</label>
		                <input type="radio" class="btn-check" name="gender" id="radio2" value="여자"  ${param.gender == '여자' ? 'checked' : ''}>
		                <label class="btn btn-light mx-3" for="radio2">여성</label>
		                <input type="radio" class="btn-check" name="gender" id="radio3" value="기타"  ${param.gender == '기타' ? 'checked' : ''}>
		                <label class="btn btn-light " for="radio3">기타</label>
		            </div>
		            <span>${genderError}</span>
		        </div>
		        <div class="my-2 ">
		            <label class="form-label">*관심 사항</label>
		            <div class="d-flex ">
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check1" name="inter" value="정치" ${fn:contains(inter, '정치') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check1">정치</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check2" name="inter" value="경제" ${fn:contains(inter, '경제') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check2">경제</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check3" name="inter" value="스포츠"  ${fn:contains(inter, '스포츠') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check3">스포츠</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check4" name="inter" value="연예" ${fn:contains(inter, '연예') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check4">연예</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check5" name="inter" value="IT/과학" ${fn:contains(inter, 'IT/과학') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check5">IT/과학</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check6" name="inter" value="사회" ${fn:contains(inter, '사회') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check6">사회</label>
		                </div>
		            </div>
		            <span>${interError}</span>
		        </div>
		        <label for="grade" class="form-label">*학력사항</label>
		        <select class="form-select"  id="grade" name="grade">
		            <option value="">학력을 선택하세요</option>
		            <option value="초등학교" ${param.grade == '초등학교' ? 'selected' : ''}>초등학교</option>
		            <option value="중학교" ${param.grade == '중학교' ? 'selected' : ''}>중학교</option>
		            <option value="고등학교" ${param.grade == '고등학교' ? 'selected' : ''}>고등학교</option>
		            <option value="대학교" ${param.grade == '대학교' ? 'selected' : ''}>대학교</option>
		        </select>
		        <span>${gradeError}</span>
		        <div class="my-2">
		            <label for="self">*자기소개</label>
		            <textarea class="form-control" rows="5" id="self" name="self">${param.self}</textarea>
		            <span>${selfError}</span>
		        </div>
		        
		        <div class="d-grid gap-2 my-2">
		            <button type="submit" class="btn btn-light">가입</button>
		        </div>
		    </form>
			</fieldset>
			<!-- 컨텐츠 끝 -->
			<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script>
			  function postcode(){
			      var themeObj = {
			          bgColor: "#162525", //바탕 배경색
			          searchBgColor: "#162525", //검색창 배경색
			          contentBgColor: "#162525", //본문 배경색(검색결과,결과없음,첫화면,검색서제스트)
			          pageBgColor: "#162525", //페이지 배경색
			          textColor: "#FFFFFF", //기본 글자색
			          queryTextColor: "#FFFFFF", //검색창 글자색
			          //postcodeTextColor: "", //우편번호 글자색
			          //emphTextColor: "", //강조 글자색
			          outlineColor: "#444444" //테두리
			          };
			      new daum.Postcode({
			          theme: themeObj,
			          oncomplete: function(data) {
			              //console.log(data.address);
			              // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
			              // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
			              // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			              var roadAddr = data.roadAddress; // 도로명 주소 변수
			              var extraRoadAddr = ''; // 참고 항목 변수
			              // 법정동명이 있을 경우 추가한다. (법정리는 제외)
			              // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
			              if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
			                  extraRoadAddr += data.bname;
			              }
			              // 건물명이 있고, 공동주택일 경우 추가한다.
			              if(data.buildingName !== '' && data.apartment === 'Y'){
			              extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
			              }
			              // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
			              if(extraRoadAddr !== ''){
			                  extraRoadAddr = ' (' + extraRoadAddr + ')';
			              }
			              // 우편번호와 주소 정보를 해당 필드에 넣는다.
			              document.getElementById('sample4_postcode').value = data.zonecode;
			              document.getElementById("sample4_roadAddress").value = roadAddr;
			              document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
			              // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
			              if(roadAddr !== ''){
			                  document.getElementById("sample4_extraAddress").value = extraRoadAddr;
			              } else {
			                  document.getElementById("sample4_extraAddress").value = '';
			              }
			              var guideTextBox = document.getElementById("guide");
			              // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
			              if(data.autoRoadAddress) {
			                  var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
			                  guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
			                  guideTextBox.style.display = 'block';
			              } else if(data.autoJibunAddress) {
			                  var expJibunAddr = data.autoJibunAddress;
			                  guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
			                  guideTextBox.style.display = 'block';
			              } else {
			                  guideTextBox.innerHTML = '';
			                  guideTextBox.style.display = 'none';
			              }
			          }
			      }).open();
			  }
			</script>
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>