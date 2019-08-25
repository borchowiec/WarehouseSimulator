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
    private final int EDGE_WIDTH = 5;

    private Product product;
    private ShelfStatus status;

    public Shelf(@Value("${warehouse.tile.size}") int tileSize) {
        status = EMPTY;
        TILE_SIZE = tileSize;
        ID = nextId++;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public double getCenterY() {
        return (Y + 0.5) * TILE_SIZE;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShelfStatus getStatus() {
        return status;
    }

    public void setStatus(ShelfStatus status) {
        this.status = status;
    }

    public void paint(Graphics2D g) {
        paint(g, X * TILE_SIZE, Y * TILE_SIZE);
    }

    public void paint(Graphics2D g, int x, int y) {
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
