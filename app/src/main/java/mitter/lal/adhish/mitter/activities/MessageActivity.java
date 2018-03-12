package mitter.lal.adhish.mitter.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import mitter.lal.adhish.mitter.R;
import mitter.lal.adhish.mitter.helpers.HideKeyBoard;
import mitter.lal.adhish.mitter.helpers.Logger;
import mitter.lal.adhish.mitter.helpers.NetworkHelper;
import mitter.lal.adhish.mitter.helpers.SnackBarHelper;
import mitter.lal.adhish.mitter.networks.retrofithelpers.RetrofitClient;
import mitter.lal.adhish.mitter.networks.retrofithelpers.RetrofitInterface;
import mitter.lal.adhish.mitter.networks.retrofithelpers.responsemodels.RetrofitResponse;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Adhish on 11/03/18.
 */

public class MessageActivity extends BaseActivity {


    TextView tvLogo, tvSend, tvValueLabel, tvValue;
    EditText etMessage;
    ProgressBar progress;

    String actualValue;

    Handler handler;
    Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initializeViews();

        handler = new Handler();

        myRunnable = new Runnable() {
            @Override
            public void run() {

                String message = etMessage.getText().toString().trim();
                hitApi(message);

            }
        };



        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = etMessage.getText().toString().trim();
                HideKeyBoard.hideKeyboard(MessageActivity.this);
                prepareAPI(message, v);
            }
        });


        etMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeCallbacks(myRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializeViews()
    {
        tvLogo = findViewById(R.id.tvLogo);
        tvSend = findViewById(R.id.tvSend);
        tvValueLabel = findViewById(R.id.tvValueLabel);
        tvValue = findViewById(R.id.tvValue);

        etMessage = findViewById(R.id.etMessage);

        progress = findViewById(R.id.progress);

    }

    private void prepareAPI(String message, View v)
    {
        if(message.length() > 0) {

            if(NetworkHelper.isNetworkAvailable(MessageActivity.this)) {

                etMessage.setText("");
                hitApi(message);
            }
            else
            {
                SnackBarHelper.getSnackBar(v,"Please turn on your internet connection").show();
            }
        }
        else
        {
            SnackBarHelper.getSnackBar(v,"Please enter a message").show();
        }
    }


    private void hitApi(String message)
    {
        progress.setVisibility(View.VISIBLE);

        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<RetrofitResponse> call = retrofitInterface.hitApi(message);

        call.enqueue(new Callback<RetrofitResponse>() {
            @Override
            public void onResponse(Call<RetrofitResponse> call, retrofit2.Response<RetrofitResponse> response) {

                progress.setVisibility(View.GONE);

                String responseString = new Gson().toJson(response.body());

                Logger.d("Response", responseString);
                Logger.d("URL",response.raw().request().url()+"");

                int code = response.body().getCode();
                String value = response.body().getValue();


                if(code < 300 && code>=200)
                {
                    tvValue.setTextColor(getResources().getColor(R.color.black));
                }
                else
                {
                    tvValue.setTextColor(getResources().getColor(R.color.red));
                }



                switch (code)
                {
                    case 200:
                        actualValue = "world!";
                        break;

                    case 404:
                        actualValue = "No value available";
                        break;

                    case 500:
                        actualValue = "Please try again later";
                        break;

                    case 403:
                        actualValue = "You don't have clearance!";
                        break;

                    default:
                        actualValue = "";
                }


                tvValue.setText(actualValue);


                handler.postDelayed(myRunnable, 10000);

            }

            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t) {

                progress.setVisibility(View.GONE);

                Logger.d("error", t.getMessage());

                handler.postDelayed(myRunnable, 10000);
            }
        });

    }
}
