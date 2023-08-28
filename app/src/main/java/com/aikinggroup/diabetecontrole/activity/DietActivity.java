package com.aikinggroup.diabetecontrole.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.aikinggroup.diabetecontrole.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(DietActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
