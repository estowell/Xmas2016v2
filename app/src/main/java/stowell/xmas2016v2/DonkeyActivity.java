package stowell.xmas2016v2;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DonkeyActivity extends AppCompatActivity {


    //MediaPlayer for audio cues
    private MediaPlayer mMediaPlayer;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donkey);
        context = this;

        mMediaPlayer =  MediaPlayer.create(this, R.raw.donkey);
        mMediaPlayer.start();

        Button donkey_back = (Button)findViewById(R.id.donkey_button);
        donkey_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent (context, MainActivity.class);
                startActivity(main);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
        //  mSoundPool.release();
    }
}
