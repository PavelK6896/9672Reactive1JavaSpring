package app.web.pavelk.reactive1.handlers;

import app.web.pavelk.reactive1.domens.Message;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GreetingHandler {

    //http реквест респонс // сервер рексвет сервер респонс //
    // mono flux пользователю

    public Mono<ServerResponse> hello(ServerRequest request) {
        //генерация контента
        BodyInserter<String, ReactiveHttpOutputMessage> body = BodyInserters.fromValue("Hello, Spring!");
        return ServerResponse
                .ok()// код 200
                .contentType(MediaType.TEXT_PLAIN)// текст
                .body(body);
    }

    public Mono<ServerResponse> main(ServerRequest serverRequest){

            String user = serverRequest.queryParam("user").orElse("no body");
            return ServerResponse
                    .ok()// код 200
                    .render("index", Map.of("user", user));

    }

    public Mono<ServerResponse> json1(ServerRequest serverRequest) {
        //генерация контента

        Long start = serverRequest.queryParam("start").map(Long::valueOf).orElse(0L);
        Long count = serverRequest.queryParam("count").map(Long::valueOf).orElse(3L);

        Flux<Message> data = Flux.just("hello re", " two post", "three post", "44", "55")
                .skip(start)//скипаем
                .take(count)
                .map(Message::new);




        return ServerResponse
                .ok()// код 200
                .contentType(MediaType.APPLICATION_JSON)// текст
                .body(data, Message.class);
    }



}