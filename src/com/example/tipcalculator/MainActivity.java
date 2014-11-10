package com.example.tipcalculator;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

    private double amount = 0.0; 
    private double percentage = 0.2;
    private CustomPercentageAdapter customPercentageAdapter;
    private ArrayList<String> customPercentageItems;
    
    
    private EditText etAmount;
    private SeekBar sbPercentage;
    private ListView listView;
    private TextView tvPercentage;
    private TextView tvResult;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText(currencyFormat.format(0.0));
        
        etAmount = (EditText) findViewById(R.id.etAmount);
        etAmount.addTextChangedListener(amountEditTextWatcher);
        
        sbPercentage = (SeekBar) findViewById(R.id.sbPercentage);
        sbPercentage.setOnSeekBarChangeListener(percentageOnSeekBarChangeListener);
        
        tvPercentage = (TextView) findViewById(R.id.tvPercentage);
        tvPercentage.setText(percentFormat.format(sbPercentage.getProgress() / 100.0));
        
        this.readCustomPercentageItems();
        this.populateCustomPercentageList();
        setupCustomPercentageListListner();
    }
    
    private void populateCustomPercentageList() {
        customPercentageAdapter = new CustomPercentageAdapter(this, customPercentageItems, amount);
        
        listView = (ListView) findViewById(R.id.lvPercentage);
        listView.setAdapter(customPercentageAdapter);
    }
    
    private void setupCustomPercentageListListner() {
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                customPercentageItems.remove(pos);
                customPercentageAdapter.notifyDataSetChanged();
                writeCustomPercentageItems();
                
                return true;
            }
        });
    }
    
    private void readCustomPercentageItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "percentage.txt");
        
        try {
            customPercentageItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            customPercentageItems = new ArrayList<String>();
        }
    }
    
    private void writeCustomPercentageItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "percentage.txt");
        try {
            FileUtils.writeLines(todoFile, customPercentageItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void onAddPercentageItem(View v) {
        customPercentageAdapter.add(String.valueOf(percentage));
        writeCustomPercentageItems();
    }
    
    public void onCaculateTenPercent(View v) {
        percentage = 0.1;
        sbPercentage.setProgress(10);
        tvResult.setText(currencyFormat.format(this.caculateTip()));
    }

    public void onCaculateFifteenPercent(View v) {
        percentage = 0.15;
        sbPercentage.setProgress(15);
        tvResult.setText(currencyFormat.format(this.caculateTip()));
    }
    
    public void onCaculateTwentyPercent(View v) {
        percentage = 0.20;
        sbPercentage.setProgress(20);
        tvResult.setText(currencyFormat.format(this.caculateTip()));
    }
    
    public Double caculateTip() {
        double tip = amount * percentage;
        
        return tip;
    }
    
    private OnSeekBarChangeListener percentageOnSeekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percentage = progress / 100.0;
            tvResult.setText(currencyFormat.format(caculateTip()));
            tvPercentage.setText(percentFormat.format(percentage));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        } 

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }; 
    
    private TextWatcher amountEditTextWatcher = new TextWatcher() {
       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) 
       {         
          try
          {
             amount = Double.parseDouble(s.toString());
          }
          catch (NumberFormatException e)
          {
             amount = 0.0;
          } 

          tvResult.setText(currencyFormat.format(caculateTip()));
          
          populateCustomPercentageList();
       }

       @Override
       public void afterTextChanged(Editable s) 
       {
       }

       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) 
       {
       } 
    }; 
}
