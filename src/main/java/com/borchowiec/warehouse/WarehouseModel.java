package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * This class contains information about warehouse such as sizes, transporters, shelves etc.
 * @author Patryk Borchowiec
 */
@Service
public
class WarehouseModel {
    public final int WIDTH;
    public final int HEIGHT;
    public final int TILE_SIZE;
    public final Shelf[] SHELVES;
    public final int ROWS;
    public final int COLS;
    public final Transporter[] TRANSPORTERS;
    public final Rectangle2D EXPORT_SPOT;
    public final Rectangle2D IMPORT_SPOT;

    /**
     * @param width Width of the warehouse.
     * @param height Height of the warehouse.
     * @param tileSize Size of the tile. Whole warehouse is divided on tiles. You can see it if you set devView property
     *                 on true in settings.properties file.
     * @param shelvesList List that contains shelves. You can get it from
     *                 {@link com.borchowiec.warehouse.shelves.ShelvesFactory shelves factory}.
     * @param rows Count of tile's rows.
     * @param cols Count of tile's columns.
     * @param transportersList List that contains transporters.
     * @param exportCol Tile's column of spot where the products are exported.
     * @param importCol Tile's column of spot where the products are imported.
     */
    WarehouseModel(@Value("${warehouse.width}") int width, @Value("${warehouse.height}") int height,
                   @Value("${warehouse.tile.size}") int tileSize,
                   @Autowired @Qualifier("shelvesList") List<Shelf> shelvesList,
                   @Value("${warehouse.shelves.rows}") int rows,
                   @Value("${warehouse.shelves.cols}") int cols,
                   @Autowired @Qualifier("transportersList") List<Transporter> transportersList,
                   @Value("${warehouse.exportSpot.col}") int exportCol,
                   @Value("${warehouse.importSpot.col}") int importCol) {
        WIDTH = width;
        HEIGHT = height;
        TILE_SIZE = tileSize;
        ROWS = rows;
        COLS = cols;

        EXPORT_SPOT = new Rectangle2D.Double(exportCol * tileSize, height - tileSize, tileSize, tileSize);
        IMPORT_SPOT = new Rectangle2D.Double(importCol * tileSize, height - tileSize, tileSize, tileSize);

        int i = 0;
        SHELVES = new Shelf[shelvesList.size()];
        for (Shelf s : shelvesList)
            SHELVES[i++] = s;

        TRANSPORTERS = new Transporter[transportersList.size()];
        i = 0;
        for (Transporter t : transportersList) {
            TRANSPORTERS[i++] = t;
            t.setWarehouseModel(this);
            t.start();
        }
    }
}