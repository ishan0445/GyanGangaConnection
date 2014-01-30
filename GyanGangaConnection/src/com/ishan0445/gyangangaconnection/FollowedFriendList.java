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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class FollowedFriendList extends ListActivity implements
		OnItemClickListener {
	private TextView tex;
	private Typeface type;
	protected ParseObject[] mUsers;
	public static final String TAG = FriendsList.class.getSimpleName();
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tex = (TextView) findViewById(R.id.FriendsTop);

		type = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");
		tex.setTypeface(type);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

		ListView lv = getListView();
		lv.setOnItemClickListener(this);

		getFollowedUsers();

	}

	private void getFollowedUsers() {
		mProgressBar.setVisibility(View.VISIBLE);

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.orderByAscending("username");
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {

				if (e == null) {
					objects = removeCurrentUser(objects);
					

					ParseRelation<ParseUser> userRelations = ParseUser
							.getCurrentUser().getRelation("UserRelation");
					userRelations.getQuery().findInBackground(
							new FindCallback<ParseUser>() {
								public void done(List<ParseUser> results,
										ParseException e) {
									if (e == null) {
										mUsers = results.toArray(new ParseObject[0]);
										FollowedUsersAdapter adapter = new FollowedUsersAdapter(
												FollowedFriendList.this,
												mUsers,
												new ArrayList<ParseObject>(
														results));
										mProgressBar
												.setVisibility(View.INVISIBLE);
										setListAdapter(adapter);

									} else {
										Log.e(TAG, "Exception caught!", e);
									}
								}
							});
				} else if (!isOnline()) {
					Toast.makeText(FollowedFriendList.this,
							"Sorry, No network access!", Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(FollowedFriendList.this,
							"Sorry, there was an error getting users!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
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

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(FollowedFriendList.this, "hello", Toast.LENGTH_SHORT)
				.show();
		

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
			Intent main = new Intent(FollowedFriendList.this, FriendsList.class);
			startActivity(main);
			break;

		case R.id.LogoutMenu:
			ParseUser.logOut();
			Intent a1 = new Intent(FollowedFriendList.this, Login.class);
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
