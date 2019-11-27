package net.ungk.web;

import javax.servlet.http.HttpSession;

import net.ungk.domain.User;

public class HttpSessionUtils {
	public static final String User_SESSION_KEY = "sessionedUser";
	
	public static boolean isLoginUser (HttpSession session) {
		Object sessionedUser = session.getAttribute(HttpSessionUtils.User_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		//세션에서 받아오는 대상은 Object 자료형
		return (User) session.getAttribute(HttpSessionUtils.User_SESSION_KEY); 
	}
}