package nigeriandailies.com.ng.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_TASK_DESC = "key_task_desc";
    Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder()
                .putString(KEY_TASK_DESC, "Hey I am sending the work data")
                .build();

        //Initialize the oneTimeWorker method
       final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
               .setInputData(data)
               .build();

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(request);
            }
        });
        mTextView = findViewById(R.id.textView);
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
        .observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String status = workInfo.getState().name();
                if (workInfo != null) {

                    if (workInfo.getState().isFinished()){
                        Data data = workInfo.getOutputData();
                        String output = data.getString(MyWorker.KEY_TASK_OUTPUT);
                        mTextView.append(status + " \n");
                        mTextView.append(output + " \n");
                    }


                    mTextView.append(status + "\n");
                }
            }
        });

    }
}