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
    private JButton uploadImageButton, verticalMirrorButton, horizontalMirrorButton, shadesOfGrayButton, quantizationButton, restoreOriginal, saveButton;
    private JSpinner shadesSpinner;
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
        initButtons();
        disableButtons();
        addButtonsToPanel();

        buttonsPanel.setPreferredSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
        buttonsPanel.setMinimumSize(new Dimension(BUTTONS_PANE_WIDTH, HEIGHT));
    }

    private void initShadesSpinner() {
        shadesSpinner = new JSpinner(buildSpinnerListModel());
        setSpinnerSize();
    }

    private SpinnerListModel buildSpinnerListModel() {
        List<Integer> range = IntStream.range(1, 255 + 1).boxed().collect(Collectors.toList());
        return new SpinnerListModel(range);
    }

    private void setSpinnerSize() {
        Component spinnerEditor = shadesSpinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
        textField.setColumns(3);
    }

    private void initButtons() {
        uploadImageButton = new JButton("Upload de imagem");
        verticalMirrorButton = new JButton("Espelhamento vertical");
        horizontalMirrorButton = new JButton("Espelhamento horizontal");
        shadesOfGrayButton = new JButton("Tons de cinza");
        quantizationButton = new JButton("Quantização");
        restoreOriginal = new JButton("Restaurar original");
        saveButton = new JButton("Salvar resultado");
    }

    private void disableButtons() {
        verticalMirrorButton.setEnabled(false);
        horizontalMirrorButton.setEnabled(false);
        shadesOfGrayButton.setEnabled(false);
        quantizationButton.setEnabled(false);
        shadesSpinner.setEnabled(false);
        restoreOriginal.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void addButtonsToPanel() {
        buttonsPanel.add(uploadImageButton);
        buttonsPanel.add(verticalMirrorButton);
        buttonsPanel.add(horizontalMirrorButton);
        buttonsPanel.add(shadesOfGrayButton);
        buttonsPanel.add(quantizationButton);
        buttonsPanel.add(shadesSpinner);
        buttonsPanel.add(restoreOriginal);
        buttonsPanel.add(saveButton);
    }

    private void addOnClickHandlersToButtons() {
        addUploadImageButtonOnClick();
        addVerticalMirrorButtonOnClick();
        addHorizontalMirrorButtonOnClick();
        addShadesOfGrayButtonOnClick();
        addQuantizationButtonOnClick();
        addRestoreOriginalButtonOnClick();
        addSaveButtonOnClick();
    }

    private void addUploadImageButtonOnClick() {
        uploadImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
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
                resultImage = ImageTransformer.mirrorVertically(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addHorizontalMirrorButtonOnClick() {
        horizontalMirrorButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = ImageTransformer.mirrorHorizontally(resultImage);
                refreshResultImagePanel();
            }
        });
    }

    private void addShadesOfGrayButtonOnClick() {
        shadesOfGrayButton.addActionListener(e -> {
            if (resultImage != null) {
                resultImage = ImageTransformer.convertToShadesOfGray(resultImage);
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
                JFileChooser fileChooser = new JFileChooser();
                //fileChooser.se
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
        shadesOfGrayButton.setEnabled(true);
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
