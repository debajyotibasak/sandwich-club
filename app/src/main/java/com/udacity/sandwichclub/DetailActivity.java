package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private ImageView mImvSandwich;
    private TextView mTxvSandwichName, mTxvAlsoKnowAsLabel, mTxvAlsoKnownAs, mTxvOriginLabel, mTxvOrigin, mTxvDescription, mTxvIngredients;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private void initView() {
        setContentView(R.layout.activity_detail);
        mImvSandwich = findViewById(R.id.imv_sandwich);
        mTxvSandwichName = findViewById(R.id.txv_sandwich_name);
        mTxvAlsoKnowAsLabel = findViewById(R.id.txv_aka_label);
        mTxvAlsoKnownAs = findViewById(R.id.txv_aka);
        mTxvOriginLabel = findViewById(R.id.txv_place_label);
        mTxvOrigin = findViewById(R.id.txv_place_origin);
        mTxvDescription = findViewById(R.id.txv_description);
        mTxvIngredients = findViewById(R.id.txv_ingredients);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI(sandwich);
        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .resize(0, 200)
                .into(mImvSandwich);
    }

    private void populateUI(Sandwich sandwich) {
        mTxvSandwichName.setText(sandwich.getMainName());
        mTxvAlsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        mTxvOrigin.setText(sandwich.getPlaceOfOrigin());
        mTxvDescription.setText(sandwich.getDescription());
        mTxvIngredients.setText(String.format("%s %s", getResources().getString(R.string.bullet), TextUtils.join("\n\u2022 ", sandwich.getIngredients())));

        if (mTxvAlsoKnownAs.getText().toString().isEmpty()) {
            mTxvAlsoKnownAs.setVisibility(View.GONE);
            mTxvAlsoKnowAsLabel.setVisibility(View.GONE);
        }

        if (mTxvOrigin.getText().toString().isEmpty()) {
            mTxvOrigin.setVisibility(View.GONE);
            mTxvOriginLabel.setVisibility(View.GONE);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
