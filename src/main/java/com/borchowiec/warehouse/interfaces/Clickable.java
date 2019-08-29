package com.borchowiec.warehouse.interfaces;

import java.awt.geom.Point2D;

/**
 * This interface describes objects that can be clicked by mouse.
 * @author Patryk Borchowiec
 */
public interface Clickable {
    /**
     * This method checks if click position is inside of object. That is, if the object was clicked, returns true.
     * @param clickPosition Position of click.
     * @return True if the object was clicked.
     */
    boolean isClicked(Point2D clickPosition);
}
