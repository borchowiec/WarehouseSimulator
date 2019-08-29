package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

/**
 * This task is responsible for moving transporter to the destination, but only horizontally.
 * @author Patryk Borchowiec
 */
public class GoHorizontallyTask extends Task {
    private double x;

    /**
     * Main constructor
     * @param transporter Transporter that will be going to the destination.
     * @param x X coordinate of destination.
     */
    public GoHorizontallyTask(Transporter transporter, double x) {
        super(transporter);
        this.x = x;
    }

    /**
     * This method moves horizontally transporter to the destination.
     * @return True, if transporter reached destination.
     */
    public boolean doTask() {
        return transporter.goTo(x, transporter.getCenterY());
    }

    @Override
    public String getTitle() {
        return "Going to (" + x + "|" + transporter.getCenterY()+")";
    }
}
