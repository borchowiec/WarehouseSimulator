package com.borchowiec.warehouse.shelves;

import java.awt.*;

public class Shelf {
    private final int X;
    private final int Y;
    private final int TILE_SIZE;
    private final int ID;

    private final Font FONT = new Font("SansSerif", Font.PLAIN, 13);
    private final Color BG_COLOR = new Color(255, 199, 44);
    private final Color EDGE_COLOR = new Color(174, 132, 32);
    private final Color FONT_COLOR = new Color(255, 232, 101);
    private final Color LABEL_COLOR = new Color(229, 176, 41);
    private final int EDGE_WIDTH = 5;

    private Product product;

    public Shelf(int x, int y, int tileSize, int id) {
        X = x;
        Y = y;
        TILE_SIZE = tileSize;
        ID = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}
