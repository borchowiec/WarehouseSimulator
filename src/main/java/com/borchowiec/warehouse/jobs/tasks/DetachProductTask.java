package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

/**
 * The goal of this task is to detach product from the transporter's arm.
 * @author Patryk Borchowiec
 */
public class DetachProductTask extends Task {
    public DetachProductTask(Transporter transporter) {
        super(transporter);
    }

    /**
     * Detaches product from the transporter's arm.
     * @return Always true because this task is finished after one execution.
     */
    @Override
    public boolean doTask() {
        transporter.detachProduct();
        return true;
    }

    @Override
    public String getTitle() {
        return "Detaching the product";
    }
}
