/*
Alexandra Elyze Villar
FOPI01 - CSS124L 
SA1: Practical Exam
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class GUIexam extends JFrame {
    private JButton songLyricsButton;
    private JButton starlinkButton;

    public GUIexam() {
        setTitle("Main Menu");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons for each program
        songLyricsButton = new JButton("Song Lyrics Display");
        starlinkButton = new JButton("SpaceX Starlink Project");

        // Add action listeners to the buttons
        songLyricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSongLyricsProgram();
            }
        });

        starlinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStarlinkProgram();
            }
        });

        // Create main panel with grid layout
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(songLyricsButton);
        mainPanel.add(starlinkButton);

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private void openSongLyricsProgram() {
        // Hide the main menu
        setVisible(false);

        // Create and show the Song Lyrics program window
        JFrame songLyricsFrame = new JFrame("Song Lyrics Display");
        songLyricsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the Song Lyrics program panel
        MainSongLyrics songLyricsProgram = new MainSongLyrics();

        // Add the program panel to the frame
        songLyricsFrame.add(songLyricsProgram);
        songLyricsFrame.setSize(800, 600);
        songLyricsFrame.setVisible(true);

        // Add a back button to return to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songLyricsFrame.dispose();
                setVisible(true);
            }
        });

        // Return to Main Menu button
        songLyricsProgram.add(backButton, BorderLayout.SOUTH);
    }

    private void openStarlinkProgram() {
        // Hide the main menu
        setVisible(false);

        // Create and show the Starlink program window
        JFrame starlinkFrame = new JFrame("SpaceX Starlink Project");
        starlinkFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the Starlink program panel
        MainStarlink starlinkProgram = new MainStarlink();

        // Add the program panel to the frame
        starlinkFrame.add(starlinkProgram);
        starlinkFrame.setSize(800, 600);
        starlinkFrame.setVisible(true);

        // Add a back button to return to the main menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starlinkFrame.dispose();
                setVisible(true);
            }
        });

        // Return to Main Menu button
        starlinkProgram.add(backButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIexam());
    }
}

class MainSongLyrics extends JPanel {
    private JTextArea lyricsTextArea;
    private JComboBox<String> songComboBox;
    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton largeRadioButton;
    private JCheckBox normalCheckBox;
    private JCheckBox boldCheckBox;
    private JCheckBox italicCheckBox;

    public MainSongLyrics() {
       setTitle("Song Lyrics Display");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Song ComboBox
        String[] songs = {"Take Me Back To Eden Sleep Token", "Ligaya Eraserheads", "Midnight Rain Taylor Swift", "Tek it Cafuné",
            "I Wanna Be Yours Arctic Monkeys"}; //Song lists
        songComboBox = new JComboBox<>(songs);

        // Font Size Radio Buttons
        smallRadioButton = new JRadioButton("Small");
        mediumRadioButton = new JRadioButton("Medium");
        largeRadioButton = new JRadioButton("Large");

        ButtonGroup fontSizeGroup = new ButtonGroup();
        fontSizeGroup.add(smallRadioButton);
        fontSizeGroup.add(mediumRadioButton);
        fontSizeGroup.add(largeRadioButton);

        // Font Style Checkboxes
        normalCheckBox = new JCheckBox("Normal");
        boldCheckBox = new JCheckBox("Bold");
        italicCheckBox = new JCheckBox("Italic");

        // Lyrics TextArea
        lyricsTextArea = new JTextArea();
        lyricsTextArea.setEditable(false);
        lyricsTextArea.setBackground(new Color(102, 255, 102)); // Set the background color to light blue

        // Button to display lyrics
        JButton displayButton = new JButton("Display Lyrics");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLyrics();
            }
        });

        // Layout
        JPanel panel = new JPanel(new GridLayout(9, 1));
        panel.add(new JLabel("Select Song:"));
        panel.add(songComboBox);
        panel.add(new JLabel("Select Font Size:"));
        panel.add(smallRadioButton);
        panel.add(mediumRadioButton);
        panel.add(largeRadioButton);
        panel.add(new JLabel("Select Font Style:"));
        panel.add(normalCheckBox);
        panel.add(boldCheckBox);
        panel.add(italicCheckBox);
        panel.add(displayButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.WEST);
        add(new JScrollPane(lyricsTextArea), BorderLayout.CENTER);
    }

    private void displayLyrics() {
        // Read selected song lyrics from the file
        String selectedSong = (String) songComboBox.getSelectedItem();
        String fileName = "C:\\Users\\alexa\\Documents\\Java Programs\\SA1\\" + selectedSong.toLowerCase().replace(" ", "_") + ".txt";
        System.out.println("Attempting to read file: " + fileName); // For Debugging

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder lyrics = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                lyrics.append(line).append("\n");
            }
            lyricsTextArea.setText(lyrics.toString());

            // Apply font size and style
            int fontSize = getSelectedFontSize();
            int fontStyle = getSelectedFontStyle();
            lyricsTextArea.setFont(new Font("Arial", fontStyle, fontSize));
        } catch (IOException e) {
            e.printStackTrace(); // Print the exception details for debugging
            lyricsTextArea.setText("Error reading lyrics file.");
        }
    }

    private int getSelectedFontSize() {
        if (smallRadioButton.isSelected()) {
            return 12;
        } else if (mediumRadioButton.isSelected()) {
            return 16;
        } else if (largeRadioButton.isSelected()) {
            return 20;
        } else {
            return 16; // Default size
        }
    }

    private int getSelectedFontStyle() {
        int style = Font.PLAIN;
        if (normalCheckBox.isSelected()) {
            style += Font.PLAIN;
        }
        if (boldCheckBox.isSelected()) {
            style += Font.BOLD;
        }
        if (italicCheckBox.isSelected()) {
            style += Font.ITALIC;
        }
        return style;
    }

    public static void MainS(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainSongLyrics().setVisible(true);
            }
        });
    }

    private void setTitle(String song_Lyrics_Display) {
    }

    private void setDefaultCloseOperation(int EXIT_ON_CLOSE) {
    }
}

class MainStarlink extends JPanel {
    private JTextField satelliteIdField, satelliteNameField, longitudeField, latitudeField, elevationField;
    private JButton findSatelliteButton, saveButton, updateButton, deleteButton, clearAllButton;
    private DefaultTableModel tableModel;
    private JTable dataTable;
    private JComboBox<String> healthStatusComboBox;

    public MainStarlink() {
        setTitle("SpaceX Starlink Project");
        setSize(600, 400);

        // Create panels with different layouts
        JPanel mainPanel = new JPanel(new GridLayout(6, 1));
        JPanel satelliteIdPanel = createFlowPanel("Satellite ID", 20);
        JPanel satelliteNamePanel = createBorderPanel("Satellite Name", 20);
        JPanel locationPanel = createGridPanel("Longitude", "Latitude", 20, 5);
        JPanel additionalInfoPanel = createAdditionalInfoPanel("Elevation", "Health Status", 20, 5);
        JPanel buttonPanel = createButtonPanel();

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Satellite ID");
        tableModel.addColumn("Satellite Name");
        tableModel.addColumn("Longitude");
        tableModel.addColumn("Latitude");
        tableModel.addColumn("Elevation");
        tableModel.addColumn("Health Status");

        dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        // Clear All Button
        clearAllButton = new JButton("Clear All");
        clearAllButton.setPreferredSize(new Dimension(200, 30));
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
            }
        });

        // Add components to panels
        mainPanel.add(satelliteIdPanel);
        mainPanel.add(satelliteNamePanel);
        mainPanel.add(locationPanel);
        mainPanel.add(additionalInfoPanel);
        mainPanel.add(buttonPanel);

        // Add panels to the frame
        add(mainPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(clearAllButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createFlowPanel(String labelText, int textFieldWidth) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.BLUE); // Set text color to blue
        satelliteIdField = new JTextField(textFieldWidth);
        findSatelliteButton = new JButton("Find Satellite");

        panel.add(label);
        panel.add(satelliteIdField);
        panel.add(findSatelliteButton);

        return panel;
    }

    private JPanel createBorderPanel(String labelText, int textFieldWidth) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.BLUE); // Set text color to blue
        satelliteNameField = new JTextField(textFieldWidth);

        panel.add(label, BorderLayout.WEST);
        panel.add(satelliteNameField, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGridPanel(String label1, String label2, int textFieldWidth, int gap) {
        JPanel panel = new JPanel(new GridLayout(1, 4, gap, 0));

        JLabel labelLongitude = new JLabel(label1);
        labelLongitude.setForeground(Color.BLUE); // Set text color to blue
        longitudeField = new JTextField(textFieldWidth);

        JLabel labelLatitude = new JLabel(label2);
        labelLatitude.setForeground(Color.BLUE); // Set text color to blue
        latitudeField = new JTextField(textFieldWidth);

        panel.add(labelLongitude);
        panel.add(longitudeField);
        panel.add(labelLatitude);
        panel.add(latitudeField);

        return panel;
    }

    private JPanel createAdditionalInfoPanel(String label1, String label2, int textFieldWidth, int gap) {
        JPanel panel = new JPanel(new GridLayout(1, 4, gap, 0));

        JLabel labelElevation = new JLabel(label1);
        labelElevation.setForeground(Color.BLUE); // Set text color to blue
        elevationField = new JTextField(textFieldWidth);

        JLabel labelHealthStatus = new JLabel(label2);
        labelHealthStatus.setForeground(Color.BLUE); // Set text color to blue
        healthStatusComboBox = new JComboBox<>(new String[]{"Very Good", "Good", "Bad", "Very Bad"});

        panel.add(labelElevation);
        panel.add(elevationField);
        panel.add(labelHealthStatus);
        panel.add(healthStatusComboBox);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        saveButton = new JButton("Save");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Set preferred size for the buttons
        saveButton.setPreferredSize(new Dimension(200, 30));
        updateButton.setPreferredSize(new Dimension(200, 30));
        deleteButton.setPreferredSize(new Dimension(200, 30));
        findSatelliteButton.setPreferredSize(new Dimension(200, 30));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSatellite();
            }
        });

        panel.add(saveButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        return panel;
    }

    private void clearAllFields() {
        satelliteIdField.setText("");
        satelliteNameField.setText("");
        longitudeField.setText("");
        latitudeField.setText("");
        elevationField.setText("");
        healthStatusComboBox.setSelectedIndex(0); // Set the default selection
    }

    private void saveSatellite() {
        // Add a row to the table when the Save button is clicked
        Object[] data = {
                satelliteIdField.getText(),
                satelliteNameField.getText(),
                longitudeField.getText(),
                latitudeField.getText(),
                elevationField.getText(),
                healthStatusComboBox.getSelectedItem()
        };
        tableModel.addRow(data);
    }

    private void setTitle(String spaceX_Starlink_Project) {
    }
}
