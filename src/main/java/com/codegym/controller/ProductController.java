package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping
    public String showList(Model model){
        Iterable<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "list";
    }
    @GetMapping("/create")
    public String showFormCreate(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "create";
    }
    @PostMapping("/create")
    public String save(@ModelAttribute("product") Product product, Model model){
        productService.save(product);
        model.addAttribute("message", "product has been added");
        return "redirect:/products";
    }
    @GetMapping("/update/{id}")
    public String showFormUpdate(@PathVariable Long id, Model model){
        Optional<Product> product = productService.findById(id);
        if(!product.isPresent()){
            return "error";
        }
        model.addAttribute("product", product);
        return "update";
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(!product.isPresent()){
            return "error";
        }
        productService.delete(id);
        return "redirect:/products";
    }
}
