<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="main-definition">
	<tiles:putAttribute name="title">Main</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div id="contents">

		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="script">
		<script src="/viewScript/main.js?ver=20230308"></script>
		<style type="text/css">
			/* 커스텀 행 스타일 */
			.my-row-style {
				background-color:#FAFA96;
				font-weight:bold;
				color:#d3825c;
			}
		</style>
	</tiles:putAttribute>
</tiles:insertDefinition>
