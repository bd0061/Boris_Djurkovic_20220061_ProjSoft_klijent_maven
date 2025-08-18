package view;

import com.formdev.flatlaf.FlatLightLaf;
import framework.model.KriterijumDescriptor;
import framework.model.KriterijumWrapper;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import iznajmljivanjeapp.domain.Iznajmljivanje;
import iznajmljivanjeapp.domain.Zaposleni;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.RequestManager;
import org.jfree.chart.axis.NumberAxis;
import util.DateUtil;

public class StatistikaPrikaz extends JDialog {

    private final Map<YearMonth, List<Iznajmljivanje>> iznajmljivanjeCache;

    private static final String[] MESECI = {"Januar", "Februar", "Mart", "April", "Maj", "Jun",
        "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"};
    private static final int[] MESECI_PRETHODNI_BROJDANA
            = {
                31,
                31,
                28,
                31,
                30,
                31,
                30,
                31,
                31,
                30,
                31,
                30
            };

    private List<Iznajmljivanje> iznList;
    private int month;
    private int year;
    private JPanel chartContainer;
    private JPanel statsPanel;

    public StatistikaPrikaz(Frame parent, int month, int year, Map<YearMonth, List<Iznajmljivanje>> iznajmljivanjeCache) {
        super(parent, "Statistika iznajmljivanja", true);
        this.month = month;
        this.year = year;
        this.iznajmljivanjeCache = iznajmljivanjeCache;

        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setLocationRelativeTo(parent);
        setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());

        JPanel controlPanel = new JPanel(new BorderLayout());
        JButton prevBtn = new JButton("<");
        JButton nextBtn = new JButton(">");
        JButton osveziBtn = new JButton("Osveži");

        JPanel osveziPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        osveziPanel.add(osveziBtn);

        controlPanel.add(prevBtn, BorderLayout.WEST);
        controlPanel.add(osveziPanel, BorderLayout.CENTER);
        controlPanel.add(nextBtn, BorderLayout.EAST);
        add(controlPanel, BorderLayout.NORTH);

        chartContainer = new JPanel(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);
        statsPanel = new JPanel(new GridLayout(2, 1));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 25, 15));
        statsPanel.setBackground(Color.WHITE);
        add(statsPanel, BorderLayout.SOUTH);

        prevBtn.addActionListener(e -> {
            changeMonth(-1);
            update(false);
        });
        nextBtn.addActionListener(e -> {
            changeMonth(1);
            update(false);
        });

        osveziBtn.addActionListener(e -> {
            iznList = vratiIznajmljivanja();
            if (iznList == null) {
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri osvežavanju statistike za dati mesec.", "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Uspešno osvežavanje statistike za dati mesec.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("mesec: " + this.month + " godina: " + this.year);
                iznajmljivanjeCache.put(YearMonth.of(this.year,this.month),iznList);
            }
            update(true);
        });

        initUI(false);
        pack();
        setLocationRelativeTo(parent);
    }

    private List<Iznajmljivanje> vratiIznajmljivanja() {
        try {
            NetworkResponse r = RequestManager.sendRequest(
                    new NetworkRequest("iznajmljivanje/vrati_sve", new KriterijumWrapper(List.of(
                            new KriterijumDescriptor(Iznajmljivanje.class, "datumSklapanja", ">", DateUtil.date(month - 1 < 1 ? year - 1 : year, month - 1 < 1 ? 12 : month - 1, (month == 3 && DateUtil.isLeapYear(year)) ? 29 : MESECI_PRETHODNI_BROJDANA[month - 1])),
                            new KriterijumDescriptor(Iznajmljivanje.class, "datumSklapanja", "<", DateUtil.date(month + 1 > 12 ? year + 1 : year, month + 1 > 12 ? 1 : month + 1, 1))
                    ),KriterijumWrapper.DepthLevel.SHALLOW)));
            if (!r.success) {
                return null;
            }
            return (List<Iznajmljivanje>) r.payload;
        } catch (Exception ex) {
            return null;
        }
    }

    private void changeMonth(int delta) {
        month += delta;
        if (month < 1) {
            month = 12;
            year--;
        }
        if (month > 12) {
            month = 1;
            year++;
        }
    }

    private void initUI(boolean refreshing) {

        if (!refreshing) {
            iznList = iznajmljivanjeCache.computeIfAbsent(YearMonth.of(year, month), _ -> vratiIznajmljivanja());

            if (iznList == null) {
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri prikazivanju statistike za dati mesec.", "Greška", JOptionPane.ERROR_MESSAGE);
                iznList = new ArrayList<>();
            }
        }

        chartContainer.removeAll();
        statsPanel.removeAll();
        Map<Integer, Integer> dayCount = new TreeMap<>();
        double mesecnaZarada = 0;
        Map<Zaposleni, Double> zaradaZaposleni = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        for (Iznajmljivanje iz : iznList) {
            Date d = iz.getDatumSklapanja();
            cal.setTime(d);
            int m = cal.get(Calendar.MONTH) + 1;
            int y = cal.get(Calendar.YEAR);
            if (m == month && y == year) {
                int day = cal.get(Calendar.DAY_OF_MONTH);
                dayCount.put(day, dayCount.getOrDefault(day, 0) + 1);
                double iznos = iz.getUkupanIznos();
                mesecnaZarada += iznos;
                Zaposleni emp = iz.getZaposleni();
                zaradaZaposleni.put(emp, zaradaZaposleni.getOrDefault(emp, 0.0) + iznos);
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int d = 1; d <= daysInMonth; d++) {
            int count = dayCount.getOrDefault(d, 0);
            dataset.addValue(Integer.valueOf(count), "Broj iznajmljivanja", String.valueOf(d));
        }

        JFreeChart barChart = ChartFactory.createBarChart("Statistika iznajmljivanja za " + MESECI[month - 1] + " " + year + ".",
                "Dan u mesecu",
                "Broj iznajmljivanja",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        Font segoe = new Font("Segoe UI", Font.PLAIN, 14);
        barChart.getTitle().setFont(segoe.deriveFont(Font.BOLD | Font.ITALIC, 24));
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getDomainAxis().setLabelFont(segoe.deriveFont(Font.BOLD, 14));
        plot.getRangeAxis().setLabelFont(segoe.deriveFont(Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(segoe);
        plot.getRangeAxis().setTickLabelFont(segoe);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        range.setNumberFormatOverride(new DecimalFormat("0"));

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.GRAY));
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        Zaposleni topEmp = null;
        double maxRev = 0;
        for (var e : zaradaZaposleni.entrySet()) {
            if (e.getValue() > maxRev) {
                maxRev = e.getValue();
                topEmp = e.getKey();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        String titleMonth = MESECI[month - 1] + " " + year;

        JLabel totalLabel = new JLabel(
                String.format("Ukupan prihod za %s: %.2f RSD", titleMonth, mesecnaZarada), SwingConstants.CENTER);
        totalLabel.setFont(segoe.deriveFont(Font.BOLD, 14));
        JLabel empLabel = new JLabel(
                topEmp != null
                        ? String.format("Zaposleni meseca: %s (%s) — %.2f RSD",
                                topEmp.getIme() + " " + topEmp.getPrezime(), topEmp.getEmail(), maxRev)
                        : "Nema podataka za zadati period", SwingConstants.CENTER);
        empLabel.setFont(segoe.deriveFont(Font.BOLD, 14));

        statsPanel.add(totalLabel);
        statsPanel.add(empLabel);
    }


    private void update(boolean refreshing) {
        initUI(refreshing);
        this.revalidate();
        this.repaint();
    }
}
