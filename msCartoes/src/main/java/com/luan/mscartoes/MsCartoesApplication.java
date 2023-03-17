package com.luan.mscartoes;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class MsCartoesApplication {

	public static void main(String[] args) {
		/*log.info("Informação: {}", "teste info" );
		log.warn("Aviso: {}", "teste warn" );
		log.error("Erro: {}", "teste error" );*/
		SpringApplication.run(MsCartoesApplication.class, args);
	}

}
