package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import static com.borchowiec.warehouse.shelves.ShelfStatus.HAS_PRODUCT;

public class LeaveProductOnShelfTask extends Task {
    private Shelf shelf;
    private int stage = 0;

    public LeaveProductOnShelfTask(Transporter transporter, Shelf shelf) {
        super(transporter);
        this.shelf = shelf;
    }

    @Override
    public boolean doTask() {
        switch (stage) {
            case 0:
                double lengthOfArm = shelf.getCenterY() - transporter.getCenterY();
                if (transporter.propoundTheArm(lengthOfArm))
                    stage++;
                break;
            case 1:
                shelf.setProduct(transporter.detachProduct());
                shelf.setStatus(HAS_PRODUCT);
                stage++;
                break;
            case 2:
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
