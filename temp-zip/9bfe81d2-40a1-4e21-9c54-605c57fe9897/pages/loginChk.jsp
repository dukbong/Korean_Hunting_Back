<%@page import="lims.util.Util"%>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="login-definition">
    <tiles:putAttribute name="body">
        <form  class="iLogin" id="frmLogin" name="frmLogin" action="./loginCheck.lims" method="POST">
            <input  type="hidden"  id="lgnId" name="lgnId" value="${lgnId}">
            <input  type="hidden"  id="pswd" name="pswd" value="${pswd}">
            <input  type="hidden"  id="langCd" name="langCd" value="${locale}">
        </form>
    </tiles:putAttribute>
    <tiles:putAttribute name="script">
        <script type="text/javascript" src="<c:url value='/viewScript/loginChk.js?ver=20230520'/>"/>
    </tiles:putAttribute>
</tiles:insertDefinition>
