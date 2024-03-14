<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%--<tiles:importAttribute name="topMenu"/>--%>
<tiles:importAttribute name="currentMenu"/>
<div class="sub_board">
	<div class="sub_path">
		<span><i class="fi-rr-home"></i></span>
		<span><img src="/assets/image/main_arrow.png" alt="화살표"></span>
		<span>${currentMenu.menuNm2}</span>
		<span><img src="/assets/image/main_arrow.png" alt="화살표"></span>
		<span>${currentMenu.menuNm3}</span>
		<span><img src="/assets/image/main_arrow.png" alt="화살표"></span>
		<span>${currentMenu.menuNm4}</span>
		<span><img src="/assets/image/manual_blue.png" alt="매뉴얼" title="Help" style="cursor:pointer" id="btnMenual"></span>
		<span id="bookMarkSpan"></span>
		<span class="btnWrap1">
			<button type="button" id="resetCombo" class="search">콤보재로드</button>
			</span>
	</div>
	<div class="log_box">
		<button type="button" class="sanctner_btn" id="sanctner_btn">${msg.C100001020} ${msg.C100001301} <span data-sanctn-totalcount-id="total"></span>${msg.C000001280}</button>
		<p class="sanctnCountBox sanctnCountBox-hide" id="arrowBox"></p>
		<span class="mgR10" title="${UserMVo.authorSeCodeNm}"><i class="fi-rr-user"></i>${UserMVo.userNm} ${msg.C100001302} (${UserMVo.inspctInsttNm})</span> <!-- 님이 로그인하였습니다 -->

		<!-- 다국어 셀렉트 박스 -->
		<select id="langCdComboBox"></select>

		<c:choose>
			<c:when test="${UserMVo.authorSeCode == 'SY09000004'}"> <!-- 고객사(일반) -->
				<input type="hidden" id="userAuthor" name="userAuthor" value="N">
			</c:when>
			<c:otherwise> <!-- 일반사용자 또는 관리자 -->
				<c:choose>
					<c:when test="${auditAt == 'Y'}"> <!-- 고객사(일반) -->
						<input type="hidden" id="userAuthor" name="userAuthor" value="N">
					</c:when>
					<c:otherwise> <!-- 일반사용자 또는 관리자 -->
						<input type="hidden" id="userAuthor" name="userAuthor" value="B">
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script>

	var sanctnCountBox = new SanctnCountBox(document.querySelector('#arrowBox'));
	var bookmarkIcon = '<img src="/assets/image/bookmarkIcon.png" alt="북마크 삭제" style="margin-top: -5px; cursor:pointer">';
	var bookmarkAddIcon = '<img src="/assets/image/bookmarkAddIcon.png" alt="북마크 추가" style="margin-top: -5px; cursor:pointer">';

	$(document).ready(function() {

		dynamicShowIcon();

		sanctnCountBox.setHrefEvent();
		sanctnCountBox.setSanctnCnt();

		// 결재 대기건수 click event
		$("#sanctner_btn").click(function(e) {
			sanctnCountBox.toggle();
		});
		//콤보재로드
		$("#resetCombo").click(function(e) {
			resetCombo();
		});

		$("#sanctner_btn").hover(function() {
			$(this).css('color', '#1f296f');
		}, function() {
			$(this).css('color', '#555');
		});

		// 다국어 콤보.
		ajaxJsonComboBox("/com/getCmmnCode.lims", "langCdComboBox", {"upperCmmnCode" : "SY06"}, false, null, "${locale}");

		document.getElementById("btnMenual").addEventListener("click",function(){
			var menuSeqno;
			customAjax({"url":"/sys/getMenuSn.lims","data":{menuUrl:window.location.pathname+window.location.search},"successFunc":function(data){
					menuSeqno =data.menuSeqno;

					windowPostOpen('/sys/MenuHelpP.lims',{
						"menuUrl" : window.location.pathname + window.location.search,
						"menuSeqno" : menuSeqno
					}, 'manual', '1000','650');

				}});
		});

		document.getElementById("bookMarkSpan").addEventListener("click", function() {
			bookMarkControl();
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


	function bookMarkControl() {
		customAjax({
			url: "/sys/bookMarkControl.lims",
			data: { menuSeqno: ${currentMenu.menuCd4} },
			successFunc: function (data) {
				var status = data.returnStr;
				var newTopMenu = data.topMenu;
				var newLeftMenu = data.leftMenu;
				var isBookMarkPage = window.location.href.indexOf("bookmark");

				// 북마크 삭제 후, 등록된 북마크메뉴가 더 이상 없다면 홈으로 이동
				if (newLeftMenu.length == 0 && isBookMarkPage > 0) {
					window.location.href = "/main.lims";
				}

				if (status == "save") {
					success("북마크 메뉴로 등록되었습니다.");
					document.getElementById("bookMarkSpan").innerHTML = bookmarkAddIcon;
				} else if (status == "del") {
					success("북마크 메뉴에서 삭제되었습니다.");
					document.getElementById("bookMarkSpan").innerHTML = bookmarkIcon;
				} else {
					err("${msg.C100000597}");
				}

				// topMenu에 기존 My메뉴 el 제거하고 갱신된 My메뉴 el 적용
				var newTopHtml = "<li><a href=\""+newTopMenu[newTopMenu.length-1].menuUrl+"\">"+newTopMenu[newTopMenu.length-1].menuNm+"</a></li>";
				var childLength = document.querySelector('#sub_gnb').children.length;
				document.querySelector('#sub_gnb').children[childLength-1].remove();
				document.querySelector('#sub_gnb').innerHTML += newTopHtml;

				// 현재화면이 북마크메뉴 화면인 경우에만 갱신된 leftMenu 렌더링
				if (isBookMarkPage > 0) {
					document.querySelector("#sub_gnb").lastChild.childNodes[0].classList.add('active');
					document.querySelector('#lnb').innerHTML = null;
					document.querySelector('#lnb').innerHTML = "<li class=\"lnbLst tric\"><a href=\"#\" class=\"open\"></a></li>";

					var upperMenuIndex = -1;

					for (var i = 0; i < newLeftMenu.length; i++) {
						if (newLeftMenu[i].upperMenuSeqno == "100000") { // 2차메뉴 el 렌더링
							upperMenuIndex += 1;

							document.querySelector('#lnb').innerHTML += "<li class=\"lnbLst\"><a href=\"#\" class=\"close\"><i class=\"fi-rr-folder-add\"></i>"+newLeftMenu[i].menuNm+"</a></li>";
							document.querySelectorAll('.lnbLst')[upperMenuIndex+1].innerHTML += "<ul class=\"lnb2dt\"></ul>";

						} else { // 3차메뉴 el 렌더링
							document.querySelectorAll('.lnb2dt')[upperMenuIndex].innerHTML +=
									"<li class=\"lnb2dtLst\"><a href=\""+newLeftMenu[i].menuUrl+"\" value=\""+newLeftMenu[i].menuSeqno+"\"><i class=\"fi-rr-folder\"></i><span class=\"list_bg\"></span>"+newLeftMenu[i].menuNm+"</a></li>";
						}
					}

					// 최상단에 위치한 2차메뉴와 그에 해당되는 3차메뉴들 css 적용
					if (newLeftMenu.length > 0) {
						document.querySelectorAll('.lnbLst')[1].children[0].classList.add('open');
						document.querySelector('.lnb2dt').setAttribute('style', 'display: block');
					}
				}
			}
		});
	}


	// 북마크아이콘 동적 컨트롤
	function dynamicShowIcon() {
		customAjax({
			url: "/sys/chkBookMarkRegistred.lims",
			data: { menuSeqno: ${currentMenu.menuCd4} },
			successFunc: function (data) {
				if (data == 1) {
					document.getElementById("bookMarkSpan").innerHTML = bookmarkAddIcon;
				} else {
					document.getElementById("bookMarkSpan").innerHTML = bookmarkIcon;
				}
			}
		});
	}

</script>
