package com.borchowiec.warehouse.transporter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Transporter {
    private volatile double x;
    private volatile double y;
    private final double SIZE;

    private Color BG_COLOR = new Color(217, 159, 46);
    private Color BORDER_COLOR = new Color(255, 199, 44);
    private Color DETAILS_COLOR = new Color(153, 1, 0);

    private final int BORDER_WIDTH = 4;

    public Transporter(@Value("${warehouse.transporter.size}") int transportersSize) {
        SIZE = transportersSize;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void Paint(Graphics2D g) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
        Line2D line = new Line2D.Double(x + 2, y - 1, x + SIZE - 3, y - 1);

        g.setColor(BORDER_COLOR);
        g.fill(rect);

        g.setColor(BG_COLOR);
        rect.setRect(rect.getMinX() + BORDER_WIDTH / 2.0, rect.getMinY() + BORDER_WIDTH / 2.0,
                SIZE - BORDER_WIDTH, SIZE - BORDER_WIDTH);
        g.fill(rect);

        g.setColor(DETAILS_COLOR);
        g.draw(line);
    }
}
