package org.qny;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.data.DaoImpl.StudentDaoImpl;
import org.data.Entity.Student;
import org.data.Entity2.User;
import org.data.Mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    @FXML
    private ListView<Student> lv_stu;

    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_number;
    @FXML
    private TextField tf_age;
    @FXML
    private TextField tf_phone;
    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;


    /**
     * 在initiate()函数完成初始化工作！
     */
    InputStream inputStream ;
    SqlSessionFactory sqlSessionFactory;
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

//    private static URL load(String path){
//        MFXLabel
//    }
    @FXML
    private void addStudentBtn(){
        Student stu= new Student();
        stu.setSname(tf_name.getText());
        stu.setSage(Integer.parseInt(tf_age.getText()));
        stu.setSnumber(tf_number.getText());
        stu.setSphone(tf_phone.getText());
        stu.setSaddress(tf_address.getText());

        StudentDaoImpl sdi = new StudentDaoImpl();

        sdi.addStudent(stu);

//        System.out.println(stu);

        lv_stu.getItems().add(stu);
        lv_stu.refresh();
    }
    @FXML
    private void deleteStuSelected(){
        Student stu=lv_stu.getSelectionModel().getSelectedItem();
        StudentDaoImpl sdi = new StudentDaoImpl();
        sdi.delStudentbyName(stu.getSname());

        lv_stu.getItems().remove(stu);

        // 但是这个过程是刷新list表的全部行，有没有刷新当行?
        lv_stu.refresh();

        // 通过SimpleProperty即可以只刷新当行
    }
    @FXML
    private void selectAll()  {
        StudentDaoImpl sdi = new StudentDaoImpl();
        List<Student> rows = sdi.getAllStudent();
        for (Student student : rows) {
            System.out.printf("Hello " + student.getSname() + " , your age: " + student.getSage()+"\n");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        StudentDaoImpl sdi = new StudentDaoImpl();
        List<Student> rows = sdi.getAllStudent();
        lv_stu.getItems().addAll(rows);

        // List数值的监听
        ObservableList<Student> lst_obj = lv_stu.getItems();
        lst_obj.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
               ObservableList<Student> data = (ObservableList<Student>) observable;

                System.out.println(data);
            }
        });
        /**
         * ListChangeListener 监听list的CRUD行为
         */
        lst_obj.addListener(new ListChangeListener<Student>() {
            @Override
            public void onChanged(Change<? extends Student> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        System.out.println("添加动作 add action");
                    }
                    else if (change.wasUpdated()) {
                        System.out.println("Update action");
                    }
                    else if (change.wasRemoved()) {
                        System.out.println("Remove action");
                    }
                    else{
                        System.out.println("Other actions");
                    }
                }
            }
        });
        // 利用工厂函数 把数据转变一下
        // https://www.bilibili.com/video/av42380834?from=search&seid=1216133184415812545
        //ListView关于五种数据类型的加载和更新删除等操作，
        // 展示对象的时候，可以改变显示的信息
        lv_stu.setCellFactory(TextFieldListCell.forListView(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student.getSname()+ " - " + student.getSage() + " - " + student.getSphone()+"\n";
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        }));

    }

    @FXML
    public void addList(){
        Student stu= new Student();
        stu.setSname("zhang Shanfeng");
        stu.setSage(39);
        stu.setSnumber("324233");
        stu.setSphone("15101033442");
        stu.setSaddress("平光");
        lv_stu.getItems().add(stu);
    }
    //remove(from,to)
    @FXML
    public void removeObjectFromList(){
        lv_stu.getItems().remove(0);
    }

    @FXML
    public void updateObjectFromList(){
        Student stu= lv_stu.getSelectionModel().getSelectedItem();
        ObservableList<Integer> indexOfStu= lv_stu.getSelectionModel().getSelectedIndices();

        stu.setSnumber("1111111111111111");
        System.out.println(indexOfStu.get(0));
        // 直接设置对象的形式 是指添加的形式
        lv_stu.getItems().set(indexOfStu.get(0),stu);
    }

    @FXML
    public void selectByIdWithMyBatis(){
        int id_selected =Integer.parseInt(tf_id.getText()) ;

        try {

//            String resource = "mybatis-config.xml";
//            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//            System.out.println("ok1");
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//            System.out.println("ok2");

            try(SqlSession session = sqlSessionFactory.openSession()) {
                //方式2
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user2 = mapper.selectById(id_selected);
                System.out.println("The second method: " + user2);

                User user3 = mapper.selectByIdAndUsername(2, "user1");
                System.out.println("ID and username Method: " + user3);
            }

        }catch(Exception e){
//            System.out.println("Exceptions  ");
            e.printStackTrace();
        }

    }

    @FXML
    public void insertUserWithMybatis(){

        String uname = tf_username.getText();
        String upassword = pf_password.getText();
        System.out.println("unmae = " + uname + "     upassword = " + upassword);
        try {

//            String resource = "mybatis-config.xml";
//            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()) {
                //方式2
                UserMapper mapper = session.getMapper(UserMapper.class);
                boolean returnv = mapper.addUser(new User(uname,upassword));

                session.commit();

                System.out.println("Insert database , ok? " + returnv);

            }

        }catch(Exception e){
//            System.out.println("Exceptions  ");
            e.printStackTrace();
        }
    }
}