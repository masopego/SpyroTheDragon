
package dam.pmdm.spyrothedragon.utils;

import android.content.Context;
import android.media.SoundPool;

public class SoundManager {
    private final SoundPool soundPool;
    private final Context context;

    public SoundManager(Context context) {
        this.context = context;
        this.soundPool = new SoundPool.Builder().setMaxStreams(1).build();
    }

    public void play(int soundResource) {
        int soundId = soundPool.load(context, soundResource, 1);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0) {
                soundPool.play(soundId, 1f, 1f, 0, 0, 1f);
            }
        });
    }

    public void release() {
        soundPool.release();
    }

}
