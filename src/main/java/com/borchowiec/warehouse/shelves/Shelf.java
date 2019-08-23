package com.borchowiec.warehouse.shelves;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;

import static com.borchowiec.warehouse.shelves.ShelfStatus.*;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Shelf {
    private int X;
    private int Y;
    private final int TILE_SIZE;
    public final int ID;

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
        g.setColor(EDGE_COLOR);
        g.fillRect(X * TILE_SIZE, Y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.setColor(BG_COLOR);
        g.fillRect(X * TILE_SIZE + EDGE_WIDTH, Y * TILE_SIZE + EDGE_WIDTH,
                TILE_SIZE - 2 * EDGE_WIDTH, TILE_SIZE - EDGE_WIDTH);

        if (product != null) {
            int space = (TILE_SIZE - product.SIZE) / 2;
            product.paint(g, X * TILE_SIZE + space, Y * TILE_SIZE + space);
        }

        g.setColor(LABEL_COLOR);
        g.fillRect(X * TILE_SIZE + EDGE_WIDTH, Y * TILE_SIZE + EDGE_WIDTH, 15, 15);
        g.setColor(FONT_COLOR);
        g.setFont(FONT);
        g.drawString(String.valueOf(ID), X * TILE_SIZE + EDGE_WIDTH, Y * TILE_SIZE + EDGE_WIDTH + 11);
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
