package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private String TAG = "PasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        final EditText et_email = findViewById(R.id.email_edit);
        final TextView tv_email = findViewById(R.id.email_valid);

        // 확인버튼 누르면?
        Button button_confirm = findViewById(R.id.sign_up_button);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // email 형식이 올바르지 않게 입력되었을 경우
                if(! Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
                    tv_email.setText("Email 형식이 올바르지 않습니다.");
                }
                else{
                    tv_email.setText("");
                }



                if((tv_email.getText().toString().equals("")) ) {

                    // 서버와 통신해서 Sign up 해주기

                    AlertDialog.Builder dialog = new AlertDialog.Builder(PasswordActivity.this);

                    // 제목셋팅
                    dialog.setTitle("비밀번호 찾기");

                    // AlertDialog 셋팅
                    dialog
                            .setMessage("입력하신 이메일로 비밀번호를 보낼까요?")
                            .setCancelable(false) //
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 서버와 통신해서 비밀번호 찾기 실행

                                    // Firebase

                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    String emailAddress = et_email.getText().toString();

                                    auth.sendPasswordResetEmail(emailAddress)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Email sent.");
                                                        Toast.makeText(PasswordActivity.this, "비밀번호 재설정 이메일을 발송하였습니다.", Toast.LENGTH_LONG).show();
                                                    } else {

                                                    }
                                                }
                                            });
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

                else{

                }


            }
        });



        // 취소버튼 누르면 activity 종료
        Button button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(PasswordActivity.this);

                // AlertDialog 셋팅
                dialog
                        .setMessage("비밀번호 찾기를 취소할까요?")
                        .setCancelable(false) //
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
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
}
