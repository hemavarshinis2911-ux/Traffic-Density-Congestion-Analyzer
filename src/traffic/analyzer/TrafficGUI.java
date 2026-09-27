package traffic.analyzer;

import javax.swing.*;
import java.awt.*;

public class TrafficGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame(
                "Traffic Density & Congestion Analyzer - Smart Traffic Monitoring"
        );

        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // -----------------------------
        // Title
        // -----------------------------
        JLabel title = new JLabel(
                "TRAFFIC DENSITY & CONGESTION ANALYZER",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Subtitle
        JLabel subtitle = new JLabel(
                "AI-Based Vehicle Detection and Traffic Density Analysis",
                SwingConstants.CENTER
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        // -----------------------------
        // Image display area
        // -----------------------------
        JLabel imageLabel = new JLabel(
                "Select a traffic image",
                SwingConstants.CENTER
        );

        imageLabel.setPreferredSize(new Dimension(500, 300));

        // -----------------------------
        // Buttons
        // -----------------------------
        JButton selectButton = new JButton("Select Traffic Image");
        JButton analyzeButton = new JButton("Analyze Traffic");

        // -----------------------------
        // Result area
        // -----------------------------
        JTextArea resultArea = new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        resultArea.setPreferredSize(
                new Dimension(250, 300)
        );

        resultArea.setBorder(
                BorderFactory.createTitledBorder(
                        "TRAFFIC ANALYSIS RESULT"
                )
        );

        // -----------------------------
        // File chooser
        // -----------------------------
        JFileChooser fileChooser = new JFileChooser();

        final String[] selectedImagePath = {null};

        // -----------------------------
        // Select Image Button
        // -----------------------------
        selectButton.addActionListener(e -> {

            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {

                String imagePath =
                        fileChooser.getSelectedFile().getAbsolutePath();

                selectedImagePath[0] = imagePath;

             // Check whether the selected file is an image
             String lowerPath = imagePath.toLowerCase();

             if (!(lowerPath.endsWith(".jpg") ||
                   lowerPath.endsWith(".jpeg") ||
                   lowerPath.endsWith(".png"))) {

                 JOptionPane.showMessageDialog(
                         frame,
                         "Please select a JPG, JPEG, or PNG image."
                 );

                 selectedImagePath[0] = null;
                 return;
             }

             ImageIcon imageIcon =
                     new ImageIcon(imagePath);

                Image image =
                        imageIcon.getImage().getScaledInstance(
                                500,
                                300,
                                Image.SCALE_SMOOTH
                        );

                imageLabel.setIcon(
                        new ImageIcon(image)
                );

                imageLabel.setText("");
            }
        });

        // -----------------------------
        // Analyze Traffic Button
        // -----------------------------
        analyzeButton.addActionListener(e -> {

            if (selectedImagePath[0] == null) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please select a traffic image first."
                );

                return;
            }

            TrafficResult result =
                    TrafficAnalyzer.analyzeTraffic(
                            selectedImagePath[0]
                    );

            if (result == null) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Traffic analysis failed."
                );

                return;
            }

            // Display analysis result
            resultArea.setText(
                    "TRAFFIC ANALYSIS RESULT\n\n" +
                    "Cars          : " + result.carCount + "\n" +
                    "Motorcycles   : " + result.motorcycleCount + "\n" +
                    "Buses         : " + result.busCount + "\n" +
                    "Trucks        : " + result.truckCount + "\n" +
                    "-----------------------------\n" +
                    "Total Vehicles: " + result.totalVehicles + "\n\n" +
                    "Traffic Level : " + result.trafficLevel + "\n" +
                    "Status        : " + result.status
            );

            // Display detected result image
            ImageIcon resultIcon =
                    new ImageIcon(result.resultImagePath);

            Image resultImage =
                    resultIcon.getImage().getScaledInstance(
                            500,
                            300,
                            Image.SCALE_SMOOTH
                    );

            imageLabel.setIcon(
                    new ImageIcon(resultImage)
            );
        });

        // -----------------------------
        // Button Panel
        // -----------------------------
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(selectButton);
        buttonPanel.add(analyzeButton);

        // -----------------------------
        // Heading Panel
        // -----------------------------
        JPanel headingPanel =
                new JPanel(new BorderLayout());

        headingPanel.add(
                title,
                BorderLayout.NORTH
        );

        headingPanel.add(
                subtitle,
                BorderLayout.CENTER
        );

        // -----------------------------
        // Main Panel
        // -----------------------------
        JPanel panel = new JPanel();

        panel.setLayout(
                new BorderLayout(10, 10)
        );

        panel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        panel.add(
                imageLabel,
                BorderLayout.CENTER
        );

        panel.add(
                resultArea,
                BorderLayout.EAST
        );

        panel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // -----------------------------
        // Display Window
        // -----------------------------
        frame.add(panel);

        frame.setVisible(true);
    }
}    