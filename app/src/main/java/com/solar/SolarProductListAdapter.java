package com.solar;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solar.Databasehandle;
import com.solar.Helper;
import com.solar.Model.FetchAllSolarProductList;
import com.solar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

public class SolarProductListAdapter extends  RecyclerView.Adapter<SolarProductListAdapter.ViewHolder>  {

List<FetchAllSolarProductList> productLists;
Context context;

Databasehandle databasehandle;

    public SolarProductListAdapter(List<FetchAllSolarProductList> productLists, Context context) {
        this.productLists = productLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_solar_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        FetchAllSolarProductList modelProduct = productLists.get(position);
        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes= modelProduct.getDiscountNotes();
        String discountPrice= modelProduct.getDiscountprices();

        String productIcon= modelProduct.getSimage();
        String productDescription= modelProduct.getDescription();
        String origonalPrice= modelProduct.getOrigonalPrice();

        String productTitle= modelProduct.getTitle();

        String uid= modelProduct.getId();


        //set data

        //set data
        holder.titleTv.setText(""+productTitle);
        // holder.quantityTv.setText(""+productQuantity);

        holder.discountNoteTv.setText(""+discountNotes+"%"+""+"OFF");
        holder.discountPriceTV.setText("Rs"+discountPrice);
        holder.origonalPriceTv.setText("Rs"+origonalPrice);

        if (discountAvaliabel.equals("1")){
            holder.discountPriceTV.setVisibility(View.VISIBLE);
            holder.discountNoteTv.setVisibility(View.VISIBLE);
            holder.origonalPriceTv.setPaintFlags(holder.origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.discountPriceTV.setVisibility(View.GONE);
            holder.discountNoteTv.setVisibility(View.GONE);
            holder.origonalPriceTv.setPaintFlags(0);
        }

        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_add_shopping_cart_primary).into(holder.productIconIv);

        }catch (Exception e){

            holder.productIconIv.setImageResource(R.drawable.ic_add_shopping_cart_primary);

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailBottomSheet(modelProduct);
            }
        });

        holder.addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show prodct Detail
                ShowProductQuantity(modelProduct);
            }
        });

    }
    private double cost = 0;
    private double finalCost = 0;
    String price ;
    private  int quantity = 0;

    private void ShowProductQuantity(FetchAllSolarProductList modelProduct) {



        View view = LayoutInflater.from(context).inflate(R.layout.dialoug_box_cart,null);
        ImageView productIv;
        final TextView titleTv,quantityTv,descriptionTv,discountNoteTv,
                origonalPriceTv,discountPriceTV,finalText,countTv;
        ImageButton decrmentBtn,incerementBtn;
        Button addCartBtn;
        productIv= view.findViewById(R.id.productIv);
        titleTv = view.findViewById(R.id.titleTv);
        discountNoteTv = view.findViewById(R.id.discountNoteTv);
        origonalPriceTv = view.findViewById(R.id.origonalPriceTv);
        discountPriceTV = view.findViewById(R.id.discountPriceTV);
        finalText = view.findViewById(R.id.finalText);
        countTv = view.findViewById(R.id.countTv);
        decrmentBtn = view.findViewById(R.id.decrmentBtn);
        incerementBtn = view.findViewById(R.id.incerementBtn);
        addCartBtn = view.findViewById(R.id.addCartBtn);
        //get Dataa

        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes= modelProduct.getDiscountNotes();
        String discountPrice= modelProduct.getDiscountprices();

        String productIcon= modelProduct.getSimage();

        String origonalPrice= modelProduct.getOrigonalPrice();

        String productTitle= modelProduct.getTitle();

        String uid = modelProduct.getId();
        final String tempId = String.valueOf(uid);



        if (discountAvaliabel.equals("1")){
            price = modelProduct.getDiscountprices();

            discountPriceTV.setVisibility(View.VISIBLE);

            discountNoteTv.setVisibility(View.VISIBLE);
            origonalPriceTv.setPaintFlags(origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }else {

            discountPriceTV.setVisibility(View.GONE);
            discountNoteTv.setVisibility(View.GONE);
            price = modelProduct.getOrigonalPrice();

        }

        cost = Double.parseDouble(price.replaceAll("Rs", ""));
        finalCost= Double.parseDouble(price.replaceAll("Rs", ""));
        quantity = 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        discountNoteTv.setText(""+discountNotes+"%-"+"OFF");
        discountPriceTV.setText("Rs"+discountPrice);
        origonalPriceTv.setText("Rs"+origonalPrice);


        titleTv.setText(""+productTitle);
        finalText.setText(""+finalCost);


        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_add_shopping_cart_primary).into(productIv);

        }catch (Exception e){

            productIv.setImageResource(R.drawable.ic_add_shopping_cart_primary);

        }
        final AlertDialog dialog = builder.create();
        dialog.show();

        incerementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>5){
                    Toast.makeText(context,"Please Select 5 sevicres At a Time ",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    finalCost = finalCost + cost;
                    quantity++;
                    finalText.setText(""+finalCost);
                    countTv.setText(""+quantity);
                }



            }
        });
        decrmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    finalCost = finalCost - cost;
                    quantity--;

                    finalText.setText(""+finalCost);
                    countTv.setText(""+quantity);

                }
            }
        });

        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTv.getText().toString().trim();

                String priceEach = price;
                String totalPrice  = finalText.getText().toString().trim().replace("Rs","");

                String count = countTv.getText().toString().trim();

                //add data into sqlite
                addToCart(tempId,title,priceEach,totalPrice,count);
                dialog.dismiss();



            }
        });



    }

    private void addToCart(String tempId, String title, String priceEach, String totalPrice, String count) {
databasehandle = new Databasehandle(context);

       if(databasehandle.insertData(tempId,title,priceEach,price,count)){
           Helper.SHowToast(context,"Added");
       }

      //  ((Sub_cat_Activity)mCtx).CartCount();

    }

    private void detailBottomSheet(FetchAllSolarProductList modelProduct) {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //infilate view For Bottom Sheet
        View view =  LayoutInflater.from(context).inflate(R.layout.item_detail_bottom,null);
        bottomSheetDialog.setContentView(view);

        TextView discountNoteTv,titleTv,descriptionTv,discountPriceTV,origonalPriceTv,duration;
        titleTv = view.findViewById(R.id.titleTv);
        discountNoteTv= view.findViewById(R.id.discountNoteTv);
        duration = view.findViewById(R.id.duration);
        origonalPriceTv = view.findViewById(R.id.origonalPriceTv);
        discountPriceTV = view.findViewById(R.id.discountPriceTV);
        descriptionTv = view.findViewById(R.id.description);

        final String id = modelProduct.getId();

        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes = modelProduct.getDiscountNotes();
        String discountPrice = modelProduct.getDiscountprices();
        String  productDescription = modelProduct.getDescription();
        String  productDuration = modelProduct.getDuration();



        final String productTitle = modelProduct.getTitle();
        String origonalPrice = modelProduct.getOrigonalPrice();

        titleTv.setText(productTitle);
        descriptionTv.setText(productDescription);

        discountPriceTV.setText("Rs:"+discountPrice);
        discountNoteTv.setText(discountNotes+"%-OFF");
        origonalPriceTv.setText("Rs:"+origonalPrice);
        duration.setText(productDuration);



        if (discountAvaliabel.equals("1")){
            discountPriceTV.setVisibility(View.VISIBLE);
            discountNoteTv.setVisibility(View.VISIBLE);
            origonalPriceTv.setPaintFlags(origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            discountPriceTV.setVisibility(View.GONE);
            discountNoteTv.setVisibility(View.GONE);

        }
        //show dialoug
        bottomSheetDialog.show();


    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productIconIv;

        TextView discountNoteTv,titleTv,quantityTv,discountPriceTV,origonalPriceTv,addcart,descriptionTV;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);

            titleTv = itemView.findViewById(R.id.titleTv);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);

            discountPriceTV = itemView.findViewById(R.id.discountPriceTV);
            origonalPriceTv = itemView.findViewById(R.id.origonalPriceTv);
            productIconIv = itemView.findViewById(R.id.productIconIv);

            addcart = itemView.findViewById(R.id.cartTv);



        }
    }
}
