package org.data.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 自身具备监听和非监听两种能力
 * 1. 需要具备监听则使用nameProperty().bind()...
 * 2. 不需要监听则按照正常类进行name set get即可！
 *
 * 进一步分析 当调用nameProperty() 会创建SimpleStringProperty 这样就有了namePro
 * 2021-08-31 0:06   终于知道了各个控件调用  ***Property().函数的作！
 * 也就是调用nameProperty().addListener()...
 */
public class SimplePerson {
    /**
     * 单例模式
     */
    private String name ;
    private SimpleStringProperty namePro= null;

    public SimplePerson(String name) {
        if (namePro == null) {
            this.name = name;
        }else{
            this.namePro.set(name);
        }
    }

    /**
     * 当你需要监听的时候 即调用 对象.nameProperty().getName
     *  基本上大多数控件都有对应属性的Property()函数
     * @return
     */
    public StringProperty nameProperty(){
        /**
         * 只在第一次创建
         */
        if (namePro == null) {
            namePro = new SimpleStringProperty(this,"name",name);
        }
        return namePro;
    }

    public String getName() {
        if (namePro==null) {
            // 正常写法
            return this.name;
        }else{
            return this.namePro.get();
        }
    }

    public void setName(String name) {
        if (namePro == null) {
            this.name = name;
        }else {
            this.namePro.set(name);
        }
    }
}
