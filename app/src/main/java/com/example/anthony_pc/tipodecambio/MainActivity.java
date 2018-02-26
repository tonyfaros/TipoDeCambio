package com.example.anthony_pc.tipodecambio;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView txtResultado;
    private RadioGroup radioGroup;
    private EditText montoIngresado;
    private Double tipoCambio = 0.000;
    private Double resultado = 0.000;
    private ImageView imgIngreso;
    private ImageView imgResul;


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
        parseJson("http://www.apilayer.net/api/live?access_key=28f50adf000d76e4f35a832e251d65de");
        montoIngresado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!montoIngresado.getText().toString().equals("")){
                    try {
                        onClickCambio(null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{}
            }
        });

    }

    public void onClickCambio(View view) throws InterruptedException {
        Double monto = 0.000;

        try{
            monto = Double.parseDouble(montoIngresado.getText().toString());

        }catch (NumberFormatException e){
            Toast.makeText(this, "Número no válido", Toast.LENGTH_SHORT).show();
            txtResultado.setText("");
            montoIngresado.setText("");
            return;
        }

        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.radioBtnCRC:
                imgIngreso.setImageResource(R.drawable.dollar);
                imgResul.setImageResource(R.drawable.colones);

                resultado = monto * tipoCambio ;

                Log.e("cambio",tipoCambio.toString());
                txtResultado.setText(String.format("%.2f",resultado));
                break;
            case R.id.radioBtnUSD:
                imgResul.setImageResource(R.drawable.dollar);
                imgIngreso.setImageResource(R.drawable.colones);

                resultado = monto / tipoCambio ;
                Log.e("cambio",tipoCambio.toString());
                txtResultado.setText(String.format("%.2f",resultado));
                break;
        }



    }

    public void parseJson(String url){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.e("parse",response.getString("success"));
                            tipoCambio = Double.parseDouble(response.getJSONObject("quotes").get("USDCRC").toString());
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
