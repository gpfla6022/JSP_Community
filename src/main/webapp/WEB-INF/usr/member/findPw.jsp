<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="비밀번호찾기" />
<%@ include file="../part/head.jspf"%>

<section class="section section-member-findPw flex-grow flex justify-center items-center">
	<div class="w-full max-w-md card-wrap">
		<div class="card bordered shadow-lg">
			<div class="card-title">
				<span>
					<i class="fas fa-sign-in-alt"></i>
				</span>
				<span>비밀번호찾기</span>
			</div>

			<div class="px-4 py-4">
				<script>
					let MemberFindPw__submitDone = false;
					function MemberFindPw__submit(form) {
						if (MemberFindPw__submitDone) {
							return;
						}

						if (form.loginId.value.length == 0) {
							alert('로그인 아이디를 입력해주세요.');
							form.loginId.focus();

							return;
						}

						if (form.email.value.length == 0) {
							alert('이메일을 입력해주세요.');
							form.email.focus();

							return;
						}

						form.submit();
						MemberFindPw__submitDone = true;
					}
				</script>
				<form action="../member/doFindPw" method="POST"
					onsubmit="MemberFindId__submit(this); return false;">
					<input type="hidden" name="redirectUri" value="${param.afterLoginUri}" />
					
					<div class="form-control">
						<label class="label">
							<span class="label-text">로그인 아이디</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100"
								name="loginId" type="text" placeholder="로그인 아이디을 입력해주세요." />
						</div>
					</div>

					<div class="form-control">
						<label class="label">
							<span class="label-text">이메일</span>
						</label>
						<div>
							<input class="input input-bordered w-full" maxlength="100"
								name="email" type="text" placeholder="이메일을 입력해주세요." />
						</div>
					</div>

					<div class="btns">
						<button type="submit" class="btn btn-link">찾기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<%@ include file="../part/foot.jspf"%>