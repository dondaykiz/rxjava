package com.basic.boot.api.controller.sample;

import com.basic.boot.api.service.BlobService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @PostMapping(value = "upload")
    public void uploadBlob(@RequestBody JSONObject array) {
        logger.debug("BLOB_UPLOAD_STARTED : " + array);
/*        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> blobService.uploadBlob(data))
                                .subscribeOn(Schedulers.io())
                                .onErrorReturn(e -> {
                                    e.printStackTrace();
                                    return "EXCEPTION";
                                })
                )
                .subscribe(result -> logger.debug("RESULT >> " + result));*/
        logger.debug("BLOB_UPLOAD_FINISHED");
    }
}
