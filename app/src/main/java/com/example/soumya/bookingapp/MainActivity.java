package com.example.soumya.bookingapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final String MyPreferences = "prefs";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "E2Yspa6wB82Yu8OgfzZc2hhg51S5COpW1kqveZeo", "3i0kL0U14FnxEtfhSF8QoRk2O28IbkFxOXO4iCdl");


        final EditText editText = (EditText) findViewById(R.id.editText1);
        final EditText editText1 = (EditText) findViewById(R.id.editText3);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                final String name = editText.getText().toString();
                final String mobile = editText1.getText().toString();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("phone");
                query.whereEqualTo("phone", mobile);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, com.parse.ParseException e) {
                        /*if (e == null) {
                            Toast.makeText(getApplicationContext(), "already signed up", Toast.LENGTH_SHORT).show();
                        } else {*/
                        if(mobile.equals(list)){
                          Toast.makeText(getApplicationContext(),"User already exists",Toast.LENGTH_SHORT).show();
                        }

                        else {
                            ParseUser user = new ParseUser();

                            user.setUsername(name);
                            user.setPassword(mobile);
                            //user.put("phone", mobile);

                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
 //                                   if (e == null) {
                                        ParseObject parseObject = new ParseObject("myApp");
                                        parseObject.put("name", name);
                                        parseObject.put("mobileNumber", mobile);
                                        parseObject.saveInBackground();

                                        editor = prefs.edit();
                                        editor.putString("Name", name);
                                        editor.putString("Mob_num", mobile);
                                        editor.commit();
                                        Toast.makeText(MainActivity.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(view.getContext(), RoomList.class);
                                        startActivity(intent);
   //                                 } else {
   //                                     Toast.makeText(getApplicationContext(), "Could not sign up", Toast.LENGTH_SHORT).show();
     //                               }
                                }
                            });
                        }
                    }
                });
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            final String name = editText.getText().toString();
            final String mobile = editText1.getText().toString();


            @Override
            public void onClick(final View v) {
                ParseUser.logInInBackground(name, mobile, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (parseUser != null) {
                            Toast.makeText(getApplicationContext(), "you are successfully logged in", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(v.getContext(),RoomList.class);
                            startActivity(in);
                            // Hooray! The user is logged in.
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                            // Signup failed. Look at the ParseException to see what happened.
                        }
                    }


                });
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
