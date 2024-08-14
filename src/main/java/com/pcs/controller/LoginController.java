package com.pcs.controller;

import com.pcs.configuration.ConnectedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @Autowired
    private ConnectedUser connectedUser;

    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/accessDeniedPage")
    public String showAccessDeniedPage(Model model, UsernamePasswordAuthenticationToken token) {
        System.out.println("showAccessDeniedPage called");
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        model.addAttribute("connectedUserName", connectedUser.getUsernamePasswordLoginInfo(token));
        return "403";
    }

}
