package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.jobs.tasks.JobType;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.borchowiec.warehouse.jobs.tasks.JobType.EXPORT;
import static com.borchowiec.warehouse.jobs.tasks.JobType.IMPORT;
import static com.borchowiec.warehouse.shelves.ShelfStatus.*;

public class JobProducer {
    private static volatile JobType previousJob = IMPORT;

    public static synchronized Job getJob(Transporter transporter, WarehouseModel warehouseModel) {
        Job job;
        if (previousJob == IMPORT)
            job = getExportJob(transporter, warehouseModel);
        else
            job = getImportJob(transporter, warehouseModel);

        if (job == null)
            job = getImportJob(transporter, warehouseModel);

        return job;
    }

    private static ExportJob getExportJob(Transporter transporter, WarehouseModel warehouseModel) {
        List<Shelf> potentialShelves = Arrays.stream(warehouseModel.SHELVES)
                .filter(shelf -> shelf.getStatus() == HAS_PRODUCT)
                .collect(Collectors.toList());

        if (potentialShelves.size() > 0) {
            Shelf shelf = potentialShelves.get(new Random().nextInt(potentialShelves.size()));
            shelf.setStatus(WAITING_FOR_EXPORT);

            previousJob = EXPORT;
            return new ExportJob(transporter, warehouseModel, shelf);
        }

        return null;
    }

    private static Job getImportJob(Transporter transporter, WarehouseModel warehouseModel) {
        List<Shelf> potentialShelves = Arrays.stream(warehouseModel.SHELVES)
                .filter(shelf -> shelf.getStatus() == EMPTY)
                .collect(Collectors.toList());

        if (potentialShelves.size() > 0) {
            Shelf shelf = potentialShelves.get(potentialShelves.size() - 1);
            shelf.setStatus(WAITING_FOR_IMPORT);

            previousJob = IMPORT;
            return new ImportJob(transporter, warehouseModel, shelf);
        }

        return null;
    }
}
