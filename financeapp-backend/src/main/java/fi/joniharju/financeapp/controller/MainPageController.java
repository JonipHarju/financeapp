package fi.joniharju.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

import fi.joniharju.financeapp.dto.TransactionResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.TransactionType;
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
        List<TransactionResponse> transactions = transactionService.getTransactions(user);
        BigDecimal income = transactions.stream().filter(t -> t.getType() == TransactionType.INCOME).map(TransactionResponse::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expenses = transactions.stream().filter(t -> t.getType() == TransactionType.EXPENSE).map(TransactionResponse::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalIncome", income);
        model.addAttribute("totalExpenses", expenses);
        model.addAttribute("balance", income.subtract(expenses));
        return "main";
    }

}
