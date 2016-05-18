package com.zhxk.aijia;

import java.util.Timer;
import java.util.TimerTask;

import com.zhxk.aijia.util.NetStatusUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MallWebActivity extends Activity implements OnClickListener{
	
	private WebView mallWeb;
	public static final int TIME_OUT = 5000;
	public static final int TIME_OUT_FLAG = 1;
	private Timer timer;
	private Button netSetting;
	private ProgressBar bar;
	private WebChromeClient client;

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
		netSetting = (Button) findViewById(R.id.net_setting);
		bar = (ProgressBar) findViewById(R.id.progress);
		client = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				super.onProgressChanged(view, newProgress);
				if (view.getUrl() != null)
					if (newProgress < 100) {
						bar.setVisibility(View.VISIBLE);
						bar.setProgress(newProgress);
					} else {
						bar.setVisibility(View.GONE);
					}
			}
		};
		mallWeb.setWebChromeClient(client);
		WebSettings settings = mallWeb.getSettings();
		settings.setJavaScriptEnabled(true);
		mallWeb.setWebViewClient(new WebViewClient(){
			
			
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//				timer = new Timer();
//				TimerTask task = new TimerTask() {
//					
//					@Override
//					public void run() {
//						if (mallWeb.getProgress() < 100) {
//							Message msg = new Message();
//							msg.what = TIME_OUT_FLAG;
//							handler.sendMessage(msg);
//							timer.cancel();
//							timer.purge();
//						}
//					}
//				};
//				timer.schedule(task, TIME_OUT, 100);
//			}
			
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		checkNet();
	}

	private void checkNet(){
		if (NetStatusUtil.isNetworkConnected(this)) {
			mallWeb.setVisibility(View.VISIBLE);
			mallWeb.loadUrl("http://ajhm315.com/wap");
			netSetting.setVisibility(View.GONE);
		}else {
			Toast.makeText(MallWebActivity.this, "«ÎºÏ≤Èƒ˙µƒÕ¯¬Á", Toast.LENGTH_SHORT).show();
			mallWeb.setVisibility(View.GONE);
			netSetting.setVisibility(View.VISIBLE);
			netSetting.setOnClickListener(this);
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		checkNet();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mall_web, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.ACTION_DOWN == event.getAction()
				&& keyCode == KeyEvent.KEYCODE_BACK && mallWeb.canGoBack()) {
			mallWeb.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.net_setting:
			Intent intent = new Intent();
			if (android.os.Build.VERSION.SDK_INT > 10) {
				intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
			}else {
				intent = new Intent();
				intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
			}
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
