package app.web.pavelk.reactive1.repositorys;


import app.web.pavelk.reactive1.domens.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String name);
}
