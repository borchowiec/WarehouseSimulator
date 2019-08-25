package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.jobs.tasks.Task;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Job {
    protected Transporter transporter;
    protected Queue<Task> tasks;
    protected WarehouseModel warehouseModel;

    public Job(Transporter transporter, WarehouseModel warehouseModel) {
        this.transporter = transporter;
        this.warehouseModel = warehouseModel;
        tasks = new LinkedList<>();
    }

    public boolean doJob() {
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
