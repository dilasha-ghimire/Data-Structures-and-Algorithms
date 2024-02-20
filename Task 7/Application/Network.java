import java.io.*;
import java.util.*;

//Class of a User
class User {
    private String username;
    private List<String> posts; 
    private Set<String> interests; 
    private List<String> interactions; 

    public User(String username,Set<String> interests) {
        this.username = username;
        posts = new ArrayList<>();
        this.interests = interests;
        interactions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<String> getPosts() {
        return posts;
    }
    public Set<String> getInterests() {
        return interests;
    }

    public void postContent(String content) {
        posts.add(content);
    }

    public void followUser(User otherUser, Network network) {
        network.addEdge(this, otherUser);
    }
    public void trackInteraction(String interaction) {
        interactions.add(interaction);
    }

    // Save usering data to a file (user.txt)
    public void saveUserDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Username: " + username);
            writer.println("Interests: " + interests.toString());
            writer.println("Posts:");
            for (String post : posts) {
                writer.println(post); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //loading set of strings from User.txt
    public void loadUserDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean readingPosts = false;
            boolean readingInterests = false;
            StringBuilder currentPost = new StringBuilder(); 
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Interests: ")) {
                    readingInterests = true;
                    // Extract interests
                    String interestsStr = line.substring("Interests: ".length());
                    interestsStr = interestsStr.substring(1, interestsStr.length() - 1);
                    String[] interestsArray = interestsStr.split(", ");
                    interests = new HashSet<>(Arrays.asList(interestsArray));
                } else if (line.equals("Posts:")) {
                    readingPosts = true;
                } else if (readingPosts && line.equals("---")) {
                    // Add the accumulated post to the list
                    if (currentPost.length() > 0) {
                        posts.add(currentPost.toString());
                        currentPost.setLength(0);
                    }
                } else if (readingPosts) {
                    // Append the line to the current post
                    currentPost.append(line).append("\n");
                }
            }
            // Add the last post if there's any
            if (currentPost.length() > 0) {
                posts.add(currentPost.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//this is to define the distance or relation between users. Creation of Edge in Network graph exixsts if the relation exixsts
class Edge {
    private User source;
    private User destination;

    public Edge(User source, User destination) {
        this.source = source;
        this.destination = destination;
    }

    public User getSource() {
        return source;
    }

    public User getDestination() {
        return destination;
    }
}

public class Network {
    private List<User> users;
    private List<Edge> edges;

    //Here I have use Adjacenty list to define the relation
    public Network() {
        users = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addEdge(User source, User destination) {
        edges.add(new Edge(source, destination));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void saveEdgesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Edge edge : edges) {
                writer.println(edge.getSource().getUsername() + " -> " + edge.getDestination().getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEdgesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" -> ");
                String sourceUsername = parts[0];
                String destUsername = parts[1];
                User sourceUser = getUserByUsername(sourceUsername);
                User destUser = getUserByUsername(destUsername);
                if (sourceUser != null && destUser != null) {
                    edges.add(new Edge(sourceUser, destUser));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saving graph ds to a file (network.txt)
    public void saveNetworkDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.println("Username: " + user.getUsername());
                writer.println("Interests: " + user.getInterests().toString());
                writer.println("Posts:");
                for (String post : user.getPosts()) {
                    writer.println(post); 
                }
                writer.println("---"); 
            }
            writer.println("Edges:");
            for (Edge edge : edges) {
                writer.println("Edge: " + edge.getSource().getUsername() + " -> " + edge.getDestination().getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
