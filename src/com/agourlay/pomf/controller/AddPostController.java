package com.agourlay.pomf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agourlay.pomf.dao.Dao;
import com.agourlay.pomf.tools.Validation;

@SuppressWarnings("serial")
public class AddPostController extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.sendRedirect("/TheFridge.jsp");
		
		int captchaNumberInSession = 0;
		int captchaNumberSubmitted = 0;
		
		try {
			captchaNumberInSession = (Integer) req.getSession().getAttribute("captchaNumber");
			captchaNumberSubmitted = Integer.parseInt(req.getParameter("captcha"));
		} catch (Exception e) {
			return;
		}
		
		if (captchaNumberInSession == captchaNumberSubmitted){
			String content = Validation.checkNull(req.getParameter("content"));
			String author = Validation.checkNull(req.getParameter("author"));
			Dao.INSTANCE.add(author, content);
		}

	}

}