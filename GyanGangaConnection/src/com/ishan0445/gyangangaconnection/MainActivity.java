package com.ishan0445.gyangangaconnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseUser;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (ParseUser.getCurrentUser() != null) {
			// do stuff with the user
			Intent main = new Intent(MainActivity.this, FollowedFriendList.class);
			startActivity(main);
			finish();
		} else {
			Intent main = new Intent(MainActivity.this, LoginAndSignup.class);
			startActivity(main);
			finish();
		}
		
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.FollowMenu:
			Intent main = new Intent(MainActivity.this, FriendsList.class);
			startActivity(main);
			break;

		case R.id.LogoutMenu:
			ParseUser.logOut();
			Intent a1 = new Intent(MainActivity.this, Login.class);
			startActivity(a1);
			break;

		case R.id.ExitMenu:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		}
		return false;
	}

}
