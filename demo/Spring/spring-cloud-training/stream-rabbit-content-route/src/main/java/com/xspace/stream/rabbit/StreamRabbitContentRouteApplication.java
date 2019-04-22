package com.xspace.stream.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(StreamRabbitContentRouteApplication.TestTopic.class)
@SpringBootApplication
public class StreamRabbitContentRouteApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitContentRouteApplication.class, args);
	}

	@RestController
	static class TestController {

		@Autowired
		private TestTopic testTopic;

		/**
		 * 消息生产接口
		 *
		 * @param message
		 * @return
		 */
		@GetMapping("/sendMessage")
		public String messageWithMQ(@RequestParam String message) {
			testTopic.output().send(MessageBuilder.withPayload(message).setHeader("version", "1.0").build());
			testTopic.output().send(MessageBuilder.withPayload(message).setHeader("version", "2.0").build());
			return "ok";
		}

	}

	/**
	 * 消息消费逻辑
	 */
	@Slf4j
	@Component
	static class TestListener {

		@StreamListener(value = TestTopic.INPUT, condition = "headers['version']=='1.0'")
		public void receiveV1(String payload, @Header("version") String version) {
			log.info("Received v1 : {}, {}", payload, version);
		}

		@StreamListener(value = TestTopic.INPUT, condition = "headers['version']=='2.0'")
		public void receiveV2(String payload, @Header("version") String version) {
			log.info("Received v2 : {}, {}", payload, version);
		}

	}

	interface TestTopic {

		String OUTPUT = "example-topic-output";
		String INPUT = "example-topic-input";

		@Output(OUTPUT)
		MessageChannel output();

		@Input(INPUT)
		SubscribableChannel input();

	}

}
