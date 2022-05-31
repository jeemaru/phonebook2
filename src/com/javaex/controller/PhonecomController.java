package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonecomController extends HttpServlet {

	//필드
	private static final long serialVersionUID = 1L;

	//필드
	
    //생성자 (기본생성자 사용)
	
	//메소드 gs
	
	//메소드 일반
	//get방식으로 요청시 호출되는 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일때 한글꺼짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//action파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {//리스트일떄
			//데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getPersonList();
			System.out.println("personList");
			
			request.setAttribute("pList", personList);
			
			WebUtil webUtil = new WebUtil();
			webUtil.forward(request, response, "/list.jsp");
			
			//데이터 + html --> jsp 시킨다
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			
		}else if("writeForm".equals(action)) {//등록폼일떄
			//포워드
			RequestDispatcher rd = request.getRequestDispatcher(action);
			rd.forward(request, response);
			
		}else if("write".equals(action)) {//등록일때
			//파라미터에서 값 꺼내기(name, hp, company)
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");					
			
			//vo만들어서 값 초기화
			PersonVo personVo = new PersonVo(name, hp, company);
			
			//phoneDao.personInsert()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			phoneDao.personInsert(personVo);
			
			//리다이렉트 list
			response.sendRedirect("/Phonebook2/pbc?action=list");
			
		}else if("delete".equals(action)) {//삭제일떄
			
			//파라미터에서 id값을 꺼낸다
			int id = Integer.parseInt(request.getParameter("id"));
			
			//phoneDao.personDelete();통해서 삭제하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personDelete(id);

			//리다이렉트 list
			response.sendRedirect("./pbc?action=list");
			
		}else if("update".equals(action)) {
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");					
			int personid = Integer.parseInt(request.getParameter("personId"));
			
			//vo만들어서 값 초기화
			PersonVo personVo = new PersonVo(personid, name, hp, company);
			
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personUpdate(personVo);
			
			//리다이렉트 리스트
			response.sendRedirect("/phonebook2/pbc?action=list");
			
		} else if("updateForm".equals(action)) {
			
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> phoneList = phoneDao.getPersonList();
			request.setAttribute("pList", phoneList);
			
			RequestDispatcher rd = request.getRequestDispatcher("/updateForm.jsp");
			rd.forward(request, response);
			
		} else{
			System.out.println("action 파라미터 없음");
		}
		
		
	}

	//post방식으로 요청시 호출되는 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
