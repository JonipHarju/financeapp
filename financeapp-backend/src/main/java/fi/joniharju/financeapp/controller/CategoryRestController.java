package fi.joniharju.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.joniharju.financeapp.dto.CategoryResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(categoryService.getCategories(user));
    }

}
