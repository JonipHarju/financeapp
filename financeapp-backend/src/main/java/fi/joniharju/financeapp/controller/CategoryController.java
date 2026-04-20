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

import fi.joniharju.financeapp.dto.CategoryRequest;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String categoriesPage(Model model, @AuthenticationPrincipal AppUser user) {
        model.addAttribute("categories", categoryService.getCategories(user));
        model.addAttribute("categoryRequest", new CategoryRequest());
        return "categories/index";
    }

    @PostMapping("/new")
    public String createCategory(@Valid @ModelAttribute CategoryRequest categoryRequest,
            BindingResult bindingResult, Model model, @AuthenticationPrincipal AppUser user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories(user));
            return "categories/index";
        }
        categoryService.createCategory(categoryRequest, user);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, @AuthenticationPrincipal AppUser user) {
        categoryService.deleteCategory(id, user);
        return "redirect:/categories";
    }

}
