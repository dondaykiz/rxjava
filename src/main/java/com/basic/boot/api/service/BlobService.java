package com.basic.boot.api.service;

import io.reactivex.rxjava3.core.Observable;
import org.json.simple.JSONArray;

import java.util.List;

public interface BlobService {
    void uploadBlob(Observable<String> array);
}
