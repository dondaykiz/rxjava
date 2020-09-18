package com.skt.tsop.cctv.controller;

import com.skt.tsop.cctv.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RxJavaErrorController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaErrorController.class);

    @Autowired
    BlobService blobService;

    /**
     * Error 핸들링 적용 flatMap()
     */
    @GetMapping(value = "erReturn")
    public void rxJavaError() {
        logger.debug("RXJAVA_ERROR_HANDLER_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .doOnNext(next -> logger.debug("DATA : {}, THREAD : {}", next, Thread.currentThread().getName()))
                .flatMap(data ->
                        Observable.fromCallable(() -> exceptionTest())
                                .subscribeOn(Schedulers.io())
                )
                .onErrorReturn(e -> {
                    e.printStackTrace();
                    return "EXCEPTION 발생";
                })
                .subscribe(result -> logger.debug("RESULT : {}, THREAD : {}", result, Thread.currentThread().getName()));
        logger.debug("RXJAVA_ERROR_HANDLER_FINISHED");
    }

    /**
     * Error 핸들링 적용 flatMap()
     */
    @GetMapping(value = "retry")
    public void rxJavaRetry() {
        logger.debug("RXJAVA_RETRY_STARTED");
        Observable.just(5)
                .doOnNext(next -> logger.debug("DATA : {}, THREAD : {}", next, Thread.currentThread().getName()))
                .map(data -> exceptionTest())
                .retry(1)
                .subscribe(result -> logger.debug("RESULT : {}, THREAD : {}", result, Thread.currentThread().getName()));
        logger.debug("RXJAVA_RETRY_FINISHED");
    }

    public String exceptionTest() throws Exception {
        throw new Exception("ERROR!!!");
    }


}
