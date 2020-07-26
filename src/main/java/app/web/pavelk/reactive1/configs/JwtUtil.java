package app.web.pavelk.reactive1.configs;

import app.web.pavelk.reactive1.domens.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil { // сам токен
    @Value("${jwt.secret}") // строчька для кодировки
    private String secret;
    @Value("${jwt.expiration}")// строчька для времени токена
    private String expirationTime;

    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken)
                .getSubject();//имя в токене
    }

    public Claims getClaimsFromToken(String authToken) {
        String key = Base64.getEncoder().encodeToString(secret.getBytes()); //строка шифрования
        return Jwts.parserBuilder()
                .setSigningKey(key)//ключь для расшифровки
                .build()
                .parseClaimsJws(authToken)//преобразуем токен
                .getBody();//контент в токене
    }

    public boolean validateToken(String authToken) {
        return getClaimsFromToken(authToken)
                .getExpiration()
                .after(new Date());//проверяем активность токена
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>(); //claims требования
        claims.put("role", List.of(user.getRole()));

        long expirationSeconds = Long.parseLong(expirationTime);//количество секунд
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);//конец тоена

        return Jwts.builder()//билдим токен
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)//создание
                .setExpiration(expirationDate)//экспирация
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))//подписываем токен ключем шифрования
                .compact();// compact - компактный
    }
}
