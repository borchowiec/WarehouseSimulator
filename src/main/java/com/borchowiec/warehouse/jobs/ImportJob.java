package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.jobs.tasks.*;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.context.ApplicationContext;

public class ImportJob extends Job {

    public ImportJob(Transporter transporter, WarehouseModel warehouseModel, Shelf shelf) {
        super(transporter, warehouseModel);

        double x;
        double y;

        if (transporter.getCenterY() >= warehouseModel.HEIGHT - warehouseModel.TILE_SIZE) {
            x = warehouseModel.WIDTH - warehouseModel.TILE_SIZE / 2.0;
            y = warehouseModel.HEIGHT - warehouseModel.TILE_SIZE * 2.5;
            tasks.add(new GoToTask(x, y, transporter));
        }

        x = warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoHorizontallyTask(transporter, x));

        y = warehouseModel.IMPORT_SPOT.getCenterY();
        tasks.add(new GoToTask(x, y, transporter));

        x = warehouseModel.IMPORT_SPOT.getCenterX();
        tasks.add(new GoToTask(x, y, transporter));

        tasks.add(new TakeProductFromImportSpotTask(transporter));

        x = warehouseModel.WIDTH - warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoHorizontallyTask(transporter, x));

        y = (shelf.getY() + 1) * warehouseModel.TILE_SIZE + warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        x = shelf.getX() * warehouseModel.TILE_SIZE + warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        tasks.add(new LeaveProductOnShelfTask(transporter, shelf));
    }
}
