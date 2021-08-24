package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    //
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_price;
    @FXML
    private TextField tf_quantity;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    //Get all of the products
    public ObservableList<Product> getProduct(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("Laptop", 859.00, 20));
        products.add(new Product("Bouncy Ball", 2.49, 198));
        products.add(new Product("Toilet", 99.00, 74));
        products.add(new Product("The Notebook DVD", 19.99, 12));
        products.add(new Product("Corn", 1.49, 856));
        return products;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ttc_name.setMinWidth(50);
        ttc_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        ttc_price.setMinWidth(30);
        ttc_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ttc_quantity.setMinWidth(30);
        ttc_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tv_product.setItems(getProduct());
        // 这时候不能再添加了，因为已经注解了
//        tv_product.getColumns().addAll(ttc_name,ttc_price,ttc_quantity);

    }
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

}
