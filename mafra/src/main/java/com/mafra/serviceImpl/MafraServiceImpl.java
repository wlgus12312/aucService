package com.mafra.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mafra.SecurityConfig;
import com.mafra.dao.MafraDao;
import com.mafra.dto.User;
import com.mafra.service.MafraService;

@Service("MafraService")
public class MafraServiceImpl implements MafraService{
	
	private static Logger log = LoggerFactory.getLogger(MafraServiceImpl.class);
	
	@Autowired
	MafraDao mafraDto;
	@Autowired
	PasswordEncoder passwordEncoder;

	int insCnt  = 1;
	
	@Override
	public void getMafra(String reqDate) throws JsonProcessingException{
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		String resultString = "";
		
		try {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate rt = new RestTemplate(factory);
            
            HttpHeaders header = new HttpHeaders();            
            HttpEntity<?> entity = new HttpEntity<>(header);
			
            String URL = "http://211.237.50.150:7080/openapi/56dc751c318b37a396e7904b6b3c79e1c46c6cd7f931cf0c011749162ade55a4/json/Grid_20141119000000000012_1/1/1?AUCNG_DE=" + reqDate;
            
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL).build();
			
            ResponseEntity<Map> resultMap = rt.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            
            result.put("statusCode", resultMap.getStatusCodeValue()); 
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody()); 
            
            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();
            resultString = mapper.writeValueAsString(resultMap.getBody());
            Map<String, Object> paraMap = new ObjectMapper().readValue(resultString, new TypeReference<Map<String, Object>>(){});
            
            Map<String, Object> cntMap = (Map<String, Object>) paraMap.get("Grid_20141119000000000012_1");
            Map<String, Object> codeMap = (Map<String, Object>) cntMap.get("result");

