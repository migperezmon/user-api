package cl.ntt.userapi.user_api.services.implementations;

import static cl.ntt.userapi.user_api.utils.Utils.formattedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.dto.PatchUserRequest;
import cl.ntt.userapi.user_api.dto.UpdateUserRequest;
import cl.ntt.userapi.user_api.dto.CreateUserRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;
import cl.ntt.userapi.user_api.error.NotFoundException;
import cl.ntt.userapi.user_api.error.UnauthorizedException;
import cl.ntt.userapi.user_api.model.Telephone;
import cl.ntt.userapi.user_api.model.User;
import cl.ntt.userapi.user_api.repository.UserRepository;
import cl.ntt.userapi.user_api.services.interfaces.UserService;
import cl.ntt.userapi.user_api.utils.JwtUtil;
import cl.ntt.userapi.user_api.utils.Mapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImp implements UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public UserServiceImp(UserRepository userRepository, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	private static final String USER_NOT_FOUND = "Usuario no encontrado";
	private static final String INVALID_USERNAME_OR_PASSWORD = "Usuario o contraseña inválidos";

	@Override
	public UserResponse createUser(CreateUserRequest userRequest) {
		if (userExists(userRequest.getCorreo())) {
			throw new DataIntegrityViolationException("El correo ya está registrado");
		}
		User user = Mapper.userRequestToUser(userRequest);
		user.getTelephones().forEach(telephone -> telephone.setUser(user));
		User savedUser = userRepository.save(user);
		generateToken(savedUser.getId());
		return buildUserResponse(savedUser);
	}

	private void generateToken(UUID id) {
		String token = jwtUtil.generateToken(id.toString());
		userRepository.createToken(id, token);
	}

	private boolean userExists(String email) {
		return userRepository.findByEmailAndActivoTrue(email).isPresent();
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(this::buildUserResponse)
				.toList();
	}

	@Override
	public UserResponse getUserById(UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		return buildUserResponse(user);
	}

	@Override
	public UserResponse getByEmail(String email) {
		User user = userRepository.findByEmailAndActivoTrue(email)
				.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		user.setToken(null);
		return buildUserResponse(user);
	}

	@Override
	public UserResponse login(LoginRequest loginRequest) {
		User user = userRepository.findByEmailAndActivoTrue(loginRequest.getEmail())
				.orElseThrow(() -> new UnauthorizedException(INVALID_USERNAME_OR_PASSWORD));
		if (!user.getPassword().equals(loginRequest.getPassword())) {
			throw new UnauthorizedException(INVALID_USERNAME_OR_PASSWORD);
		}
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		return buildUserResponse(user);
	}

	@Override
	public UserResponse updateUser(UpdateUserRequest userRequest) {
		User user = userRepository.findById(userRequest.getId())
				.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		user.setName(userRequest.getNombre());
		user.setEmail(userRequest.getCorreo());
		user.setPassword(userRequest.getPassword());

		updateTelephones(userRequest, user);

		User updatedUser = userRepository.save(user);
		updatedUser.setToken(null);
		return buildUserResponse(updatedUser);

	}

	@Override
	public UserResponse patchUser(PatchUserRequest userRequest) {
		User user = userRepository.findById(userRequest.getId())
				.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		log.info("Patch User patchMethod: {}", user);
		// Actualiza solo los campos presentes en la solicitud
		if (userRequest.getNombre() != null && !userRequest.getNombre().isBlank()) {
			user.setName(userRequest.getNombre());
		}
		if (userRequest.getCorreo() != null && !userRequest.getCorreo().isBlank()) {
			user.setEmail(userRequest.getCorreo());
		}
		if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
			user.setPassword(userRequest.getPassword());
		}
		patchTelephones(userRequest, user);

		User updatedUser = userRepository.save(user);
		updatedUser.setToken(null);
		return buildUserResponse(updatedUser);
	}

	@Override
	public UserResponse deleteUser(UUID id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		if (!user.isActivo()) {
			throw new UnauthorizedException("El usuario ya está eliminado");
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = (User) principal;
		if (currentUser instanceof User && currentUser.getId().equals(user.getId())) {
			throw new UnauthorizedException("No puedes eliminar tu propio usuario");
		}

		user.setActivo(false);
		user.setDeletedAt(LocalDateTime.now());
		user.getTelephones().forEach(telephone -> telephone.setDeletedAt(LocalDateTime.now()));
		userRepository.save(user);
		user.setToken(null);
		return buildUserResponse(user);
	}

	public void updateTelephones(UpdateUserRequest userRequest, User user) {
		List<Telephone> existingTelephones = user.getTelephones();

		if (userRequest.getTelefonos() == null || userRequest.getTelefonos().isEmpty()) {
			existingTelephones.clear();
			return;
		}

		List<Telephone> newTelephones = userRequest.getTelefonos().stream()
				.map(Mapper::phoneRequestToTelephone)
				.toList();

		existingTelephones.removeIf(existing -> {
			boolean existsInNewList = newTelephones.stream()
					.anyMatch(newPhone -> newPhone.getId() != null && newPhone.getId().equals(existing.getId()));
			return !existsInNewList;
		});

		newTelephones.forEach(newPhone -> {
			if (newPhone.getId() != null) {
				existingTelephones.stream()
						.filter(existing -> existing.getId().equals(newPhone.getId()))
						.findFirst()
						.ifPresentOrElse(
								existing -> {
									existing.setNumber(newPhone.getNumber());
									existing.setCityCode(newPhone.getCityCode());
									existing.setCountryCode(newPhone.getCountryCode());
									existing.setUpdatedAt(LocalDateTime.now());
								},
								() -> {
									// Si no existe, lo agregamos como nuevo
									newPhone.setUser(user);
									newPhone.setCreatedAt(LocalDateTime.now());
									existingTelephones.add(newPhone);
								});
			} else {
				// Si no tiene ID, es un teléfono nuevo
				newPhone.setUser(user);
				newPhone.setCreatedAt(LocalDateTime.now());
				existingTelephones.add(newPhone);
			}
		});
	}

	public void patchTelephones(PatchUserRequest userRequest, User user) {
		log.info("Patch User patchTelephones: {}", user);
		try {
			List<Telephone> existingTelephones = user.getTelephones();

			if (userRequest.getTelefonos() == null || userRequest.getTelefonos().isEmpty()) {
				return;
			}

			// Filtramos y mapeamos los teléfonos que tienen ID y que están en el request
			List<Telephone> telephonesToUpdate = userRequest.getTelefonos().stream()
					.filter(phoneRequest -> phoneRequest.getId() != null)
					.map(Mapper::phoneRequestToTelephone)
					.toList();

			// Actualiza los teléfonos existentes que coincidan con los IDs de la solicitud
			telephonesToUpdate.forEach(newPhone -> {
				existingTelephones.stream()
						.filter(existing -> existing.getId().equals(newPhone.getId()))
						.findFirst()
						.ifPresentOrElse(
								existing -> {
									existing.setNumber(newPhone.getNumber());
									existing.setCityCode(newPhone.getCityCode());
									existing.setCountryCode(newPhone.getCountryCode());
									existing.setUpdatedAt(LocalDateTime.now());
								},
								() -> log.warn("El teléfono con ID {} no existe y será ignorado.", newPhone.getId()));
			});
		} catch (Exception e) {
			log.error("Error al actualizar los teléfonos: {}", e.getMessage(), e);
		}
	}

	private UserResponse buildUserResponse(User user) {
		return UserResponse.builder()
				.id(user.getId().toString())
				.creado(formattedDate(user.getCreatedAt()))
				.modificado(user.getUpdatedAt() != null ? formattedDate(user.getUpdatedAt()) : null)
				.ultimoLogin(user.getLastLogin() != null ? formattedDate(user.getLastLogin()) : null)
				.activo(user.isActivo())
				.token(user.getToken() != null ? user.getToken() : null)
				.build();
	}

}
