package com.agourlay.pomf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agourlay.pomf.dao.Dao;
import com.agourlay.pomf.tools.Utils;
import com.agourlay.pomf.tools.Validation;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class AddPostController extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("text/html");
				
		int captchaNumberInSession = 0;
		int captchaNumberSubmitted = 0;
		
		try {
			captchaNumberInSession = (Integer) req.getSession().getAttribute("captchaNumber");
			captchaNumberSubmitted = Integer.parseInt(req.getParameter("captcha"));
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);	
			return;
		}
		
		if (captchaNumberInSession == captchaNumberSubmitted){
			String content = Validation.checkNull(req.getParameter("content"));
			String author = Validation.checkNull(req.getParameter("author"));
			Double positionX = Double.parseDouble(req.getParameter("positionX"));
			Double positionY = Double.parseDouble(req.getParameter("positionY"));
			String color = Validation.checkNull(req.getParameter("color"));
			String dueDateString = req.getParameter("dueDate");
			Dao.INSTANCE.add(author, content,positionX,positionY,color,Utils.stringToDate(dueDateString, "MM/dd/yyyy"));	
		    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
			syncCache.delete("fridgeContent");
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		}else{
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);	
		}

	}

}