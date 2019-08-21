package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.jobs.tasks.GoToTask;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.Random;

public class Job {
    private Transporter transporter;
    private GoToTask task;

    public Job(Transporter transporter) {
        this.transporter = transporter;
        task = new GoToTask(new Random().nextInt(1200), new Random().nextInt(750), transporter);
    }

    public boolean doJob() {
        return task.doTask();
    }
}
