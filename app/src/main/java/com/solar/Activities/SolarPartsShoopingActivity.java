package com.solar.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.solar.Model.Api;
import com.solar.Model.FetchAllSolarProductList;
import com.solar.Model.FetchAllSolarProductLists;
import com.solar.R;
import com.solar.SolarProductListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolarPartsShoopingActivity extends AppCompatActivity {
    @BindView(R.id.rvshooping)
    RecyclerView rvshooping;
    private RecyclerView.LayoutManager mLayoutManager;

    SolarProductListAdapter solarProductListAdapter;

    private List<FetchAllSolarProductList> productLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar_parts_shooping);
        ButterKnife.bind(this);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        rvshooping.setHasFixedSize(true); //Increases the app's performance since the size of the items layout won't increase
        mLayoutManager = new LinearLayoutManager(this);
        rvshooping.setLayoutManager(mLayoutManager);
        fetchData();


    }

    private void fetchData() {
        productLists = new ArrayList<>();

        Api api = ApiClient.getClient().create(Api.class);
        Call<FetchAllSolarProductLists> productListsCall = api.fetchAllSolarProductLists();
        productListsCall.enqueue(new Callback<FetchAllSolarProductLists>() {
            @Override
            public void onResponse(Call<FetchAllSolarProductLists> call, Response<FetchAllSolarProductLists> response) {
                if (!response.body().getError()) {
                    for (int i = 0; i < response.body().getFetchAllSolarProductLists().size(); i++) {
                        String image = response.body().getFetchAllSolarProductLists().get(i).getSimage();
                        String completeUrl = "http://192.168.10.12/solar/Sub_image/"+image;
                        response.body().getFetchAllSolarProductLists().get(i).setSimage(completeUrl);
                        //  response.body().setFetchAllSolarProductLists();
                        //    productLists.add(response.body().getFetchAllSolarProductLists().get(i).getSimage())
                    }
                    //  productLists = response.body().getFetchAllSolarProductLists();
                    solarProductListAdapter = new SolarProductListAdapter(response.body().getFetchAllSolarProductLists(), SolarPartsShoopingActivity.this);
                   // setRecyelerView(productLists);

                }
                rvshooping.setAdapter(solarProductListAdapter);
                solarProductListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FetchAllSolarProductLists> call, Throwable t) {
            }
        });


    }
}