package org.example.visualisation;

import org.example.DB.Mapper.StudentsFromDBMapper;
import org.example.Models.Student;
import org.example.visualisation.drawer.LineChartDrawer;
import org.example.visualisation.mapper.ChartDataMapper;
import org.example.visualisation.drawer.BarChartDrawer;
import org.example.visualisation.drawer.PieChartDrawer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CombinedChartDrawer extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private final String BAR_CHART_BUTTON = "Линейчатая Д.";
    private final String PIE_CHART_BUTTON = "Круговая Д.";
    private final String LINE_CHART_BUTTON = "График";

    public CombinedChartDrawer(String title, ArrayList<Student> studentList) {
        super(title);

        var barChart = BarChartDrawer.createBarChart(ChartDataMapper.createPointsDataset(studentList));
        var pieChart = PieChartDrawer.createPieChart(ChartDataMapper.createStudentByGroupDataset(studentList));
        var lineChart = LineChartDrawer.createLineChart(ChartDataMapper.createPointsModulesDataset(studentList));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createMenuPanel(), "Menu");
        cardPanel.add(getPanel(barChart), BAR_CHART_BUTTON);
        cardPanel.add(getPanel(pieChart), PIE_CHART_BUTTON);
        cardPanel.add(getPanel(lineChart), LINE_CHART_BUTTON);
        setContentPane(cardPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout.show(cardPanel, "Menu");
    }

    public static void main(String[] args) {
        var studentList = StudentsFromDBMapper.getStudentsFromEntitys();
        var frame = new CombinedChartDrawer("JavaProject", studentList);
        frame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        var menuPanel = new JPanel(new BorderLayout());

//        var imageLabel = getImage();
//        menuPanel.add(imageLabel, BorderLayout.CENTER);

        var welcomeLabel = new JLabel("Выберите нужный тип диаграммы", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48));

        var buttonPanel = new JPanel(new FlowLayout());
        var barChartButton = getMenuButton(BAR_CHART_BUTTON);
        var pieChartButton = getMenuButton(PIE_CHART_BUTTON);
        var lineChartButton = getMenuButton(LINE_CHART_BUTTON);
        var exitButton = getExitButton();

        buttonPanel.add(barChartButton);
        buttonPanel.add(pieChartButton);
        buttonPanel.add(lineChartButton);
        buttonPanel.add(exitButton);

        menuPanel.add(welcomeLabel, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        return menuPanel;
    }

    private JLabel getImage(){
        var imagePath = "C:\\Users\\msape\\Desktop\\duke.png";
        var originalIcon = new ImageIcon(imagePath);
        var originalImage = originalIcon.getImage();
        var width = 600; // ширина нового изображения
        var height = 600; // высота нового изображения
        var resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        var resizedIcon = new ImageIcon(resizedImage);
        var imageLabel = new JLabel(resizedIcon);
        return imageLabel;
    }

    private JPanel getPanel(JFreeChart pieChart){
        var panel = new JPanel(new BorderLayout());
        panel.add(new ChartPanel(pieChart), BorderLayout.CENTER);
        var savePngButton = getSavePNGButton(pieChart, panel);
        var backButton = getBackButton();
        var buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(savePngButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JButton getMenuButton(String title){
        var button = new JButton(title);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, title);
            }
        });
        return button;
    }

    private JButton getSavePNGButton(JFreeChart pieChart, JPanel panel){
        var savePngButton = new JButton("Сохранить в PNG");
        savePngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var pngFile = new File("C:\\Users\\msape\\Desktop\\Diagram.png");
                    ChartUtils.saveChartAsPNG(pngFile, pieChart, 800, 600);
                    JOptionPane.showMessageDialog(panel, "Диаграмма сохранена в формате PNG", "Успешно!", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Не удалось сохранить диаграмму в формате PNG", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return savePngButton;
    }

    private JButton getBackButton(){
        var backButton = new JButton("Назад");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");
            }
        });
        return backButton;
    }

    private JButton getExitButton(){
        var exitButton = new JButton("Выйти");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return exitButton;
    }
}