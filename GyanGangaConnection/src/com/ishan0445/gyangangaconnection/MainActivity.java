package com.ishan0445.gyangangaconnection;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		if (ParseUser.getCurrentUser() != null) {
			// do stuff with the user
			Intent main = new Intent(MainActivity.this, FollowedFriendList.class);
			startActivity(main);
		} else {
			Intent main = new Intent(MainActivity.this, LoginAndSignup.class);
			startActivity(main);
		}
		

	}

}
