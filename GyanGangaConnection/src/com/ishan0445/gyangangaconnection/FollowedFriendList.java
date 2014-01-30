package com.ishan0445.gyangangaconnection;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class FollowedFriendList extends ListActivity {
	private TextView tex;
	private Typeface type;
	protected ParseObject[] mUsers;
	public static final String TAG = FriendsList.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tex = (TextView) findViewById(R.id.FriendsTop);

		type = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");
		tex.setTypeface(type);

		getFollowedUsers();

	}

	private void getFollowedUsers() {
		// mProgressBar.setVisibility(View.VISIBLE);

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		query.orderByAscending("username");
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				// mProgressBar.setVisibility(View.INVISIBLE);

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
										FollowedUsersAdapter adapter = new FollowedUsersAdapter(
												FollowedFriendList.this,
												mUsers,
												new ArrayList<ParseObject>(
														results));
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

}
