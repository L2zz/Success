package edu.skku.sw3.success;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {



    // UI references.

    private EditText mEmailView;
    private EditText mPasswordView;


    // 파이어베이스 인증 객체 생성
    private FirebaseAuth mAuth;

    // Log
    private String TAG = "LoginActivity";

    // 이메일과 비밀번호 EditText
    private EditText editTextEmail;
    private EditText editTextPassword;

    // 이메일 , 비밀번호 정보
    private String email = "";
    private String password = "";


    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (password.length() < 6) {
            // 비밀번호 길이 너무 짧음
            Toast.makeText(this, "비밀번호의 길이가 너무 짧습니다 !", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    // 로그인
    public void signIn() {
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            signIn_with_EmailAndPW(email, password);
        }

    }

    private void signIn_with_EmailAndPW(final String input_email, String input_password)
    {
        Toast.makeText(this, "로그인 시도 중 ..", Toast.LENGTH_SHORT).show();


        mAuth.signInWithEmailAndPassword(input_email, input_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "로그인 하였습니다.", Toast.LENGTH_SHORT).show();
                            mEmailView.setText("");
                            mPasswordView.setText("");

                            // Go to MainActivity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 다시 확인하세요",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email_ET);
        mPasswordView = (EditText) findViewById(R.id.password_ET);

        // Firebase
        mAuth = FirebaseAuth.getInstance();


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

        Button mPasswordSearchButton = (Button) findViewById(R.id.search_password_button); // password search button


        // event handler

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input_email = mEmailView.getText().toString();
                String input_password = mPasswordView.getText().toString();

                email = input_email;
                password = input_password;

                if(!isValidEmail()){
                    Toast.makeText(LoginActivity.this, "이메일 형식에 문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!isValidPasswd()){

                    Toast.makeText(LoginActivity.this, "비밀번호의 길이가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                    signIn();

                }




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

        mPasswordSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // PasswordActivity 를 불러온다
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);

            }
        });








    }
}
