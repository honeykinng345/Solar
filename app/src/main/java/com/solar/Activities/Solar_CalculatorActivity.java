package com.solar.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import com.solar.Helper;
import com.solar.Model.Api;
import com.solar.Model.Appliances;
import com.solar.Model.Price;
import com.solar.Model.PricesBIP;
import com.solar.R;
import com.solar.adapters.CalculatorAdapter;
import com.solar.adapters.StringArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Solar_CalculatorActivity extends AppCompatActivity {
    String bettryValue, InverterValue, PlatesValue;
    @BindView(R.id.spinner1)
    Spinner spinner1;

    StringArrayAdapter arrayAdapter;
    StringArrayAdapter arrayAdapter1;
    StringArrayAdapter arrayAdapter2;
    ArrayList<String> bettries;
    ArrayList<String> inverters;
    ArrayList<String> plates;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.applicance_name)
    EditText applicanceName;
    @BindView(R.id.watts_or_hp)
    EditText wattsOrHp;
    @BindView(R.id.quantity_of_devices)
    EditText quantityOfDevices;

    private RecyclerView mRecyclerView;
    private CalculatorAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Appliances> mAppliances;
    //  private List<PricesBIP> goodModelArrayList;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar__calculator);
        inverters = new ArrayList<>();
        bettries = new ArrayList<>();
        plates = new ArrayList<>();
        mAppliances = new ArrayList<>();
        ButterKnife.bind(this);
        buildRecyclerView();
        fetchSpinenrData();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    bettryValue = Helper.spinnerItemSelected(position, bettries);

                    Helper.SHowToast(Solar_CalculatorActivity.this, "done" + bettryValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0){
                InverterValue = Helper.spinnerItemSelected(position, inverters);
                Helper.SHowToast(Solar_CalculatorActivity.this, "done" + InverterValue);

            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                PlatesValue = Helper.spinnerItemSelected(position, plates);
                Helper.SHowToast(Solar_CalculatorActivity.this, "done" + PlatesValue);
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void fetchSpinenrData() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<PricesBIP> pricesBIPCall = api.get_All_prices();
        pricesBIPCall.enqueue(new Callback<PricesBIP>() {
            @Override
            public void onResponse(Call<PricesBIP> call, Response<PricesBIP> response) {
                if (response.body() != null) {
                    if (!response.body().getError()) {
                        setList(response);
                    }
                    Toast.makeText(Solar_CalculatorActivity.this, "ok" + response.body().getPrices().get(0).getStatus(), Toast.LENGTH_LONG).show();
                    Log.d("JSON  LIST", new GsonBuilder().setPrettyPrinting().create().toJson(response));


                }
            }

            @Override
            public void onFailure(Call<PricesBIP> call, Throwable t) {
            }
        });

    }

    private void setList(Response<PricesBIP> response) {
        List<Price> list = response.body().getPrices();

        for (int i = 0; i < list.size(); i++) {
            // Helper.SHowToast(Solar_CalculatorActivity.this, "" + list.get(i).getPrice());
            if (list.get(i).getStatus().equals("1")) {
                inverters.add(list.get(i).getName() + " = " + list.get(i).getPrice());
            } else if (list.get(i).getStatus().equals("2")) {
                bettries.add(list.get(i).getName() + " = " + list.get(i).getPrice());

            } else {
                plates.add(list.get(i).getName() + " = " + list.get(i).getPrice());


            }


        }

        bettries.add(0, "Select:           = 3");
        arrayAdapter = new StringArrayAdapter(this, bettries);
        spinner1.setAdapter(arrayAdapter);
        inverters.add(0, "Select:        =  3");
        arrayAdapter1 = new StringArrayAdapter(this, inverters);
        spinner2.setAdapter(arrayAdapter1);
        plates.add(0, "Select:          =  3");
        arrayAdapter2 = new StringArrayAdapter(this, plates);
        spinner3.setAdapter(arrayAdapter2);
    }


    @OnClick({R.id.add_an_appliance_btn, R.id.calculate_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_an_appliance_btn:
                if (mAppliances.size() >= 7){
                    Helper.SHowToast(Solar_CalculatorActivity.this,"just calculate 7  Appliance Data..");

                }else{
                    pos = 0;
                    insertApplianceWhenInWatt(pos);
                }
                break;
            case R.id.calculate_btn:

                if (mAdapter.getItemCount() == 0) {
                    Toast.makeText(Solar_CalculatorActivity.this, "You haven't added any appliance yet", Toast.LENGTH_SHORT).show();
                } else {
                    calc();
                }
                break;
        }
    }

    private void calc() {
        int totalCalculate= 0;

        for (Appliances appliance : mAppliances) {
          /*  totalWattHour = totalWattHour + (Integer.parseInt(appliance.getmApplianceQuantity()) *
                    Integer.parseInt(appliance.getApplianceDurationOfUse()))
                    * Integer.parseInt(appliance.getApplianceQuantity());*/
            totalCalculate =totalCalculate+ (Integer.parseInt(appliance.getmApplianceQuantity())
                    * Integer.parseInt(appliance.getmApplianceWattage()) +  Integer.parseInt(appliance.getBettryType())
                    +Integer.parseInt(appliance.getInverterType()));

        }
        Helper.SHowToast(Solar_CalculatorActivity.this,""+totalCalculate);
        //startActivity(new Intent(Solar_CalculatorActivity.this,CalculateResultActivity.class));
    }

    private void insertApplianceWhenInWatt(int pos) {
        String aplName, aplWattage, aplQuantity;
        aplName = applicanceName.getText().toString();
        aplWattage = wattsOrHp.getText().toString();
        aplQuantity = quantityOfDevices.getText().toString();
        if (aplName.isEmpty() && aplWattage.isEmpty() && aplQuantity.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else if (bettryValue == null || InverterValue == null || PlatesValue == null) {
            Toast.makeText(this, "Please Select Battery,Inverter,Plates ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAppliances.add(pos, new Appliances(aplName, aplWattage, aplQuantity, bettryValue, InverterValue, PlatesValue));
        Helper.SHowToast(Solar_CalculatorActivity.this, "" + mAppliances.get(0).getmApplianceQuantity());

        mAdapter.notifyDataSetChanged();
        clearField();


    }

    private void clearField() {
        applicanceName.setText("");
        wattsOrHp.setText("");
        quantityOfDevices.setText("");
        bettryValue = null;
        InverterValue = null;
        PlatesValue = null;
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.appliance_recycler);
        mRecyclerView.setHasFixedSize(true); //Increases the app's performance since the size of the items layout won't increase
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CalculatorAdapter(mAppliances);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CalculatorAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                mAppliances.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
    }
}