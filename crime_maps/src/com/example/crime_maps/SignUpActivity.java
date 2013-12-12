package com.example.crime_maps;

import com.example.database_helper.DataBaseHelper;
import com.example.sqlite.model.user_entity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.preference.PreferenceManager;

public class SignUpActivity extends Activity
{
	EditText editTextUserName,
	editTextPassword,editTextConfirmPassword
	, editTextBirthday, editTextSex,editTextCity;
	Button btnCreateAccount;
	
	DataBaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		// Get Refferences of Views
		editTextUserName=(EditText)findViewById(R.id.editTextUserName);
		editTextPassword=(EditText)findViewById(R.id.editTextPassword);
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
		editTextBirthday=(EditText)findViewById(R.id.editTextBirthday);
		editTextSex=(EditText)findViewById(R.id.editTextSex);
		editTextCity=(EditText)findViewById(R.id.editTextCity);
				
		btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			
			String userName=editTextUserName.getText().toString();
			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();
			String birthday=editTextBirthday.getText().toString();
			String sex=editTextSex.getText().toString();
			String city=editTextCity.getText().toString();
			
			
			// check if any of the fields are null
			if(userName.trim().equals("")||password.trim().equals("")||confirmPassword.trim().equals("")||city.trim().equals("")||birthday.trim().equals("")||sex.trim().equals(""))
			{
					Toast.makeText(getApplicationContext(), "Please fill up fields", Toast.LENGTH_LONG).show();
					return;
			}
			// check if both password matches
			else if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
				user_entity ue;
				ue= new user_entity(userName,password,sex,city,birthday,null);
				db = new DataBaseHelper(getApplicationContext());
				
				db.createUser(ue);
				db.close();
				
				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("username", ue.getUsername());
				
				startActivity(i);
				
				//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				//SharedPreferences.Editor editor = settings.edit();
				//editor.remove("username");
				
			    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
			}
			
			
		}
	});
}

}
