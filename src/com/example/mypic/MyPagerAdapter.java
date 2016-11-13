package com.example.mypic;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdapter extends PagerAdapter {
	private List<ImageView> viewList;
	private Context context;
	private Handler handler;

	public MyPagerAdapter(Context context, List<ImageView> viewList,
			Handler handler) {
		this.context = context;
		this.viewList = viewList;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// ͨ���ؼ����ϻ�ȡimageView ��ӵ������У�����Ϊkey ����
		ImageView imageView = viewList.get(position % viewList.size());
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				// �����µ�ʱ��ֹͣ�ֲ�
				case MotionEvent.ACTION_DOWN:
					// �Ƴ����еĻص�����Ϣ
					handler.removeCallbacksAndMessages(null);
					break;
				// ̧��
				case MotionEvent.ACTION_UP:
					handler.sendEmptyMessageDelayed(0, 2000);
					break;
				// ȡ����ʱ�򣬼�����ʼ¼��
				case MotionEvent.ACTION_CANCEL:
					handler.sendEmptyMessageDelayed(0, 2000);
					break;
				default:
					break;
				}
				//�����¼��������ѣ�true ���Ѹô����¼�
				return true;
			}
		});
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// super.destroyItem(container, position, object);
		container.removeView((View) object);
	}
}
