package com.vistony.salesforce.Controller.Utilitario;

import android.view.Surface;

public class ORIENTATIONS {
    public static final int DEGREE_0 = 0;
    public static final int DEGREE_90 = 1;
    public static final int DEGREE_180 = 2;
    public static final int DEGREE_270 = 3;

    public static int get(int rotation) {
        switch (rotation) {
            case Surface.ROTATION_0:
                return DEGREE_0;
            case Surface.ROTATION_90:
                return DEGREE_90;
            case Surface.ROTATION_180:
                return DEGREE_180;
            case Surface.ROTATION_270:
                return DEGREE_270;
            default:
                return DEGREE_0;
        }
    }
}
