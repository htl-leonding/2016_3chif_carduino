package com.example.alex.carduino;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Alex on 04.02.2016.
 */
public class ClientSocket implements Runnable{
    Socket client;
    DataInputStream input;
    DataOutputStream output;
    String command;

    public ClientSocket(String s) {
        command = s;
    }


    @Override
    public void run() {
        try {
            client = new Socket("192.168.1.128" , 1337);
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
            output.writeBytes(command +'\n');
            output.flush();
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
