package com.borchowiec.warehouse.shelves;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Product {
    private final String name;
    private final Color color;
    private final Font FONT = new Font("SansSerif", Font.BOLD, 7);

    public static final String[] ADJECTIVES = {
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

    public static final String[] NOUNS = {
            "Drill",
            "Hammer",
            "Saw",
            "Tape",
            "Ladder",
            "Sander"
    };

    public static final Color[] COLORS = {
            new Color(153, 110, 83),
            new Color(228, 106, 83),
            new Color(0, 63, 81),
            new Color(116, 171, 80),
            new Color(153, 125, 171)
    };

    private Color DETAILS_COLOR = new Color(102, 102, 102);

    public final int SIZE;

    public Product(@Value("${warehouse.product.size}") int size) {
        SIZE = size;
        Random rand = new Random();
        name = ADJECTIVES[rand.nextInt(ADJECTIVES.length)] + " " + NOUNS[rand.nextInt(NOUNS.length)];
        color = COLORS[rand.nextInt(COLORS.length)];
    }

    public String getName() {
        return name;
    }

    public void paint(Graphics2D g, double x, double y) {
        paint(g, x, y, 0);
    }

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
