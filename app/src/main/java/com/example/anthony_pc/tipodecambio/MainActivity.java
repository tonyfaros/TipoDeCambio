package com.example.anthony_pc.tipodecambio;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
    private Double tipoCambio = 0.000;
    private Double resultado = 0.000;
    private ImageView imgIngreso;// =
    private ImageView imgResul;// =


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResultado = findViewById(R.id.txtResultado);
        radioGroup = findViewById(R.id.radioGroup);
        montoIngresado = findViewById(R.id.editMonto);
        mQueue = Volley.newRequestQueue(this);
        imgResul = findViewById(R.id.imageViewResu);
        imgIngreso = findViewById(R.id.imageViewIng);
        //parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=USD_CRC&compact=ultra");

    }

    public void onClickCambio(View view){
        final String CRC_USD = "CRC_USD";
        final String USD_CRC ="USD_CRC";
        Double monto = 0.000;

        try{
            monto = Double.parseDouble(montoIngresado.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Numero no valido", Toast.LENGTH_SHORT).show();;
            return;
        }

        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radioBtnCRC:
                imgIngreso.setImageResource(R.drawable.dollar);
                imgResul.setImageResource(R.drawable.colones);

                Log.e("prueba","USD_CRC");
                //parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=USD_CRC&compact=ultra",USD_CRC);
                //resultado = parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=USD_CRC&compact=ultra") * Integer.parseInt(montoIngresado.getText().toString());
                 resultado = (monto * 566.36);

                txtResultado.setText(String.format("%.2f",resultado));
                //txtResultado.setText(resultado.toString());
                break;
            case R.id.radioBtnUSD:
                imgResul.setImageResource(R.drawable.dollar);
                imgIngreso.setImageResource(R.drawable.colones);
                Log.e("prueba","usd");
                //parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=CRC_USD&compact=ultra",CRC_USD);
                //resultado = parseJson("http://free.currencyconverterapi.com/api/v3/convert?q=CRC_USD&compact=ultra") * Integer.parseInt(montoIngresado.getText().toString());
                resultado = (Integer.parseInt(montoIngresado.getText().toString()) / 566.36);
                txtResultado.setText(String.format("%.2f",resultado));
                break;
        }



    }

    public void parseJson(String url, final String tipo){
        tipoCambio = 0.0;
        resultado = 0.0;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("prueba",response.get("USD_CRC").toString());
                            tipoCambio = Double.parseDouble(response.get(tipo).toString());
                            resultado = tipoCambio * Integer.parseInt(montoIngresado.getText().toString());
                            txtResultado.setText(resultado.toString());
                            Log.e("prueba",response.get("USD_CRC").toString());
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
