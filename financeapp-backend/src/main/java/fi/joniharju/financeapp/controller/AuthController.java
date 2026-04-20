package fi.joniharju.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

import fi.joniharju.financeapp.dto.RegisterRequest;
import fi.joniharju.financeapp.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            authService.register(registerRequest);
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

}
