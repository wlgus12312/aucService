package com.mafra.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mafra.dto.User;

public interface MafraService {
	
	//데이터 적재
	void getMafra(String reqDate) throws JsonProcessingException;
	//데이터 조회
	List<Map<String, Object>> getMafraList(String loginId) throws Exception;
	//회원가입
	String SignIn(Map<String, String> signMap) throws Exception;
	//로그인 체크
	String loginCheck(Map<String, String> loginMap) throws Exception;
	//로그인
	Map<String, Object> login(String loginId) throws Exception;
	//사용자 설정 조회
	Map<String, List> selectConf(String loginId) throws Exception;
	//코드 조회
	Map<String, List> getCodeList() throws Exception;
	//사용자 설정 저장
	int setUserConf(Map<String, Object> params, String loginId) throws Exception;
	//상세 조회
	List<Map<String, Object>> getDefMafraList(Map<String, Object> params) throws Exception;
	//사용자 조회
	List<Map<String, Object>> getUserList(String selUser) throws Exception;
	//사용자 승인처리
	int updateUserYn(String selUserId) throws Exception;
	//코드 적재
	void setCode() throws Exception;
	//목 상세 조회
	List<Map<String, Object>> selListDef(String prdlst_cd, String spcies_cd) throws Exception;
	
	
}
