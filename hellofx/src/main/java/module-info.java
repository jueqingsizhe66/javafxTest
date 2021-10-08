module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    requires java.desktop;
    requires org.mybatis;
    requires hutool.all;
    requires feilong;
//    opens cn.hutool;
//    opens com.feilong.core;
    opens org.data.Entity2;
    opens org.data.Mapper;

}