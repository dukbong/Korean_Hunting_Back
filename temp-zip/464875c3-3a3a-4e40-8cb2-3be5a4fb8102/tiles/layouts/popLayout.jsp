<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>

<html>
 <head>
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <meta http-equiv="X-UA-Compatible" content="IE=Edge" charset="UTF-8">
  <title>COM</title>
  <link href="<c:url value='/assets/logo/favicon.ico' />" rel="icon" type="image/x-icon" />
<link href="<c:url value='/assets/logo/favicon.ico' />" rel="shortcut icon" type="image/x-icon" />
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/jquery-ui.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/common.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/uicons-regular-straight.css'/>">

  <script src="<c:url value='/assets/js/jquery-3.2.1.min.js' />"></script>

<!-- toastr lims.js 보다 위에서 먼저 load되야합니다.-->
<link rel="stylesheet" href="<c:url value='/assets/js/toastr/toastr.min.css' />">
<script  type="text/javascript" src="<c:url value='/assets/js/toastr/toastr.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/assets/js/lims.js?ver=0.4' />"></script>
  <script src="<c:url value='/assets/js/jquery-ui.min.js' />"></script>
  <script src="<c:url value='/assets/js/interface.js' />"></script>
   <!-- dropZone -->
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/dropzone/basic.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/dropzone/dropzone.css' />">
  <script type="text/javascript" src="<c:url value='/assets/js/dropzone/dropzone.js' />"></script>

  <!-- AUIGrid 테마 CSS 파일입니다. 그리드 출력을 위해 꼭 삽입하십시오. -->
  <!-- 원하는 테마가 있다면, 다른 파일로 교체 하십시오. -->
  <link href="<c:url value='/AUIGrid/AUIGrid_style.css' />" rel="stylesheet">
  <!-- AUIGrid 라이센스 파일입니다. 그리드 출력을 위해 꼭 삽입하십시오. -->
  <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGridLicense.js' />"></script>
  <!-- 실제적인 AUIGrid 라이브러리입니다. 그리드 출력을 위해 꼭 삽입하십시오.-->
  <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGrid.js' />"></script>
  <!-- AUIGrid PDFkit 라이브러리입니다. -->
   <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGrid.pdfkit.js' />"></script>
  <!-- AUIGrid 메세지 파일 - 한글 -->
  <script type="text/javascript" src="<c:url value='/AUIGrid/messages/AUIGrid.messages.kr.js' />"></script>
 <script type="text/javascript" src="<c:url value='/assets/js/auigridcommon.js?ver=0.1' />"></script>
     <script type="text/javascript" src="<c:url value='/assets/js/userdialog.js?ver=0.4' />"></script>
  <script type="text/javascript" src="<c:url value='/assets/js/fileCommon.js' />"></script>

  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/AUIGrid_style.css' />">

  <script type="text/javascript" src="<c:url value='/assets/js/jquery.cache.min.js'/>"></script>

  <!-- 풀 카렌다 -->
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/fullcalendar/fullcalendar.min.css'/>">
  <link rel="stylesheet"  media="print" href="<c:url value='/assets/stylesheet/fullcalendar/fullcalendar.print.min.css'/>">
  <script type="text/javascript" src="<c:url value='/assets/js/fullcalendar/fullcalendar.min.js'/>"></script>

  <!-- 시간 선택기! -->
  <script type="text/javascript" src="<c:url value='/assets/js/timepicker.js'/>"></script>
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/timepicker.css'/>">

  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/jquery-ui-timepicker-addon.css' />">
  <script src="<c:url value='/assets/js/jquery-ui-timepicker-addon.js' />"></script>
   <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/common.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/style.css' />">
  <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/font.css' />">

   <script src="/assets/js/Chart.min.js"></script>
   <script type="text/javascript" src="/assets/js/chartZoom.js"></script>

<!-- select box 검색 plugin -->
<script type="text/javascript"  src="/assets/js/select2/select2.full.min.js"></script>
<link rel="stylesheet" href="<c:url value='/assets/stylesheet/select2/select2Custom.css'/>">
	<style type="text/css">
		td { vertical-align: middle; }
	</style>
 </head>
<body>
    <!-- 사용자 권한에 따른 사업장 selectBox를 제어하기위한 element입니다. -->
    <div id="userAuthorDiv">
        <input type="hidden" id="layoutUserBplcCode" value="${UserMVo.bplcCode}">
        <input type="hidden" id="layoutUserAuthorSeCode" value="${UserMVo.authorSeCode}">
        <input type="hidden" id="layoutReqUrl" value="${reqUrl}">
    </div>

	<div id="sub_wrap" style="margin:0px;">
		<tiles:insertAttribute name="body" ignore="true"/>
	</div>

    <!-- 로딩 animation -->
    <div id="wrap-loading"style="display:none">
        <div class="load-wrapp">
            <div class="load-4">
                <div class="ring-1"></div>
            </div>
        </div>
    </div>

	<tiles:insertAttribute name="script" ignore="true"/>
</body>

</html>
