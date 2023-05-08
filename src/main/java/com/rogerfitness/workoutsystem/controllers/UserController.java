package com.rogerfitness.workoutsystem.controllers;

import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.searchcriteria.UserSearchCriteria;
import com.rogerfitness.workoutsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers()throws Exception{
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping(value = "/fetchUsers")
    public ResponseEntity<Page<UserResponseDto>> fetchUsers(
            @RequestParam(required = false) Integer userIdSeq,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) throws Exception {
        log.info("GET endpoint fetchUsers --START");
        UserSearchCriteria userSearchCriteria = UserSearchCriteria.builder()
                .userIdSeq(userIdSeq)
                .name(name)
                .email(email)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        return new ResponseEntity<>(userService.fetchUsers(userSearchCriteria), HttpStatus.OK);
    }
}
