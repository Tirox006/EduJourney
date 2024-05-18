package com.example.edujourney.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edujourney.R;
import com.example.edujourney.db.DBHelper;

public class LoginActivity2 extends AppCompatActivity {

    EditText username , password;
    Button btnlogin;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        username = findViewById(R.id.user_name_id2);
        password=findViewById(R.id.password_id2);
        btnlogin=findViewById(R.id.btn_signin_id2);
        DB = new DBHelper(this);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass= password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity2.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity2.this,"Sign in successfull",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),CourseList.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity2.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}