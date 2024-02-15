import javax.imageio.ImageIO; // Import for handling images
import javax.swing.*; // Import for Swing GUI components
import javax.swing.border.EmptyBorder; // Import for adding empty borders to Swing components
import java.awt.*; // Import for AWT components
import java.awt.event.ActionEvent; // Import for ActionEvent class
import java.awt.event.ActionListener; // Import for ActionListener interface
import java.awt.event.ComponentAdapter; // Import for ComponentAdapter class
import java.awt.event.ComponentEvent; // Import for ComponentEvent class
import java.awt.image.BufferedImage; // Import for BufferedImage class
import java.io.BufferedInputStream; // Import for BufferedInputStream class
import java.io.File; // Import for File class
import java.io.IOException; // Import for IOException class
import java.net.URI; // Import for URI class
import java.net.URISyntaxException; // Import for URISyntaxException class
import java.net.URL; // Import for URL class
import java.nio.file.Path; // Import for Path class
import java.nio.file.Paths; // Import for Paths class
import java.text.SimpleDateFormat; // Import for SimpleDateFormat class
import java.util.Date; // Import for Date class
import java.util.concurrent.*; // Import for ExecutorService and related classes

/**
 * ImageDownloaderDesktopApplication is a simple desktop application that allows users to download images from multiple URLs concurrently.
 * It utilizes Swing for the graphical user interface and employs multithreading to enhance performance during downloads.
 * The application provides features like pausing, resuming, and canceling downloads, along with displaying download progress and status messages.
 */

public class ImageDownloaderDesktopApplication {
    private JFrame frame; // Declaration of JFrame for the main application window
    private JTextField[] urlFields; // Array of JTextFields for entering URLs
    private JButton downloadButton; // JButton for starting downloads
    private JButton pauseButton; // JButton for pausing downloads
    private JButton resumeButton; // JButton for resuming downloads
    private JButton cancelButton; // JButton for canceling downloads
    private JTextArea statusTextArea; // JTextArea for displaying status messages
    private ExecutorService executor; // ExecutorService for managing download tasks
    private JProgressBar[] progressBars; // Array of JProgressBars for displaying download progress
    private volatile boolean paused = false; // Flag for indicating whether downloads are paused

    // Constructor for initializing the application
    public ImageDownloaderDesktopApplication() {
        initialize(); // Call the initialize method to set up the GUI
        executor = Executors.newFixedThreadPool(5); // Initialize the ExecutorService with a fixed thread pool size
    }

    // Method for resetting the GUI components
    private void resetGUI() {
        // Reset progress bars to initial state
        for (JProgressBar progressBar : progressBars) {
            progressBar.setValue(0);
        }
        // Clear status text area
        statusTextArea.setText("");

        // Clear URL input fields
        for (int i = 0; i < 5; i++) {
            urlFields[i].setText("");
        }
    }

