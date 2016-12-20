package stowell.xmas2016v2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
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

    private int TIME_UNTIL_FIRST_NOTIFICATION = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = this; //TODO check this!

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


       // setAlarmService(10);

        CountDownTimer cdtimer = new CountDownTimer(TIME_UNTIL_FIRST_NOTIFICATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Main", "Tick");
            }

            @Override
            public void onFinish() {
                Log.d("Main", "DONE");
                sendNotification();
            }
        };

        cdtimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
      //  mSoundPool.release();
    }


    private void setAlarmService (int seconds){

        Toast.makeText(getApplicationContext(), "Setting up Alarm", Toast.LENGTH_LONG).show();

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("ALARM: ", "SET");

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, seconds);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);


    }

    private void sendNotification(){
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.donkey)
                .setContentTitle("Merry Christmas!")
                .setContentText("You're a donkey")
                .setAutoCancel(true)
                .setSound(android.net.Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.donkey))
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }



}
