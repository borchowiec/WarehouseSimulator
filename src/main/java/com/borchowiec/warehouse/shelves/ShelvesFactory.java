package com.borchowiec.warehouse.shelves;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component("shelvesList")
public class ShelvesFactory implements FactoryBean<List<Shelf>>, ApplicationContextAware {
    private ApplicationContext context;

    @Value("${warehouse.shelves.cols}")
    int cols;

    @Value("${warehouse.shelves.rows}")
    int rows;

    public List<Shelf> getObject() throws Exception {
        List<Shelf> list = new LinkedList<Shelf>();
        Random rand = new Random();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Shelf shelf = context.getBean("shelf", Shelf.class);
                shelf.setX(x + 1);
                shelf.setY(y * 2);
                if (rand.nextBoolean()) {
                    shelf.setProduct(context.getBean("product", Product.class));
                    shelf.setStatus(ShelfStatus.HAS_PRODUCT);
                }
                list.add(shelf);
            }
        }
        return list;
    }

    public Class<?> getObjectType() {
        return List.class;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
