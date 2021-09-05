package org.data.Entity;

import javafx.beans.property.SimpleObjectProperty;

public class BindingSelectStringStudent {

    // 注意在module-info.java 需要opens org.data.Entity 把这个包打开给org.openjfx包使用
    private BindingSelectStringName name = new BindingSelectStringName();
    private SimpleObjectProperty<BindingSelectStringName> sop = new SimpleObjectProperty<BindingSelectStringName>(new BindingSelectStringName());


    public void setName(String name) {
        this.name.setName(name);
        // 因为sop已经和name绑定在一起，所以不需要进行 sop.set
//        this.sop.set(this.name);
    }
    //构造函数

    /**
     * 为什么那么麻烦 ，不明白！
     * @param name
     */
    public BindingSelectStringStudent(String name){
        this.name.setName(name);
        this.sop.set(this.name);

    }
    public BindingSelectStringName getSop() {
        return sop.get();
    }

    // 潜规则生成方式 : 固定写法!  字段反射过程
    public SimpleObjectProperty<BindingSelectStringName> sopProperty() {
        System.out.println("-----------------BindingSelectStringStudent SimpleObjectProperty----------");
        return sop;
    }
}
