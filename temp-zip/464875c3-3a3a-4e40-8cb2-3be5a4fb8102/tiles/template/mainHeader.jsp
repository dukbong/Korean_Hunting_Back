<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:importAttribute name="topMenu"/>
<div id="header">
	<div class="inner">
		<h1>
			<a href="#">
                <img src="${pageContext.request.contextPath}/assets/logo/logo.png" width="186" height="36">
			</a>
		</h1>
		<ul id="gnb">
			<c:forEach items="${topMenu}" var="item" varStatus="status">
				<li><a href="${item.menuUrl}">${item.menuNm}</a></li>
			</c:forEach>
		</ul>
		<div class="top_button">
			<button type="button" class="change_pw" id="passwordBtn">${msg.C000001292}</button> <!-- 비밀번호 변경 -->
			<button type="button" class="logout" onClick="location.href='/logout.lims'">${msg.C000001293}</button> <!-- 로그아웃 -->
		</div>
	</div>
	<div class="board">
		<div class="path">
			<span><i class="fi-rr-home"></i></span>
			<span id="auditAt"><img src="${pageContext.request.contextPath}/assets/image/main_arrow.png"></span>
			<span><a href="/main.lims">${msg.C000001302}</a></span> <!-- DASHBOARD -->
		</div>
		<div class="log_box">
			<button type="button" class="sanctner_btn" id="sanctnerBtn">${msg.C100001020} ${msg.C000000308} <span data-sanctn-totalcount-id="total"></span>${msg.C000001280}</button>
			<p class="sanctnCountBox sanctnCountBox-hide" id="arrowBox"></p>
			<%--<p class="user" title="${UserMVo.authorSeCodeNm}">--%>
				<span><i class="fi-rr-user"></i></span>${UserMVo.userNm} ${msg.C100001302} (${UserMVo.inspctInsttNm})
			<%--</p>--%> <!-- 님이 로그인하셨습니다. -->
			&nbsp <select id="langCdComboBox" name="langCd"></select>
		</div>
	</div>

	<!-- 다국어 재설정 리로드 폼 -->
	<form id="reloadPage" action="/locale/setLocale.lims" method="post" onsubmit="return false;">
		<input type="hidden" id="reloadLangCd" name="langCd">
		<input type="hidden" id="url" name="url">
	</form>
</div>

<!-- 패스워드 변경 팝업 -->
<div class="popupBg" id="popupBg_dialogPassword" style="display:none;"></div>
<section class="popup popup1" id="popup_dialogPassword" style="width: 350px ; padding:0; height: 365px; display:none;" >
	<div style="width:100%; height:50px; background:#1f296f; text-align:center; position:relative;">
		<div style="position:absolute; top:50%; left:50%; margin:-10px 0 0 -50px;">
			<h3 style="color:white; font-size:17px;">${msg.C000001292}</h3> <!-- 비밀번호 변경 -->
		</div>
	</div>
	<img class="popupClose" src="${pageContext.request.contextPath}/assets/image/popupClose.png" style="right:10px; top:10px;" id="popupClose_dialogPassword" alt="">
	<article class="ctsInnerArea">
		<div class='mgT55' style="width:70%; margin-left:15%;">
			<form id="passwordFrm">
				<table class="subTable1">
					<tr>
						<td class="wd70p">
							<input type="password" id="prePassword" name="prePassword" class="wd100p" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; font-size :12px; padding:10px;" placeHolder="현재 비밀번호">
						</td>
					</tr>
					<tr>
						<td class="wd70p">
							<input type="password" id="password" name="password" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; margin-top:10px; font-size :12px; padding:10px;" placeHolder="새 비밀번호">
							<input type="hidden" id="userIdPw" name="userIdPw" value="${UserMVo.lgnId}">
						</td>
					</tr>
					<tr>
						<td class="wd70p">
							<input type="password" id="passwordChk" name="passwordChk" style="width:100%; height: 40px; border : 1px solid #ccc; outline:0; margin-top:10px; font-size :12px; padding:10px;" placeHolder="새 비밀번호를 확인">
						</td>
					</tr>

					<tr>
						<td class="wd70p">
							<button type="button" id="btn_save_password" class="btn3" style="color:white; background:#1f296f;width:100%; height: 40px; border : 1px solid #ccc; outline:0; margin-top:10px; font-size :12px; padding:10px;">${msg.C000000015}</button><!-- 저장 -->
						</td>
					</tr>
				</table>
			</form>
		</div>
	</article>
</section>

<!-- 오딧 설정 팝업 -->
<div class="popupBg" id="popupBg_dialogAuditAt" style="display:none;"></div>
<section class="popup popup1" id="popup_dialogAuditAt" style="width: 350px ; display:none;" >
	<img class="popupClose" src="${pageContext.request.contextPath}/assets/image/popupClose.png" id="popupClose_dialogAuditAt" alt="">
	<div>
		<table style="float:left; width:80%">
			<colgroup>
				<col style="width:10%"/>
				<col style="width:5%"/>
				<col style="width:5%"/>
			</colgroup>
			<tr>
				<th>Audit 설정</th>
				<td style="text-align:center;"><label><input type="radio" name="radioAuditAt" value="Y">&nbsp;Yes&nbsp;</label></td>
				<td style="text-align:center;"><label><input type="radio" name="radioAuditAt" value="N">&nbsp;No&nbsp;</label></td>
			</tr>
		</table>
		<button type="button" class="btn1 inTableBtn inputBtn save" id="btnAuditAtSave" style="float:right;">저장</button>
	</div>
