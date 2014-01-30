package com.ishan0445.gyangangaconnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity {
	TextView name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		name = (TextView) findViewById(R.id.textView1);

		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");
		name.setTypeface(type);

		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					Intent main = new Intent(Splash.this, MainActivity.class);
					startActivity(main);
				}
			}
		};
		timer.start();

	}

}
