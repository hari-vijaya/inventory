package com.codebasicz.inventory.controller;

import com.codebasicz.inventory.entity.Stock;
import com.codebasicz.inventory.entity.Supplier;
import com.codebasicz.inventory.model.StockResponse;
import com.codebasicz.inventory.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StocksController {
    private final StocksService stocksService;

    @GetMapping
    public ModelAndView getProducts(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        ModelAndView modelAndView = new ModelAndView("stocks/list");
        Page<StockResponse> stockPage = stocksService.getStocks(search, pageable);
        modelAndView.addObject("allStocks", stockPage.getContent());
        modelAndView.addObject("search", search);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("totalElements", stockPage.getTotalElements());
        modelAndView.addObject("totalPages", stockPage.getTotalPages());
        return modelAndView;
    }
}
