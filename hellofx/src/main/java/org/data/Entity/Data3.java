package org.data.Entity;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * @author Zhaoliang Ye 叶昭良(zl_ye@qny.chng.com.cn)
 * @version V0.1
 * @Title: Data3.java
 * @Description: 用于测试TreeTableView
 * @Package org.data.Entity
 * @Time: 2021-09-19 17:34
 */
public class Data3 {
    private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
    private ReadOnlyIntegerWrapper age = new ReadOnlyIntegerWrapper();
    private ReadOnlyBooleanWrapper sex = new ReadOnlyBooleanWrapper();

    public Data3(String yz, int i, boolean b) {
        name.set(yz);
        age.set(i);
        sex.set(b);
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringWrapper nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public ReadOnlyIntegerWrapper ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean isSex() {
        return sex.get();
    }

    public ReadOnlyBooleanWrapper sexProperty() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex.set(sex);
    }

}
