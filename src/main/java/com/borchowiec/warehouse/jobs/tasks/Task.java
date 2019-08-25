package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

public abstract class Task {
    protected Transporter transporter;

    public Task(Transporter transporter) {
        this.transporter = transporter;
    }

    public abstract boolean doTask();

    public abstract String getTitle();
}
