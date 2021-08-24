package org.data.Entity;

import javafx.beans.property.SimpleStringProperty;

public class BindingSelectStringName {

    public void setName(String name) {
    this.name.set(name);
    }

    private SimpleStringProperty name = new SimpleStringProperty("A");


    public String getName() {
        return name.get();
    }

    // 潜规则生成方式 : 固定写法  字段反射
    public SimpleStringProperty nameProperty() {
        System.out.println("-----------------BindingSelectStringName SimpleObjectProperty----------");
        return name;
    }
}
