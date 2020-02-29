package com.picktur.server.controllers.admin;

import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.SimpleUserDto;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.services.DtoPopulators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user_controller")
public class UserAdminController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    DtoPopulators populators;

    @GetMapping("/list_all_user_pages")
    public ResponseEntity<Page<SimpleUserDto>> getAllPhotosForUser(@RequestBody Pageable request) {

        Page<User> userPage = userRepo.findAll(request);
        Page<SimpleUserDto> response = userPage.map(user -> new SimpleUserDto(user));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("user_activate")
    public ResponseEntity userActivate(@RequestBody String usedId){
        User user = userRepo.findById(usedId).get();
        user.setActive(true);
        userRepo.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @PostMapping("user_deactivate")
    public ResponseEntity userDeactivate(@RequestBody String usedId){
        User user = userRepo.findById(usedId).get();
        user.setActive(false);
        userRepo.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
