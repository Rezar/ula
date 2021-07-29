# ula

We want to count steps individually for every activity like (Downstairs -Jogging -Sitting -Standing -Upstairs -Walking), 
so we used [this](https://github.com/curiousily/TensorFlow-on-Android-for-Human-Activity-Recognition-with-LSTMs)  library to detect the activity of the user ([TensorFlowClassifier](https://github.com/Rezar/ula/blob/activitytracker/app/src/main/java/com/ula/gameapp/activitytracker/TensorFlowClassifier.java))
then we used Sensor.TYPE_STEP_DETECTOR to detect steps ([ActivityTracker](https://github.com/Rezar/ula/blob/activitytracker/app/src/main/java/com/ula/gameapp/core/service/ActivityTracker.java)), so by combining these two parts we reached our goal.
