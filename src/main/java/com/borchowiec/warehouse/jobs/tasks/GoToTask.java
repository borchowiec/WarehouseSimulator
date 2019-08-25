package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

public class GoToTask extends Task {

    private double x;
    private double y;

    public GoToTask(double x, double y, Transporter transporter) {
        super(transporter);
        this.x = x;
        this.y = y;
    }

    public boolean doTask() {
        return transporter.goTo(x, y);
    }

    @Override
    public String getTitle() {
        return "Going to (" + x + "|" + y+")";
    }
}