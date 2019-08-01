package com.borchowiec.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Controller
public class Warehouse extends JPanel {
    @Autowired
    private WarehouseModel warehouseModel;
    @Autowired
    private WarehouseView warehouseView;

    @PostConstruct
    private void setDimensions() {
        Dimension d = new Dimension(warehouseModel.WIDTH, warehouseModel.HEIGHT);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        warehouseView.paint(g2);
    }
}
