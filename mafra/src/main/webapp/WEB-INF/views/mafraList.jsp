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
<!-- 스와이프 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.js"></script>

<script>

	mafraCnt = 120;
	udpCnt   = 0;
	
	//시세 조회	
	$(window).load(function(){	
	
			$.ajax({
				url:'/mafra/selectMafraList',
				type:'POST',
				dataType:'json',
				cache : false,
				success:function(data) { 					
					//리턴 데이터
					results = data;
					
					if(results.length < 1){
						alert("조회된 내용이 없습니다.");
						return false;
					}else{
						updateMafra();
					}
					
				}, complete:function() {
				
					if(results.length > 0){
						listUp();
					}
					
   				}
			});
	})
			
	function listUp(){
		udpInterval = setInterval(updateMafra, 5000);
	}
	
	function appendSw(str){
	  	swiper.appendSlide('<div class="swiper-slide">' + str + '</div>');
	}
	
	function updateMafra(){
					 
		 var cnt = 0;	
		 var str = "";		 
		 var eCnt = 0;
		 
		 for(udpCnt; udpCnt < results.length; udpCnt++){
		 
		 	var qy = results[udpCnt].DELNGBUNDLE_QY;
		 	if(qy == 0) qy = 1;
		 	
			str  +='<div name="data" value1="'+ results[udpCnt].PRDLST_CD +'" value2="'+ results[udpCnt].SPCIES_CD +'" value3="' + results[udpCnt].PRDLST_NM  + '" value4="' + results[udpCnt].SPCIES_NM + '" style="width:100%; height:13%; border: 0.5px groove white; display=table;">'
			     + '<div class="div_name"  ><span class="mafspan" style="margin-left:10px;">' + results[udpCnt].PRDLST_NM                + '</span></div>'
			     + '<div class="div_name2" ><span class="  mafspan" style="margin-left:10px;">' + results[udpCnt].SPCIES_NM              + '</span></div>'
			     + '<div class="div_100amt"><span class="mafspan">' + priceString(Math.round((results[udpCnt].AVRG_AMT / (qy * 10))))    + '</span></div>'
			     + '<div class="div_amt"  style="text-align:right;" ><span class="mafspan">' + results[udpCnt].DELNGBUNDLE_QY + " " + results[udpCnt].STNDRD     + '<br>'
			     + '<span class="mafspan"><span style="color:#FFF44F;">' + priceString(results[udpCnt].AVRG_AMT)                          + '</span></span></div>'
			     + '<div class="div_grad"  ><span class="mafspan" >' + results[udpCnt].GRAD                                              + '</span></div>'
			     + '</div>';			     
		 	cnt++;
			eCnt++;
			
			if(results.length > 7){
			
				if(cnt == 7){
					appendSw(str);
					str = '';				
					cnt = 0;
				}				
				if(eCnt == 14){
				    eCnt = 0;
				    return false;
				}				
			}else if(!results[udpCnt+1]){
				appendSw(str);
			}
		 
		 }
		 	 		 
	}
	
	function priceString(amt){		
		return amt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
	
	
	$(document).on("click","div[name=data]",function(){		
		
		var prdlst_cd = $(this).attr("value1");
		var spcies_cd = $(this).attr("value2");
		var prdlst_nm = $(this).attr("value3");
		var spcies_nm = $(this).attr("value4");
		
		window.open("/mafra/mafraListDef?value1=" + prdlst_cd + "&value2=" + spcies_cd + "&value3=" + prdlst_nm + "&value4="+ spcies_nm, "품목상세 조회","location=no,width=870px,height=500px,scrollbars=no,resizable=yes");
				
		
	});
	
</script>
<style>

 html,body {
    position: relative;
    height: 95%;
  }

  body {
    background: black;
    font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 25px;   
    color: white;
    margin: 0;
    padding: 0;
  }
  
  .mafTable{
  	width:100%;  	
  }
    
  .mafTable th {
	  padding: 25px;
	  color: white;
	  border: 1px groove yellow;
	  text-align: center;	  
	}

.mafTable th:nth-of-type(1) { width: 20%; height:20%;}
.mafTable th:nth-of-type(2) { width: 20%; height:20%;}
.mafTable th:nth-of-type(3) { width: 20%; height:20%;}
.mafTable th:nth-of-type(4) { width: 20%; height:20%;}
.mafTable th:nth-of-type(6) { width: 20%; height:20%;}

.swiper-container {
  width: 100%;
  height: 90%;
}
.swiper-slide {
  text-align: center;
  font-size: 45px;
  font-weight: bold;
  background: black;
  color:white; 
}

.mafspan{text-align:center;}

.div_name  {width:20%; height:100%; float:left; text-align:left;               display:flex; align-items:center;}
.div_name2 {width:20%; height:100%; float:left; text-align:left;               display:flex; align-items:center;}
.div_100amt{width:20%; height:100%; float:left; color:#00FF7F; text-align:right; display:flex; align-items:center; justify-content: center;}
.div_amt   {width:20%; height:100%; float:left;                                display:flex; align-items:center; justify-content: center;}
.div_grad  {width:19%; height:100%; float:left; color:#FFF44F;                  display:flex; align-items:center; justify-content: center;}

.swiper-button-next {
	position:absolute;
	top:97%;
	left:55%;
	transform: rotate(90deg);
}
.swiper-button-prev{
	position:absolute;
	top:97%;
	left:45%;
	transform: rotate(90deg);
}
</style>		
<title>농림축산 경매 시세</title>
</head>
<body>
	<div> 
		<table class="mafTable">
		    <thead>
	            <tr style="text-align: center;">
	                <!-- <th>거래일자</th> -->
	                <th>품목명</th>
	                <th>품종명</th>
	                <th style="color:#00FF7F;">100g 가격</th>
                	<th>경락단위<br><span style="color:#FFF44F;">경락평균</span></th>
	                <th style="color:#FFF44F;">등급</th>
	            </tr>
               </thead>
	    </table>
	</div>
	<div class="swiper-container">
	    <div class="swiper-wrapper">
	    
	    </div>        	    
  <!-- Add Arrows -->
  <div class="swiper-button-next swiper-button-white slider-arrow"></div>
  <div class="swiper-button-prev swiper-button-white slider-arrow"></div>

	</div>

<script>

	var swiper = new Swiper('.swiper-container', {
    	direction: 'vertical',
    	//direction: 'horizontal',
   		spaceBetween: 60,
    	centeredSlides: true,
    	loop:true,
    	autoplay: {
	        delay: 5000,
	        disableOnInteraction: false,
	    },
	    pagination: {
	        el: '.swiper-pagination',
	        clickable: true,
	      },
	    navigation: {
	        nextEl: '.swiper-button-prev',
	        prevEl: '.swiper-button-next'
	    }      
	  });
	
	  
</script>
</body>
</html>