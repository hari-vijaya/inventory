package com.codebasicz.inventory.controller;

import com.codebasicz.inventory.entity.Product;
import com.codebasicz.inventory.service.ProductsService;
import com.codebasicz.inventory.util.InventoryUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    public ModelAndView getProducts(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        System.out.print("test2");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        ModelAndView modelAndView = new ModelAndView("products/list");
        Page<Product> productPage = productsService.getProducts(search, pageable);
        modelAndView.addObject("allProducts", productPage.getContent());
        modelAndView.addObject("search", search);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalElements", productPage.getTotalElements());
        modelAndView.addObject("totalPages", productPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/save")
    public ModelAndView saveProductPage(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView modelAndView = new ModelAndView("products/save");
        Product product = productsService.getProductById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productsService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product saved successfully.");
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        productsService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully.");
        return "redirect:/products";
    }

    @GetMapping("/download-template")
    public void downloadImportProductCsvTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products.csv");

        PrintWriter writer = response.getWriter();
        writer.println("Name,Category,Brand,Price");
        writer.flush();
    }

    @PostMapping("/upload")
    public String importProducts(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        List<Product> products = InventoryUtils.parseCSV(new String(file.getBytes()), Product.class);
        productsService.saveAllProducts(products);
        attributes.addFlashAttribute("message", "File uploaded successfully.");
        return "redirect:/products";
    }
}
