package org.openjfx;

import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.BiConsumer;

// 13.0.2之后最好是加入
// 参考 org.openjfx.SecondaryController
public class FXCollectionsTestController implements Initializable {

    // list initial
    private ObservableList<String> list;
    private SimpleListProperty<String> slp;

    // set initial
    ObservableSet<String> set ;
    SimpleSetProperty<String> ssp ;

    // map initial
    ObservableMap<String,String> map;
    SimpleMapProperty<String,String> smp;


    // list with SimpleStringProperty
    @FXML
    private TextField tf_listpro;
    private SimpleStringProperty s_apple;
    private SimpleStringProperty s_bana;
    private ObservableList<SimpleStringProperty> list_pro;
    private ObservableList<SimpleStringProperty> list_pro_special;
//    private SimpleListProperty<SimpleStringProperty> slp_pro;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    public void listTestAdd(){
        System.out.println("--------------------*****yzl list  add******---------------------");
        list.add("Ee");
    }


    @FXML
    public void listTestUpdate(){
        System.out.println("--------------------*****yzl list  update******---------------------");
        // 等到SimpleStringProperty 再讲
    }

    @FXML
    public void listTestAdds(){
        System.out.println("--------------------*****yzl list  adds******---------------------");
        list.addAll("Ee","Ff");
    }

    @FXML
    public void listTestRemoves(){
        System.out.println("--------------------*****yzl list  removes******---------------------");
        list.remove(0,2); // 删除0,1 到2停，2不删除
    }

    @FXML
    public void listTestRemove(){
        System.out.println("--------------------*****yzl list remove******---------------------");
        list.remove(0);
    }

    @FXML
    public void listTestSort(){
        System.out.println("--------------------*****yzl list sort******---------------------");
        // 这里不要要用slp 因为slp是一次一次的执行list ，会不断的产生compare的动作
//        slp.sort();
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
               return o2.compareTo(o1); // 反序  正序为01.compareTo(o1)
            }
        });
    }

    @FXML
    public void setTestAdd(){
        System.out.println("--------------------*****yzl set add******---------------------");
        set.add("Dd");
    }

    @FXML
    public void setTestAdds(){
        System.out.println("--------------------*****yzl set adds******---------------------");
//        set.addAll();
        Set<String> set1 = new HashSet<String>();
        set1.add("oo");
        set1.add("pp");
        set.addAll(set1);
    }


    @FXML
    public void setTestRemove(){
        System.out.println("--------------------*****yzl set remove******---------------------");
        set.remove("Dd");
    }

    @FXML
    public void setTestRemovess(){
        System.out.println("--------------------*****yzl set removes******---------------------");
//        set.addAll();
        Set<String> set1 = new HashSet<String>();
        set1.add("oo");
        set1.add("pp");
        set.removeAll(set1);
    }

    @FXML
    public void mapTestAdd(){
//        Map map1 = new HashMap();
        System.out.println("--------------------*****yzl map add******---------------------");
        map.put("H","h");
    }

    @FXML
    public void mapTestAdds(){
        Map map1 = new HashMap();
        map1.put("T","t");
        map1.put("U","u");
        System.out.println("--------------------*****yzl map adds******---------------------");
        map.putAll(map1);
    }
    @FXML
    public void mapTestRemove(){
        System.out.println("--------------------*****yzl map remove******---------------------");
        map.remove("A");
    }

    @FXML
    public void list_proTestAdd(){
        System.out.println("--------------------*****yzl list_pro  add******---------------------");
        SimpleStringProperty s_orange= new SimpleStringProperty("orange");
        list_pro.add(s_orange);
    }

    @FXML
    public void list_proAndSpecialTestAdd(){
        System.out.println("--------------------*****yzl list_pro and Special add******---------------------");
        System.out.println("--------------------*****Take care !!!******---------------------");
        SimpleStringProperty s_orange= new SimpleStringProperty("GOd , OMG");
        list_pro_special.add(s_orange);
    }

    @FXML
    public void list_proTestUpdate(){
        System.out.println("--------------------*****yzl list_pro  update******---------------------");
        System.out.println(" banana should exists! ");
//        tf_listpro.getText();
        // 等到SimpleStringProperty 再讲
        s_bana.set(tf_listpro.getText() );
        // 重复执行 没有效果， 只有当设置的值改变才有效果
    }

    @FXML
    public void list_proTestAdds(){
        System.out.println("--------------------*****yzl list_pro  adds******---------------------");

        SimpleStringProperty s_peach= new SimpleStringProperty("peach");
        SimpleStringProperty s_pear= new SimpleStringProperty("pear");
        List<SimpleStringProperty> liste = new ArrayList<SimpleStringProperty>();
        liste.add(s_peach);
        liste.add(s_pear);
        list_pro.addAll(liste);
    }

    @FXML
    public void list_proTestRemoves(){
        System.out.println("--------------------*****yzl list_pro  removes******---------------------");
        list_pro.remove(0,2);
    }

    @FXML
    public void list_proTestRemove(){
        System.out.println("--------------------*****yzl list_pro remove******---------------------");
        list_pro.remove(0);
    }

    @FXML
    public void list_proTestSort(){
        System.out.println("--------------------*****yzl list_pro sort******---------------------");
        // 这里不要要用slp 因为slp是一次一次的执行list ，会不断的产生compare的动作
//        slp.sort();
        list_pro.sort(new Comparator<SimpleStringProperty>() {
            @Override
            public int compare(SimpleStringProperty o1, SimpleStringProperty o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * list 测试初始化
         */
        list = FXCollections.observableArrayList("Aa","Bb","Cc");
        list.add("Dd");

        slp = new SimpleListProperty<String>(list);

        slp.addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> oldvalue, ObservableList<String> newvalue) {
                oldvalue.forEach(System.out::println);

                System.out.println("-------------------------***--------------------------");

                newvalue.forEach(System.out::println);

                list.forEach(item-> System.out.println(item));
            }
        });

        slp.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                // 必须得使用change.next()
                // { [Ee] added at 4}  Ee是数据源  4表示插入的索引号
                System.out.println(change);

                change.getList().forEach(System.out::println);
                // change.next()  索引指导第一次更改的位置(或者更改位置)
                while (change.next()) {
                    //从哪里来
                    System.out.println("Start Position ：  "+ change.getFrom());
                    //到哪里去
                    // 有可能出现添加和删除同时出现，所以只用if
                    System.out.println("End Position ：    "+ change.getTo());
                    if (change.wasAdded()) {

                        System.out.println("Was added = " + change.wasAdded());
                        System.out.println("Added list :"+change.getAddedSubList());
                    }
                    if(change.wasRemoved()){

                        System.out.println("Was removed = " + change.wasRemoved());
                        System.out.println("Deleted list :"+change.getAddedSubList());
                    }
                    if(change.wasUpdated()){

                        System.out.println("Was Updated = " + change.wasUpdated());
                    }
                    if(change.wasReplaced()){

                        System.out.println("Was replaced = " + change.wasReplaced());
                    }
                    if (change.wasPermutated()) {

                        System.out.println("Was sorted = " + change.wasPermutated());
                        System.out.println("where is the 0-index element going to？ "+ change.getPermutation(0));
                    }

                }

            }
        });

        /**
         * set 测试初始化
         */
        set = FXCollections.observableSet("A","B","C");
        ssp = new SimpleSetProperty<String>(set);

        ssp.addListener(new SetChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {

                //迭代器
                ssp.forEach(System.out::println);
                if (change.wasAdded()) {
                    System.out.println("Was added = "+change.wasAdded());
                    System.out.println("added List is " + change.getElementAdded());
                }

                if(change.wasRemoved()){
                    System.out.println("Was removed = "+change.wasRemoved());
                    System.out.println("removed List is " + change.getElementRemoved());
                }

                // 特别的地方 因为继承了Set集合
                // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Set.html?is-external=true#addAll(java.util.Collection)

                Iterator<String> it = ssp.iterator();
                while (it.hasNext()) {
                    System.out.println("Iterator shows : "+ it.next());
                }
            }
        });


        /**
         * map 测试初始化
         */
        map = FXCollections.observableHashMap();
        map.put("A","a");
        map.put("B","b");
        map.put("C","c");
        map.put("D","d");
        smp = new SimpleMapProperty<String,String>(map);
        smp.addListener(new MapChangeListener<String, String>() {
            @Override
            public void onChanged(Change<? extends String, ? extends String> change) {
                // 特别地方 具备Map的特性 key可迭代
                Iterator<String> keyIterator = (Iterator<String>) map.keySet().iterator();
                while (keyIterator.hasNext()) {
                    System.out.println("changed?  --- " + map.get(keyIterator.next()));
                }

                if (change.wasAdded()) {
                    System.out.println("was added = " + change.wasAdded());
                    System.out.println("Added list = " + change.getValueAdded());
                }
                if(change.wasRemoved()){
                    System.out.println("Was removed = " + change.wasRemoved());
                    System.out.println("Removed list = " + change.getValueRemoved());
                }

                smp.forEach(new BiConsumer<String, String>() {
                    @Override
                    public void accept(String s, String s2) {
                        System.out.println(s + " --- " + s2);
                    }
                });

                // 测试map结束
            }
        });

        /**
         * list with SimpleStringProperty 测试初始化
         * 只有特殊的Simple[String,Double,Integer,Boolean]Property类需要添加new Callback类
         */
        s_apple = new SimpleStringProperty("Aa");
        s_bana = new SimpleStringProperty("Bb");
        list_pro = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty simpleStringProperty) {
                System.out.println("Call list_pro = " + simpleStringProperty);
                // 监听所有list_pro的信息
                Observable[] obsarray = new Observable[]{simpleStringProperty};
//                return new Observable[0];
                return obsarray;
            }
        });
        list_pro.add(s_apple);
        list_pro.add(s_bana);
