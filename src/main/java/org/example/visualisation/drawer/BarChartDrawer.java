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

public class BarChartDrawer extends JFrame {
    public BarChartDrawer(String title, ArrayList<Student> studentList) {
        super(title);
        setContentPane(createStudentsByGroupPanel(studentList));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 300);
    }

    public static JPanel createStudentsByGroupPanel(ArrayList<Student> studentList)
    {
        JFreeChart chart = createBarChart(ChartDataMapper.createPointsDataset(studentList));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        return new ChartPanel(chart);
    }

    public static JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Зависимость успеваемости от месяца в дате рождения",
                "Месяц в дате рождения",
                "Итоговое количество баллов",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false
        );

        chart.addSubtitle(new TextTitle("Средний балл каждого месяца"));
        chart.setBackgroundPaint(Color.white);

        return chart;
    }
}
