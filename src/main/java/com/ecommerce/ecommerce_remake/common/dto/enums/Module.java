package com.ecommerce.ecommerce_remake.common.dto.enums;

public enum Module {
//    user("userService", "user");
    store("storeService", "store");

    private final String beanName;
    private final String moduleName;

    public String getBeanName() {
        return this.beanName;
    }

    public String getModuleName() {
        return this.moduleName;
    }
    private Module(String beanName, String moduleName){
        this.beanName = beanName;
        this.moduleName = moduleName;
    }
}
