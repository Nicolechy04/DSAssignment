import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

class TreeNode {
    String username;
    List<TreeNode> friends;

    public TreeNode(String username) {
        this.username = username;
        this.friends = new ArrayList<>();
    }
}

public class FriendshipGraph extends JFrame {

    private TreeNode root;
    private Connection connection;
    private String loggedInUsername;
    private String currentUserType;
    private Map<String, JButton> userButtons;

    public FriendshipGraph(String loggedInUsername, String currentUserType) {
        this.loggedInUsername = loggedInUsername;
        this.currentUserType = currentUserType;
        root = new TreeNode(loggedInUsername);
        userButtons = new HashMap<>();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginandregister", "root", "@2004Nc1001");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buildTreeFromDatabase();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Friendship Graph");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLines(g);
            }
        };
        mainPanel.setLayout(null);
        add(mainPanel);

        addButtons(mainPanel, root);

        setVisible(true);
    }

    private void buildTreeFromDatabase() {
        Set<String> visited = new HashSet<>();
        buildTree(root, loggedInUsername, visited);

    }

    private void buildTree(TreeNode node, String username, Set<String> visited) {
        if (visited.contains(username)) {
            return; // Avoid infinite recursion by skipping already visited users
        }
        visited.add(username); // Mark the current user as visited

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT Friends FROM student WHERE Username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String friendsString = resultSet.getString("Friends");
                String[] friendsArray = friendsString.split(","); // Assuming ',' is the delimiter
                for (String friendUsername : friendsArray) {
                    TreeNode friendNode = new TreeNode(friendUsername.trim());
                    node.friends.add(friendNode);
                    // Recursively build tree for friend
                    buildTree(friendNode, friendUsername.trim(), visited);
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void addButtons(JPanel panel, TreeNode node) {
        JButton button = new JButton(node.username);
        button.setBounds(50, 50, 100, 50);
        panel.add(button);
        userButtons.put(node.username, button);

        int y = 200;
        for (TreeNode friend : node.friends) {
            JButton friendButton = new JButton(friend.username);
            friendButton.setBounds(300, y, 100, 50);
            panel.add(friendButton);
            userButtons.put(friend.username, friendButton);
            y += 100;
        }
    }

    private void drawLines(Graphics g) {
        for (TreeNode friend : root.friends) {
            JButton userButton = userButtons.get(root.username);
            JButton friendButton = userButtons.get(friend.username);
            if (userButton != null && friendButton != null) {
                Point userPoint = userButton.getLocation();
                Point friendPoint = friendButton.getLocation();
                g.drawLine(userPoint.x + 50, userPoint.y + 25, friendPoint.x + 50, friendPoint.y + 25);
                for (TreeNode friendOfFriend : friend.friends) {
                    if (!friendOfFriend.username.equals(loggedInUsername)) {
                        JButton friendOfFriendButton = userButtons.get(friendOfFriend.username);
                        if (friendOfFriendButton != null) {
                            Point friendOfFriendPoint = friendOfFriendButton.getLocation();
                            g.drawLine(friendPoint.x + 50, friendPoint.y + 25, friendOfFriendPoint.x + 50, friendOfFriendPoint.y + 25);
                        }
                    }
                }
            }
        }
    }

}
