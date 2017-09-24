package com.adithyasairam.oddi;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChatClient;

public class ChatActivity extends AppCompatActivity {
    private static final String SERVER_TOKEN_URL = "http://75bda9a6.ngrok.io";
    ChatClient mChatClient = null;
    Channel mChannel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initTwilio();
        makeChannel();
    }

    private void initTwilio() {
        ChatClient.create(this, retrieveAccessTokenfromServer(),
                new ChatClient.Properties.Builder().createProperties(), new CallbackListener<ChatClient>() {
                    @Override
                    public void onSuccess(ChatClient chatClient) {
                        mChatClient = chatClient;
                    }
        });
    }

    private void makeChannel() {
        mChatClient.getChannels().createChannel("Project Groupchat", Channel.ChannelType.PRIVATE, new CallbackListener<Channel>() {
            @Override
            public void onSuccess(Channel channel) {
                mChannel = channel;
            }
        });
    }

    private String retrieveAccessTokenfromServer() {
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String tokenURL = SERVER_TOKEN_URL + "?device=" + deviceId;
        final String[] token = {null};
        Ion.with(this)
                .load(tokenURL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            String identity = result.get("identity").getAsString();
                            String accessToken = result.get("token").getAsString();
                            token[0] = accessToken;
                        } else {
                            Toast.makeText(ChatActivity.this,
                                    "Error retriving token", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
        return token[0];
    }
}