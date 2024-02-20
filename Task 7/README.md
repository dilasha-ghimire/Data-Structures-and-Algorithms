# FlipBook Social Network

FlipBook is a Java-based social networking application that allows users to connect, share content, and interact with each other based on common interests. This project provides a foundation for building a social networking platform with basic functionalities like user authentication, posting content, following other users, and tracking interactions.

## Features

1. **User Management**: Users can create accounts, log in, and log out of the application. Each user has a unique username and can specify their interests upon account creation.

2. **Posting Content**: Logged-in users can create posts and share their thoughts, opinions, or updates with the community. Posts are categorized based on user-selected interests.

3. **User Interactions**: Users can interact with posts by commenting on them or liking them. These interactions are tracked and stored for future reference.

4. **Data Persistence**: User data, including profiles, posts, and interactions, are saved to files for persistent storage. This ensures that user data is retained between sessions.

## File Structure

 - `Application.java`: Main class that contains the graphical user interface (GUI) setup and orchestrates the application flow.
 - `User.java`: Class representing a user in the social network, including attributes and methods for user management and data manipulation.
 - `Edge.java`: Class representing a connection between users in the social network.
 - `Network.java`: Class managing the network of users and connections, including methods for adding users, creating connections, and persisting network data.
 - `LoginWindow.java`: Class for the login window GUI component.
 - `CreateAccountWindow.java`: Class for the create account window GUI component.
 - `interactions.txt`: File storing user interactions such as comments and likes.
 - `Users.txt`: File storing user data including profiles, interests, and posts.
 - `Network.txt`: File storing network data including user profiles, posts, and connections.