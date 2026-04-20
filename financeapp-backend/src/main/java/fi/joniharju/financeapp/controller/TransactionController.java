package fi.joniharju.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

import fi.joniharju.financeapp.dto.TransactionRequest;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.TransactionType;
import fi.joniharju.financeapp.service.CategoryService;
import fi.joniharju.financeapp.service.TransactionService;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/new")
    public String newTransactionForm(Model model, @AuthenticationPrincipal AppUser user) {
        model.addAttribute("transactionRequest", new TransactionRequest());
        model.addAttribute("categories", categoryService.getCategories(user));
        model.addAttribute("types", TransactionType.values());
        return "transactions/form";
    }

    @PostMapping("/new")
    public String createTransaction(@Valid @ModelAttribute TransactionRequest transactionRequest,
            BindingResult bindingResult, Model model, @AuthenticationPrincipal AppUser user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories(user));
            model.addAttribute("types", TransactionType.values());
            return "transactions/form";
        }
        transactionService.createTransaction(transactionRequest, user);
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/edit")
    public String editTransactionForm(@PathVariable Long id, Model model, @AuthenticationPrincipal AppUser user) {
        model.addAttribute("transactionRequest", transactionService.getTransactionRequest(id, user));
        model.addAttribute("categories", categoryService.getCategories(user));
        model.addAttribute("types", TransactionType.values());
        model.addAttribute("transactionId", id);
        return "transactions/form";
    }

    @PostMapping("/{id}/edit")
    public String updateTransaction(@PathVariable Long id,
            @Valid @ModelAttribute TransactionRequest transactionRequest,
            BindingResult bindingResult, Model model, @AuthenticationPrincipal AppUser user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories(user));
            model.addAttribute("types", TransactionType.values());
            model.addAttribute("transactionId", id);
            return "transactions/form";
        }
        transactionService.updateTransaction(id, transactionRequest, user);
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        transactionService.deleteTransaction(id, user);
        return "redirect:/dashboard";
    }

}
