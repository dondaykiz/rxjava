package com.basic.boot.api.controller.sample;

import com.basic.boot.api.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yjkim@ntels.com
 */
@RestController
public class BlobController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(BlobController.class);

    @Autowired
    BlobService blobService;

    @GetMapping(value = "upload")
    public void uploadBlob() {
        logger.debug("BLOB_UPLOAD_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> blobService.uploadBlob(data))
                                .onErrorReturn(e -> {
                                    e.printStackTrace();
                                    return "EXCEPTION";
                                })
                )
                .subscribeOn(Schedulers.io())
                .subscribe(result -> logger.debug("RESULT >> " + result));
        logger.debug("BLOB_UPLOAD_FINISHED");
    }
}
