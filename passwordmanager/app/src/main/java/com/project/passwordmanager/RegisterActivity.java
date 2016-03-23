package com.project.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.passwordmanager.Utils.AESEncryptor;
import com.project.passwordmanager.Utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText masterCode;
    private EditText confirmMastercode;
    private Button registerButtion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setComponent();
        registerButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRegistration(v);
            }
        });
    }

    public void setComponent(){
        name = (EditText)findViewById(R.id.name);
        masterCode = (EditText)findViewById(R.id.mastercode);
        confirmMastercode = (EditText)findViewById(R.id.confirmmastercode);
        registerButtion = (Button)findViewById(R.id.registerButton);
    }

    public void makeRegistration(View v) {
        setComponent();
        if(validateDetails()){
            try {
                String passCode = masterCode.getText().toString();
                if(passCode.length()>= AppUtils.MINIMUM_PASSWORD_LENGTH){
                    JSONObject fileObj = new JSONObject();
                    JSONArray personDetailArray = new JSONArray();
                    JSONObject personDetailObj = new JSONObject();
                    personDetailObj.put("name", name.getText().toString());
                    personDetailObj.put("passcode", passCode);
                    personDetailArray.put(personDetailObj);
                    fileObj.put("personDetail", personDetailArray);
                    AESEncryptor.writeDataFile(fileObj.toString(), getApplicationContext(), passCode);
                    AppUtils.makeLongToast(getApplicationContext(), "Registration done successfully....");
                    Intent loginIntent = new Intent(getApplicationContext(), Authenticate.class);
                    startActivity(loginIntent);
                } else {
                    AppUtils.makeLongToast(getApplicationContext(), "masterCode should be minimum 8 character long");
                    Intent loginIntent = new Intent(getApplicationContext(), Authenticate.class);
                    startActivity(loginIntent);
                }
            } catch (JSONException e){
                AppUtils.makeLongToast(getApplicationContext(),"Error while registration. please try after some time");
            }
        }
    }

    private boolean validateDetails(){
        if(masterCode != null && confirmMastercode != null && masterCode.getText().toString().equals(confirmMastercode.getText().toString())){
         return true;
        }
    return false;
    }
}
