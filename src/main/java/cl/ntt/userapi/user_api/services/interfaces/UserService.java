package cl.ntt.userapi.user_api.services.interfaces;

import java.util.List;
import java.util.UUID;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.dto.PatchUserRequest;
import cl.ntt.userapi.user_api.dto.UpdateUserRequest;
import cl.ntt.userapi.user_api.dto.CreateUserRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;

public interface UserService {

    public UserResponse createUser(CreateUserRequest userRequest);

    public List<UserResponse> getAllUsers();

    public UserResponse getUserById(UUID id);

    public UserResponse getByEmail(String email);

    public UserResponse login(LoginRequest loginRequest);

    public UserResponse updateUser(UpdateUserRequest userRequest);

    public UserResponse patchUser(PatchUserRequest userRequest);

    public UserResponse deleteUser(UUID id);

}
