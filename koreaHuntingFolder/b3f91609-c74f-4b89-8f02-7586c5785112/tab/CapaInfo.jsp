<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!-- 

 이건에이치티엠엘함정테스트
두번째에이치티엠엘
 -->
 
 <%-- 
 이건제이에스피두번째함정테스트
 두번째제이에스피
  --%>
  
  <!-- 한줄테스트 -->
  <%-- 제이에스피한중테스트 --%>
				<div id="ptc_pg3_1" class="ptc_pg3_1 subCon1">
						<h2><i class="fi-rr-apps"></i>CAPA 정보</h2>
						<form id=${ptc_pg3_1}Frm name=${ptc_pg3_1}Frm onsubmit="return false">
							<table cellpadding="0" cellspacing="0" width="100%" class="subTable1 mgT15">
								<tr>
									<th class="taCt vaMd" style="min-width:120px;">CAPA 제목</th>
									<td class="wd33p" colspan="3"><input type="text" id=${ptc_pg3_1}sj name="sj"  class="wd100p readonlyClass" style="min-width:10em;" readonly></td>
								</tr>
								<tr>
									<th class="taCt vaMd" style="min-width:120px;">QMS 관리번호</th>
									<td class="wd33p"><input type="text" id=${ptc_pg3_1}manageNo name="manageNo"  class="wd100p readonlyClass" style="min-width:10em;" readonly></td>
									<th class="taCt vaMd" style="min-width:120px;">CAPA 계획서 기한</th>
									<td class="wd33p"><input type="text" id=${ptc_pg3_1}actplnTmlmt name="actplnTmlmt"  class="wd100p readonlyClass" style="min-width:10em;" readonly></td>
								</tr>
								<tr>
									<th class="taCt vaMd" style="min-width:120px;">원인 조사 수행 여부</th>
									<td class="wd33p">
										<label><input type="radio" class = "disabledClass" id=${ptc_pg3_1}causeExaminExcAtY name="causeExaminExcAt" value="Y" checked>&nbsp;YES</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" class = "disabledClass" id=${ptc_pg3_1}causeExaminExcAtN name="causeExaminExcAt" value="N" >&nbsp;NO</label>
									</td>
									<th class="taCt vaMd" style="min-width:120px;">원인 조사 QMS 관리번호</th>
									<td class="wd33p"><input type="text" id=${ptc_pg3_1}causeManageNo name="causeManageNo"  class="wd100p readonlyClass" style="min-width:10em;" readonly></td>
								</tr>
							</table>
							
							<div class="ctsInnerAreaTop" style="width:100%;"  id=${ptc_pg3_1}CAPA >
								<h3>시정 조치 및 에방 조치 </h3>
									<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
								<div id=${ptc_pg3_1}ptcGrid_Detail3_1 style="width:100%; height:300px; margin:0 auto;"></div>
							</div>
						</form>
				</div>
