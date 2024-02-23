package com.example.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String aaa;
    private String bbb;
    private ViewPager viewPager;
    private BannerAdapter mBannerAdapter;
    private int[] img = {R.drawable.one, R.drawable.two, R.drawable.there, R.drawable.four};
    private TextView[] tabs = new TextView[4];
    private int currentPage = 0;
    private final Handler handler = new Handler(Looper.myLooper());

    private String TAG = "";
    private final long Millis = 3000;//间隔时间
    private final Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            currentPage = (currentPage + 1) % mBannerAdapter.getCount();
            Log.e("TAG", "run: "+(currentPage + 1) % mBannerAdapter.getCount() );
            viewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(autoScrollRunnable, Millis);  //autoScrollRunnable重新调度到消息队列中，延迟Millis毫秒后再次执行
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        startAutoScroll();
    }

    private void initData() {

        tabs[0] = findViewById(R.id.id_icon1);
        tabs[1] = findViewById(R.id.id_icon2);
        tabs[2] = findViewById(R.id.id_icon3);
        tabs[3] = findViewById(R.id.id_icon4);
        viewPager = findViewById(R.id.id_viewPage);
        mBannerAdapter = new BannerAdapter();
        viewPager.setAdapter(mBannerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                for (TextView tab : tabs) {
                    tab.setBackground(getDrawable(R.drawable.point_style_normal));
                }
                tabs[currentPage % img.length].setBackground(getDrawable(R.drawable.point_style_check));
                Log.e("TAG", "position: "+position );
                Log.e("TAG", "currentPage % img.length: "+currentPage % img.length );


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void startAutoScroll() {

        handler.postDelayed(autoScrollRunnable, Millis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(autoScrollRunnable);
    }

    private class BannerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            //把图片放在  viewpage 里面
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(img[position % img.length]);
            container.addView(imageView);
            return imageView;


        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }
}
