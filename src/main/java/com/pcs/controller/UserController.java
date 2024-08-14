package com.pcs.controller;

import com.pcs.configuration.ConnectedUser;
import com.pcs.model.User;
import com.pcs.service.UniqueUserNameValidationService;
import com.pcs.service.UserService;
import com.pcs.web.dto.UserDTO;
import com.pcs.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UniqueUserNameValidationService uniqueUserNameValidationService;
    @Autowired
    private ConnectedUser connectedUser;


    @RequestMapping("/user/list")
    public String home(Model model, UsernamePasswordAuthenticationToken token) {
        model.addAttribute("userDTOs", userMapper.getUserDTOs());
        model.addAttribute("connectedUserName", connectedUser.getUsernamePasswordLoginInfo(token));
        return "user/list";
    }

    @GetMapping("/user/add")
    public String showUserAddForm(UserDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String addUser(@Valid UserDTO userDTO, BindingResult result, Model model) {
        String err = uniqueUserNameValidationService.validateUserDTOUserName(userDTO);
        if (!result.hasErrors() && err.isEmpty()) {
            User user = userMapper.toUser(userDTO);
            userService.save(user);
            return "redirect:/user/list";
        }
        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUserUpdateForm(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        model.addAttribute("userDTO", userMapper.toUserDTO(userService.getById(id)));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDTO,
                             BindingResult result, Model model) throws IllegalArgumentException {
        String initialUserName = userService.getById(id).getUsername();
        String err = "";
        if (!Objects.equals(initialUserName, userDTO.getUsername())) {
            err = uniqueUserNameValidationService.validateUserDTOUserName(userDTO);
        }
        if (!result.hasErrors() && err.isEmpty()) {
            User user = userMapper.toUser(userDTO);
            userService.update(user);
            return "redirect:/user/list";
        }
        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }
        model.addAttribute("userDTO", userDTO);
        return "user/update";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) throws IllegalArgumentException {
        userService.deleteById(id);
        return "redirect:/user/list";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
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
