package org.qny;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {
//http://www.voidcn.com/article/p-sdorvxpk-oh.html
//   https://www.cnblogs.com/xiaoliu66007/p/3304835.html fxml javacode
    @FXML
    private TableView<Product> tv_product;
    @FXML
    private TableColumn ttc_name;
    @FXML
    private TableColumn ttc_price;
    // 等价于
    @FXML
    private TableColumn ttc_quantity;

    @FXML
    private TableColumn ttc_per;

    @FXML
    private TableColumn ttc_to;
    //
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_price;
    @FXML
    private TextField tf_quantity;

    @FXML
    private Button tv_name;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void clickTVButton(){

        if (!tv_product.getSelectionModel().isEmpty()) {
            tv_product.getSelectionModel().getSelectedItem().setName("h3");
//            tv_product.getItems().add(new Product("hl",34.2,39));
            tv_product.refresh();
        }

    }
    //Get all of the products
    public ObservableList<Product> getProduct(){
//        ObservableList<Product> products = FXCollections.observableArrayList();
        ObservableList<Product> products = FXCollections.observableArrayList(new Callback<Product, Observable[]>() {
            @Override
            public Observable[] call(Product param) {
                SimpleStringProperty[] arr = new SimpleStringProperty[]{new SimpleStringProperty(param.getName())};
                return arr;
            }
        });
        SimpleStringProperty ht = new SimpleStringProperty("3");
        products.add(new Product("Laptop", 859.00, 20));
        products.add(new Product("Bouncy Ball", 2.49, 198));
        products.add(new Product("Toilet", 99.00, 74));
        products.add(new Product("The Notebook DVD", 19.99, 12));
        products.add(new Product("Corn", 1.49, 856));

        products.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                while (c.next()) {
                    System.out.println("change");
                }
            }
        });
        return products;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tv_product.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ttc_name.setMinWidth(70);
        /**
         * PropertyValueFactory到底是什么？
         */

        ttc_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        ttc_price.setMinWidth(70);

        ttc_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ttc_quantity.setMinWidth(70);
        ttc_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        ttc_per.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ttc_per.setCellFactory(new Callback<TableColumn<Product,Number>, TableCell<Product,Number>>(){

            @Override
            public TableCell<Product, Number> call(TableColumn<Product, Number> param) {
                TableCell<Product, Number>  cell = new TableCell<Product, Number>(){

                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false && item != null) {
                            /**
                             * Hbox控制布局的方式很有用，支持拖拉跟随列变化
                             * 在javafx中一般布局类和textfield支持该功能
                             */
                            HBox hbox = new HBox();
//                            hbox.setStyle("-fx-background-color: #fff344");
                            hbox.setAlignment(Pos.CENTER);;

                            ProgressIndicator progressIndicator = new ProgressIndicator(item.doubleValue()/800.0);
                            hbox.getChildren().add(progressIndicator);
                            this.setGraphic(hbox);

                        }
                    }
                };
                return cell;
            }
        });

        tv_product.setItems(getProduct());
        // 这时候不能再添加了，因为已经注解了
//        tv_product.getColumns().addAll(ttc_name,ttc_price,ttc_quantity);

    }

    //region 按钮点击时间
    //Add button clicked
    @FXML
    public void addButtonClicked(){
        Product product = new Product();
        product.setName(tf_name.getText());
        product.setPrice(Double.parseDouble(tf_price.getText()));
        product.setQuantity(Integer.parseInt(tf_quantity.getText()));
        tv_product.getItems().add(product);
        tf_name.clear();
        tf_price.clear();
        tf_quantity.clear();
    }

    //Delete button clicked
    public void deleteButtonClicked(){
        ObservableList<Product> productSelected, allProducts;
        allProducts = tv_product.getItems();
        productSelected = tv_product.getSelectionModel().getSelectedItems();

        if (productSelected.isEmpty()) {
            System.out.println("事先选定好！");
        }else{
            productSelected.forEach(allProducts::remove);
        }
    }

    //endregion

}
