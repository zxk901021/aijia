package com.zhxk.aijia;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MallWebActivity extends Activity {
	
	private WebView mallWeb;
	public static final int TIME_OUT = 5000;
	public static final int TIME_OUT_FLAG = 1;
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall_web);
		initView();
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIME_OUT_FLAG:
				mallWeb.stopLoading();
				Toast.makeText(MallWebActivity.this, "º”‘ÿ ß∞‹", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	
	private void initView(){
		mallWeb = (WebView) findViewById(R.id.mall_web);
		mallWeb.setWebViewClient(new WebViewClient(){
			
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				timer = new Timer();
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						if (mallWeb.getProgress() < 100) {
							Message msg = new Message();
							msg.what = TIME_OUT_FLAG;
							handler.sendMessage(msg);
							timer.cancel();
							timer.purge();
						}
					}
				};
				timer.schedule(task, TIME_OUT, 100);
			}
			
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		mallWeb.loadUrl("http://ajhm315.com/");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mall_web, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
