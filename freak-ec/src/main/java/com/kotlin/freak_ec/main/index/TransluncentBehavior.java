package com.kotlin.freak_ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.kotlin.freak_core.ui.recycler.RgbValue;
import com.kotlin.freak_ec.R;

public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private final int SHOW_SPEED = 3;
    private int mDistanceY = 0;
    private RgbValue rgb_value = new RgbValue(255, 124, 2);


    public TransluncentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, @NonNull View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //增加滑动距离
        mDistanceY += dy;
//toolbar的高度
        int targetHeight = child.getBottom();
//当滑动时，距离小于toolbar高度的时候，调整渐变色
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            float scale = mDistanceY * 1F / targetHeight;
            float alpha = scale * 255;
            child.setBackgroundColor(
                    Color.argb(
                            (int) alpha,
                            rgb_value.red(),
                            rgb_value.green(),
                            rgb_value.blue()
                    )
            );
        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(
                    Color.rgb(
                            rgb_value.red(),
                            rgb_value.green(),
                            rgb_value.blue()
                    )
            );
        }
    }
}
