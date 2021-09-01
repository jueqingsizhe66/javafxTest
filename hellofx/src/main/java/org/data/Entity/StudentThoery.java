package org.data.Entity;

import java.beans.PropertyChangeSupport;

public class StudentThoery {
    private String name;
    private int age;

    //观察者模式  2021-08-30 22:07
    // 原理就是当 Student执行 set命令  那么其内部的set会猝发firePropertyChange事件
    // 然后该事件会被s的内部变量pcs捕捉到， 因为Student内部的pcs内置了 PropertyChangeListener监听器
    // 这个监听器可以未每个属性字段设置标签，比如setNamePro  setAgePro对应都有自己的PropertyChange的处理方法
    // 该监听器可以捕捉到Property的改变
    /**
     *  map维护着监听器和标签名！
                     public void addPropertyChangeListener(
                     String propertyName,
                     PropertyChangeListener listener) {
                     if (listener == null || propertyName == null) {
                     return;
                     }
                     listener = this.map.extract(listener);
                     if (listener != null) {
                     this.map.add(propertyName, listener);
                     }

     ChangeListenerMap维护一个map结构标准化字段！
         abstract class ChangeListenerMap<L extends EventListener> {
         private Map<String, L[]> map;
     */
    // 执行该类的PropertyChange函数， 携带env变量，带有新旧值！
    // this 把自己当做Property 监听器

    // Simple*Property类的一个共同特点是可以设置监听器，具有监听功能，也就是一出生就具备观察者模式！
    // 用起来你才知道知识的用途！

    // 格局小了，得想想这个知识点背后产生的逻辑，他的思路，他的运思过程，他为什么这么表示，是不是还有其他表示方法？
    // 要把他的整个链条联系好！
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
