package com.aikinggroup.diabetecontrole.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.aikinggroup.diabetecontrole.DiabeteControleApplication;
import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.presenter.HelloPresenter;
import com.aikinggroup.diabetecontrole.tools.LabelledSpinner;
import com.aikinggroup.diabetecontrole.view.HelloView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.realm.Realm;


public class HelloActivity extends AppCompatActivity implements HelloView {

    @BindView(R.id.activity_hello_spinner_gender)
    LabelledSpinner genderSpinner;


    @BindView(R.id.activity_hello_lastname)
    TextView lastname;

    @BindView(R.id.activity_hello_firstname)
    TextView firstname;

    @BindView(R.id.activity_hello_email)
    TextView email;

    @BindView(R.id.activity_hello_telephone)
    TextView telephone;

    @BindView(R.id.activity_hello_password)
    TextView password;

    @BindView(R.id.activity_hello_spinner_diabetes_type)
    LabelledSpinner typeSpinner;

    @BindView(R.id.activity_hello_spinner_preferred_unit)
    LabelledSpinner unitSpinner;

    @BindView(R.id.activity_hello_button_start)
    Button startButton;

    @BindView(R.id.activity_hello_button_previous)
    Button previousButton;

    @BindView(R.id.activity_hello_age)
    TextView ageTextView;

   private HelloPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ButterKnife.bind(this);


        // Prevent SoftKeyboard to pop up on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        DiabeteControleApplication application = (DiabeteControleApplication) getApplication();
        presenter = application.createHelloPresenter(this);
        presenter.loadDatabase();

        genderSpinner.setItemsArray(R.array.helloactivity_gender_list);
        unitSpinner.setItemsArray(R.array.helloactivity_preferred_glucose_unit);
        typeSpinner.setItemsArray(R.array.helloactivity_diabetes_type);

        initStartButton();

        Log.i("HelloActivity", "Setting screen name: hello");


    }


    private void initStartButton() {
        final Drawable pinkArrow = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_navigate_next_pink_24px, null);
        if (pinkArrow != null) {
            pinkArrow.setBounds(0, 0, 60, 60);
            startButton.setCompoundDrawables(null, null, pinkArrow, null);
        }
    }


    @OnClick(R.id.activity_hello_button_start)
    void onStartClicked() {
        presenter.onNextClicked(
                lastname.getText().toString(),
                firstname.getText().toString(),
                email.getText().toString(),
                telephone.getText().toString(),
                password.getText().toString()
               );
    }

    @OnClick(R.id.activity_hello_button_previous)
    void onStartPreviousClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void displayErrorWrongAge() {
        //Why toast and not error in edit box or dialog
        Toast.makeText(getApplicationContext(), getString(R.string.helloactivity_age_invalid), Toast.LENGTH_SHORT).show();
    }

    public void startMainView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


}
