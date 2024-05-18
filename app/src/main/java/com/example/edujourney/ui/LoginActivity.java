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

public class LoginActivity extends AppCompatActivity {

    EditText username,password,repassword;
    Button signup , signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username=findViewById(R.id.user_name_id);
        password=findViewById(R.id.password_id);
        repassword=findViewById(R.id.repassword_id);
        signin=findViewById(R.id.btn_signin_id);
        signup=findViewById(R.id.btn_signup_id);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user =username.getText().toString();
                String pass=password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(LoginActivity.this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            boolean insert = DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(LoginActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),CourseList.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"User already exists! please sign in",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this,"Password not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                startActivity(intent);
            }
        });

    }
}