package cl.ntt.userapi.user_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("email")
    @Email(message = "El campo 'email' debe ser un correo electrónico válido")
    @NotBlank(message = "El campo 'email' no puede estar vacío")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "El campo 'password' no puede estar vacío")
    private String password;

}
