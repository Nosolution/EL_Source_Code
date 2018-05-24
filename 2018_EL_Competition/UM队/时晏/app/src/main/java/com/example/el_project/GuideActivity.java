package com.example.el_project;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
	private ViewPager viewPager;
	private ViewPagerAdapter viewPagerAdapter;
	private LinearLayout dotLinearLayout;  // 用来放底部小圆点的LinearLayout
	private Button btnStart;  // 进入APP
	private View dotWhite;  // 跟随移动的小圆点
	private ArrayList<View> viewList;
	private int mNum = 0;
	private int distance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		initView();
		initDot();
		initEvent();
	}

	private void initView() {
		btnStart = findViewById(R.id.btn_start);
		viewPager = findViewById(R.id.guide_view_pager);
		dotLinearLayout = findViewById(R.id.dot_linear_layout);
		dotWhite = findViewById(R.id.dot_white);

		viewList = new ArrayList<>();
		LayoutInflater layoutInflater = getLayoutInflater();
		viewList.add(layoutInflater.inflate(R.layout.view_pager_one, null, false));
		viewList.add(layoutInflater.inflate(R.layout.view_pager_two, null, false));
		viewList.add(layoutInflater.inflate(R.layout.view_pager_three, null, false));
	}

	private void initDot() {
//		ImageView imageView;
		View view;
		for(int i=0; i<viewList.size(); i++) {
			view = new View(GuideActivity.this);
			view.setBackgroundResource(R.drawable.dot_background);
			view.setEnabled(false);
			// 设置宽高
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
			// 设置间隔
			if (i != 0) {
				layoutParams.leftMargin = 20;
			}
			// 添加到LinearLayout
			dotLinearLayout.addView(view, layoutParams);
		}
	}

	private void initEvent() {
		// 第一次显示小白点
		dotLinearLayout.getChildAt(0).setEnabled(true);

		viewPagerAdapter = new ViewPagerAdapter(viewList);
		viewPager.setAdapter(viewPagerAdapter);

		dotWhite.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				distance = dotLinearLayout.getChildAt(1).getLeft() - dotLinearLayout.getChildAt(0).getLeft();

			}
		});
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				float leftMargin = distance * positionOffset + position * distance;
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)dotWhite.getLayoutParams();  // 获取会动的小白点的布局参数
				layoutParams.leftMargin = (int)leftMargin;  // 设置该点的左边距
				dotWhite.setLayoutParams(layoutParams);
			}

			@Override
			public void onPageSelected(int position) {
				dotLinearLayout.getChildAt(mNum).setEnabled(false);
				dotLinearLayout.getChildAt(position).setEnabled(true);
				mNum = position;

				if (position == viewList.size()-1) {
					btnStart.setVisibility(View.VISIBLE);
					btnStart.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(GuideActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}
					});
				} else {
					btnStart.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}
}
