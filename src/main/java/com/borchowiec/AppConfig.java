package com.borchowiec;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This class configure app. It's application's context.
 * @author Patryk Borchowiec
 */
@Configuration
@ComponentScan(basePackages = {"com.borchowiec.warehouse"})
@PropertySource(value = "settings.properties")
public class AppConfig {
}