import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientServerGUI implements ActionListener {
    private JFrame frame;
    private JButton clientButton;
    private JButton serverButton;
    
    public ClientServerGUI() {
        frame = new JFrame("Main Page");
        clientButton = new JButton("Client");
        serverButton = new JButton("Server");
        
        clientButton.addActionListener(this);
        serverButton.addActionListener(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Add the client button to the center of the panel
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(clientButton, c);
        
        // Add the server button to the center of the panel
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(serverButton, c);
        
        frame.add(panel);
        frame.setSize(300, 300);
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
