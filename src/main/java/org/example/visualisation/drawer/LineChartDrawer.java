package org.example.visualisation.drawer;

import org.example.Models.Student;
import org.example.visualisation.mapper.ChartDataMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LineChartDrawer extends JFrame {
    public LineChartDrawer(String title, ArrayList<Student> studentList) {
        super(title);
        setContentPane(createPointsLineChartPanel(studentList));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 300);
    }

    public static JPanel createPointsLineChartPanel(ArrayList<Student> studentList) {
        JFreeChart chart = createLineChart(ChartDataMapper.createPointsModulesDataset(studentList));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }

    public static JFreeChart createLineChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Зависимость успеваемости всех студентов от модуля",
                "Название модуля",
                "Средний балл",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chart.addSubtitle(new TextTitle("Средний балл всех студентов за каждый модуль"));
        chart.setBackgroundPaint(Color.WHITE);

        return chart;
    }
}