//        list.add("Dd");
        // 挺有意思的测试！！！！  列表监听
        // very interest test
        // list_pro_special changes, list_pro will change too
        // list_pro change, but lsit_pro_special will not change!!
        list_pro_special = FXCollections.observableList(list_pro,new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty simpleStringProperty) {
                System.out.println("Call list_pro_speical = "+simpleStringProperty);
                Observable[] observables = new Observable[]{simpleStringProperty};
//                return new Observable[0];
                return observables;
            }
        });

        list_pro.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> change) {

                System.out.println(change);

                change.getList().forEach(System.out::println);
                // change.next()  索引指导第一次更改的位置(或者更改位置)
                while (change.next()) {
                    //从哪里来
                    System.out.println("list_pro Start Position ：  "+ change.getFrom());
                    //到哪里去
                    // 有可能出现添加和删除同时出现，所以只用if
                    System.out.println("list_pro End Position ：    "+ change.getTo());
                    if (change.wasAdded()) {

                        System.out.println("list_pro Was added = " + change.wasAdded());
                        System.out.println("list_pro Added list :"+change.getAddedSubList());
                    }
                    if(change.wasRemoved()){

                        System.out.println("list_pro Was removed = " + change.wasRemoved());
                        System.out.println("list_pro Deleted list :"+change.getAddedSubList());
                    }
                    if(change.wasUpdated()){

                        System.out.println("list_pro Was Updated = " + change.wasUpdated());
                    }
                    if(change.wasReplaced()){

                        System.out.println("list_pro Was replaced = " + change.wasReplaced());
                    }
                    if (change.wasPermutated()) {

                        System.out.println("list_pro Was sorted = " + change.wasPermutated());
                        System.out.println("list_pro where is the 0-index element going to？ "+ change.getPermutation(0));
                    }

                }
            }
        });
        list_pro_special.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> change) {

                System.out.println(change);

                change.getList().forEach(System.out::println);
                // change.next()  索引指导第一次更改的位置(或者更改位置)
                while (change.next()) {
                    //从哪里来
                    System.out.println("list_pro_special Start Position ：  "+ change.getFrom());
                    //到哪里去
                    // 有可能出现添加和删除同时出现，所以只用if
                    System.out.println("list_pro_special End Position ：    "+ change.getTo());
                    if (change.wasAdded()) {

                        System.out.println("list_pro_special Was added = " + change.wasAdded());
                        System.out.println("list_pro_special Added list :"+change.getAddedSubList());
                    }
                    if(change.wasRemoved()){

                        System.out.println("list_pro_special Was removed = " + change.wasRemoved());
                        System.out.println("list_pro_special Deleted list :"+change.getAddedSubList());
                    }
                    if(change.wasUpdated()){

                        System.out.println("list_pro_special Was Updated = " + change.wasUpdated());
                    }
                    if(change.wasReplaced()){

                        System.out.println("list_pro_special Was replaced = " + change.wasReplaced());
                    }
                    if (change.wasPermutated()) {

                        System.out.println("list_pro_special Was sorted = " + change.wasPermutated());
                        System.out.println("list_pro_special where is the 0-index element going to？ "+ change.getPermutation(0));
                    }

                }
            }
        });


    }
