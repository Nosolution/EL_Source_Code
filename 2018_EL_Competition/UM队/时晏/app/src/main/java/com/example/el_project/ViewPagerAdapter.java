package com.example.el_project;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
	private ArrayList<View> viewList;

	public ViewPagerAdapter() {

	}

	public ViewPagerAdapter(ArrayList<View> viewList) {
		super();
		this.viewList = viewList;
	}

	/**
	 * 获取当前要显示的对象（界面数）
	 */
	public int getCount() {
		if (viewList != null) {
			return viewList.size();
		}
		return 0;
	}

	/**
	 * 判断是否需要由对象生成界面
	 */
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	/**
	 * 当前要显示的对象（初始化position位置的界面）
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
//		return super.instantiateItem(container, position);
		container.addView(viewList.get(position));
		return viewList.get(position);
	}

//	public Object instantiateItem(View view, int position) {
//		((ViewPager) view).addView(views.get(position), 0);
//		return views.get(position);
//	}

	/**
	 * 销毁position位置的界面
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
		container.removeView(viewList.get(position));
	}

//	public void destoryItem(View view, int position, Object object) {
//		(ViewPager) view.removeView(views.get(position));
//	}


}
