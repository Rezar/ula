# ula

We want to count steps individually for every activity like (Downstairs -Jogging -Sitting -Standing -Upstairs -Walking), 
so we used [this library](https://github.com/curiousily/TensorFlow-on-Android-for-Human-Activity-Recognition-with-LSTMs) to detect the activity of the user (TensorFlowClassifier)
then we used Sensor.TYPE_STEP_DETECTOR to detect steps (ActivityTracker), so by combining these two parts we reached our goal.
