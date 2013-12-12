package com.example.crime_maps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.crime_maps.R;
import com.example.database_helper.*;
import com.example.sqlite.model.user_entity;

import android.preference.PreferenceManager;

public class LoginActivity extends Activity 
{
	private Button btnSignIn,btnSignUp;
	private EditText editTextUsername, editTextPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_login);
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIn);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUp);
	     
	     editTextUsername=(EditText)findViewById(R.id.editTextUserNameToLogin);
	     editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
	     
	     SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	     SharedPreferences.Editor editor = settings.edit();
	     editor.remove("username");
	     editor.commit();
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUp=new Intent(getApplicationContext(),SignUpActivity.class);
			startActivity(intentSignUp);
			}
		});
	
	// Methos to handleClick Event of Sign In Button
				
	 btnSignIn.setOnClickListener(new View.OnClickListener() {
		 public void onClick(View v) {
			 
			 DataBaseHelper db = new DataBaseHelper(getApplicationContext());
			 
					// get The User name and Password
					String username=editTextUsername.getText().toString();
					String password=editTextPassword.getText().toString();
					
					user_entity user;
					
					user=db.AuthenticateUser(username);
					
					if(username.trim().equals("")||password.trim().equals(""))
					{
				        Toast.makeText(LoginActivity.this, "One or more fields are empty.", Toast.LENGTH_LONG).show();	
						
					}
					
					else if (user.getUsername()==null||user.getUsername().trim().equals(""))
					{
			        Toast.makeText(LoginActivity.this, "User does not exist. Please try again.", Toast.LENGTH_LONG).show();	
					}
					
					else 
					{
						if(password.equals(user.getPassword()))
						{
						Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
						Intent i = new Intent(getApplicationContext(),MainActivity.class);
						
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("username", username);
						
						startActivity(i);
						}
						else
						{
						Toast.makeText(LoginActivity.this, "Username or Password does not match. Please try again.", Toast.LENGTH_LONG).show();
						}
					}
					db.close();
		 }
	 });
	}

	
}
