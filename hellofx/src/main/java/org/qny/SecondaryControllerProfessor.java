package org.qny;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;
import com.leewyatt.rxcontrols.controls.RXHighlightText;
import com.leewyatt.rxcontrols.utils.StringUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.data.Entity.JFinal.Professor;
import org.data.Entity.JFinal._MappingKit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class SecondaryControllerProfessor implements Initializable {

    @FXML
    private ListView<Professor> lv_pro;

    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_company;
    @FXML
    private TextField tf_duty;
    @FXML
    private TextField tf_pro;
    @FXML
    private TextField tf_phone;

    @FXML
    private TextField tf_idcard;

    @FXML
    private TextField tf_bankid;

    @FXML
    private TextField tf_bank;

    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_id;

    private FilteredList<Professor> filteredList;
    @FXML
    private ComboBox<RXHighlightText.MatchRules> cb_match;
    @FXML
    private Label lb_count;

    @FXML
    private Label lb_name;
    private ObservableList<Professor> obProfessors;
    @FXML
    private Button btnP_sort;
    private Professor proDao = new Professor().dao();

    /**
     * 在initiate()函数完成初始化工作！
     */

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    //    private static URL load(String path){
//        MFXLabel
//    }
    @FXML
    private void addStudentBtn() {
        Professor pro = new Professor();
        pro.setName(tf_name.getText());
        pro.setCompany(tf_company.getText());
        pro.setDutyAndTitle(tf_duty.getText());
        pro.setPhone(tf_phone.getText());
        pro.setProfessional(tf_pro.getText());
        pro.setID(tf_idcard.getText());
        pro.setBankID(tf_bankid.getText());
        pro.setBank(tf_bank.getText());
        pro.setEmail(tf_email.getText());

        pro.save();

        System.out.println(pro);

//        lv_pro.getItems().add(pro);
        obProfessors.add(pro);
        lv_pro.refresh();
    }

    //region 删除专家: 1. 更新列表 2. 更新数据库
    @FXML
    private void deleteStuSelected() {
        Professor stu = lv_pro.getSelectionModel().getSelectedItem();
        // https://jfinal.com/feedback/1403
        // 不要将参数直接拼接在 sql 中，而是要用 "?" 进行占位，防止被 sql 注入

//        lv_pro.getItems().remove(stu);

        obProfessors.remove(stu);
        // 但是这个过程是刷新list表的全部行，有没有刷新当行?
        lv_pro.refresh();

        // 先删掉list，然后再删掉数据库，数据库是最后一步
        // 通过SimpleProperty即可以只刷新当行
        Db.delete("delete from professor where Name = ?", stu.getName());
    }
    //endregion

    //region 查询全部专家
    @FXML
    private void selectAll() {
//        List<Professor> rows = (List<Professor>) proDao.template("select * from professor");
        List<Professor> rows =proDao.findAll();
        for (Professor professor : rows) {
            System.out.println("Hello " + professor.getName() + " , your company: " + professor.getCompany() + "\n");
        }
    }
    //endregion

    //region 初始化组件
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Prop p = PropKit.useFirstFound("jdbc.properties", "app-config-pro.txt", "app-config-dev.txt");
        DruidPlugin dp = new DruidPlugin(p.get("jdbc.url"), p.get("jdbc.username"), p.get("jdbc.password").trim());
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
//    arp.addMapping("blog", Blog.class);
        _MappingKit.mapping(arp);///所有表映射上

        // 与 jfinal web 环境唯一的不同是要手动调用一次相关插件的start()方法
        dp.start();
        arp.start();

        cb_match.getItems().addAll(FXCollections.observableArrayList(RXHighlightText.MatchRules.values()));
        cb_match.getSelectionModel().select(0);

        List<Professor> rows = proDao.findAll();
//        lv_pro.getItems().addAll(rows);

        // 源头list
        // 把定义提供到类变量级别
        obProfessors = FXCollections.observableArrayList(rows);
        // 包装list
        filteredList = new FilteredList<>(obProfessors, param -> true);
        tf_id.textProperty().addListener((ob, ov, nv) -> {
            searchKeywords();
        });
        cb_match.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> {
            searchKeywords();
        });

        //lb_count.textProperty().bind(Bindings.format("人数： %d ",filteredList.size())); // 不具有实时更新的能力
        lb_count.textProperty().bind(Bindings.format("人数： %d ", Bindings.size(filteredList))); // 但是这样就可以了
        lv_pro.setItems(filteredList);
        lv_pro.setEditable(true);
        // List数值的监听
        ObservableList<Professor> lst_obj = lv_pro.getItems();
        lst_obj.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<Professor> data = (ObservableList<Professor>) observable;

                System.out.println(data);
            }
        });
        /**
         * ListChangeListener 监听list的CRUD行为
         */
        lst_obj.addListener(new ListChangeListener<Professor>() {
            @Override
            public void onChanged(Change<? extends Professor> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        System.out.println("添加动作 add action");
                    } else if (change.wasUpdated()) {
                        System.out.println("Update action");
                    } else if (change.wasRemoved()) {
                        System.out.println("Remove action");
                    } else {
                        System.out.println("Other actions");
                    }
                }
            }
        });

        // 利用工厂函数 把数据转变一下
        // https://www.bilibili.com/video/av42380834?from=search&seid=1216133184415812545
        //ListView关于五种数据类型的加载和更新删除等操作，
        // 展示对象的时候，可以改变显示的信息
        lv_pro.setCellFactory(TextFieldListCell.forListView(new StringConverter<Professor>() {
            @Override
            public String toString(Professor professor) {
                return professor.getName() + " - " + professor.getCompany() + " - " + professor.getDutyAndTitle() + "\n";
            }

            @Override
            public Professor fromString(String s) {
                return null;
            }
        }));

        lv_pro.setCellFactory(param -> new HighlightCell());


        lb_name.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            if (!lv_pro.getSelectionModel().getSelectedItems().isEmpty()) {
                                return "hello" + obProfessors.get(lv_pro.getSelectionModel().getSelectedIndex()).getName();
                            } else {
                                return "no selected";
                            }
                        }, lv_pro.getSelectionModel().selectedItemProperty()));
