<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<legend class="float-none w-auto px-3">${token['sub']}님 회원정보 수정</legend>
				<form method="post" action="<c:url value="/Homepage/MyPageUpdate.chs"/>" enctype="multipart/form-data">
				<div class="row">
					<div class="col">
						<img id="profileImage" src="${pageContext.request.contextPath}/profile/${empty user.profile?'default.png':user.profile}" style="width: 240px; height: 240px;" alt="프로필 사진" />
						<div>
							<input type="file" name="profile" accept="image/*" onchange="previewImage(event)"/>
						</div>
					</div>
					<div class="col">
				        <div class="my-2 ">
				            <label for="username" class="form-label w">아이디</label>
				            <input type="text" class="form-control" id="username" name="username" value="${user['username']}" readonly>
				        </div>
				        <div class="my-2 ">
				            <label for="redidate" class="form-label">가입일</label>
	            			<input type="text" class="form-control " id="redidate" name="redidate" value="${user['regiDate']}" readonly>
				        </div>
				        <div class="my-2 ">
				            <label for="name" class="form-label">이름</label>
				            <input type="text" class="form-control " id="name" placeholder="성함을 입력하세요." name="name" value="${user['name']}" >
				        </div>
			        </div>		
		    	</div>
		    	<div class="my-2 ">
		            <label for="password" class="form-label">비밀번호</label>
		            <input type="password" class="form-control " id="password" placeholder="비밀번호를 입력하세요." name="password" value="${user['password']}">
		        </div>
		    	<div class="my-2 ">
			    	 <fieldset>
					  <legend style="font-size:1em">주소</legend>
					  <input type="text" id="sample4_postcode" placeholder="우편번호" name="addrnum" value='${fn:split(user.address, "_")[0]}'>
					  <input type="button" onclick="postcode()" value="주소변경"><br/>
					  <input type="text" id="sample4_roadAddress" name="roadaddr" placeholder="도로명주소" class="w-50" value='${fn:split(user.address, "_")[1]}'><br/>
					  <input type="text" id="sample4_jibunAddress" placeholder="지번주소" hidden>
					  <span id="guide" style="color:#999;display:none"></span>
					  <input type="text" id="sample4_detailAddress" name="detailaddr" placeholder="상세주소" class="w-50" value='${fn:split(user.address, "_")[2]}'>
					  <input type="text" id="sample4_extraAddress" placeholder="참고항목" hidden>
					</fieldset>
				</div>
		        <div class="my-2 ">
		            <label for="tel" class="form-label">전화번호</label>
		            <input type="tel" class="form-control" id="tel" name="tel" placeholder="000-0000-0000" pattern="[0-9]{0,3}-[0-9]{0,4}-[0-9]{0,4}" name="tel" value="${user['telNumber']}" >
		        </div>
		    	<div class="my-2 ">
		            <label class="form-label">성별</label>
		            <div class="d-flex ">
		                <input type="radio" class="btn-check" name="gender" id="radio1" value="남자"  ${user['gender'] == '남자' ? 'checked' : ''}>
		                <label class="btn btn-light " for="radio1">남성</label>
		                <input type="radio" class="btn-check" name="gender" id="radio2" value="여자"  ${user['gender'] == '여자' ? 'checked' : ''}>
		                <label class="btn btn-light mx-3" for="radio2">여성</label>
		                <input type="radio" class="btn-check" name="gender" id="radio3" value="기타"  ${user['gender'] == '기타' ? 'checked' : ''}>
		                <label class="btn btn-light " for="radio3">기타</label>
		            </div>
		            <span>${genderError}</span>
		        </div>
      			<div class="my-2 ">
		            <label class="form-label">*관심 사항</label>
		            <div class="d-flex ">
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check1" name="inter" value="정치" ${fn:contains(user['interest'], '정치') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check1">정치</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check2" name="inter" value="경제" ${fn:contains(user['interest'], '경제') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check2">경제</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check3" name="inter" value="스포츠"  ${fn:contains(user['interest'], '스포츠') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check3">스포츠</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check4" name="inter" value="연예" ${fn:contains(user['interest'], '연예') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check4">연예</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check5" name="inter" value="IT/과학" ${fn:contains(user['interest'], 'IT/과학') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check5">IT/과학</label>
		                </div>
		                <div class="form-check ">
		                    <input type="checkbox" class="btn-check" id="check6" name="inter" value="사회" ${fn:contains(user['interest'], '사회') ? 'checked' : ''}>
		                    <label class="btn btn-light" for="check6">사회</label>
		                </div>
		            </div>
	            </div>
		            <label for="grade" class="form-label">*학력사항</label>
			        <select class="form-select"  id="grade" name="grade">
			            <option value="초등학교" ${user['edu'] == '초등학교' ? 'selected' : ''}>초등학교</option>
			            <option value="중학교" ${user['edu'] == '중학교' ? 'selected' : ''}>중학교</option>
			            <option value="고등학교" ${user['edu'] == '고등학교' ? 'selected' : ''}>고등학교</option>
			            <option value="대학교" ${user['edu'] == '대학교' ? 'selected' : ''}>대학교</option>
			        </select>

		            <label for="self">자기소개</label>
		            <textarea class="form-control" rows="5" id="self" name="self" >${user['introduce']}</textarea>
			        <div class="my-2 text-end">
			            <button type="submit" class="btn btn-secondary">변경 사항 저장</button>
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

				function previewImage(event) {
					var input = event.target;
					var reader = new FileReader();
					reader.onload = function(){
						var output = document.getElementById('profileImage');
						output.src = reader.result;
					}
					if (input.files && input.files[0]) {
						reader.readAsDataURL(input.files[0]);
					} else {
						// 파일이 선택되지 않으면 기본 이미지로 변경
						var output = document.getElementById('profileImage');
						output.src = '${pageContext.request.contextPath}/profile/default.png';
					}
				}

			</script>
			<jsp:include page="/template/Footer.jsp"/>
		</div>
		<!-- container-fluid -->
	</div>
	<!-- container -->
</body>
</html>