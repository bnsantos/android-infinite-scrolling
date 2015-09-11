package com.bnsantos.scroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.Arrays;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UltimateRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = UltimateRecyclerViewActivity.class.getSimpleName();
    private UltimateRecyclerView mUltimateRecyclerView;
    private Subscription mSubscription;
    private Provider mProvider;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_recycler_view);

        mUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        mProvider = new Provider(Arrays.asList(getResources().getStringArray(R.array.countries)));

        initAdapter();
    }

    private void initAdapter(){
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mUltimateRecyclerView.setLayoutManager(layout);
        mAdapter = new ItemAdapter();
        mUltimateRecyclerView.setAdapter(mAdapter);
        mUltimateRecyclerView.enableLoadmore();
        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int i, int i1) {
                loadItems();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ultimate_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadItems(){
        if(mSubscription!=null){
            mSubscription.unsubscribe();
        }
        mSubscription = mProvider.getItems().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> items) {
                        mAdapter.load(items);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(UltimateRecyclerViewActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error while loading items", throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "Done loading items");
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
