package cl.ntt.userapi.user_api.error;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.ntt.userapi.user_api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    private static final String NOT_AUTHORIZED = "Acceso no autorizado.";
    private static final String NOT_FOUND_OBJECT = "No encontrado.";
    private static final String BAD_REQUEST = "Solicitud incorrecta.";

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .message("Error interno del servidor.")
                .build());
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleJdbcSQLIntegrityConstraintViolationException(
            JdbcSQLIntegrityConstraintViolationException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .message("El correo ya está registrado.")
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String reason = ex.getMessage() != null ? ex.getMessage() : "Error de integridad de datos.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .message(reason)
                .build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        log.error("ServiceException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode()).body(ErrorResponse.builder()
                .message(ex.getReason())
                .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("UnauthorizedException: {}", ex.getMessage(), ex);
        String reason = ex.getReason() != null ? ex.getReason() : NOT_AUTHORIZED;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                .message(reason)
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage(), ex);
        String reason = ex.getReason() != null ? ex.getReason() : NOT_FOUND_OBJECT;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .message(reason)
                .build());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        log.error("ForbiddenException: {}", ex.getMessage(), ex);
        String reason = ex.getReason() != null ? ex.getReason() : NOT_AUTHORIZED;
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder()
                .message(reason)
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        log.error("BadRequest: {}", ex.getMessage(), ex);
        String reason = ex.getReason() != null ? ex.getReason() : BAD_REQUEST;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .message(reason)
                .build());
    }

}
