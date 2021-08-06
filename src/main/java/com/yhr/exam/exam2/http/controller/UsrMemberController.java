package com.yhr.exam.exam2.http.controller;

import com.yhr.exam.exam2.container.Container;
import com.yhr.exam.exam2.dto.Member;
import com.yhr.exam.exam2.dto.ResultData;
import com.yhr.exam.exam2.http.Rq;
import com.yhr.exam.exam2.service.MemberService;
import com.yhr.exam.exam2.util.Ut;

public class UsrMemberController extends Controller {
	private MemberService memberService;

	public void init() {
		memberService = Container.memberService;
	}

	@Override
	public void performAction(Rq rq) {
		switch (rq.getActionMethodName()) {
		case "login":
			actionShowLogin(rq);
			break;
		case "doLogin":
			actionDoLogin(rq);
			break;
		case "doLogout":
			actionDoLogout(rq);
			break;
		case "join":
			actionShowJoin(rq);
			break;
		case "doJoin":
			actionDoJoin(rq);
			break;
		case "findId":
			actionShowFindId(rq);
			break;
		case "doFindId":
			actionDoFindId(rq);
			break;
		case "findPw":
			actionShowFindPw(rq);
			break;
		case "doFindPw":
			actionDoFindPw(rq);
			break;
		case "modify":
			actionShowModify(rq); 
			break;
		case "doModify":
			actionDoModify(rq);
			break;
		default:
			rq.println("존재하지 않는 페이지 입니다.");
			break;
		}
	}


	private void actionDoModify(Rq rq) {
		int id = rq.getIntParam("id", 0);
		String loginPw = rq.getParam("loginPw", "");
		String name = rq.getParam("name", "");
		String nickname = rq.getParam("nickname", "");
		String cellphoneNo = rq.getParam("cellphoneNo", "");
		String email = rq.getParam("email", "");
		
		if (loginPw.length() == 0) {
			rq.historyBack("비밀번호를 입력해주세요.");
			return;
		}

		if (name.length() == 0) {
			rq.historyBack("이름을 입력해주세요.");
			return;
		}

		if (nickname.length() == 0) {
			rq.historyBack("별명을 입력해주세요.");
			return;
		}

		if (cellphoneNo.length() == 0) {
			rq.historyBack("전화번호를 입력해주세요.");
			return;
		}

		if (email.length() == 0) {
			rq.historyBack("이메일을 입력해주세요.");
			return;
		}

		ResultData modifyRd = memberService.modify(id, loginPw, name, nickname, cellphoneNo, email);

		if (modifyRd.isFail()) {
			rq.historyBack(modifyRd.getMsg());
			return;
		}
		
		rq.replace(modifyRd.getMsg(), "../home/main");
	}

	private void actionShowModify(Rq rq) {
		
		Member member = rq.getLoginedMember();
		
		rq.setAttr("member", member);
		rq.jsp("usr/member/modify");
		
	}

	private void actionDoFindPw(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String email = rq.getParam("email", "");

		if (loginId.length() == 0) {
			rq.historyBack("이름을 입력해주세요.");
			return;
		}

		if (email.length() == 0) {
			rq.historyBack("이메일을 입력해주세요.");
			return;
		}
		

		Member oldMember = memberService.getMemberByLoginId(loginId);

		if (oldMember == null) {
			rq.historyBack("일치하는 회원이 존재하지 않습니다.");
			return;
		}

		if (oldMember.getEmail().equals(email) == false) {
			rq.historyBack("일치하는 회원이 존재하지 않습니다.");
			return;
		}

		ResultData sendTempLoginPwToEmailRd = memberService.sendTempLoginPwToEmail(oldMember);
		
		if ( sendTempLoginPwToEmailRd.isFail() ) {
			rq.historyBack(sendTempLoginPwToEmailRd.getMsg());
			return;
		}
		
		rq.replace(sendTempLoginPwToEmailRd.getMsg(), "../home/main");
		return;
	}

