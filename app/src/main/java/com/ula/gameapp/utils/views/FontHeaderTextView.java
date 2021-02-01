package com.ula.gameapp.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class FontHeaderTextView extends AppCompatTextView {

  private static final String FONT_DIR = "Font/";
  private static final String FONT_HEADER = "thechickennoodle.ttf";


  public FontHeaderTextView(Context context) {
    super(context);
    init(context);
  }

  public FontHeaderTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public FontHeaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }
  private void init(Context context) {
    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), FONT_DIR + FONT_HEADER);
    setTypeface(tf);
  }
}