package com.ecommerce.ecommerce_remake.common.factory;

import com.ecommerce.ecommerce_remake.common.dto.enums.Module;
import com.ecommerce.ecommerce_remake.common.service.CrudService;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

public class CrudServiceFactory {

    private final ApplicationContext applicationContext;

    public CrudServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public CrudService getService(Module module){
        String beanName = Arrays.stream(Module.values())
                .filter(mod -> mod.equals(module))
                .iterator()
                .next()
                .getBeanName();

        return (CrudService) applicationContext.getBean(beanName);
    }

}
