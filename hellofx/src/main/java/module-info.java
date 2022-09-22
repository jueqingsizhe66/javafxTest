module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires rxcontrols;
    requires activerecord;
    requires druid;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    exports org.data.Entity.JFinal;
    exports org.data.Entity; //selectstring binding test
    requires java.desktop;
    requires org.mybatis;
    requires hutool.all;
    requires feilong;
    requires com.jfoenix;
    requires org.controlsfx.controls;
//    opens cn.hutool;
//    opens com.feilong.core;
    opens org.data.Entity2;
    opens org.data.Mapper;

}