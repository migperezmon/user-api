package cl.ntt.userapi.user_api.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cl.ntt.userapi.user_api.validations.ValidEmail;
import cl.ntt.userapi.user_api.validations.ValidPassword;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnUpdateUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateUserRequest {

    @JsonProperty("id")
    @NotNull(message = "El campo 'id' no puede ser nulo", groups = OnUpdateUser.class)
    private UUID id;

    @JsonProperty("name")
    @NotBlank(message = "El campo 'nombre' no puede estar vacío", groups = OnUpdateUser.class)
    private String nombre;

    @JsonProperty("email")
    @NotBlank(message = "El campo 'correo' no puede estar vacío", groups = OnUpdateUser.class)
    @ValidEmail(message = "El campo 'correo' no cumple con la regex", groups = OnUpdateUser.class)
    private String correo;

    @JsonProperty("password")
    @NotBlank(message = "El campo 'contraseña' no puede estar vacío", groups = OnUpdateUser.class)
    @ValidPassword(message = "El campo 'contraseña' no cumple con la regex", groups = OnUpdateUser.class)
    private String password;

    @Valid
    @JsonProperty("phones")
    private List<PhoneRequest> telefonos;

}
