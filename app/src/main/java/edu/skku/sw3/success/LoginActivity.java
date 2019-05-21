package edu.skku.sw3.success;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
    TODO : (Login) String resource 한글로 작성
    TODO : (Login) Layout 다듬기
 */

public class LoginActivity extends AppCompatActivity {

    // UI references.

    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.

        mEmailView = (EditText) findViewById(R.id.email_edit);
        mPasswordView = (EditText) findViewById(R.id.password);

        // event handler

        mEmailView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL){
                    return true;
                }
                return false;
            }

        });
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {


                    return true;
                }
                return false;
            }
        });

        // Button

        Button mEmailSignInButton = (Button) findViewById(R.id.log_in_Button); // sign in button

        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_up_button); // sign up button


        // event handler

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mEmailSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // SignUpActivity 를 불러온다
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);


            }
        });
    }
}
