package com.example.networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnRequest = null;
    Button btnSort = null;
    TextView tVSort = null;
    EditText mNrInput = null;
    TextView tVReply = null;
    Socket mySocket = null;
    DataOutputStream myOutputStream = null;
    BufferedReader myInputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByID();
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSth();
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortMNr();
            }
        });

    }

    private void findViewsByID() {
        btnSort = (Button)findViewById(R.id.btnCalculate);
        tVSort = (TextView)findViewById(R.id.tVSort);
        btnRequest = (Button)findViewById(R.id.btnSend);
        mNrInput = (EditText)findViewById(R.id.inputMNr);
        tVReply = (TextView)findViewById(R.id.textViewReply);

    }

    private void SortMNr() {
        int length = mNrInput.getText().toString().length();
        int[] sortNumbers = new int[length];
        List<Integer> evenNumbers = new ArrayList<Integer>();
        List<Integer> oddNumbers = new ArrayList<Integer>();
        String output = "";
        for(int i = 0; i < length; i++) {
            sortNumbers[i] = mNrInput.getText().charAt(i) - '0';
        }
        for(int i = 0; i < sortNumbers.length; i++) {
            if(sortNumbers[i] % 2 == 0) {
                evenNumbers.add(sortNumbers[i]);
            } else {
                oddNumbers.add(sortNumbers[i]);
            }
        }
        Collections.sort(evenNumbers);
        Collections.sort(oddNumbers);
        output = evenNumbers.toString() + oddNumbers.toString();
        tVSort.setText(output);
    }

    private void doSth() {
        SimpleTCPClient s = new SimpleTCPClient(mNrInput.getText().toString());
        Log.d("INFO", "Starting TCPClient Thread");
        s.start();
        try {
            s.join();
        } catch (InterruptedException ex) {
            Log.e("Exception:", ex.getMessage());
        }

        Log.d("Info", "Waiting is over");
        tVReply.setText(s.getReturnFromServer());
    }
}