//        btn_time.setOnAction(event -> Notification);
    }
    //endregion

    //region 直接测试listview 添加而已
    @FXML
    public void addList() {
        Professor pro = new Professor();
        pro.setName("zhang Shanfeng");
        pro.setCompany("China");
        pro.setDutyAndTitle("高工");
        pro.setProfessional("气动工程师");
        pro.setPhone("151111111");
        pro.setID("36666666");
        pro.setBankID("33333");
        pro.setBank("招商");
        pro.setEmail("zhaoturkkey@163.com");
        lv_pro.getItems().add(pro);
    }
    //endregion

    //remove(from,to)
    // 直接测试listview 删除而已
    @FXML
    public void removeObjectFromList() {
        lv_pro.getItems().remove(0);
    }

    //region 直接测试listview 更新而已
    @FXML
    public void updateObjectFromList() {
        Professor pro = lv_pro.getSelectionModel().getSelectedItem();
        ObservableList<Integer> indexOfStu = lv_pro.getSelectionModel().getSelectedIndices();

        pro.setID("1111111111111111");
        System.out.println(indexOfStu.get(0));
        // 直接设置对象的形式 是指添加的形式
        lv_pro.getItems().set(indexOfStu.get(0), pro);
    }
    //endregion

    //region 专家排序
    @FXML
    public void sortList(){
        obProfessors.sort(new Comparator<Professor>() {
            @Override
            public int compare(Professor o1, Professor o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
    }
    //endregion

    //region textFiled关键字着色
    private void searchKeywords() {
        filteredList.setPredicate(param -> {
            //如果关键字为空, 那么全部匹配
            if (tf_id.getText().trim().isEmpty()) {
                return true;
            }

            RXHighlightText.MatchRules rule = cb_match.getSelectionModel().getSelectedItem();
            //匹配结果
            ArrayList<Pair<String, Boolean>> result;
            if (rule.equals(RXHighlightText.MatchRules.REGEX)) {
                result = StringUtil.matchText(param.getName(), tf_id.getText());
            } else if (rule.equals(RXHighlightText.MatchRules.IGNORE_CASE)) {
                result = StringUtil.parseText(param.getName(), tf_id.getText(), true);
            } else {
                result = StringUtil.parseText(param.getName(), tf_id.getText(), false);
            }
            for (Pair<String, Boolean> stringBooleanPair : result) {
                if (stringBooleanPair.getValue()) {
                    //如果找到一个匹配结果,那么返回匹配成功
                    return true;
                }
            }
            //没有找到, 那么返回匹配失败
            return false;
        });
        lv_pro.refresh();
    }

    private class HighlightCell extends ListCell<Professor> {
        RXHighlightText highlightText;

        public HighlightCell() {
            highlightText = new RXHighlightText();

        }

        // alt+insert 还可以重写很多方法！
        @Override
        protected void updateItem(Professor item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
//                System.out.println("******** output nothing *******************");
                setGraphic(null);
                return;
            }

//            System.out.println("******** output sth *******************");
            highlightText.setText(item.getName() + "\t" + item.getCompany() + "\t" + item.getDutyAndTitle() + "\t" +
                    item.getPhone() + "\t" + item.getID() + "\t" + item.getBankID() + "\t" + item.getEmail());
            highlightText.setKeywords(tf_id.getText());
            // 注意cb_match的类型是  HighligthText.MatchRules
//            System.out.println("what is this" + item);
            highlightText.setMatchRules(cb_match.getSelectionModel().getSelectedItem());
            setGraphic(highlightText);
        }
    }
    //endregion
}

