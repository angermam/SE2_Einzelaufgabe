package com.example.networktest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SimpleTCPClient extends Thread{
    private String returnFromServer;
    private String inputMNr;
    private Socket mySocket = null;
    private DataOutputStream myOutputStream = null;
    private BufferedReader myInputStream = null;

    public SimpleTCPClient(String inputMNr) {
        this.inputMNr = inputMNr;
    }

    public String getReturnFromServer() {
        return returnFromServer;
    }


    public void run() {
        try {
            mySocket = new Socket("se2-isys.aau.at", 53212);
            Log.d("Info: ", Boolean.toString(mySocket.isConnected()));
            myOutputStream = new DataOutputStream(mySocket.getOutputStream());
            myInputStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            if(inputMNr != "") {
                myOutputStream.writeBytes(inputMNr);
            }
            Log.d("negor", "nigayasdeae");
            returnFromServer =  myInputStream.readLine();
            Log.d("yoyo", returnFromServer);
            Log.d("xoxoxox", "nigayasdeae");
            mySocket.close();

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
    }
}
