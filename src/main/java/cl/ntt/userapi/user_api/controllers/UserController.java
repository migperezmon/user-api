package cl.ntt.userapi.user_api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.ntt.userapi.user_api.dto.CreateUserRequest;
import cl.ntt.userapi.user_api.dto.DeleteUserRequest;
import cl.ntt.userapi.user_api.dto.PatchUserRequest;
import cl.ntt.userapi.user_api.dto.UpdateUserRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;
import cl.ntt.userapi.user_api.services.interfaces.UserService;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnCreateUser;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnDeleteUser;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnPatchUser;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnUpdateUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Validated(OnCreateUser.class) CreateUserRequest user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/v1/user/get/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/v1/user/get/id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/v1/user/get/email/{email}")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PutMapping("/v1/user/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Validated(OnUpdateUser.class) UpdateUserRequest user) {
        log.info("User: {}", user);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PatchMapping("/v1/user/patch")
    public ResponseEntity<UserResponse> patchUser(@RequestBody @Validated(OnPatchUser.class) PatchUserRequest user) {
        return ResponseEntity.ok(userService.patchUser(user));
    }

    @DeleteMapping("/v1/user/delete")
    public ResponseEntity<UserResponse> deleteUser(@RequestBody @Validated(OnDeleteUser.class) DeleteUserRequest user) {
        return ResponseEntity.ok(userService.deleteUser(user.getId()));
    }
}