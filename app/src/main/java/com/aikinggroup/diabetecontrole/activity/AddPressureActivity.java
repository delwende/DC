package com.aikinggroup.diabetecontrole.activity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.db.PressureReading;
import com.aikinggroup.diabetecontrole.presenter.AddPressurePresenter;
import com.aikinggroup.diabetecontrole.tools.FormatDateTime;

import java.util.Calendar;

public class AddPressureActivity extends AddReadingActivity {

    private TextView minPressureTextView;
    private TextView maxPressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pressure);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
        }

        this.retrieveExtra();

        AddPressurePresenter presenter = new AddPressurePresenter(this);
        setPresenter(presenter);
        presenter.setReadingTimeNow();

        minPressureTextView = findViewById(R.id.pressure_add_value_min);
        maxPressureTextView = findViewById(R.id.pressure_add_value_max);

        this.createDateTimeViewAndListener();
        this.createFANViewAndListener();

        // Initialize value
        FormatDateTime formatDateTime = new FormatDateTime(getApplicationContext());
        if (this.isEditing()) {
            // set edit title
            setTitle(R.string.title_activity_add_pressure_edit);
            PressureReading readingToEdit = presenter.getPressureReadingById(this.getEditId());

            // set reading values
            minPressureTextView.setText(numberFormat.format(readingToEdit.getMinReading()));
            maxPressureTextView.setText(numberFormat.format(readingToEdit.getMaxReading()));

            // set reading time
            Calendar cal = Calendar.getInstance();
            cal.setTime(readingToEdit.getCreated());
            this.getAddDateTextView().setText(formatDateTime.getDate(cal));
            this.getAddTimeTextView().setText(formatDateTime.getTime(cal));
            presenter.updateReadingSplitDateTime(readingToEdit.getCreated());
        } else {
            this.getAddDateTextView().setText(formatDateTime.getCurrentDate());
            this.getAddTimeTextView().setText(formatDateTime.getCurrentTime());
        }

    }

    @Override
    protected void dialogOnAddButtonPressed() {
        AddPressurePresenter presenter = (AddPressurePresenter) getPresenter();
        // If an id is passed, open the activity in edit mode
        if (this.isEditing()) {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), minPressureTextView.getText().toString(), maxPressureTextView.getText().toString(), this.getEditId());
        } else {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), minPressureTextView.getText().toString(), maxPressureTextView.getText().toString());
        }
    }

    public void showErrorMessage() {
        Toast.makeText(getApplicationContext(), getString(R.string.dialog_error2), Toast.LENGTH_SHORT).show();
    }
}
