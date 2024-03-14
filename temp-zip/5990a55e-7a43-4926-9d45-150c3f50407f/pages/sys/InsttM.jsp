<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">Tiles 3 Test</tiles:putAttribute>
    <tiles:putAttribute name="body">

<article class="ctsInnerArea">

	<div class="ctsInnerAreaTop">
		<div class="ctsInnerAreaTopLft">
			<h6><img class="imgAlign" src="/assets/image/titico.png">${msg.C000000412}</h6>
		</div>
		<div class="ctsInnerAreaTopRit">
			<input type="button" id="btn_select" class="btn3" value="${msg.C000000002}">
		</div>
	</div>
	<!-- Main content -->
    <section class="content">
		<div id="main_wrap">
			<div id="top_wrap">
			<form id="searchFrm">
				<table class="tb3">
					<tr>
						<th class="taCt vaMd">${msg.C000000413}</th>
						<td><input type="text" name="inspctInsttNm" class="wd20p" style="min-width:10em;"></td>
					</tr>
				</table>
			</form>
				<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
				<div id="insttMList" class="mgT15" style="width:100%; height:150px; margin:0 auto;"></div>
			</div>
			<div id="middle_wrap">
				<br>
				<article id="tabMenuLst" class="tabMenuLst round skin-peter-river">
					<ul id="tabs">
				          <li id="tab1"  class="on">${msg.C000000422}</li>
				          <li id="tab2">${msg.C000000423}</li>
				          <li id="tab3">${msg.C000000424}</li>
				          <li id="tab4">${msg.C000000425}</li>
				    </ul>
				</article>
				<article id="tabCtsLst">

				   	<div id="tabCts1" class="tabCts">
						<!--  첫번째 탭에 포함된 article -->
						<article class="ctsInnerArea">

							<div class="ctsInnerAreaTop">
								<div class="ctsInnerAreaTopLft">
									<h6><img class="imgAlign" src="/assets/image/titico.png"> ${msg.C000000422}</h6>
								</div>
								<div class="ctsInnerAreaTopRit">
									<input type="button" id="btn_new" class="btn4" value="${msg.C000000426}">
									<input type="button" id="btnSave" class="btn1" value="${msg.C000000015}">
								</div>
							</div>

							<!-- Main content -->
							<section class="content">
								<div id="main_wrap">
									<div id="top_wrap">
										<form id="insttForm">
											<table class="tb3" id="userInfotbl">
												<tr>
													<th class="taCt vaMd necessary" style="min-width:150px;">${msg.C000000413}</th>
													<td class="wd33p"><input type="text" id="inspctInsttNm" name="inspctInsttNm"  class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000414}</th>
													<td class="wd33p"><input type="text" id="telno" name="telno" class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000415}</th>
													<td class="wd33p"><input type="text" id="fxnum" name="fxnum"  class="wd100p" style="min-width:10em;"></td>
												</tr>
												<tr>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000159}</th>
													<td class="wd33p">
														<input type="email" id="email" name="email" class="wd100p" style="min-width:10em;">
													</td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000416}</th>
													<td class="wd33p" colspan="3" >
														<input type="text" id="hmpg" name="hmpg" class="wd100p" style="min-width:10em;">
													</td>
												</tr>
												<tr>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000427}</th>
													<td colspan="5" >
														<input type="text" id="zip" name="zip" class="wd10p" style="min-width:10em;" readonly>
														<input id="adresBtn" type="button" class="inTableBtn inputBtn" value="${msg.C000000164}">
														<input type="text" id="adres" name="adres" class="wd35p" style="min-width:10em;" readonly>
														<input type="text" id="detailAdres" name="detailAdres" class="wd46p" style="min-width:10em;">
													</td>

												</tr>
												<tr>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000428}</th>
													<td colspan="5"><textarea id="dc" name="dc" class="wd100p" rows="3" style="min-width:10em;"></textarea></td>
												</tr>
												<tr>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000417}</th>
													<td><input type="text" id="delngBankNm" name="delngBankNm" class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000418}</th>
													<td><input type="text" id="acnutNo" name="acnutNo" class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000419}</th>
													<td><input type="text" id="dpstrNm" name="dpstrNm" class="wd100p" style="min-width:10em;"></td>
												</tr>
												<tr>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000420}</th>
													<td><input type="text" id="rprsntvNm" name="rprsntvNm" class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000421}</th>
													<td><input type="text" id="bsnmRegistNo" name="bsnmRegistNo" class="wd100p" style="min-width:10em;"></td>
													<th class="taCt vaMd" style="min-width:150px;">${msg.C000000065}</th>
													<td><label><input type="radio" id="use_y" name="useAt" value="Y" checked>${msg.C000000063}</label>
														<label><input type="radio" id="use_n" name="useAt" value="N" >${msg.C000000064}</label>
														</td>

												</tr>
												<tr>
													<th class="taCt vaMd">${msg.C000000429}</th>
													<td colspan="5"><textarea id="rqstdocRm" name="rqstdocRm" class="wd100p" rows="3" style="min-width:10em;"></textarea></td>

												</tr>
												<tr>
													<th class="taCt vaMd">${msg.C000000430}</th>
													<td colspan="5">
														<textarea id="prqudoRm" name="prqudoRm" class="wd100p" rows="3" style="min-width:10em;"></textarea>
														<input type="hidden" id="inspctInsttCode" name="inspctInsttCode" class="wd33p" style="min-width:10em;">
														<input type="hidden" id="bestInspctInsttCode" name="bestInspctInsttCode" class="wd33p" style="min-width:10em;">
														<input type="hidden" id="offcsFileSeqno" name="offcsFileSeqno" class="wd33p" style="min-width:10em;">
														<input type="hidden" id="logoFileSeqno" name="logoFileSeqno" class="wd33p" style="min-width:10em;">
														<input type="hidden" id="ocslFileSeqno" name="ocslFileSeqno" class="wd33p" style="min-width:10em;">
													</td>

												</tr>

											</table>
											<input type="hidden" id="crud" name="crud" value="C"/>
										</form>
									</div>
								</div>
							</section>
						</article>
					</div>

					<div id="tabCts2" class="tabCts">
						<!--  첫번째 탭에 포함된 article -->
						<article class="ctsInnerArea">

							<div class="ctsInnerAreaTop">
								<div class="ctsInnerAreaTopLft">
									<h6><img class="imgAlign" src="/assets/image/titico.png"> ${msg.C000000423}</h6>
								</div>
								<div class="ctsInnerAreaTopRit">
									<input type="button" id="btnSave_logo" value="${msg.C000000015}">
								</div>
							</div>

							<!-- Main content -->
							<section class="content">
								<div id="main_wrap">
									<div id="top_wrap">
										<table class="tb3">
											<tr>
												<th class="taCt vaMd">${msg.C000000431}</th>
												<td colspan="5">
														<!-- 로고파일첨부영역 -->
													<div id="dropzoneArea_logo"></div>
												</td>

											</tr>
										</table>
									</div>
								</div>
							</section>
						</article>
					</div>

					<div id="tabCts3" class="tabCts">
						<!--  첫번째 탭에 포함된 article -->
						<article class="ctsInnerArea">

							<div class="ctsInnerAreaTop">
								<div class="ctsInnerAreaTopLft">
									<h6><img class="imgAlign" src="/assets/image/titico.png"> ${msg.C000000424}</h6>
								</div>
								<div class="ctsInnerAreaTopRit">
									<input type="button" id="btnSave_offcs" value="${msg.C000000015}">
								</div>
							</div>

							<!-- Main content -->
							<section class="content">
								<div id="main_wrap">
									<div id="top_wrap">
										<table class="tb3">
											<tr>
												<th class="taCt vaMd">${msg.C000000432}</th>
												<td colspan="5">
														<!-- 로고파일첨부영역 -->
													<div id="dropzoneArea_offcs"></div>
												</td>

											</tr>
										</table>
									</div>
								</div>
							</section>
						</article>
					</div>

					<div id="tabCts4" class="tabCts">
						<!--  첫번째 탭에 포함된 article -->
						<article class="ctsInnerArea">

							<div class="ctsInnerAreaTop">
								<div class="ctsInnerAreaTopLft">
									<h6><img class="imgAlign" src="/assets/image/titico.png"> ${msg.C000000425}</h6>
								</div>
								<div class="ctsInnerAreaTopRit">
									<input type="button" id="btnSave_ocsl" value="${msg.C000000015}">
								</div>
							</div>

							<!-- Main content -->
							<section class="content">
								<div id="main_wrap">
									<div id="top_wrap">
										<table class="tb3">
											<tr>
												<th class="taCt vaMd">${msg.C000000433}</th>
												<td colspan="5">
														<!-- 로고파일첨부영역 -->
													<div id="dropzoneArea_ocsl"></div>
												</td>

											</tr>
										</table>
									</div>
								</div>
							</section>
						</article>
					</div>
				</article>
			</div>

			<div id="bottom_wrap">

			</div>
		</div>
	</section>
