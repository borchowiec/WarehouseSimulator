package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.jobs.tasks.*;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import java.awt.geom.Rectangle2D;

public class ExportJob extends Job {

    public ExportJob(Transporter transporter, WarehouseModel warehouseModel, Shelf shelf) {
        super(transporter, warehouseModel);

        //todo check position of transporter and then set tasks

        System.out.println(shelf.ID);

        double x;
        double y = (warehouseModel.ROWS * 2) * warehouseModel.TILE_SIZE + warehouseModel.TILE_SIZE / 2.0;;
        if (transporter.getCenterY() < warehouseModel.HEIGHT - warehouseModel.TILE_SIZE) {
            x = warehouseModel.TILE_SIZE / 2.0;
            tasks.add(new GoHorizontallyTask(transporter, x));

            tasks.add(new GoToTask(x, y, transporter));
        }

        x = (warehouseModel.COLS + 1) * warehouseModel.TILE_SIZE +  warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        y = (shelf.getY() + 1) * warehouseModel.TILE_SIZE + warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        x = shelf.getX() * warehouseModel.TILE_SIZE + warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        tasks.add(new TakeProductOfTheShelfTask(transporter, shelf));

        x = warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoHorizontallyTask(transporter, x));

        y = warehouseModel.HEIGHT - warehouseModel.TILE_SIZE / 2.0;
        tasks.add(new GoToTask(x, y, transporter));

        Rectangle2D exportSpot = warehouseModel.EXPORT_SPOT;
        tasks.add(new GoToTask(exportSpot.getCenterX(), exportSpot.getCenterY(), transporter));

        tasks.add(new DetachProductTask(transporter));

        tasks.add(new GoHorizontallyTask(transporter, warehouseModel.WIDTH - warehouseModel.TILE_SIZE / 2));
    }

    public boolean doJob() {
        if (tasks.isEmpty()) {
            System.out.println(transporter + " finished job");
            return true;
        }

        Task task = tasks.peek();
        boolean done = task.doTask();
        if (done) {
            tasks.remove();
            System.out.println(transporter + " finished task");
        }
        return false;
    }
}