		/*
		ResultData findPwRd = memberService.findPw(loginId, email);

		if (findPwRd.isFail()) {
			rq.historyBack(findPwRd.getMsg());
			return;
		}

		Member member = (Member) findPwRd.getBody().get("member");

		String memPw = member.getLoginId();

		rq.replace("비밀번호는 " + memPw + "입니다. 잊어버리지 마세요", "../home/main");

	}*/

	private void actionShowFindPw(Rq rq) {
		rq.jsp("usr/member/findPw");

	}

	private void actionDoFindId(Rq rq) {
		String name = rq.getParam("name", "");
		String email = rq.getParam("email", "");

		if (name.length() == 0) {
			rq.historyBack("이름을 입력해주세요.");
			return;
		}

		if (email.length() == 0) {
			rq.historyBack("이메일을 입력해주세요.");
			return;
		}
		
		Member oldMember = memberService.getMemberByNameAndEmail(name, email);

		if (oldMember == null) {
			rq.historyBack("일치하는 회원이 존재하지 않습니다.");
			return;
		}

		String replaceUri = "../member/login?loginId=" + oldMember.getLoginId();
		rq.replace(Ut.f("해당 회원의 로그인아이디는 `%s` 입니다.", oldMember.getLoginId()), replaceUri);
	}

		/*ResultData findIdRd = memberService.findId(name, email);

		if (findIdRd.isFail()) {
			rq.historyBack(findIdRd.getMsg());
			return;
		}

		Member member = (Member) findIdRd.getBody().get("member");

		String memId = member.getLoginId();

		rq.replace("아이디는 " + memId + "입니다. 잊어버리지 마세요", "../home/main");
		
		}*/

	

	private void actionShowFindId(Rq rq) {
		rq.jsp("usr/member/findId");

	}

	private void actionDoLogout(Rq rq) {
		rq.removeSessionAttr("loginedMemberJson");
		rq.replace(null, "../../");
	}

	private void actionDoLogin(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String loginPw = rq.getParam("loginPw", "");

		if (loginId.length() == 0) {
			rq.historyBack("아이디를 입력해주세요.");
			return;
		}

		if (loginPw.length() == 0) {
			rq.historyBack("비밀번호를 입력해주세요.");
			return;
		}

		ResultData loginRd = memberService.login(loginId, loginPw);

		if (loginRd.isFail()) {
			rq.historyBack(loginRd.getMsg());
			return;
		}

		Member member = (Member) loginRd.getBody().get("member");

		rq.setSessionAttr("loginedMemberJson", Ut.toJson(member, ""));

		String redirectUri = rq.getParam("redirectUri", "../article/list");

		rq.replace(loginRd.getMsg(), redirectUri);
	}

	private void actionShowLogin(Rq rq) {
		rq.jsp("usr/member/login");
	}

	private void actionDoJoin(Rq rq) {
		String loginId = rq.getParam("loginId", "");
		String loginPw = rq.getParam("loginPw", "");
		String name = rq.getParam("name", "");
		String nickname = rq.getParam("nickname", "");
		String cellphoneNo = rq.getParam("cellphoneNo", "");
		String email = rq.getParam("email", "");

		if (loginId.length() == 0) {
			rq.historyBack("아이디를 입력해주세요.");
			return;
		}

		if (loginPw.length() == 0) {
			rq.historyBack("비밀번호를 입력해주세요.");
			return;
		}

		if (name.length() == 0) {
			rq.historyBack("이름을 입력해주세요.");
			return;
		}

		if (nickname.length() == 0) {
			rq.historyBack("별명을 입력해주세요.");
			return;
		}

		if (cellphoneNo.length() == 0) {
			rq.historyBack("전화번호를 입력해주세요.");
			return;
		}

		if (email.length() == 0) {
			rq.historyBack("이메일을 입력해주세요.");
			return;
		}

		rq.debugParams();
		// rq.print("여기까지는 성공");

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			rq.historyBack(joinRd.getMsg());
			return;
		}
		rq.replace(joinRd.getMsg(), "../member/login");
	}

	private void actionShowJoin(Rq rq) {
		rq.jsp("usr/member/join");
	}
}