// 列表bind，两边的slp1 slp2 都可以改变  但是字段bind slp1.bind(slp2) 只能slp2改变，slp1不能改变
    // 字段绑定的话，slp1会提示被束缚， 只有slp2改变，slp1才会改变。
    @FXML
    public void listbindTest(){
       ObservableList<String> list1 = FXCollections.observableArrayList();
       // slp1指针 维护数据源list1
       SimpleListProperty<String> slp1 = new SimpleListProperty<>(list1);
       list1.add("A");
       list1.add("B");
       list1.add("C");

       ObservableList<String> list2 = FXCollections.observableArrayList();// 数据源2
        //slp2 指针维护数据源 list2
        SimpleListProperty<String> slp2 = new SimpleListProperty<>(list2);

        list2.add("D");
        list2.add("e");
        list2.add("f");

        System.out.println("before slp1 bind slp2 slp1 = " + slp1.getValue() );
        System.out.println("before slp1 bind slp2 slp2 = " + slp2.getValue() );

        //slp1绑定到slp2 slp1指针此时换到维护slp2
        slp1.bind(slp2);
//        slp1.bindBidirectional(slp2); // 类似bind
        System.out.println("after slp1 bind slp2 slp1 = " + slp1.getValue() );
        System.out.println("after slp1 bind slp2 slp2 = " + slp2.getValue() );


        slp1.add("hello");
        slp2.add("javafx") ;
        System.out.println("after slp1 add and slp2 add slp1 = " + slp1.getValue() );
        System.out.println("after slp1 add and slp2 add  slp2 = " + slp2.getValue() );

        System.out.println("data source list1= "+list1);
        System.out.println("data source list2= "+list2);

        // 倒序排列
        slp1.sort((item1,item2)->item2.compareTo(item1));
        System.out.println("after slp1 sort slp1 = " + slp1.getValue() );
        System.out.println("after slp1 sort slp2 = " + slp2.getValue() );

        System.out.println("data source list1= "+list1);
        System.out.println("data source list2= "+list2);
    }


    @FXML
    public void listbindContentTest(){
        ObservableList<String> list1 = FXCollections.observableArrayList();
        // slp1指针 维护数据源list1
        SimpleListProperty<String> slp1 = new SimpleListProperty<>(list1);
        list1.add("A");
        list1.add("B");
        list1.add("C");

        ObservableList<String> list2 = FXCollections.observableArrayList();// 数据源2
        //slp2 指针维护数据源 list2
        SimpleListProperty<String> slp2 = new SimpleListProperty<>(list2);

        list2.add("D");
        list2.add("e");
        list2.add("f");

        System.out.println("before slp1 bind slp2 slp1 = " + slp1.getValue() );
        System.out.println("before slp1 bind slp2 slp2 = " + slp2.getValue() );

        //slp1绑定到slp2 slp1指针此时换到维护slp2
        // 只是把slp2的内容拷贝过去， slp1 依旧维持list1的内容
        slp1.bindContent(slp2);
//        slp1.bindBidirectional(slp2); // 类似bind
        System.out.println("after slp1 bind slp2 slp1 = " + slp1.getValue() );
        System.out.println("after slp1 bind slp2 slp2 = " + slp2.getValue() );


        slp1.add("hello");
        slp2.add("javafx") ;
        System.out.println("after slp1 add and slp2 add slp1 = " + slp1.getValue() );
        System.out.println("after slp1 add and slp2 add  slp2 = " + slp2.getValue() );

        System.out.println("data source list1= "+list1);
        System.out.println("data source list2= "+list2);

        // 倒序排列
        slp1.sort((item1,item2)->item2.compareTo(item1));
        System.out.println("after slp1 sort slp1 = " + slp1.getValue() );
        System.out.println("after slp1 sort slp2 = " + slp2.getValue() );

        System.out.println("data source list1= "+list1);
        System.out.println("data source list2= "+list2);
    }
}
