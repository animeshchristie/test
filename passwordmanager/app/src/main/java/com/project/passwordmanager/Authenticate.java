package com.project.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.passwordmanager.Utils.AESEncryptor;
import com.project.passwordmanager.Utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Authenticate extends AppCompatActivity {

    private EditText passcodeText;
    private String passcode;
    private Button unlockbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        setComponent();
        unlockbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUnlock(v);
            }
        });

    }

    public void setComponent(){
        passcodeText = (EditText)findViewById(R.id.password);
        unlockbutton = (Button)findViewById(R.id.unlockbutton);
        passcode = passcodeText.getText().toString();
    }

    public void processUnlock(View v){
        setComponent();
        try {
            String fileData = readDataFromFile();
            if (fileData != null) {
                JSONObject fileDataObject = new JSONObject(fileData);
                if(fileDataObject!= null){
                    JSONArray personDetailArray = fileDataObject.getJSONArray("personDetail");
                    if(personDetailArray != null && personDetailArray.length()== 1){
                        JSONObject personDetailObj = (JSONObject)personDetailArray.get(0);
                        String passCode = personDetailObj.getString("passcode");
                        if(passCode != null && passCode.equals(passcode)){
                            AppUtils.makeLongToast(getApplicationContext(),"Passcode is valid....");
                        } else {
                            AppUtils.makeLongToast(getApplicationContext(),"Passcode is not valid....");
                        }

                    } else {
                        AppUtils.makeLongToast(getApplicationContext(),"Passcode is not valid....");
                    }
                } else {
                    AppUtils.makeLongToast(getApplicationContext(),"Passcode is not valid....");
                }
            } else {
                AppUtils.makeLongToast(getApplicationContext(),"Passcode is not valid....");
            }
        }catch (JSONException e){
            AppUtils.makeLongToast(getApplicationContext(),"Passcode is not valid....");
        }
    }

    public String readDataFromFile(){
        String fileData = AESEncryptor.readDataFile(getApplicationContext(),passcode);
        return fileData;
    }

    public void processRegister(View view){
       Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}
