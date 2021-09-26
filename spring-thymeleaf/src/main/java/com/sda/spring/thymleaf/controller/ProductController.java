package com.sda.spring.thymleaf.controller;

import com.sda.spring.thymleaf.model.Product;
import com.sda.spring.thymleaf.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// mvc controller
@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/products

    // map url to controller method
    @GetMapping("/products")
    public String showProductsPage(Model model) {
        // return a html page with products
        // add list of products
        List<Product> products = productService.findAll();
        model.addAttribute("productsInView", products);

        // resolved by the view resolver
        return "index";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditForm(Model model,
                               @PathVariable Long id) {

        model.addAttribute("product", productService.findById(id));
        return "product-edit";
    }

    @PostMapping("/products/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Product product) {

            productService.update(product);
            return "redirect:/products";
    }
    
    @GetMapping("/products/{id}/delete")
    public String delete(@PathVariable long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
