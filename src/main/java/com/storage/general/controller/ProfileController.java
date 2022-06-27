package com.storage.general.controller;

import com.storage.general.utility.FileUploadUtil;
import com.storage.user.domain.User;
import com.storage.user.dto.NewPasswordDto;
import com.storage.user.dto.PictureDto;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final IUserService _userService;

    public ProfileController(IUserService _userService) {
        this._userService = _userService;
    }


    @GetMapping()
    public String showProfile(Principal principal, Model model) {
        PictureDto pictureDto = new PictureDto();
        model.addAttribute("picture", pictureDto);
        NewPasswordDto newPassword = new NewPasswordDto();
        model.addAttribute("newPassword", newPassword);
        User user = _userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "general/profile";
    }

    @PostMapping(value = {"/information/update"})
    public String editEmployee(@ModelAttribute("user") @Valid User user,
                               @ModelAttribute("newPassword") @Valid NewPasswordDto newPassword,
                               BindingResult bindingResult,
                               Principal principal, Model model) {
        var errorFound = false;
        if (newPassword.getPassword() != null && !newPassword.getPassword().equals("")) {
            if (newPassword.getPassword().length() < 6) {
                model.addAttribute("errorPassword", "Password must be at least 6 characters long");
                errorFound = true;
            } else if (!newPassword.getPassword().equals(newPassword.getConfirmPassword())) {
                model.addAttribute("errorPassword", "Passwords do not match");
                errorFound = true;
            } else {
                user.setPassword(newPassword.getPassword());
            }
        }
        if (user.getEmail().isBlank()) {
            model.addAttribute("errorEmail", "Email cannot be blank");
            errorFound = true;
        }
        if (!user.getUsername().equals(principal.getName())) {
            if (user.getUsername().length() < 6) {
                model.addAttribute("errorUsername", "Username must be at least 6 characters long!");
                errorFound = true;
            }
            if (_userService.checkIfUserExistsByUsername(user.getUsername())) {
                model.addAttribute("errorUsername", "Username already taken!");
                errorFound = true;
            }
        }

        if (errorFound) {
            model.addAttribute("picture", new PictureDto());
            model.addAttribute("user", user);
            return "general/profile";
        }


        User userToUpdate = _userService.getUserByUsername(principal.getName());

        _userService.updateUser(user, userToUpdate.getId());
        if (bindingResult.hasErrors()) {
            return "redirect:/profile";
        }
        return "redirect:/logout";
    }


    @PostMapping("/picture/update")
    public String savePicture(
            @ModelAttribute("picture") @Valid PictureDto picture,
            BindingResult bindingResult, Principal principal) throws IOException {
        if (!bindingResult.hasErrors() && picture != null && picture.getImage() != null) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getImage().getOriginalFilename()));
            User user = _userService.updatePicture(fileName, principal.getName());
            String uploadDir = "user-photos/" + user.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, picture.getImage());
            return "redirect:/logout";
        }
        return "redirect:/profile";
    }

    @GetMapping(value = {"/disable/{userId}"})
    public String disableUser(@PathVariable("userId") int userToDisableId, Principal principal, Model model) {
        User user = _userService.getUserByUsername(principal.getName());
        if (user.getId() != userToDisableId) {
            return "redirect:/profile";
        }
        _userService.disableUser(userToDisableId);
        return "redirect:/logout";
    }

}
