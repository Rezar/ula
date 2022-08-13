package com.ula.gameapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ula.gameapp.app.main.MainActivity;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PrimaryDataProducer {
    private ArrayList<JAnimation> animationsList = new ArrayList<>();

    public ArrayList<JAnimation> getAnimationsList(Context c) {

        JAnimation animation;

        // first run
        animation = new JAnimation(1, FileType.IMAGE, "1-1_00024.png", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 1);
        animationsList.add(animation);
        animation = new JAnimation(2, FileType.MOVIE, "1-1.mp4", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 1);
        animationsList.add(animation);
        animation = new JAnimation(3, FileType.MOVIE, "1-2.mp4", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 1);
        animationsList.add(animation);
        animation = new JAnimation(4, FileType.MOVIE, "2-1.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 1);
        animationsList.add(animation);
        animation = new JAnimation(5, FileType.MOVIE, "2-2.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 2);
        animationsList.add(animation);

        animation = new JAnimation(6, FileType.MOVIE, "2-3.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 2);
        animationsList.add(animation);
        animation = new JAnimation(7, FileType.MOVIE, "2-4.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 2);
        animationsList.add(animation);

        animation = new JAnimation(8, FileType.MOVIE, "3-1.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 3);
        animationsList.add(animation);
        animation = new JAnimation(9, FileType.MOVIE, "3-2.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 3);
        animationsList.add(animation);
        animation = new JAnimation(10, FileType.MOVIE, "3-3.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 3);
        animationsList.add(animation);

        animation = new JAnimation(11, FileType.MOVIE, "4-1.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 0, 4);
        animationsList.add(animation);
        animation = new JAnimation(12, FileType.MOVIE, "4-2.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, 4);
        animationsList.add(animation);
        animation = new JAnimation(13, FileType.MOVIE, "5-1.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 0, 5);
        animationsList.add(animation);

        animation = new JAnimation(14, FileType.MOVIE, "5-2.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 0, 5);
        animationsList.add(animation);

        animation = new JAnimation(15, FileType.MOVIE, "5-2.mp4", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true, 1, true, 13, 14, 5);
        animationsList.add(animation);

        // second run

        animation = new JAnimation(16, FileType.MOVIE, "6-1.mp4", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, true, 6);
        animationsList.add(animation);
        animation = new JAnimation(17, FileType.MOVIE, "6-2.mp4", 2,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, 6);
        animationsList.add(animation);
        animation = new JAnimation(18, FileType.MOVIE, "6-3.mp4", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, true, true, 1, 6);
        animationsList.add(animation);


//         third run
        animation = new JAnimation(19, FileType.MOVIE, "7-1-1.mp4", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, true, 7);
        animationsList.add(animation);
        animation = new JAnimation(20, FileType.MOVIE, "7-1-2.mp4", 0/*9*/,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, 7);
        animationsList.add(animation);
        animation = new JAnimation(21, FileType.MOVIE, "7-1-2.mp4", 0/*9*/,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, 7);
        animationsList.add(animation);

        animation = new JAnimation(22, FileType.MOVIE, "7-2.mp4", 2,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, 7);
        animationsList.add(animation);
        animation = new JAnimation(23, FileType.MOVIE, "7-3.mp4", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, true, true, 1, 7);
        animationsList.add(animation);

        animation = new JAnimation(24, FileType.MOVIE, "8-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, true, 8);
        animationsList.add(animation);
        animation = new JAnimation(25, FileType.MOVIE, "8-2.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, false, true, 1, 8);
        animationsList.add(animation);
        animation = new JAnimation(26, FileType.MOVIE, "8-3.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.CHILD, EmotionType.NONE, true, true, 1, 8);
        animationsList.add(animation);


        animation = new JAnimation(27, FileType.MOVIE, "9-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, false, true, 1, true, 9);
        animationsList.add(animation);
        animation = new JAnimation(28, FileType.MOVIE, "9-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, false, true, 1, 9);
        animationsList.add(animation);
        animation = new JAnimation(29, FileType.MOVIE, "9-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, true, true, 1, 9);
        animationsList.add(animation);

        animation = new JAnimation(30, FileType.MOVIE, "10-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, false, true, 1, true, 10);
        animationsList.add(animation);
        animation = new JAnimation(31, FileType.MOVIE, "10-2.mp4", 1/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, false, true, 1, 10);
        animationsList.add(animation);
        animation = new JAnimation(32, FileType.MOVIE, "10-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.NONE, true, true, 1, 10);
        animationsList.add(animation);


        animation = new JAnimation(33, FileType.MOVIE, "11-1-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, true, 11);
        animationsList.add(animation);
        animation = new JAnimation(34, FileType.MOVIE, "11-1-2.mp4", 1/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 11);
        animationsList.add(animation);
        animation = new JAnimation(35, FileType.MOVIE, "11-1-3.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, true, true, 1, 11);
        animationsList.add(animation);


        animation = new JAnimation(36, FileType.MOVIE, "11-2-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, true, 11);
        animationsList.add(animation);
        animation = new JAnimation(37, FileType.MOVIE, "11-2-2.mp4", 1/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 11);
        animationsList.add(animation);
        animation = new JAnimation(38, FileType.MOVIE, "11-2-3.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, true, true, 1, 11);
        animationsList.add(animation);


        animation = new JAnimation(39, FileType.MOVIE, "12-1-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 12);
        animationsList.add(animation);
        animation = new JAnimation(40, FileType.MOVIE, "12-1-2.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, true, 12);
        animationsList.add(animation);
        animation = new JAnimation(41, FileType.MOVIE, "12-1-3.mp4", 2/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 12);
        animationsList.add(animation);
        animation = new JAnimation(42, FileType.MOVIE, "12-2-1.mp4", 2/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 12);
        animationsList.add(animation);
        animation = new JAnimation(43, FileType.MOVIE, "12-3-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, false, true, 1, 12);
        animationsList.add(animation);
        animation = new JAnimation(44, FileType.MOVIE, "12-3-2.mp4", 0/*3*/,
                BodyShape.FAT, Age.CHILD, EmotionType.NONE, true, true, 1, 12);
        animationsList.add(animation);

        animation = new JAnimation(45, FileType.MOVIE, "13-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 13);
        animationsList.add(animation);
        animation = new JAnimation(46, FileType.MOVIE, "13-2.mp4", 2/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 13);
        animationsList.add(animation);
        animation = new JAnimation(47, FileType.MOVIE, "13-3.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, true, true, 1, 13);
        animationsList.add(animation);


        animation = new JAnimation(48, FileType.MOVIE, "14-1-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, true, 14);
        animationsList.add(animation);
        animation = new JAnimation(49, FileType.MOVIE, "14-1-2.mp4", 2/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 14);
        animationsList.add(animation);
        animation = new JAnimation(50, FileType.MOVIE, "14-2-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, true, 14);
        animationsList.add(animation);
        animation = new JAnimation(51, FileType.MOVIE, "14-2-2.mp4", 1/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 14);
        animationsList.add(animation);
        animation = new JAnimation(52, FileType.MOVIE, "14-3-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, true, 14);
        animationsList.add(animation);
        animation = new JAnimation(53, FileType.MOVIE, "14-3-2.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, true, true, 1, 14);
        animationsList.add(animation);


        animation = new JAnimation(54, FileType.MOVIE, "15-1-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, true, 15);
        animationsList.add(animation);
        animation = new JAnimation(55, FileType.MOVIE, "15-1-2.mp4", 2/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 15);
        animationsList.add(animation);
        animation = new JAnimation(56, FileType.MOVIE, "15-2.mp4", 1/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 15);
        animationsList.add(animation);
        animation = new JAnimation(57, FileType.MOVIE, "15-3.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, true, true, 1, 15);
        animationsList.add(animation);

        animation = new JAnimation(58, FileType.MOVIE, "16-1-1.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, true, 16);
        animationsList.add(animation);
        animation = new JAnimation(59, FileType.MOVIE, "16-1-2.mp4", 2/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 16);
        animationsList.add(animation);
        animation = new JAnimation(60, FileType.MOVIE, "16-2-2.mp4", 2/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, false, true, 1, 16);
        animationsList.add(animation);
        animation = new JAnimation(61, FileType.MOVIE, "16-3.mp4", 0/*3*/,
                BodyShape.NORMAL, Age.ADULT, EmotionType.NONE, true, true, 1, 16);
        animationsList.add(animation);


        animation = new JAnimation(62, FileType.MOVIE, "17-1-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 17);
        animationsList.add(animation);
        animation = new JAnimation(63, FileType.MOVIE, "17-1-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 17);
        animationsList.add(animation);
        animation = new JAnimation(64, FileType.MOVIE, "17-2.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 17);
        animationsList.add(animation);
        animation = new JAnimation(65, FileType.MOVIE, "17-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, true, true, 1, 17);
        animationsList.add(animation);

        animation = new JAnimation(66, FileType.MOVIE, "18-1-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 18);
        animationsList.add(animation);
        animation = new JAnimation(67, FileType.MOVIE, "18-1-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 18);
        animationsList.add(animation);
        animation = new JAnimation(68, FileType.MOVIE, "18-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 18);
        animationsList.add(animation);
        animation = new JAnimation(69, FileType.MOVIE, "18-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, true, true, 1, 18);
        animationsList.add(animation);

        animation = new JAnimation(70, FileType.MOVIE, "19-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 19);
        animationsList.add(animation);
        animation = new JAnimation(71, FileType.MOVIE, "19-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 19);
        animationsList.add(animation);
        animation = new JAnimation(72, FileType.MOVIE, "19-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, true, true, 1, 19);
        animationsList.add(animation);


        animation = new JAnimation(73, FileType.MOVIE, "20-1-1.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 20);
        animationsList.add(animation);
        animation = new JAnimation(74, FileType.MOVIE, "20-1-2.mp4", 2/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 20);
        animationsList.add(animation);
        animation = new JAnimation(75, FileType.MOVIE, "20-2.mp4", 1/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, false, true, 1, 20);
        animationsList.add(animation);
        animation = new JAnimation(76, FileType.MOVIE, "20-3.mp4", 0/*3*/,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.NONE, true, true, 1, 20);
        animationsList.add(animation);


        animation = new JAnimation(77, FileType.MOVIE, "21-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 21);
        animationsList.add(animation);
        animation = new JAnimation(78, FileType.MOVIE, "21-2.mp4", 2/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, 21);
        animationsList.add(animation);
        animation = new JAnimation(79, FileType.MOVIE, "21-3.mp4", 0/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, true, true, 1, 21);
        animationsList.add(animation);


        animation = new JAnimation(80, FileType.MOVIE, "22-1-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 22);
        animationsList.add(animation);
        animation = new JAnimation(81, FileType.MOVIE, "21-1-2.mp4", 2/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, 22);
        animationsList.add(animation);
        animation = new JAnimation(82, FileType.MOVIE, "22-2.mp4", 1/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, true, true, 1, 22);
        animationsList.add(animation);

        animation = new JAnimation(83, FileType.MOVIE, "23-1.mp4", 0/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 23);
        animationsList.add(animation);
        animation = new JAnimation(84, FileType.MOVIE, "23-2.mp4", 1/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, false, true, 1, 23);
        animationsList.add(animation);
        animation = new JAnimation(85, FileType.MOVIE, "23-3.mp4", 1/*3*/,
                BodyShape.FAT, Age.ADULT, EmotionType.NONE, true, true, 1, 23);
        animationsList.add(animation);


        animation = new JAnimation(86, FileType.MOVIE, "24-1.mp4", 0/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 24);
        animationsList.add(animation);
        animation = new JAnimation(87, FileType.MOVIE, "24-2.mp4", 1/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 24);
        animationsList.add(animation);
        animation = new JAnimation(88, FileType.MOVIE, "24-3.mp4", 1/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 24);
        animationsList.add(animation);
        animation = new JAnimation(89, FileType.MOVIE, "24-4.mp4", 1/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 24);
        animationsList.add(animation);

        animation = new JAnimation(90, FileType.MOVIE, "24-2.mp4", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 86, 88, 24);
        animationsList.add(animation);
        ;
        animation = new JAnimation(91, FileType.MOVIE, "25-1.mp4", 0/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 25);
        animationsList.add(animation);

        animation = new JAnimation(92, FileType.MOVIE, "25-2.mp4", 2/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 25);
        animationsList.add(animation);
        animation = new JAnimation(93, FileType.MOVIE, "25-3.mp4", 0/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, true, true, 1, 25);
        animationsList.add(animation);


        animation = new JAnimation(94, FileType.MOVIE, "26-1.mp4", 0/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 26);
        animationsList.add(animation);
        animation = new JAnimation(95, FileType.MOVIE, "26-2-1.mp4", 0/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 26);
        animationsList.add(animation);
        animation = new JAnimation(96, FileType.MOVIE, "26-2-2.mp4", 2/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 26);
        animationsList.add(animation);
        animation = new JAnimation(97, FileType.MOVIE, "26-3.mp4", 2/*3*/,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, 26);
        animationsList.add(animation);

        animation = new JAnimation(98, FileType.MOVIE, "26-3.mp4", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.NONE, false, true, 1, true, 95, 96, 26);
        animationsList.add(animation);


        SharedPreferences sharedPreferences = c.getSharedPreferences("ulaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String data = sharedPreferences.getString("animationData", "{}");

        try {
            JSONObject obj = new JSONObject(data);

            for (JAnimation anim : animationsList) {

                String animAge = anim.getAge().toString();
                if (!obj.has(animAge))
                    obj.put(animAge, new JSONObject());


                JSONObject ageObj = obj.getJSONObject(animAge);

                String animBodyShape = anim.getBodyShape().toString();
                if (!ageObj.has(animBodyShape))
                    ageObj.put(animBodyShape, new JSONObject(data));


                JSONObject bodyShapeObj = ageObj.getJSONObject(animBodyShape);

                String animScenario = String.valueOf(anim.getScenario());

                if (!bodyShapeObj.has(animScenario)) {
                    bodyShapeObj.put(animScenario, new JSONObject(data));
                    JSONObject scenarioObj = bodyShapeObj.getJSONObject(animScenario);

                    scenarioObj.put("count", 0);
                    scenarioObj.put("times", new JSONArray());
                    scenarioObj.put("startAnimId", anim.getId());
                }

            }
            editor.putString("animationData", obj.toString());
            editor.apply();


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return animationsList;
    }
}
