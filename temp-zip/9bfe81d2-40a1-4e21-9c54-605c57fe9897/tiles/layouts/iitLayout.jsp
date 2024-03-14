<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE html>

<html>
<head>
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <title>COM</title>
    <link href="<c:url value='/assets/logo/favicon.ico' />" rel="icon" type="image/x-icon" />
    <link href="<c:url value='/assets/logo/favicon.ico' />" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/common.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/style.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/font.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/uicons-regular-straight.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/jquery-ui.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/common.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/dropzone/basic.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/dropzone/dropzone.css' />">
    <link href="<c:url value='/AUIGrid/AUIGrid_style.css' />"  rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/fullcalendar/fullcalendar.min.css'/>">
    <link rel="stylesheet" media="print" href="<c:url value='/assets/stylesheet/fullcalendar/fullcalendar.print.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/AUIGrid_style.css' />">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/crownix-viewer.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/timepicker.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/jquery-ui-timepicker-addon.css' />">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
    <script src="<c:url value='/assets/js/jquery-3.2.1.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGridLicense.js' />"></script>
    <!-- 실제적인 AUIGrid 라이브러리입니다. 그리드 출력을 위해 꼭 삽입하십시오.-->
    <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGrid.js' />"></script>

    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/css/basic02_style.css' />">

    <!-- toastr lims.js 보다 위에서 먼저 load되야합니다.-->
    <link rel="stylesheet" href="<c:url value='/assets/js/toastr/toastr.min.css' />">
    <script  type="text/javascript" src="<c:url value='/assets/js/toastr/toastr.min.js'/>"></script>

    <!-- AUIGrid PDFkit 라이브러리입니다. -->
    <script type="text/javascript" src="<c:url value='/AUIGrid/AUIGrid.pdfkit.js' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/lims.js?ver=0.5' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/comm/lims.pop.js?ver=230306' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/userdialog.js?ver=0.4' />"></script>
    <script src="<c:url value='/assets/js/jquery-ui.min.js' />"></script>
    <script src="<c:url value='/assets/js/interface.js' />"></script>

    <script type="text/javascript" src="<c:url value='/assets/js/dropzone/dropzone.js' />"></script>
    <!-- AUIGrid 메세지 파일 - 한글 -->
    <script type="text/javascript" src="<c:url value='/AUIGrid/messages/AUIGrid.messages.kr.js' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/auigridcommon.js?ver=0.1' />"></script>

    <script type="text/javascript" src="<c:url value='/assets/js/fileCommon.js' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/FileSaver.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/all.js?ver=0.2' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/crownix-viewer.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/crownix-invoker.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/jquery.scannerdetection.js' />"></script>
    <script type="text/javascript" src="<c:url value='/assets/js/jquery.cache.min.js'/>"></script>
    <!-- <script type="text/javascript" src="<c:url value='/assets/js/fullcalendar/fullcalendar.min.js'/>"></script> -->
    <script type="text/javascript" src="<c:url value='/assets/js/timepicker.js'/>"></script>
    <script src="<c:url value='/assets/js/jquery-ui-timepicker-addon.js' />"></script>
    <script src="<c:url value='/assets/js/Chart.min.js' />"></script>
    <script type="text/javascript" src="/assets/js/chartZoom.js"></script>


    <!-- select box 검색 plugin -->
    <script type="text/javascript"  src="/assets/js/select2/select2.full.min.js"></script>
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/select2/select2Custom.css'/>">

    <!-- Spread JS -->
    <script type="text/javascript" src="<c:url value='/Spread/gc.spread.sheets.all.16.0.4.min.js' />"></script>
    <link rel="stylesheet" href="<c:url value='/assets/stylesheet/gc.spread.sheets.16.0.4.css' />">
    <tiles:insertAttribute name="head"/>

    <style type="text/css">
        td {
            vertical-align: middle;
        }
        <tiles:insertAttribute name="style"/>
    </style>
</head>
<body style="background: #fff;">

<!-- 사용자 권한에 따른 사업장 selectBox를 제어하기위한 element입니다. -->
<%--<div id="userAuthorDiv">
    <input type="hidden" id="layoutUserBplcCode" value="${UserMVo.bplcCd}">
    <input type="hidden" id="layoutUserAuthorSeCode" value="${UserMVo.authorSeCode}">
    <input type="hidden" id="layoutReqUrl" value="${reqUrl}">
</div>--%>



<tiles:insertAttribute name="header" />
<div id="sub_wrap">
    <tiles:insertAttribute name="dashboard" />
    <tiles:insertAttribute name="menu" />
    <tiles:insertAttribute name="body" />
</div>

<!-- 로딩 animation -->
<div id="wrap-loading"style="display:none">
    <div class="load-wrapp">
        <div class="load-4">
            <div class="ring-1"></div>
        </div>
    </div>
</div>

<tiles:insertAttribute name="footer" />
</body>

<script>
  <tiles:insertAttribute name="script" />
  let editBeforeRow = {};
  const bplcCd = "${UserMVo.bplcCd}"; //사업장코드
  const deptSn = "${UserMVo.deptSn}"; //부서
  const lgnId = "${UserMVo.lgnId}"; //Login ID
  const authrtCd = "${UserMVo.authrtCd}"; //Login ID
  const menuSn = "${menuSn}"; //메뉴 시퀀스
  const aprvDmndYn = "${aprvDmndYn}";
  const prgrsSittnSn = "${prgrsSittnSn}";
  const hghrkDptSn = "${UserMVo.hghrkDeptSn}";
  const userSn = "${UserMVo.userSn}";
  const menuUrl = "${menuUrl}";
  const lang = ${msg};
</script>
</html>
