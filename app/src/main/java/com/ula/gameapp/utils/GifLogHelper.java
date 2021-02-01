package com.ula.gameapp.utils;

import android.content.Context;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GifLogHelper {
    Map<String, String> gifSpeeds;

    public GifLogHelper() {
        gifSpeeds = new HashMap<>();
    }

    public void sortGifs() {
        gifSpeeds = new TreeMap<>(gifSpeeds);
    }

    public void saveSpeed(String name, double speed) {
        gifSpeeds.put(name, new DecimalFormat("##.##").format(speed) + "");
    }

    public double checkSpeed(String name) {
        if (gifSpeeds.containsKey(name)) {
            return Double.valueOf(gifSpeeds.get(name));
        }

        return 1;
    }

    public void populateLog(Context context, TextView txt) {
        sortGifs();

        String str = "";
        for (Map.Entry<String, String> gif : gifSpeeds.entrySet()) {
            str += gif.getKey() + " : " + gif.getValue() + "\n";
        }

        txt.setText(str);
        PreferencesUtil.saveMap(context, "speed", gifSpeeds);
    }

    public void loadMap(Context context) {
        gifSpeeds = PreferencesUtil.loadMap(context, "speed");
    }
}
