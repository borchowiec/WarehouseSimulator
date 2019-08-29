package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import static com.borchowiec.warehouse.shelves.ShelfStatus.EMPTY;

/**
 * This task is responsible for taking product from shelf and giving it for the transporter.
 * @author Patryk Borchowiec
 */
public class TakeProductOfTheShelfTask extends Task {
    private Shelf shelf;
    private int stage = 0;

    /**
     * Main constructor
     * @param transporter The transporter that will has had a product.
     * @param shelf Shelf from which the product will be taken.
     */
    public TakeProductOfTheShelfTask(Transporter transporter, Shelf shelf) {
        super(transporter);
        this.shelf = shelf;
    }

    /**
     * This method is responsible for taking product from the shelf and giving it to the transporter.
     * @return True if the product will be taken.
     */
    @Override
    public boolean doTask() {
        switch (stage) {
            case 0: //propound transporter's arm
                double lengthOfArm = shelf.getCenterY() - transporter.getCenterY();
                if (transporter.propoundTheArm(lengthOfArm))
                    stage++;
                break;
            case 1: //attaching product to the transporter's arm and setting shelf's status
                Product product = shelf.getProduct();
                shelf.setProduct(null);
                shelf.setStatus(EMPTY);
                transporter.attachToTheArm(product);
                stage++;
                break;
            case 2: //slip the arm back
                if (transporter.propoundTheArm(0))
                    return true;
        }
        return false;
    }

    @Override
    public String getTitle() {
        return "Taking the product from the shelf";
    }
}