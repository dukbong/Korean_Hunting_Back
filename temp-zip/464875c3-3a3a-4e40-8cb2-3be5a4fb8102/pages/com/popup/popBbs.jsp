<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!-- title -->
<div class="popup popContentHalf wd50p">
	<article class="ctsInnerArea mgL10 mgR10">
		<div class="ctsInnerAreaTop">
			<div class="ctsInnerAreaTopLft">
				<h2><i class="fi-rr-volume"></i> 공지사항</h2>
			</div>
			<div class="ctsInnerAreaTopRit">
			</div>
		</div>
		<section class='content'>
			<div id='main_wrap'>
				<div id="top_wrap"class ='subCon1'>
					<form id='bbsForm' name='bbsForm'>
						<table class='tb3'>
							<tr>
								<th class='taCt vaMd' style="width: 3%;">제목</th>
								<td>
									<%--<input type='text' name='popSj' id='popSj' class='wd100p' value="${detail.SJ }" readonly>--%>
								</td>
								<th class='taCt vaMd' style="width: 3%;">작성자</th>
								<td class='wd10p'>
									<%--<input type='text' name='popWrterNm' id='popWrterNm' class='wd100p' value="${detail.WRTER_NM }" readonly>--%>
								</td>
							</tr>
							<tr>
								<th class='taCt vaMd' style="width: 3%;">이메일</th>
								<td class='wd10p'>
									<%--<input type='text' name='popEmail' id='popEmail' class='wd100p' value="${detail.EMAIL }" readonly>--%>
								</td>
								<th class='taCt vaMd' style="width: 3%;">작성일</th>
								<td class='wd10p'>
									<%--<input type='text' name='popWritngDe' id='popWritngDe' class='wd100p' value="${detail.WRITNG_DE }" readonly>--%>
								</td>
							</tr>
							<tr>
								<th class='taCt vaMd' style="width: 3%;">내용</th>
								<td class='wd10p' colspan='5'>
									<div id='popCn' name='popCn' style='min-width: 10em; border: 1px solid #dcdcdc;  overflow-y:scroll; height:300px;  width:100%;  text-align:left;'>
										<!-- 			 				<textarea cols='100' rows='12' id='popCn' name='popCn' class='wd100p' style='min-width:10em;' value="" readonly> </textarea> -->
									</div>
								</td>
							</tr>
							<tr>
								<th class='taCt vaMd' style="width: 3%;">첨부파일</th>
								<td colspan='7'>
									<div id='dropzoneArea'></div>
									<%--<input type='hidden' id='atchmnflSeqno' name='atchmnflSeqno' class='wd33p' style='min-width:10em;' value="${detail.ATCHMNFL_SEQNO }">--%>
								</td>
							</tr>
						</table>
						<%--<input type="hidden" id="sntncSeqno" name="sntncSeqno" value="${detail.SNTNC_SEQNO }">--%>
					</form>
				</div>
			</div>

			<div class ="subCon1">
				<div class ="btnWrap mgT20 " style="position: static;">
					<button type="button" id="btn_bbsMore" style="margin: auto;display: block;" class="btn5" onclick="popClose()">닫기</button>
				</div>
			</div>
			<label for="notpopup" id="notpopuplabel" name="notpopuplabel" style="float: right; font-size: small;">
				<input type="checkbox" id="notpopup" name='notpopup' onclick="closePop()" style="vertical-align: middle;"> 오늘 하루동안 열지 않기
			</label>
		</section>
	</article>


</div>
<%--</tiles:putAttribute>
<tiles:putAttribute name="script">--%>
<style>
	#wrap {
		padding: 15px;
		height: 100%;
		min-width: 500px;
		min-height: 540px;
	}
	.tb3 th {
		/*     width: 20px; */
		text-align: left;
		background: #f8f8f8;
		text-indent: 5px;
	}
	#popCn img{
		float: left;
		max-width: 330px;
		height : auto;
	}
</style>
<script>
	//AUIGrid 생성 후 반환 ID
	/*var dropzoneArea;
    $(document).ready(function(){
        $('#popCn').html('${detail.CN}');
	dropzoneArea = DDFC.bind().EventHandler("dropzoneArea", { btnId : "#btnSave_applcntSignFileSeqno",'readOnly': true,lang : " "} );
	getFiles();
});

function closePop(){
    if ($("input:checkbox[id='notpopup']").is(":checked")){
    setCookie("popup"+$('#sntncSeqno').val(), "done", 1);
}
    self.close();
}

function setCookie(name, value, expiredays){
    var todayDate = new Date();
        todayDate.setDate (todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toUTCString() + ";";
}

function getFiles(){

	dropzoneArea.getFiles($("#atchmnflSeqno").val(),null,'N');
}*/

</script>
