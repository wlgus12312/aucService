package com.mafra.daoImpl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.mafra.dao.MafraDao;
import com.mafra.dto.User;

@Repository("MafraDao")
public class MafraDaoImpl implements MafraDao{
	
	private static Logger log = LoggerFactory.getLogger(MafraDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
    private NamedParameterJdbcTemplate nameTemplate;
	
	@Override
	public void insertMafra(Map map, int insCnt) throws Exception{		
		
		String sql = "INSERT INTO MAFRA(AUCNG_NO, AUCNG_DE, PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD,  CPR_NM, CPR_CD, PRDLST_NM, PRDLST_CD, SPCIES_NM, SPCIES_CD,\r\n"
				+ "                  GRAD, GRAD_CD, DELNGBUNDLE_QY, STNDRD, STNDRD_CD, DELNG_QY, MUMM_AMT, AVRG_AMT, MXMM_AMT, AUC_CO, B_DATE)\r\n"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
		jdbcTemplate.update(sql, insCnt
				               , map.get("AUCNG_DE")
				               , map.get("PBLMNG_WHSAL_MRKT_NM")
				               , map.get("PBLMNG_WHSAL_MRKT_CD")
				               , map.get("CPR_NM")
				               , map.get("CPR_CD")
				               , map.get("PRDLST_NM")
				               , map.get("PRDLST_CD")
				               , map.get("SPCIES_NM")
				               , map.get("SPCIES_CD")
				               , map.get("GRAD")
				               , map.get("GRAD_CD")
				               , map.get("DELNGBUNDLE_QY")
				               , map.get("STNDRD")
				               , map.get("STNDRD_CD")
				               , map.get("DELNG_QY")
				               , map.get("MUMM_AMT")
				               , map.get("AVRG_AMT")
				               , map.get("MXMM_AMT")
				               , map.get("AUC_CO")
				               );
		
	}

	@Override
	public List<Map<String, Object>> getMafraList(String loginId) throws Exception {
		
		String sql = "SELECT AUCNG_NO, AUCNG_DE, PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD, CPR_NM, CPR_CD              \r\n"
				+ "     , PRDLST_NM, PRDLST_CD, SPCIES_NM, SPCIES_CD, GRAD, GRAD_CD, DELNGBUNDLE_QY, STNDRD, STNDRD_CD   \r\n"
				+ "	 , DELNG_QY, MUMM_AMT, AVRG_AMT, MXMM_AMT, AUC_CO, B_DATE FROM MAFRA                                 \r\n"
				+ " WHERE AUCNG_DE = (SELECT MAX(AUCNG_DE) FROM MAFRA)                                                   \r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)                                                           \r\n"
				+ "                       FROM MAFRA_USER_COF                                                            \r\n"
				+ "			          WHERE ID   = ?                                                                     \r\n"
				+ "				        AND CODE = '05') = 0 THEN 1                                                      \r\n"
				+ "		       WHEN PBLMNG_WHSAL_MRKT_CD IN (SELECT SUB_CODE             \r\n"
				+ "                                                 FROM MAFRA_USER_COF    \r\n"
				+ "			                                    WHERE ID   = ?             \r\n"
				+ "				                                  AND CODE = '05') THEN 1  \r\n"
				+ "			   ELSE 0\r\n"
				+ "               END\r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)\r\n"
				+ "                       FROM MAFRA_USER_COF\r\n"
				+ "			          WHERE ID   = ?\r\n"
				+ "				        AND CODE = '06') = 0 THEN 1\r\n"
				+ "		       WHEN CPR_CD IN (SELECT SUB_CODE\r\n"
				+ "                                   FROM MAFRA_USER_COF\r\n"
				+ "			                      WHERE ID   = ?\r\n"
				+ "				                    AND CODE = '06') THEN 1\r\n"
				+ "			   ELSE 0\r\n"
				+ "               END\r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)\r\n"
				+ "                       FROM MAFRA_USER_COF\r\n"
				+ "			          WHERE ID   = ?\r\n"
				+ "				        AND CODE = '01') = 0 THEN 1\r\n"
				+ "		       WHEN PRDLST_CD IN (SELECT SUB_CODE\r\n"
				+ "                                      FROM MAFRA_USER_COF\r\n"
				+ "			                         WHERE ID   = ?\r\n"
				+ "				                       AND CODE = '01') THEN 1\r\n"
				+ "			   ELSE 0\r\n"
				+ "               END\r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)\r\n"
				+ "                       FROM MAFRA_USER_COF\r\n"
				+ "			          WHERE ID   = ?\r\n"
				+ "				        AND CODE = '02') = 0 THEN 1\r\n"
				+ "		       WHEN SPCIES_CD IN (SELECT SUB_CODE\r\n"
				+ "                                      FROM MAFRA_USER_COF\r\n"
				+ "			                         WHERE ID   = ?\r\n"
				+ "				                       AND CODE = '02') THEN 1\r\n"
				+ "			   ELSE 0\r\n"
				+ "               END\r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)\r\n"
				+ "                       FROM MAFRA_USER_COF\r\n"
				+ "			          WHERE ID   = ?\r\n"
				+ "				        AND CODE = '03') = 0 THEN 1\r\n"
				+ "		       WHEN GRAD_CD IN (SELECT SUB_CODE\r\n"
				+ "                                      FROM MAFRA_USER_COF\r\n"
				+ "			                         WHERE ID   = ?\r\n"
				+ "				                       AND CODE = '03') THEN 1\r\n"
				+ "			   ELSE 0\r\n"
				+ "               END\r\n"
				+ "  AND 1 = CASE WHEN (SELECT count(SUB_CODE)\r\n"
				+ "                       FROM MAFRA_USER_COF\r\n"
				+ "			          WHERE ID   = ?\r\n"
				+ "				        AND CODE = '04') = 0 THEN 1\r\n"
				+ "		       WHEN STNDRD_CD IN (SELECT SUB_CODE\r\n"
				+ "                                      FROM MAFRA_USER_COF\r\n"
				+ "			                         WHERE ID   = ?\r\n"
				+ "				                       AND CODE = '04') THEN 1\r\n"
				+ "			   ELSE 0\r\n"
				+ "               END";
				   
		List<Map<String, Object>> mafraList = jdbcTemplate.queryForList(sql, loginId, loginId, loginId, loginId, loginId, loginId
				                                                           , loginId, loginId, loginId, loginId, loginId, loginId);
								
		return mafraList;
	}

	@Override
	public int checkId(String signId, String signAddr) throws Exception {

		int check = 0;
		
		String sql = "SELECT COUNT(*) \r\n"
				+ "     FROM MAFRA_USER \r\n"
				+ "    WHERE ID = ?";
		
		check = jdbcTemplate.queryForObject(sql, Integer.class, signId);
		
		return check;
	}

	@Override
	public int signIn(Map<String, String> signMap) throws Exception {
		
		int check = 0;
		
		String sql = "INSERT INTO MAFRA_USER(ID, PWD, NAME, IP, YN, ST_DT, ED_DT)\r\n"
				   + "VALUES(?, ?, ?, ?, ?, GETDATE(), dateadd(mm, 1, GETDATE()))";
		
		check = jdbcTemplate.update(sql
	                      , signMap.get("signId")
	                      , signMap.get("encodePwd")
	                      , signMap.get("signName")
	                      , signMap.get("signAddr")
	                      , "0"
	                       );	
		
		return check;
	}

	@Override
	public int loginCheck(String loginId) throws Exception {

		int check = 0;
		
		String sql = "SELECT count(*)\r\n"
				   + "  FROM MAFRA_USER\r\n"
			       + " WHERE ID  = ?\r\n";
		
		check = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
		
		return check;
	}

	@Override
	public int loginCheckIp(String loginId, String loginAddr) throws Exception {

		int check = 0;
		
		String sql = "SELECT count(*)\r\n"
				+ "  FROM MAFRA_USER\r\n"
				+ " WHERE ID  = ?\r\n"
				+ "   AND IP  = ?";
		
		check = jdbcTemplate.queryForObject(sql, Integer.class, loginId, loginAddr);	
		
		return check;
	}

	@Override
	public int loginCheckDate(String loginId) throws Exception {
		
		int check = 0;
		
		String sql = "SELECT count(*)\r\n"
				   + "  FROM MAFRA_USER\r\n"
				   + " WHERE ID = ?\r\n"
				   + "   AND GETDATE() <= ED_DT\r\n";
		
		check = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
		
		return check;
	}

	@Override
	public int loginCheckYn(String loginId) throws Exception {
		
		int check = 0;
		
		String sql = "SELECT count(*)  \r\n"
				   + "  FROM MAFRA_USER\r\n"
				   + " WHERE ID = ?    \r\n"
				   + "   AND YN != 0   \r\n";
		
		check = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
		
		return check;
	}

	@Override
	public Map<String, Object> login(String loginId) throws Exception {
		
		Map<String, Object> userMap = null;
		
		String sql = "SELECT id, name, yn, st_dt, ed_dt \r\n"
			       + "  FROM MAFRA_USER \r\n"
				   + " Where id = ? \r\n";
		
		userMap = jdbcTemplate.queryForMap(sql, loginId);
		
		return userMap;
	}

	@Override
	public String getPwd(String loginId) throws Exception {
		
		String encodePwd = "";
		
		String sql = "SELECT pwd \r\n"
				   + "  FROM MAFRA_USER \r\n"
				   + " WHERE id = ?";
		
		encodePwd = jdbcTemplate.queryForObject(sql, String.class, loginId);
		
		return encodePwd;
	}

	@Override
	public Map<String, List> selectConf(String loginId) throws Exception {
	    
	    Map<String, List> confMap = null;
	    List confList = null;
	    int cnt = 0;
	    
	    String sql = "SELECT count(*)       \r\n"
	    		   + "  FROM MAFRA_USER_COF \r\n"
	    		   + " WHERE ID   = ?       \r\n"
	    		      ;
	    
	    cnt = jdbcTemplate.queryForObject(sql, Integer.class, loginId);
	    
	    if(cnt > 0) {
	    	
	    	confMap = new HashMap<String, List>();
	    	
	    	 sql = "SELECT id, code, sub_code  \r\n"
		         + "  FROM MAFRA_USER_COF      \r\n"
		    	 + " WHERE ID   = ?            \r\n"
		    	 + "   AND CODE = ?            \r\n"
		    	   ;
	    	 
	    	 String cn = "";
	    	 	    	 
	    	 for(int i = 1; i < 7; i++) {	    		 
	    		   		 
	    	     cn = String.format("%02d", i);	    	     
	    	     confList = jdbcTemplate.queryForList(sql, loginId, cn);	    	     
	    		 confMap.put(cn, confList);	  
	    		 
	    	 }
	    }
	    		
		return confMap;
	}

	@Override
	public Map<String, List> getCodeList() throws Exception {
		
		Map<String, List> codeMap = new HashMap<String, List>();;
	    List codeList = null;
	    
	    //공판
	    String sql = "SELECT PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD    \r\n"
	    		   + "  FROM MAFRA_CPR                                     \r\n"
	    		   + " GROUP BY PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD "
	    		      ;
	    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("mrkt", codeList);
	    
	    //도매
	    sql = "SELECT CPR_NM, CPR_CD, PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD \r\n"
	    		+ "  FROM MAFRA_CPR                                              \r\n"
	    		+ " GROUP BY CPR_NM, CPR_CD, PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD ";
	    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("cpr", codeList);
	    
	    //품목
	    sql = "SELECT '01', SUB_CODE, SUB_CODE_NM \r\n"
	      	+ "  FROM MAFRA_CODE                  \r\n"
	    	+ " WHERE CODE = '01'           ";
	    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("01", codeList);
	    
	    //품종
	    sql = "SELECT '02', SUB_CODE, SUB_CODE_NM \r\n"
		      	+ "  FROM MAFRA_CODE              \r\n"
		    	+ " WHERE CODE = '02'       ";
		    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("02", codeList);
		
	    //등급
		sql = "SELECT '03', SUB_CODE, SUB_CODE_NM \r\n"
		   	+ "  FROM MAFRA_CODE                  \r\n"
		   	+ " WHERE CODE = '03'           ";
			    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("03", codeList);
	    
	    //규격
	    sql = "SELECT '04', SUB_CODE, SUB_CODE_NM   \r\n"
		      	+ "  FROM MAFRA_CODE                \r\n"
		    	+ " WHERE CODE = '04'         ";
		    
	    codeList = jdbcTemplate.queryForList(sql);
	    codeMap.put("04", codeList);
	    
		return codeMap;
	}
	
	@Override
	public int updateUserConf(String loginId) throws Exception {
		
		int result = 0;
		
		String sql = "DELETE FROM MAFRA_USER_COF \r\n"
				   + " WHERE ID = ?         ";
		
		result = jdbcTemplate.update(sql, loginId);	
		
		return result;
	}


	@Override
	public int insUserCode(List list, String loginId, String code) throws Exception {
		
		int result = 0;
		
		String sql = "INSERT INTO MAFRA_USER_COF(ID, SQ, CODE, SUB_CODE) \r\n"
			       + " VALUES(?, (select count(SQ)                       \r\n"
				   + "              from MAFRA_USER_COF                  \r\n"
				   + "			   where ID = ?) + 1, ?, ?)          ";
		
		for(int i = 0; i < list.size(); i++) {			
			result += jdbcTemplate.update(sql, loginId, loginId, code, list.get(i));	
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> selectDefMafList(Map<String, Object> params) throws Exception {
		
		List<Map<String, Object>> list = null;
		
		String sql = "";
		
		MapSqlParameterSource code = new MapSqlParameterSource();
		
		//05 - 공판, 06 - 도매, 01 - 품목, 02 - 품종, 03 - 등급, 04 - 규격
		String date = (String) params.get("selDate");
		code.addValue("date", new SqlParameterValue(Types.VARCHAR, date));
		List<String> code01 = (List) params.get("code01");
		List<String> code02 = (List) params.get("code02");
		List<String> code03 = (List) params.get("code03");
		List<String> code04 = (List) params.get("code04");
		List<String> code05 = (List) params.get("code05");
		List<String> code06 = (List) params.get("code06");		
		
		sql = "SELECT * FROM MAFRA                                           \r\n"
			    + " WHERE 1=1                                                \r\n"
			    + "   AND AUCNG_DE = :date                                   \r\n"
				;
				
		if(code01 != null && code01.size() > 0) {			
			sql += "  AND PRDLST_CD IN (:code01)                             \r\n";
			
			code.addValue("code01", code01, Types.NVARCHAR);
			
		}
		if(code02 != null && code02.size() > 0) {			
			sql += "  AND SPCIES_CD IN (:code02)                             \r\n";
			
			String code02str = String.join(",", code02);			
			code.addValue("code02", code02, Types.NVARCHAR);
		}
		if(code03 != null && code03.size() > 0) {			
			sql += "  AND GRAD_CD IN (:code03)                               \r\n";

			String code03str = String.join(",", code03);			
			code.addValue("code03", code03, Types.NVARCHAR);
		}
		if(code04 != null && code04.size() > 0) {			
			sql += "  AND STNDRD_CD IN (:code04)                             \r\n";

			String code04str = String.join(",", code04);			
			code.addValue("code04", code04, Types.NVARCHAR);
		}
		if(code05 != null && code05.size() > 0) {			
			sql += "  AND PBLMNG_WHSAL_MRKT_CD IN (:code05)                  \r\n";

			String code05str = String.join(",", code05);			
			code.addValue("code05", code05, Types.NVARCHAR);
		}
		if(code06 != null && code06.size() > 0) {			
			sql += "  AND CPR_CD IN (:code06)                                \r\n";
			
			String code06str = String.join(",", code06);			
			code.addValue("code06", code06, Types.NVARCHAR);
		}		
		
		list = nameTemplate.queryForList(sql, code);
				
		return list;
		
	}

	@Override
	public List<Map<String, Object>> selectUserList(String selUser) throws Exception {

		List<Map<String, Object>> list = null;
		
		String sql = "";
		
		sql = "SELECT * FROM MAFRA_USER                  \r\n"
				+ " WHERE 1=1                            \r\n"
				+ "   AND id != 'admin'                  \r\n"
				+ "   AND 1 = CASE WHEN ?  = '' THEN 1   \r\n"
				+ "                WHEN id  = ? THEN 1   \r\n"
				+ "		           ELSE 0                \r\n"
				+ "	       END"
				;
		
		
		list = jdbcTemplate.queryForList(sql, selUser, selUser);
		
		return list;
	}

	@Override
	public int updateUserYn(String selUserId) throws Exception {
		
		int updCnt = 0;
		
		String sql = "UPDATE MAFRA_USER  \r\n"
				   + "   SET YN = '1'    \r\n"
			   	   + " WHERE ID = ?          ";
		
		updCnt = jdbcTemplate.update(sql, selUserId);
		
		return updCnt;
	}

	@Override
	public void setCode() throws Exception {

		String sql = "";
		
		//도매
		sql = " INSERT INTO MAFRA_CPR(CPR_CD, CPR_NM, PBLMNG_WHSAL_MRKT_CD,PBLMNG_WHSAL_MRKT_NM)  \r\n"
			+ " SELECT CPR_CD, CPR_NM, PBLMNG_WHSAL_MRKT_CD,PBLMNG_WHSAL_MRKT_NM FROM MAFRA       \r\n"
			+ "  WHERE CPR_CD NOT IN(SELECT CPR_CD FROM MAFRA_CPR)                                \r\n"
			+ "  GROUP BY CPR_CD, CPR_NM, PBLMNG_WHSAL_MRKT_CD,PBLMNG_WHSAL_MRKT_NM               \r\n"
			;
		jdbcTemplate.update(sql);
		
		//품목
		sql = " INSERT INTO MAFRA_CODE(CODE, CODE_NM, SUB_CODE, SUB_CODE_NM)   \r\n"
			+ " SELECT '01', '품목', PRDLST_CD, PRDLST_NM                       \r\n"
			+ "   FROM MAFRA                                                   \r\n"
			+ "  WHERE PRDLST_CD NOT IN(SELECT SUB_CODE FROM MAFRA_CODE        \r\n"
			+ "                          WHERE CODE = '01')                    \r\n"
			+ "  GROUP BY PRDLST_CD, PRDLST_NM                                 \r\n"
			;		
		jdbcTemplate.update(sql);
		
		//품종
		sql = " INSERT INTO MAFRA_CODE(CODE, CODE_NM, SUB_CODE, SUB_CODE_NM)   \r\n"
			+ " SELECT '02', '품종', SPCIES_CD, SPCIES_NM                       \r\n"
			+ "   FROM MAFRA                                                   \r\n"
			+ "  WHERE SPCIES_CD NOT IN(SELECT SUB_CODE FROM MAFRA_CODE        \r\n"
			+ "                          WHERE CODE = '02')                    \r\n"
			+ "  GROUP BY SPCIES_CD, SPCIES_NM                                 \r\n"
			;		
		jdbcTemplate.update(sql);
		
		//등급
		sql = " INSERT INTO MAFRA_CODE(CODE, CODE_NM, SUB_CODE, SUB_CODE_NM)   \r\n"
			+ " SELECT '03', '등급', GRAD_CD, GRAD                              \r\n"
			+ "   FROM MAFRA                                                   \r\n"
			+ "  WHERE GRAD_CD NOT IN(SELECT SUB_CODE FROM MAFRA_CODE          \r\n"
			+ "                          WHERE CODE = '03')                    \r\n"
			+ "  GROUP BY GRAD_CD, GRAD                                        \r\n"
			;		
		jdbcTemplate.update(sql);
		
		//규격
		sql = " INSERT INTO MAFRA_CODE(CODE, CODE_NM, SUB_CODE, SUB_CODE_NM)   \r\n"
			+ " SELECT '04', '규격', STNDRD_CD, STNDRD                          \r\n"
			+ "   FROM MAFRA                                                   \r\n"
			+ "  WHERE STNDRD_CD NOT IN(SELECT SUB_CODE FROM MAFRA_CODE        \r\n"
			+ "                          WHERE CODE = '04')                    \r\n"
			+ "  GROUP BY STNDRD_CD, STNDRD                                    \r\n"
			;		
		jdbcTemplate.update(sql);
		
	}

	@Override
	public List<Map<String, Object>> selListDef(String prdlst_cd, String spcies_cd) throws Exception {
		
		List<Map<String, Object>> list = null;
		
		String sql = "";
		
		sql = "\r\n"
				+ "SELECT CONVERT(VARCHAR,B_DATE, 120) AS B_DATE, PBLMNG_WHSAL_MRKT_NM, PBLMNG_WHSAL_MRKT_CD, DELNGBUNDLE_QY, DELNG_QY, MUMM_AMT, AVRG_AMT, MXMM_AMT, "
				+ "GRAD, GRAD_CD, "
				+ " STNDRD, STNDRD_CD, CPR_NM, CPR_CD \r\n"
				+ "  FROM MAFRA\r\n"
				+ " WHERE AUCNG_DE = (SELECT MAX(AUCNG_DE) FROM MAFRA) \r\n"
				+ "   AND prdlst_cd = ? \r\n"
				+ "   AND spcies_cd = ? \r\n"
				+ " ORDER BY 1 ASC \r\n"
				+ "  ;";
		
		list = jdbcTemplate.queryForList(sql, prdlst_cd, spcies_cd);
		
		return list;
	}

}
