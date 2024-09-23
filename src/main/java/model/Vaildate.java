package model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.member.MemberDto;

public class Vaildate {
	public static boolean signupValidate(HttpServletRequest req, HttpServletResponse resp) {
		int validateCount=0;
		if(req.getParameter("username")==""){
			req.setAttribute("usernameError", "아이디는 필수 입력항목입니다.");
			validateCount++;
		}
		if(req.getParameter("password")==""){
			req.setAttribute("passwordError", "비밀번호는 필수 입력항목입니다.");
			validateCount++;
		}
		if(req.getParameter("passwordConfirm")!=null && !req.getParameter("passwordConfirm").equals(req.getParameter("password"))){
			req.setAttribute("passwordConfirmError", "비밀번호와 일치하지 않습니다.");
			validateCount++;
		}
		if(req.getParameter("name")==""){
			req.setAttribute("nameError", "이름은 필수 입력항목입니다.");
			validateCount++;
		}
		if(req.getParameter("gender")==null){
			req.setAttribute("genderError", "성별을 선택해주세요.");
			validateCount++;
		}
		if(req.getParameterValues("inter")==null || req.getParameterValues("inter").length < 2){
			req.setAttribute("interError", "관심 사항은 2개 이상 선택해주세요.");
			validateCount++;
		}
		if(req.getParameter("grade")==""){
			req.setAttribute("gradeError", "학력을 선택해주세요.");
			validateCount++;
		}
		if(req.getParameter("self")==""){
			req.setAttribute("selfError", "자기 소개를 입력해주세요.");
			validateCount++;
		}
		if(validateCount>0) return false;
		return true;
	}
	public static boolean boardValidate(HttpServletRequest req, HttpServletResponse resp) {
		int validateCount=0;
		try {
			Collection<Part> parts = req.getParts();
			long maxSize = 0;
			for(Part part:parts) {
				maxSize +=part.getSize();
				if(part.getSize()>1024*500){
					req.setAttribute("fileError", "업로드 최대 파일 용량(파일 하나:500KB, 전체:2.5MB)를 초과했어요.");
		            validateCount++;
				}
			}
			if(maxSize>1024*500*5) {
				req.setAttribute("fileError", "업로드 최대 파일 용량(파일 하나:500KB, 전체:2.5MB)를 초과했어요.");
	            validateCount++;
			}
		} catch (IOException | ServletException |IllegalStateException e) {
			req.setAttribute("fileError", "업로드 최대 파일 용량(파일 하나:500KB, 전체:2.5MB)를 초과했어요.");
            validateCount++;
		}
		if (req.getParameter("title") == null || req.getParameter("title").isEmpty()) {
            req.setAttribute("titleError", "제목은 필수 입력항목입니다.");
            validateCount++;
        }
        if (req.getParameter("content") == null || req.getParameter("content").isEmpty()) {
            req.setAttribute("contentError", "내용은 필수 입력항목입니다.");
            validateCount++;
        }
        if (req.getParameter("content") == null || req.getParameter("content").isEmpty()) {
            req.setAttribute("contentError", "내용은 필수 입력항목입니다.");
            validateCount++;
        }
        
        if(validateCount>0) return false;
		return true;
	}
}
