package com.skt.tsop.cctv.controller;

import com.skt.tsop.cctv.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RxJavaSchedulerController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaSchedulerController.class);

    @Autowired
    BlobService blobService;

    /**
     * 스케줄러 적용 map()
     */
    @GetMapping(value = "scmap")
    public void rxJavaMap() {
        logger.debug("RXJAVA_SCHEDULER_MAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                //.map(lamdaMapFormatString)
                .doOnNext(data -> logger.debug("DATA : {}, THREAD : {}" ,data, Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(data -> formatString(data))
                .subscribe(result -> logger.debug("RESULT : {}, THREAD : {}" ,result, Thread.currentThread().getName()));
        logger.debug("RXJAVA_SCHEDULER_MAP_FINISHED");
    }

    /**
     * 스케줄러 적용 flatMap()
     */
    @GetMapping(value = "scflatmap")
    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_SCHEDULER_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .doOnNext(next -> logger.debug("DATA : {}, THREAD : {}" ,next, Thread.currentThread().getName()))
                .flatMap(data ->
                        //Observable.just(data, data + " + @")
                        Observable.fromCallable(() -> formatString(data))
                                .subscribeOn(Schedulers.io())
                )
                .subscribe(result -> logger.debug("RESULT : {}, THREAD : {}" ,result, Thread.currentThread().getName()));
        logger.debug("RXJAVA_SCHEDULER_FLATMAP_FINISHED");
    }

    @GetMapping(value = "scfilter")
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
