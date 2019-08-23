package com.borchowiec.warehouse.jobs.tasks;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import static com.borchowiec.warehouse.shelves.ShelfStatus.EMPTY;

public class TakeProductOfTheShelfTask extends Task {
    private Shelf shelf;
    private int stage = 0;

    public TakeProductOfTheShelfTask(Transporter transporter, Shelf shelf) {
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
                Product product = shelf.getProduct();
                shelf.setProduct(null);
                shelf.setStatus(EMPTY);
                transporter.attachToTheArm(product);
                stage++;
                break;
            case 2:
                if (transporter.propoundTheArm(0))
                    return true;
        }
        return false;
    }
}
