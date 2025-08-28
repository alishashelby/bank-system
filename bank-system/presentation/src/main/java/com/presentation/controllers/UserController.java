package com.presentation.controllers;

import com.businesslogic.dto.user.RegisterUserRequest;
import com.businesslogic.dto.user.UserDTO;
import com.businesslogic.exceptions.BLException;
import com.businesslogic.services.user.UserService;
import com.dataaccess.enums.Gender;
import com.dataaccess.enums.HairColor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "is responsible for user management")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create the user", description = "Allows to register new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User is successfully created"),
            @ApiResponse(responseCode = "400", description = "Incorrect user data"),
            @ApiResponse(responseCode = "409", description = "User with this login already exists")
    })
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserRequest request) throws BLException {
        userService.createUser(request.getLogin(),
                request.getName(),
                request.getAge(),
                request.getGender(),
                request.getHairColor());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{login}")
    @Operation(summary = "Getting user information", description = "In JSON format")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Information received successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "JSON generation error")
    })
    public ResponseEntity<UserDTO> getUserInfo(
            @Parameter(description = "User's login", example = "alishashelby")
            @PathVariable("login") String login
    ) throws Exception {
        var userInfo = userService.getUserInfo(login);
        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO.toUserDTO(userInfo), HttpStatus.OK);
    }

    @PostMapping("/makeFriends/{userLogin}/{friendLogin}")
    @Operation(summary = "Adding as a friend")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Friendship successfully established"),
            @ApiResponse(responseCode = "400", description = "You can't add yourself"),
            @ApiResponse(responseCode = "404", description = "One of the users was not found"),
            @ApiResponse(responseCode = "409", description = "Users are already friends")
    })
    public ResponseEntity<?> makeFriends(
            @Parameter(description = "User's login", example = "alisha")
            @PathVariable("userLogin") String userLogin,
            @Parameter(description = "Friend's login", example = "polina")
            @PathVariable("friendLogin") String friendLogin
    ) throws BLException {
        userService.makeFriends(userLogin, friendLogin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unmakeFriends/{userLogin}/{friendLogin}")
    @Operation(summary = "Unfriending")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Friendship successfully ended"),
            @ApiResponse(responseCode = "400", description = "You can't delete yourself"),
            @ApiResponse(responseCode = "404", description = "One of the users was not found"),
            @ApiResponse(responseCode = "409", description = "Users are already unfriend")
    })
    public ResponseEntity<?> unmakeFriends(
            @Parameter(description = "User's login", example = "alisha")
            @PathVariable("userLogin") String userLogin,
            @Parameter(description = "Friend's login", example = "polina")
            @PathVariable("friendLogin") String friendLogin
    ) throws BLException {
        userService.unmakeFriends(userLogin, friendLogin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/friends/{userId}")
    @Operation(summary = "Getting logins of all friends of a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Friends list received"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Collection<String>> getFriendsByUserId(
            @Parameter(description = "User's UUID", example = "c4cc9b48-a58a-4632-85ac-444b24187f9f")
            @PathVariable("userId") UUID userId
    ) throws BLException {
        Collection<String> rez = userService.getAllFriendsByUserId(userId);
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter users by: hair color or gender")
    @ApiResponse(responseCode = "200", description = "Users list received")
    public ResponseEntity<Collection<String>> getUsersFiltered(
            @Parameter(description = "Hair color", example = "AUBURN")
            @RequestParam(name = "hairColor", required = false)HairColor hairColor,
            @Parameter(description = "Gender", example = "FEMALE")
            @RequestParam(name = "gender", required = false)Gender gender) {
        Collection<String> rez = userService.getUsersFiltered(hairColor, gender);
        return new ResponseEntity<>(rez, HttpStatus.OK);
    }
}
