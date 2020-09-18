package com.skt.tsop.cctv.controller;

import com.skt.tsop.cctv.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RxJavaSchedulerController22 {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaSchedulerController22.class);

    @Autowired
    BlobService blobService;

    @GetMapping(value = "scMap2")
    public void rxJavaMap() {
        logger.debug("RXJAVA_MAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .map(data -> formatString(data))
                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .subscribe(logger::debug);
        logger.debug("RXJAVA_MAP_FINISHED");
    }

    @GetMapping(value = "scFlatMap2")
    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> formatString(data))
                                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.io())
                )
                .subscribe(logger::debug);

        logger.debug("RXJAVA_FLATMAP_FINISHED");
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
}
