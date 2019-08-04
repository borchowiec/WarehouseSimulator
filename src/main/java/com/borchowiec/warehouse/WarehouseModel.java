package com.borchowiec.warehouse;

import com.borchowiec.warehouse.shelves.Product;
import com.borchowiec.warehouse.shelves.Shelf;
import com.borchowiec.warehouse.transporter.Transporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
class WarehouseModel  implements ApplicationContextAware {
    final int WIDTH;
    final int HEIGHT;
    final int TILE_SIZE;
    final Shelf[] SHELVES;
    final Transporter[] TRANSPORTERS;
    private ApplicationContext context;

    WarehouseModel(@Value("${warehouse.width}") int width, @Value("${warehouse.height}") int height,
                   @Value("${warehouse.tile.size}") int tileSize, @Autowired Shelf[] shelves,
                   @Autowired @Qualifier("transportersList") List<Transporter> transportersList) {
        WIDTH = width;
        HEIGHT = height;
        TILE_SIZE = tileSize;
        SHELVES = shelves;

        TRANSPORTERS = new Transporter[transportersList.size()];
        int i = 0;
        for (Transporter t : transportersList)
            TRANSPORTERS[i++] = t;
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
