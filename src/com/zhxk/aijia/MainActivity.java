package com.zhxk.aijia;

import java.util.ArrayList;
import java.util.List;

import com.zhxk.aijia.adapter.ViewPagerAdapter;
import com.zhxk.aijia.view.CircleIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {

	private ViewPagerAdapter adapter;
	private ViewPager pager;
	private List<View> views;
	private CircleIndicator indicator;
	private Button startExper;
	private SharedPreferences pre;
	private Editor editor;

	private static final int[] pics = new int[] { R.drawable.welcome1,
			R.drawable.welcome2, R.drawable.welcome3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pre = getSharedPreferences("login", Context.MODE_PRIVATE);
		// boolean isLogin = pre.getBoolean("isLogin", false);
		// if (isLogin) {
		// Intent intent = new Intent(MainActivity.this, MallWebActivity.class);
		// startActivity(intent);
		// finish();
		// }else {
		setContentView(R.layout.activity_main);
		initView();
		// }

	}

	private void initView() {
		pager = (ViewPager) findViewById(R.id.guide_pager);
		indicator = (CircleIndicator) findViewById(R.id.guide_indicator);
		startExper = (Button) findViewById(R.id.guide_start);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		views = new ArrayList<View>();
		for (int i = 0; i < pics.length; i++) {
			ImageView v = new ImageView(this);
			v.setLayoutParams(params);
			v.setImageResource(pics[i]);
			v.setScaleType(ScaleType.FIT_XY);
			views.add(v);
		}
		adapter = new ViewPagerAdapter(views);
		pager.setAdapter(adapter);
		indicator.setViewPager(pager);
		pager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == (views.size() - 1)) {
					startExper.setVisibility(View.VISIBLE);
					startExper.setClickable(true);
					startExper.setOnClickListener(MainActivity.this);
				} else {
					startExper.setVisibility(View.INVISIBLE);
					startExper.setClickable(false);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guide_start:
			Intent intent = new Intent(MainActivity.this, MallWebActivity.class);
			startActivity(intent);
			// editor = pre.edit();
			// editor.putBoolean("isLogin", true);
			// editor.commit();
			finish();
			break;

		default:
			break;
		}

	}
}
