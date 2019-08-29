package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.jobs.tasks.Task;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents job that will be doing by one of the transporters. It contains queue of {@link Task tasks}
 * that will be done one by one.
 * @author Patryk Borchowiec
 */
public abstract class Job {
    protected Transporter transporter;
    protected Queue<Task> tasks;

    /**
     * Main constructor
     * @param transporter Transporter that will be doing this job.
     * @param warehouseModel Current warehouse model.
     */
    Job(Transporter transporter, WarehouseModel warehouseModel) {
        this.transporter = transporter;
        tasks = new LinkedList<>();
    }

    /**
     * This method do tasks from task's queue. If task is finished, removes it from queue. If task's queue is empty,
     * the job is finished so the truth will be returned.
     * @return True, if job is finished.
     */
    public boolean doJob() {
        //if queue is empty, job is finished
        if (tasks.isEmpty())
            return true;

        Task task = tasks.peek();
        boolean done = task.doTask();
        if (done) {
            tasks.remove();
            if (!tasks.isEmpty())
                transporter.setStatus(tasks.peek().getTitle());
        }
        return false;
    }
}
