package edu.skku.sw3.success;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText et_email = findViewById(R.id.email_edit);
        final TextView tv_email = findViewById(R.id.email_valid);

        final EditText pw1 = findViewById(R.id.password_input1);
        final EditText pw2 = findViewById(R.id.password_input2);

        final TextView pw_valid1 = findViewById(R.id.pw_valid1);
        final TextView pw_valid2 = findViewById(R.id.pw_valid2);

        final CheckBox checkBox = findViewById(R.id.privacy_checkbox);

        final Button privacy_button =findViewById(R.id.privacy_url_button);




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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youngskku.blogspot.com/2019/05/project-2-mobile-app-for-skku-notice.html"));
                startActivity(intent);

            }
        });




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

                // 입력받은 두 password 가 같지 않으면
                String password1 = pw1.getText().toString();
                String password2 = pw2.getText().toString();

                if(password1.equals("")){
                    pw_valid1.setText("비밀번호를 입력해주세요.");
                }
                else{
                    pw_valid1.setText("");
                }

                if(!password1.equals(password2)){
                    pw_valid2.setText("비밀번호가 동일하지 않습니다.");
                }
                else{
                    pw_valid2.setText("");
                }



                // 정상 진행
                if((tv_email.getText().toString().equals("")) && (pw_valid1.getText().toString().equals("")) && (pw_valid2.getText().toString().equals(""))
                        && checkBox.isChecked()
                ) {

                    // 서버와 통신해서 Sign up 해주기

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
                                    // 서버와 통신해서 회원가입 실행

                                    Toast.makeText(SignUpActivity.this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(SignUpActivity.this, "회원가입 취소", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
