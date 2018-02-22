package com.example.anthony_pc.tipodecambio;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView txtResultado;
    private RadioGroup radioGroup;
    private EditText montoIngresado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResultado = findViewById(R.id.txtResultado);
        radioGroup = findViewById(R.id.radioGroup);
        montoIngresado = findViewById(R.id.editMonto);
        mQueue = Volley.newRequestQueue(this);

    }

    public void onClickCambio(View view){
        if(montoIngresado.getText().equals(""))
            Toast.makeText(this,"Por favor ingrese un monto",Toast.LENGTH_SHORT).show();
        else{
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.radioBtnCRC:
                    parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=USD_CRC&compact=ultra");
                    break;
                case R.id.radioBtnUSD:
                    parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=CRC_USD&compact=ultra");
                    break;
            }
        }


    }

    public void parseJson(String url){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("response",response.get("USD_CRC").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(jsonObjReq);
    }
}
