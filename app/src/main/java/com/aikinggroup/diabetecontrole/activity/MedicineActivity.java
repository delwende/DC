package com.aikinggroup.diabetecontrole.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.aikinggroup.diabetecontrole.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(MedicineActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
