package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

/**
 * This task is responsible for moving transporter to the destination.
 * @author Patryk Borchowiec
 */
public class GoToTask extends Task {

    private double x;
    private double y;

    /**
     * Main constructor
     * @param x X coordinate of destination.
     * @param y Y coordinate of destination.
     * @param transporter Transporter that will be going to the destination.
     */
    public GoToTask(double x, double y, Transporter transporter) {
        super(transporter);
        this.x = x;
        this.y = y;
    }

    /**
     * This method moves transporter to the destination.
     * @return True, if transporter reached destination.
     */
    public boolean doTask() {
        return transporter.goTo(x, y);
    }

    @Override
    public String getTitle() {
        return "Going to (" + x + "|" + y+")";
    }
}