<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>

<html>
<head>
	<link href="<c:url value='/assets/logo/favicon.ico' />" rel="icon" type="image/x-icon" />
	<link href="<c:url value='/assets/logo/favicon.ico' />" rel="shortcut icon" type="image/x-icon" />

	<link href="<c:url value='/AUIGrid/AUIGrid_style.css' />"  rel="stylesheet">
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/AUIGrid_style.css' />">
	<script type="text/javascript" src="<c:url value='/AUIGrid/AUIGridLicense.js' />"></script>
	<!-- 실제적인 AUIGrid 라이브러리입니다. 그리드 출력을 위해 꼭 삽입하십시오.-->
	<script type="text/javascript" src="<c:url value='/AUIGrid/AUIGrid.js' />"></script>
	<script type="text/javascript" src="<c:url value='/AUIGrid/messages/AUIGrid.messages.kr.js' />"></script>

	<script src="<c:url value='/assets/js/jquery-3.2.1.min.js' />"></script>
	<!-- toastr lims.js 보다 위에서 먼저 load되야합니다.-->
	<link rel="stylesheet" href="<c:url value='/assets/js/toastr/toastr.min.css' />">
	<script  type="text/javascript" src="<c:url value='/assets/js/toastr/toastr.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/js/all.js?ver=0.2' />"></script>
	<script type="text/javascript" src="<c:url value='/assets/js/lims.js?ver=0.3' />"></script>
	<script type="text/javascript" src="<c:url value='/assets/js/auigridcommon.js?ver=0.1' />"></script>
	<script src="<c:url value='/assets/js/jquery-ui.min.js' />"></script>
	<script src="<c:url value='/assets/js/interface.js' />"></script>

	<script type="text/javascript" src="<c:url value='/assets/js/jquery.cache.min.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/assets/js/timepicker.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/timepicker.css'/>">
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/common.css' />">
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/uicons-regular-straight.css'/>">
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/jquery-ui-timepicker-addon.css' />">
	<script src="<c:url value='/assets/js/jquery-ui-timepicker-addon.js' />"></script>
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/font.css' />">
	<link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/style.css' />">
	<style type="text/css">
		td { vertical-align: middle; }
	</style>
</head>
<body>
	<div id="sub_wrap" style="margin:0px;">
		<tiles:insertAttribute name="body" ignore="true"/>
	</div>
	<script>
		const lang = ${msg};
		const auth = "${UserMVo.authrtCd}";
	</script>
	<tiles:insertAttribute name="script" ignore="true"/>
</body>
</html>
