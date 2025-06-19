package cl.ntt.userapi.user_api.utils;

import cl.ntt.userapi.user_api.dto.PhoneRequest;
import cl.ntt.userapi.user_api.dto.CreateUserRequest;
import cl.ntt.userapi.user_api.model.Telephone;
import cl.ntt.userapi.user_api.model.User;

public class Mapper {

    private Mapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Telephone phoneRequestToTelephone(PhoneRequest telephoneRequest) {
        return Telephone.builder()
                .id(telephoneRequest.getId())
                .number(telephoneRequest.getNumero())
                .cityCode(telephoneRequest.getCodigoCiudad())
                .countryCode(telephoneRequest.getCodigoPais())
                .build();
    }

    public static User userRequestToUser(CreateUserRequest userRequest) {
        return User.builder()
                .name(userRequest.getNombre())
                .email(userRequest.getCorreo())
                .password(userRequest.getPassword())
                .telephones(userRequest.getTelefonos().stream()
                        .map(Mapper::phoneRequestToTelephone).toList())
                .build();
    }

}
