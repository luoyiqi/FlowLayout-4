package com.flowlayouttest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaoPz_PC on 2016/11/1.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 存储所有的 View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    // 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //父控件传进来的宽度和高度以及对应的测量模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 如果当前ViewGroup 的宽和高为 wrap_parent 的情况
        int width = 0; // 自己测量的宽度
        int height = 0; // 自己测量的高度
        // 记录每一行的宽和高, 也就是布局中控件已经占用的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        // 获取子 view 的个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量子View 的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 拿到布局参数
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            // 子View 占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 子View 占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 换行问题 当前子View的宽度　+ 已经占用行的宽度 > 布局设定的宽度
            if (childWidth + lineWidth > sizeWidth) {
                // 对比得到最大的宽度
                width = Math.max(lineWidth, width);
                // 重置 width
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                // 重置 height
                lineHeight = childHeight;
            } else {
                // 不换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 处理最后一个 View, 例如就一行情况
            if (i == childCount -1) {
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
            }
        }
        //wrap_content 考虑是 包裹内容
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 优化
        mAllChildViews.clear();
        mLineHeight.clear();

        // 拿到当前 ViewGroup 的宽度
        int width = getWidth();

        int lineWidth = 0; // 行的宽度
        int lineHeight = 0; // 高的宽度

        // 记录当前行的 view
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) (child.getLayoutParams());
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 换行问题
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录 height
                mLineHeight.add(lineHeight);
                // 记录当前行的View
                mAllChildViews.add(lineViews);
                // 重置行的宽和高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;

                lineViews = new ArrayList<>();
            }

            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineWidth = lineWidth + childWidth + lp.leftMargin + lp.rightMargin;
            lineViews.add(child);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        // 设置子 View 的位置
        int left = 0;
        int top = 0;
        // mAllChildViews 存放的是行数
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            // 拿到对应行的 行对象
            lineViews = mAllChildViews.get(i);
            // 拿到对应行的 高对象
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                // 拿到 对应行对象里面的 每一个对象
                View child = lineViews.get(j);
                // 判断是否隐藏
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cleft = left + lp.leftMargin;
                int ctop = top + lp.topMargin;
                int cright = cleft + child.getMeasuredWidth() + lp.rightMargin;
                int cbottom = ctop + child.getMeasuredHeight() + lp.bottomMargin;

                child.layout(cleft, ctop, cright, cbottom);

                // 更新同一行中left
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            // 更新不同行的 top
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
