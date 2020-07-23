package app.web.pavelk.reactive1.controllers;

import app.web.pavelk.reactive1.domens.Message;
import app.web.pavelk.reactive1.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/c")
public class MainController {

    private final MessageService messageService;

    @Autowired
    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/j")
    public Flux<Message> cj(@RequestParam(required = false, defaultValue = "0") Long start,
                            @RequestParam(required = false, defaultValue = "3") Long count) {
        return Flux.just("hello re", " two post", "three post", "44", "55")
                .skip(start)//скипаем
                .take(count)
                .map(Message::new);
    }

    @GetMapping
    public Flux<Message> findAll() {
        return messageService.findAll();
    }

    @PostMapping
    public Mono<Message> save(@RequestBody Message message) {
        System.out.println("save");
        return messageService.save(message);
    }

}
