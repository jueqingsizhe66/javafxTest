package org.openjfx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.data.Entity.BindingMyIntegerCustomize;
import org.data.Entity.BindingSelectStringStudent;
import org.data.Entity.StudentThoery;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class BindTheoryTestController implements Initializable {

    @FXML
    private TextField tf_name;


    @FXML
    private TextField tf_age;

//    @FXML
//    private Label lb_a;
//    @FXML
//    private Label lb_b;
//    @FXML
//    private Label lb_c;
//    @FXML
//    private Label lb_d;
//    @FXML
//    private Label lb_e;

    @FXML
    private TextField tf_row;

    @FXML
    private TextField tf_content;

    @FXML
    private VBox vb_label;

    @FXML
    private Button bt_sourceBi;

    private StudentThoery stu;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stu = new StudentThoery("yzl",34);
        stu.pcs.addPropertyChangeListener(new PropertyChangeListener() {
            // ?????????????????????PropertyChangeEvent??????,???????????????
            // primaryStage.widthProperty.addListener(new ChangeListner<Numer>()){
            //  ???????????????oldValue ???newValue} ??????javafx??????????????????
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Platform summary notify: old = " + evt.getOldValue());
                System.out.println("Platform summary notify: new = " + evt.getNewValue());

                System.out.println(evt.getSource().toString());
            }
        });

        stu.pcs.addPropertyChangeListener("Special_name", new PropertyChangeListener() {
            // ?????????????????????PropertyChangeEvent??????
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                System.out.println("In the special_name filed notify: old = " + evt.getOldValue());
                System.out.println("In the special_name filed notify: new = " + evt.getNewValue());
            }
        });


        stu.pcs.addPropertyChangeListener("Special_age", new PropertyChangeListener() {
            // ?????????????????????PropertyChangeEvent??????
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                System.out.println("In the special_age filed notify: old = " + evt.getOldValue());
                System.out.println("In the special_age filed notify: new = " + evt.getNewValue());
            }
        });

        //?????????????????????
        ObservableList<String> lableList = FXCollections.observableArrayList();
        SimpleListProperty<String> listlable =new SimpleListProperty<String>(lableList);
        listlable.add("A");
        listlable.add("B");
        listlable.add("C");
        listlable.add("D");
        listlable.add("E");
        listlable.add("F");

        for (int i = 0; i < listlable.size(); i++) {

            Label l1 = new Label();
//            MFXLabel l1 = new MFXLabel();
            l1.setText("SS"+i);
            ObjectBinding obj = listlable.valueAt(i); // OB??????
            l1.textProperty().bind(obj); // ???????????????
            vb_label.getChildren().add(l1);
        }
        tf_content.textProperty().addListener(new ChangeListener<String>() {
            /**
             * 1. ?????????????????????
             * 2. ???????????????????????????
             * @param observableValue
             * @param s
             * @param t1
             */
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (tf_row.getText().equals("")) {
                    return;
                }

                try{
                    int index = Integer.parseInt(tf_row.getText());
                    listlable.set(index,tf_content.getText());
                }catch (Exception e){
                    System.out.println("tf_content listen Error");
                }
            }
        });

    }

    @FXML
    public void modifyName(){
        System.out.println("--------------Modify Student name Thoery-------------------");
        stu.setName(tf_name.getText());
    }
    @FXML
    public void modifyAge(){
        System.out.println("--------------Modify Student age Thoery-------------------");
        stu.setAge(Integer.parseInt(tf_age.getText()));
    }
    @FXML
    public void commonVarObjectBindingTest(){

        ObservableList<String> oblist = FXCollections.observableArrayList();
        SimpleListProperty<String> list = new SimpleListProperty<String>(oblist);

        list.add("A");
        list.add("B");
        list.add("C");

        System.out.println("----------------**** value at common int style objbind---------");
        ObjectBinding<String> objbind = list.valueAt(0);
        System.out.println(objbind.get());

        list.set(0,"E");
        System.out.println("The zero indice element is set to E, so objbind = "+objbind.get());

        System.out.println("----------------------------------");
    }


        @FXML
    public void simplePropertyObjectBindingTest(){

        ObservableList<String> oblist = FXCollections.observableArrayList();
        SimpleListProperty<String> list = new SimpleListProperty<String>(oblist);

        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println("----------------**** value at SimpleIntegerProperty style objbind2 ---------");
        SimpleIntegerProperty sip = new SimpleIntegerProperty(2);
        ObjectBinding<String> objbind2  = list.valueAt(sip);
        System.out.println("The objbin2 is bind to index =2, so objbin2 is "+objbind2.get());

        list.set(2,"DDDDD");
        System.out.println("The list(2) is set DDDD, so objbind2 is "+objbind2.get());

        // ??????1 ?????????2????????????i???????????????????????????????????????????????????callback???????????????
        // SimpleintegerProperty??????callback??????
        System.out.println("--------------------**** value at SimpleInteger propertye changed!!! objbind2----");
        sip.set(1);
        System.out.println(" The SimpleIntegerProperty is set to 1, so objbind 2 list(1), list(1) is = " + objbind2.get());

        list.set(1,"hhhlloo");
        System.out.println("--------------------changing!!!! objbind2 --------------");
        System.out.println("list(1) is set hhhlloo, so objbin2 is"+objbind2.get());

    }



    @FXML
    public void commonVarObjectBindingMapTest(){

        ObservableMap<String,String> obmap = FXCollections.observableHashMap();
        SimpleMapProperty<String,String> map = new SimpleMapProperty<String,String>(obmap);

        map.put("A","1");
        map.put("B","1");
        map.put("C","1");

        System.out.println("----------------**** value at common int style objbind map---------");
        ObjectBinding<String> objbind = map.valueAt("A");
        System.out.println("objbind is bind to A key in  map, objbind = "+objbind.get());

        map.put("A","E");
        System.out.println("The A indice element in set is set to E, so objbind = "+objbind.get());

        System.out.println("----------------------------------");
    }

    /**
     * ????????????   ????????????????????????????????????????????????
     */
    @FXML
    public void simplePropertyObjectBindingMapTest(){

        ObservableMap<String,String> obmap = FXCollections.observableHashMap();
        SimpleMapProperty<String,String> map = new SimpleMapProperty<String,String>(obmap);

        map.put("A","1");
        map.put("B","1");
        map.put("C","1");
        System.out.println("----------------**** value at SimpleIntegerProperty style objbind2 ---------");
        SimpleStringProperty sip = new SimpleStringProperty("A");
        ObjectBinding<String> objbind2  = map.valueAt(sip);
        System.out.println("The objbin2 is bind to key=A, so objbin2 is "+objbind2.get());

        map.put("A","100000");
        System.out.println("The map(A) is set 100000, so objbind2 is "+objbind2.get());

        // ??????1 ?????????2????????????i???????????????????????????????????????????????????callback???????????????
        // SimpleintegerProperty??????callback??????
        System.out.println("--------------------**** value at SimpleInteger property changed!!! objbind2----");
        sip.set("B");
        System.out.println(" The SimpleIntegerProperty is set to B, so objbind 2 map(B), map(B) is = " + objbind2.get());

        map.put("B","yfx");
        System.out.println("--------------------changing!!!! objbind2 --------------");
        System.out.println("map(B) is set yfx, so objbin2 is"+objbind2.get());

    }
    // Bindings ?????????????????????
    @FXML
    public void formatOutput(){
        SimpleIntegerProperty sip = new SimpleIntegerProperty(10);
        System.out.println("-----------------------Sexp1------------------------");
        StringExpression sexp = Bindings.concat("Sip value by concat is ",sip.asString(Locale.getDefault(),"%s"));
        System.out.println("1. concat before settings: "+sexp.get());
        sip.set(100000);
        System.out.println("2. concat after settings:"+ sexp.get());

        System.out.println("-----------------------Sexp2 is similar to language c's format------------------------");
        StringExpression sexp2 = Bindings.format("sip by format value = %s", sip);
        System.out.println("1. format before settings: "+sexp2.get());
        sip.set(222222222);
        System.out.println("2. format after settings:"+ sexp2.get());

    }
    @FXML
    public void maxmin(){
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        System.out.println("Bindings maximum value  " + Bindings.max(x,y).intValue());
        System.out.println("Bindings minimum value  " + Bindings.min(x,y).intValue());

        x.set(3);

        System.out.println("Bindings maximum value after set x=3, maximum value is " + Bindings.max(x,y).intValue());
        System.out.println("Bindings minimum value after set x =3 , minimum value is  " + Bindings.min(x,y).intValue());
    }
    @FXML
    public void createStringBindingTest(){

        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(2);

        // ?????????????????????????????????????????????????????????????????????????????????)
        // ?????????x??????????????? stringbnd???????????????????????? ?????????????????????
        StringBinding stringbnd = Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call() ");
                String value =" hello";
                // ??????????????????x, ?????????????????????x,y ???????????????y????????????

                if (x.get()==1) {
                    value = "apple";
                }else if(x.get() == 20){
                    value = "twenty";
                }else if(x.get() == 2){
                    value = "banana";
                }
                return value;
            }
        },x);

        System.out.println(stringbnd.get());
        System.out.println(x.get());

        x.set(20);

        System.out.println("1. after set x to 20: "+stringbnd.get());
        System.out.println("1. after set x to 20: "+ x.get());

        x.set(2);

        System.out.println("1. after set x to 2: "+stringbnd.get());
        System.out.println("1. after set x to 2: "+ x.get());
    }

    @FXML
    public void selectStringBindingTest(){
        // ???????????? public???????????? fieldProperty();
        // javafx   tableview?????? columnProperty
        BindingSelectStringStudent student = new BindingSelectStringStudent("apple");
        SimpleObjectProperty<BindingSelectStringStudent> stu = new SimpleObjectProperty<>(student);
        // sop ?????????BindingSelectStringStudent???sopProperty()  ??????????????? sop
        // name ?????????BindingSelectStringName???nameProperty()  ??????????????? name
        StringBinding strvalue = Bindings.selectString(stu,"sop","name");

        System.out.println("1. before set : "+ strvalue.get());
        student.setName("banana");

        System.out.println("2. after set student's name to bananan === " + strvalue.get());

    }

    @FXML
    public void convertTest(){
        // ?????? Bindings.concat   Bindings.format
        StringExpression sb = Bindings.convert(new SimpleIntegerProperty(100)) ;

        System.out.println("conver before : " + sb.get());


    }


    @FXML
    public void bindingCustomizeMyInteger(){
        // ?????? Bindings.concat   Bindings.format
        SimpleIntegerProperty sip = new SimpleIntegerProperty(10);
        BindingMyIntegerCustomize bmic = new BindingMyIntegerCustomize(100);
        System.out.println("before binding sip= "+ sip.get());
        System.out.println("before binding bmic = "+ bmic.get());

        sip.bind(bmic);

        System.out.println("sip bind to bmic  sip= "+ sip.get());
        System.out.println("sip bind to bmic   bmic = "+ bmic.get());

        bmic.setValue(200);
        // ??????sip???bind???bmic??????????????????????????? ??????set
//        sip.setValue(200);

        System.out.println(" bmic set to 200  sip= "+ sip.get());
        System.out.println("bmic set to 200   bmic = "+ bmic.get());

    }

    @FXML
    public void thoeryApplied(){
        SimpleIntegerProperty sip =new SimpleIntegerProperty(2);
        // ????????????
        // ????????? ??????????????????????????????????????????ChangeListener,???????????????????????????
        // ???????????????addListener ???????????????removeListner
        sip.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Something is change : from "+ oldValue + " to " + newValue);
            }
        });

        // ????????????ChangeListener?????? ????????????
        // ???????????????sip.get()???????????????????????????????????????! ????????????
        // ???set???????????????get?????????????????????????????????delay listener???!!
        sip.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("delay listener: "+ observable);
            }
        });
        sip.set(10);
        sip.set(20);

        System.out.println("final value is "+sip.get());
    }

    @FXML
    public void readOnlyWrapperTest(){
        // ???????????????????????????
        ReadOnlyDoubleWrapper rodw = new ReadOnlyDoubleWrapper(3);
        // ????????????????????????
        ReadOnlyDoubleProperty only = rodw.getReadOnlyProperty();

        /**
         * ????????????????????????????????????
         */
        rodw.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("in the server rodw listenr : " + oldValue + "    "+ newValue);
            }
        });

        /**
         * ???????????????????????? ????????????
         */
        only.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("in the clinet only listner " + oldValue + "   "+ newValue);
            }
        });
        System.out.println(rodw.get());
        System.out.println(only.get());

        System.out.println("************ after  set rodw new value **********************");

        /**
         * ???????????????????????????
         * ?????????????????????????????????
         */
        rodw.set(1000);
        System.out.println("new value of rodw is " + rodw.get());
        System.out.println("new value of only is "+ only.get());

    }

    @FXML
    public void testDoubleDataSource() {
        SimpleStringProperty a = new SimpleStringProperty("a");
        SimpleStringProperty b = new SimpleStringProperty("b");

        ObservableList<SimpleStringProperty> list= FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>()
        {
            @Override
            public Observable[] call(SimpleStringProperty param) {

                System.out.println("call1: "+ param);
                SimpleStringProperty[] arr = new SimpleStringProperty[]{param};
                return arr;
            }
        });

        list.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                while (c.next()) {
                    System.out.println("onChange1: "+ c + "was updated: "+c.wasUpdated());
                }
            }
        });

        /**
         * ObservableList???ObservableArrayList????????????
         * observableList??????????????????ObservableList<T>??????</T>
         * ObservableArrayList????????????Callback????????????
         *
         * ??????list??????????????????????????????list2??????????????????
         * list2?????????????????????list?????????(list?????????????????? list2??????????????? ??????????????????????????????????????????list?????????list?????????????????????
         * list2??????????????????????????????list??? ??????list??????????????????????????????list2????????????????????????????????????????????????
         */
        ObservableList<SimpleStringProperty> list2=  FXCollections.observableList(list,new Callback<SimpleStringProperty, Observable[]>()
        {
            @Override
            public Observable[] call(SimpleStringProperty param) {

                System.out.println("call2: "+ param);
                SimpleStringProperty[] arr = new SimpleStringProperty[]{param};
                return arr;
            }
        });

        list2.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                while (c.next()) {
                    System.out.println("onChange2: "+ c + "was updated: "+c.wasUpdated());
                }
            }
        });

        list.add(a);
        list2.add(b);
        b.set("anna");
        a.set("banana");
    }
}

