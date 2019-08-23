package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

public class GoHorizontallyTask extends Task {
    private double x;

    public GoHorizontallyTask(Transporter transporter, double x) {
        super(transporter);
        this.x = x;
    }

    public boolean doTask() {
        return transporter.goTo(x, transporter.getCenterY());
    }
}