    // Method for initializing the GUI
    private void initialize() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // Set Nimbus look and feel for modern appearance
        } 
        catch (Exception e) {
            e.printStackTrace(); // Print stack trace if look and feel setting fails
        }

        frame = new JFrame("Image Downloader"); // Create a new JFrame with title "Image Downloader"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.setLayout(new BorderLayout()); // Set BorderLayout for the frame layout

        Container contentPane = frame.getContentPane(); // Get content pane of the frame
        contentPane.setBackground(new Color(137, 171, 227)); // Set background color

        JLabel titleLabel = new JLabel("Image Downloader", SwingConstants.CENTER); // Create JLabel for title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font for title label
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Add empty border for spacing
        frame.add(titleLabel, BorderLayout.NORTH); // Add title label to the north (top) of the frame

        JPanel inputPanel = new JPanel(new GridBagLayout()); // Create JPanel for input fields
        inputPanel.setBackground(new Color(137, 171, 227)); // Set background color for input panel
        inputPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Add empty border for spacing
        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints for layout
        gbc.insets = new Insets(5, 5, 5, 5); // Set insets for spacing
        gbc.fill = GridBagConstraints.HORIZONTAL; // Set fill to horizontal

        JLabel[] urlLabels = new JLabel[5]; // Array of JLabels for URL labels
        urlFields = new JTextField[5]; // Array of JTextFields for URL input
        progressBars = new JProgressBar[5]; // Array of JProgressBars for progress indication

        // Loop to create URL input fields with labels and progress bars
        for (int i = 0; i < 5; i++) {
            urlLabels[i] = new JLabel("Enter URL " + (i+1) + ":"); // Create URL label
            gbc.gridx = 0; // Set grid x position
            gbc.gridy = i; // Set grid y position
            inputPanel.add(urlLabels[i], gbc); // Add URL label to input panel

            urlFields[i] = new JTextField(20); // Create URL text field
            urlFields[i].setBackground(new Color(252, 246, 245)); // Set background color
            gbc.gridx = 1; // Set grid x position
            gbc.weightx = 1.0; // Set weight for horizontal resizing
            inputPanel.add(urlFields[i], gbc); // Add URL text field to input panel

            progressBars[i] = new JProgressBar(0, 100); // Create progress bar with range 0-100
            progressBars[i].setStringPainted(true); // Enable progress bar string painting
            gbc.gridx = 2; // Set grid x position
            gbc.weightx = 0.0; // Reset weight for horizontal resizing
            inputPanel.add(progressBars[i], gbc); // Add progress bar to input panel
        }

        downloadButton = new JButton("Download"); // Create download button
        pauseButton = new JButton("Pause"); // Create pause button
        resumeButton = new JButton("Resume"); // Create resume button
        cancelButton = new JButton("Cancel"); // Create cancel button
        gbc.gridx = 3; // Set grid x position
        gbc.gridy = 0; // Set grid y position for download button
        inputPanel.add(downloadButton, gbc); // Add download button to input panel
        gbc.gridy = 1; // Set grid y position for pause button
        inputPanel.add(pauseButton, gbc); // Add pause button to input panel
        gbc.gridy = 2; // Set grid y position for resume button
        inputPanel.add(resumeButton, gbc); // Add resume button to input panel
        gbc.gridy = 3; // Set grid y position for cancel button
        inputPanel.add(cancelButton, gbc); // Add cancel button to input panel

        frame.add(inputPanel, BorderLayout.CENTER); // Add input panel to center of the frame

        JPanel statusPanel = new JPanel(new BorderLayout()); // Create JPanel for status messages
        JLabel statusLabel = new JLabel("Status:"); // Create label for status messages
        statusPanel.setBackground(new Color(137, 171, 227)); // Set background color for status panel
        statusTextArea = new JTextArea(10, 30); // Create JTextArea for displaying status messages
        statusTextArea.setEditable(false); // Make JTextArea non-editable
        statusTextArea.setBackground(new Color(252, 246, 245)); // Set background color
        JScrollPane scrollPane = new JScrollPane(statusTextArea); // Create JScrollPane for JTextArea
        statusPanel.add(statusLabel, BorderLayout.NORTH); // Add status label to north of status panel
        statusPanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to center of status panel
        statusPanel.setBorder(new EmptyBorder(10, 20, 20, 20)); // Add empty border for spacing
        frame.add(statusPanel, BorderLayout.SOUTH); // Add status panel to south of the frame

        frame.setMinimumSize(new Dimension(800, 600)); // Set minimum size for the frame
        frame.addComponentListener(new ComponentAdapter() { // Add component listener for frame resizing
            @Override
            public void componentResized(ComponentEvent e) {
                // Resize status panel dynamically when frame size changes
                super.componentResized(e);
                Dimension newSize = frame.getSize(); // Get new size of the frame
                Dimension minSize = frame.getMinimumSize(); // Get minimum size of the frame
                int width = Math.max(newSize.width, minSize.width); // Calculate maximum width
                int height = Math.max(newSize.height, minSize.height); // Calculate maximum height
                frame.setSize(width, height); // Set frame size to maximum values

                // Calculate new size for status panel
                int statusWidth = width - 40; // Calculate width for status panel
                int statusHeight = height - titleLabel.getHeight() - 250; // Calculate height for status panel
                statusPanel.setPreferredSize(new Dimension(statusWidth, statusHeight)); // Set new size for status panel
                frame.revalidate(); // Revalidate frame to update layout
            }
        });

        // Add action listener to pause button
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set paused flag to true
                paused = true;
            }
        });

        // Add action listener to resume button
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set paused flag to false
                paused = false;
            }
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Shutdown executor and reset GUI
                executor.shutdownNow();
                executor = Executors.newFixedThreadPool(5); // Reinitialize executor with fixed thread pool size
                resetGUI(); // Reset GUI components
            }
        });

        // Add action listener to download button
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start download for each entered URL
                for (int i = 0; i < 5; i++) {
                    String url = urlFields[i].getText(); // Get URL from text field
                    if (!url.isEmpty()) {
                        downloadImages(url, i); // Call method to download images
                    }
                }
            }
        });

        frame.pack(); // Pack components to fit preferred sizes
        frame.setLocationRelativeTo(null); // Center frame on screen
        frame.setMinimumSize(frame.getSize()); // Set frame size to its minimum size
        frame.setVisible(true); // Make frame visible
    }

    // Method for downloading images from URLs
    private void downloadImages(String urlString, int index) {
        // Create Callable task for downloading images
        Callable<Void> downloadTask = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    URI uri = new URI(urlString); // Create URI from URL string
                    URL url = uri.toURL(); // Convert URI to URL
                    BufferedImage image = ImageIO.read(url); // Read image from URL
                    if (image != null) {
                        publish("Image downloaded successfully!"); // Publish success message
                        long startTime = System.currentTimeMillis(); // Record start time
                        long totalBytesRead = 0; // Initialize total bytes read
                        long totalFileSize = url.openConnection().getContentLengthLong(); // Get total file size

                        // Simulate download and update progress
                        try (BufferedInputStream in = new BufferedInputStream(url.openStream())) {
                            byte[] buffer = new byte[8192]; // Create buffer for reading data
                            int bytesRead; // Variable to store bytes read in each iteration
                            while ((bytesRead = in.read(buffer)) != -1) {
                                if (Thread.currentThread().isInterrupted()) {
                                    return null; // If interrupted, stop download process
                                }
                                totalBytesRead += bytesRead; // Increment total bytes read
                                int progress = (int) ((totalBytesRead * 100) / totalFileSize); // Calculate progress
                                setProgress(index, progress); // Set progress bar value
                                while (paused) {
                                    Thread.sleep(100); // Sleep to reduce CPU load when paused
                                }
                            }
                        }

                        long endTime = System.currentTimeMillis(); // Record end time
                        long elapsedTime = endTime - startTime; // Calculate elapsed time
                        double downloadSpeed = (totalBytesRead / 1024.0) / (elapsedTime / 1000.0); // Calculate download speed
                        publish("Download speed: " + String.format("%.2f", downloadSpeed) + " KB/s"); // Publish download speed message
                        saveImage(image); // Save image to disk
                    } 
                    else {
                        publish("Failed to download image."); // Publish failure message
                    }
                } 
                catch (IOException | URISyntaxException ex) {
                    publish("Error: " + ex.getMessage()); // Publish error message
                }
                return null;
            }

            // Method for publishing status messages to text area
            private void publish(String message) {
                SwingUtilities.invokeLater(() -> statusTextArea.append(message + "\n")); // Append message to text area
            }

            // Method for setting progress of progress bar
            private void setProgress(int index, int value) {
                SwingUtilities.invokeLater(() -> progressBars[index].setValue(value)); // Set progress bar value
            }
        };

        executor.submit(downloadTask); // Submit download task to executor
    }

    // Method for saving image to disk
    private void saveImage(BufferedImage image) {
        try {
            // Get downloads folder
            String downloadsFolder = System.getProperty("user.home") + File.separator + "Downloads";

            // Create unique file name based on timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
            String fileName = dateFormat.format(new Date()) + ".png";

            // Save image to downloads folder
            Path outputPath = Paths.get(downloadsFolder, fileName);
            ImageIO.write(image, "png", outputPath.toFile());

            // Publish status message
            publish("Image saved to: " + outputPath.toString());
        } 
        catch (IOException e) {
            publish("Error saving image: " + e.getMessage()); // Publish error message if saving fails
        }
    }

    // Method for publishing status messages
    private void publish(String string) {
        SwingUtilities.invokeLater(() -> statusTextArea.append(string + "\n")); // Append message to text area
    }

    // Main method for starting the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageDownloaderDesktopApplication::new); // Create instance of ImageDownloaderApp and run in EDT
    }
}
