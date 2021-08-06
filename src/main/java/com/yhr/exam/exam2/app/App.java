package com.yhr.exam.exam2.app;

import java.io.File;

import com.yhr.exam.exam2.container.Container;
import com.yhr.exam.exam2.container.ContainerComponent;
import com.yhr.exam.exam2.util.Ut;
import com.yhr.mysqliutil.MysqlUtil;

import lombok.Getter;

public class App implements ContainerComponent {
	@Getter
	private boolean ready = false;
	private String smtpGmailPw;

	@Override
	public void init() {

		// 필수적으로 로딩 되어야 하는 내용 불러오기
		smtpGmailPw = Ut.getFileContents("c://work//yhr//SmtpGmailPw.txt");

		if (smtpGmailPw != null && smtpGmailPw.trim().length() > 0) {
			ready = true;
		}
	}

	// 개발모드
	public static boolean isDevMode() {
		// 이 부분을 false로 바꾸면 production 모드 이다.
		return true;
	}
	
	// 서버에서 돌아가는 모드
	private static boolean isProductMode() {
		return isDevMode() == false;
	}

	// 정적 요소 세팅
	public static void start() {
		// DB 세팅
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jsp_board");
		MysqlUtil.setDevMode(isDevMode());

		// 공용 객체 세팅
		Container.init();

	}

	public String getSmtpGmailId() {
		return "gpfla3503@gmail.com";
	}

	public String getSmtpGmailPw() {
		return smtpGmailPw;

	}

	public String getSiteName() {
		return "JSP Community";
	}

	public String getBaseUri() {
		String appUri = getSiteProtocol() + "://" + getSiteDomain();

		if (getSitePort() != 80 && getSitePort() != 443) {
			appUri += ":" + getSitePort();
		}

		if (getContextName().length() > 0) {
			appUri += "/" + getContextName();
		}

		return appUri;
	}

	private String getContextName() {
		if (isProductMode()) {
			return "";
		}

		return "2021_jsp_community";
	}

	private int getSitePort() {
		return 8087;
	}

	private String getSiteDomain() {
		return "localhost";
	}

	private String getSiteProtocol() {
		return "http";
	}

	public String getLoginUri() {
		return getBaseUri() + "/usr/member/login";
	}

	public String getNotifyEmailFromName() {
		return "JSP Community 알림봇";
	}
}
