package com.epam.popcornapp.ui.switcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.epam.popcornapp.ui.base.InflateFrameLayout;
import com.epam.ui.R;

public class PageSwitcher extends InflateFrameLayout {

    private Button prevButton;
    private Button nextButton;
    private SwitcherClickListener listener;

    private int count;
    private int numbers;
    private int current;

    @SuppressLint("RestrictedApi")
    final Drawable next =
            AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_next);

    @SuppressLint("RestrictedApi")
    final Drawable prev =
            AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_previous);

    public PageSwitcher(final Context context) {
        super(context);
    }

    public PageSwitcher(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public PageSwitcher(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        prevButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);

        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (listener != null) {
                    current--;
                    updateState();
                    listener.onPrevClicked(current);
                }
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (listener != null) {
                    current++;
                    updateState();
                    listener.onNextClicked(current);
                }
            }
        });

    }

    public void setParams(final int count, final int numbers, final int current) {
        this.count = count;
        this.numbers = numbers;
        this.current = current;
        updateState();
    }

    private void updateState() {
        if (count == numbers) {
            state(1);
        } else {
            state(0);
        }
    }

    private void state(final int param) {
        if (current > param && current < numbers) {
            changePrevButtonState(true);
            changeNextButtonState(true);
        } else if (current == param && current == numbers) {
            changePrevButtonState(false);
            changeNextButtonState(false);
        } else if (current == param) {
            changePrevButtonState(false);
            changeNextButtonState(true);
        } else if (current == numbers) {
            changeNextButtonState(false);
            changePrevButtonState(true);
        }
    }

    private void changePrevButtonState(final boolean isActive) {
        prevButton.setEnabled(isActive);

        if (isActive) {
            prevButton.setTextColor(getResources().getColor(R.color.color_accent));
            DrawableCompat.setTint(prev, getResources().getColor(R.color.color_accent));
            prevButton.setCompoundDrawablesWithIntrinsicBounds(prev,
                    null, null, null);
        } else {
            prevButton.setTextColor(getResources().getColor(R.color.color_text_disable));
            DrawableCompat.setTint(prev, getResources().getColor(R.color.color_text_disable));
            prevButton.setCompoundDrawablesWithIntrinsicBounds(prev,
                    null, null, null);
        }
    }

    private void changeNextButtonState(final boolean isActive) {
        nextButton.setEnabled(isActive);

        if (isActive) {
            nextButton.setTextColor(getResources().getColor(R.color.color_accent));
            DrawableCompat.setTint(next, getResources().getColor(R.color.color_accent));
            nextButton.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, next, null);
        } else {
            nextButton.setTextColor(getResources().getColor(R.color.color_text_disable));
            DrawableCompat.setTint(next, getResources().getColor(R.color.color_text_disable));
            nextButton.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, next, null);
        }
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_page_switcher;
    }

    public void setListener(final SwitcherClickListener listener) {
        this.listener = listener;
    }

    public interface SwitcherClickListener {
        void onPrevClicked(int current);
        void onNextClicked(int current);
    }
}
