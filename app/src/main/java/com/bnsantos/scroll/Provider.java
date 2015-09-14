package com.bnsantos.scroll;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by bruno on 11/09/15.
 */
public class Provider {
    private final List<String> mCountries;
    private static final int MAX_ITEMS = 20;

    public Provider(List<String> countries) {
        this.mCountries = countries;
    }

    public Observable<List<String>> getItems(final int page){
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                int first = page*MAX_ITEMS;
                if(first>mCountries.size()){
                    subscriber.onNext(new ArrayList<String>());
                }else{
                    int last = first+MAX_ITEMS-1;
                    if(last>mCountries.size()){
                        last=mCountries.size();

                    }
                    subscriber.onNext(mCountries.subList(first, last));
                }
            }
        });
    }
}
