package cl.ntt.userapi.user_api.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cl.ntt.userapi.user_api.validations.ValidEmail;
import cl.ntt.userapi.user_api.validations.ValidPassword;
import cl.ntt.userapi.user_api.validations.ValidationGroups.OnPatchUser;
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
public class PatchUserRequest {

    @JsonProperty("id")
    @NotNull(message = "El campo 'id' no puede estar vacío", groups = OnPatchUser.class)
    private UUID id;

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("email")
    @ValidEmail(message = "El campo 'correo' no cumple con la regex", groups = OnPatchUser.class)
    private String correo;

    @JsonProperty("password")
    @ValidPassword(message = "El campo 'contraseña' no cumple con la regex", groups = OnPatchUser.class)
    private String password;

    @JsonProperty("phones")
    private List<PhoneRequest> telefonos;

}
