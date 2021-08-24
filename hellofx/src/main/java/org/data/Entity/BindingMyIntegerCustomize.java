package org.data.Entity;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class BindingMyIntegerCustomize extends IntegerBinding {

    private SimpleIntegerProperty sip = new SimpleIntegerProperty();
    public BindingMyIntegerCustomize(int num){
        //        sip = new simpleintegerproperty(num); //错误写法
        this.bind(sip); // 没有bind则不会实时更新
        sip.set(num);

        // integerbinding provides a simple invalidation-scheme. an extending class can register dependencies by calling bind(observable...).
        // this.bind(sip);  // 和创建新对象的写法类似 ? 不一样 必须得用bind写法
        // sip.set(num);

    }
    public void setValue(int num){
        sip.set(num);
    }

    @Override
    protected int computeValue() {
        // 可以自定义各种计算， 在bindings工具类提供了很多加 减 乘除 但没有计算面积等
        return sip.get()*100*3;
    }
}
