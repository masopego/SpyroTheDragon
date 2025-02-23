package dam.pmdm.spyrothedragon.utils;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class BubblePositionCalculator {
    private final WindowManager windowManager;
    private final View screen;
    private final View bubble;

    private static final int NUM_ELEMENTS = 3;

    public BubblePositionCalculator(
            View screen,
            View bubble,
            WindowManager windowManager

    ) {
        this.screen = screen;
        this.bubble = bubble;
        this.windowManager = windowManager;
    }

    public BubbleCoordinates getPosition(BubblePosition position) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        int widthView = screen.getWidth();
        int menuButtonWidth = screenWidth / NUM_ELEMENTS;

        return new BubbleCoordinates(
            (menuButtonWidth * (position.ordinal())) + (menuButtonWidth / 2) - (widthView / 2),
            heightPixels - (bubble.getHeight() / NUM_ELEMENTS * 2)
        );
    }
}
