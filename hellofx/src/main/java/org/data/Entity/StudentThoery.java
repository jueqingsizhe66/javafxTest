package org.data.Entity;

import java.beans.PropertyChangeSupport;

public class StudentThoery {
    private String name;
    private int age;

    //观察者模式
    // this 把自己当做Property 监听器
    public PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public StudentThoery(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        int oldAge = this.age;
        int newAge = age;
        this.age = newAge;
        // 发出一个通知！！
        pcs.firePropertyChange("Special_age",oldAge,newAge);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        String newName = name;
        this.name = newName;
        // 发出一个通知！！
        pcs.firePropertyChange("Special_name",oldName,newName);
    }
}
