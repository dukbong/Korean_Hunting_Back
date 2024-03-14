<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:importAttribute name="leftMenu"/>
<tiles:importAttribute name="currentMenu"/>
<div class="left_menu">
	<div class="menu type1">
		<span>MENU</span>
		<div class="toggle open">
			<span class="border border1"></span>
			<span class="border border2"></span>
			<span class="border border3"></span>
		</div>
	</div>

	<div class="sideNav">
		<ul>
			<c:forEach items="${leftMenu}" var="item" varStatus="status">
			<c:if test="${item.menuLevel == 1}">
			<c:if test="${submenu1 == '2'}">
		</ul>
		</c:if>
		<c:if test="${submenu == '1'}">
			</ul>
			</li>
		</c:if>
		<c:set value="1" var="submenu"/>
		<c:set value="1" var="submenu1"/>

		<c:if test="${item.menuSn == currentMenu.menuCd1}">
		<li class="has-sub open"><i class="fi-rs-folder oneDepth"></i><a href="#">${item.menuNm}</a>
			<ul style="display: block;">
				</c:if>
				<c:if test="${item.menuSn != currentMenu.menuCd1}">
				<li class="has-sub"><i class="fi-rs-folder oneDepth"></i><a href="#">${item.menuNm}</a>
					<ul style="display: none;">
						</c:if>
						</c:if>
						<c:if test="${item.menuLevel == 2}">
						<c:if test="${submenu1 == '2'}">
					</ul>
				</li>
				</c:if>
					<c:set value="2" var="submenu1"/>
				<c:if test="${item.menuSn == currentMenu.menuCd2}">
				<li class="has-sub open"><i class="fi-rs-file-chart-line twoDepth"></i><a href="#">${item.menuNm}</a>
					<ul style="display: block;">
						</c:if>
						<c:if test="${item.menuSn != currentMenu.menuCd2}">
						<li class="has-sub"><i class="fi-rs-file-chart-line twoDepth"></i><a href="#">${item.menuNm}</a>
							<ul style="display: none;">
								</c:if>
								</c:if>
								<c:if test="${item.menuLevel == 3}">
									<c:if test="${item.menuSn == currentMenu.menuCd3}">
										<li><i class="fi-rs-template title threeDepth"></i><a href="${item.menuUrl}" value="${item.menuSn}" class="clicked_menu" >${item.menuNm}</a></li>
									</c:if>
									<c:if test="${item.menuSn != currentMenu.menuCd3}">
										<li><i class="fi-rs-template title threeDepth"></i><a href="${item.menuUrl}" value="${item.menuSn}">${item.menuNm}</a></li>
									</c:if>
								</c:if>
								</c:forEach>
								<c:if test="${submenu == '1'}">
							</ul>
						</li>
						</c:if>
					</ul>
	</div><!-- lnb END -->
</div><!-- left_menu END -->
<script>
	document.title = "COM - " + "${currentMenu.menuNm3}";
</script>