</section>
<script>

var sanctnCountBox = new SanctnCountBox(document.querySelector('#arrowBox'));
$(document).ready(function(){
	popupInit("passwordBtn", "dialogPassword");

	sanctnCountBox.setHrefEvent();
	sanctnCountBox.setSanctnCnt();

	// 다국어 콤보 리스트.
	ajaxJsonComboBox("/com/getCmmnCode.lims", "langCdComboBox", {"upperCmmnCode" : "SY06"}, false, null, "${locale}");


	// 결재 대기건수 click event
	$("#sanctnerBtn").click(function(e) {
		sanctnCountBox.toggle();
	});

	$("#sanctnerBtn").hover(function() {
		$(this).css('color', '#1f296f');
	}, function() {
		$(this).css('color', '#555');
	});

    /**
     * 사용자 변경기능 쓰면 살림
     *
     popupInit("userChangeBtn", "dialogUserChange", function() {
     });
     */


	if("${UserMVo.authorSeCode}" === "SY09000001"){
		auditAtInit();
	}

	$("#auditAt").click(function(e){
	});

	// 패스워드 체크
	$("#passwordFrm").find("#Password").change(function(){
		passwordChk("password");
	});

	// 패스워드 체크
	$('#passwordFrm').find('#passwordChk').change(function(){
		passwordChk("passwordChk");
	});
	$('#btn_save_password').click( function() {

		if($("#passwordFrm").find("#prePassword").val() === "" || $("#passwordFrm").find("#prePassword").val() == null){
			alert('${msg.C000001298}'); /* 현재 비밀번호를 입력하여 주세요. */
			return;
		}

		if($("#passwordFrm").find("#password").val() === "" || $("#passwordFrm").find("#password").val() == null){
			alert('${msg.C000001299}'); /* 새 비밀번호를 입력하여 주세요. */
			return;
		}

		if($("#passwordFrm").find("#password").val() !== $("#passwordFrm").find("#passwordChk").val()){
			alert('${msg.C000001300}'); /* 비밀번호가 일치하지 않습니다. */
			return;
		}

		var param = $("#passwordFrm").find("#password").val();
		customAjax({
			"url": "/com/passwordPolicyCheck.lims",
			"data": param,
			"successFunc": function(data) {
				if(data === 100) {
					warn('${msg.C100001132}'); /* 허용 가능한 비밀번호 최대 자릿수를 초과하였습니다. */
				}else if(data === 101) {
					warn('${msg.C100001133}'); /* 비밀번호는 특수문자를 반드시 포함해야 합니다. */
				}else if(data === 102) {
					warn('${msg.C100001134}'); /* 비밀번호는 숫자를 반드시 포함해야 합니다. */
				}else if(data === 103) {
					warn('${msg.C100001135}'); /* 비밀번호는 영문 대문자를 반드시 포함해야 합니다. */
				}else {
					saveChangePassword();
				}
			}
		});
	});



	$("#userChangeFrm").find("#userId").change(function(){
		$("#userChangeFrm").find("#pwd option:eq("+$(this).children('option:selected')[0].index+")").attr("selected", "selected");
	});

	$("#userChangeFrm").find('#btn_select_userChange').click( function(data) {
		$("#userChangeFrm").submit();
	});

	$(window).on("pageshow",function(event){
		if(event.target.URL.indexOf("#") !== -1){
			event.target.URL = event.target.URL.split("#")[0];
		}

	})

	//audit 여부 저장
	$("#btnAuditAtSave").click(function(){

		confirm("저장 하시겠습니까?", () => {
			var params = {
				ctmmnyUseAt : $(":input:radio[name=radioAuditAt]:checked").val()
				,ctmmnyCntrmsrSeqno : "1"
			};

			ajaxJsonParam3('/com/insAuditAt.lims', params, function (data) {
				if(data > 0){
					alert("저장 하였습니다.");
					$("#popupClose_dialogPassword").trigger("click");
				}
				else{
					alert("저장에 실패 하였습니다.");
				}
			});
		});
	});


	// 언어 변경 이벤트.
	var langCdComboBox = document.getElementById("langCdComboBox");
	var reloadPage = document.getElementById("reloadPage");
	var reloadLangCd = document.getElementById("reloadLangCd");
	var urlPath = document.getElementById("url");
	langCdComboBox.onchange = function(e) {
		reloadLangCd.value = langCdComboBox.value;
		urlPath.value = location.pathname;
		reloadPage.submit();
	}

});

function auditAtInit(){
	var auditAt = "${auditAt}";
	popupInit("auditAt", "dialogAuditAt");
	$("input:radio[name='radioAuditAt']:radio[value='"+auditAt+"']").prop("checked", true);
}

// 비밀번호 변경
function saveChangePassword() {
	ajaxJsonForm('/saveUserPassword.lims', 'passwordFrm', function (data) {
		if(data === 0){
			alert('${msg.C000000170}'); /* 저장에 실패하였습니다. */
		} else if(data === -1){
			alert('${msg.C000001301}') /* 현재 비밀번호와 다릅니다. */
		} else {
			alert('${msg.C000000071}'); /* 저장 되었습니다. */
			$("#passwordFrm").find("#prePassword").val('');
			$("#passwordFrm").find("#Password").val('');
			$("#passwordFrm").find("#PasswordChk").val('');
			$("#popupClose_dialogPassword").trigger("click");
		}
	});
}
</script>
