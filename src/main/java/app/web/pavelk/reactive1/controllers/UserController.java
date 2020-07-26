package app.web.pavelk.reactive1.controllers;

import app.web.pavelk.reactive1.configs.JwtUtil;
import app.web.pavelk.reactive1.domens.User;
import app.web.pavelk.reactive1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class UserController {
    private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe) { //Exchange = обмен
        return swe.getFormData().flatMap(credentials ->  // credentials - полученные данные
                userService.findByUsername(credentials.getFirst("username"))
                        .cast(User.class)
                        .map(userDetails ->
                                Objects.equals( // проверка пороля
                                        credentials.getFirst("password"),//получаем попроль
                                        userDetails.getPassword()//пороль из базы + зашифровать
                                )
                                        //пароли равны генерируем токен // устанавливаем токен
                                        ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                        : UNAUTHORIZED//указываем код неавторизовный
                        )
                .defaultIfEmpty(UNAUTHORIZED)//тоже если не нашли
        );
    }
}
