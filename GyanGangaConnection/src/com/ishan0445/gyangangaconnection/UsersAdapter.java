package com.ishan0445.gyangangaconnection;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class UsersAdapter extends ArrayAdapter<ParseObject> {

	protected Context mContext;
	protected LayoutInflater mInflater;
	protected ParseObject[] mUsers;
	protected ArrayList<ParseObject> mUserRelations;

	private final String RELATION = "UserRelation";

	public UsersAdapter(Context context, ParseObject[] users,
			ArrayList<ParseObject> userRelations) {
		super(context, R.layout.user_list_item, users);

		mContext = context;
		mUsers = users;
		mUserRelations = userRelations;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_list_item, null);

			holder = new ViewHolder();
			holder.emailLabel = (TextView) convertView
					.findViewById(R.id.emailLabel);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.userImage);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ParseObject user = mUsers[position];

		holder.thumbnail.setImageResource(R.drawable.ic_social_person);
		holder.emailLabel.setText(user.getString("username"));

		holder.checkbox.setChecked(false);
		for (int i = 0; i < mUserRelations.size(); i++) {
			if (mUserRelations.get(i).getObjectId().equals(user.getObjectId())) {
				holder.checkbox.setChecked(true);
			}
		}

		holder.checkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox checkbox = (CheckBox) v;
				if (checkbox.isChecked()) {
					updateRelationship(user, true);
				} else {
					updateRelationship(user, false);
				}
			}
		});

		return convertView;
	}

	private static class ViewHolder {
		TextView emailLabel;
		ImageView thumbnail;
		CheckBox checkbox;
	}

	protected void updateRelationship(ParseObject user, boolean shouldAdd) {

		ParseUser currentUser = ParseUser.getCurrentUser();

		ParseRelation<ParseObject> userRelation = currentUser
				.getRelation(RELATION);
		if (shouldAdd) {
			userRelation.add(user);
			mUserRelations.add(user);
		} else {
			userRelation.remove(user);
			for (int i = 0; i < mUserRelations.size(); i++) {
				if (mUserRelations.get(i).getObjectId()
						.equals(user.getObjectId())) {
					mUserRelations.remove(i);
				}
			}
		}
		currentUser.saveInBackground();

	}

}
