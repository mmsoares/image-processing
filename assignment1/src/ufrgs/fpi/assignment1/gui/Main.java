package ufrgs.fpi.assignment1.gui;

import ufrgs.fpi.assignment1.imageprocesing.ImageTransformer;
import ufrgs.fpi.assignment1.imageprocesing.JPEGHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javax.swing.JOptionPane.*;

class Main extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final int PICTURE_PANE_WIDTH = (int) (WIDTH * 0.425);
    private static final int BUTTONS_PANE_WIDTH = (int) (WIDTH * 0.15);

    private JPanel originalImagePanel, resultImagePanel, buttonsPanel;
    private JButton uploadImageButton, histogramButton, brightEnhancementButton, grayscaleButton, quantizationButton, restoreOriginal, saveButton;
    private JSpinner shadesSpinner, brightSpinner;
    private JLabel originalImageJLabel, resultImageJLabel;
    private BufferedImage originalImage, resultImage;

    private Main() {
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
        createOriginalImagePanel();
        createModifiedImagePanel();
        createButtonsPanel();
        addOnClickHandlersToButtons();
    }

    private void createOriginalImagePanel() {
        originalImagePanel = new JPanel();
        originalImagePanel.setLayout(new BorderLayout());
        originalImagePanel.setPreferredSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
        originalImagePanel.setMinimumSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
    }

    private void createModifiedImagePanel() {
        resultImagePanel = new JPanel();
        resultImagePanel.setLayout(new BorderLayout());
        resultImagePanel.setPreferredSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
        resultImagePanel.setMinimumSize(new Dimension(PICTURE_PANE_WIDTH, HEIGHT));
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        initShadesSpinner();
        initBrightSpinner();
        initButtons();
        disableButtons();
        addButtonsToPanel();

        buttonsPanel.setPreferredSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
        buttonsPanel.setMinimumSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
    }

    private void initShadesSpinner() {
        shadesSpinner = new JSpinner(buildSpinnerListModel(1, 255));
        setShadesSpinnerSize();
    }

    private void initBrightSpinner() {
        brightSpinner = new JSpinner(buildSpinnerListModel(-255, 255));
        setBrightSpinnerSize();
    }

    private SpinnerListModel buildSpinnerListModel(int min, int max) {
        List<Integer> range = IntStream.range(min, max + 1).boxed().collect(Collectors.toList());
        return new SpinnerListModel(range);
    }

    private void setShadesSpinnerSize() {
        Component spinnerEditor = shadesSpinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
        textField.setColumns(3);
    }

    private void setBrightSpinnerSize() {
        Component spinnerEditor = brightSpinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
        textField.setColumns(4);
    }

    private void initButtons() {
        uploadImageButton = new JButton("Upload de imagem");
        histogramButton = new JButton("Histograma");
        brightEnhancementButton = new JButton("   Brilho");
        grayscaleButton = new JButton("Tons de cinza");
        quantizationButton = new JButton("Quantização");
        restoreOriginal = new JButton("Restaurar original");
        saveButton = new JButton("Salvar resultado");
    }

    private void disableButtons() {
        histogramButton.setEnabled(false);
        brightEnhancementButton.setEnabled(false);
        grayscaleButton.setEnabled(false);
        quantizationButton.setEnabled(false);
        shadesSpinner.setEnabled(false);
        brightSpinner.setEnabled(false);
        restoreOriginal.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void addButtonsToPanel() {
        buttonsPanel.add(uploadImageButton);
        buttonsPanel.add(histogramButton);
        buttonsPanel.add(brightEnhancementButton);
        buttonsPanel.add(brightSpinner);
        buttonsPanel.add(grayscaleButton);
        buttonsPanel.add(quantizationButton);
        buttonsPanel.add(shadesSpinner);
        buttonsPanel.add(restoreOriginal);
        buttonsPanel.add(saveButton);
    }

    private void addOnClickHandlersToButtons() {
        addUploadImageButtonOnClick();
        addHistogramButtonOnClick();
        addBrightEnhancementOnClick();
        addGrayscaleButtonOnClick();
        addQuantizationButtonOnClick();
        addRestoreOriginalButtonOnClick();
        addSaveButtonOnClick();
    }

    private void addUploadImageButtonOnClick() {
        uploadImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\Matheus\\IdeaProjects\\image-processing\\samples");
            int returnValue = fileChooser.showOpenDialog(uploadImageButton);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    originalImage = JPEGHandler.readImage(selectedFile.getAbsolutePath());
                    refreshOriginalImagePanel();
                    resultImage = originalImage;
                    refreshResultImagePanel();
                    enableButtons();
                } catch (IOException ioException) {
                    showMessageDialog(null, "Um erro ocorreu. Não foi possível carregar a imagem.");
                    ioException.printStackTrace();
                }
            }
        });
    }

    private void addHistogramButtonOnClick() {
        histogramButton.addActionListener(e -> {
            resultImage = ImageTransformer.getHistogramAsImage(originalImage);
            refreshResultImagePanel();
        });
    }

    private void addBrightEnhancementOnClick() {
        brightEnhancementButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = ImageTransformer.brightEnhancement(resultImage, (Integer) shadesSpinner.getValue());
                refreshResultImagePanel();
            }
        });
    }

    private void addGrayscaleButtonOnClick() {
        grayscaleButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = ImageTransformer.convertToGrayscale(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addQuantizationButtonOnClick() {
        quantizationButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = ImageTransformer.quantizeImage(resultImage, (Integer) shadesSpinner.getValue());
                refreshResultImagePanel();
            }
        });
    }

    private void addRestoreOriginalButtonOnClick() {
        restoreOriginal.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = originalImage;
                refreshResultImagePanel();
            }
        });
    }

    private void addSaveButtonOnClick() {
        saveButton.addActionListener(e -> {
            if (resultImage != null) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\Matheus\\IdeaProjects\\image-processing\\output");
                int returnValue = fileChooser.showSaveDialog(saveButton);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        JPEGHandler.writeImage(resultImage, file);
                        showMessageDialog(null, "Imagem salva com sucesso");
                    } catch (IOException ioException) {
                        showMessageDialog(null, "Um erro ocorreu. Não foi possível salvar a imagem.");
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    private void enableButtons() {
        histogramButton.setEnabled(true);
        brightEnhancementButton.setEnabled(true);
        brightSpinner.setEnabled(true);
        grayscaleButton.setEnabled(true);
        quantizationButton.setEnabled(true);
        shadesSpinner.setEnabled(true);
        restoreOriginal.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private void refreshOriginalImagePanel() {
        if (originalImageJLabel != null) {
            originalImagePanel.remove(originalImageJLabel);
        }

        originalImageJLabel = new JLabel(new ImageIcon(originalImage));
        originalImagePanel.add(originalImageJLabel);
        originalImagePanel.repaint();
        originalImagePanel.revalidate();
    }

    private void refreshResultImagePanel() {
        if (resultImageJLabel != null) {
            resultImagePanel.remove(resultImageJLabel);
        }

        resultImageJLabel = new JLabel(new ImageIcon(resultImage));
        resultImagePanel.add(resultImageJLabel);
        resultImagePanel.repaint();
        resultImagePanel.revalidate();
    }

    private void createSplitterPanes(JPanel topPanel) {
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        topPanel.add(splitPaneLeft, BorderLayout.CENTER);

        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneRight.setLeftComponent(originalImagePanel);
        splitPaneRight.setRightComponent(resultImagePanel);

        splitPaneLeft.setLeftComponent(splitPaneRight);
        splitPaneLeft.setRightComponent(buttonsPanel);
    }

    private void setMainWindowProperties() {
        setTitle("Assignment number one");
        setBackground(Color.gray);
        setSize(WIDTH, HEIGHT);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}