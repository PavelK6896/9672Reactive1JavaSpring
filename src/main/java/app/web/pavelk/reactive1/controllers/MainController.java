package app.web.pavelk.reactive1.controllers;

import app.web.pavelk.reactive1.domens.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/c")
public class MainController {

    @GetMapping
    public Flux<Message> list(@RequestParam(required = false, defaultValue = "0") Long start,
                              @RequestParam(required = false, defaultValue = "3") Long count) {

        return Flux.just("hello re", " two post", "three post", "44", "55")
                .skip(start)//скипаем
                .take(count)
                .map(Message::new);
    }
}
