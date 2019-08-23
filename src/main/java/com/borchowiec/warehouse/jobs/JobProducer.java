package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.borchowiec.warehouse.shelves.ShelfStatus.HAS_PRODUCT;
import static com.borchowiec.warehouse.shelves.ShelfStatus.WAITING_FOR_EXPORT;

public class JobProducer {
    public static Job getJob(Transporter transporter, WarehouseModel warehouseModel) {
        Job job;
        job = getExportJob(transporter, warehouseModel);

        if (job == null)
            job = getImportJob(transporter, warehouseModel);

        return job;
    }

    private static ExportJob getExportJob(Transporter transporter, WarehouseModel warehouseModel) {
        List<Shelf> potentialShelves = Arrays.stream(warehouseModel.SHELVES)
                .filter(shelf -> shelf.getStatus() == HAS_PRODUCT)
                .collect(Collectors.toList());

        System.out.println("    " + potentialShelves.size());

        if (potentialShelves.size() > 0) {
            Shelf shelf = potentialShelves.get(new Random().nextInt(potentialShelves.size()));
            shelf.setStatus(WAITING_FOR_EXPORT);

            return new ExportJob(transporter, warehouseModel, shelf);
        }

        return null;
    }

    private static Job getImportJob(Transporter transporter, WarehouseModel warehouseModel) {
        //todo import job
        System.out.println("import job");
        return null;
    }
}
