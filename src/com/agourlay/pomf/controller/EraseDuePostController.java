package com.agourlay.pomf.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agourlay.pomf.dao.Dao;
import com.agourlay.pomf.model.Post;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class EraseDuePostController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		List<Post> posts = Dao.INSTANCE.getPosts();		
		for (Post post : posts) {
			if (post.getDueDate() != null && post.getDueDate().before(new Date())){
				Dao.INSTANCE.remove(post.getId());
				MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
				syncCache.delete("fridgeContent");
			}
		}
		
		resp.setContentType("text/html");
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);	
	}
}
