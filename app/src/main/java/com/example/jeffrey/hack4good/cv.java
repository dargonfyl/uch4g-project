package com.example.jeffrey.hack4good;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class cv {
    private Mat image;
    private Mat black;
    private Mat scratched;

    cv(Bitmap image) {
        this.image = new Mat();
        Utils.bitmapToMat(image, this.image);
        this.black = Imgcodecs.imread("black.png");
        configureBlack();
        scratched = new Mat();
        Imgproc.resize(black, scratched, this.image.size());
    }

    public Mat getImage() {
        return image;
    }

    private void configureBlack() {
        Imgproc.resize(black, black, image.size());
    }

    public void scratch(int x, int y) {
        byte[] color = new byte[image.channels()];
        for (int i = 0; i < image.channels(); i++) {
            color[i] = (byte) 0;
        }
        scratched.put(x, y, color);
    }

    public Bitmap convertScratched() {
        Bitmap result = Bitmap.createBitmap(scratched.cols(), scratched.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(scratched, result);
        return result;
    }
}
