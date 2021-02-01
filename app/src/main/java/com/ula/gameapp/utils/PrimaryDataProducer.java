package com.ula.gameapp.utils;

import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;

import java.util.ArrayList;

public class PrimaryDataProducer {
    private ArrayList<JAnimation> animationsList = new ArrayList<>();

    public ArrayList<JAnimation> getAnimationsList() {
        JAnimation animation;

        // first run
        animation = new JAnimation(1, FileType.IMAGE, "1-1_00024.jpg", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(2, FileType.GIF, "1_1_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(3, FileType.GIF, "1_1_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(4, FileType.GIF, "1_1_3.gif", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);

        // second run
        animation = new JAnimation(5, FileType.IMAGE, "1-2_00108.jpg", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(6, FileType.GIF, "1_2_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(7, FileType.GIF, "1_2_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(8, FileType.GIF, "1_2_3.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(9, FileType.GIF, "1_2_3.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(10, FileType.GIF, "1_2_4.gif", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);

        // third run
        animation = new JAnimation(11, FileType.GIF, "2_1_1.gif", 0/*3*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(12, FileType.GIF, "2_1_2.gif", 0/*3*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(13, FileType.GIF, "2_1_3.gif", 0/*3*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(14, FileType.GIF, "2_1_4.gif", 0/*9*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);

        // forth run
        animation = new JAnimation(15, FileType.GIF, "2_2.gif", 0/*3*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(16, FileType.GIF, "2_3_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(17, FileType.GIF, "2_3_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(18, FileType.GIF, "2_4_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(19, FileType.GIF, "2_4_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(20, FileType.GIF, "2_4_3.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, true, true);
        animationsList.add(animation);
        animation = new JAnimation(21, FileType.IMAGE, "white.png", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, true, false);
        animationsList.add(animation);

        // fifth run
        animation = new JAnimation(22, FileType.GIF, "3_1.gif", 0/*3*/,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(23, FileType.GIF, "3_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(24, FileType.GIF, "3_2_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(25, FileType.GIF, "3_2_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(26, FileType.GIF, "3_2_3.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);

        // sixth run
        animation = new JAnimation(27, FileType.GIF, "4_1_1.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(28, FileType.GIF, "4_1_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, false, true);
        animationsList.add(animation);
        animation = new JAnimation(29, FileType.GIF, "4_2.gif", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, true, true);
        animationsList.add(animation);
        animation = new JAnimation(30, FileType.IMAGE, "white.png", 0,
                BodyShape.NONE, Age.EGG, EmotionType.NONE, true, false);
        animationsList.add(animation);

        // Child
        animation = new JAnimation(31, FileType.GIF, "6_1.gif", 5,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(32, FileType.GIF, "6_2.gif", 4,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(33, FileType.GIF, "6_3.gif", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(34, FileType.GIF, "6_3.gif", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, true, true);
        animationsList.add(animation);

        animation = new JAnimation(35, FileType.GIF, "7_1.gif", 4,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(36, FileType.GIF, "7_2.gif", 5,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(37, FileType.GIF, "7_3.gif", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.HAPPY, true, true);
        animationsList.add(animation);

        animation = new JAnimation(38, FileType.GIF, "8_1.gif", 5,
                BodyShape.NORMAL, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(39, FileType.GIF, "8_2.gif", 5,
                BodyShape.NORMAL, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(40, FileType.GIF, "8_3.gif", 0,
                BodyShape.NORMAL, Age.CHILD, EmotionType.OK, true, true);
        animationsList.add(animation);

        animation = new JAnimation(41, FileType.GIF, "9_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(42, FileType.GIF, "9_2.gif", 4,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(43, FileType.GIF, "9_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, true, true);
        animationsList.add(animation);
        animation = new JAnimation(44, FileType.GIF, "10_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(45, FileType.GIF, "10_2.gif", 4,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(46, FileType.GIF, "10_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.CHILD, EmotionType.OK, false, true);
        animationsList.add(animation);

        animation = new JAnimation(47, FileType.GIF, "11_1.gif", 5,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(48, FileType.GIF, "11_2.gif", 0,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(49, FileType.GIF, "11_3.gif", 0,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, true, true);
        animationsList.add(animation);

        animation = new JAnimation(50, FileType.GIF, "12_1.gif", 5,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(51, FileType.GIF, "12_2.gif", 4,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(52, FileType.GIF, "12_3.gif", 0,
                BodyShape.FAT, Age.CHILD, EmotionType.SAD, true, true);
        animationsList.add(animation);

        // Adult
        animation = new JAnimation(53, FileType.GIF, "13_1.gif", 5,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(54, FileType.GIF, "13_2.gif", 4,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(55, FileType.GIF, "13_3.gif", 0,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, true, true);
        animationsList.add(animation);
        animation = new JAnimation(56, FileType.GIF, "14_1.gif", 5,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(57, FileType.GIF, "14_2.gif", 4,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(58, FileType.GIF, "14_3.gif", 0,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, true, true);
        animationsList.add(animation);
        animation = new JAnimation(59, FileType.GIF, "15_1.gif", 5,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(60, FileType.GIF, "15_2.gif", 4,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(61, FileType.GIF, "15_3.gif", 0,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, true, true);
        animationsList.add(animation);
        animation = new JAnimation(62, FileType.GIF, "16_1.gif", 5,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(63, FileType.GIF, "16_2.gif", 4,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, false, true);
        animationsList.add(animation);
        animation = new JAnimation(64, FileType.GIF, "16_3.gif", 0,
                BodyShape.NORMAL, Age.ADULT, EmotionType.OK, true, true);
        animationsList.add(animation);

        animation = new JAnimation(65, FileType.GIF, "17_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(66, FileType.GIF, "17_2.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(67, FileType.GIF, "17_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(68, FileType.GIF, "17_3_1.gif", 0,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(69, FileType.GIF, "18_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(70, FileType.GIF, "18_2.gif", 4,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(71, FileType.GIF, "18_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(72, FileType.GIF, "19_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(73, FileType.GIF, "19_2.gif", 4,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(74, FileType.GIF, "19_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(75, FileType.GIF, "20_1.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(76, FileType.GIF, "20_2.gif", 5,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(77, FileType.GIF, "20_3.gif", 0,
                BodyShape.OVER_WEIGHT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);

        animation = new JAnimation(78, FileType.GIF, "21_1.gif", 5,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(79, FileType.GIF, "21_2.gif", 4,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(80, FileType.GIF, "21_3.gif", 0,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(81, FileType.GIF, "22_1.gif", 5,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(82, FileType.GIF, "22_2.gif", 4,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);
        animation = new JAnimation(83, FileType.GIF, "22_3.gif", 0,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(84, FileType.GIF, "23_1.gif", 5,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(85, FileType.GIF, "23_2.gif", 4,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, false, true);
        animationsList.add(animation);
        animation = new JAnimation(86, FileType.GIF, "23_3.gif", 0,
                BodyShape.FAT, Age.ADULT, EmotionType.SAD, true, true);
        animationsList.add(animation);

        animation = new JAnimation(87, FileType.GIF, "24_1.gif", 5,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(88, FileType.GIF, "24_2.gif", 5,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(89, FileType.GIF, "24_3.gif", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(90, FileType.GIF, "24_4.gif", 1,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(91, FileType.GIF, "25_1.gif", 5,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(92, FileType.GIF, "25_2.gif", 4,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(93, FileType.GIF, "25_3.gif", 1,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(94, FileType.GIF, "26_1.gif", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        animation = new JAnimation(95, FileType.GIF, "26_2.gif", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);
        /*animation = new JAnimation(96, FileType.GIF, "26_3.gif", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);*/
        animation = new JAnimation(96, FileType.MOVIE, "26_3.mp4", 0,
                BodyShape.FIT, Age.ADULT, EmotionType.HAPPY, false, true);
        animationsList.add(animation);

        return animationsList;
    }
}
