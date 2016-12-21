package stowell.xmas2016v2;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    //MediaPlayer for audio cues
    private MediaPlayer mMediaPlayer;

    private Context context;

    String TAG = "Main";
    SharedPreferences preferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = this;

        preferences = getSharedPreferences("XmasPreferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = preferences.edit();
        sharedPreferencesEditor.commit();

        //create the media player.
        mMediaPlayer =  MediaPlayer.create(this, R.raw.bells);
        mMediaPlayer.start();

        final RelativeLayout startRL = (RelativeLayout)findViewById(R.id.start_rl);
        final RelativeLayout whoopsRL = (RelativeLayout)findViewById(R.id.whoops_rl);
        Button presentButton = (Button)findViewById(R.id.present_button);
        Button backButton = (Button)findViewById(R.id.back_button);

        presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRL.setVisibility(View.GONE);
                whoopsRL.setVisibility(View.VISIBLE);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRL.setVisibility(View.VISIBLE);
                whoopsRL.setVisibility(View.GONE);
            }
        });



        setAlarm(setAlarmTime());

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
      //  mSoundPool.release();
    }



    private int setAlarmTime(){
        int convertedToMilli = 60*1000;
        int time_till_alarm = preferences.getInt("Time", 0);

        Log.d(TAG, "hour" + Calendar.HOUR_OF_DAY);

        if (Calendar.HOUR_OF_DAY > 21){
            time_till_alarm = 12*60*convertedToMilli;
            Log.d(TAG, "Time Till Alarm: " +  time_till_alarm);
            sharedPreferencesEditor.putInt("Time", time_till_alarm);
            return time_till_alarm;
        }

        if (time_till_alarm == 0){
            time_till_alarm = 15;
        } else if (time_till_alarm > 120){
            time_till_alarm = 120;
        } else {
            time_till_alarm = time_till_alarm*2;
        }


        Log.d(TAG, "Time Till Alarm: " +  time_till_alarm);
        sharedPreferencesEditor.putInt("Time", time_till_alarm);
        return (time_till_alarm*convertedToMilli);

    }





    private void setAlarm(int delay)
    {
        // TODO Auto-generated method stub
        Intent i = new Intent(getApplicationContext(), DonkeyActivity.class);
        i.putExtra("dayORmonth", "day");
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),12345,i, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar cal = Calendar.getInstance();
        //cal.set(year, month, d, h, min);
        //registering our pending intent with alarmmanager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + delay), pi);
        //cal.getTimeInMillis();
        Log.d(TAG, "In set Alarm:" + ((cal.getTimeInMillis() + delay)-cal.getTimeInMillis()));
    }


}
