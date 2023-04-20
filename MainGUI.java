import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientServerGUI implements ActionListener {
    private JFrame frame;
    private JButton clientButton;
    private JButton serverButton;
    
    public ClientServerGUI() {
        frame = new JFrame("Client/Server GUI");
        clientButton = new JButton("Client");
        serverButton = new JButton("Server");
        
        clientButton.addActionListener(this);
        serverButton.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.add(clientButton);
        panel.add(serverButton);
        
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clientButton) {
            openClientGUI();
        } else if (e.getSource() == serverButton) {
            openServerGUI();
        }
    }
    
    private void openClientGUI() {
        JFrame clientFrame = new JFrame("Client GUI");
        // Add components to clientFrame here...
        clientFrame.pack();
        clientFrame.setVisible(true);
    }
    
    private void openServerGUI() {
        JFrame serverFrame = new JFrame("Server GUI");
        // Add components to serverFrame here...
        serverFrame.pack();
        serverFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new ClientServerGUI();
    }
}
