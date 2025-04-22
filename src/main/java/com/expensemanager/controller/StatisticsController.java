package com.expensemanager.controller;

import com.expensemanager.database.ExpenseDAO;
import com.expensemanager.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @FXML
    private VBox statisticsContainer;

    @FXML
    private Label totalExpenseLabel;

    @FXML
    private PieChart expensePieChart;

    @FXML
    private void initialize() {

    }


    public void startStatisticDaily(String date) {
        try {
            Stage stage = new Stage();
            // Tạo TabPane chính
            TabPane tabPaneStatisticDaily = new TabPane();

            // Tạo Tab 1 - Labels
            Tab tab1 = new Tab("Các Label");
            VBox labelsContainer = new VBox(10);
            labelsContainer.setPadding(new Insets(20));

            Label label1 = new Label("Đây là Label 1");
            Label label2 = new Label("Đây là Label 2");
            Label label3 = new Label("Đây là Label 3");
            Label label4 = new Label("Đây là Label 4");

            labelsContainer.getChildren().addAll(label1, label2, label3, label4);
            tab1.setContent(labelsContainer);

            // Tạo Tab 2 - Biểu đồ tròn
            Tab tab2 = new Tab("Biểu đồ tròn");
            VBox pieChartContainer = new VBox(10);
            pieChartContainer.setPadding(new Insets(20));

            PieChart pieChart = createPieChart(date);

            pieChartContainer.getChildren().add(pieChart);
            tab2.setContent(pieChartContainer);

            // Tạo Tab 3 - Biểu đồ cột
            Tab tab3 = new Tab("Biểu đồ cột");
            VBox barChartContainer = new VBox(10);
            barChartContainer.setPadding(new Insets(20));

            BarChart<String, Number> barChart = createBarChart(date);

            barChartContainer.getChildren().add(barChart);
            tab3.setContent(barChartContainer);

            // Thêm các tab vào TabPane
            tabPaneStatisticDaily.getTabs().addAll(tab1, tab2, tab3);

            // Tạo Scene và hiển thị
            Scene scene = new Scene(tabPaneStatisticDaily, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Thống kê chi tiêu ngày " + date);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PieChart createPieChart(String date) {
        try {
            // Lấy danh sách chi tiêu theo ngày
            List<Expense> expenses = ExpenseDAO.getExpensesByDate(date);
            
            if (expenses.isEmpty()) {
                PieChart emptyChart = new PieChart();
                emptyChart.setTitle("Không có dữ liệu chi tiêu cho ngày " + date);
                return emptyChart;
            }

            // Tính tổng chi tiêu và gộp các chi tiêu cùng category
            double total = 0;
            Map<String, Double> categoryTotals = new HashMap<>();
            
            for (Expense expense : expenses) {
                String category = expense.getCategory();
                double amount = Double.parseDouble(expense.getAmount());
                total += amount;
                
                // Gộp số tiền cho các category giống nhau
                categoryTotals.merge(category, amount, Double::sum);
            }

            // Tạo dữ liệu cho biểu đồ tròn
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                double percentage = (entry.getValue() / total) * 100;
                pieChartData.add(new PieChart.Data(
                    entry.getKey() + " (" + String.format("%.1f", percentage) + "%)",
                    entry.getValue()
                ));
            }

            // Tạo biểu đồ tròn
            PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Thống kê chi tiêu theo danh mục ngày " + date);
            chart.setLegendSide(Side.BOTTOM);
            chart.setStyle("-fx-font-size: 14px;");

            return chart;
        } catch (Exception e) {
            logger.error("Lỗi khi tạo biểu đồ tròn", e);
            PieChart errorChart = new PieChart();
            errorChart.setTitle("Lỗi khi tạo biểu đồ: " + e.getMessage());
            return errorChart;
        }
    }

    private BarChart<String, Number> createBarChart( String date) {
        try {
            // Lấy tất cả chi tiêu
            List<Expense> expenses = ExpenseDAO.getExpensesByDate(date);
            
            if (expenses.isEmpty()) {
                BarChart<String, Number> emptyChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
                emptyChart.setTitle("Không có dữ liệu chi tiêu");
                return emptyChart;
            }

            // Tính tổng chi tiêu theo category
            Map<String, Double> categoryTotals = new HashMap<>();
            for (Expense expense : expenses) {
                String category = expense.getCategory();
                double amount = Double.parseDouble(expense.getAmount());
                categoryTotals.merge(category, amount, Double::sum);
            }

            // Tạo trục cho biểu đồ cột
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Danh mục");
            yAxis.setLabel("Số tiền (VNĐ)");

            // Tạo biểu đồ cột
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Tổng chi tiêu theo danh mục");

            // Tạo dữ liệu cho biểu đồ cột
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Tổng chi tiêu");

            // Thêm dữ liệu cho mỗi category
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            barChart.getData().add(series);
            barChart.setStyle("-fx-font-size: 14px;");

            return barChart;
        } catch (Exception e) {
            logger.error("Lỗi khi tạo biểu đồ cột", e);
            BarChart<String, Number> errorChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
            errorChart.setTitle("Lỗi khi tạo biểu đồ: " + e.getMessage());
            return errorChart;
        }
    }

} 