package com.codebasicz.inventory.controller;

import com.codebasicz.inventory.entity.Supplier;
import com.codebasicz.inventory.service.SuppliersService;
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
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SuppliersController {

    private final SuppliersService suppliersService;

    @GetMapping
    public ModelAndView getProducts(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        ModelAndView modelAndView = new ModelAndView("suppliers/list");
        Page<Supplier> supplierPage = suppliersService.getSuppliers(search, pageable);
        modelAndView.addObject("allSuppliers", supplierPage.getContent());
        modelAndView.addObject("search", search);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalElements", supplierPage.getTotalElements());
        modelAndView.addObject("totalPages", supplierPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/save")
    public ModelAndView saveSupplierPage(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView modelAndView = new ModelAndView("suppliers/save");
        Supplier supplier = suppliersService.getSupplierById(id);
        modelAndView.addObject("supplier", supplier);
        return modelAndView;
    }

    @PostMapping("/save")
    public String saveSupplier(@ModelAttribute Supplier supplier, RedirectAttributes redirectAttributes) {
        suppliersService.saveSupplier(supplier);
        redirectAttributes.addFlashAttribute("message", "Supplier saved successfully.");
        return "redirect:/suppliers";
    }

    @GetMapping("/delete")
    public String deleteSupplier(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        suppliersService.deleteSupplier(id);
        redirectAttributes.addFlashAttribute("message", "Supplier deleted successfully.");
        return "redirect:/suppliers";
    }

    @GetMapping("/download-template")
    public void downloadImportSupplierCsvTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=suppliers.csv");
        PrintWriter writer = response.getWriter();
        writer.println("supplierName,contactName,phone,email");
        writer.flush();
    }

    @PostMapping("/upload")
    public String importProducts(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        List<Supplier> suppliers = InventoryUtils.parseCSV(new String(file.getBytes()), Supplier.class);
        suppliersService.saveAllSuppliers(suppliers);
        attributes.addFlashAttribute("message", "File uploaded successfully.");
        return "redirect:/suppliers";
    }
}
