package app.web.pavelk.reactive1.configs;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil; //авторизация для токена

    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString(); //полномочия

        String username;

        try {
            username = jwtUtil.extractUsername(authToken);//расшифрованое имя даные
        } catch (Exception e) {
            username = null;
            System.out.println(e);
        }
        System.out.println(" расшифрованное имя " + username);

        if (username != null && jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getClaimsFromToken(authToken);//требования
            List<String> role = claims.get("role", List.class);//получаем роли из
            List<SimpleGrantedAuthority> authorities = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());//это роли

            authorities.forEach(System.out::println);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(//для подтверждения атинтефик
                    username,
                    null,
                    authorities
            );

            return Mono.just(authenticationToken); // передали токен для авторизации
        } else {
            return Mono.empty();//авторизация не прошла
        }
    }
}
