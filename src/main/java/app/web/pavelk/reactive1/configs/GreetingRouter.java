package app.web.pavelk.reactive1.configs;

import app.web.pavelk.reactive1.handlers.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

import java.util.Map;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        //создали роут
        RequestPredicate route = RequestPredicates
                .GET("/hello")//get mapping
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));//json no
        //создаем роуты
        return RouterFunctions
                .route(route, greetingHandler::hello) //функция возврощает сервер ответ
                .andRoute(
                        RequestPredicates.GET("/"),
                        greetingHandler::main
                )
                .andRoute(RequestPredicates.GET("/j"),greetingHandler::json1);
    }
}