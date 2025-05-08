package cl.ntt.userapi.user_api.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.ntt.userapi.user_api.repository.UserRepository;
import cl.ntt.userapi.user_api.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {

        // String requestPath = request.getRequestURI();

        // if (requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/swagger-ui")
        //         || requestPath.startsWith("/h2-console")) {
        //     log.info("Skipping JWT filter for path: {}", requestPath);
        //     filterChain.doFilter(request, response);
        //     return;
        // }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String idUsuario = jwtUtil.verifyToken(token);

            log.info("Token: {}", token);

            if (idUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userRepository.findById(UUID.fromString(idUsuario)).ifPresent(user -> {
                    if (user.getToken().equals(token)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user, null, null);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                });
            }
        }

        filterChain.doFilter(request, response);
    }

}
