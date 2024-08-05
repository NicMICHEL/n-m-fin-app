package com.pcs.controller;

import com.pcs.model.BidList;
import com.pcs.service.BidListService;
import com.pcs.web.dto.BidListDTO;
import com.pcs.web.mapper.BidListMapper;
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
public class BidListController {

    @Autowired
    private BidListService bidListService;
    @Autowired
    private BidListMapper bidListMapper;


    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidListDTOs", bidListMapper.getBidListDTOs());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String showBidListAddForm(BidListDTO bidListDTO) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String addBidList(@Valid BidListDTO bidListDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BidList bidList = bidListMapper.toBidList(bidListDTO);
            bidList.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            bidListService.save(bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showBidListUpdateForm(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        model.addAttribute(
                "bidListDTO", bidListMapper.toBidListDTO(bidListService.getById(id)));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBidList(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                                BindingResult result, Model model) throws IllegalArgumentException {
        if (!result.hasErrors()) {
            BidList bidList = bidListMapper.toBidList(bidListDTO);
            bidList.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
            bidListService.update(bidList);
            return "redirect:/bidList/list";
        }
        model.addAttribute("BidListDTO", bidListDTO);
        return "bidList/update";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBidList(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleIllegalArgumentException
            (IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException.getMessage();
    }

}
