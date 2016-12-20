package stowell.xmas2016v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by elizabethstowell on 12/11/16.
 */

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Setting up the new survey!", Toast.LENGTH_SHORT).show();
        Intent secIntent = new Intent(context, MainActivity.class);
        secIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(secIntent);
    }
}
