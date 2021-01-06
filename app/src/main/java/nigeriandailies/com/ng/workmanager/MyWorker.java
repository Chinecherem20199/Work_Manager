package nigeriandailies.com.ng.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public final static String KEY_TASK_OUTPUT = "key_task_output";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String desc = data.getString(MainActivity.KEY_TASK_DESC);

        Data data1 = new Data.Builder()
                .putString(KEY_TASK_OUTPUT, "Task finish successfully")
                .build();
        Result.success(data1);


        displayNotification("Hey, i am your work", desc);
        return Result.success();
    }
    private void displayNotification(String task, String desc){

        //initializing NotificationManager
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //if android version is greater or equal to version O, then create notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel
                    ("indentification", "indentification", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        // Initialize Notification Builder
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), "indentification")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.drawable.ic_notifications);
        manager.notify(1, builder.build());
    }
}
