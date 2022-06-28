package com.storage.general.controller;

import com.storage.general.utility.FileUploadUtil;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.user.domain.User;
import com.storage.user.dto.NewPasswordDto;
import com.storage.user.dto.PictureDto;
import com.storage.user.model.MyUserDetails;
import com.storage.user.service.IUserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final IUserService _userService;
    @Autowired
    private final IDatabaseLoggerService logger;
    public ProfileController(IUserService _userService, IDatabaseLoggerService databaseLoggerService) {
        this._userService = _userService;
        logger = databaseLoggerService;
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
        try {
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
                PictureDto pictureDto = new PictureDto();
                model.addAttribute("picture", pictureDto);
                newPassword = new NewPasswordDto();
                model.addAttribute("newPassword", newPassword);
                user = _userService.getUserByUsername(principal.getName());
                model.addAttribute("user", user);
                return "general/profile";
            }
            logger.info("User " + user.getUsername() + " updated profile", principal);

            User userToUpdate = _userService.getUserByUsername(principal.getName());

            user = _userService.updateUser(user, userToUpdate.getId());
            Authentication authentication = new UsernamePasswordAuthenticationToken(new MyUserDetails(user), user.getPassword(), new MyUserDetails(user).getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/index";
        } catch (Exception e) {
            logger.error("Error updating profile: " + e.toString(), principal);
            return "redirect:/index";
        }
    }


    @PostMapping("/picture/update")
    public String savePicture(
            @ModelAttribute("picture") @Valid PictureDto picture,
            BindingResult bindingResult, Principal principal, Model model) throws IOException {
        try {
            if (!bindingResult.hasErrors() && picture != null && picture.getImage() != null) {
                if (!fileSizeKBTooLarge(picture.getImage(), 512)) {
                    model.addAttribute("errorPicture", "Picture size must be less than 512KB");
                    return "redirect:/profile";
                }
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getImage().getOriginalFilename()));
                User user = _userService.updatePicture(fileName, principal.getName());
                String uploadDir = "src/main/resources/static/user-photos/" + user.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, picture.getImage());
                Authentication authentication = new UsernamePasswordAuthenticationToken(new MyUserDetails(user), user.getPassword(), new MyUserDetails(user).getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User " + user.getUsername() + " updated picture", principal);
                return "redirect:/index";
            }
        }
        catch(FileSizeLimitExceededException ex){
            model.addAttribute("errorPicture", "Picture size must be less than 512KB");
            System.err.println(ex);
            logger.error("Error updating profile picture: " + ex.toString(), principal);
            return "redirect:/profile";
        }
        catch(Exception ex){
            model.addAttribute("errorPicture", "Error uploading picture!");
            System.err.println(ex);
            model.addAttribute("info", ex.toString());
            logger.error("Error updating profile picture: " + ex.toString(), principal);
            return "redirect:/profile";
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
    public static boolean fileSizeKBTooLarge(MultipartFile file, Integer size) {
        try {
            long bytes = file.getBytes().length;
            System.out.println(String.format("%,d kilobytes", bytes / 1024));
            if (bytes /(1024) > size) {
                return true;
            }
            return false;

        } catch (IOException e) {
            return true;
        }

    }
}
