package ufrgs.fpi.assignment1.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Runner extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private static final int PICTURE_PANE_WIDTH = (int) (WIDTH * 0.425);
    private static final int BUTTONS_PANE_WIDTH = (int) (WIDTH * 0.16);

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    private JButton uploadImageButton;
    private JButton verticalMirrorButton;
    private JButton horizontalMirrorButton;
    private JButton shadesOfGrayButton;
    private JButton quantizationButton;
    private JButton saveButton;
    private JSpinner shadesSpinner;

    private Runner() {
        JPanel topPanel = initTopPanel();
        createContentPanels();
        createSplitterPanes(topPanel);
        setMainWindowProperties();
    }

    private JPanel initTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);
        return topPanel;
    }

    private void createContentPanels() {
        createPanel1();
        createPanel2();
        createPanel3();
    }

    private void createSplitterPanes(JPanel topPanel) {
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        topPanel.add(splitPaneLeft, BorderLayout.CENTER);

        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneRight.setLeftComponent(panel1);
        splitPaneRight.setRightComponent(panel2);

        splitPaneLeft.setLeftComponent(splitPaneRight);
        splitPaneLeft.setRightComponent(panel3);
    }

    private void setMainWindowProperties() {
        setTitle("Assignment number one");
        setBackground(Color.gray);
        setSize(WIDTH, HEIGHT);
        pack();
        setVisible(true);
    }

    private void createPanel1() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setPreferredSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
        panel1.setMinimumSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
    }

    private void createPanel2() {
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setPreferredSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
        panel2.setMinimumSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
    }

    private void createPanel3() {
        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());

        initShadesSpinner();
        initButtons();
        addButtonsToPanel();

        panel3.setPreferredSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
        panel3.setMinimumSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
    }

    private void addButtonsToPanel() {
        panel3.add(uploadImageButton);
        panel3.add(verticalMirrorButton);
        panel3.add(horizontalMirrorButton);
        panel3.add(shadesOfGrayButton);
        panel3.add(quantizationButton);
        panel3.add(shadesSpinner);
        panel3.add(saveButton);
    }

    private void initButtons() {
        uploadImageButton = new JButton("Upload de imagem");
        verticalMirrorButton = new JButton("Espelhamento vertical");
        horizontalMirrorButton = new JButton("Espelhamento horizontal");
        shadesOfGrayButton = new JButton("Tons de cinza");
        quantizationButton = new JButton("Quantização");
        saveButton = new JButton("Salvar resultado");
    }

    private void initShadesSpinner() {
        shadesSpinner = new JSpinner(buildSpinnerListModel(0, 255));
        setSpinnerSize(3);
    }

    private SpinnerListModel buildSpinnerListModel(int min, int max) {
        List<Integer> range = IntStream.range(min, max+1).boxed().collect(Collectors.toList());
        return new SpinnerListModel(range);
    }

    private void setSpinnerSize(int size) {
        Component spinnerEditor = shadesSpinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
        textField.setColumns(size);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Runner::new);
    }
}
