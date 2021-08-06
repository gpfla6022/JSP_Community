package com.yhr.exam.exam2.interceptor;

import com.yhr.exam.exam2.http.Rq;

public class NeedLoginInterceptor extends Interceptor {

	public void init() {
		
	}
	
	@Override
	public boolean runBeforeAction(Rq rq) {
		
		if(rq.getControllerTypeName().equals("usr") == false) {
			return true;
		}
		
		switch (rq.getActionPath()) {
		case "/usr/article/list":
		case "/usr/article/detail":
		case "/usr/home/main":
		case "/usr/member/login":
		case "/usr/member/doLogout":
		case "/usr/member/doLogin":
		case "/usr/member/join":
		case "/usr/member/doJoin":
		case "/usr/member/modify":
		case "/usr/member/doModify":
		case "/usr/member/findId":
		case "/usr/member/doFindId":
		case "/usr/member/findPw":
		case "/usr/member/doFindPw":
		case "/usr/member/findLoginId":
		case "/usr/member/doFindLoginId":
		case "/usr/member/findLoginPw":
		case "/usr/member/doFindLoginPw":
			return true;
		}
		
		if ( rq.isNotLogined() ) {
			rq.replace("로그인 후 이용해주세요.", "../member/login?afterLoginUri=" + rq.getEncodedAfterLoginUri());
			
			return false;
		}

		return true;
	}

}
