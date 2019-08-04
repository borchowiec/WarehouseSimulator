package com.borchowiec;

import com.borchowiec.warehouse.shelves.Shelf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.borchowiec.warehouse"})
@PropertySource(value = "settings.properties")
public class AppConfig {

    @Bean
    public Shelf[] shelves(@Value("${warehouse.tile.size}") final int TILE_SIZE,
                           @Value("${warehouse.shelves.cols}") final int COLS,
                           @Value("${warehouse.shelves.rows}") final int ROWS) {

        Shelf[] shelves = new Shelf[COLS * ROWS];
        for (int y = 0; y < ROWS; y++)
            for (int x = 0; x < COLS; x++)
                shelves[y * COLS + x] = new Shelf(x + 1, y * 2, TILE_SIZE, y * COLS + x);
        return shelves;
    }
}