package com.example.mypic;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lidroid.xutils.BitmapUtils;

public class MainActivity extends Activity {

	private ViewPager viewPager;
	private LinearLayout ll_dot;
	// private String[] imageUrls = new String[] {
	// "http://pic8.nipic.com/20100701/5290458_114840036316_2.jpg",
	// "http://pic2.nipic.com/20090424/1468853_230119053_2.jpg",
	// "http://img3.3lian.com/2013/s1/20/d/57.jpg",
	// "http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg",
	// "http://a0.att.hudong.com/15/08/300218769736132194086202411_950.jpg" };

	private String[] imageUrls = new String[] {
			"http://192.168.230.1:8080/ab.jpg",
			"http://192.168.230.1:8080/ae.jpg",
			"http://192.168.230.1:8080/af.jpg",
			"http://192.168.230.1:8080/ah.jpg" };

	private ArrayList<ImageView> viewList;
	private ArrayList<ImageView> dotList;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 先去获取到当前条目索引
			int currentItem = viewPager.getCurrentItem();
			// 条目索引+1
			currentItem++;
			// 重新设置给viewPager
			viewPager.setCurrentItem(currentItem);
			// 再调用发延时消息的方法
			sendDelayMessage();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
		// 初始化图片
		initViewList();
		// 初始化小圆点
		initDots();
		// 设置数据适配器
		viewPager.setAdapter(new MyPagerAdapter(this, viewList, handler));
		// 设置初始的展示条目
		viewPager.setCurrentItem(viewList.size() * 100000);
		// 设置延时切换
		sendDelayMessage();

		// 监听viewPager的一个滑动事件
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 遍历小点的集合
				for (int i = 0; i < dotList.size(); i++) {
					// 如果当前的索引值和i相等
					if (position % dotList.size() == i) {
						// 设置小点为亮色
						dotList.get(i).setImageResource(R.drawable.dot_focuse);
					} else {
						// 否则暗色
						dotList.get(i).setImageResource(R.drawable.dot_normal);
					}
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

	/**
	 * 发送延时消息
	 */
	private void sendDelayMessage() {
		handler.sendEmptyMessageDelayed(0, 2000);
	}

	private void initDots() {
		// 创建一个装小点控件的集合
		dotList = new ArrayList<ImageView>();
		dotList.clear();
		for (int i = 0; i < imageUrls.length; i++) {
			ImageView imageView = new ImageView(this);
			if (i == 0) {
				// 如果是第一张，默认给一个亮的小点
				imageView.setImageResource(R.drawable.dot_focuse);
			} else {
				// 如果不是滴一个，默认给一个暗的小点
				imageView.setImageResource(R.drawable.dot_normal);
			}
			// 设置小点的默认宽高为20dp
			LayoutParams params = new LayoutParams(20, 20);
			// 设置小点的间距
			params.setMargins(5, 0, 5, 0);
			ll_dot.addView(imageView, params);
			// 往小点集合中添加view
			dotList.add(imageView);
		}
	}

	private void initViewList() {
		// 先使用xutils的工具类
		BitmapUtils bitmapUtils = new BitmapUtils(this);
		viewList = new ArrayList<ImageView>();
		for (int i = 0; i < imageUrls.length; i++) {
			// 创建imageView 并通过工具类将图片设置到控件上
			ImageView imageView = new ImageView(this);
			bitmapUtils.display(imageView, imageUrls[i]);
			viewList.add(imageView);
		}
	}
}
