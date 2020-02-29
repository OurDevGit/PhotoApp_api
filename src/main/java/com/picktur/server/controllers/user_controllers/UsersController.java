package com.picktur.server.controllers.user_controllers;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.constants.FileResolution;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.UserDto;
import com.picktur.server.entities.dto.SimpleUserDto;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/public/users")
public class UsersController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/getUserList")
    public Page<SimpleUserDto> getUserList(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("surname", "name"));
        Page<SimpleUserDto> response = userRepo.findAll(pageable).map(user -> new SimpleUserDto(user));
        return response;
    }

    @GetMapping("/getUserDetails")
    public UserDto getUserdetail(@RequestBody String userId){
        UserDto response = new UserDto(userRepo.findById(userId).get());
        return response;
    }


    //update user

    @PostMapping("/update_user")
    public ResponseEntity updateUser(@AuthenticationPrincipal UserPrincipal principal, UserDto userData) throws Throwable {
        //ToDO: implement logic
        User user = userRepo.findById(principal.getId()).get();
        user.setPassword(userData.getPassword());
        user.setSurname(userData.getSurname());
        user.setName(userData.getName());
        user.setFacebook(userData.getFacebook());
        user.setEmail(userData.getEmail());
        user.setDescription(userData.getDescription());
        user.setInstagram(userData.getInstagram());
        user.setLocation(userData.getLocation());
        userRepo.save(user);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Submit avatar photo.", description = "A photo of User for Avatar.")
    @PostMapping("/submitMultiplePhoto")
    public ResponseEntity submitMultiplePhoto(@AuthenticationPrincipal UserPrincipal principal,
                                              @RequestPart(value = "files", required = true) final MultipartFile file) {
        String avatarName = "";
        String iconName = "";

        try {
            avatarName = fileStorageService.uploadAndPersistAvatarAndIcon(file, FileResolution.AV_);
            iconName = fileStorageService.uploadAndPersistAvatarAndIcon(file, FileResolution.ICO_);
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userRepo.findById(principal.getId()).get();
        user.setAvatar(avatarName);
        user.setIcon(iconName);

        userRepo.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
