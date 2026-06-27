import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OnlineVotingSystem extends JFrame {

    private JTextField nameField, voterIdField, ageField;
    private JComboBox<String> partyBox;
    private JTextArea resultArea;

    private Map<String, Integer> votes = new HashMap<>();
    private Set<String> votedIds = new HashSet<>();

    public OnlineVotingSystem() {

        votes.put("DMK", 0);
        votes.put("ADMK", 0);
        votes.put("NTK", 0);
        votes.put("TVK", 0);

        setTitle("Online Voting System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("ONLINE VOTING SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Name"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Voter ID"));
        voterIdField = new JTextField();
        form.add(voterIdField);

        form.add(new JLabel("Age"));
        ageField = new JTextField();
        form.add(ageField);

        form.add(new JLabel("Select Party"));
        partyBox = new JComboBox<>(new String[]{"DMK", "ADMK", "NTK", "TVK"});
        form.add(partyBox);

        JButton vote = new JButton("CAST VOTE");
        JButton result = new JButton("SHOW RESULTS");

        form.add(vote);
        form.add(result);

        panel.add(form, BorderLayout.CENTER);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        add(panel);

        vote.addActionListener(e -> castVote());

        result.addActionListener(e -> showResult());

        setVisible(true);
    }

    private void castVote() {

        String name = nameField.getText().trim();
        String id = voterIdField.getText().trim();
        String ageText = ageField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Age");
            return;
        }

        if (age < 18) {
            JOptionPane.showMessageDialog(this, "Not Eligible");
            return;
        }

        if (votedIds.contains(id)) {
            JOptionPane.showMessageDialog(this, "Already Voted");
            return;
        }

        String party = (String) partyBox.getSelectedItem();

        votes.put(party, votes.get(party) + 1);
        votedIds.add(id);

        JOptionPane.showMessageDialog(this, "Vote Cast Successfully");

        nameField.setText("");
        voterIdField.setText("");
        ageField.setText("");
    }

    private void showResult() {

        resultArea.setText("===== RESULTS =====\n\n");

        String winner = "";
        int max = -1;

        for (String party : votes.keySet()) {

            int count = votes.get(party);

            resultArea.append(party + " : " + count + " Votes\n");

            if (count > max) {
                max = count;
                winner = party;
            }
        }

        if (max == 0)
            winner = "No Votes";

        resultArea.append("\nWinner : " + winner);
    }

    public static void loginPage() {

        JFrame login = new JFrame("Login");
        login.setSize(350, 220);
        login.setLayout(new GridLayout(3, 2, 10, 10));
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        login.add(new JLabel("Username"));
        login.add(user);

        login.add(new JLabel("Password"));
        login.add(pass);

        JButton btn = new JButton("LOGIN");
        login.add(new JLabel());
        login.add(btn);

        btn.addActionListener(e -> {

            String u = user.getText();
            String p = new String(pass.getPassword());

            if (u.equals("admin") && p.equals("1234")) {
                login.dispose();
                new OnlineVotingSystem();
            } else {
                JOptionPane.showMessageDialog(login, "Invalid Login");
            }
        });

        login.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> loginPage());
    }
}               
