package com.mafra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mafra.service.MafraService;

@Component
public class ScheduleTask {
	
	private static Logger log = LoggerFactory.getLogger(ScheduleTask.class);
	
	@Autowired
	MafraService mainservice;
	
//	1 2 3 4 5 6  // 순서
//	* * * * * *  // 실행주기 문자열
//
//	// 순서별 정리
//	1. 초(0-59)
//	2. 분(0-59)
//	3. 시간(0-23)
//	4. 일(1-31)
//	5. 월(1-12)
//	6. 요일(0-7)

	@Scheduled(cron = "0 20 8 * * *")
	public void task1()  throws Exception{
		
		//현재날짜 -1
		String reqDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));				
		log.info("scheduled task1  : " + reqDate);
		//mafra data 호출
		mainservice.getMafra(reqDate);
		mainservice.setCode();
		
	}
	
	
//	@Scheduled(cron = "10 * * * * *")
//	public void task2() {		
//		log.info("scheduled task2  : " + LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		
//	}
	
	
}
