package com.ula.gameapp.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class FontBodyTextView extends AppCompatTextView {

  private static final String FONT_DIR = "Font/";
  private static final String FONT_BODY = "djbfirstgradeteacher.ttf";

  public FontBodyTextView(Context context) {
    super(context);
    init(context);
  }

  public FontBodyTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public FontBodyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), FONT_DIR + FONT_BODY);
    setTypeface(tf);
  }
}
