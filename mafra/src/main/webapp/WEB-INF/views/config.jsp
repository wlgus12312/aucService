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
<script src="https://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.sumoselect/3.0.2/sumoselect.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.sumoselect/3.0.2/jquery.sumoselect.min.js"></script>
 <title>농림축산 조회 설정</title>
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
				setUserConf();
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
	
	
	function setUserConf(){
			
		var userConf = results["user"][0];
		
		//공판
		var userMrkt = userConf["05"];		
		
		$.each(userMrkt, function(i){			
			$('#sumoSelectId1')[0].sumo.selectItem(userMrkt[i].sub_code);
		});
		
		//도매
		createCpr();
		var userCpr = userConf["06"];		
			
		$.each(userCpr, function(i){			
			$('#sumoSelectId2')[0].sumo.selectItem(userCpr[i].sub_code);
		});		
		
		//품목
		var userCode01 = userConf["01"];		
		
		$.each(userCode01, function(i){			
			$('#sumoSelectId3')[0].sumo.selectItem(userCode01[i].sub_code);
		});
		
		//품종
		createCode02();
		var userCode02 = userConf["02"];		
		
		$.each(userCode02, function(i){			
			$('#sumoSelectId4')[0].sumo.selectItem(userCode02[i].sub_code);
		});
		
		//포장
		var userCode03 = userConf["03"];		
		
		$.each(userCode03, function(i){			
			$('#sumoSelectId5')[0].sumo.selectItem(userCode03[i].sub_code);
		});
		
		//규격
		var userCode04 = userConf["04"];		
		
		$.each(userCode04, function(i){			
			$('#sumoSelectId6')[0].sumo.selectItem(userCode04[i].sub_code);
		});		
		

	}

	
	function configOk(){
	
		var selMrkt    = $('#sumoSelectId1').val();
		var selCpr     = $('#sumoSelectId2').val();
		var selCode01  = $('#sumoSelectId3').val();
		var selCode02  = $('#sumoSelectId4').val();
		var selCode03  = $('#sumoSelectId5').val();
		var selCode04  = $('#sumoSelectId6').val();
		
		//저장		
		$.ajax({
			url:'/mafra/configOk',
			contentType: 'application/json',
			type:'POST',
			dataType:'json',
			data:JSON.stringify({
			      selMrkt:selMrkt,
			      selCpr:selCpr,
			      selCode01:selCode01,
			      selCode02:selCode02,
			      selCode03:selCode03,
			      selCode04:selCode04
			     }),
			success:function(data) { 					
				//화면 종료
				window.open('about:blank', '_self').close();
			}
		});
		
	}
	
</script>
<style>
.detaleDiv {width:500px; height:500px;}

</style>
<body>
	<h1>상세조회 설정</h1>
	<div class="detaleDiv">
		<span>공판</span>
        <select id="sumoSelectId1" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
		<span>도매</span>
		<select id="sumoSelectId2" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
		<br/>
		<span>품목</span>
		<select id="sumoSelectId3" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
		<span>품종</span>
		<select id="sumoSelectId4" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
		<span>등급</span>
		<select id="sumoSelectId5" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
		<span>규격</span>
		<select id="sumoSelectId6" name="sumoSelectName" multiple="multiple" class="sumoselect_multiple">
		</select>
	</div>
	<div>
	    <input type="button" onclick="configOk();" value="확인">
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
		
			if($(".sumoselect_multiple")[i].length < 1 ){
				$(".sumoselect_multiple")[i].sumo.unSelectAll();
			}	
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