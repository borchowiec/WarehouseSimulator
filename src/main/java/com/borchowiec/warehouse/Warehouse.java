package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;

@Controller
public class Warehouse extends JPanel {
    @Autowired
    private WarehouseModel warehouseModel;
    @Autowired
    private WarehouseView warehouseView;
    @Autowired
    private InfoPanel infoPanel;

    private Object clickedObject;

    public Warehouse() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Transporter t : warehouseModel.TRANSPORTERS) {
                    if (t.isClicked(e.getPoint())) {
                        clickedObject = t;
                        return;
                    }
                }
                for (Shelf s : warehouseModel.SHELVES) {
                    if (s.isClicked(e.getPoint())) {
                        clickedObject = s;
                        return;
                    }
                }
                clickedObject = null;
            }
        });
    }

    @PostConstruct
    private void setDimensions() {
        Dimension d = new Dimension((int) (warehouseModel.WIDTH + infoPanel.WIDTH), warehouseModel.HEIGHT);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        warehouseView.paint(g2);
        if (clickedObject == null)
            infoPanel.paint(g2);
        else if (clickedObject instanceof Transporter)
            infoPanel.paint(g2, (Transporter) clickedObject);
        else if (clickedObject instanceof Shelf)
            infoPanel.paint(g2, (Shelf) clickedObject);
    }
}
