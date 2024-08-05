package com.pcs.controller;

import com.pcs.model.Trade;
import com.pcs.service.TradeService;
import com.pcs.web.dto.TradeDTO;
import com.pcs.web.mapper.TradeMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeMapper tradeMapper;


    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("tradeDTOs", tradeMapper.getTradeDTOs());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String showTradeAddForm(TradeDTO tradeDTO) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String addTrade(@Valid TradeDTO tradeDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Trade trade = tradeMapper.toTrade(tradeDTO);
            trade.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            tradeService.save(trade);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showTradeUpdateForm(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        model.addAttribute(
                "tradeDTO", tradeMapper.toTradeDTO(tradeService.getById(id)));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                              BindingResult result, Model model) throws IllegalArgumentException {
        if (!result.hasErrors()) {
            Trade trade = tradeMapper.toTrade(tradeDTO);
            trade.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
            tradeService.update(trade);
            return "redirect:/trade/list";
        }
        // maj à tradeDTO cf aussi  bidlist controll l 77
        model.addAttribute("tradeDTO", tradeDTO);
        return "trade/update";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleIllegalArgumentException
            (IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException.getMessage();
    }

}
