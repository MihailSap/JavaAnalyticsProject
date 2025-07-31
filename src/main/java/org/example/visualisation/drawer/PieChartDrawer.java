package org.example.visualisation.drawer;

import org.example.Models.Student;
import org.example.visualisation.mapper.ChartDataMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.PieDataset;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PieChartDrawer extends JFrame {

    public PieChartDrawer(String title, ArrayList<Student> studentList){
        super(title);
        setContentPane(createStudentsByGroupsPanel(studentList));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 300);
    }

    public static JPanel createStudentsByGroupsPanel(ArrayList<Student> studentList){
        JFreeChart chart = createPieChart(ChartDataMapper.createStudentByGroupDataset(studentList));
        return new ChartPanel(chart);
    }

    public static JFreeChart createPieChart(PieDataset dataset){
        JFreeChart chart = ChartFactory.createPieChart(
                "Месяцы рождения студентов",
                dataset,
                false,
                true,
                false
        );

        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 26));
        t.setText("Месяцы рождения студентов");

        return chart;
    }

}


























