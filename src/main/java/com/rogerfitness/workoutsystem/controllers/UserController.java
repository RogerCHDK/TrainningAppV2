package com.rogerfitness.workoutsystem.controllers;

import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.jpa.searchcriteria.UserSearchCriteria;
import com.rogerfitness.workoutsystem.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api
@Slf4j
@RestController
@RequestMapping("api/v1/user")
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
    @ApiOperation(value = "Endpoint to fetch users", nickname = "getUser")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful", response = UserResponseDto.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = String.class),
                    @ApiResponse(code = 401, message = "UnAuthorized", response = String.class),
                    @ApiResponse(code = 404, message = "Not Found", response = String.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
            })
    public ResponseEntity<Page<UserResponseDto>> fetchUsers(
            @RequestParam(required = false) @ApiParam("User's id") Integer userIdSeq,
            @RequestParam(required = false) @ApiParam("User name") String name,
            @RequestParam(required = false) @ApiParam("User email")String email,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") @ApiParam(allowableValues = "ASC, DESC") String sortOrder
    ) throws Exception {
        log.info("GET endpoint fetchUsers --START");
        UserSearchCriteria userSearchCriteria = UserSearchCriteria.builder()
                .userIdSeq(userIdSeq)
                .name(name)
                .email(email)
                .weight(weight)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        return new ResponseEntity<>(userService.fetchUsers(userSearchCriteria), HttpStatus.OK);
    }
}
