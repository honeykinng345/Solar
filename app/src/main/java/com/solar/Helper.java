
package com.solar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Helper {


    public static void ShowDialoug(Context context, String Message) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(Message);
        progressDialog.show();

    }

    public static void hideDialoug(Context context) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.dismiss();
    }


    public  static  void  transectionActivityToActivity(Context context,Class context1){
        Intent intent = new Intent(context,context1);
        context.startActivity(intent);

    }


    public  static  void SHowToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static String spinnerItemSelected(int position, ArrayList<String> list) {
        // code here

        //spinner1.getSelectedItem().toString();
       String    selectedSize=  list.get(position);
       String[] arr = selectedSize.split(" = ");
        String s = arr[1];
    //   Pattern pattern = Pattern.compile("[^0-9]");
       //String numberOnly = pattern.matcher(selectedSize).replaceAll("");
       // Toast.makeText(this, numberOnly, Toast.LENGTH_SHORT).show();

        return s;
    }
}
