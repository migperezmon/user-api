package cl.ntt.userapi.user_api.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cl.ntt.userapi.user_api.validations.ValidEmail;
import cl.ntt.userapi.user_api.validations.ValidPassword;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnCreateUser;
import jakarta.validation.Valid;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    @NotBlank(message = "El campo 'name' no puede estar vacío", groups = OnCreateUser.class)
    private String nombre;

    @ValidEmail(message = "El campo 'email' no cumple con la regex", groups = OnCreateUser.class)
    @JsonProperty("email")
    @NotBlank(message = "El campo 'email' no puede estar vacío", groups = OnCreateUser.class)
    private String correo;

    @JsonProperty("password")
    @NotBlank(message = "El campo 'password' no puede estar vacío", groups = OnCreateUser.class)
    @ValidPassword(message = "El campo 'contraseña' no cumple con la regex", groups = OnCreateUser.class)
    private String password;

    @Valid
    @JsonProperty("phones")
    private List<PhoneRequest> telefonos;

}
