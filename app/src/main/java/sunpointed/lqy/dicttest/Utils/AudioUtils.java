package sunpointed.lqy.dicttest.Utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by lqy on 16/5/27.
 */
public class AudioUtils {

    public static MediaPlayer createAudioFromNet(String url){
        if(url == null || "".equals(url)){
            return null;
        }

        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(url);
            return player;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
