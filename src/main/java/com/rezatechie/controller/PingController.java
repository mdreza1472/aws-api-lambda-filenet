package com.rezatechie.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rezatechie.context.StageContextHolder;


@RestController
public class PingController {
	
	private static final Logger log = LoggerFactory.getLogger(PingController.class);
	
    @Autowired
    private StageContextHolder stageContextHolder;
	
    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public Map<String, String> ping() {
    	String uri = stageContextHolder.get("filenetUri");
    	System.out.println("uri:"+ uri);
        Map<String, String> pong = new HashMap<>();
        pong.put("pong", "Hello, World!");
        return pong;
    }
}
