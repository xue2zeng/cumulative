package com.xspace.stream.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ExampleBinder {
  String NAME = "example-topic";

  @Input(NAME)
  SubscribableChannel input();
}
