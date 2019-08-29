package com.borchowiec;

import com.borchowiec.warehouse.Warehouse;
import com.borchowiec.warehouse.WarehouseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * This class starts application. Creates frame and sets the {@link Warehouse} panel.
 * @author Patryk Borchowiec
 */
@Controller
public class MainController {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Warehouse warehouse = context.getBean("warehouse", Warehouse.class);
        WarehouseModel warehouseModel = warehouse.getWarehouseModel();

        //creating main frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(warehouse);
        frame.setTitle("Warehouse");
        frame.setVisible(true);

        int width = (int) (warehouseModel.WIDTH + warehouse.getInfoPanel().WIDTH) +
                frame.getInsets().left + frame.getInsets().right;
        int height = warehouseModel.HEIGHT + frame.getInsets().top + frame.getInsets().bottom;
        frame.setSize(new Dimension(width ,height));
        frame.setLocationRelativeTo(null);

        //infinite loop that repaints application
        Timer timer = new Timer(10, e -> warehouse.repaint());
        timer.start();
    }
}
