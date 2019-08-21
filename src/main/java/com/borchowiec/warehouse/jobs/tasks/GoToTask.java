package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

import java.util.Random;

public class GoToTask {

    private double x;
    private double y;
    private Transporter transporter;

    public GoToTask(double x, double y, Transporter transporter) {
        this.x = x;
        this.y = y;
        this.transporter = transporter;
    }

    public boolean doTask() {
        return transporter.goTo(x, y);
    }
}