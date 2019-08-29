package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

/**
 * This class represents tasks doing by transporters.
 * @author Patryk Borchowiec
 */
public abstract class Task {
    protected Transporter transporter;

    /**
     * Main constructor.
     * @param transporter Transporter that will be doing the task.
     */
    public Task(Transporter transporter) {
        this.transporter = transporter;
    }

    /**
     * This method do actions that are important to finish the task.
     * @return True if task is finished.
     */
    public abstract boolean doTask();

    /**
     * This method returns title of the task.
     * @return Title of the task.
     */
    public abstract String getTitle();
}
