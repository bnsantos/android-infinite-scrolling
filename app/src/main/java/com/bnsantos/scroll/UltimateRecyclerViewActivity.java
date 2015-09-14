package com.bnsantos.scroll;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

public class UltimateRecyclerViewActivity extends ScrollingActivity {
    private UltimateRecyclerView mUltimateRecyclerView;
    private int mPage = 0;

    public UltimateRecyclerViewActivity() {
        super(R.layout.activity_ultimate_recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadItems(mPage++);
    }

    @Override
    protected void initViews(){
        mUltimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
    }

    @Override
    protected void initAdapter(){
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mUltimateRecyclerView.setLayoutManager(layout);
        mAdapter = new ItemAdapter();
        mUltimateRecyclerView.setAdapter(mAdapter);
        mUltimateRecyclerView.enableLoadmore();
        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int i, int i1) {
                loadItems(mPage++);
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
}
