package org.goznak.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpSession session) {
        session.setAttribute("error", getErrorMessage(request));
        return "login";
    }
    private String getErrorMessage(HttpServletRequest request) {
        Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String error;
        if (exception instanceof BadCredentialsException) {
            error = "Ошибка при вводе логина или пароля";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Ошибка при вводе логина или пароля!";
        }
        return error;
    }
}
