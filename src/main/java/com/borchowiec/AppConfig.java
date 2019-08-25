package com.borchowiec;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.borchowiec.warehouse"})
@PropertySource(value = "settings.properties")
public class AppConfig {

}