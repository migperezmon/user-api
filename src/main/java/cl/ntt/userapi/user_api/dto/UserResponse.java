package cl.ntt.userapi.user_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("created")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String creado;

    @JsonProperty("modified")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String modificado;

    @JsonProperty("last_login")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String ultimoLogin;

    @JsonProperty("token")
    private String token;

    @JsonProperty("is_active")
    private Boolean activo;

}
