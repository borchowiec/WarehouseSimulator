package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class WarehouseModel {
    final int WIDTH;
    final int HEIGHT;
    final int TILE_SIZE;
    final Shelf[] SHELVES;
    final Transporter[] TRANSPORTERS;

    WarehouseModel(@Value("${warehouse.width}") int width, @Value("${warehouse.height}") int height,
                   @Value("${warehouse.tile.size}") int tileSize,
                   @Autowired @Qualifier("shelvesList") List<Shelf> shelvesList,
                   @Autowired @Qualifier("transportersList") List<Transporter> transportersList) {
        WIDTH = width;
        HEIGHT = height;
        TILE_SIZE = tileSize;

        int i = 0;
        SHELVES = new Shelf[shelvesList.size()];
        for (Shelf s : shelvesList)
            SHELVES[i++] = s;

        TRANSPORTERS = new Transporter[transportersList.size()];
        i = 0;
        for (Transporter t : transportersList)
            TRANSPORTERS[i++] = t;
    }
}
