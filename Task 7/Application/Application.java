import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application extends JFrame {
    private JLabel loggedInLabel;
    private JButton loginButton;
    private JButton createAccountButton; 
    private JTextArea textArea; 
    private Network network; 
    private JPanel postsPanel;
    private User loggedInUser; 
    private JComboBox<String> categoryDropdown;

    public Application() {
        //this is setting up a GUI .
        setTitle("FlipBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        network = new Network();

      
        JLabel headingLabel = new JLabel("FlipBook", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headingLabel, BorderLayout.NORTH);

     
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    
        loggedInLabel = new JLabel("", JLabel.CENTER);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginWindow();
            }
        });
        loginPanel.add(loginButton);
        loginPanel.add(loggedInLabel);

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateAccountWindow();
            }
        });
        loginPanel.add(createAccountButton);

        add(loginPanel, BorderLayout.EAST);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardPanel.setPreferredSize(null);

        //this is to create a drop down so user gets to select their interest before creating user account.
        JPanel cardTopPanel = new JPanel(new BorderLayout());
        String[] categories = {"Memes and Funs", "Politics", "Sports", "Movies", "News"};
        categoryDropdown = new JComboBox<>(categories);
        cardTopPanel.add(categoryDropdown, BorderLayout.NORTH);

        textArea = new JTextArea("Whats on your mind?");
        textArea.setLineWrap(true);

        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton post = new JButton("Post");
        buttonPanel.add(post);

        //triggered when posting
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postContent();
            }
        });

        // Adding components to card panel
        cardPanel.add(cardTopPanel, BorderLayout.NORTH);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding card panel directly to main frame
        add(cardPanel, BorderLayout.CENTER);

        // Panel that  holds posts
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setPreferredSize(null); 
        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        add(postsScrollPane, BorderLayout.SOUTH);

        //loading the post from file
        loadPostsFromFile();
        pack();
    }

    private void postContent() {
        String content = textArea.getText(); 
        String category = (String) categoryDropdown.getSelectedItem();

        if (!content.isEmpty() && loggedInUser != null) {
            loggedInUser.postContent("Category: " + category + "\n" + content); 
            network.saveNetworkDataToFile("Network.txt"); 
            refreshPosts();
            // Display a message indicating successful posting
            JOptionPane.showMessageDialog(null, "Post successfully saved!");
        } else {
            JOptionPane.showMessageDialog(null, "Please log in to post", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String interaction = "Posted content in category: " + category;
        loggedInUser.trackInteraction(interaction);
    }
    //this did not work but I can explain what I am trying to achieve here later.
    private void recommendContent() {
    // Get the logged-in user
    if (loggedInUser == null) {
        return;
    }

        // Initializing a map to store content recommendations
        Map<String, Double> contentRecommendations = new HashMap<>();

        //this is the traversal
        for (Edge edge : network.getEdges()) {
            User sourceUser = edge.getSource();
            User destUser = edge.getDestination();

            
            if (sourceUser.equals(loggedInUser)) {
                for (String interest : destUser.getInterests()) {
                    for (String post : destUser.getPosts()) {
                        if (post.contains(interest)) {
                            // Increasing the relevance score for the if the interest matches with other user's interest
                            double relevanceScore = calculateRelevanceScore(interest, post);
                            contentRecommendations.put(post, contentRecommendations.getOrDefault(post, 0.0) + relevanceScore);
                        }
                    }
            }
        }
    }

        // Sorting the content recommendations by relevance score
        List<Map.Entry<String, Double>> sortedRecommendations = new ArrayList<>(contentRecommendations.entrySet());
        sortedRecommendations.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Displaying the top recommendations to the user
        int recommendationCount = Math.min(5, sortedRecommendations.size());
        for (int i = 0; i < recommendationCount; i++) {
            System.out.println("Recommendation " + (i + 1) + ": " + sortedRecommendations.get(i).getKey());
        }
    }
    private void commentOnPost(String username, String content) {
        // Implementing comment functionality here
        String interaction = "Commented on post by " + username + ": " + content;
        saveInteractionToFile(interaction);
        System.out.println(interaction);
    }
    
    private void likePost(String username, String content) {
        // Implementing like functionality here 
        String interaction = "Liked post by " + username + ": " + content;
        saveInteractionToFile(interaction);
        System.out.println(interaction);
    }
    
    private void saveInteractionToFile(String interaction) {
        try (FileWriter writer = new FileWriter("interactions.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(interaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private double calculateRelevanceScore(String interest, String post) {
        double relevanceScore = 0.0;
        int interestFrequency = countInterestFrequency(interest, post);
        relevanceScore += interestFrequency * 0.5; // Adjusting weight based on frequency
        int interactionCount = countInteractions(post); 
        relevanceScore += interactionCount * 0.3; 
        double userInfluence = calculateUserInteractions(post); 
        relevanceScore += userInfluence * 0.2;

        return relevanceScore;
    }

    // Counting the frequency of the interest in the post
    private int countInterestFrequency(String interest, String post) {
        
        int frequency = 0;
        String[] words = post.split("\\s+"); 
        for (String word : words) {
            if (word.equalsIgnoreCase(interest)) {
                frequency++;
            }
        }
        return frequency;
    }

    // counting interactions here
    private int countInteractions(String post) {
        int interactionCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // to chheck if the line represents a like or comment on the post
                if (line.contains(post) && (line.contains("liked") || line.contains("commented"))) {
                    interactionCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return interactionCount;
    }

    //to calculate total userinteractions
    private double calculateUserInteractions(String post) {
        int totalInteractions = countInteractions(post); // Counting interactions on the post
        int totalFollowers = getTotalFollowers(post); // Getting total followers of the user who posted the content

        // Placeholder calculation for user influence
        double userInfluence = totalInteractions * 0.3 + totalFollowers * 0.7;

        return userInfluence;
    }
    private int getTotalFollowers(String post) {
        String username = getUsernameFromPost(post);
        int totalFollowers = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("interactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Checking if the line represents a follower interaction for the user who posted the given post
                if (line.contains(username) && line.contains("followed")) {
                    totalFollowers++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalFollowers;
    }

    // Helper method to extract username from the post
    private String getUsernameFromPost(String post) {
        // Assuming the username is the first line of the post
        String[] lines = post.split("\n");
        if (lines.length > 0) {
            String firstLine = lines[0];
            if (firstLine.startsWith("Username: ")) {
                return firstLine.substring("Username: ".length());
            }
        }
        return null;
    }

    private void loadPostsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Network.txt"))) {
            String line;
            String username = null;
            String category = null;
            StringBuilder contentBuilder = new StringBuilder();
            boolean readingPosts = false;
    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    username = line.substring("Username: ".length());
                } else if (line.startsWith("Category: ")) {
                    category = line.substring("Category: ".length());
                } else if (line.equals("Posts:")) {
                    readingPosts = true;
                } else if (line.equals("---")) {
                    // Adding the total post to the UI
                    if (username != null && category != null && contentBuilder.length() > 0) {
                        addPostToUI(username, category, contentBuilder.toString());
                        contentBuilder.setLength(0);
                    }
                    // Reset variables
                    username = null;
                    category = null;
                    readingPosts = false;
                } else if (readingPosts) {
                    // Appending the line to the current post content
                    contentBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addPostToUI(String username, String category, String content) {
        JPanel postPanel = new JPanel();
        postPanel.setBorder(BorderFactory.createEtchedBorder());
        postPanel.setLayout(new BorderLayout());
        
        // Adding username and category labels
        JLabel usernameLabel = new JLabel("Username: " + username);
        JLabel categoryLabel = new JLabel("Category: " + category);
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1));
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(categoryLabel);
        postPanel.add(userInfoPanel, BorderLayout.NORTH);
        
        // Adding content text area
        JTextArea contentTextArea = new JTextArea(content);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setEditable(false);
        postPanel.add(new JScrollPane(contentTextArea), BorderLayout.CENTER);
        
        // Adding comment and like buttons
        JButton commentButton = new JButton("Comment");
        commentButton.addActionListener(e -> commentOnPost(username, content));
        JButton likeButton = new JButton("Like");
        likeButton.addActionListener(e -> likePost(username, content));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(commentButton);
        buttonPanel.add(likeButton);
        postPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adding post panel to the posts panel
        postsPanel.add(postPanel);
        postsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adding spacing between posts
        revalidate(); // Refreshing the layout
        repaint(); // Repainting the UI
    }
    
    //work in progress

    // private void followUser(String username) {
    //     if (loggedInUser != null) {
    //         // Trimming leading and trailing whitespace, to make the comparison case-insensitive
    //         String trimmedUsername = username.trim().toLowerCase();
    //         User userToFollow = null;
    //         // Search for the user by iterating over the existing users
    //         for (User user : network.getUsers()) {
    //             if (user.getUsername().toLowerCase().equals(trimmedUsername)) {
    //                 userToFollow = user;
    //                 break;
    //             }
    //         }
    //         if (userToFollow != null) {
    //             loggedInUser.followUser(userToFollow, network);
    //             // Save the updated network data with edges to the file
    //             saveNetworkDataToFile("Network.txt");
    //             refreshPosts();
    //             JOptionPane.showMessageDialog(null, "You are now following " + userToFollow.getUsername());
    //         } else {
    //             JOptionPane.showMessageDialog(null, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
    //         }
    //     } else {
    //         JOptionPane.showMessageDialog(null, "Please log in to follow users", "Error", JOptionPane.ERROR_MESSAGE);
    //     }
    // }
    

    //refreshing the post once the user has posted
    private void refreshPosts() {
        for (User user : network.getUsers()) {
            for (String post : user.getPosts()) {
                // Spliting the post into lines to extract the category
                String[] lines = post.split("\n");
                if (lines.length > 0 && lines[0].startsWith("Category: ")) {
                    String category = lines[0].substring("Category: ".length()); 
                    StringBuilder contentBuilder = new StringBuilder();
                    for (int i = 1; i < lines.length; i++) {
                        contentBuilder.append(lines[i]);
                        if (i < lines.length - 1) {
                            contentBuilder.append("\n");
                        }
                    }
                    String content = contentBuilder.toString();
                    addPostToUI(user.getUsername(), category, content);
                }
            }
        }
    }
    

    private void openLoginWindow() {
        LoginWindow loginWindow = new LoginWindow(this);
        loginWindow.setVisible(true);
    }

    private void openCreateAccountWindow() {
        CreateAccountWindow createAccountWindow = new CreateAccountWindow(this);
        createAccountWindow.setVisible(true);
    }

    public void setUserLoggedIn(User user) {
        loggedInUser = user;
        loggedInLabel.setText("Logged in as: " + user.getUsername());
        loginButton.setText("Logout");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    class LoginWindow extends JFrame {
        private Application parent;

        public LoginWindow(Application parent) {
            this.parent = parent;
            setTitle("Login");
            setSize(300, 150);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            // Username text field
            JTextField usernameField = new JTextField(20);
            JPanel usernamePanel = new JPanel();
            usernamePanel.add(new JLabel("Username:"));
            usernamePanel.add(usernameField);

            // Login button
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    User user = network.getUserByUsername(username);
                    if (user != null) {
                        parent.setUserLoggedIn(user);
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Add components to the login window
            add(usernamePanel, BorderLayout.NORTH);
            add(loginButton, BorderLayout.CENTER);
        }
    }

    //creating account GUI
    class CreateAccountWindow extends JFrame {
        private Application parent;

        public CreateAccountWindow(Application parent) {
            this.parent = parent;
            setTitle("Create Account");
            setSize(300, 200);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            // Username text field
            JTextField usernameField = new JTextField(20);
            JPanel usernamePanel = new JPanel();
            usernamePanel.add(new JLabel("Username:"));
            usernamePanel.add(usernameField);

            // Adding interests checkboxes
            JPanel interestsPanel = new JPanel();
            interestsPanel.setLayout(new GridLayout(0, 1));
            String[] interests = {"Memes and Fun", "Politics", "Sports", "News", "Music"};
            List<JCheckBox> checkBoxes = new ArrayList<>();
            for (String interest : interests) {
                JCheckBox checkBox = new JCheckBox(interest);
                interestsPanel.add(checkBox);
                checkBoxes.add(checkBox);
            }

            // Create account button
            JButton createAccountButton = new JButton("Create Account");
            createAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    if (!username.isEmpty()) {
                        // Get selected interests
                        Set<String> selectedInterests = new HashSet<>();
                        for (JCheckBox checkBox : checkBoxes) {
                            if (checkBox.isSelected()) {
                                selectedInterests.add(checkBox.getText());
                            }
                        }
                        // Create user with selected interests
                        User newUser = new User(username, selectedInterests);
                        network.addUser(newUser);
                        saveNetworkDataToFile("Users.txt"); 
                        parent.setUserLoggedIn(newUser);
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Adding components to the create account window
            add(usernamePanel, BorderLayout.NORTH);
            add(interestsPanel, BorderLayout.CENTER);
            add(createAccountButton, BorderLayout.SOUTH);
        }
    }
    

    // Saving network data to a file
    private void saveNetworkDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Save user details
            for (User user : network.getUsers()) {
                writer.println("Username: " + user.getUsername());
                writer.println("Interests: " + user.getInterests().toString());
                writer.println("Posts:");
                for (String post : user.getPosts()) {
                    writer.println(post);
                }
                writer.println("---");
            }
            // Save edges
            writer.println("Edges:");
            for (Edge edge : network.getEdges()) {
                writer.println("Edge: " + edge.getSource().getUsername() + " -> " + edge.getDestination().getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
