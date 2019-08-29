package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import static com.borchowiec.warehouse.shelves.ShelfStatus.HAS_PRODUCT;

/**
 * This task is responsible for taking product from transporter and leaving it on proper shelf.
 * @author Patryk Borchowiec
 */
public class LeaveProductOnShelfTask extends Task {
    private Shelf shelf;
    private int stage = 0;

    /**
     * Main constructor
     * @param transporter Transporter from which the product is taken.
     * @param shelf Shelf where the product will be left.
     */
    public LeaveProductOnShelfTask(Transporter transporter, Shelf shelf) {
        super(transporter);
        this.shelf = shelf;
    }

    /**
     * This method leaves product on the shelf.
     * @return True, if the product was left on the shelf, and the arm was slipped back.
     */
    @Override
    public boolean doTask() {
        switch (stage) {
            case 0: //propound transporter's arm
                double lengthOfArm = shelf.getCenterY() - transporter.getCenterY();
                if (transporter.propoundTheArm(lengthOfArm))
                    stage++;
                break;
            case 1: //leave product on shelf and set shelf's status
                shelf.setProduct(transporter.detachProduct());
                shelf.setStatus(HAS_PRODUCT);
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
        return "Leaving the product on the shelf";
    }
}
