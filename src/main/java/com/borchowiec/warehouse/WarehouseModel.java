package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.shelves.Shelf;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
class WarehouseModel  implements ApplicationContextAware {
    final int WIDTH;
    final int HEIGHT;
    final int TILE_SIZE;
    final Shelf[] SHELVES;
    private ApplicationContext context;

    WarehouseModel(@Value("${warehouse.width}") int width, @Value("${warehouse.height}") int height
            , @Value("${warehouse.tile.size}") int tileSize, @Autowired Shelf[] shelves) {
        WIDTH = width;
        HEIGHT = height;
        TILE_SIZE = tileSize;
        SHELVES = shelves;
    }

    @PostConstruct
    public void fillRandomShelves() {
        Random rand = new Random();
        for (Shelf shelf : SHELVES)
            if (rand.nextBoolean())
                shelf.setProduct(context.getBean("product", Product.class));
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
