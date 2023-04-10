package com.example.chattcp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText mMessageEditText;
    private Button mSendButton;
    private TextView mReceivedTextView;
    private Socket mSocket;
    private BufferedReader mBufferedReader;
    private PrintWriter mPrintWriter;
    private String mServerAddress = "localhost"; // Dirección IP del servidor
    private int mServerPort = 5555; // Puerto del servidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageEditText = findViewById(R.id.message_edit_text);
        mSendButton = findViewById(R.id.send_button);
        mReceivedTextView = findViewById(R.id.received_text_view);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(mServerAddress, mServerPort);
                    mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mPrintWriter = new PrintWriter(new OutputStreamWriter(mSocket.getOutputStream()), true);
                    while (true) {
                        String receivedMessage = mBufferedReader.readLine();
                        if (receivedMessage != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mReceivedTextView.append("Server: " + receivedMessage + "\n");
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendMessage() {
        String message = mMessageEditText.getText().toString();
        if (!message.isEmpty()) {
            mPrintWriter.println(message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mReceivedTextView.append("You: " + message + "\n");
                    mMessageEditText.setText("");
                }
            });
        }
    }


}