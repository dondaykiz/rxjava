package com.skt.tsop.cctv.controller;

import com.skt.tsop.cctv.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class RxJavaBasicController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaBasicController.class);

    @Autowired
    BlobService blobService;

    /**
     * just() 함수에 넣은 데이터를 차례로 발행한다.
     */
    @GetMapping(value = "just")
    public void rxJavaJust() {
        logger.debug("RXJAVA_JUST_STARTED");
        Observable.just("apple", "banana", "grape", "orange", "tomato")
                /**
                 * just() 함수는 subscribe이전에 메모리에 할당되므로 스케줄러 설정시
                 * start, finish 타임의 딜레이가 발생
                 * (Observable 만들어지기 전 <-> 후의 시간타임)
                 */
                //.subscribeOn(Schedulers.io())
                .subscribe(logger::debug);
        logger.debug("RXJAVA_JUST_FINISHED");
    }

    /**
     * Array로 구성된 데이터를 차례로 발행한다.
     */
    @GetMapping(value = "array")
    public void rxJavaArray() {
        logger.debug("RXJAVA_ARRAY_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .subscribe(logger::debug);
        logger.debug("RXJAVA_ARRAY_FINISHED");
    }

    /**
     * list로 구성된 데이터를 차례로 발행한다.
     */
    @GetMapping(value = "list")
    public void rxJavaIterator() {
        logger.debug("RXJAVA_ITERABLE_STARTED");
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("grape");
        list.add("orange");
        list.add("tomato");

        Observable.fromIterable(list)
                .subscribe(logger::debug);
        logger.debug("RXJAVA_ITERABLE_FINISHED");
    }

    @GetMapping(value = "call")
    public void rxJavaCallable() {
        logger.debug("RXJAVA_CALLABLE_STARTED");
        Observable.fromCallable(() -> delay())
                /**
                 * subscribe 할 때까지 observable을 생성하지 않아 start<->finish 타임의
                 * 딜레이가 많이 없음
                 */
                //.subscribeOn(Schedulers.io())
                .subscribe(logger::debug);
        logger.debug("RXJAVA_CALLABLE_FINISHED");
    }

    public String delay() {
        Random random = new Random();
        int delay = random.nextInt(3000);
        return "OK";
    }

}
