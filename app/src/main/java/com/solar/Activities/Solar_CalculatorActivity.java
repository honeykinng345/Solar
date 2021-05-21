package com.solar.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;
import com.solar.Helper;
import com.solar.Model.Api;
import com.solar.Model.Appliances;
import com.solar.Model.Price;
import com.solar.Model.PricesBIP;
import com.solar.R;

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

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter1;
    ArrayAdapter<String> arrayAdapter2;
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


    private ArrayList<Appliances> mAppliances;
    //  private List<PricesBIP> goodModelArrayList;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solar__calculator);
        mAppliances = new ArrayList<>();
        ButterKnife.bind(this);
        fetchSpinenrData();
spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bettryValue = Helper.spinnerItemSelected(arrayAdapter, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});

spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        InverterValue = Helper.spinnerItemSelected(arrayAdapter1, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});

spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        PlatesValue = Helper.spinnerItemSelected(arrayAdapter2, position);
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
        inverters = new ArrayList<>();
        bettries = new ArrayList<>();
        plates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // Helper.SHowToast(Solar_CalculatorActivity.this, "" + list.get(i).getPrice());
            if (list.get(i).getStatus().equals("1")) {
                inverters.add(list.get(i).getName() + " = " + list.get(i).getPrice());
            } else if (list.get(i).getStatus().equals("2")) {
                bettries.add(list.get(i).getName() + " = " + list.get(i).getPrice());

            } else {
                plates.add(list.get(i).getName() + " = " + list.get(i).getPrice());


            }
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bettries);
            arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, inverters);
            arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, plates);
            spinner1.setAdapter(arrayAdapter);
            spinner2.setAdapter(arrayAdapter1);
            spinner3.setAdapter(arrayAdapter2);

        }


    }


    @OnClick(R.id.add_an_appliance_btn)
    public void onViewClicked() {
        pos = 0;
        insertApplianceWhenInWatt(pos);
    }

    private void insertApplianceWhenInWatt(int pos) {
        String aplName, aplWattage, aplQuantity;
        aplName = applicanceName.getText().toString();
        aplWattage = wattsOrHp.getText().toString();
        aplQuantity = quantityOfDevices.getText().toString();
        if (aplName.isEmpty() && aplWattage.isEmpty() && aplQuantity.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        } else if (bettryValue.isEmpty() && InverterValue.isEmpty() && PlatesValue.isEmpty()) {
            Toast.makeText(this, "Please Select Battery,Inverter,Plates ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAppliances.add(pos, new Appliances(aplName, aplWattage, aplQuantity, bettryValue, InverterValue, PlatesValue));
        Helper.SHowToast(Solar_CalculatorActivity.this, "" + mAppliances.get(0).getmApplianceQuantity());
        pos=+1;
        // mAdapter.notifyDataSetChanged();
        clearField();


    }

    private void clearField() {
        applicanceName.setText("");
        wattsOrHp.setText("");
        quantityOfDevices.setText("");
        bettryValue = null;
        InverterValue = null;
        PlatesValue = null;
    }
}