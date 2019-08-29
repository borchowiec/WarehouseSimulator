package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class is responsible for displaying information about transporters, shelves etc. Information displays on the
 * right side of window.
 * @author Patryk Borchowiec
 */
@Component
public class InfoPanel {
    private final double X;
    private final double Y;
    public final double WIDTH;
    final double HEIGHT;
    private final Rectangle2D BG_RECT;

    private final Color BG_COLOR = new Color(44, 125, 160);
    private final Color FONT_COLOR = new Color(246, 246, 246);
    private final int FONT_SIZE = 10;
    private final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, FONT_SIZE);
    private final Font FONT_PLAIN = new Font("SansSerif", Font.PLAIN, FONT_SIZE);

    /**
     * Main constructor
     * @param x X position of panel. Should be end of the warehouse (size of warehouse).
     * @param y Y position of panel. Should be top of the screen (0).
     * @param WIDTH Width of panel.
     * @param HEIGHT Height of panel. Should be same as height of warehouse.
     */
    public InfoPanel(@Value("${warehouse.width}") double x,
                     @Value("0") double y,
                     @Value("${infopanel.width}") double WIDTH,
                     @Value("${warehouse.height}") double HEIGHT) {
        X = x;
        Y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        BG_RECT = new Rectangle2D.Double(X, Y, WIDTH, HEIGHT);
    }

    /**
     * This method paints empty info panel.
     * @param g Graphic that draws panel.
     */
    public void paint(Graphics2D g) {
        g.setColor(BG_COLOR);
        g.fill(BG_RECT);
    }

    /**
     * This method paints info panel that contains information about given transporter.
     * @param g Graphic that draws panel.
     * @param transporter Transporter whose information will be displayed on the info panel.
     */
    public void paint(Graphics2D g, Transporter transporter) {
        paint(g);
        double x = X + (WIDTH - transporter.SIZE) / 2;
        double y = (WIDTH - transporter.SIZE) / 2;
        transporter.paint(g, x, y);

        drawProperty(g,300, 35, "ID", transporter.ID + "");
        drawProperty(g,310, 35, "Status", transporter.getStatus());
        drawProperty(g,325, 60, "X", transporter.getX() + "");
        drawProperty(g,335, 60, "Y", transporter.getY() + "");
        drawProperty(g,345, 60, "Rotation", transporter.getRotation() + "");
        drawProperty(g,360, 60, "Arm length", Math.abs(transporter.getArmLength()) + "");

        Product product = transporter.getProduct();
        if (product != null)
            drawProperty(g,370, 60, "Product", product.getName());

    }

    /**
     * This method paints info panel that contains information about given shelf.
     * @param g Graphic that draws panel.
     * @param shelf Shelf whose information will be displayed on the info panel.
     */
    public void paint(Graphics2D g, Shelf shelf) {
        paint(g);
        double x = X + (WIDTH - shelf.TILE_SIZE) / 2;
        double y = (WIDTH - shelf.TILE_SIZE) / 2;
        shelf.paint(g, (int) x, (int) y);

        drawProperty(g,300, 35, "ID", shelf.ID + "");
        drawProperty(g,310, 35, "Status", shelf.getStatus().getDisplayName());
        drawProperty(g,325, 60, "Column", shelf.getX() + "");
        drawProperty(g,335, 60, "Row", (int) ((shelf.getY() + 3) / 2.0) + "");

        Product product = shelf.getProduct();
        if (product != null)
            drawProperty(g,350, 60, "Product", product.getName());
    }

    /**
     * This method draws property in specific pattern:
     * <code>property</code>: (space) <code>value</code>
     * @param g Graphic that draws property.
     * @param y Position y where the property will be drawn.
     * @param space Space between property and value.
     * @param property Title of property.
     * @param value Value of property.
     */
    private void drawProperty(Graphics2D g, int y, int space, String property, String value) {
        g.setColor(FONT_COLOR);
        g.setFont(FONT_BOLD);
        g.drawString(property + ":", (int) X + 4, y);
        g.setFont(FONT_PLAIN);
        g.drawString(value, (int) X + 4 + space, y);
    }
}