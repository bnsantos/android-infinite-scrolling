package com.bnsantos.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.Arrays;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bruno on 14/09/15.
 */
public class ScrollingActivity extends AppCompatActivity {
    protected Subscription mSubscription;
    protected ItemAdapter mAdapter;
    protected Provider mProvider;
    protected final int mLayout;

    public ScrollingActivity(int mLayout) {
        this.mLayout = mLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayout);

        mProvider = new Provider(Arrays.asList(getResources().getStringArray(R.array.countries)));
        initViews();
        initAdapter();
    }

    protected void initViews(){}

    protected void initAdapter(){}

    protected void loadItems(int page){
        if(mSubscription!=null){
            mSubscription.unsubscribe();
        }
        mSubscription = mProvider.getItems(page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> items) {
                        mAdapter.load(items);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(ScrollingActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        Log.e(this.getClass().getSimpleName(), "Error while loading items", throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.i(this.getClass().getSimpleName(), "Done loading items");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription!=null){
            mSubscription.unsubscribe();
        }
    }
}
