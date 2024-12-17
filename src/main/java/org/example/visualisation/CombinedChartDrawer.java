package org.example.visualisation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.Image;

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
        cardPanel.add(createMenuPanel(barChart, pieChart, lineChart), "Menu");
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
        var frame = new CombinedChartDrawer("JavaProject", (ArrayList<Student>) studentList);
        frame.setVisible(true);
    }

    private JPanel createMenuPanel(JFreeChart barChart, JFreeChart pieChart, JFreeChart lineChart) {
        var menuPanel = new JPanel(new BorderLayout());

        var welcomeLabel = new JLabel("Выберите нужный тип диаграммы", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48));

        var buttonPanel = new JPanel(new FlowLayout());
        var barChartButton = getMenuButton(BAR_CHART_BUTTON);
        var pieChartButton = getMenuButton(PIE_CHART_BUTTON);
        var lineChartButton = getMenuButton(LINE_CHART_BUTTON);
        var downloadPdfButton = getDownloadPDFButton(barChart, pieChart, lineChart);
        var exitButton = getExitButton();

        buttonPanel.add(barChartButton);
        buttonPanel.add(pieChartButton);
        buttonPanel.add(lineChartButton);
        buttonPanel.add(downloadPdfButton);
        buttonPanel.add(exitButton);

        menuPanel.add(welcomeLabel, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        return menuPanel;
    }

    private JButton getDownloadPDFButton(JFreeChart barChart, JFreeChart pieChart, JFreeChart lineChart) {
        var downloadPdfButton = new JButton("Скачать отчёт");
        downloadPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var pdfFile = new File("C:\\Users\\msape\\Desktop\\Report.pdf");
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                    document.open();
                    document.add(new Paragraph("Отчёт с диаграммами"));
                    document.add(new Paragraph(" "));
                    addChartToPDF(document, barChart,"Линейчатая диаграмма");
                    addChartToPDF(document, pieChart,"Круговая диаграмма");
                    addChartToPDF(document, lineChart,"График");
                    document.close();
                    JOptionPane.showMessageDialog(null, "Отчёт успешно сохранён в формате PDF: ", "Успешно!", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка при создании отчёта: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        return downloadPdfButton;
    }

    private void addChartToPDF(Document document, JFreeChart chart, String chartTitle) throws IOException, DocumentException {
        var tempFile = File.createTempFile("chart", ".png");
        ChartUtils.saveChartAsPNG(tempFile, chart, 600, 400);
        var image = Image.getInstance(tempFile.getAbsolutePath());
        image.scaleToFit(500, 400);
        document.add(new Paragraph(chartTitle));
        document.add(image);
        document.add(new Paragraph(" "));
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