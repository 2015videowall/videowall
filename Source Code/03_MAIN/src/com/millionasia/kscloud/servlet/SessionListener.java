package com.millionasia.kscloud.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener  implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent source) {
		// TODO Auto-generated method stub
		HttpSession session = source.getSession();
		int sessionTimeout = 60*Integer.valueOf(Configurations.getParamValue("SESSION_TIMEOUT_IN_MINUTES"));
		session.setMaxInactiveInterval(sessionTimeout);
		
	
		
		//System.out.println("sessionCreated call log start======================");
	  	/*
		System.out.println("session ID: " + session.getId());
	  	System.out.println("session MaxInactibeinterval: " + String.valueOf(session.getMaxInactiveInterval()));
	  	System.out.println("session created time: " + Utilities.getTime(session.getCreationTime()));
	  	System.out.println("session LastAccessedTime: " + Utilities.getTime(session.getLastAccessedTime()));
	  	System.out.println("session created:(myID)" + session.getAttribute("myID"));
	  	System.out.println("session created:(stID)" + String.valueOf(session.getAttribute("stID")));
		System.out.println("sessionCreated call log end======================");
        */
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent source) {
		// TODO Auto-generated method stub
		
		//HttpSession session = source.getSession();
		
	}

	
}
