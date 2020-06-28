package com.example.background;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class JobServices extends JobService {
    private static final String TAG = "JobServices";
    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Job Started");
        BackgroundWork(params);
        return false;
    }

    private void BackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    Log.d(TAG, "Run with : "+ i);
                    if (jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job Finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Cancelled");
        jobCancelled = true;
        return false;
    }
}
