package co.owl.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //used once I have NOT crated discover server and there was NOT multiple server for inventory Server
    /*@Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }*/

    //using once I have crated discover server and multiple server for inventory Server
    //now it will not call all inventory-service or any other multiple server
    // SO putting loadBalanced annotation
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
