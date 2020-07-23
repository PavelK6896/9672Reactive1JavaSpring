package app.web.pavelk.reactive1.services;

import app.web.pavelk.reactive1.domens.Message;
import app.web.pavelk.reactive1.repositorys.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {
    private MessageRepo messageRepo;

    @Autowired
    public void setMessageRepo(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Flux<Message> findAll() {
        return messageRepo.findAll();
    }

    public Mono<Message> save(Message message) {
        return messageRepo.save(message);
    }
}
