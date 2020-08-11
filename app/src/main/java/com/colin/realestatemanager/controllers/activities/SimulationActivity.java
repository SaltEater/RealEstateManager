package com.colin.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.colin.realestatemanager.R;
import com.colin.realestatemanager.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static butterknife.ButterKnife.bind;

public class SimulationActivity extends AppCompatActivity {
    @BindView(R.id.simulation_toolbar)
    Toolbar toolbar;
    @BindView(R.id.initial_amount_ed)
    TextInputEditText initialAmount;
    @BindView(R.id.rate_ed)
    TextInputEditText rate;
    @BindView(R.id.duration_ed)
    TextInputEditText duration;
    @BindView(R.id.calculate_button)
    Button calculate;
    @BindView(R.id.final_amount)
    TextView finalAmount;
    @BindView(R.id.monthly_payment)
    TextView monthlyAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        bind(this);
        configToolbar();
    }

    private void configToolbar() {
        toolbar.setTitle("Simulation");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_estate_activity_menu, menu);
        return true;
    }


    @OnClick(R.id.calculate_button)
    void onClickCalculate() {
        if (checkValues()) {
            double initialAmountValue = Double.parseDouble(Utils.getText(initialAmount));
            double durationValue = Double.parseDouble(Utils.getText(duration));
            double rateValue = Double.parseDouble(Utils.getText(rate)) / 100;

            double monthlyAmountValue = (initialAmountValue * (rateValue / 12)) / (1 - Math.pow( (1 + rateValue / 12), (-durationValue * 12)));
            double finalAmountValue = monthlyAmountValue*durationValue*12;

            String finalAmountText = "Final amount : " + finalAmountValue + " €";
            String monthlyAmountText = "Monthly amount : " + monthlyAmountValue + " € / month";
            finalAmount.setText(finalAmountText);
            monthlyAmount.setText(monthlyAmountText);
        }

    }

    private boolean checkValues() {
        return !Utils.getText(initialAmount).equals("") && !Utils.getText(rate).equals("") && !Utils.getText(duration).equals("");
    }

}
