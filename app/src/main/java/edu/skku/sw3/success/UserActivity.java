package edu.skku.sw3.success;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    TextView title;
    EditText emailEdit, pwdEdit, pwdConfirmEdit;
    Button cancelBtn, confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        title = findViewById(R.id.password_title);
        emailEdit = findViewById(R.id.email_edit);
        pwdEdit = findViewById(R.id.password_input1);
        pwdConfirmEdit = findViewById(R.id.password_input2);
        cancelBtn = findViewById(R.id.cancel);
        confirmBtn = findViewById(R.id.sign_up_button);

        title.setText(getResources().getString(R.string.user_title));

        // 개인정보 반영

        // 개인 정보 수정
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
