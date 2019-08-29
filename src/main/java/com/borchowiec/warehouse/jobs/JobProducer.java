package com.borchowiec.warehouse.jobs;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.borchowiec.warehouse.jobs.JobType.EXPORT;
import static com.borchowiec.warehouse.jobs.JobType.IMPORT;
import static com.borchowiec.warehouse.shelves.ShelfStatus.*;

/**
 * This class is responsible for creating jobs for transporters.
 * @author Patryk Borchowiec
 */
public class JobProducer {
    private static volatile JobType previousJob = IMPORT;

    /**
     * This method creates job for transporter.
     * @param transporter Transporter that will be doing this job.
     * @param warehouseModel Current warehouse model.
     * @return New job.
     */
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

    /**
     * This method creates job which consists in exporting product from warehouse.
     * @param transporter Transporter that will export the product.
     * @param warehouseModel Current warehouse model.
     * @return New export job or null if there is no products to export.
     */
    private static ExportJob getExportJob(Transporter transporter, WarehouseModel warehouseModel) {
        //shelves that has products to export
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

    /**
     * This method creates job which consists in importing product to the closest, free shelf.
     * @param transporter Transporter that will import the product.
     * @param warehouseModel Current warehouse model.
     * @return New import job or null if there is no free shelves.
     */
    private static Job getImportJob(Transporter transporter, WarehouseModel warehouseModel) {
        //free shelves
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
