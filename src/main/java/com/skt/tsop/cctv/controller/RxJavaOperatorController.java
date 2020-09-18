package com.skt.tsop.cctv.controller;

import com.skt.tsop.cctv.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RxJavaOperatorController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaOperatorController.class);

    @Autowired
    BlobService blobService;

    /**
     * 특정 함수에 넣어서 원하는 값으로 변경
     */
    @GetMapping(value = "map")
    public void rxJavaMap() {
        logger.debug("RXJAVA_MAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                //.map(lamdaMapFormatString)
                .map(data -> formatString(data))
                .subscribe(logger::debug);
        logger.debug("RXJAVA_MAP_FINISHED");
    }

    /**
     * 특정 함수에 넣어서 원하는 값으로 변경
     * Observe 패턴을 사용할 수 있음
     */
    @GetMapping(value = "flatmap")
    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.just(data, data + " + @")
                        //Observable.fromCallable(() -> formatString(data))
                )
                .subscribe(logger::debug);
        logger.debug("RXJAVA_FLATMAP_FINISHED");
    }

    @GetMapping(value = "filter")
    public void rxJavaFilter() {
        logger.debug("RXJAVA_FILTER_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .filter(data -> data.equals("orange"))
                .subscribe(logger::debug);
        logger.debug("RXJAVA_FILTER_FINISHED");
    }

    public String formatString(String data) {
        try {
            Random random = new Random();
            int sleep = random.nextInt(3000);
            Thread.sleep(sleep);
            return data + " + @";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    Function<String, String> lamdaMapFormatString = data -> {
        Random random = new Random();
        int sleep = random.nextInt(3000);
        Thread.sleep(sleep);
        return data + " + @";
    };
}
