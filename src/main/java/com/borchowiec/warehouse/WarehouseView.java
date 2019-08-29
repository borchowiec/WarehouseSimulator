package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class is responsible for drawing warehouse and warehouse's elements.
 * @author Patryk Borchowiec
 */
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

    /**
     * This method paints warehouse and warehouse's elements.
     * @param g Graphic that paints warehouse.
     */
    public void paint(Graphics2D g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, warehouseModel.WIDTH, warehouseModel.HEIGHT);

        for (Shelf shelf : warehouseModel.SHELVES)
            shelf.paint(g);

        for (Transporter t : warehouseModel.TRANSPORTERS)
            t.paint(g);

        paintSpot(g, EXPORT_COLOR, warehouseModel.EXPORT_SPOT, "EXPORT");
        paintSpot(g, IMPORT_COLOR, warehouseModel.IMPORT_SPOT, "IMPORT");

        if (devView) {
            g.setColor(TILE_EDGE);
            for (int x = 0; x < warehouseModel.WIDTH; x += warehouseModel.TILE_SIZE) {
                for (int y = 0; y < warehouseModel.HEIGHT; y += warehouseModel.TILE_SIZE) {
                    g.drawRect(x, y, warehouseModel.TILE_SIZE - 1, warehouseModel.TILE_SIZE - 1);
                }
            }

            g.setColor(Color.WHITE);
            for (Transporter t : warehouseModel.TRANSPORTERS)
                g.draw(t.getDetector());
        }
    }

    /**
     * This method paints spots such as export spot or import spot.
     * @param g Graphic that draws spot.
     * @param color Color of spot.
     * @param rect Rectangle that has sizes and position of spot.
     * @param label Label that will be displayed on spot.
     */
    private void paintSpot(Graphics2D g, Color color, Rectangle2D rect, String label) {
        double border = 5;

        g.setColor(color);
        g.fill(rect);

        g.setColor(color.darker());
        g.fill(new Rectangle2D.Double(rect.getX() + border, rect.getY() + border,
                rect.getWidth() - 2 * border, rect.getHeight() - 2 * border));

        g.setColor(color);
        g.drawString(label, (int) (rect.getX() + border + 1), (int) (rect.getY() + border + 11));
    }
}
