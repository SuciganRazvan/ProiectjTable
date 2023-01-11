package org.example;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


class ListSelectionListenerImpl implements ListSelectionListener {

    private final JTable table;

    public ListSelectionListenerImpl(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selectedRows = table.getSelectedRows();
        int[] selectedColumns = table.getSelectedColumns();
        StringBuilder value = new StringBuilder();
        for (int selectedRow : selectedRows) {
            for (int selectedColumn : selectedColumns) {
                value.append(table.getValueAt(selectedRow, selectedColumn));
            }
        }
        System.out.println("Table element selected is: " + value);
    }
}
class TestTableSortFilter extends JPanel {

    private String[] columnNames
            = {"NUME", "PRENUME", "SPECIALIZAREA","Clasa","TESTARE PT LICENTA?"};

    private Object[][] data = {
            {"Enescu", "Mihai", "Mate-info",12,true},
            {"Cristecu", "Vasile","Filosofie",11, false},
            {"Ungureanu", "Ionut","Sociolozie", 9, false},
            {"Fal", "Zac", "Economie",12, true},
            {"IONEL", "VITEAZU", "Economie",12, true},
            {"CSF", "ZED", "Alimentara",12, true},
            {"Viorel", "", "", },


    };

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);
    private JTable jTable = new JTable(model);

    private TableRowSorter<TableModel> rowSorter
            = new TableRowSorter<>(jTable.getModel());

    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");







    public TestTableSortFilter() {
        jTable.setRowSorter(rowSorter);
        jTable.setBackground(Color.BLACK);
        jTable.setGridColor(Color.GREEN);
        jTable.setForeground(Color.white);
        jTable.getTableHeader().setBackground(Color.BLACK);
        jTable.getTableHeader().setForeground(Color.WHITE);

        ListSelectionListenerImpl listSelectionListener = new ListSelectionListenerImpl(jTable);

        ListSelectionModel select = jTable.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        select.addListSelectionListener(listSelectionListener);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Specificati cuvantul cautat"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });




    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                JFrame frame = new JFrame("LICEEENIII");
                frame.add(new TestTableSortFilter());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }

        });
    }
}