package com.codebasicz.inventory.controller;


import com.codebasicz.inventory.entity.Purchase;
import com.codebasicz.inventory.model.DropDownData;
import com.codebasicz.inventory.model.PurchaseDTO;
import com.codebasicz.inventory.service.PurchasesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchasesController {

    private final PurchasesService purchasesService;

    @GetMapping
    public ModelAndView getProducts(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        ModelAndView modelAndView = new ModelAndView("purchases/list");
        Page<PurchaseDTO> purchaseDTOPage = purchasesService.getPurchases(search, pageable);
        modelAndView.addObject("allPurchases", purchaseDTOPage.getContent());
        modelAndView.addObject("search", search);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalElements", purchaseDTOPage.getTotalElements());
        modelAndView.addObject("totalPages", purchaseDTOPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/save")
    public ModelAndView savePurchasePage() {
        ModelAndView modelAndView = new ModelAndView("purchases/save");
        PurchaseDTO purchase = purchasesService.getPurchaseById();
        List<DropDownData> productsDropDown = purchasesService.getProductsDropDown();
        List<DropDownData> suppliersDropDown = purchasesService.getSuppliersDropDown();
        modelAndView.addObject("purchase", purchase);
        modelAndView.addObject("productsDropDown", productsDropDown);
        modelAndView.addObject("suppliersDropDown", suppliersDropDown);
        return modelAndView;
    }

    @PostMapping("/save")
    public String savePurchase(@ModelAttribute Purchase purchase, RedirectAttributes redirectAttributes) {
        purchasesService.savePurchase(purchase);
        redirectAttributes.addFlashAttribute("message", "Purchase saved successfully.");
        return "redirect:/purchases";
    }

    @GetMapping("/delete")
    public String deletePurchase(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        purchasesService.deletePurchase(id);
        redirectAttributes.addFlashAttribute("message", "Purchase deleted successfully.");
        return "redirect:/purchases";
    }
}
