package com.yhr.exam.exam2.container;

import java.util.ArrayList;
import java.util.List;

import com.yhr.exam.exam2.app.App;
import com.yhr.exam.exam2.http.controller.AdmHomeController;
import com.yhr.exam.exam2.http.controller.Controller;
import com.yhr.exam.exam2.http.controller.UsrArticleController;
import com.yhr.exam.exam2.http.controller.UsrHomeController;
import com.yhr.exam.exam2.http.controller.UsrMemberController;
import com.yhr.exam.exam2.interceptor.BeforeActionInterceptor;
import com.yhr.exam.exam2.interceptor.NeedAdminInterceptor;
import com.yhr.exam.exam2.interceptor.NeedLoginInterceptor;
import com.yhr.exam.exam2.interceptor.NeedLogoutInterceptor;
import com.yhr.exam.exam2.repository.ArticleRepository;
import com.yhr.exam.exam2.repository.BoardRepository;
import com.yhr.exam.exam2.repository.MemberRepository;
import com.yhr.exam.exam2.service.ArticleService;
import com.yhr.exam.exam2.service.BoardService;
import com.yhr.exam.exam2.service.EmailService;
import com.yhr.exam.exam2.service.MemberService;

public class Container {
	// container를 만든 이유
	// 객체는 한 번만 만들어져서, 그 만들어진 객체를 공유하여 사용하기 위해 container를 만들었다. 
	
	// 필드
	private static List<ContainerComponent> containerComponents;
	// containerComponents를 만든 이유
	// containerComponents가 들어갈 수 있는 리스트를 만듬
	
	public static App app; // App 클래스를 객체화 해서 사용하려고 만듬
	
	public static BeforeActionInterceptor beforeActionInterceptor;
	public static NeedLoginInterceptor needLoginInterceptor;
	public static NeedLogoutInterceptor needLogoutInterceptor;
	public static NeedAdminInterceptor needAdminInterceptor;

	public static ArticleRepository articleRepository;
	public static ArticleService articleService;
	public static UsrArticleController usrArticleController;

	public static MemberRepository memberRepository;
	public static MemberService memberService;
	public static UsrMemberController usrMemberController;

	public static UsrHomeController usrHomeController;

	public static BoardRepository boardRepository;
	public static BoardService boardService;
	
	public static AdmHomeController admHomeController;
	
	public static EmailService emailService;

	// 생성자 대신 init()을 만들어 생성자의 역할을 하게 함
	// 왜? -> container에서 모든 객체를 관리하기 위해서
	public static void init() { // 메소드
		containerComponents = new ArrayList<>(); // 비어있는 리스트 객체생성

		// 의존성 세팅 시작 - 위에 필드에 값을 넣어줌
		app = addContainerComponent(new App());
		memberRepository = addContainerComponent(new MemberRepository());
		boardRepository = addContainerComponent(new BoardRepository());
		articleRepository = addContainerComponent(new ArticleRepository());

		memberService = addContainerComponent(new MemberService());
		boardService = addContainerComponent(new BoardService());
		articleService = addContainerComponent(new ArticleService());

		beforeActionInterceptor = addContainerComponent(new BeforeActionInterceptor());
		needLoginInterceptor = addContainerComponent(new NeedLoginInterceptor());
		needLogoutInterceptor = addContainerComponent(new NeedLogoutInterceptor());
		needAdminInterceptor = addContainerComponent(new NeedAdminInterceptor());

		usrMemberController = addContainerComponent(new UsrMemberController());
		usrArticleController = addContainerComponent(new UsrArticleController());
		usrHomeController = addContainerComponent(new UsrHomeController());

		admHomeController = addContainerComponent(new AdmHomeController());

		emailService = addContainerComponent(new EmailService());
		
		// 객체 초기화
		for(ContainerComponent containerComponent : containerComponents) {
			containerComponent.init();
		}
		
	}
      // 제네릭은 치환가능(가상의 타입), object는 치환불가(일종의 데이터 타입)
		private static <T> T addContainerComponent(ContainerComponent containerComponent) {
			containerComponents.add(containerComponent);

			return (T) containerComponent;

		
		
	}
}
