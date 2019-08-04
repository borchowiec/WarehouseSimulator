package com.borchowiec.warehouse.transporter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component("transportersList")
public class TransportersFactory implements FactoryBean<List<Transporter>>, ApplicationContextAware {

    private ApplicationContext context;
    @Value("${warehouse.tile.size}")
    private int tileSize;

    @Value("${warehouse.transporter.size}")
    int transportersSize;

    @Value("${warehouse.transporter.amount}")
    int amount;

    public List<Transporter> getObject() throws Exception {
        List<Transporter> list = new LinkedList<Transporter>();

        for (int i = 0; i < amount; i++) {
            Transporter transporter = context.getBean("transporter", Transporter.class);
            transporter.setX(tileSize * (i + 1));
            transporter.setY(tileSize + (tileSize - transportersSize) / 2.0);
            list.add(transporter);
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
