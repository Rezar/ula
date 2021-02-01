package com.ula.gameapp.utils.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.ula.gameapp.R;

public class MySnackbar extends BaseTransientBottomBar<MySnackbar> {

    /**
     * Constructor for the transient bottom bar.
     *
     * @param parent   The parent for this transient bottom bar.
     * @param content  The content view for this transient bottom bar.
     * @param callback The content view callback for this transient bottom bar.
     */
    private MySnackbar(ViewGroup parent, View content, ContentViewCallback callback) {
        super(parent, content, callback);
    }

    public static MySnackbar make(@NonNull ViewGroup parent, @Duration int duration) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View content = inflater.inflate(R.layout.my_snackbar, parent, false);
        final ContentViewCallback viewCallback = new ContentViewCallback(content);
        final MySnackbar customSnackbar = new MySnackbar(parent, content, viewCallback);
        customSnackbar.setDuration(duration);
        return customSnackbar;
    }

    public MySnackbar setText(CharSequence text) {
        TextView textView = (TextView) getView().findViewById(R.id.snackbar_text);
        textView.setText(text);
        textView.setTextColor(getContext().getResources().getColor(R.color.md_white_1000));
        return this;
    }

    public MySnackbar setAction(CharSequence text, final View.OnClickListener listener) {
        Button actionView = (Button) getView().findViewById(R.id.snackbar_action);
        actionView.setText(text);
        actionView.setVisibility(View.VISIBLE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                // Now dismiss the Snackbar
                dismiss();
            }
        });
        return this;
    }

    private static class ContentViewCallback implements BaseTransientBottomBar.ContentViewCallback {

        private View content;

        public ContentViewCallback(View content) {
            this.content = content;
        }

        @Override
        public void animateContentIn(int delay, int duration) {
            ViewCompat.setScaleY(content, 0f);
            ViewCompat.animate(content).scaleY(1f).setDuration(duration).setStartDelay(delay);
        }

        @Override
        public void animateContentOut(int delay, int duration) {
            ViewCompat.setScaleY(content, 1f);
            ViewCompat.animate(content).scaleY(0f).setDuration(duration).setStartDelay(delay);
        }
    }
}