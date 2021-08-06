package com.yhr.exam.exam2.http.controller;

import com.yhr.exam.exam2.app.App;
import com.yhr.exam.exam2.container.Container;
import com.yhr.exam.exam2.http.Rq;
import com.yhr.exam.exam2.util.Ut;

public class UsrHomeController extends Controller {
	
	public void init() {
		
	}
	
	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "main":
			actionShowMain(rq);
			break;
		case "mail":
			actionShowMmail(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}

	private void actionShowMmail(Rq rq) {
		
		App app = Container.app;
		Ut.sendMail(app.getSmtpGmailId(), app.getSmtpGmailPw(), "happy@gmail.com", "테스트 메일입니다.", "gpfla3503@gmail.com", "테스트 제목", "테스트 내용");
		
		rq.jsp("usr/home/main");
		
	}

	private void actionShowMain(Rq rq) {
		rq.jsp("usr/home/main");
	}
}
