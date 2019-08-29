package com.borchowiec.warehouse.shelves;

import com.borchowiec.warehouse.interfaces.Clickable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static com.borchowiec.warehouse.shelves.ShelfStatus.*;

/**
 * This class represents shelf that can store products.
 * @author Patryk Borchowiec
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Shelf implements Clickable {

    public final int TILE_SIZE;
    public final int ID;
    private int X;
    private int Y;

    private static int nextId = 0;

    private final Font FONT = new Font("SansSerif", Font.PLAIN, 13);
    private final Color BG_COLOR = new Color(255, 199, 44);
    private final Color EDGE_COLOR = new Color(174, 132, 32);
    private final Color FONT_COLOR = new Color(255, 232, 101);
    private final Color LABEL_COLOR = new Color(229, 176, 41);

    private Product product;
    private ShelfStatus status;

    /**
     * Main constructor
     * @param tileSize Size of the tiles. Shelf has same size as size of tile.
     */
    public Shelf(@Value("${warehouse.tile.size}") int tileSize) {
        status = EMPTY;
        TILE_SIZE = tileSize;
        ID = nextId++;
    }

    /**
     * This method sets x tile of warehouse. It's not coordinate!. Whole warehouse is divided on tiles.
     * @param x X tile of warehouse.
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * This method sets y tile of warehouse. It's not coordinate!. Whole warehouse is divided on tiles.
     * @param y Y tile of warehouse.
     */
    public void setY(int y) {
        Y = y;
    }

    /**
     * This method returns x tile of warehouse. It's not coordinate!. Whole warehouse is divided on tiles.
     * @return x tile of warehouse.
     */
    public int getX() {
        return X;
    }

    /**
     * This method returns x tile of warehouse. It's not coordinate!. Whole warehouse is divided on tiles.
     * @return y tile of warehouse.
     */
    public int getY() {
        return Y;
    }

    /**
     * This method returns the center y COORDINATE. It's coordinate, not tile.
     * @return the center y coordinate.
     */
    public double getCenterY() {
        return (Y + 0.5) * TILE_SIZE;
    }

    /**
     * @return Product that is on shelf. If there is no product, returns null.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set product on the shelf.
     * @param product The product that will be stored on shelf.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return Shelf's status.
     */
    public ShelfStatus getStatus() {
        return status;
    }

    /**
     * This method sets status of shelf.
     * @param status Status of shelf.
     */
    public void setStatus(ShelfStatus status) {
        this.status = status;
    }

    /**
     * This method draws shelf.
     * @param g Graphic that draws the shelf.
     */
    public void paint(Graphics2D g) {
        paint(g, X * TILE_SIZE, Y * TILE_SIZE);
    }

    /**
     * This method draws shelf at specific coordinates.
     * @param g Graphic that draws the shelf.
     * @param x X coordinate where the shelf will be drawn.
     * @param y Y coordinate where the shelf will be drawn.
     */
    public void paint(Graphics2D g, int x, int y) {
        int EDGE_WIDTH = 5;

        g.setColor(EDGE_COLOR);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        g.setColor(BG_COLOR);
        g.fillRect(x + EDGE_WIDTH, y + EDGE_WIDTH,
                TILE_SIZE - 2 * EDGE_WIDTH, TILE_SIZE - EDGE_WIDTH);

        if (product != null) {
            int space = (TILE_SIZE - product.SIZE) / 2;
            product.paint(g, x + space, y + space);
        }

        g.setColor(LABEL_COLOR);
        g.fillRect(x + EDGE_WIDTH, y + EDGE_WIDTH, 15, 15);
        g.setColor(FONT_COLOR);
        g.setFont(FONT);
        g.drawString(String.valueOf(ID), x + EDGE_WIDTH, y + EDGE_WIDTH + 11);
    }

    @Override
    public boolean isClicked(Point2D clickPosition) {
        Rectangle2D rect = new Rectangle2D.Double(X * TILE_SIZE, Y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        return rect.contains(clickPosition);
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "ID=" + ID +
                ", product=" + product +
                ", status=" + status +
                '}';
    }
}
