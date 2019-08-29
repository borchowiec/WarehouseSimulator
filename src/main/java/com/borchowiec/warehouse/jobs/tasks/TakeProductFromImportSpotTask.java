package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.transporter.Transporter;

/**
 * This task is responsible for taking product from import spot and giving it for the transporter.
 * @author Patryk Borchowiec
 */
public class TakeProductFromImportSpotTask extends Task {
    private Product product;

    /**
     * Main constructor
     * @param transporter The transporter that will has had a product.
     */
    public TakeProductFromImportSpotTask(Transporter transporter) {
        super(transporter);
        product = transporter.getContext().getBean("product", Product.class); //taking new product from context
    }

    /**
     * Attaches product to the transporter's arm.
     * @return Always true because this task is finished after one execution.
     */
    @Override
    public boolean doTask() {
        transporter.attachToTheArm(product);
        return true;
    }

    @Override
    public String getTitle() {
        return "Taking the product from import spot";
    }
}
