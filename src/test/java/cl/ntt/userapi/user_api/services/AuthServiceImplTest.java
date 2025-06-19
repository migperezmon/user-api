package cl.ntt.userapi.user_api.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.error.UnauthorizedException;
import cl.ntt.userapi.user_api.model.User;
import cl.ntt.userapi.user_api.repository.UserRepository;
import cl.ntt.userapi.user_api.services.implementations.UserServiceImp;

@SpringBootTest
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void testLogin_UserNotFound() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("Mapm@12345678");

        when(userRepository.findByEmailAndActivoTrue(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> userService.login(loginRequest));
        verify(userRepository, times(1)).findByEmailAndActivoTrue(loginRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_InvalidPassword() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("Mapm@12345678");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(loginRequest.getEmail());
        user.setPassword("password123");

        when(userRepository.findByEmailAndActivoTrue(loginRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedException.class, () -> userService.login(loginRequest));
        verify(userRepository, times(1)).findByEmailAndActivoTrue(loginRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
}