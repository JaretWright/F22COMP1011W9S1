package com.example.f22comp1011w9s1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardViewController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TableColumn<Customer, LocalDate> birthdayColumn;

    @FXML
    private TableColumn<Customer, String> bloodTypeColumn;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label customerLabel;

    @FXML
    private Label avgAgeLabel;

    @FXML
    private TableColumn<Customer, String> fullNameColumn;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> provinceColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> cityFilterComboBox;

    @FXML
    private TableView<Customer> tableView;

    ArrayList<Customer> customers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = DBUtility.getCustomers();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        bloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableView.getItems().addAll(customers);



        //configure the combobox
        comboBox.getItems().addAll("Age","Blood Type","Province");
        comboBox.valueProperty().addListener((obs,old,newValue)->{
            barChart.getData().clear();
        });

        barChart.setLegendVisible(false);

        updateLabels();
    }

    private void updateLabels()
    {
        customerLabel.setText("Customers in table: "+tableView.getItems().size());
    }
}

