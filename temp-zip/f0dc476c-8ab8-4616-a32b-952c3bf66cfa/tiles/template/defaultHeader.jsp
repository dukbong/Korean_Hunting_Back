<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:importAttribute name="currentMenu"/>
<%--<tiles:importAttribute name="currentMenu"/>--%>

<div class="sub_header">
	<div class="sub_inner">
		<h1>
			<a href="/main.lims" class="d_flex"><img src="/assets/logo/logo.png" alt="로고"></a>
		</h1>
		<div class="top_menu">
			<div class="top_menuTop">
				<div class="top_button">
					<button type="button" class="change_pw" data-divid="popDiv" id="passwordBtn" tooltip="비밀번호 변경" flow="down"><i class="fi-rs-lock"></i></button> <!-- 비밀번호 변경 -->
					<button type="button" class="logout" onClick="closeWindow()"><i class="fi-rs-exit"></i></button> <!-- 로그아웃 -->
					<button type="button" class="change_pw" id="updateLangProperties" tooltip="다국어 업데이트" flow="down"><i class="fi-rs-text"></i></button> <!-- 다국어 업데이트 -->
					<!-- 다국어 셀렉트 박스 -->
					<select id="langCdComboBox"></select>
				</div>
			</div>
			<div class="location">
				<div class="path">
					<c:if test="${currentMenu.menuNm3 != null}">
						<span><a href="/"><i class="fi-rs-home"></i></a>홈</span>
						<span>${currentMenu.menuNm1}</span>
						<span>${currentMenu.menuNm2}</span>
						<span>${currentMenu.menuNm3}</span>
					</c:if>
					<c:if test="${currentMenu.menuNm3 == null}">
						<span><a href="/"><i class="fi-rs-home"></i></a>홈</span>
					</c:if>
					<c:if test="${currentMenu.mnlUseAt == 'Y'}">
						<div class="help"><button type="button"><i id="manual" class="fi-rs-interrogation"></i></button></div>
					</c:if>
				</div>
				<div class="user">
					<span><i class="fi-rs-portrait"></i></span>
					<span>(${sessionScope.UserMVo.deptNm}) <strong>${sessionScope.UserMVo.userNm}</strong>(${sessionScope.UserMVo.lgnId})${msg.C000000145}</span><c:if test="${mndtYn == 'Y'}"> <button type="button" class="absentee">${msg.C000000223}</button></c:if> <!-- 님이 로그인하였습니다. --><!-- 부재중 -->
				</div>
			</div>
		</div>
		<!-- 다국어 재설정 리로드 폼 -->
		<form id="reloadPage" action="/locale/setLocale.lims" method="post" onsubmit="return false;">
			<input type="hidden" id="reloadLangCd" name="langCd">
			<input type="hidden" id="url" name="url">
		</form>
		<%--COM 연결을위한 from--%>
		<form id="comViewForm" name="comViewForm" onsubmit="return false;">
			<input type="hidden" name="cid" id="cid" value="${sessionScope.UserMVo.lgnId}">
			<input type="hidden" name="cpw" id="cpw" value="${sessionScope.UserMVo.pswd}">
			<input type="hidden" name="langCd" id="langCd" value="${sessionScope.locale}">
		</form>
	</div>
</div>

<script>

	var printAuth = "Y";
	let pswdChgMPop = null;

	$(document).ready(function(){
		//loadDialog("/popup/changePswd.lims", "ChangePswd");

		pswdChgMPop = new Popup({
			"url" : "/popup/PswdChgM.lims",
			"popupGridIds" : ["#popDiv"],
			"param" : {
			},
			"successFunc" : function(obj){
				console.log(obj);
			},
			"openFunc" : function(){
				console.log("open");
			}
		});

		$("#userChangeFrm").find("#bestInspctInsttCode").change(function(){
			ajaxJsonComboBox('/sys/getUpperComboListM.lims','inspctInsttCode',{bestInspctInsttCode:$('#userChangeFrm').find('#bestInspctInsttCode').val()},true);
		});

		$("#userChangeFrm").find("#inspctInsttCode").change(function(){
			ajaxJsonComboBox2('/getUserChangeUserList.lims','userId',{inspctInsttCode:this.value},true,null,null,null,null,'pwd');
		});

		$("#userChangeFrm").find("#userId").change(function(){
			$("#userChangeFrm").find("#pwd option:eq("+$(this).children('option:selected')[0].index+")").attr("selected", "selected");
		});

		$("#userChangeFrm").find('#btn_select_userChange').click( function(data) {
			$("#userChangeFrm").submit();
		});

		/*//COM으로 이동
		$('#comLogin').click( function(data) {
			var comForm = document.comViewForm;
			var url = "http://localhost:8086/COM/comlogin.lims"

			window.open("" ,"comViewForm");
			comForm.action =url;
			comForm.method="post";
			comForm.target="comViewForm";

			comForm.submit();
		});*/

		//비밀번호 변경
		$('#passwordBtn').click((e) => {
			pswdChgMPop.show();
		});

		//메뉴얼
		$("#manual").click(() => {
			openPopupM("/sys/MenuHelpP.lims?menuSn=".concat(btoa(menuSn), "&menuUrl=", btoa(menuUrl)), "manual", { width: "1000", height: "650" });
		});

		defaultHeaderEvent();

		// 다국어 콤보.
		ajaxJsonComboBox("/com/getCmmnCode.lims", "langCdComboBox", {"upComnCd" : "NTLG" }, false, undefined, "${locale}");


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

	function setHeaderCombo(){
		ajaxJsonComboBox('/getUserChangeBestInspctInsttList.lims','bestInspctInsttCode',null,true);
		ajaxJsonComboBox('/sys/getUpperComboListM.lims','inspctInsttCode',{bestInspctInsttCode:$('#userChangeFrm').find('#bestInspctInsttCode').val()},true);
		ajaxJsonComboBox('/getUserChangeUserList.lims','userId',{inspctInsttCode:$('#userChangeFrm').find('#inspctInsttCode').val()},true);
	}

	function defaultHeaderEvent() {

		// 다국어 업데이트 버튼 클릭 이벤트
		$("#updateLangProperties").click(() => {
			updateProperties();
		});

	}

	// 다국어 properties 파일 수정.
	async function updateProperties() {
		try {
			const result = await customAjax({ "url": "/locale/updateProperties.lims" });
			if (result == 1) {
				success(lang.C000000729);//완료되었습니다.
			} else {
				err(lang.C000000728);//오류가 발생했습니다. 관리자에게 문의해 주세요.
			}
		} catch {
			err(lang.C000000728);//오류가 발생했습니다. 관리자에게 문의해 주세요.
		}
	}

	//윈도우 팝업 호출
	function openPopupM(url, name, prop) {
		const keys = Object.keys(prop);
		let option = "";
		for (let i = 0; i < keys.length; i++) {
			option += keys[i].concat("-", prop[keys[i]], ", ");
		}

		option = option.substring(0, option.length - 1);
		return window.open(url, name, option);
	}

	function closeWindow() {
		window.open('about:blank','_parent').parent.close();
	}

</script>
