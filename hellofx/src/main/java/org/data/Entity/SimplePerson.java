package org.data.Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
