package com.borchowiec;

import com.borchowiec.warehouse.Warehouse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * This class starts application. Creates frame and sets the {@link Warehouse} panel.
 * @author Patryk Borchowiec
 */
public class MainController {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        final Warehouse warehouse = context.getBean("warehouse", Warehouse.class);

        //creating main frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(warehouse);
        frame.setTitle("Warehouse");
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //infinite loop that repaints application
        Timer timer = new Timer(10, e -> warehouse.repaint());
        timer.start();
    }
}
