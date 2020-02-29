package com.picktur.server.controllers.admin;

import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.UserDto;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.services.DtoPopulators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user_controller")
public class AdminUserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    DtoPopulators populators;

    @GetMapping("/get_users")
    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> photoPage = userRepo.findAll(pageable);
        Page<UserDto> response = photoPage.map(u -> populators.populateUserDto(u));

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_user")
    public ResponseEntity<UserDto> getUser(@RequestParam(value = "userId") String userId) {

        User user = userRepo.findById(userId).orElse(null);
        UserDto response = populators.populateUserDto(user);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/block_user")
    public ResponseEntity blockUser(@RequestParam(value = "userId") String userId) {

        User user = userRepo.findById(userId).orElse(null);
        user.setActive(false);
        userRepo.save(user);

        return new ResponseEntity<>( HttpStatus.ACCEPTED);
    }

    @GetMapping("unblock_user")
    public ResponseEntity unblockUser(@RequestParam(value = "userId") String userId) {

        User user = userRepo.findById(userId).orElse(null);
        user.setActive(true);
        userRepo.save(user);

        return new ResponseEntity<>( HttpStatus.ACCEPTED);
    }

}
