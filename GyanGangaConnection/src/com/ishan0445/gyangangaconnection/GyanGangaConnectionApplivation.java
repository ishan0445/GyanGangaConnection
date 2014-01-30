package com.ishan0445.gyangangaconnection;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.PushService;

import android.app.Application;

public class GyanGangaConnectionApplivation extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "8pqEQMpNHAZf514L25WGIsm5Cu0sDSc00ExKTdOR",
				"uLsCZYAOUQfoQLpgPzPwjRxG3XS5PEwqmGz2WwcV");
		
		PushService.setDefaultPushCallback(this, MainActivity.class);

		ParseACL defaultACL = new ParseACL();

		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
