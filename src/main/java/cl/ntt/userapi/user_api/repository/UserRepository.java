package cl.ntt.userapi.user_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cl.ntt.userapi.user_api.error.NotFoundException;
import cl.ntt.userapi.user_api.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    @SuppressWarnings("null")
    public Optional<User> findByIdAndActivoTrue(UUID id) throws NotFoundException;

    public Optional<User> findByEmailAndActivoTrue(String email) throws NotFoundException;

    public Optional<User> findByEmailAndPasswordAndActivoTrue(String email, String password) throws NotFoundException;

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.id = :userId")
    void createToken(@Param("userId") UUID userId, @Param("token") String token);
}
