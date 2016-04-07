package com.kk.kflykeyboard;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RelativeLayout mainrl;
    private EditText medit;
    int mHeight, mWidth;
    private ArrayList<TextView> textViewArrayList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mainrl = (RelativeLayout) findViewById(R.id.mainrl);
        medit = (EditText) findViewById(R.id.mEditText);
        medit.addTextChangedListener(watcher);
        getHW();
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if (count > 0) {
                TextPaint mtextp = medit.getPaint();
                int contentWidth = medit.getWidth() - medit.getPaddingLeft() - medit.getPaddingRight();
                int textWidth = (int) mtextp.measureText(s.toString().substring(0, start));
                if (textWidth > contentWidth - 2) {
                    int coun = contentWidth * (textWidth / contentWidth);
                    textWidth -= coun;
                }
                int el = medit.getLeft();
                int eh = medit.getTop();
                int ep = medit.getHeight();
                String mt = "";
                if (s.length() > 0) {
                    mt = s.toString().substring(start, start + 1);
                }
                TextView mtv = new TextView(MainActivity.this);
                mtv.setTextColor(Color.WHITE);
                mtv.setText(mt);
                LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                rlp.leftMargin = el + textWidth;
                mainrl.addView(mtv, rlp);
                ObjectAnimator animator = ObjectAnimator.ofFloat(mtv, "TextSize", 50);
                animator.setDuration(400);
                animator.start();
                Animation translateAnimation = new TranslateAnimation(0, 0, 0, eh + ep + mtv.getHeight() + 50 - mHeight);
                translateAnimation.setDuration(400);//设置动画持续时间为3秒
                translateAnimation.setInterpolator(MainActivity.this, android.R.anim.decelerate_interpolator);//设置动画插入器
                //  translateAnimation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
                translateAnimation.setAnimationListener(new trl());
                mtv.startAnimation(translateAnimation);
                textViewArrayList.add(mtv);

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            if (after == 0) {
                int el = medit.getLeft();
                int eh = medit.getTop();
                int ep = medit.getLineHeight();
                int contentWidth = medit.getWidth() - medit.getPaddingLeft() - medit.getPaddingRight();
                TextPaint mtextp = medit.getPaint();
                int textWidth = (int) mtextp.measureText(s.toString().substring(0, start));
                if (textWidth > contentWidth - 2) {
                    int coun = contentWidth * (textWidth / contentWidth);
                    int ecoun = ep * (textWidth / contentWidth);
                    textWidth -= coun;
                    eh += ecoun;
                }
                String mt = "";
                if (s.length() > 0) {
                    mt = s.toString().substring(start, start + 1);
                }
                TextView mtv = new TextView(MainActivity.this);
                mtv.setTextColor(Color.WHITE);
                mtv.setText(mt);
                LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rlp.topMargin = eh;
                rlp.leftMargin = el + textWidth;
                mainrl.addView(mtv, rlp);
                ObjectAnimator animator = ObjectAnimator.ofFloat(mtv, "TextSize", 50);
                animator.setDuration(400);
                animator.start();
                Animation translateAnimation = new TranslateAnimation(0, 0, 0, mHeight);
                translateAnimation.setDuration(400);//设置动画持续时间为3秒
                translateAnimation.setInterpolator(MainActivity.this, android.R.anim.accelerate_interpolator);//设置动画插入器
                translateAnimation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
                translateAnimation.setAnimationListener(new trl());
                mtv.startAnimation(translateAnimation);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

    private class trl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (textViewArrayList.size() > 0) {
                TextView textView = textViewArrayList.get(0);
                textViewArrayList.remove(0);
                mainrl.removeView(textView);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private void getHW() {
        WindowManager manager = MainActivity.this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        mWidth = outMetrics.widthPixels;

        mHeight = outMetrics.heightPixels;
    }

}
