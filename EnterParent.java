/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author linna
 */
public class EnterParent extends javax.swing.JFrame {

    private JFrame previousFrame;
    private String studentUsername;
    private String parentacc;
     
    public EnterParent(JFrame previousFrame) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.previousFrame = previousFrame;
        
        // Add a window listener to the JFrame
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    // Establish database connection
                    String SUrl = "jdbc:mysql://localhost:3306/loginandregister";
                    String SUser = "root";
                    String Spass = "@2004Nc1001";
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(SUrl, SUser, Spass);
                    
                    // Delete both the student and parent accounts from the database
                    String deleteQuery = "DELETE FROM user WHERE Username = ?";
                    String deleteParentQuery = "DELETE FROM user WHERE Username = ?";
                    
                    PreparedStatement deletePstmt = con.prepareStatement(deleteQuery);
                    deletePstmt.setString(1, studentUsername);
                    deletePstmt.executeUpdate();
                    
                    PreparedStatement deleteParentPstmt = con.prepareStatement(deleteParentQuery);
                    deleteParentPstmt.setString(1, parentacc);
                    deleteParentPstmt.executeUpdate();
                    
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    // Method to set the username
    public void setUser(String username) {
        this.studentUsername = username;
    }
    
    public void setparentacc(String parentacc) {
        this.parentacc=parentacc;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Parent1 = new javax.swing.JTextField();
        Parent2 = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel1.setText("Please Enter Your Parent's Username:");

        Parent1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N

        Parent2.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N

        label1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        label1.setText("(optional)");

        button1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        button1.setLabel("Confirm");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Parent2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Parent1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel1)
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Parent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(Parent2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        String p1 = Parent1.getText();
        String p2 = Parent2.getText();
       
        if(p1.equals("")){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter your parent's username","Error", JOptionPane.ERROR_MESSAGE);
        }else if(!usernameAlreadyExists(p1) && !p2.isEmpty() && !usernameAlreadyExists(p2)){
            Confirm1 c = new Confirm1();
            c.setVisible(true);
        }else if(!usernameAlreadyExists(p1)){
            Confirm1 c = new Confirm1();
            c.setVisible(true);
        }else if(!p2.isEmpty() && !usernameAlreadyExists(p2)){
            Confirm1 c = new Confirm1();
            c.setVisible(true);
        }else{
            
            try{
                String SUrl, SUser, Spass, query;
                SUrl = "jdbc:mysql://localhost:3306/loginandregister";
                SUser = "root";
                Spass="@2004Nc1001";
        
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(SUrl, SUser, Spass);
                Statement st = con.createStatement();
                
                query = "UPDATE user SET Parent1 = ?"; // Start with updating Parent1
                
                if (!p2.isEmpty()) {
                    query += ", Parent2 = ?"; // Add Parent2 to the query if it's not empty
                }
                
                query += " WHERE Username = ?"; // Update based on the current user's username
                
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, p1);
                
                if (!p2.isEmpty()) {
                    pstmt.setString(2, p2); // Set Parent2 if it's not empty
                    pstmt.setString(3, studentUsername); // Set the username of the current user
                } else {
                    pstmt.setString(2, studentUsername); // Set the username of the current user
                }
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                // Successfully saved parent usernames to the database
                // Generate the text file using ParentChildTxtGenerator
                ParentChildTxtGenerator.main(new String[0]); // Call the main method of ParentChildTxtGenerator to generate the file
                
                // Check the type of the previous frame
                if (previousFrame instanceof signupforstudent) {
                    // If previous frame was SignUpForParent, open EnterParent frame
                    showMessageDialog(null, "Account has been created successfully!");

                } else {
                    // If previous frame was SignUp, open Login frame
                    showMessageDialog(null, "Account has been created successfully!");
                    login logdin = new login();
                    logdin.setVisible(true);
                }
                    dispose(); // Dispose the current frame
                }else{
                    JOptionPane.showMessageDialog(null, "Failed to save parent usernames to the database!");
                }        
                con.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_button1ActionPerformed

    //method to check if the username already exists in database
    private boolean usernameAlreadyExists(String username) {
        String SUrl, SUser, Spass;
        SUrl = "jdbc:mysql://localhost:3306/loginandregister";
        SUser = "root";
        Spass="@2004Nc1001";

        String query = "SELECT Username FROM user WHERE Role = 'Parent' AND Username = ?";
    
        try (Connection con = DriverManager.getConnection(SUrl, SUser, Spass);
            PreparedStatement pstmt = con.prepareStatement(query)) {
        
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
        
            return rs.next(); // Return true if the username exists in the database
        } catch (Exception e) {
            System.out.println("Error checking username existence: " + e.getMessage());
            return false; // Return false in case of any exception
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EnterChildren.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EnterChildren.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EnterChildren.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnterChildren.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Parent1;
    private javax.swing.JTextField Parent2;
    private java.awt.Button button1;
    private javax.swing.JLabel jLabel1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
