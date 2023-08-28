package com.aikinggroup.diabetecontrole.activity;

import static com.aikinggroup.diabetecontrole.Constants.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.DiabeteControleApplication;
import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.adapter.LoginAdapter;
import com.aikinggroup.diabetecontrole.db.DatabaseHandler;
import com.aikinggroup.diabetecontrole.fragment.LoginTabFragment;
import com.aikinggroup.diabetecontrole.presenter.LoginPresenter;
import com.aikinggroup.diabetecontrole.presenter.MainPresenter;
import com.aikinggroup.diabetecontrole.tools.LocaleHelper;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private LoginPresenter presenter;
    String token, tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        DiabeteControleApplication application = (DiabeteControleApplication) getApplication();

        initPresenters(application);
        if(!app.currentUser().getCustomData().isEmpty()) {
            Log.i("EXAMPLE", app.currentUser().getCustomData().toJson());
           Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if(data!=null) {
            token=data.getQueryParameter("token");
            tokenId=data.getQueryParameter("tokenId");
       app.getEmailPassword().confirmUserAsync(token, tokenId, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully confirmed new user.");
                showAlert("Account Created Successfully!", "Please verify your email before Login", false);

            } else {
                Log.e("EXAMPLE", "Failed to confirm user: " + it.getError().getErrorMessage());
                showAlert("Error Account Creation failed", "Account could not be created" + " :" +it.getError().getErrorMessage(), true);

            }
        });
        }


        tabLayout =findViewById(R.id.lr_tabs_layout);
        viewPager =findViewById(R.id.lr_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Patient"));
        tabLayout.addTab(tabLayout.newTab().setText("Consultant"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) );
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                    }
                });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) );

    }

    private void initPresenters(DiabeteControleApplication application) {

        final DatabaseHandler dbHandler = application.getDBHandler();
        presenter = new LoginPresenter(this, dbHandler);
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(this, LoginTabFragment.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}