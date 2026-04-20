package fi.joniharju.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.joniharju.financeapp.dto.TransactionResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(transactionService.getTransactions(user));
    }

}
