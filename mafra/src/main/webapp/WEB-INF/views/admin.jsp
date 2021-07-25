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
<title>농림축산 시세 관리자 페이지</title>
</head>
<script>
	
	function selectUser(){
	
		var selUser = $("#userId").val();
	
		$("#userList").html("");
		
		$.ajax({
			url:'/mafra/selectUser',
			type:'POST',
			dataType:'json',
			data:{
				selUser:selUser
			},
			cache : false,
			success:function(data) { 					
				//리턴 데이터
				results = data;
				
				var str     = '<tr>';
				
				$.each(results, function(i){
				
					var ynStr = '';
					if(results[i].YN == '1'){
						ynStr = '승인';		
					}else{
						ynStr = '미승인';
					}
									    
					str += '<td style="text-align:center;">' + results[i].ID + '</td><td style="text-align:center;">'
				                      + results[i].NAME    + '</td><td style="text-align:center;">'
				                      + results[i].IP      + '</td><td style="text-align:center;">'
						              + ynStr              + '</td><td style="text-align:center;">';
						              
						              if(results[i].YN == '0'){						              	
						              	str += '<input type="button" name="user" id="' + results[i].ID + '" value="승인하기"/></td><td style="text-align:center;">';
						              }else{
										str += '</td><td style="text-align:center;">';						              
						              }
						              
						              str += results[i].ST_DT.substr(0,10)   + '</td><td style="text-align:center;">'
						                   + results[i].ED_DT.substr(0,10)   + '</td>';
					str += '</tr>';
					});		
								
					$("#userList").append(str);
			   }
		 });
	}
	
	$(document).on("click", "#userList input[name*=user]", function() {
    	
    	var id = $(this).attr("id");
    	
    	$.ajax({
			url:'/mafra/updUserYn',
			type:'POST',
			dataType:'json',
			data:{
				selUserId:id
			},
			cache : false,
			success:function(data) { 					
				//리턴 데이터
				alert("승인처리 완료");
				
			}, complete:function() {
			    selectUser();
   			}
		 });
    	
    	
    	
    	
    	
	});

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
	  padding:20px;
	  color: #168;
	  border: 1px solid #168;
	  text-align: center;
	  border-collapse: collapse;
  } 
 .mafTable td{
	  border: 2px solid #168;
	  padding:10px;
	  text-align: left;
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
  }
</style>
<body>

<h1>관리자페이지</h1>
	
	<div>
		<input type="button" onclick="selectUser();" value="조회">
		<table class="mafTable">
            <tr style="text-align: center;">
                <td>
	                <label class="title">사용자 ID</label>
	                <input type="text" id="userId">
	            </td>
	        </tr>
	    </table>
	    <table class="mafTable2">
		    <thead>
                <tr style="text-align: center;">
                    <th>사용자 ID</th>
                    <th>사용자 명</th>
                    <th>IP 대역</th>
                    <th>승인여부</th>
                    <th>승인처리</th>
                    <th>시작일자</th>
                    <th>종료일자</th>
                </tr>
           </thead>
           <tbody id="userList">
           
           </tbody>
	    </table>
	</div>
	
</body>
</html>