package stowell.xmas2016v2;

/**
 * Created by elizabethstowell on 12/20/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    public static final int REQUEST_CODE = 12345;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "in onReceive");
        if (intent != null){
            Intent i = new Intent(context, DonkeyActivity.class);
            // Start service:
           // context.startService(intent);
            // OR start activity:
            context.startActivity(i);
        }
    }
}
