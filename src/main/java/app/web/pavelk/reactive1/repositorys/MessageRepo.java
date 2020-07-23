package app.web.pavelk.reactive1.repositorys;


import app.web.pavelk.reactive1.domens.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {
}
