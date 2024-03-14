<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
<tiles:putAttribute name="title">${msg.C000001178}</tiles:putAttribute><!-- 휴일 관리 -->
	<tiles:putAttribute name="body">
        <div class="subContent">
			<div class="subCon1">
                <h2><i class="fi-rr-apps"></i>${msg.C000001178}</h2> <!-- 휴일 관리 -->
              <div class="btnWrap">
                <button type="button" class="etcBtn" id="prevYear" title=${msg.C000001330}><<</button><!-- 년 이동 -->
                <button type="button" class="etcBtn" id="prev" title=${msg.C000001331}><</button><!-- 월 이동 -->
                <button type="button" class="etcBtn" id="next" title=${msg.C000001331}>></button><!-- 월 이동 -->
                <button type="button" class="etcBtn" id="nextYear" title=${msg.C000001330}>>></button><!-- 년 이동 -->
                <button type="button" class="etcBtn" id="today">${msg.C000000974}</button> <!-- 오늘 -->
              </div>
                    <form id="SearchForm" onsubmit="return false;">
                        <table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
                            <colgroup>
				                <col style="width:10%"></col>
				                <col style="width:15%"></col>
				                <col style="width:10%"></col>
				                <col style="width:15%"></col>
				                <col style="width:10%"></col>
				                <col style="width:15%"></col>
				                <col style="width:10%"></col>
				                <col style="width:15%"></col>
                            </colgroup>
                            <tr>
                                <th>${msg.C000000396}</th>  <!-- 연도 -->
			                    <td><input type="text" id="year" name="year" readonly></td>
			
			                    <th>${msg.C000000419}</th> <!-- 월 -->
			                    <td><input type="text" id="month" name="month" readonly></td>
                            </tr>
                        </table>
                    </form>	  
            </div>
                <div id="orgGrid" class="mgT15" style="width:100%; height:300px; margin:0 auto;"></div>
        </div>
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script>
	 		var mapyy = "${map.year}";
			var mapmm = "${map.month}";
		</script>
		<style>
			.my-sunday-style {
			  color: red;
			}
			.my-setday-style {
			  color: blue;
			}
			.smpColctPlan-div {
			  color: black;
       		  text-align: left;
			}
			.holiday-style {
				color: red;
			}
		  </style>
		<script src="/viewScript/wrk/HldyM.js?ver=20230605"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
