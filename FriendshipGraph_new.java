import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.base.Function;
import javax.swing.*;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;


public class FriendshipGraph_new extends JFrame {
    private Connection conn;

    public FriendshipGraph_new() {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/loginandregister";
        String username = "root";
        String password = "@2004Nc1001";

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // SQL query to retrieve data
        String query = "SELECT Username, Friends FROM student";

        // Create a graph
        Graph<String, String> graph = new SparseGraph<>();

        try (Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            // Map to store friends of each user
            Map<String, List<String>> friendMap = new HashMap<>();

            // Populate the graph and friendMap
            while (rs.next()) {
                String user = rs.getString("Username");
                String friendsString = rs.getString("Friends");
                if (friendsString != null) {
                    String[] friends = friendsString.split(",");
                    graph.addVertex(user);
                    friendMap.put(user, new ArrayList<>());
                    for (String friend : friends) {
                        graph.addVertex(friend);
                        graph.addEdge(user + "-" + friend, user, friend, EdgeType.UNDIRECTED);
                        friendMap.get(user).add(friend);
                    }
                }
            }

            // Add friends' friends to the graph
            for (String user : friendMap.keySet()) {
                for (String friend : friendMap.get(user)) {
                    List<String> friendsOfFriend = friendMap.getOrDefault(friend, new ArrayList<>());
                    for (String friendOfFriend : friendsOfFriend) {
                        if (!friendOfFriend.equals(user) && !friendMap.get(user).contains(friendOfFriend)) {
                            graph.addVertex(friendOfFriend);
                            graph.addEdge(friend + "-" + friendOfFriend, friend, friendOfFriend, EdgeType.UNDIRECTED);
                        }
                    }
                }
            }

            // Visualization
            // Visualization
            Layout<String, String> layout = new SpringLayout<>(graph);


            // Custom vertex shape transformer to render vertices as clickable buttons
            Function<String, Shape> vertexShapeFunction = vertex -> {
                Rectangle2D rectangle = new Rectangle2D.Double(-20, -10, 40, 20);
                return AffineTransform.getTranslateInstance(layout.apply(vertex).getX(), layout.apply(vertex).getY()).createTransformedShape(rectangle);
            };

            // Custom vertex label transformer to render vertex labels as buttons
            Function<String, String> vertexLabelFunction = vertex -> vertex;

            // Create VisualizationViewer with layout and size
            VisualizationViewer<String, String> vv = new VisualizationViewer<>(layout, new Dimension(600, 600));

            // Set vertex shape and label transformers
            vv.getRenderContext().setVertexShapeTransformer(vertexShapeFunction);
            vv.getRenderContext().setVertexLabelTransformer(vertexLabelFunction);

            // Add vertex mouse listener to handle vertex clicks
            // Add vertex mouse listener to handle vertex clicks
            // Add vertex mouse listener to handle vertex clicks
            vv.getRenderContext().getMultiLayerTransformer().addChangeListener(evt -> {
                Point2D p = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(vv.getMousePosition());
                String vertex = null;
                for (String v : graph.getVertices()) {
                    Shape shape = vertexShapeFunction.apply(v);
                    if (shape.contains(p)) {
                        vertex = v;
                        break;
                    }
                }
                if (vertex != null) {
                    handleVertexClick(vertex);
                }
            });



            vv.setGraphMouse(new DefaultModalGraphMouse<>());

            setTitle("Friendship Graph");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().add(vv);
            pack();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle vertex clicks
    private void handleVertexClick(String vertex) {
        String currentUsername = getCurrentUsername(); // Fetch the current user's username

        // Print the currentUsername to trace its value
        System.out.println("Current Username: " + currentUsername);

        if (vertex.equals(currentUsername)) {
            JOptionPane.showMessageDialog(this, "You clicked on yourself!");
        } else {
            try {
                if (areFriends(currentUsername, vertex)) {
                    JOptionPane.showMessageDialog(this, "You clicked on your friend: " + vertex);
                } else {
                    if (isRequestSent(currentUsername, vertex)) {
                        int option = JOptionPane.showOptionDialog(this, "Friend request has already been sent to this user.",
                                "Delete Friend Request", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                                new String[]{"Delete Friend Request", "Cancel"}, "Delete Friend Request");
                        if (option == JOptionPane.YES_OPTION) {
                            deleteFriendRequest(vertex);
                        }
                    } else {
                        int option = JOptionPane.showOptionDialog(this, "You can send a friend request to this user.",
                                "Send Friend Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                new String[]{"Send Friend Request", "Cancel"}, "Send Friend Request");
                        if (option == JOptionPane.YES_OPTION) {
                            sendFriendRequest(vertex);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to check if the user is already friends with the searched username
    private boolean areFriends(String username, String friend) throws SQLException {
        String query = "SELECT * FROM student WHERE Username=? AND Friends LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, "%" + friend + "%");
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    // Method to check if a friend request has already been sent to the searched username
    private boolean isRequestSent(String username, String friend) throws SQLException {
        String query = "SELECT * FROM student WHERE Username=? AND sent_friends LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, "%" + friend + "%");
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    // Method to delete a friend request
    private void deleteFriendRequest(String friendUsername) throws SQLException {
        String currentUsername = getCurrentUsername(); // Get the current logged-in username

        // Remove friend from friend_requests column
        String deleteFriendRequestsQuery = "UPDATE student SET Friend_Requests = REPLACE(Friend_Requests, ?, '') WHERE Username = ?";
        PreparedStatement deleteFriendRequestsStmt = conn.prepareStatement(deleteFriendRequestsQuery);
        deleteFriendRequestsStmt.setString(1, currentUsername + ",");
        deleteFriendRequestsStmt.setString(2, friendUsername);
        deleteFriendRequestsStmt.executeUpdate();

        // Remove friend from sent_friends column
        String deleteSentFriendsQuery = "UPDATE student SET sent_friends = REPLACE(sent_friends, ?, '') WHERE Username = ?";
        PreparedStatement deleteSentFriendsStmt = conn.prepareStatement(deleteSentFriendsQuery);
        deleteSentFriendsStmt.setString(1, friendUsername + ",");
        deleteSentFriendsStmt.setString(2, currentUsername);
        deleteSentFriendsStmt.executeUpdate();

        JOptionPane.showMessageDialog(this, "Friend request deleted successfully!");
    }



    // Method to send a friend request
    private void sendFriendRequest(String friendUsername) throws SQLException {
        String currentUsername = getCurrentUsername(); // Get the current logged-in username

        // Insert into Sent_Friends table for the current user
        String sentFriendsQuery = "UPDATE student SET sent_friends = CONCAT(IFNULL(sent_friends, ''), ?) WHERE Username=?";
        PreparedStatement sentFriendsStmt = conn.prepareStatement(sentFriendsQuery);
        sentFriendsStmt.setString(1, friendUsername + ",");
        sentFriendsStmt.setString(2, currentUsername);
        sentFriendsStmt.executeUpdate();

        // Update Friend_Requests column of the friend
        String friendRequestQuery = "UPDATE student SET Friend_Requests = CONCAT(IFNULL(Friend_Requests, ''), ?) WHERE Username=?";
        PreparedStatement friendRequestStmt = conn.prepareStatement(friendRequestQuery);
        friendRequestStmt.setString(1, currentUsername + ",");
        friendRequestStmt.setString(2, friendUsername);
        friendRequestStmt.executeUpdate();

        JOptionPane.showMessageDialog(this, "Friend request sent successfully!");
    }

    // Method to get the current logged-in username
    private String getCurrentUsername() {
        // Implement your logic to fetch the current user's username
        return "current_username"; // Placeholder, replace with actual implementation
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FriendshipGraph_new().setVisible(true);
        });
    }
}
