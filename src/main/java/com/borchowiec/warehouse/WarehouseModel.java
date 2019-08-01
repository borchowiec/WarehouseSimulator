package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class WarehouseModel {
    final int WIDTH;
    final int HEIGHT;
    final int TILE_SIZE;
    final Shelf[] SHELVES;

    WarehouseModel(@Value("${warehouse.width}") int width, @Value("${warehouse.height}") int height
            , @Value("${warehouse.tile.size}") int tileSize, @Autowired Shelf[] shelves) {
        WIDTH = width;
        HEIGHT = height;
        TILE_SIZE = tileSize;
        SHELVES = shelves;
    }
}
