<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>        
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.sumoselect/3.0.2/sumoselect.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.sumoselect/3.0.2/jquery.sumoselect.min.js"></script>
 <title>농림축산 상품 조회</title>
</head>
<script>
	
	$(window).load(function(){
		
		$.ajax({
			url:'/mafra/selectConfig',
			type:'POST',
			dataType:'json',
			cache : false,
			success:function(data) { 					
				//리턴 데이터
				results = data;
				
				var mrkt   = results["mrkt"];
				var code01 = results["01"];
				var code02 = results["02"];
				var code03 = results["03"];
				var code04 = results["04"];
																								
				$.each(mrkt, function(i){							
					$('#sumoSelectId1')[0].sumo.add(mrkt[i].PBLMNG_WHSAL_MRKT_CD, mrkt[i].PBLMNG_WHSAL_MRKT_NM);
				});				
				$.each(code01, function(i){							
					$('#sumoSelectId3')[0].sumo.add(code01[i].SUB_CODE, code01[i].SUB_CODE_NM);
				});
				$.each(code03, function(i){							
					$('#sumoSelectId5')[0].sumo.add(code03[i].SUB_CODE, code03[i].SUB_CODE_NM);
				});
				$.each(code04, function(i){							
					$('#sumoSelectId6')[0].sumo.add(code04[i].SUB_CODE, code04[i].SUB_CODE_NM);
				});	
				
			}, complete:function() {				
			}
		});		
	})


	function createCpr(){
				
		$('#sumoSelectId2').html('');
		$('#sumoSelectId2')[0].sumo.reload();
		
		var mrktValues = $('#sumoSelectId1').val();
				
		var cpr = results["cpr"];
		
		if(mrktValues == null){
			return true;
		}		
		
		$.each(cpr, function(i){						
			if(mrktValues.indexOf(cpr[i].PBLMNG_WHSAL_MRKT_CD) != -1){
				$('#sumoSelectId2')[0].sumo.add(cpr[i].CPR_CD, cpr[i].CPR_NM);
			}						
		});					
		
		$('#sumoSelectId2')[0].sumo.unSelectAll();
		
	}
	
	function createCode02(){
	
		$('#sumoSelectId4').html('');
		$('#sumoSelectId4')[0].sumo.reload();
		
		var code01Values = $('#sumoSelectId3').val();
				
		var code02 = results["02"];
		
		if(code01Values == null){
			return true;
		}		
		
		$.each(code02, function(i){			
						
			if(code01Values.indexOf(code02[i].SUB_CODE.substring(0,4)) != -1){
				$('#sumoSelectId4')[0].sumo.add(code02[i].SUB_CODE, code02[i].SUB_CODE_NM);
			}					
		});
		
		$('#sumoSelectId4')[0].sumo.unSelectAll();
	}
	
	function selectMaf(){
	
		var selDate = $("#mafDate").val().replaceAll("-", "");
		
		var code05 = $('#sumoSelectId1').val();
		var code06 = $('#sumoSelectId2').val();
		var code01 = $('#sumoSelectId3').val();
		var code02 = $('#sumoSelectId4').val();
		var code03 = $('#sumoSelectId5').val();
		var code04 = $('#sumoSelectId6').val();
		
		$("#defMafList").html("");
					
		$.ajax({
			url:'/mafra/selectDefMaf',
			contentType: 'application/json',
			type:'POST',
			dataType:'json',
			data:JSON.stringify({
			    selDate:selDate,
				code01:code01,
				code02:code02,
				code03:code03,
				code04:code04,
				code05:code05,
				code06:code06
			}),
			success:function(data) { 					
				//리턴 데이터
				resultsList = data;
				
				if(resultsList.length < 1){
					alert("조회된 내용이 없습니다.");
					return false;
				}
										
				var str     = '<tr>';
									
				$.each(resultsList, function(i){
					var qy = resultsList[i].DELNGBUNDLE_QY;	
					if(qy == 0) qy = 1; 					    
					str += '<td style="text-align:center;">' + resultsList[i].AUCNG_DE.substr(0,4) + '-' + resultsList[i].AUCNG_DE.substr(4,2) + '-' + resultsList[i].AUCNG_DE.substr(6,2) + '</td><td style="text-align:center;">'
				                      + resultsList[i].CPR_NM    + '</td><td style="text-align:center;">'
				                      + resultsList[i].PRDLST_NM + '</td><td style="text-align:center;">'
						              + resultsList[i].SPCIES_NM + '</td><td style="text-align:center;">'
						              + resultsList[i].GRAD      + '</td><td style="text-align:right;">'
						              + priceString(resultsList[i].AVRG_AMT)  + '</td><td style="text-align:center;">'
						              + resultsList[i].DELNGBUNDLE_QY + " " + resultsList[i].STNDRD + '</td><td style="text-align:right;">'
						              + priceString(Math.round((resultsList[i].AVRG_AMT / (qy * 10)))) + '</td>';
					    str += '</tr>';
					    
					});					
					$("#defMafList").append(str);
				
			}
		});	
		
	}
		
	function priceString(amt){		
		return amt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
	
	
	
</script>
<style>

 html,body {
    position: relative;
    height: 100%;
  }

  body {
    background: white;
    font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 14px;
    color: #000;
    margin: 0;
    padding: 0;
  }
  
  .mafTable{
  	  width:100%;
	  padding:10px;
	  color: #168;
	  border: 1px solid #168;
	  text-align: center;
	  border-collapse: collapse;
  }
 
 .mafTable td{
	  border: 1px solid #168;
	  text-align: left;
  }
 
  .title {  	
	  padding:10px;
	  color: #168;
	  text-align: center;
  }
  
  .mafTable2{
  	  width:100%;
	  padding:10px;
	  color: #168;
	  border: 1px solid #168;
	  text-align: center;
	  border-collapse: collapse;
  }
  
  .mafTable2, tr td {
    border: 1px solid #168;
  }
  mafbody {
    display: block;
    height: 50px;
    overflow: auto;
  }
  .mafTable2 thead, tbody tr {
    display: table;
    width: 100%;
    table-layout: fixed;/* even columns width , fix width of table too*/
  }

  .mafTable2 thead {
  	height: 40px;
  	border: 1px solid #168;
  }
  
  .mafTable2 tbody {
    display: block;
    max-height: 450px;
    overflow-y: scroll;
    overflow-x:hidden;
  }
 
  .mafTable2 {
    width: 100%;
  }
  

</style>
<body>
	<div> 
		<input type="button" onclick="selectMaf();" value="조회">
		<table class="mafTable">
            <tr style="text-align: center;">
                <td>
	                <label class="title">거래일자</label>
	                <input type="date" id="mafDate">
	            </td>
	            <td>
					<label class="title">공판</label>
			        <select id="sumoSelectId1" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
				<td>
					<label class="title">도매</label>
					<select id="sumoSelectId2" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
			</tr>
			<tr style="text-align: center;">
			    <td></td>
				<td>
					<label class="title">품목</label>
					<select id="sumoSelectId3" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
				<td>
					<span class="title">품종</span>
					<select id="sumoSelectId4" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
			</tr>
			<tr style="text-align: center;">
				<td></td>
				<td>
					<label class="title">등급</label>
					<select id="sumoSelectId5" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
				<td>
					<label class="title">규격</label>
					<select id="sumoSelectId6" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
					</select>
				</td>
            </tr>
	    </table>
	    <table class="mafTable2">
		    <thead>
                <tr style="text-align: center;">
                    <th>거래일자</th>
                    <th>도매</th>
                    <th>품목명</th>
                    <th>품종명</th>
                    <th>등급</th>
                    <th>경락평균</th>
                    <th>경락단위</th>
                    <th>100g 가격</th>
                </tr>
           </thead>
           <tbody class="mafbody" id="defMafList">
           
           </tbody>
	    </table>
	</div>


</body>

<script>

	$('.sumoselect_multiple').SumoSelect({
		placeholder: '선택해주세요',
		captionFormat: '{0}',
		captionFormatAllSelected: '{0} 전체선택',
		okCancelInMulti: true, 
		selectAll:true,
		locale :  ['OK', 'Cancel', '전체'],
	});
	
	
	$( document ).ready(function() {
		
		var sumoCnt = $(".sumoselect_multiple").length;
		
		for(i=0; i<sumoCnt; i++){
		
			$(".sumoselect_multiple")[i].sumo.unSelectAll();
			
		}
		
	});
	

	$("#sumoSelectId1 ~ .optWrapper .MultiControls .btnOk").on('click', function(){
		createCpr();
	})	
	
	
	$("#sumoSelectId3 ~ .optWrapper .MultiControls .btnOk").on('click', function(){
		createCode02();
	})
</script>

</html>