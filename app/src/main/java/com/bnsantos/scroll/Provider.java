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
    private int mLastItem;
    private static final int MAX_ITEMS = 20;

    public Provider(List<String> countries) {
        this.mCountries = countries;
    }

    public Observable<List<String>> getItems(){
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> items = new ArrayList<>(0);
                if(mLastItem+MAX_ITEMS<mCountries.size()){
                   items = mCountries.subList(mLastItem, mLastItem+MAX_ITEMS);
                    mLastItem += MAX_ITEMS;
                }else if(mLastItem<mCountries.size()){
                    items = mCountries.subList(mLastItem, mCountries.size());
                    mLastItem = mCountries.size();
                }
                subscriber.onNext(items);
            }
        });
    }
}
