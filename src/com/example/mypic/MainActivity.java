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
			// ��ȥ��ȡ����ǰ��Ŀ����
			int currentItem = viewPager.getCurrentItem();
			// ��Ŀ����+1
			currentItem++;
			// �������ø�viewPager
			viewPager.setCurrentItem(currentItem);
			// �ٵ��÷���ʱ��Ϣ�ķ���
			sendDelayMessage();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
		// ��ʼ��ͼƬ
		initViewList();
		// ��ʼ��СԲ��
		initDots();
		// ��������������
		viewPager.setAdapter(new MyPagerAdapter(this, viewList, handler));
		// ���ó�ʼ��չʾ��Ŀ
		viewPager.setCurrentItem(viewList.size() * 100000);
		// ������ʱ�л�
		sendDelayMessage();

		// ����viewPager��һ�������¼�
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// ����С��ļ���
				for (int i = 0; i < dotList.size(); i++) {
					// �����ǰ������ֵ��i���
					if (position % dotList.size() == i) {
						// ����С��Ϊ��ɫ
						dotList.get(i).setImageResource(R.drawable.dot_focuse);
					} else {
						// ����ɫ
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
	 * ������ʱ��Ϣ
	 */
	private void sendDelayMessage() {
		handler.sendEmptyMessageDelayed(0, 2000);
	}

	private void initDots() {
		// ����һ��װС��ؼ��ļ���
		dotList = new ArrayList<ImageView>();
		dotList.clear();
		for (int i = 0; i < imageUrls.length; i++) {
			ImageView imageView = new ImageView(this);
			if (i == 0) {
				// ����ǵ�һ�ţ�Ĭ�ϸ�һ������С��
				imageView.setImageResource(R.drawable.dot_focuse);
			} else {
				// ������ǵ�һ����Ĭ�ϸ�һ������С��
				imageView.setImageResource(R.drawable.dot_normal);
			}
			// ����С���Ĭ�Ͽ��Ϊ20dp
			LayoutParams params = new LayoutParams(20, 20);
			// ����С��ļ��
			params.setMargins(5, 0, 5, 0);
			ll_dot.addView(imageView, params);
			// ��С�㼯�������view
			dotList.add(imageView);
		}
	}

	private void initViewList() {
		// ��ʹ��xutils�Ĺ�����
		BitmapUtils bitmapUtils = new BitmapUtils(this);
		viewList = new ArrayList<ImageView>();
		for (int i = 0; i < imageUrls.length; i++) {
			// ����imageView ��ͨ�������ཫͼƬ���õ��ؼ���
			ImageView imageView = new ImageView(this);
			bitmapUtils.display(imageView, imageUrls[i]);
			viewList.add(imageView);
		}
	}
}
