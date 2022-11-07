package com.example.f22comp1011w9s1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
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
        comboBox.valueProperty().addListener((obs,old,chartSelected)->{
            comboBoxChanged(chartSelected);
        });

        barChart.setLegendVisible(false);

        //configure the searchText field to filter the tableview
        searchTextField.textProperty().addListener((obs, old, searchText)->{
            tableView.getItems().clear();
            tableView.getItems().addAll(customers.stream()
                                        .filter(customer -> customer.contains(searchText))
                                        .collect(Collectors.toList()));
            updateLabels();
        });

        updateLabels();
    }

    private XYChart.Series<String, Integer> getAgeRanges()
    {
        XYChart.Series<String, Integer> ageCounts = new XYChart.Series<>();
        Map<Integer, Long> ageMap = tableView.getItems()
                .stream()
                .collect(Collectors.groupingBy(customer -> customer.getAge()/10,
                        Collectors.counting()));

        System.out.println(ageMap);
        for (Integer age : ageMap.keySet())
        {
            String ageString = age +"0's";
            ageCounts.getData().add(new XYChart.Data<>(ageString,
                                        ageMap.get(age).intValue()));
        }

        return ageCounts;
    }
    private XYChart.Series<String,Integer> getProvinceCounts()
    {
        XYChart.Series<String, Integer> provinceCounts = new XYChart.Series<>();
        Map<String, Long> provinceMap = tableView.getItems()
                .stream()
                .collect(Collectors.groupingBy(customer -> customer.getProvince(),
                        Collectors.counting()));

        //loop over the key set to convert key-value pairs into XYChart.data objects
        for (String province : provinceMap.keySet()) {
            provinceCounts.getData().add(new XYChart.Data<>(province,
                    provinceMap.get(province).intValue()));
        }

        return provinceCounts;
    }

    private XYChart.Series<String, Integer> getBloodTypeBarChartData() {
        XYChart.Series<String, Integer> bloodData = new XYChart.Series<>();

        //create a stream from the Customer objects in the tableview
        //count how many are of each blood type
        Map<String, Long> counterMap = tableView.getItems()
                .stream()
                .collect(Collectors.groupingBy(customer->customer.getBloodType(),
                        Collectors.counting()));

        //Map objects have a key-value pair
        //for example, the key "B+" has a value of 229
        //the key "A+" has a value of 240
        //{B+=229, A+=240, AB+=57, B-=21, AB-=6, A-=33, O+=373, O-=41}
        //counters.keySet() returns the set of keys ("B+","A+","AB+",etc...)
        System.out.println(counterMap.keySet());
        for (String bloodType : counterMap.keySet())
        {
            Integer numCustomersWithBloodType = counterMap.get(bloodType).intValue();
            bloodData.getData().add(new XYChart.Data<>(bloodType,numCustomersWithBloodType));
        }

        return bloodData;
    }

    private void updateLabels()
    {
        customerLabel.setText("Customers in table: "+tableView.getItems().size());

        OptionalDouble avg =tableView.getItems().stream()                           //stream of Customer objects
                                    .mapToDouble(customer -> customer.getAge())     //stream of Double
                                    .average();

        if (avg.isPresent())
            avgAgeLabel.setText(String.format("Avg Age: %.0f",avg.getAsDouble()));
        else
            avgAgeLabel.setText("Avg Age: N/A");

        //if a chart has been selected, update it with the info from the
        //tableview
        if (comboBox.getValue() != null)
        {
            comboBoxChanged(comboBox.getValue());
        }
    }

    private void comboBoxChanged(String chartSelected)
    {
        barChart.getData().clear();

        //load the appropriate data for the barchart
        if (chartSelected.equals("Blood Type"))
            barChart.getData().addAll(getBloodTypeBarChartData());
        else if (chartSelected.equals("Province"))
            barChart.getData().addAll(getProvinceCounts());
        else
            barChart.getData().addAll(getAgeRanges());
    }
}

