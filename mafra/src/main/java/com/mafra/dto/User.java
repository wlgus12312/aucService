package com.mafra.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.*;

@Setter
@Getter
@Component
@ToString
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String yn;
	private String st_dt;
	private String ed_dt;
	private Map<String, List> confMap;
	
}
