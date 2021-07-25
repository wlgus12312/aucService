package com.mafra.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mafra.dto.User;
import com.mafra.service.MafraService;

@RestController
public class MainController {
	
	private static Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	MafraService mainservice;
	
	@Resource
	User user;
	
	@RequestMapping("/main")
	public ModelAndView main() throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");		
		return mv;
	}	
			
	@RequestMapping("/mafraList")
	public ModelAndView mafraList() throws Exception{	
		
		ModelAndView mv = new ModelAndView();
		
		if(user.getId() == null) {
			mv.setViewName("index");
			return mv;
		}				
		mv.setViewName("mafraList");
		mv.addObject("user", user);
		return mv;
	}
	
	@RequestMapping(value="/mafraListDef", method=RequestMethod.GET)
	public ModelAndView mafraListDef(HttpServletRequest req) throws Exception{	
		
		String prdlst_cd = req.getParameter("value1");
		String spcies_cd = req.getParameter("value2");
		String prdlst_nm = req.getParameter("value3");
		String spcies_nm = req.getParameter("value4");
		
		String title = spcies_nm + "(" + prdlst_nm + ")";
				
		ModelAndView mv = new ModelAndView();
		
		if(user.getId() == null) {
			mv.setViewName("index");
			return mv;
		}					
		
		List<Map<String, Object>> list = mainservice.selListDef(prdlst_cd, spcies_cd);
				
		mv.setViewName("mafraListDef");
		mv.addObject("user", user);
		mv.addObject("prdlst_cd", prdlst_cd);
		mv.addObject("spcies_cd", spcies_cd);
		mv.addObject("title", title);
		mv.addObject("list", list);
		
		return mv;
	}

	@ResponseBody
	@RequestMapping(value="/selectMafraList", method=RequestMethod.POST)
	public List<Map<String, Object>> selectMafraList(HttpServletRequest req) throws Exception{	
	
		List<Map<String, Object>> mafList = null;		
        mafList = mainservice.getMafraList(user.getId());        
		return mafList;
	}
	
		
	@ResponseBody
	@RequestMapping(value="/signIn", method=RequestMethod.POST)
	public Map<String, Object> signIn(HttpServletRequest req) throws Exception{	
	
		String reString = "";
		
		String signId   = req.getParameter("signId");
		String signPwd  = req.getParameter("signPwd");
		String signName = req.getParameter("signName");
		String signAddr = req.getRemoteAddr();
		int thdat = signAddr.lastIndexOf(".");
		signAddr = signAddr.substring(0, thdat);
				
		Map<String, String> signMap = new HashMap<String, String>();
		
		signMap.put("signId", signId);
		signMap.put("signPwd", signPwd);
		signMap.put("signName", signName);
		signMap.put("signAddr", signAddr);
				
		reString = mainservice.SignIn(signMap);
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		reMap.put("result", reString);
						
		return reMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Map<String, Object> login(HttpServletRequest req) throws Exception{	
	
		String reString = "";
		
		String loginId   = req.getParameter("loginId");
		String loginPwd  = req.getParameter("loginPwd");
		String loginAddr = req.getRemoteAddr();
		int thdat = loginAddr.lastIndexOf(".");
		loginAddr = loginAddr.substring(0, thdat);
		
		Map<String, String> loginMap = new HashMap<String, String>();
		
		loginMap.put("loginId", loginId);
		loginMap.put("loginPwd", loginPwd);
		loginMap.put("loginAddr", loginAddr);
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		reString = mainservice.loginCheck(loginMap);
				
		if(reString.equals("success")) {	
			
			Map<String, Object> userMap = null;
			Map<String, List> confMap = null;	
			
			userMap = mainservice.login(loginId);
			
			user.setId((String) userMap.get("id"));
			user.setName((String)userMap.get("name"));
			user.setYn((String)userMap.get("yn"));			
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String st_dt = dateFormat.format(userMap.get("st_dt"));
			String ed_dt = dateFormat.format(userMap.get("ed_dt"));
            user.setSt_dt(st_dt);
			user.setEd_dt(ed_dt);
			
			confMap = mainservice.selectConf(user.getId());		
			user.setConfMap(confMap);
			
		}				
		
		reMap.put("result", reString);
		
		return reMap;				
	}
	
	@RequestMapping(value="/loginMain")
    public ModelAndView loginGo(HttpServletRequest req) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		if(user.getId() == null) {
			mv.setViewName("index");
			return mv;
		}				
		
		if(user.getId().equals("admin")) {
			mv.setViewName("admin");
		}else {
			mv.setViewName("login");
		}		
		mv.addObject("user", user);
				
		return mv;	
	}
	
	@RequestMapping(value="/config")
    public ModelAndView config(HttpServletRequest req) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		if(user.getId() == null) {
			mv.setViewName("index");
			return mv;
		}				
		mv.setViewName("config");
		
		return mv;	
	}
	
	@ResponseBody
	@RequestMapping(value="/selectConfig", method=RequestMethod.POST)
	public Map<String, List> selectConfig(HttpServletRequest req) throws Exception{	
	
		Map<String, List> codeMap = null;
		codeMap = mainservice.getCodeList();
		
		List userList = new ArrayList<>();		
		userList.add(user.getConfMap());
		
		codeMap.put("user", userList);
				
		return codeMap;
	}
		
	@ResponseBody
	@RequestMapping(value="/configOk", method=RequestMethod.POST)
	public Map<String, Object> configOk(@RequestBody Map<String, Object> params, HttpServletRequest req) throws Exception{	

		int result = 0;
		
		result = mainservice.setUserConf(params, user.getId());
		
		Map<String, List> confMap = null;	
		confMap = mainservice.selectConf(user.getId());
		user.setConfMap(confMap);
		
		Map<String, Object> reMap = new HashMap<String, Object>();		
		reMap.put("result", result);
				
		return reMap;
		
	}
		
	@RequestMapping(value="/defList")
    public ModelAndView defList(HttpServletRequest req) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		if(user.getId() == null) {
			mv.setViewName("index");
			return mv;
		}				
		mv.setViewName("defList");
		
		return mv;	
	}
	
	@ResponseBody
	@RequestMapping(value="/selectDefMaf", method=RequestMethod.POST)
	public List<Map<String, Object>> selectDefMaf(@RequestBody Map<String, Object> params,HttpServletRequest req) throws Exception{	
	
		List<Map<String, Object>> mafList = null;		
				
        mafList = mainservice.getDefMafraList(params);
        
		return mafList;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/selectUser", method=RequestMethod.POST)
	public List<Map<String, Object>> selectUser(HttpServletRequest req) throws Exception{	
		
		String selUser = req.getParameter("selUser");
		
		List<Map<String, Object>> userList = null;		
		
		userList = mainservice.getUserList(selUser);
        
		return userList;
		
	}
		
	@ResponseBody
	@RequestMapping(value="/updUserYn", method=RequestMethod.POST)
	public String updUserYn(HttpServletRequest req) throws Exception{	
		
		String selUserId = req.getParameter("selUserId");
		String reString = "";
		
		int udpCnt = 0;
		
		udpCnt = mainservice.updateUserYn(selUserId);
		
		if(udpCnt > 0) reString = "success";
        
		return reString;
		
	}
	
	

}
