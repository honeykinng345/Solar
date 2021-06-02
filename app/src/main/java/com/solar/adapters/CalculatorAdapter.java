package com.solar.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solar.Model.Appliances;
import com.solar.R;

import java.util.ArrayList;


public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder> {

    //Generals
    private ArrayList<Appliances> mAppliances;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public CalculatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appliance_layout, parent, false);
        CalculatorViewHolder calculatorViewHolder = new CalculatorViewHolder(view, mListener);
        return calculatorViewHolder;
    }

    public CalculatorAdapter(ArrayList<Appliances> appliances){
        mAppliances = appliances;
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculatorViewHolder holder, int position) {
        Appliances currentAppliance = mAppliances.get(position);

        holder.name.setText(currentAppliance.getmApplianceName());
        holder.wattage.setText(currentAppliance.getmApplianceWattage() + " Watt");
        holder.quantity.setText(currentAppliance.getmApplianceQuantity() + " Pieces");
        holder.Bv.setText(currentAppliance.getBettryType());
        holder.Iv.setText(currentAppliance.getInverterType());
        holder.PV.setText(currentAppliance.getPlatesType());


    }

    @Override
    public int getItemCount() {
        return mAppliances.size();
    }

    //This is a public static inner class for our ViewHolder
    public static class CalculatorViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView wattage;
        public TextView duration;
        public TextView quantity;
        public TextView Bv;
        public TextView Iv;
        public TextView PV;
        public Button remove;
        //public TextView quantity;

        public CalculatorViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.AnV);
            wattage = itemView.findViewById(R.id.RIWV);

            quantity = itemView.findViewById(R.id.qV);
            Bv = itemView.findViewById(R.id.Bv);
            Iv = itemView.findViewById(R.id.Iv);
            PV = itemView.findViewById(R.id.PV);
            remove = itemView.findViewById(R.id.delete);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}