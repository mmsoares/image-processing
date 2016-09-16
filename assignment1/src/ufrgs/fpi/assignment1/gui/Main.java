package ufrgs.fpi.assignment1.gui;

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
import static ufrgs.fpi.assignment1.imageprocesing.ImageTransformer.*;

class Main extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final int PICTURE_PANE_WIDTH = (int) (WIDTH * 0.425);
    private static final int BUTTONS_PANE_WIDTH = (int) (WIDTH * 0.15);

    private JPanel originalImagePanel, resultImagePanel, part1ButtonsPanel, part2ButtonsPanel;
    private JButton uploadImageButton, verticalMirrorButton, horizontalMirrorButton, grayscaleButton, quantizationButton, restoreOriginal, saveButton;
    private JButton histogramButton, brightAdjustButton, contrastAdjustButton, negativeButton, equalizedHistogramButton, zoomOutButton, zoomInButton, rotationPlusButton, rotationMinusButton, convolutionButton;
    private JSpinner shadesSpinner, brightSpinner, contrastSpinner;
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
        createButtonsPanels();
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

    private void createButtonsPanels() {
        part1ButtonsPanel = new JPanel();
        part1ButtonsPanel.setLayout(new FlowLayout());

        part2ButtonsPanel = new JPanel();
        part2ButtonsPanel.setLayout(new FlowLayout());

        initShadesSpinner();
        initContrastSpinner();
        initBrightSpinner();
        initButtons();
        disableButtons();
        addButtonsToPanels();

        part1ButtonsPanel.setPreferredSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
        part1ButtonsPanel.setMinimumSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));

        part2ButtonsPanel.setPreferredSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
        part2ButtonsPanel.setMinimumSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
    }

    private void initShadesSpinner() {
        shadesSpinner = new JSpinner(buildSpinnerListModel(1, 255));
        setShadesSpinnerSize();
    }

    private void initBrightSpinner() {
        brightSpinner = new JSpinner(buildSpinnerListModel(-255, 255));
        setBrightSpinnerSize();
    }

    private void initContrastSpinner() {
        contrastSpinner = new JSpinner(buildSpinnerListModel(1, 255));
        setContrastSpinnerSize();
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
        textField.setColumns(3);
    }

    private void setContrastSpinnerSize() {
        Component spinnerEditor = contrastSpinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
        textField.setColumns(3);
    }

    private void initButtons() {
        uploadImageButton = new JButton("Upload de imagem");
        verticalMirrorButton = new JButton("Espelhamento vertical");
        horizontalMirrorButton = new JButton("Espelhamento horizontal");
        grayscaleButton = new JButton("Tons de cinza");
        quantizationButton = new JButton("Quantização");
        restoreOriginal = new JButton("Restaurar original");
        saveButton = new JButton("Salvar resultado");

        histogramButton = new JButton("  Histograma  ");
        brightAdjustButton = new JButton("Brilho");
        contrastAdjustButton = new JButton("Contraste");
        negativeButton = new JButton("Negativo");
        equalizedHistogramButton = new JButton("Histograma equalizado");
        zoomOutButton = new JButton("Zoom out");
        zoomInButton = new JButton("Zoom in");
        rotationPlusButton = new JButton("Rotação +90°");
        rotationMinusButton = new JButton("Rotação -90°");
        convolutionButton = new JButton("Convolução");
    }

    private void disableButtons() {
        verticalMirrorButton.setEnabled(false);
        horizontalMirrorButton.setEnabled(false);
        grayscaleButton.setEnabled(false);
        quantizationButton.setEnabled(false);
        shadesSpinner.setEnabled(false);
        restoreOriginal.setEnabled(false);
        saveButton.setEnabled(false);

        histogramButton.setEnabled(false);
        brightAdjustButton.setEnabled(false);
        brightSpinner.setEnabled(false);
        contrastAdjustButton.setEnabled(false);
        contrastSpinner.setEnabled(false);
        negativeButton.setEnabled(false);
        equalizedHistogramButton.setEnabled(false);
        zoomOutButton.setEnabled(false);
        zoomInButton.setEnabled(false);
        rotationPlusButton.setEnabled(false);
        rotationMinusButton.setEnabled(false);
        convolutionButton.setEnabled(false);
    }

    private void addButtonsToPanels() {
        part1ButtonsPanel.add(uploadImageButton);
        part1ButtonsPanel.add(verticalMirrorButton);
        part1ButtonsPanel.add(horizontalMirrorButton);
        part1ButtonsPanel.add(grayscaleButton);
        part1ButtonsPanel.add(quantizationButton);
        part1ButtonsPanel.add(shadesSpinner);
        part1ButtonsPanel.add(restoreOriginal);
        part1ButtonsPanel.add(saveButton);

        part2ButtonsPanel.add(histogramButton);
        part2ButtonsPanel.add(brightAdjustButton);
        part2ButtonsPanel.add(brightSpinner);
        part2ButtonsPanel.add(contrastAdjustButton);
        part2ButtonsPanel.add(contrastSpinner);
        part2ButtonsPanel.add(negativeButton);
        part2ButtonsPanel.add(equalizedHistogramButton);
        part2ButtonsPanel.add(zoomOutButton);
        part2ButtonsPanel.add(zoomInButton);
        part2ButtonsPanel.add(rotationPlusButton);
        part2ButtonsPanel.add(rotationMinusButton);
        part2ButtonsPanel.add(convolutionButton);
    }

    private void addOnClickHandlersToButtons() {
        addUploadImageButtonOnClick();
        addVerticalMirrorButtonOnClick();
        addHorizontalMirrorButtonOnClick();
        addGrayscaleButtonOnClick();
        addGrayscaleButtonOnClick();
        addQuantizationButtonOnClick();
        addRestoreOriginalButtonOnClick();
        addSaveButtonOnClick();

        addHistogramButtonOnClick();
        addBrightAdjustButtonOnClick();
        addContrastAdjustButtonOnClick();
        addNegativeButtonOnClick();
        addEqualizedHistogramButtonOnClick();
        addZoomOutButtonOnClick();
        addZoomInButtonOnClick();
        addRotationPlusButtonOnClick();
        addRotationMinusButtonOnClick();
        addConvolutionButtonOnClick();
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

    private void addVerticalMirrorButtonOnClick() {
        verticalMirrorButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = mirrorVertically(resultImage);
                refreshResultImagePanel();

            }
        });
    }

    private void addHorizontalMirrorButtonOnClick() {
        horizontalMirrorButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = mirrorHorizontally(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addConvolutionButtonOnClick() {
        convolutionButton.addActionListener(e -> {
            if (resultImage != null) {
                //todo
                refreshResultImagePanel();
            }
        });
    }

    private void addRotationPlusButtonOnClick() {
        rotationPlusButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = rotatePlus90(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addRotationMinusButtonOnClick() {
        rotationMinusButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = rotateMinus90(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addZoomInButtonOnClick() {
        zoomInButton.addActionListener(e -> {
            if (resultImage != null) {
                //todo
                refreshResultImagePanel();
            }
        });
    }

    private void addZoomOutButtonOnClick() {
        zoomOutButton.addActionListener(e -> {
            if (resultImage != null) {
                //todo
                refreshResultImagePanel();
            }
        });
    }

    private void addEqualizedHistogramButtonOnClick() {
        equalizedHistogramButton.addActionListener(e -> {
            if (resultImage != null) {
                BufferedImage histogram = getHistogramAsImage(resultImage);
                BufferedImage equalizedImage = histogramEqualization(resultImage);
                BufferedImage equalizedHistogram = getHistogramAsImage(equalizedImage);

                showHistogramsPopup(resultImage, histogram, equalizedHistogram, equalizedImage);

                resultImage = equalizedImage;
                refreshResultImagePanel();
            }
        });
    }

    private void showHistogramsPopup(BufferedImage originalImage, BufferedImage histogram, BufferedImage equalizedHistogram, BufferedImage equalizedImage) {
        JLabel originalImageLabel = new JLabel(new ImageIcon(originalImage));
        JLabel histogramLabel = new JLabel(new ImageIcon(histogram));
        JLabel equalizedImageLabel = new JLabel(new ImageIcon(equalizedImage));
        JLabel equalizedHistogramLabel = new JLabel(new ImageIcon(equalizedHistogram));

        JPanel popupRoot = new JPanel();
        JPanel popupBottom = new JPanel();
        JPanel popupBottomRight = new JPanel();
        JPanel popupBottomLeft = new JPanel();
        JPanel popupTop = new JPanel();
        JPanel popupTopRight = new JPanel();
        JPanel popupTopLeft = new JPanel();

        popupTopRight.add(originalImageLabel);
        popupTopLeft.add(histogramLabel);
        popupBottomRight.add(equalizedImageLabel);
        popupBottomLeft.add(equalizedHistogramLabel);

        JSplitPane popupHorizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane popupVerticalTopSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane popupVerticalBottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        popupRoot.add(popupHorizontalSplit, BorderLayout.CENTER);
        popupHorizontalSplit.setTopComponent(popupTop);
        popupHorizontalSplit.setBottomComponent(popupBottom);

        popupBottom.add(popupVerticalBottomSplit);
        popupVerticalBottomSplit.setRightComponent(popupBottomRight);
        popupVerticalBottomSplit.setLeftComponent(popupBottomLeft);

        popupTop.add(popupVerticalTopSplit);
        popupVerticalTopSplit.setRightComponent(popupTopRight);
        popupVerticalTopSplit.setLeftComponent(popupTopLeft);

        JOptionPane.showMessageDialog(null, popupRoot, "Histogramas", JOptionPane.PLAIN_MESSAGE, null);
    }

    private void addNegativeButtonOnClick() {
        negativeButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = negative(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addContrastAdjustButtonOnClick() {
        contrastAdjustButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = contrastAdjust(resultImage, (Integer) contrastSpinner.getValue());
                refreshResultImagePanel();
            }
        });
    }

    private void addBrightAdjustButtonOnClick() {
        brightAdjustButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = brightEnhancement(resultImage, (Integer) brightSpinner.getValue());
                refreshResultImagePanel();
            }
        });
    }

    private void addHistogramButtonOnClick() {
        histogramButton.addActionListener(e -> {
            BufferedImage histogram = getHistogramAsImage(resultImage);

            JLabel histogramLabel = new JLabel(new ImageIcon(histogram));

            JPanel popupRoot = new JPanel();
            popupRoot.add(histogramLabel, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(null, popupRoot, "Histograma", JOptionPane.PLAIN_MESSAGE, null);
        });
    }

    private void addGrayscaleButtonOnClick() {
        grayscaleButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = convertToGrayscale(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addQuantizationButtonOnClick() {
        quantizationButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = quantizeImage(resultImage, (Integer) shadesSpinner.getValue());
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
        verticalMirrorButton.setEnabled(true);
        horizontalMirrorButton.setEnabled(true);
        grayscaleButton.setEnabled(true);
        quantizationButton.setEnabled(true);
        shadesSpinner.setEnabled(true);
        restoreOriginal.setEnabled(true);
        saveButton.setEnabled(true);

        histogramButton.setEnabled(true);
        brightAdjustButton.setEnabled(true);
        brightSpinner.setEnabled(true);
        contrastAdjustButton.setEnabled(true);
        contrastSpinner.setEnabled(true);
        negativeButton.setEnabled(true);
        equalizedHistogramButton.setEnabled(true);
        zoomOutButton.setEnabled(true);
        zoomInButton.setEnabled(true);
        rotationPlusButton.setEnabled(true);
        rotationMinusButton.setEnabled(true);
        convolutionButton.setEnabled(true);
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
        JSplitPane splitPaneMiddle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        topPanel.add(splitPaneLeft, BorderLayout.CENTER);

        splitPaneRight.setLeftComponent(originalImagePanel);
        splitPaneRight.setRightComponent(resultImagePanel);

        splitPaneMiddle.setLeftComponent(splitPaneRight);
        splitPaneMiddle.setRightComponent(part1ButtonsPanel);

        splitPaneLeft.setLeftComponent(splitPaneMiddle);
        splitPaneLeft.setRightComponent(part2ButtonsPanel);
    }

    private void setMainWindowProperties() {
        setTitle("Assignment number two");
        setBackground(Color.gray);
        setSize(WIDTH, HEIGHT);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}