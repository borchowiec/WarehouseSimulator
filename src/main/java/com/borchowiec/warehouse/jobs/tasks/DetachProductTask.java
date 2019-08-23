package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.transporter.Transporter;

public class DetachProductTask extends Task {
    public DetachProductTask(Transporter transporter) {
        super(transporter);
    }

    @Override
    public boolean doTask() {
        transporter.detachProduct();
        return true;
    }
}
