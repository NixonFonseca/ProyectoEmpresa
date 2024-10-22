/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sena.adso.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Matrix;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class FormularioPOS extends javax.swing.JFrame {

    public FormularioPOS() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana al abrir
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        setLocationRelativeTo(null);
        initComponents();
        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(200);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logos/kfcLogo.png")));
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        colorearTabs();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarCierre(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jrdBtnTop.addActionListener(e -> pnlPrincipal.setTabPlacement(JTabbedPane.TOP));
        jrdBtnLeft.addActionListener(e -> pnlPrincipal.setTabPlacement(JTabbedPane.LEFT));
        jrdBtnBottom.addActionListener(e -> pnlPrincipal.setTabPlacement(JTabbedPane.BOTTOM));
        jrdBtnRight.addActionListener(e -> pnlPrincipal.setTabPlacement(JTabbedPane.RIGHT));

        // Por defecto, seleccionamos el botón "Top"
        jrdBtnTop.setSelected(true);
    }
    private static final int MAX_TABS = 20;
    private static int tabNo = 0;
    private JTabbedPane tabbedPane = null;
//     tabbedPane.setTabPlacement(JTabbedPane.LEFT);
//        tabbedPane.revalidate();  
//        tabbedPane.repaint();

    private void createCustomTabbedPane() {
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("DefaultTab", new JPanel());

        for (int i = 1; i < 7; i++) {
            addNewTab();
        }

        addAddButton();

        tabPanel.add(tabbedPane, BorderLayout.CENTER);
        this.add(tabPanel, BorderLayout.CENTER);
    }

    private void addNewTab() {
        String tabTitle = "Tab " + (++tabNo) + "  ";
        tabbedPane.insertTab(tabTitle, null, new JPanel(), null, tabbedPane.getTabCount() - 1);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 2, new CloseableTabComponent(tabbedPane, tabTitle));
    }

    private void addAddButton() {
        tabbedPane.addTab("+", new JPanel());
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, new AddTabComponent(tabbedPane));
        tabbedPane.setEnabledAt(tabbedPane.getTabCount() - 1, false);
    }

    private static class CloseButton extends JButton {

        private static final ImageIcon CLOSER_ICON = new ImageIcon(CloseButton.class.getResource("/closer.gif"));
        private static final ImageIcon CLOSER_ROLLOVER_ICON = new ImageIcon(CloseButton.class.getResource("/closer_rollover.gif"));
        private static final ImageIcon CLOSER_PRESSED_ICON = new ImageIcon(CloseButton.class.getResource("/closer_pressed.gif"));
        private static final Dimension PREF_SIZE = new Dimension(16, 16);

        public CloseButton() {
            super("");
            setIcon(CLOSER_ICON);
            setRolloverIcon(CLOSER_ROLLOVER_ICON);
            setPressedIcon(CLOSER_PRESSED_ICON);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            setFocusable(false);
        }

        @Override
        public Dimension getPreferredSize() {
            return PREF_SIZE;
        }
    }

    private static class CloseableTabComponent extends JPanel {

        private final JTabbedPane tabbedPane;
        private final JLabel titleLabel;
        private final Font defaultFont;
        private final Font selectedFont;
        private final Color selectedColor;

        public CloseableTabComponent(JTabbedPane aTabbedPane, String title) {
            super(new BorderLayout());
            this.tabbedPane = aTabbedPane;
            setOpaque(false);

            titleLabel = new JLabel(title);
            titleLabel.setOpaque(false);
            defaultFont = titleLabel.getFont().deriveFont(~Font.BOLD);
            selectedFont = titleLabel.getFont().deriveFont(Font.BOLD);
            selectedColor = UIManager.getColor("TabbedPane.selectedForeground");
            JButton closeButton = new CloseButton();
            add(titleLabel, BorderLayout.CENTER);
            add(closeButton, BorderLayout.EAST);

            closeButton.addActionListener(e -> {
                int tabIndex = getTabIndex();
                if (tabIndex >= 0) {
                    tabbedPane.removeTabAt(tabIndex);
                }
                if ((tabbedPane.getTabCount() > 1) && (tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1)) {
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 2);
                }
            });
        }

        private int getTabIndex() {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (this.equals(tabbedPane.getTabComponentAt(i))) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int tabIndex = getTabIndex();
            if (tabIndex >= 0) {
                if (tabIndex == tabbedPane.getSelectedIndex()) {
                    titleLabel.setFont(selectedFont);
                    titleLabel.setForeground(selectedColor);
                } else {
                    titleLabel.setFont(defaultFont);
                    titleLabel.setForeground(tabbedPane.getForegroundAt(tabIndex));
                }
            }
        }
    }

    private class AddTabComponent extends JPanel {

        public AddTabComponent(JTabbedPane tabPane) {
            super(new BorderLayout());
            setOpaque(false);
            JButton addButton = new AddButton();

            addButton.addActionListener(e -> {
                if (tabPane.getTabCount() < MAX_TABS) {
                    addNewTab();
                    SwingUtilities.invokeLater(() -> {
                        if (tabPane.getTabCount() > 1) {
                            tabPane.setSelectedIndex(tabPane.getTabCount() - 2);
                        }
                    });
                }
            });
            add(addButton, BorderLayout.EAST);
        }
    }

    private static class AddButton extends JButton {

        private static final Dimension PREF_SIZE = new Dimension(16, 15);

        public AddButton() {
            super("+");
            setFont(new Font("Dialog", Font.BOLD, 14));
            setForeground(Color.blue);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            setFocusable(false);
        }

        @Override
        public Dimension getPreferredSize() {
            return PREF_SIZE;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        pnlTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pnlPrincipal = new javax.swing.JTabbedPane();
        pnlNuggets = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnParteYComparte = new javax.swing.JButton();
        lbql1 = new javax.swing.JLabel();
        btnComboNuggets = new javax.swing.JButton();
        lqbl2 = new javax.swing.JLabel();
        btnAdicion5Nuggets = new javax.swing.JButton();
        lqbl3 = new javax.swing.JLabel();
        btnAdicion10Nuggets = new javax.swing.JButton();
        btnCombo5Nuggets = new javax.swing.JButton();
        lbql5 = new javax.swing.JLabel();
        lqbl4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlBuckets = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMega3 = new javax.swing.JButton();
        btnmegaFamiliar = new javax.swing.JButton();
        btnMegaVariedadXL = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lqbl6 = new javax.swing.JLabel();
        lqbl7 = new javax.swing.JLabel();
        lqbl8 = new javax.swing.JLabel();
        btnBucketTradicional = new javax.swing.JButton();
        btnMegaFamiliarArepas = new javax.swing.JButton();
        btnMegaFamiliarXL = new javax.swing.JButton();
        lqbl10 = new javax.swing.JLabel();
        lqbl11 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lqbl9 = new javax.swing.JLabel();
        lqbl12 = new javax.swing.JLabel();
        btnMedioKFC = new javax.swing.JButton();
        lqbl13 = new javax.swing.JLabel();
        btnFamiliarKFCArepas = new javax.swing.JButton();
        lqbl14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        btnTrioSupremo = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        pnlCombos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnAltasPicantes = new javax.swing.JButton();
        btn2Presas = new javax.swing.JButton();
        btn3Presas = new javax.swing.JButton();
        lqbl16 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lqbl17 = new javax.swing.JLabel();
        lqbl15 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btnNuggets = new javax.swing.JButton();
        lqbl20 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        bntPopCorn = new javax.swing.JButton();
        lqbl18 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        btnCasero = new javax.swing.JButton();
        lqbl19 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pnlBoxes = new javax.swing.JPanel();
        btnbigBoxPopHelado = new javax.swing.JButton();
        lqbl21 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        btnBigBoxKSandwichNuggets = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        lqbl22 = new javax.swing.JLabel();
        btnBigBoxKCoronel = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        lqbl23 = new javax.swing.JLabel();
        pnl2Personas = new javax.swing.JPanel();
        btnParteComparteAlas = new javax.swing.JButton();
        lqbl24 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lqbl25 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        btnParteComparteMixto = new javax.swing.JButton();
        lqbl26 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        btnParteCompartePresas = new javax.swing.JButton();
        lqbl27 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        btnParteComparteNuggets = new javax.swing.JButton();
        lqbl28 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        btnWowDuoDeluxe = new javax.swing.JButton();
        lqbl29 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        btnWowDuoDeluxeNuggets = new javax.swing.JButton();
        pnlSandwich = new javax.swing.JPanel();
        btnBbqCrunch = new javax.swing.JButton();
        lqbl30 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        btnComboBbqCrunch = new javax.swing.JButton();
        lqbl31 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        btnComboKSandwich = new javax.swing.JButton();
        lqbl32 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        btnKentuckySandwich = new javax.swing.JButton();
        lqbl33 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        btnKentuckyCoronelSandich = new javax.swing.JButton();
        lqbl34 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        btnComboKCoronelSandwich = new javax.swing.JButton();
        lqbl35 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        btnSandwichCrispyBbq = new javax.swing.JButton();
        lqbl36 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        btnComboSandwichCrispyBbq = new javax.swing.JButton();
        lqbl37 = new javax.swing.JLabel();
        pnlIndividual = new javax.swing.JPanel();
        btn6AlitasPicantes = new javax.swing.JButton();
        lqbl38 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        btnPopcornGrande = new javax.swing.JButton();
        jLabel78 = new javax.swing.JLabel();
        lqbl39 = new javax.swing.JLabel();
        btn9AlitasPicantes = new javax.swing.JButton();
        jLabel80 = new javax.swing.JLabel();
        lqbl40 = new javax.swing.JLabel();
        btnPopCornMediano = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        lqbl41 = new javax.swing.JLabel();
        btnAdicion5NuggetsIndividual = new javax.swing.JButton();
        btnpopCornPequeño = new javax.swing.JButton();
        jLabel84 = new javax.swing.JLabel();
        lqbl42 = new javax.swing.JLabel();
        btn20AlitasPicantes = new javax.swing.JButton();
        jLabel86 = new javax.swing.JLabel();
        lqbl43 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        lqbl44 = new javax.swing.JLabel();
        btnAdicion10NuggetsIndividual = new javax.swing.JButton();
        jLabel90 = new javax.swing.JLabel();
        lqbl45 = new javax.swing.JLabel();
        pnlbebidas = new javax.swing.JPanel();
        btnPapaGrande = new javax.swing.JButton();
        lqbl46 = new javax.swing.JLabel();
        btnensaladaColslawGrande = new javax.swing.JButton();
        lqbl47 = new javax.swing.JLabel();
        btnpapaPequeña = new javax.swing.JButton();
        lqbl48 = new javax.swing.JLabel();
        btnensaladaColslawPersonal = new javax.swing.JButton();
        lqbl49 = new javax.swing.JLabel();
        btnGaseosapet400ML = new javax.swing.JButton();
        lqbl50 = new javax.swing.JLabel();
        btn3PresasBebidas = new javax.swing.JButton();
        lqbl51 = new javax.swing.JLabel();
        btn12Arepas = new javax.swing.JButton();
        lqbl52 = new javax.swing.JLabel();
        btnGaseosa15Lts = new javax.swing.JButton();
        lqbl53 = new javax.swing.JLabel();
        btnMrTea = new javax.swing.JButton();
        lqbl54 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        pnlPostres = new javax.swing.JPanel();
        btnAvalanchaOreo = new javax.swing.JButton();
        jLabel109 = new javax.swing.JLabel();
        lqbl55 = new javax.swing.JLabel();
        btnAvalanchaOreoPromo = new javax.swing.JButton();
        jLabel111 = new javax.swing.JLabel();
        lqbl56 = new javax.swing.JLabel();
        btn3AvalanchasOreo = new javax.swing.JButton();
        jLabel113 = new javax.swing.JLabel();
        lqbl57 = new javax.swing.JLabel();
        btnSundareArequipe = new javax.swing.JButton();
        jLabel115 = new javax.swing.JLabel();
        lqbl58 = new javax.swing.JLabel();
        btnSundaeDeChocolate = new javax.swing.JButton();
        jLabel117 = new javax.swing.JLabel();
        lqbl59 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFactura = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtEfectivo = new javax.swing.JTextField();
        txtCambio = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnEliminarIteam = new javax.swing.JButton();
        jrdBtnBottom = new javax.swing.JRadioButton();
        jrdBtnLeft = new javax.swing.JRadioButton();
        jrdBtnTop = new javax.swing.JRadioButton();
        jrdBtnRight = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTitulo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logos/logo.png"))); // NOI18N

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlPrincipal.setForeground(new java.awt.Color(0, 0, 0));

        pnlNuggets.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        btnParteYComparte.setBackground(new java.awt.Color(204, 204, 204));
        btnParteYComparte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuggets/nug_1.png"))); // NOI18N
        btnParteYComparte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteYComparteActionPerformed(evt);
            }
        });

        lbql1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbql1.setForeground(new java.awt.Color(0, 0, 0));
        lbql1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbql1.setText("0");

        btnComboNuggets.setBackground(new java.awt.Color(204, 204, 204));
        btnComboNuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuggets/nug_2.png"))); // NOI18N
        btnComboNuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComboNuggetsActionPerformed(evt);
            }
        });

        lqbl2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl2.setForeground(new java.awt.Color(0, 0, 0));
        lqbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl2.setText("0");

        btnAdicion5Nuggets.setBackground(new java.awt.Color(204, 204, 204));
        btnAdicion5Nuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuggets/nug_3.png"))); // NOI18N
        btnAdicion5Nuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicion5NuggetsActionPerformed(evt);
            }
        });

        lqbl3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl3.setForeground(new java.awt.Color(0, 0, 0));
        lqbl3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl3.setText("0");

        btnAdicion10Nuggets.setBackground(new java.awt.Color(204, 204, 204));
        btnAdicion10Nuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuggets/nug_4.png"))); // NOI18N
        btnAdicion10Nuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicion10NuggetsActionPerformed(evt);
            }
        });

        btnCombo5Nuggets.setBackground(new java.awt.Color(204, 204, 204));
        btnCombo5Nuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuggets/nug_5.png"))); // NOI18N
        btnCombo5Nuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCombo5NuggetsActionPerformed(evt);
            }
        });

        lbql5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbql5.setForeground(new java.awt.Color(0, 0, 0));
        lbql5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbql5.setText("0");

        lqbl4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl4.setForeground(new java.awt.Color(0, 0, 0));
        lqbl4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl4.setText("0");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("NUGGETS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAdicion10Nuggets)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnParteYComparte)
                                .addComponent(lbql1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(52, 52, 52)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnCombo5Nuggets)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lqbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnComboNuggets))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lbql5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(14, 14, 14)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lqbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(232, 232, 232)))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdicion5Nuggets)
                    .addComponent(lqbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnParteYComparte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicion5Nuggets, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnComboNuggets, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbql1)
                            .addComponent(lqbl2)
                            .addComponent(lqbl3))
                        .addGap(18, 18, 18)
                        .addComponent(btnAdicion10Nuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCombo5Nuggets, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbql5)
                    .addComponent(lqbl4))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout pnlNuggetsLayout = new javax.swing.GroupLayout(pnlNuggets);
        pnlNuggets.setLayout(pnlNuggetsLayout);
        pnlNuggetsLayout.setHorizontalGroup(
            pnlNuggetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuggetsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlNuggetsLayout.setVerticalGroup(
            pnlNuggetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuggetsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(273, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Nuggets", pnlNuggets);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        btnMega3.setBackground(new java.awt.Color(204, 204, 204));
        btnMega3.setForeground(new java.awt.Color(255, 255, 255));
        btnMega3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_1.png"))); // NOI18N
        btnMega3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMega3ActionPerformed(evt);
            }
        });

        btnmegaFamiliar.setBackground(new java.awt.Color(204, 204, 204));
        btnmegaFamiliar.setForeground(new java.awt.Color(255, 255, 255));
        btnmegaFamiliar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_2.png"))); // NOI18N
        btnmegaFamiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmegaFamiliarActionPerformed(evt);
            }
        });

        btnMegaVariedadXL.setBackground(new java.awt.Color(204, 204, 204));
        btnMegaVariedadXL.setForeground(new java.awt.Color(255, 255, 255));
        btnMegaVariedadXL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_3.png"))); // NOI18N
        btnMegaVariedadXL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMegaVariedadXLActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("MEGA 3");
        jLabel27.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("MEGA FAMILIAR");
        jLabel28.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("MEGA VARIEDAD XL");
        jLabel29.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl6.setForeground(new java.awt.Color(0, 0, 0));
        lqbl6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl6.setText("0");

        lqbl7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl7.setForeground(new java.awt.Color(0, 0, 0));
        lqbl7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl7.setText("0");

        lqbl8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl8.setForeground(new java.awt.Color(0, 0, 0));
        lqbl8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl8.setText("0");

        btnBucketTradicional.setBackground(new java.awt.Color(204, 204, 204));
        btnBucketTradicional.setForeground(new java.awt.Color(255, 255, 255));
        btnBucketTradicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_4.png"))); // NOI18N
        btnBucketTradicional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBucketTradicionalActionPerformed(evt);
            }
        });

        btnMegaFamiliarArepas.setBackground(new java.awt.Color(204, 204, 204));
        btnMegaFamiliarArepas.setForeground(new java.awt.Color(255, 255, 255));
        btnMegaFamiliarArepas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_5.png"))); // NOI18N
        btnMegaFamiliarArepas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMegaFamiliarArepasActionPerformed(evt);
            }
        });

        btnMegaFamiliarXL.setBackground(new java.awt.Color(204, 204, 204));
        btnMegaFamiliarXL.setForeground(new java.awt.Color(255, 255, 255));
        btnMegaFamiliarXL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_6.png"))); // NOI18N
        btnMegaFamiliarXL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMegaFamiliarXLActionPerformed(evt);
            }
        });

        lqbl10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl10.setForeground(new java.awt.Color(0, 0, 0));
        lqbl10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl10.setText("0");

        lqbl11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl11.setForeground(new java.awt.Color(0, 0, 0));
        lqbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl11.setText("0");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("BUCKET TRADICIONAL");
        jLabel30.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("MEGA FAMILIAR AREPAS");
        jLabel31.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("MEGA FAMILIAR XL");
        jLabel32.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl9.setForeground(new java.awt.Color(0, 0, 0));
        lqbl9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl9.setText("0");

        lqbl12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl12.setForeground(new java.awt.Color(0, 0, 0));
        lqbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl12.setText("0");

        btnMedioKFC.setBackground(new java.awt.Color(204, 204, 204));
        btnMedioKFC.setForeground(new java.awt.Color(255, 255, 255));
        btnMedioKFC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_8.png"))); // NOI18N
        btnMedioKFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedioKFCActionPerformed(evt);
            }
        });

        lqbl13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl13.setForeground(new java.awt.Color(0, 0, 0));
        lqbl13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl13.setText("0");

        btnFamiliarKFCArepas.setBackground(new java.awt.Color(204, 204, 204));
        btnFamiliarKFCArepas.setForeground(new java.awt.Color(255, 255, 255));
        btnFamiliarKFCArepas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_9.png"))); // NOI18N
        btnFamiliarKFCArepas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFamiliarKFCArepasActionPerformed(evt);
            }
        });

        lqbl14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lqbl14.setForeground(new java.awt.Color(0, 0, 0));
        lqbl14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl14.setText("0");

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("FAMILIAR KFC AREPAS");
        jLabel33.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("TRIO SUPREMO");
        jLabel34.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("MEDIO KFC");
        jLabel35.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnTrioSupremo.setBackground(new java.awt.Color(204, 204, 204));
        btnTrioSupremo.setForeground(new java.awt.Color(255, 255, 255));
        btnTrioSupremo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buckets/buck_7.png"))); // NOI18N
        btnTrioSupremo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrioSupremoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("BUCKETS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lqbl9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lqbl10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lqbl11, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnBucketTradicional)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lqbl6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(lqbl7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(lqbl8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(btnMegaFamiliarArepas)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnMegaFamiliarXL))))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lqbl12, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnTrioSupremo, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lqbl13, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnMedioKFC, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lqbl14, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnFamiliarKFCArepas, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnMega3)
                                .addGap(18, 18, 18)
                                .addComponent(btnmegaFamiliar)
                                .addGap(30, 30, 30)
                                .addComponent(btnMegaVariedadXL))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(jLabel8)))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMegaVariedadXL, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmegaFamiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMega3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(1, 1, 1)
                        .addComponent(lqbl8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lqbl7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBucketTradicional, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMegaFamiliarArepas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMegaFamiliarXL, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lqbl11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lqbl10)))
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTrioSupremo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lqbl12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnFamiliarKFCArepas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMedioKFC, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lqbl13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lqbl14, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout pnlBucketsLayout = new javax.swing.GroupLayout(pnlBuckets);
        pnlBuckets.setLayout(pnlBucketsLayout);
        pnlBucketsLayout.setHorizontalGroup(
            pnlBucketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBucketsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBucketsLayout.setVerticalGroup(
            pnlBucketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBucketsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Buckets", pnlBuckets);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));

        btnAltasPicantes.setBackground(new java.awt.Color(204, 204, 204));
        btnAltasPicantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/3-removebg-preview.png"))); // NOI18N
        btnAltasPicantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltasPicantesActionPerformed(evt);
            }
        });

        btn2Presas.setBackground(new java.awt.Color(204, 204, 204));
        btn2Presas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/1-removebg-preview.png"))); // NOI18N
        btn2Presas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2PresasActionPerformed(evt);
            }
        });

        btn3Presas.setBackground(new java.awt.Color(204, 204, 204));
        btn3Presas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/2-removebg-preview.png"))); // NOI18N
        btn3Presas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3PresasActionPerformed(evt);
            }
        });

        lqbl16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl16.setForeground(new java.awt.Color(0, 0, 0));
        lqbl16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl16.setText("0");

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("ALITAS PICANTES");
        jLabel39.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl17.setForeground(new java.awt.Color(0, 0, 0));
        lqbl17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl17.setText("0");

        lqbl15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl15.setForeground(new java.awt.Color(0, 0, 0));
        lqbl15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl15.setText("0");

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("2 PRESAS");
        jLabel36.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("3 PRESAS");
        jLabel37.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnNuggets.setBackground(new java.awt.Color(204, 204, 204));
        btnNuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/6-removebg-preview.png"))); // NOI18N
        btnNuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuggetsActionPerformed(evt);
            }
        });

        lqbl20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl20.setForeground(new java.awt.Color(0, 0, 0));
        lqbl20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl20.setText("0");

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("POP CORN");
        jLabel41.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        bntPopCorn.setBackground(new java.awt.Color(204, 204, 204));
        bntPopCorn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/4-removebg-preview.png"))); // NOI18N
        bntPopCorn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPopCornActionPerformed(evt);
            }
        });

        lqbl18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl18.setForeground(new java.awt.Color(0, 0, 0));
        lqbl18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl18.setText("0");

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("CASERO");
        jLabel43.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnCasero.setBackground(new java.awt.Color(204, 204, 204));
        btnCasero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Combos/5-removebg-preview.png"))); // NOI18N
        btnCasero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaseroActionPerformed(evt);
            }
        });

        lqbl19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl19.setForeground(new java.awt.Color(0, 0, 0));
        lqbl19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl19.setText("0");

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("NUGGETS");
        jLabel45.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("COMBOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bntPopCorn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lqbl15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn2Presas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lqbl16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn3Presas, javax.swing.GroupLayout.PREFERRED_SIZE, 145, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lqbl17, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAltasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lqbl20, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lqbl18, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lqbl19, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCasero, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(29, 29, 29)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(78, 78, 78))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn3Presas, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAltasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel39)
                        .addGap(65, 65, 65)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(bntPopCorn, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel45))
                                .addGap(1, 1, 1)
                                .addComponent(lqbl18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnCasero, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lqbl19)
                                    .addComponent(lqbl20)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn2Presas, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl16)
                            .addComponent(lqbl15)
                            .addComponent(lqbl17))))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout pnlCombosLayout = new javax.swing.GroupLayout(pnlCombos);
        pnlCombos.setLayout(pnlCombosLayout);
        pnlCombosLayout.setHorizontalGroup(
            pnlCombosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCombosLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnlCombosLayout.setVerticalGroup(
            pnlCombosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCombosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Combos", pnlCombos);

        btnbigBoxPopHelado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Boxes/1-removebg-preview.png"))); // NOI18N
        btnbigBoxPopHelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbigBoxPopHeladoActionPerformed(evt);
            }
        });

        lqbl21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl21.setText("0");

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("BIG BOX POP HELADO");
        jLabel47.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnBigBoxKSandwichNuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Boxes/2-removebg-preview.png"))); // NOI18N
        btnBigBoxKSandwichNuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBigBoxKSandwichNuggetsActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("BIG BOX K. SANDWICH NUGGETS");
        jLabel48.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl22.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl22.setText("0");

        btnBigBoxKCoronel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Boxes/3-removebg-preview.png"))); // NOI18N
        btnBigBoxKCoronel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBigBoxKCoronelActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("BIG BOX KENTUCKY CORONEL");
        jLabel50.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl23.setText("0");

        javax.swing.GroupLayout pnlBoxesLayout = new javax.swing.GroupLayout(pnlBoxes);
        pnlBoxes.setLayout(pnlBoxesLayout);
        pnlBoxesLayout.setHorizontalGroup(
            pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBoxesLayout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnbigBoxPopHelado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBoxesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lqbl21, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBigBoxKSandwichNuggets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel48)))
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lqbl22, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lqbl23, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBigBoxKCoronel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel50))
                .addGap(43, 43, 43))
        );
        pnlBoxesLayout.setVerticalGroup(
            pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoxesLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addComponent(btnBigBoxKSandwichNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48))
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addComponent(btnBigBoxKCoronel)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel50)
                        .addGap(1, 1, 1)
                        .addComponent(lqbl23))
                    .addGroup(pnlBoxesLayout.createSequentialGroup()
                        .addComponent(btnbigBoxPopHelado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBoxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl21)
                            .addComponent(lqbl22))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Boxes", pnlBoxes);

        btnParteComparteAlas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/1-removebg-preview (1).png"))); // NOI18N
        btnParteComparteAlas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteComparteAlasActionPerformed(evt);
            }
        });

        lqbl24.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl24.setText("0");

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("PARTE Y COPARTE ALAS");
        jLabel52.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl25.setText("0");

        jLabel54.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("PARTE Y COMPARTE MIXTO");
        jLabel54.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnParteComparteMixto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/2-removebg-preview (2).png"))); // NOI18N
        btnParteComparteMixto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteComparteMixtoActionPerformed(evt);
            }
        });

        lqbl26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl26.setText("0");

        jLabel56.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("PARTE Y COMPARTE PRESAS");
        jLabel56.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnParteCompartePresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/3-removebg-preview (1).png"))); // NOI18N
        btnParteCompartePresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteCompartePresasActionPerformed(evt);
            }
        });

        lqbl27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl27.setText("0");

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("PARTE Y COMPARTE NUGGETS");
        jLabel58.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnParteComparteNuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/4-removebg-preview.png"))); // NOI18N
        btnParteComparteNuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteComparteNuggetsActionPerformed(evt);
            }
        });

        lqbl28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl28.setText("0");

        jLabel60.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("WOW DUO DELUXE");
        jLabel60.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnWowDuoDeluxe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/5-removebg-preview.png"))); // NOI18N
        btnWowDuoDeluxe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWowDuoDeluxeActionPerformed(evt);
            }
        });

        lqbl29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl29.setText("0");

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("WOW DUO DELUXE NUGGETS");
        jLabel62.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnWowDuoDeluxeNuggets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DosPersonas/6-removebg-preview.png"))); // NOI18N
        btnWowDuoDeluxeNuggets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWowDuoDeluxeNuggetsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl2PersonasLayout = new javax.swing.GroupLayout(pnl2Personas);
        pnl2Personas.setLayout(pnl2PersonasLayout);
        pnl2PersonasLayout.setHorizontalGroup(
            pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnParteComparteAlas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                        .addComponent(lqbl24, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2PersonasLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addComponent(btnParteComparteNuggets))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2PersonasLayout.createSequentialGroup()
                        .addComponent(lqbl27, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnParteComparteMixto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lqbl25, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnParteCompartePresas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(lqbl26, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnWowDuoDeluxe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2PersonasLayout.createSequentialGroup()
                                .addComponent(lqbl28, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnWowDuoDeluxeNuggets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lqbl29, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        pnl2PersonasLayout.setVerticalGroup(
            pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addComponent(btnParteComparteMixto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54)
                        .addGap(3, 3, 3)
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl25)
                            .addComponent(lqbl24)
                            .addComponent(lqbl26))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addComponent(btnParteComparteAlas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel52))
                            .addGroup(pnl2PersonasLayout.createSequentialGroup()
                                .addComponent(btnParteCompartePresas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel56)))
                        .addGap(45, 45, 45)))
                .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addComponent(btnParteComparteNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58)
                        .addGap(3, 3, 3)
                        .addGroup(pnl2PersonasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl27)
                            .addComponent(lqbl28)))
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addComponent(btnWowDuoDeluxe, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel60)
                        .addGap(3, 3, 3)
                        .addComponent(lqbl29))
                    .addGroup(pnl2PersonasLayout.createSequentialGroup()
                        .addComponent(btnWowDuoDeluxeNuggets, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel62)))
                .addContainerGap(319, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("2 Personas", pnl2Personas);

        btnBbqCrunch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/1-removebg-preview.png"))); // NOI18N
        btnBbqCrunch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBbqCrunchActionPerformed(evt);
            }
        });

        lqbl30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl30.setText("0");

        jLabel63.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("BBQ CRUNCH");
        jLabel63.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("COMBO BBQ CRUNCH");
        jLabel64.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnComboBbqCrunch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/2-removebg-preview.png"))); // NOI18N
        btnComboBbqCrunch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComboBbqCrunchActionPerformed(evt);
            }
        });

        lqbl31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl31.setText("0");

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("COMBO K. SANDWICH");
        jLabel65.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnComboKSandwich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/3-removebg-preview.png"))); // NOI18N

        lqbl32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl32.setText("0");

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("KENTUCKY SANDWICH");
        jLabel67.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnKentuckySandwich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/4-removebg-preview.png"))); // NOI18N

        lqbl33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl33.setText("0");

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("KENTUCKY CORONEL S.");
        jLabel69.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnKentuckyCoronelSandich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/5-removebg-preview.png"))); // NOI18N

        lqbl34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl34.setText("0");

        jLabel71.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("COMBO K. CORONEL S.");
        jLabel71.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnComboKCoronelSandwich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/6-removebg-preview.png"))); // NOI18N

        lqbl35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl35.setText("0");

        jLabel73.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("SANDWICH CRISPY BBQ");
        jLabel73.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnSandwichCrispyBbq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/7-removebg-preview.png"))); // NOI18N

        lqbl36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl36.setText("0");

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("COMBO S. CRISPY BBQ");
        jLabel75.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnComboSandwichCrispyBbq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sandwiches/8-removebg-preview.png"))); // NOI18N

        lqbl37.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl37.setText("0");

        javax.swing.GroupLayout pnlSandwichLayout = new javax.swing.GroupLayout(pnlSandwich);
        pnlSandwich.setLayout(pnlSandwichLayout);
        pnlSandwichLayout.setHorizontalGroup(
            pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSandwichLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBbqCrunch, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lqbl30, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSandwichLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(btnComboBbqCrunch, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lqbl31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnComboKSandwich, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lqbl32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(154, 154, 154))
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSandwichLayout.createSequentialGroup()
                                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSandwichCrispyBbq, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lqbl36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lqbl37, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnComboSandwichCrispyBbq, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlSandwichLayout.createSequentialGroup()
                                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lqbl33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnKentuckySandwich, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lqbl34, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnKentuckyCoronelSandich, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28)
                                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lqbl35, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnComboKCoronelSandwich, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlSandwichLayout.setVerticalGroup(
            pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSandwichLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBbqCrunch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnComboBbqCrunch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(btnComboKSandwich, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(jLabel64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl32)
                            .addComponent(lqbl31)))
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSandwichLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnKentuckySandwich, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl33))
                    .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlSandwichLayout.createSequentialGroup()
                            .addComponent(btnComboKCoronelSandwich, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel71)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lqbl35))
                        .addGroup(pnlSandwichLayout.createSequentialGroup()
                            .addComponent(btnKentuckyCoronelSandich, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel69)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lqbl34))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSandwichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addComponent(btnSandwichCrispyBbq, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl36))
                    .addGroup(pnlSandwichLayout.createSequentialGroup()
                        .addComponent(btnComboSandwichCrispyBbq, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl37)))
                .addContainerGap(192, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Sandwich", pnlSandwich);

        btn6AlitasPicantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/1-removebg-preview.png"))); // NOI18N

        lqbl38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl38.setText("0");

        jLabel77.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("6 ALITAS PICANTES");
        jLabel77.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btnPopcornGrande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/2-removebg-preview.png"))); // NOI18N

        jLabel78.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("POPCORN GRANDE");
        jLabel78.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl39.setText("0");

        btn9AlitasPicantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/3-removebg-preview.png"))); // NOI18N

        jLabel80.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("9 ALITAS PICANTES");
        jLabel80.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl40.setText("0");

        btnPopCornMediano.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/4-removebg-preview.png"))); // NOI18N

        jLabel82.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setText("POPCORN MEDIANO");
        jLabel82.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl41.setText("0");

        btnAdicion5NuggetsIndividual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/7-removebg-preview.png"))); // NOI18N

        btnpopCornPequeño.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/5-removebg-preview.png"))); // NOI18N

        jLabel84.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("POPCORN PEQUEÑO");
        jLabel84.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl42.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl42.setText("0");

        btn20AlitasPicantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/6-removebg-preview.png"))); // NOI18N

        jLabel86.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText("20 ALITAS PICANTES");
        jLabel86.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl43.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl43.setText("0");

        jLabel88.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("ADICION 5 NUGGETS");
        jLabel88.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl44.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl44.setText("0");

        btnAdicion10NuggetsIndividual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Individuales/8-removebg-preview.png"))); // NOI18N

        jLabel90.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("ADICIÓN 10 NUGGETS");
        jLabel90.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl45.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl45.setText("0");

        javax.swing.GroupLayout pnlIndividualLayout = new javax.swing.GroupLayout(pnlIndividual);
        pnlIndividual.setLayout(pnlIndividualLayout);
        pnlIndividualLayout.setHorizontalGroup(
            pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIndividualLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn6AlitasPicantes, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lqbl38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnPopCornMediano, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lqbl41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlIndividualLayout.createSequentialGroup()
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnPopcornGrande, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lqbl39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lqbl40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(btn9AlitasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnlIndividualLayout.createSequentialGroup()
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnpopCornPequeño, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lqbl42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn20AlitasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lqbl43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lqbl44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdicion5NuggetsIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdicion10NuggetsIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lqbl45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        pnlIndividualLayout.setVerticalGroup(
            pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIndividualLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addComponent(btn9AlitasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel80)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl40))
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addComponent(btnPopcornGrande, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl39))
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addComponent(btn6AlitasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl38)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addComponent(btnPopCornMediano, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lqbl41))
                    .addGroup(pnlIndividualLayout.createSequentialGroup()
                        .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlIndividualLayout.createSequentialGroup()
                                .addComponent(btnpopCornPequeño, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel86))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lqbl42)
                                    .addComponent(lqbl43)))
                            .addComponent(btn20AlitasPicantes, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdicion5NuggetsIndividual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdicion10NuggetsIndividual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jLabel90))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lqbl44)
                    .addComponent(lqbl45))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Individuales", pnlIndividual);

        btnPapaGrande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/1-removebg-preview (1).png"))); // NOI18N

        lqbl46.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl46.setText("0");

        btnensaladaColslawGrande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/2-removebg-preview (1).png"))); // NOI18N

        lqbl47.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl47.setText("0");

        btnpapaPequeña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/3-removebg-preview (1).png"))); // NOI18N

        lqbl48.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl48.setText("0");

        btnensaladaColslawPersonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/4-removebg-preview (1).png"))); // NOI18N

        lqbl49.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl49.setText("0");

        btnGaseosapet400ML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/5-removebg-preview (1).png"))); // NOI18N

        lqbl50.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl50.setText("0");

        btn3PresasBebidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/6-removebg-preview (1).png"))); // NOI18N

        lqbl51.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl51.setText("0");

        btn12Arepas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/7-removebg-preview (1).png"))); // NOI18N

        lqbl52.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl52.setText("0");

        btnGaseosa15Lts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/8-re.png"))); // NOI18N

        lqbl53.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl53.setText("0");

        btnMrTea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bebidas/9.png"))); // NOI18N

        lqbl54.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl54.setText("0");

        jLabel100.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("PAPA GRANDE");
        jLabel100.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel101.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setText("ENSALADA COL SLAW GRAN.");
        jLabel101.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel102.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("12 AREPAS");
        jLabel102.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel103.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("ENSALADA COLSLAW PERS.");
        jLabel103.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel104.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setText("PAPA PQUEÑA");
        jLabel104.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel105.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText("GASEOSA PET 400 ML");
        jLabel105.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel106.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setText("3 PRESAS");
        jLabel106.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel107.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("MR TEA");
        jLabel107.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel108.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("GAEOSA 1,5 LTS");
        jLabel108.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout pnlbebidasLayout = new javax.swing.GroupLayout(pnlbebidas);
        pnlbebidas.setLayout(pnlbebidasLayout);
        pnlbebidasLayout.setHorizontalGroup(
            pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlbebidasLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn12Arepas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lqbl49, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnensaladaColslawPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPapaGrande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lqbl46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lqbl52, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGaseosapet400ML, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lqbl50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGaseosa15Lts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMrTea)
                                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lqbl54, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lqbl51, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn3PresasBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(74, Short.MAX_VALUE))))
                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnensaladaColslawGrande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lqbl47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnpapaPequeña, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lqbl48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel108, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                .addComponent(lqbl53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        pnlbebidasLayout.setVerticalGroup(
            pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlbebidasLayout.createSequentialGroup()
                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addComponent(btnpapaPequeña, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lqbl48))
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addComponent(btnensaladaColslawGrande, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lqbl47))
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addComponent(btnPapaGrande, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel100)
                                    .addComponent(jLabel101)
                                    .addComponent(jLabel104))
                                .addGap(1, 1, 1)
                                .addComponent(lqbl46)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addComponent(btn3PresasBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lqbl51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn12Arepas, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlbebidasLayout.createSequentialGroup()
                                .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                                        .addComponent(btnensaladaColslawPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)
                                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel103)
                                            .addComponent(jLabel105)
                                            .addComponent(jLabel106))
                                        .addGap(1, 1, 1)
                                        .addComponent(lqbl49))
                                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                                        .addComponent(btnGaseosapet400ML, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lqbl50)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGaseosa15Lts, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel102)
                        .addGap(2, 2, 2)
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lqbl52)
                            .addComponent(lqbl53)
                            .addComponent(lqbl54)))
                    .addGroup(pnlbebidasLayout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(btnMrTea, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlbebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel107)
                            .addComponent(jLabel108))))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Bebidas", pnlbebidas);

        btnAvalanchaOreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Postres/1-removebg-preview.png"))); // NOI18N

        jLabel109.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText("AVALANCHA OREO");
        jLabel109.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl55.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl55.setText("0");

        btnAvalanchaOreoPromo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Postres/2-removebg-preview.png"))); // NOI18N

        jLabel111.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("AVALANCHA OREO PROMO");
        jLabel111.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl56.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl56.setText("0");

        btn3AvalanchasOreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Postres/3-removebg-preview.png"))); // NOI18N

        jLabel113.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("3 AVALANCHAS OREO");
        jLabel113.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl57.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl57.setText("0");

        btnSundareArequipe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Postres/4-removebg-preview.png"))); // NOI18N

        jLabel115.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setText("SUNADE AREQUIPE");
        jLabel115.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl58.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl58.setText("0");

        btnSundaeDeChocolate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Postres/5-removebg-preview.png"))); // NOI18N

        jLabel117.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setText("SUNDAE DE CHOCOLATE");
        jLabel117.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lqbl59.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lqbl59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lqbl59.setText("0");

        javax.swing.GroupLayout pnlPostresLayout = new javax.swing.GroupLayout(pnlPostres);
        pnlPostres.setLayout(pnlPostresLayout);
        pnlPostresLayout.setHorizontalGroup(
            pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPostresLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnSundareArequipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lqbl58, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnAvalanchaOreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lqbl55, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSundaeDeChocolate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPostresLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnAvalanchaOreoPromo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lqbl56, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPostresLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lqbl59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn3AvalanchasOreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlPostresLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lqbl57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(100, 100, 100))
        );
        pnlPostresLayout.setVerticalGroup(
            pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPostresLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPostresLayout.createSequentialGroup()
                        .addComponent(btn3AvalanchasOreo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel113)
                        .addGap(1, 1, 1)
                        .addComponent(lqbl57))
                    .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPostresLayout.createSequentialGroup()
                            .addComponent(btnAvalanchaOreoPromo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addComponent(jLabel111)
                            .addGap(1, 1, 1)
                            .addComponent(lqbl56))
                        .addGroup(pnlPostresLayout.createSequentialGroup()
                            .addComponent(btnAvalanchaOreo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addComponent(jLabel109)
                            .addGap(1, 1, 1)
                            .addComponent(lqbl55))))
                .addGap(28, 28, 28)
                .addGroup(pnlPostresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPostresLayout.createSequentialGroup()
                        .addComponent(btnSundareArequipe, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel115)
                        .addGap(1, 1, 1)
                        .addComponent(lqbl58))
                    .addGroup(pnlPostresLayout.createSequentialGroup()
                        .addComponent(btnSundaeDeChocolate, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel117)
                        .addGap(1, 1, 1)
                        .addComponent(lqbl59)))
                .addContainerGap(321, Short.MAX_VALUE))
        );

        pnlPrincipal.addTab("Postres", pnlPostres);

        tblDetalle.setBackground(new java.awt.Color(204, 204, 204));
        tblDetalle.setForeground(new java.awt.Color(0, 0, 0));
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Iteam", "Qty", "Precio"
            }
        ));
        jScrollPane1.setViewportView(tblDetalle);

        txtFactura.setEditable(false);
        txtFactura.setBackground(new java.awt.Color(255, 255, 255));
        txtFactura.setColumns(20);
        txtFactura.setRows(5);
        jScrollPane2.setViewportView(txtFactura);

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel2.setText("TOTAL");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setText("EFECTIVO");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("CAMBIO");

        txtTotal.setEditable(false);

        txtEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEfectivoActionPerformed(evt);
            }
        });

        txtCambio.setEditable(false);

        btnPagar.setBackground(new java.awt.Color(102, 153, 255));
        btnPagar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ButtonIcons/pagar.png"))); // NOI18N
        btnPagar.setText("Pagar");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(153, 255, 153));
        btnNuevo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ButtonIcons/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnFacturar.setBackground(new java.awt.Color(102, 255, 204));
        btnFacturar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ButtonIcons/factura.png"))); // NOI18N
        btnFacturar.setText("Facturar");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(255, 255, 153));
        btnImprimir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ButtonIcons/imprimir.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("PRECIO");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("OPCIONES");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(194, 194, 194))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(16, 16, 16))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnEliminarIteam.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnEliminarIteam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ButtonIcons/eliminar.png"))); // NOI18N
        btnEliminarIteam.setText("Eliminar");
        btnEliminarIteam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarIteamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminarIteam)
                .addGap(312, 312, 312))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEliminarIteam)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        buttonGroup.add(jrdBtnBottom);
        jrdBtnBottom.setText("BOTTOM");

        buttonGroup.add(jrdBtnLeft);
        jrdBtnLeft.setText("LEFT");

        buttonGroup.add(jrdBtnTop);
        jrdBtnTop.setText("TOP");
        jrdBtnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrdBtnTopActionPerformed(evt);
            }
        });

        buttonGroup.add(jrdBtnRight);
        jrdBtnRight.setText("RIGNHT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(224, 224, 224)
                                .addComponent(jrdBtnTop)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrdBtnLeft)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrdBtnBottom)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrdBtnRight)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrdBtnBottom)
                            .addComponent(jrdBtnLeft)
                            .addComponent(jrdBtnTop)
                            .addComponent(jrdBtnRight)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmarCierre() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro en que desea cerrar la aplicación?",
                "Confirmar cierre",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void colorearTabs() {
        Color[] colores = {
            new Color(255, 0, 0), //Rojo - Nuggets
            new Color(204, 255, 204), //Verde claro - Buckets
            new Color(204, 204, 255), //Azul claro - Combos
            new Color(255, 255, 204), //Amarillo claro - Boxes
            new Color(255, 204, 255), //Lavanda - 2 Personas
            new Color(204, 255, 255), //Cyan - Sandwiches
            new Color(255, 229, 204), //Melocotón claro - Individuales
            new Color(255, 204, 153), //Naranja Claro - Bebidas
            new Color(50, 205, 50), //Verde lima - Postres
            new Color(25, 250, 21) //oro Claro Krunchy
        };
        for (int i = 0; i < pnlPrincipal.getTabCount(); i++) {
            pnlPrincipal.setBackgroundAt(i, colores[i]);
        }
        pnlPrincipal.updateUI();
    }

    public void addTable(int id, String Nombre, int Qty, Double Precio) {
        DefaultTableModel dt = (DefaultTableModel) tblDetalle.getModel();

        Vector v = new Vector();

        v.add(id);
        v.add(Nombre);
        v.add(Qty);
        v.add(Precio);
        dt.addRow(v);
    }

    public void addTables(int id, String nombre, int qty, Double precio) {
        DefaultTableModel dt = (DefaultTableModel) tblDetalle.getModel();
        DecimalFormat df = new DecimalFormat("0.00");

        double totalPrecio = precio * qty;

        for (int row = 0; row < tblDetalle.getRowCount(); row++) {
            if (nombre.equals(tblDetalle.getValueAt(row, 1))) {
                dt.removeRow(tblDetalle.convertRowIndexToModel(row));
            }
        }

        Vector v = new Vector();

        v.add(id);
        v.add(nombre);
        v.add(qty);
        v.add(totalPrecio);
        dt.addRow(v);
        cal();
    }

    public void cal() {
        int numOfRow = tblDetalle.getRowCount();
        double tot = 0;

        for (int i = 0; i < numOfRow; i++) {
            try {
                double value = Double.parseDouble(tblDetalle.getValueAt(i, 3).toString());
                tot += value;
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
//        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat formatoColombia = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        txtTotal.setText(formatoColombia.format(tot));
    }


    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarIteamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarIteamActionPerformed

    }//GEN-LAST:event_btnEliminarIteamActionPerformed

    private void txtEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEfectivoActionPerformed

// Método para cargar los IDs desde el archivo
    private int cargarIdDesdeArchivo(String botonNombre) {
        try (BufferedReader reader = new BufferedReader(new FileReader("button_ids.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equals(botonNombre)) {
                    return Integer.parseInt(parts[1]); // Devolver el ID guardado

                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el ID desde el archivo: " + e.getMessage());
        }
        return 0; // Si no se encuentra, se devuelve 0
    }
    // Método para guardar los IDs en el archivo

    private void guardarIdEnArchivo(String botonNombre, int id) {
        File file = new File("button_ids.txt");
        StringBuilder newFileContent = new StringBuilder();

        // Cargar el contenido del archivo actual
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean idUpdated = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equals(botonNombre)) {
                    newFileContent.append(botonNombre).append("=").append(id).append("\n");
                    idUpdated = true;
                } else {
                    newFileContent.append(line).append("\n");
                }
            }

            // Si no se actualizó el ID, agregarlo al final
            if (!idUpdated) {
                newFileContent.append(botonNombre).append("=").append(id).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        // Guardar el nuevo contenido en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(newFileContent.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(newFileContent.toString());
            writer.write("currentId=" + currentId + "\n"); // Guardar el currentId al final
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private void cargarCurrentIdDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("button_ids.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equals("currentId")) {
                    currentId = Integer.parseInt(parts[1]); // Asigna el currentId desde el archivo
                } else {
                    // Cargar los IDs de los botones
                    String botonNombre = parts[0];
                    int id = Integer.parseInt(parts[1]);
                    // Aquí puedes agregar lógica para almacenar o utilizar el ID, si es necesario
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el ID desde el archivo: " + e.getMessage());
        }
    }
    private int currentId = 1;

    private void actualizarTabla(String nombre, JLabel lblCantidad, double precio, String botonNombre) {
        int qty = Integer.parseInt(lblCantidad.getText()); // Obtener cantidad actual
        qty++; // Incrementar la cantidad
        lblCantidad.setText(String.valueOf(qty)); // Actualizar el JLabel

        // Verifica si ya existe una fila con el mismo nombre en la tabl a
        boolean exists = false;
        int idActual = cargarIdDesdeArchivo(botonNombre); // Cargar el ID desde el archivo o 0 si no existe

        if (idActual == 0) { // Si no existe un ID, asignar uno nuevo
            idActual = currentId++;
            guardarIdEnArchivo(botonNombre, idActual); // Guardar el nuevo ID
        }

        for (int row = 0; row < tblDetalle.getRowCount(); row++) {
            if (nombre.equals(tblDetalle.getValueAt(row, 1))) {
                exists = true;
                break;
            }
        }

        // Si el producto no existe, agrega una nueva fila
        if (!exists) {
            addTables(idActual, nombre, qty, precio);
        } else {
            // Si ya existe, simplemente actualiza la cantidad
            addTables(idActual, nombre, qty, precio);
        }

        cal(); // Llama a cal() si es necesario
    }

    private void btnAdicion5NuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicion5NuggetsActionPerformed
        actualizarTabla("Adicion 5 nuggets", lqbl3, 9900.00, "btnAdicion5Nuggets");
    }//GEN-LAST:event_btnAdicion5NuggetsActionPerformed

    private void btnComboNuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComboNuggetsActionPerformed

        actualizarTabla("Combo Nuggets", lqbl2, 19900.00, "btnComboNuggetsAction");
    }//GEN-LAST:event_btnComboNuggetsActionPerformed

    private void btnParteYComparteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteYComparteActionPerformed
        actualizarTabla("Parte y Comparte", lbql1, 39900.00, "btnParteYComparte");
    }//GEN-LAST:event_btnParteYComparteActionPerformed

    private void btnAdicion10NuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicion10NuggetsActionPerformed

        actualizarTabla("Adicion 10 Nuggets", lqbl4, 14900.00, "btnAdicion10Nuggets");

    }//GEN-LAST:event_btnAdicion10NuggetsActionPerformed

    private void btnCombo5NuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCombo5NuggetsActionPerformed

        actualizarTabla("Combo5Nuggets", lbql5, 14900.00, "btnCombo5Nuggets");

    }//GEN-LAST:event_btnCombo5NuggetsActionPerformed

    private void btnMega3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMega3ActionPerformed

        actualizarTabla("Mega 3", lqbl6, 77900.00, "btnMega3");
    }//GEN-LAST:event_btnMega3ActionPerformed

    private void btnmegaFamiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmegaFamiliarActionPerformed

        actualizarTabla("Mega Familiar", lqbl7, 61900.00, "btnmegaFamiliar");

    }//GEN-LAST:event_btnmegaFamiliarActionPerformed

    private void btnMegaVariedadXLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMegaVariedadXLActionPerformed

        actualizarTabla("Mega Variedad", lqbl8, 77900.00, "btnmegaVariedad");

    }//GEN-LAST:event_btnMegaVariedadXLActionPerformed

    private void btnBucketTradicionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBucketTradicionalActionPerformed

        actualizarTabla("Bucket Tradicional", lqbl9, 62500.00, "btnBucketTradiconal");


    }//GEN-LAST:event_btnBucketTradicionalActionPerformed

    private void btnMegaFamiliarArepasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMegaFamiliarArepasActionPerformed

        actualizarTabla("Mega Familiar Arepas", lqbl10, 49900.00, "btnmegaFamiliarArepas");


    }//GEN-LAST:event_btnMegaFamiliarArepasActionPerformed

    private void btnMegaFamiliarXLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMegaFamiliarXLActionPerformed

        actualizarTabla("Mega Familiar XL", lqbl11, 76900.00, "btnmegaFamiliarXl");


    }//GEN-LAST:event_btnMegaFamiliarXLActionPerformed

    private void btnTrioSupremoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrioSupremoActionPerformed

        actualizarTabla("Trio Supremo", lqbl12, 57700.00, "btnSupremo");


    }//GEN-LAST:event_btnTrioSupremoActionPerformed

    private void btnMedioKFCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedioKFCActionPerformed

        actualizarTabla("Medio KFC", lqbl13, 32900.00, "btnMedio");


    }//GEN-LAST:event_btnMedioKFCActionPerformed

    private void btnFamiliarKFCArepasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFamiliarKFCArepasActionPerformed

        actualizarTabla("Familiar KFC", lqbl4, 62900.00, "btnFamiliarKFC");

    }//GEN-LAST:event_btnFamiliarKFCArepasActionPerformed

    private void btn2PresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2PresasActionPerformed

        actualizarTabla("2 Presas", lqbl15, 18900.00, "btn2presas");


    }//GEN-LAST:event_btn2PresasActionPerformed

    private void btn3PresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3PresasActionPerformed

        actualizarTabla("3 Presas", lqbl16, 26900.00, "btn3presas");


    }//GEN-LAST:event_btn3PresasActionPerformed

    private void btnAltasPicantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltasPicantesActionPerformed

        actualizarTabla("Alitas Picantes", lqbl17, 24900.00, "btnAlitasPicantes");

    }//GEN-LAST:event_btnAltasPicantesActionPerformed

    private void bntPopCornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPopCornActionPerformed

        actualizarTabla("Pop Corn", lqbl8, 17900.00, "btnPopCorn");


    }//GEN-LAST:event_bntPopCornActionPerformed

    private void btnCaseroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaseroActionPerformed

        actualizarTabla("Casero", lqbl19, 26900.00, "btnCasero");

    }//GEN-LAST:event_btnCaseroActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        try {
            NumberFormat formatoColombia = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            Number totalNumber = formatoColombia.parse(txtTotal.getText());
            double total = totalNumber.doubleValue();
            double efectivo = Double.parseDouble(txtEfectivo.getText());

            double vueltas = efectivo - total;

            txtCambio.setText(formatoColombia.format(vueltas));
        } catch (Exception e) {
            System.out.println("Error el valor Ingresado no es valido" + e.getMessage());
            txtCambio.setText("Error");

        }
    }//GEN-LAST:event_btnPagarActionPerformed

    private void btnNuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuggetsActionPerformed
        actualizarTabla("Nuggets", lqbl20, 19900.00, "btnNuggets");

    }//GEN-LAST:event_btnNuggetsActionPerformed

    private void btnbigBoxPopHeladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbigBoxPopHeladoActionPerformed
        actualizarTabla("big Box", lqbl21, 30500.00, "btnbigBox");

    }//GEN-LAST:event_btnbigBoxPopHeladoActionPerformed

    private void btnBigBoxKSandwichNuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBigBoxKSandwichNuggetsActionPerformed
        actualizarTabla("Bix Box Sandwich", lqbl22, 30900.00, "btnBoxSandwich");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBigBoxKSandwichNuggetsActionPerformed

    private void btnBigBoxKCoronelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBigBoxKCoronelActionPerformed
        actualizarTabla("Bix Box Corone", lqbl23, 27900.00, "btnBixCorone");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBigBoxKCoronelActionPerformed

    private void jrdBtnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrdBtnTopActionPerformed


    }//GEN-LAST:event_jrdBtnTopActionPerformed

    private void btnParteComparteAlasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteComparteAlasActionPerformed
        actualizarTabla("Parte Comprate Alas", lqbl24, 46500.00, "btnComparteAlas");

    }//GEN-LAST:event_btnParteComparteAlasActionPerformed

    private void btnParteComparteMixtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteComparteMixtoActionPerformed
        actualizarTabla("Parte Y Comparte Mixto", lqbl25, 48500.00, "btnComparteMixto");

    }//GEN-LAST:event_btnParteComparteMixtoActionPerformed

    private void btnParteCompartePresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteCompartePresasActionPerformed
        actualizarTabla("Parte Y Comparte Presas", lqbl26, 47900.00, "btnCompartePresas");

    }//GEN-LAST:event_btnParteCompartePresasActionPerformed

    private void btnParteComparteNuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteComparteNuggetsActionPerformed
        actualizarTabla("Parte Y Comparte Nuggets", lqbl27, 39900.00, "btnComparteNuggets");

    }//GEN-LAST:event_btnParteComparteNuggetsActionPerformed

    private void btnWowDuoDeluxeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWowDuoDeluxeActionPerformed
        actualizarTabla("Wow Duo Luxe", lqbl28, 41900.00, "btnComparteWowDuo");

    }//GEN-LAST:event_btnWowDuoDeluxeActionPerformed

    private void btnWowDuoDeluxeNuggetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWowDuoDeluxeNuggetsActionPerformed
        actualizarTabla("Wow Duo Luxe Nuggets", lqbl39, 48900.00, "btnComparteWowDuoNuggets");

    }//GEN-LAST:event_btnWowDuoDeluxeNuggetsActionPerformed

    private void btnBbqCrunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBbqCrunchActionPerformed
        actualizarTabla("BBQ Crunch", lqbl39, 9900.00, "btnCruch");

    }//GEN-LAST:event_btnBbqCrunchActionPerformed

    private void btnComboBbqCrunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComboBbqCrunchActionPerformed
        actualizarTabla("Combo BBQ Crunch ", lqbl39, 16900.00, "btnComboBBQCrunch");

    }//GEN-LAST:event_btnComboBbqCrunchActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        StringBuilder recibo = new StringBuilder();
        int refs = (int) (12345 + (Math.random() * 4238));

        recibo.append(String.format("%38s\n", "KFC"));
        recibo.append(String.format("%38s\n", "Sogamoso - Boyacá"));
        recibo.append(String.format("%38s\n", "Carrera # 13-81"));
        recibo.append(String.format("%38s\n", "Tel: 3134567890"));
        recibo.append(String.format("\n"));
        recibo.append(String.format("Factura No: %04d\n", refs));
        recibo.append(String.format("-------------------------------------------\n"));
        recibo.append(String.format("%-23s %5s %14s\n", "Item", "Qty", "Precio"));
        recibo.append(String.format("-------------------------------------------\n"));
        DefaultTableModel model = (DefaultTableModel) tblDetalle.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String item = model.getValueAt(i, 1).toString();
            int qty = Integer.parseInt(model.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(model.getValueAt(i, 3).toString());
            recibo.append(String.format("%-23s %5d %14s\n", item, qty, nf.format(precio)));

        }
        recibo.append("---------------------------------------------------------\n");
        double efectivo = Double.parseDouble(txtEfectivo.getText().replaceAll("[^\\d.]", ""));
        double cambio = Double.parseDouble(txtCambio.getText().replaceAll("[^\\d.]", ""));
        recibo.append(String.format("%-28s  %14s\n", "Totla:", txtTotal.getText()));
        recibo.append(String.format("%-28s  %14s\n", "Efectivo:", nf.format(efectivo)));
        recibo.append(String.format("%-28s  %14s\n", "Cambio:", txtCambio.getText()));
        recibo.append("---------------------------------------------------------\n");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        recibo.append(String.format("Fecha: %s\n", dateFormat.format(new Date())));
        recibo.append(String.format("%-38s\n", "Gracias por su compra"));
        recibo.append(String.format("%-38s\n", "Visitenos en www.KFC.com.co"));
        recibo.append(String.format("%-38s\n", "ADSO SENA"));
        txtFactura.setText(recibo.toString());
        try {
            txtFactura.print();

        } catch (PrinterException ex) {
            Logger.getLogger(FormularioPOS.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnImprimirActionPerformed
    private com.itextpdf.text.Image generatorQRcode(String content, int widht, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return com.itextpdf.text.Image.getInstance(pngData);
        
        } catch (WriterException | BadElementException | IOException e) {
        JOptionPane.showMessageDialog(null, "Error Generado QR code "+ e);
        }
    }
    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        int refs = (int) (1325 + (Math.random() * 4238));
        Calendar tiempo = Calendar.getInstance();
        SimpleDateFormat tTiempo = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dia = new SimpleDateFormat("dd-MM-YYYY");

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Recibo_KFC_" + refs + ".pdf"));
            document.open();

            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph("KFC", titleFont);
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(title);

            com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            Paragraph adress = new Paragraph("Sogamoso - Boyacá\nCarrera 11 # 13-81", normalFont);
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(adress);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell("Item");
            table.addCell("Cantidad");
            table.addCell("Precio");
            DefaultTableModel df = (DefaultTableModel) tblDetalle.getModel();
            for (int i = 0; i < tblDetalle.getRowCount(); i++) {
                table.addCell(df.getValueAt(i, 1).toString());
                table.addCell(df.getValueAt(i, 2).toString());
                table.addCell(df.getValueAt(i, 3).toString());

            }
            document.add(table);
            document.add(new Paragraph("\n"));

            Paragraph grGracias = new Paragraph("Gracias por su compra", normalFont);
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(grGracias);
            
            

        } catch (Exception e) {
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFacturarActionPerformed

    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormularioPOS.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioPOS.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioPOS.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioPOS.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioPOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntPopCorn;
    private javax.swing.JButton btn12Arepas;
    private javax.swing.JButton btn20AlitasPicantes;
    private javax.swing.JButton btn2Presas;
    private javax.swing.JButton btn3AvalanchasOreo;
    private javax.swing.JButton btn3Presas;
    private javax.swing.JButton btn3PresasBebidas;
    private javax.swing.JButton btn6AlitasPicantes;
    private javax.swing.JButton btn9AlitasPicantes;
    private javax.swing.JButton btnAdicion10Nuggets;
    private javax.swing.JButton btnAdicion10NuggetsIndividual;
    private javax.swing.JButton btnAdicion5Nuggets;
    private javax.swing.JButton btnAdicion5NuggetsIndividual;
    private javax.swing.JButton btnAltasPicantes;
    private javax.swing.JButton btnAvalanchaOreo;
    private javax.swing.JButton btnAvalanchaOreoPromo;
    private javax.swing.JButton btnBbqCrunch;
    private javax.swing.JButton btnBigBoxKCoronel;
    private javax.swing.JButton btnBigBoxKSandwichNuggets;
    private javax.swing.JButton btnBucketTradicional;
    private javax.swing.JButton btnCasero;
    private javax.swing.JButton btnCombo5Nuggets;
    private javax.swing.JButton btnComboBbqCrunch;
    private javax.swing.JButton btnComboKCoronelSandwich;
    private javax.swing.JButton btnComboKSandwich;
    private javax.swing.JButton btnComboNuggets;
    private javax.swing.JButton btnComboSandwichCrispyBbq;
    private javax.swing.JButton btnEliminarIteam;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnFamiliarKFCArepas;
    private javax.swing.JButton btnGaseosa15Lts;
    private javax.swing.JButton btnGaseosapet400ML;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnKentuckyCoronelSandich;
    private javax.swing.JButton btnKentuckySandwich;
    private javax.swing.JButton btnMedioKFC;
    private javax.swing.JButton btnMega3;
    private javax.swing.JButton btnMegaFamiliarArepas;
    private javax.swing.JButton btnMegaFamiliarXL;
    private javax.swing.JButton btnMegaVariedadXL;
    private javax.swing.JButton btnMrTea;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuggets;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnPapaGrande;
    private javax.swing.JButton btnParteComparteAlas;
    private javax.swing.JButton btnParteComparteMixto;
    private javax.swing.JButton btnParteComparteNuggets;
    private javax.swing.JButton btnParteCompartePresas;
    private javax.swing.JButton btnParteYComparte;
    private javax.swing.JButton btnPopCornMediano;
    private javax.swing.JButton btnPopcornGrande;
    private javax.swing.JButton btnSandwichCrispyBbq;
    private javax.swing.JButton btnSundaeDeChocolate;
    private javax.swing.JButton btnSundareArequipe;
    private javax.swing.JButton btnTrioSupremo;
    private javax.swing.JButton btnWowDuoDeluxe;
    private javax.swing.JButton btnWowDuoDeluxeNuggets;
    private javax.swing.JButton btnbigBoxPopHelado;
    private javax.swing.JButton btnensaladaColslawGrande;
    private javax.swing.JButton btnensaladaColslawPersonal;
    private javax.swing.JButton btnmegaFamiliar;
    private javax.swing.JButton btnpapaPequeña;
    private javax.swing.JButton btnpopCornPequeño;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton jrdBtnBottom;
    private javax.swing.JRadioButton jrdBtnLeft;
    private javax.swing.JRadioButton jrdBtnRight;
    private javax.swing.JRadioButton jrdBtnTop;
    private javax.swing.JLabel lbql1;
    private javax.swing.JLabel lbql5;
    private javax.swing.JLabel lqbl10;
    private javax.swing.JLabel lqbl11;
    private javax.swing.JLabel lqbl12;
    private javax.swing.JLabel lqbl13;
    private javax.swing.JLabel lqbl14;
    private javax.swing.JLabel lqbl15;
    private javax.swing.JLabel lqbl16;
    private javax.swing.JLabel lqbl17;
    private javax.swing.JLabel lqbl18;
    private javax.swing.JLabel lqbl19;
    private javax.swing.JLabel lqbl2;
    private javax.swing.JLabel lqbl20;
    private javax.swing.JLabel lqbl21;
    private javax.swing.JLabel lqbl22;
    private javax.swing.JLabel lqbl23;
    private javax.swing.JLabel lqbl24;
    private javax.swing.JLabel lqbl25;
    private javax.swing.JLabel lqbl26;
    private javax.swing.JLabel lqbl27;
    private javax.swing.JLabel lqbl28;
    private javax.swing.JLabel lqbl29;
    private javax.swing.JLabel lqbl3;
    private javax.swing.JLabel lqbl30;
    private javax.swing.JLabel lqbl31;
    private javax.swing.JLabel lqbl32;
    private javax.swing.JLabel lqbl33;
    private javax.swing.JLabel lqbl34;
    private javax.swing.JLabel lqbl35;
    private javax.swing.JLabel lqbl36;
    private javax.swing.JLabel lqbl37;
    private javax.swing.JLabel lqbl38;
    private javax.swing.JLabel lqbl39;
    private javax.swing.JLabel lqbl4;
    private javax.swing.JLabel lqbl40;
    private javax.swing.JLabel lqbl41;
    private javax.swing.JLabel lqbl42;
    private javax.swing.JLabel lqbl43;
    private javax.swing.JLabel lqbl44;
    private javax.swing.JLabel lqbl45;
    private javax.swing.JLabel lqbl46;
    private javax.swing.JLabel lqbl47;
    private javax.swing.JLabel lqbl48;
    private javax.swing.JLabel lqbl49;
    private javax.swing.JLabel lqbl50;
    private javax.swing.JLabel lqbl51;
    private javax.swing.JLabel lqbl52;
    private javax.swing.JLabel lqbl53;
    private javax.swing.JLabel lqbl54;
    private javax.swing.JLabel lqbl55;
    private javax.swing.JLabel lqbl56;
    private javax.swing.JLabel lqbl57;
    private javax.swing.JLabel lqbl58;
    private javax.swing.JLabel lqbl59;
    private javax.swing.JLabel lqbl6;
    private javax.swing.JLabel lqbl7;
    private javax.swing.JLabel lqbl8;
    private javax.swing.JLabel lqbl9;
    private javax.swing.JPanel pnl2Personas;
    private javax.swing.JPanel pnlBoxes;
    private javax.swing.JPanel pnlBuckets;
    private javax.swing.JPanel pnlCombos;
    private javax.swing.JPanel pnlIndividual;
    private javax.swing.JPanel pnlNuggets;
    private javax.swing.JPanel pnlPostres;
    private javax.swing.JTabbedPane pnlPrincipal;
    private javax.swing.JPanel pnlSandwich;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JPanel pnlbebidas;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextArea txtFactura;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
