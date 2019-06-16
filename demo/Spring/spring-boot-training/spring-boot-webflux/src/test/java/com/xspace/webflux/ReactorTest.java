package com.xspace.webflux;

import com.xspace.webflux.lambda.MyEventListener;
import com.xspace.webflux.lambda.MyEventSource;
import com.xspace.webflux.lambda.MyEventSource.MyEvent;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ReactorTest extends BaseTest {
	private final int EVENT_DURATION = 10;    // 生成的事件间隔时间，单位毫秒
	private final int EVENT_COUNT = 20;    // 生成的事件个数
	private final int PROCESS_DURATION = 30;    // 订阅者处理每个元素的时间，单位毫秒

	private Flux<MyEventSource.MyEvent> fastPublisher;
	private SlowSubscriber slowSubscriber;
	private MyEventSource eventSource;
	private CountDownLatch countDownLatch;

	@Test
	public void testGenerate1() {
		final AtomicInteger count = new AtomicInteger(1);
		Flux.generate(sink -> {
			sink.next(count.get() + " : " + new Date());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count.getAndIncrement() >= 5) {
				sink.complete();
			}
		}).subscribe(System.out::println);
	}

	@Test
	public void testGenerate2() {
		Flux.generate(
				() -> 1,
				(count, sink) -> {
					sink.next(count + " : " + new Date());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (count >= 5) {
						sink.complete();
					}
					return count + 1;
				}).subscribe(System.out::println);
	}

	@Test
	public void testGenerate3() {
		Flux.generate(
				() -> 1,
				(count, sink) -> {
					sink.next(count + " : " + new Date());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (count >= 5) {
						sink.complete();
					}
					return count + 1;
				}, System.out::println)
				.subscribe(System.out::println);
	}

	@Test
	public void testCreate() throws InterruptedException {
		MyEventSource eventSource = new MyEventSource();
		Flux.create(sink -> {
					eventSource.register(new MyEventListener() {
						@Override
						public void onNewEvent(MyEventSource.MyEvent event) {
							sink.next(event);
						}

						@Override
						public void onEventStopped() {
							sink.complete();
						}
					});
				}
		).subscribe(System.out::println);

		for (int i = 0; i < 20; i++) {
			Random random = new Random();
			TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
			eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
		}
		eventSource.eventStopped();
	}

	/**
	 * 准备工作。
	 */
	@BeforeEach
	public void setup() {
		countDownLatch = new CountDownLatch(1);
		slowSubscriber = new SlowSubscriber();
		eventSource = new MyEventSource();
	}

	/**
	 * 触发订阅，使用CountDownLatch等待订阅者处理完成。
	 */
	@AfterEach
	public void subscribe() throws InterruptedException {
		fastPublisher.subscribe(slowSubscriber);
		generateEvent(EVENT_COUNT, EVENT_DURATION);
		countDownLatch.await(1, TimeUnit.MINUTES);
	}


	/**
	 * 测试create方法的不同OverflowStrategy的效果。
	 */
	@Test
	public void testCreateBackPressureStratety() {
		fastPublisher =
				createFlux(FluxSink.OverflowStrategy.IGNORE)    // BUFFER/DROP/LATEST/ERROR/IGNORE
						.doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
						.publishOn(Schedulers.newSingle("newSingle"), 2);
	}

	/**
	 * 测试不同的onBackpressureXxx方法的效果。
	 */
	@Test
	public void testOnBackPressureXxx() {
		fastPublisher = createFlux(FluxSink.OverflowStrategy.BUFFER)
//                .onBackpressureDrop()
				.onBackpressureBuffer()
//                .onBackpressureLatest()
//                .onBackpressureError()
				.doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
				.publishOn(Schedulers.newSingle("newSingle"), 1);
	}

	/**
	 * 其他可以实现类似效果的操作符。
	 */
	@Test
	public void testSimilarOperators() {
		fastPublisher = createFlux(FluxSink.OverflowStrategy.BUFFER)
				.doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
				.sample(Duration.ofMillis(30))
		;
	}

	/**
	 * 使用create方法生成“快的发布者”。
	 *
	 * @param strategy 回压策略
	 * @return Flux
	 */
	private Flux<MyEventSource.MyEvent> createFlux(FluxSink.OverflowStrategy strategy) {
		return Flux.create(sink -> eventSource.register(new MyEventListener() {
			@Override
			public void onNewEvent(MyEventSource.MyEvent event) {
				System.out.println("publish >>> " + event.getMessage());
				sink.next(event);
			}

			@Override
			public void onEventStopped() {
				sink.complete();
			}
		}), strategy);
	}

	/**
	 * 生成MyEvent。
	 *
	 * @param count  生成MyEvent的个数。
	 * @param millis 每个MyEvent之间的时间间隔。
	 */
	private void generateEvent(int count, int millis) {
		for (int i = 0; i < count; i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(millis);
			} catch (InterruptedException e) {
			}
			eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
		}
		eventSource.eventStopped();
	}

	/**
	 * 内部类，“慢的订阅者”。
	 */
	class SlowSubscriber extends BaseSubscriber<MyEvent> {

		@Override
		protected void hookOnSubscribe(Subscription subscription) {
			request(1);
		}

		@Override
		protected void hookOnNext(MyEventSource.MyEvent event) {
			System.out.println("                      receive <<< " + event.getMessage());
			try {
				TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION);
			} catch (InterruptedException e) {
			}
			request(1);
		}

		@Override
		protected void hookOnError(Throwable throwable) {
			System.err.println("                      receive <<< " + throwable);
		}

		@Override
		protected void hookOnComplete() {
			countDownLatch.countDown();
		}
	}

	@Test
	public void testScheduling() {
		Flux.range(0, 10)
//                .log()
				.publishOn(Schedulers.newParallel("myParrallel"))
//                .log()
				.subscribeOn(Schedulers.newElastic("myElastic"))
				.log()
				.blockLast();
	}

	@Test
	public void testDelayElements() {
		Flux.range(0, 10)
				.delayElements(Duration.ofMillis(10))
				.log()
				.blockLast();
	}

	@Test
	public void testParallelFlux() throws InterruptedException {
		Flux.range(1, 10)
				.parallel(2)
				.runOn(Schedulers.parallel())
//                .publishOn(Schedulers.parallel())
				.log()
				.subscribe();

		TimeUnit.MILLISECONDS.sleep(10);
	}

}
