package cl.ntt.userapi.user_api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ntt.userapi.user_api.dto.UserRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;
import cl.ntt.userapi.user_api.error.BadRequestException;
import cl.ntt.userapi.user_api.services.interfaces.UserService;
import cl.ntt.userapi.user_api.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
        if (!Utils.isValidEmail(user.getCorreo())) {
            throw new BadRequestException("El correo no es válido");
        }
        if (!Utils.isValidPassword(user.getPassword())) {
            throw new BadRequestException("La contraseña no es válida");
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/v1/user/get/all")
    public ResponseEntity<List<UserResponse>> getMethodName() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/v1/user/get/id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return ResponseEntity.ok(userService.getUserById(uuid));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El UUID no es válido");
        }
    }

    @GetMapping("/v1/user/get/email")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam("email") String email) {
        if (!Utils.isValidEmail(email)) {
            throw new BadRequestException("El correo no es válido");
        }
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PutMapping("/v1/user/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) {
        log.info("User: {}", user);
        if (!Utils.isValidEmail(user.getCorreo())) {
            throw new BadRequestException("El correo no es válido");
        }
        if (!Utils.isValidPassword(user.getPassword())) {
            throw new BadRequestException("La contraseña no es válida");
        }
        if (user.getId() == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        try {
            UUID uuid = UUID.fromString(user.getId());
            return ResponseEntity.ok(userService.updateUser(uuid, user));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El UUID no es válido");
        }
    }

    @PatchMapping("/v1/user/patch")
    public ResponseEntity<UserResponse> patchUser(@RequestBody UserRequest user) {
        log.info("Patch User: {}", user);
        if (!Utils.isValidEmail(user.getCorreo())) {
            throw new BadRequestException("El correo no es válido");
        }
        if (!Utils.isValidPassword(user.getPassword())) {
            throw new BadRequestException("La contraseña no es válida");
        }
        if (user.getId() == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        try {
            UUID uuid = UUID.fromString(user.getId());
            return ResponseEntity.ok(userService.patchUser(uuid, user));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El UUID no es válido");
        }
    }

    @DeleteMapping("/v1/user/delete")
    public ResponseEntity<UserResponse> deleteUser(@RequestBody UserRequest user) {
        if (user.getId() == null) {
            throw new BadRequestException("El UUID no puede ser nulo");
        }
        try {
            UUID uuid = UUID.fromString(user.getId());            
            return ResponseEntity.ok(userService.deleteUser(uuid));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El UUID no es válido");
        }
    }
}