            //정상
            if(codeMap.get("code").equals("INFO-000")) {
            	
            	int stCnt   = 1;
            	int edCnt   = 1000;
            	int dataCnt = (int) cntMap.get("totalCnt");
            	
            	//전체 cnt를 1000건씩 조회
            	while(dataCnt > 0) {            		
            		insertMafra(stCnt, edCnt, reqDate);            		
            		stCnt   += 1000;
            		edCnt   += 1000;
            		dataCnt -= 1000;
            	}
            	
            	
            }
            
		} catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            log.info(e.toString());
 
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            log.info(e.toString());
        }		
		
	}
	
	
	
	public void insertMafra(int stCnt, int edCnt, String reqDate) throws JsonProcessingException{
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		String resultString = "";
		
		try {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate rt = new RestTemplate(factory);
            
            HttpHeaders header = new HttpHeaders();            
            HttpEntity<?> entity = new HttpEntity<>(header);
			
            String URL = "http://211.237.50.150:7080/openapi/56dc751c318b37a396e7904b6b3c79e1c46c6cd7f931cf0c011749162ade55a4/json/Grid_20141119000000000012_1/"+ stCnt + "/" + edCnt + "?AUCNG_DE=" + reqDate;            
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL).build();			
            ResponseEntity<Map> resultMap = rt.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            
            result.put("statusCode", resultMap.getStatusCodeValue()); 
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody()); 
            
            ObjectMapper mapper = new ObjectMapper();
            resultString = mapper.writeValueAsString(resultMap.getBody());
            Map<String, Object> paraMap = new ObjectMapper().readValue(resultString, new TypeReference<Map<String, Object>>(){});
            
            Map<String, Object> cntMap = (Map<String, Object>) paraMap.get("Grid_20141119000000000012_1");
            List<Map> dataMap = (List<Map>) cntMap.get("row");
            
            for(int i = 0; i<dataMap.size(); i++) {
            	//dataMap DB insert
            	mafraDto.insertMafra(dataMap.get(i), insCnt);
            	insCnt++;            	
            }          	            
            
		} catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            log.info(e.toString());
 
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            log.info(e.toString());
        }	
	
	}

	@Override
	public List<Map<String, Object>> getMafraList(String loginId) throws Exception {

		List<Map<String, Object>> list = null;
		list = mafraDto.getMafraList(loginId);
				
		return list;
	}


	@Override
	public String SignIn(Map<String, String> signMap) throws Exception {
		
		String checkString = "";		
		int check = 0;
				
		check = mafraDto.checkId(signMap.get("signId"), signMap.get("signAddr"));
		
		if(check < 1){
			
			String encodePwd = passwordEncoder.encode(signMap.get("signPwd"));
					
			signMap.put("encodePwd", encodePwd);
			
			check = mafraDto.signIn(signMap);
			
			if(check > 0) {
				checkString = "success";
			}else {
				checkString = "failed";
			}
			return checkString;
			
		}else {			
			
			checkString = "duplicated";					
			return checkString;			
		}
	}


	@Override
	public String loginCheck(Map<String, String> loginMap) throws Exception {
		
		String checkString = "";		
		int check = 0;
		
		//id,pwd 체크
		check = mafraDto.loginCheck(loginMap.get("loginId"));

		if(check > 0) {
			String encodePwd = mafraDto.getPwd(loginMap.get("loginId"));
		
			if(passwordEncoder.matches(loginMap.get("loginPwd"), encodePwd)) {
				
				
				if(loginMap.get("loginId").equals("admin")) {
					
					checkString = "success";
					return checkString;
					
				}
				
				//ip 체크	 IP와 ID가 같아야 로그인 가능
				check =  mafraDto.loginCheckIp(loginMap.get("loginId"), loginMap.get("loginAddr"));
				if(check > 0) {
					//날짜 체크 ed_dt가 오늘날짜보다 크면 로그인 안됨
					check = mafraDto.loginCheckDate(loginMap.get("loginId"));
					if(check > 0) {
						//성공
						checkString = "success";
					}else {
						//날짜가 지났어도 승인여부가 1이면 사용가능
						check = mafraDto.loginCheckYn(loginMap.get("loginId"));
						if(check > 0) {
							//성공
							checkString = "success";
						}else {
							checkString = "dateNotMatched";
						}
					}				
				}else {
					//ip 불일치
					checkString = "ipNotMatched";
				}	
				
			}else {
				checkString = "pwdNotMatched";	
			}			
		}else {
			checkString = "idNotMatched";		
		}
						
		return checkString;
	}

	@Override
	public Map<String, Object> login(String loginId) throws Exception {
		
		Map<String, Object> userMap = null;		
		userMap = mafraDto.login(loginId);		
		return userMap;
	}



	@Override
	public Map<String, List> selectConf(String loginId) throws Exception {
		
		Map<String, List> confMap = null;
		confMap = mafraDto.selectConf(loginId);		
		return confMap;
	}



	@Override
	public Map<String, List> getCodeList() throws Exception {
		
		Map<String, List> codeMap = null;
		codeMap = mafraDto.getCodeList();
		return codeMap;
	}



	@Override
	public int setUserConf(Map<String, Object> params, String loginId) throws Exception {

		int resultCnt = 0;
		
		//05, 06, 01, 02, 03, 04
		List selMrkt   = (List) params.get("selMrkt");
		List selCpr    = (List) params.get("selCpr");
		List selCode01 = (List) params.get("selCode01");
		List selCode02 = (List) params.get("selCode02");
		List selCode03 = (List) params.get("selCode03");
		List selCode04 = (List) params.get("selCode04");
		
		//현재 설정 YN 0으로 변경
		resultCnt = mafraDto.updateUserConf(loginId);
		
		resultCnt = 0;
		
		if(selMrkt != null) {			
			resultCnt += mafraDto.insUserCode(selMrkt, loginId, "05");			
		}
		if(selCpr != null) {			
			resultCnt += mafraDto.insUserCode(selCpr, loginId, "06");			
		}
		if(selCode01 != null) {			
			resultCnt += mafraDto.insUserCode(selCode01, loginId, "01");			
		}
		if(selCode02 != null) {			
			resultCnt += mafraDto.insUserCode(selCode02, loginId, "02");			
		}
		if(selCode03 != null) {			
			resultCnt += mafraDto.insUserCode(selCode03, loginId, "03");			
		}
		if(selCode04 != null) {			
			resultCnt += mafraDto.insUserCode(selCode04, loginId, "04");		
		}
		
		return resultCnt;
	}



	@Override
	public List<Map<String, Object>> getDefMafraList(Map<String, Object> params) throws Exception {
		
		List<Map<String, Object>> list = null;
		
		list = mafraDto.selectDefMafList(params);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> getUserList(String selUser) throws Exception {
		
		List<Map<String, Object>> list = null;
		
		list = mafraDto.selectUserList(selUser);
		
		return list;
	}

	@Override
	public int updateUserYn(String selUserId) throws Exception {
		
		int udpCnt = 0;
		
		udpCnt = mafraDto.updateUserYn(selUserId);
		
		return udpCnt;
	}



	@Override
	public void setCode() throws Exception {
		
		mafraDto.setCode();
		
	}



	@Override
	public List<Map<String, Object>> selListDef(String prdlst_cd, String spcies_cd) throws Exception {
		
		List<Map<String, Object>> list = null;
		
		list = mafraDto.selListDef(prdlst_cd, spcies_cd);
		
		return list;
	}
	
}





