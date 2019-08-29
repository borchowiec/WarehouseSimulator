package com.borchowiec.warehouse.shelves;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * This class is represents product that can be store on shelves and transported by transporters.
 * @author Patryk Borchowiec
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Product {
    private final String name;
    private final Color color;

    private Color DETAILS_COLOR = new Color(102, 102, 102);
    private static final String[] ADJECTIVES = {
            "Long",
            "Great",
            "Little",
            "Hard",
            "Old",
            "New",
            "Special",
            "Strong",
            "Fancy",
            "Fabulous"
    };
    private static final String[] NOUNS = {
            "Drill",
            "Hammer",
            "Saw",
            "Tape",
            "Ladder",
            "Sander"
    };
    private static final Color[] COLORS = {
            new Color(153, 110, 83),
            new Color(228, 106, 83),
            new Color(0, 63, 81),
            new Color(116, 171, 80),
            new Color(153, 125, 171)
    };

    public final int SIZE;

    /**
     * Main constructor
     * @param size Size of the product's edge. The product is square.
     */
    public Product(@Value("${warehouse.product.size}") int size) {
        SIZE = size;

        Random rand = new Random();
        //generate random name
        name = ADJECTIVES[rand.nextInt(ADJECTIVES.length)] + " " + NOUNS[rand.nextInt(NOUNS.length)];
        //set random color
        color = COLORS[rand.nextInt(COLORS.length)];
    }

    /**
     * @return Name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * This method draws product in x and y position.
     * @param g Graphics that draws product.
     * @param x X coordinate where the product will be drawn.
     * @param y Y coordinate where the product will be drawn.
     */
    public void paint(Graphics2D g, double x, double y) {
        paint(g, x, y, 0);
    }

    /**
     * This method draws product in x and y position, at a specific angle
     * @param g Graphics that draws product.
     * @param x X coordinate where the product will be drawn.
     * @param y Y coordinate where the product will be drawn.
     * @param angle Product's drawing angle
     */
    public void paint(Graphics2D g, double x, double y, double angle) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), rect.getCenterX(), rect.getCenterY());

        g.setColor(color);
        g.fill(tx.createTransformedShape(rect));

        rect = new Rectangle2D.Double(x, y, SIZE - 1, SIZE - 1);
        g.setColor(DETAILS_COLOR);
        g.draw(tx.createTransformedShape(rect));
    }
}
