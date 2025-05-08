package cl.ntt.userapi.user_api.services.interfaces;

import java.util.List;
import java.util.UUID;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.dto.UserRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;

public interface UserService {

    public UserResponse createUser(UserRequest userRequest);

    public List<UserResponse> getAllUsers();

    public UserResponse getUserById(UUID id);

    public UserResponse getByEmail(String email);

    public UserResponse login(LoginRequest loginRequest);

    public UserResponse updateUser(UUID id, UserRequest userRequest);

    public UserResponse patchUser(UUID id, UserRequest userRequest);

    public UserResponse deleteUser(UUID id);

}
