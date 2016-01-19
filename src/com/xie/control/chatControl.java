package com.xie.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.xie.server.ChatServer;

@Controller
public class chatControl {
	@Autowired
	ChatServer  chatServer;
	@RequestMapping(value="/loginforchat")
	public Object loginForChat(String id){
		if (StringUtils.isEmpty(id)){
			return "";
		}
		String res=chatServer.login(id);
		
		
		JsonObject json = new JsonObject();
		json.addProperty("userid", "");
		json.addProperty("result", "");
//		json.add(property, "");
//		json.add(property, "");
		System.out.println("json"+ json.toString());
		return json.toString();
		
	}
}
