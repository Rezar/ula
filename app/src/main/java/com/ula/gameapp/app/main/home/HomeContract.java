/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 1:16 AM
 */

package com.ula.gameapp.app.main.home;

import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.utils.enums.FileType;

class HomeContract {

    interface View {

        void updateStepsData(int steps);

        void toggleHelpView(boolean visible);

        void toggleDrawableHolder(FileType fileType);

        void showDrawable(JAnimation animation);

        void showCountdown(long time);
    }

    interface Presenter {

        void setView(View view);

        void initial(PetStatus petStatus, JAnimation animation);

        void onGifClicked();

        void decideGifUpdate();

        int getClickCount();

        void resetGifClick();
    }
}
