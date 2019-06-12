package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";

    String input_email_global;
    String input_password_global;

    // Firebase.
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // 이메일 유효성 검사
    private boolean isValidEmail(String input_email) {
        if (input_email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input_email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText Email_inputText = findViewById(R.id.email_edit);
        final TextView Email_textView = findViewById(R.id.email_valid);
        final EditText pw1 = findViewById(R.id.password_input1);
        final EditText pw2 = findViewById(R.id.password_input2);
        final TextView password_TextView1 = findViewById(R.id.pw_valid1);
        final TextView password_TextView2 = findViewById(R.id.pw_valid2);
        final CheckBox checkBox = findViewById(R.id.privacy_checkbox);
        final Button privacy_button =findViewById(R.id.privacy_url_button);

        // Firebase instance
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 개인정보 처리방침에 체크하면 -> Toast msg
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    Toast.makeText(SignUpActivity.this, "개인정보 처리방침에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 개인정보 처리방침 확인 url 버튼
        privacy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 개인정보 처리방침 페이지로 연결
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://qpxhqpsll.blog.me/221560029594"));

                Intent intent = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        // 확인버튼 누르면?
        Button button_confirm = findViewById(R.id.sign_up_button);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // email 형식이 올바르지 않게 입력되었을 경우
                final String input_email = Email_inputText.getText().toString();
                if(! Patterns.EMAIL_ADDRESS.matcher(input_email).matches()){
                    Email_textView.setText("Email 형식이 올바르지 않습니다.");
                }
                else{
                    Email_textView.setText("");
                }

                // 입력받은 두 password 가 같지 않으면
                final String password1 = pw1.getText().toString();
                String password2 = pw2.getText().toString();
                if(password1.equals("")){
                    password_TextView1.setText("비밀번호를 입력해주세요.");
                }
                else{
                    password_TextView1.setText("");
                }

                if(!password1.equals(password2)){
                    password_TextView2.setText("비밀번호가 동일하지 않습니다.");
                }
                else{
                    password_TextView2.setText("");
                }

                // 정상 진행
                if((Email_textView.getText().toString().equals("")) && (password_TextView1.getText().toString().equals("")) && (password_TextView2.getText().toString().equals(""))
                        && checkBox.isChecked()
                ) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
                    // 제목셋팅
                    dialog.setTitle("회원가입 진행");
                    // AlertDialog 셋팅
                    dialog
                            .setMessage("입력하신 정보로 회원가입을 진행하시겠습니까?")
                            .setCancelable(false) //
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    input_email_global = Email_inputText.getText().toString();
                                    input_password_global = password1;


                                    if(!isValidEmail(input_email_global)) {

                                        Toast.makeText(SignUpActivity.this, "이메일 형식 오류", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else if(!isValiedPassword(input_password_global)) {

                                        Toast.makeText(SignUpActivity.this, "비밀번호의 길이는 6글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();

                                        return;
                                    }

                                    else {

                                        // 회원가입 진행
                                        //Toast.makeText(SignUpActivity.this, "회원가입 시도 중 ..", Toast.LENGTH_SHORT).show();

                                        SignUp(input_email_global, input_password_global);

                                        return;

                                    }
                                }

                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dialog.create();
                    dialog.show(); // 알림창 보여주기
                }
                // 예외처리
                else{
                    if(!checkBox.isChecked()) // 개인정보 체크박크 unchecked -> 진행하지 않음
                        Toast.makeText(SignUpActivity.this, "개인정보 처리방침에 동의해주세요.", Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(SignUpActivity.this, "다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 취소버튼 누르면 activity 종료
        Button button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);

                // 제목셋팅
                dialog.setTitle("회원가입 취소");

                // AlertDialog 셋팅
                dialog
                        .setMessage("회원가입을 취소하시겠습니까?")
                        .setCancelable(false) //
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(SignUpActivity.this, "회원가입 취소", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog.create();
                dialog.show(); // 알림창 보여주기





            }
        });
    }

    // 비밀번호의 길이가 6보다 작으면 return false
    private boolean isValiedPassword(String input_password_global) {
        int length = input_password_global.length();
        if(length <=5)
            return false;
        return true;
    }

    // user 의 Account 생성 ( SignUp )
    public void SignUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "이미 가입된 이메일 입니다.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            String email = user.getEmail();

                            mDatabase.child("user").child(uid).child("email").setValue(email)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Failure", "" + e);
                                }
                            });
                            finish();
                        }
                    }
                });
    }
}