</article>
<br>


<!--  body 끝 -->
    </tiles:putAttribute>
    <tiles:putAttribute name="script">
<!--  script 시작 -->
		<script>
		// AUIGrid 생성 후 반환 ID
		var insttMList = 'insttMList';
		var searchFrm = 'searchFrm';
		var url = "<c:url value='/sys/getInsttMList.lims'/>";
		////그리드 정의
		var col = [];
		var dropzoneArea_logo;
		var dropzoneArea_offcs;
		var dropzoneArea_ocsl;



		$(function() {
			//그리드 셋팅
			setInsttGrid();

			//그리드 이벤트
			setInsttGridEvent();

			//버튼 이벤트
			setButtonEvent();

			$("#tab1").click();
		});




		//그리드 셋팅
		function setInsttGrid(){
			auigridCol(col);
			col.addColumnCustom("inspctInsttCode","기관 코드","*",false);
			col.addColumnCustom("bestInspctInsttCode","상위기관 코드","*",false);
			col.addColumnCustom("inspctInsttNm","${msg.C000000413}","9%",true);
			col.addColumnCustom("telno","${msg.C000000414}","9%",true);
			col.addColumnCustom("fxnum","${msg.C000000415}","9%",true);
			col.addColumnCustom("email","${msg.C000000159}","10%",true);
			col.addColumnCustom("hmpg","${msg.C000000416}","12%",true);
			col.addColumnCustom("delngBankNm","${msg.C000000417}","8%",true);
			col.addColumnCustom("acnutNo","${msg.C000000418}","8%",true);
			col.addColumnCustom("dpstrNm","${msg.C0000004196}","10%",true);
			col.addColumnCustom("rprsntvNm","${msg.C000000420}","10%",true);
			col.addColumnCustom("bsnmRegistNo","${msg.C000000421}","*",true);
			col.addColumnCustom("useAt","${msg.C000000065}","8%",true,false);

			dropzoneArea_ocsl = DDFC.bind().EventHandler("dropzoneArea_ocsl", { btnId : "#btnSave_ocsl", maxFiles : 1, lang : "${msg.C000000118}" } );
			dropzoneArea_offcs = DDFC.bind().EventHandler("dropzoneArea_offcs", { btnId : "#btnSave_offcs", maxFiles : 1, lang : "${msg.C000000118}" } );
			dropzoneArea_logo = DDFC.bind().EventHandler("dropzoneArea_logo", { btnId : "#btnSave_logo", maxFiles : 1, lang : "${msg.C000000118}" } );

			insttMList = createAUIGrid(col, insttMList);
			// 그리드 리사이즈.
			gridResize([insttMList]);

			AUIGrid.bind(insttMList, "ready", function(event) {
				gridColResize([insttMList],"2");	// 1, 2가 있으니 화면에 맞게 적용
			});
		}

		//그리드 이벤트
		function setInsttGridEvent(){
			AUIGrid.bind(insttMList, "cellClick", function(event) {
				console.log(event);
				// flag : update로 설정
				$('#crud').val('U');
				$('#inspctInsttNm').val(event.item.inspctInsttNm);
				$('#telno').val(event.item.telno);
// 				$('#user_id').prop("readonly", true);	// 아이디 수정 불가
				$('#fxnum').val(event.item.fxnum);
				$("#hmpg").val(event.item.hmpg);
				$('#email').val(event.item.email);
				$("#zip").val(event.item.zip);
				$("#adres").val(event.item.adres);
				$("#detailAdres").val(event.item.detailAdres);
				$("#dc").val(event.item.dc);
				$("#delngBankNm").val(event.item.delngBankNm);
				$("#acnutNo").val(event.item.acnutNo);
				$("#dpstrNm").val(event.item.dpstrNm);
				$("#rprsntvNm").val(event.item.rprsntvNm);
				$("#bsnmRegistNo").val(event.item.bsnmRegistNo);
				$("#rqstdocRm").val(event.item.rqstdocRm);
				$("#prqudoRm").val(event.item.prqudoRm);
				$("#insttForm #inspctInsttCode").val(event.item.inspctInsttCode);
				$("#insttForm #bestInspctInsttCode").val(event.item.bestInspctInsttCode);
				$("#offcsFileSeqno").val(event.item.offcsFileSeqno);
				$("#logoFileSeqno").val(event.item.logoFileSeqno);
				$("#ocslFileSeqno").val(event.item.ocslFileSeqno);

				if(event.item.useAt == 'Y')
					$("#use_y").val(event.item.useAt).prop("checked", true);

				else
					$("#use_n").val(event.item.useAt).prop("checked", true);

				dropzoneArea_logo.getFiles($("#logoFileSeqno").val());
				dropzoneArea_offcs.getFiles($("#offcsFileSeqno").val());
				dropzoneArea_ocsl.getFiles($("#ocslFileSeqno").val());
			});
		}

		//버튼 이벤트
		function setButtonEvent(){
			// 주소찾기 클릭 이벤트
			$('#adresBtn').click(function(){
				dialogAddress('zip', 'adres');
			});
			// 신규 클릭 이벤트
			$('#btn_new').click(function(){
				// flag : create 변경
				$('#crud').val('C');
				$('#user_id').prop("readonly", false);	// 아이디 수정 가능
				// 폼 초기화
				$('#insttForm')[0].reset();
				$("#dropzoneArea_offcs").clear();

// 				ajaxJsonParam("/sys/contextDestroyed.lims", null, null);
			});

			$("#btn_select").click(function(){
				searchInstt();
// 				mailSender("jessic32@naver.com", "jessic32@naver.com", "테스트용", "<a href='http://www.naver.com'> 네이버 </a>");
			});

			$('#btnSave').click(function(){
				if(!validLen('bsnmRegistNo', 10, '사업자 등록 번호')){
					return false;
				}
				if(!validLen('rprsntvNm', 5, '대표자 명')){
					return false;
				}

				dataSave();

		    });

			// 엔터키 이벤트
			$("input").keypress(function (e) {
		        if (e.which == 13){
		        	searchInstt(e.which);
		        }
			});

			$("#btnSave_offcs").click(function(){
				fileSave(dropzoneArea_offcs, "offcsFileSeqno");
			});

			$("#btnSave_logo").click(function(){
				fileSave(dropzoneArea_logo, "logoFileSeqno");
			});

			$("#btnSave_ocsl").click(function(){
				fileSave(dropzoneArea_ocsl, "ocslFileSeqno");
			});
		}

		//조회 함수
		function searchInstt(keyCode) {
			if(keyCode != undefined && keyCode == 13){
				searchInstt();
			}else {
				if(keyCode == undefined) {
					 ajaxJsonForm(url, searchFrm, function (data) {
						AUIGrid.setGridData(insttMList, data);
					});
// 					var min = "1"
// 					var time = "10 0/"+min + " * * * ?";
// 					console.log("time : " , time);
// 					ajaxJsonParam(url, {"email" : time}, function(data){
// 						alert("시작");
// 					});
				}
			}
		}

		//1번 저장 함수(필수사항 체크) -> 파일 저장 -> 데이터 저장
		function validationCheck(){
			var count = 0;

			// 폼 하위 요소중 input 중에 하나라도 입력안된거 있으면 카운트 상승
				$('#insttForm').find('input').each(function(){
					var nm = $(this).attr("name");
		    	if( nm == "inspctInsttNm"){
 				    if(!$(this).prop('required')){
				    		if($(this).val() == '') {
				    			count ++;
				    			return;
					    	}
					    }
			    	}
				});
				return count;
		}

		//2번 파일 저장 함수
		function fileSave(dropzoneArea, selector){
			var files = dropzoneArea.getNewFiles();
			var files_cnt = files.length;
			selector = typeof selector != "string" ? undefined : selector[0] == "#" ? selector : "#"+selector;

			if (files){
				dropzoneArea.on("uploadComplete", function(event, fileIdx){
					$(selector).val(fileIdx);
					dataSave();
				});
			}else{
				alert("${msg.C000000434}");
			}
		}


		//3번 데이터 저장
		function dataSave(){
			var saveUrl = "<c:url value='/sys/insInsttM.lims'/>";
			var updateUrl = "<c:url value='/sys/updInsttM.lims'/>";
			if($('#crud').val() =='C'){
				ajaxJsonForm(saveUrl, 'insttForm', function (data) {
						if(data == 0 ){
							alert('${msg.C000000070}');
						}else{
		 					alert('${msg.C000000071}');
		 					$('#insttForm')[0].reset(); // 폼 초기화
		 					searchInstt(); // 그리드 초기화
						}
	 				});
			}else if($('#crud').val() =='U'){
				ajaxJsonForm(updateUrl, 'insttForm', function (data) {
 					alert('${msg.C000000071}');
					$('#insttForm')[0].reset(); // 폼 초기화
					searchInstt(); // 그리드 초기화
 				});
			}
		}

		//최대길이값 제한거는 함수
		function validLen(selector, len, name){
			var val = $("#"+selector).val();
			if(val.length > len){
				alert(name+"${msg.C000000435}");
				return false;
			}else if(val.length == 0){
				alert(name+"${msg.C000000436}");
				return false;
			}

			return true;
		}
		</script>
<!--  script 끝 -->
	</tiles:putAttribute>

</tiles:insertDefinition>
