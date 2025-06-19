package cl.ntt.userapi.user_api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import cl.ntt.userapi.user_api.validations.ValidationGroups.OnDeleteUser;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {
    
    @JsonProperty("id")
    @NotNull(message = "El campo 'id' no puede estar vacío", groups = OnDeleteUser.class)
    private UUID id;
}
