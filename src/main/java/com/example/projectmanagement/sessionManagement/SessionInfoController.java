package com.example.projectmanagement.sessionManagement;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
@Controller
public class SessionInfoController {
    @GetMapping("/session-info")
    @ResponseBody
    public String sessionInfo(HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> attributeNames = session.getAttributeNames();
        sb.append("Session ID: ").append(session.getId()).append("<br>");
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            Object value = session.getAttribute(name);
            sb.append("Attribute Name: ").append(name)
              .append("Value: ").append(value).append("<br>").append("<br>");
        }
        return sb.toString();
    }
}
