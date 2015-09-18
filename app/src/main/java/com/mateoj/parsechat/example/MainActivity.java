package com.mateoj.parsechat.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mateoj.parsechat.ParseChat;
import com.mateoj.parsechat.model.ParseConversation;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseChat.showConversations(MainActivity.this);
            }
        });
        if (ParseUser.getCurrentUser() == null) {
            login();
        } else {
            startWithCurrentUser();
        }
    }

    private void startWithCurrentUser() {
        ParseChat.setUser(ParseUser.getCurrentUser());
        ParseConversation conversation = new ParseConversation.Builder()
                .setSender(ParseUser.getCurrentUser())
                .setReceiver("wFVfB0023R")
                .setTitle("Conversation title")
                .build();
//        ParseChat.startConversation(conversation);
    }

    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    startWithCurrentUser();
                    Log.d("Parse", user.toString());
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
