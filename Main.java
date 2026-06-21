import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OnlineVotingSystem extends JFrame {

    private JTextField nameField;
    private JTextField voterIdField;
    private JTextField ageField;

    private JComboBox<String> partyBox;

    private Map<String, Integer> votes;
    private Set<String> votedIds;

    public OnlineVotingSystem() {

        votes = new HashMap<>();
        votedIds = new HashSet<>();

        votes.put("DMK", 0);
        votes.put("ADMK", 0);
        votes.put("NTK", 0);
        votes.put("TVK", 0);

        setTitle("Online Voting System");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(
                "ONLINE VOTING SYSTEM",
                JLabel.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(700, 60));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Voter Registration"));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Voter ID:"));
        voterIdField = new JTextField();
        formPanel.add(voterIdField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Select Party:"));

        String[] parties = {
                "DMK",
                "ADMK",
                "NTK",
                "TVK"
        };

        partyBox = new JComboBox<>(parties);
        formPanel.add(partyBox);

        JButton voteButton = new JButton("CAST VOTE");
        voteButton.setBackground(new Color(0, 153, 76));
        voteButton.setForeground(Color.WHITE);

        JButton resultButton = new JButton("SHOW RESULTS");
        resultButton.setBackground(new Color(0, 102, 204));
        resultButton.setForeground(Color.WHITE);

        formPanel.add(voteButton);
        formPanel.add(resultButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 16));

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        voteButton.addActionListener(e -> {

            String name = nameField.getText().trim();
            String voterId = voterIdField.getText().trim();
            String ageText = ageField.getText().trim();

            if (name.isEmpty() ||
                voterId.isEmpty() ||
                ageText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all details.");
                return;
            }

            int age;

            try {
                age = Integer.parseInt(ageText);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid age.");
                return;
            }

            if (age < 18) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not eligible to vote.");
                return;
            }

            if (votedIds.contains(voterId)) {
                JOptionPane.showMessageDialog(
                        this,
                        "This Voter ID has already voted.");
                return;
            }

            String party =
                    (String) partyBox.getSelectedItem();

            votes.put(
                    party,
                    votes.get(party) + 1);

            votedIds.add(voterId);

            JOptionPane.showMessageDialog(
                    this,
                    "Vote Successfully Cast for "
                            + party);

            nameField.setText("");
            voterIdField.setText("");
            ageField.setText("");
        });

        resultButton.addActionListener(e -> {

            String winner = "";
            int maxVotes = -1;

            resultArea.setText(
                    "===== ELECTION RESULTS =====\n\n");

            for (Map.Entry<String, Integer> entry :
                    votes.entrySet()) {

                resultArea.append(
                        entry.getKey()
                                + " : "
                                + entry.getValue()
                                + " Votes\n");

                if (entry.getValue() > maxVotes) {
                    maxVotes = entry.getValue();
                    winner = entry.getKey();
                }
            }

            resultArea.append(
                    "\n========================\n");

            resultArea.append(
                    "WINNING PARTY : "
                            + winner);

            resultArea.append(
                    "\nTOTAL VOTES : "
                            + maxVotes);
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new OnlineVotingSystem());
    }
}
