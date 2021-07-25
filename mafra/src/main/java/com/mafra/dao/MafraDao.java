package com.mafra.dao;

import java.util.List;
import java.util.Map;

import com.mafra.dto.User;

public interface MafraDao {

	//데이터 적재
	void insertMafra(Map map, int insCnt) throws Exception;
	//데이터 조회
	List<Map<String, Object>> getMafraList(String loginId) throws Exception;
	//회원가입 체크
	int checkId(String signId, String signAddr) throws Exception;
	//회원가입
	int signIn(Map<String, String> signMap) throws Exception;
	//로그인 체크	
	int loginCheck(String loginId) throws Exception;
	//로그인 IP 체크
	int loginCheckIp(String loginId, String loginAddr) throws Exception;
	//로그인 날짜 체크
	int loginCheckDate(String loginId) throws Exception;
	//로그인 승인 여부 체크
	int loginCheckYn(String loginId) throws Exception;
	//로그인
	Map<String, Object> login(String loginId) throws Exception;
	//비밀번호 체크
	String getPwd(String loginId) throws Exception;
	//사용자 설정 조회
	Map<String, List> selectConf(String loginId) throws Exception;
	//코드 조회
	Map<String, List> getCodeList() throws Exception;
	//사용자 설정 값 변경
	int updateUserConf(String loginId) throws Exception;
	//사용자 품목 설정 저장
	int insUserCode(List list, String loginId, String code) throws Exception;
	//상세 조회
	List<Map<String, Object>> selectDefMafList(Map<String, Object> params) throws Exception;
	//사용자 조회
	List<Map<String, Object>> selectUserList(String selUser) throws Exception;
	//사용자 승인처리
	int updateUserYn(String selUserId) throws Exception;
	//코드 적재
	void setCode() throws Exception;
	//목 상세 조회
	List<Map<String, Object>> selListDef(String prdlst_cd, String spcies_cd) throws Exception;

}
