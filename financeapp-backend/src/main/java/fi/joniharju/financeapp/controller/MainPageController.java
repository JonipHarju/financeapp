package fi.joniharju.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.service.TransactionService;

@Controller
public class MainPageController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal AppUser user) {
        model.addAttribute("transactions", transactionService.getTransactions(user));
        return "main";
    }

}
