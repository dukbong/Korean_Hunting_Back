<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- title -->

		<section class='popup popContentHalf' id="popup_dialogPassword">
			<div class="ctsInnerAreaTop">
				<div class="ctsInnerAreaTopLft modal-header">
					<h5 class="modal-title">${msg.C000001083}</h5> <!-- 비밀번호 변경 -->
				</div>
			</div>
			<img class="popupClose popup-close" src="/assets/image/popupClose.png" data-divid="popDiv"  style="right:10px; top:10px;" id="popupClose_dialogPassword"  alt="">
			<article class="ctsInnerArea">
				<div class='mgT40' style="width:70%; margin-left:15%;">
					<form id="passwordFrm">
						<input type="hidden" id="popUserSn" name="userSn" value="${UserMVo.userSn}">
						<input type="hidden" id="popLgnId" name="lgnId" value="${UserMVo.lgnId}">
						<table class="subTable1">
							<colgroup>
								<col style="width:40%"/>
								<col style="width:60%"/>
							</colgroup>
							<tr>
								<th class="taCt vaMd">${msg.C000000699}</th> <!-- 현재 비밀번호 -->
								<td style="margin-left: 10px;">
									<input type="password" id="pswd" name="pswd" class="wd100p" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; font-size :12px; padding:10px;" placeHolder="${msg.C000000699}"> <!-- 현재 비밀번호 -->
								</td>
							</tr>
							<tr>
								<th class="taCt vaMd">${msg.C000000272}</th> <!-- 새 비밀번호 -->
								<td style="margin-left: 10px;">
									<input type="password" id="newPassword" name="newPassword" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; margin-top:10px; font-size :12px; padding:10px;" placeHolder="${msg.C000000272}"> <!-- 새 비밀번호 -->
								</td>
							</tr>
							<tr>
								<th class="taCt vaMd">${msg.C000000234}</th> <!-- 비밀번호 확인 -->
								<td style="margin-left: 10px;">
									<input type="password" id="passwordChk" name="passwordChk" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; margin-top:10px; font-size :12px; padding:10px;" placeHolder="${msg.C000000234}"> <!-- 비밀번호 확인 -->
								</td>
							</tr>
							<tr>
								<th class="taCt vaMd">${msg.C000000231}</th> <%-- 비밀번호 정책 --%>
								<td style="margin-left: 10px;">
									<input type="text" id="chgPasswordPolicy" name="chgPasswordPolicy" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; font-size :10px; padding:10px;margin-top:10px;" readonly>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="mgT20"></div>
			</article>
			<div class="modal-footer">
				<button type="button" id="btn_save_password" class="btn btn-primary" style="font-size: 12px !important;">${msg.C000000984}</button><!-- 저장 -->
			</div>
		</section>
<script src="/viewScript/com/popup/PswdChgM.js?ver=<fmt:formatDate value="<%=new Date()%>" pattern="yyMMddHHmm"/>"></script>
<style>
	*{
		margin: 0px;
		padding: 0px;
		box-sizing: border-box;
	}


	i.fi-rs-exclamation{color:#fff; vertical-align:middle; margin-right:5px; }
	.d_flex{display:flex; }

	.sign_popup form > div:nth-child(2){margin-top:10px}
	.sign_popup label{width:88px; vertical-align:middle}
	.sign_popup select{width:calc(100% - 88px)}
	.sign_popup textarea{width:calc(100% - 88px)}
	.sign_popup{margin-top:10px; padding:10px 20px; border:10px solid #f8f8f8}
	.sign_popup input[type="text"]{display:block; border:1px solid #ccc; height:30px; width:calc(100% - 88px); margin-right:0; padding-left:5px; }
	.sign_popup input[type="checkbox"]{vertical-align:middle}
	.sign_popup input + input{margin-top:10px}
	.sign_popup select{height:30px; border:1px solid #ccc}
	.sign_popup textarea{border:1px solid #ccc}
	.sign_popup p + p{margin-top:10px}
	.check{text-align: right; padding-right:4px; margin-top:10px}

	.modal-body{padding:1rem 2rem}
	.modal-header{background:#f18f77; height:60px; display:flex; align-items:center; justify-content:center; border-top-left-radius: 5px; border-top-right-radius:5px}
	.modal-title{font-weight:400; font-size:18px; color:#fff; display:flex; align-items:center; line-height:1 }

	#top_wrap{background:#f5f5f5; padding:20px 30px; text-align:center}
	#top_wrap span{line-height:1.5; word-break:keep-all; font-size:16px}
	.popup_user{margin-top:20px}


	input[type=" checkbox"] {
		vertical-align: baseline !important;
		box-sizing: border-box;
		padding: 0;
	}

	.modal-footer {
		display: flex;
		-ms-flex-align: center;
		align-items: center;
		-ms-flex-pack: end;
		justify-content: center;
		border-top:1px solid #ccc;
	}
	.modal-footer_inner{padding:15px 30px}

	.btn {
		display: inline-block;
		font-weight: 400;
		text-align: center;
		white-space: nowrap;
		vertical-align: middle;
		user-select: none;
		border: 1px solid transparent;
		padding: 0.375rem 1.75rem;
		font-size: 1rem;
		line-height: 1.5;
		border-radius: 0.25rem;
		transition: color .15s
	}

	.btn-primary{
		color: #fff;
		background-color:#595757; transition:0.3s
	}

	.btn-primary:hover{background:#333}
	.modal-footer>:not(:last-child) {
		margin-right: 0.25rem;
	}
</style>
