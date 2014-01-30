package com.ishan0445.gyangangaconnection;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

public class FollowedUsersAdapter extends ArrayAdapter<ParseObject> {
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected ParseObject[] mUsers;
	protected ArrayList<ParseObject> mUserRelations;

	public FollowedUsersAdapter(Context context, ParseObject[] users,
			ArrayList<ParseObject> userRelations) {
		super(context, R.layout.user_list_followed_item, users);

		mContext = context;
		mUsers = users;
		mUserRelations = userRelations;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_list_followed_item, null);

			holder = new ViewHolder();
			holder.emailLabel = (TextView) convertView
					.findViewById(R.id.FollowedEmail);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.follUserDp);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ParseObject user = mUsers[position];

		holder.thumbnail.setImageResource(R.drawable.ic_social_person);
		holder.emailLabel.setText(user.getString("Name"));

		return convertView;
	}

	private static class ViewHolder {
		TextView emailLabel;
		ImageView thumbnail;
	}
}
