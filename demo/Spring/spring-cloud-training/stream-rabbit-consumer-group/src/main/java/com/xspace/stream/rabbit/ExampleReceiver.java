package com.xspace.stream.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ExampleBinder.class)
@Slf4j
public class ExampleReceiver {
  @StreamListener(ExampleBinder.NAME)
  public void receive(String payload) {
    log.info("Received: {}", payload);
  }

}
