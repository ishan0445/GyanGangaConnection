package com.ishan0445.gyangangaconnection;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class FriendsList extends ListActivity {
	private TextView tex;
	protected ParseObject[] mUsers;
	public static final String TAG = FriendsList.class.getSimpleName();
	protected ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_users);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);
		getAllUsers();

		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");
		tex = (TextView) findViewById(R.id.FriendlistTop);
		tex.setTypeface(type);

	}

	private void getAllUsers() {
		mProgressBar.setVisibility(View.VISIBLE);

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.orderByAscending("username");
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				

				if (e == null) {
					objects = removeCurrentUser(objects);
					mUsers = objects.toArray(new ParseObject[0]);

					ParseRelation<ParseUser> userRelations = ParseUser
							.getCurrentUser().getRelation("UserRelation");
					userRelations.getQuery().findInBackground(
							new FindCallback<ParseUser>() {
								public void done(List<ParseUser> results,
										ParseException e) {
									if (e == null) {
										UsersAdapter adapter = new UsersAdapter(
												FriendsList.this, mUsers,
												new ArrayList<ParseObject>(
														results));
										mProgressBar.setVisibility(View.INVISIBLE);
										setListAdapter(adapter);
									} else {
										Log.e(TAG, "Exception caught!", e);
									}
								}
							});
				} else if (!isOnline()) {
					Toast.makeText(FriendsList.this,
							"Sorry, No network access!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(FriendsList.this,
							"Sorry, there was an error getting users!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private List<ParseUser> removeCurrentUser(List<ParseUser> objects) {
		ParseObject userToRemove = null;

		for (ParseObject user : objects) {
			if (user.getObjectId().equals(
					ParseUser.getCurrentUser().getObjectId())) {
				userToRemove = user;
			}
		}

		if (userToRemove != null) {
			objects.remove(userToRemove);
		}

		return objects;
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
			Intent main = new Intent(FriendsList.this, FriendsList.class);
			startActivity(main);
			break;

		case R.id.LogoutMenu:
			ParseUser.logOut();
			Intent a1 = new Intent(FriendsList.this, Login.class);
			finish();
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
