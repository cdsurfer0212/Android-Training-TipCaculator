package com.example.tipcalculator;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CustomPercentageAdapter extends ArrayAdapter<String> {
    private Double amount;
    public CustomPercentageAdapter(Context context, ArrayList<String> customPercentageItems, Double amount) {
        super(context, 0, customPercentageItems);
        
        this.amount = amount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String customPercentageText = getItem(position);  
        
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_percentage_item, parent, false);
        }
      
        //EditText etAmount = (EditText) parent.findViewById(R.id.etAmount); // can't get the view
        double customPercentage = 0.0;
        try
        {
           customPercentage = Double.parseDouble(customPercentageText);
        }
        catch (NumberFormatException e)
        {
           customPercentage = 0.0;
        } 
        
        double tip = amount * customPercentage;
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        NumberFormat percentageFormat = NumberFormat.getPercentInstance();
        
        TextView tvItemPercentage = (TextView) convertView.findViewById(R.id.tvItemPercentage);
        tvItemPercentage.setText(percentageFormat.format(customPercentage));
        
        TextView tvItemTip = (TextView) convertView.findViewById(R.id.tvItemTip);
        tvItemTip.setText(currencyFormat.format(tip));
        
        return convertView;
    }    
    
}
