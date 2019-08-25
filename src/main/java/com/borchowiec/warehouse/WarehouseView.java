package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
class WarehouseView {
    @Autowired
    private WarehouseModel warehouseModel;

    @Value("${dev.isVisible}")
    private boolean devView;

    private final Color BG_COLOR = new Color(65, 181, 230);
    private final Color TILE_EDGE = new Color(153, 1, 0);
    private final Color EXPORT_COLOR = new Color(255, 21, 82);
    private final Color IMPORT_COLOR = new Color(76, 214, 33);

    public void paint(Graphics2D g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, warehouseModel.WIDTH, warehouseModel.HEIGHT);

        for (Shelf shelf : warehouseModel.SHELVES)
            shelf.paint(g);

        for (Transporter t : warehouseModel.TRANSPORTERS)
            t.paint(g);

        g.setColor(EXPORT_COLOR);
        g.fill(warehouseModel.EXPORT_SPOT);

        g.setColor(IMPORT_COLOR);
        g.fill(warehouseModel.IMPORT_SPOT);

        if (devView) {
            g.setColor(TILE_EDGE);
            for (int x = 0; x < warehouseModel.WIDTH; x += warehouseModel.TILE_SIZE) {
                for (int y = 0; y < warehouseModel.HEIGHT; y += warehouseModel.TILE_SIZE) {
                    g.drawRect(x, y, warehouseModel.TILE_SIZE - 1, warehouseModel.TILE_SIZE - 1);
                }
            }
        }
    }
}
