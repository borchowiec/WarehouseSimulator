package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.transporter.Transporter;

public class TakeProductFromImportSpotTask extends Task {
    private Product product;

    public TakeProductFromImportSpotTask(Transporter transporter) {
        super(transporter);
        product = transporter.getContext().getBean("product", Product.class);
    }

    @Override
    public boolean doTask() {
        transporter.attachToTheArm(product);
        return true;
    }
}
