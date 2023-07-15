package com.rogerfitness.workoutsystem.controllers;

import com.rogerfitness.workoutsystem.constants.PrometheusConstants;
import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.jpa.searchcriteria.UserSearchCriteria;
import com.rogerfitness.workoutsystem.responses.ApiErrorResponse;
import com.rogerfitness.workoutsystem.responses.ApiPageableResponse;
import com.rogerfitness.workoutsystem.service.UserService;
import com.rogerfitness.workoutsystem.utilities.metrics.RequestCounter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RequestCounter requestCounter;

    @Autowired
    public UserController(UserService userService, RequestCounter requestCounter) {
        this.userService = userService;
        this.requestCounter = requestCounter;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() throws Exception {
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
                    @ApiResponse(code = 500, message = "Internal Server Error", response = ApiErrorResponse.class)
            })
    public ResponseEntity<ApiPageableResponse<UserResponseDto>> fetchUsers(
            @RequestParam(required = false) @ApiParam("User's id") Integer userIdSeq,
            @RequestParam(required = false) @ApiParam("User name") String name,
            @RequestParam(required = false) @ApiParam("User email") String email,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") @ApiParam(allowableValues = "ASC, DESC") String sortOrder
    ) throws Exception {
        log.info("GET endpoint fetchUsers --START");
        requestCounter.incrementCount(PrometheusConstants.FETCH_USERS_VOLUME);
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
        try {
            return ResponseEntity.ok(new ApiPageableResponse<>(userService.fetchUsers(userSearchCriteria)));
        } catch (Exception e) {
            requestCounter.incrementCount(PrometheusConstants.FETCH_USERS_ERROR);
            log.error("[UserController] fetchUsers() exception occurs", e);
            throw e;
        }
    }
}
