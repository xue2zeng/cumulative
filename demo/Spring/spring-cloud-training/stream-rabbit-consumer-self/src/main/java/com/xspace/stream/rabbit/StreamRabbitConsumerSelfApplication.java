package com.xspace.stream.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TestTopic.class)
@SpringBootApplication
public class StreamRabbitConsumerSelfApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamRabbitConsumerSelfApplication.class, args);
  }

}
