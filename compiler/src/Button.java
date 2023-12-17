import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Button extends JFrame implements ActionListener {
    JButton openFileButton,lexicalButton,syntaxButton,semanticButton,clearButton;
    JLabel resultLabel,codeTextArea;
    private static String syntax;
    private static File file;
    private static JFileChooser fileChooser;
    private static java.io.File selectedFile;
    private static  BufferedReader reader;
    private static StringBuilder content;
    private static String line;

    Button() {
        resultLabel = new JLabel();
        resultLabel.setText(" RESULT: ");
        resultLabel.setForeground((new Color(0xf3f4f5)));
        resultLabel.setSize(250, 500);
        resultLabel.setBounds(290, 20, 500, 50);
        resultLabel.setVisible(true);
        resultLabel.setBorder(BorderFactory.createEtchedBorder());

        codeTextArea = new JLabel();
        codeTextArea.setSize(250, 500);
        codeTextArea.setBounds(290, 80, 500, 230);
        codeTextArea.setVisible(true);
        codeTextArea.setBorder(BorderFactory.createEtchedBorder());
        codeTextArea.setBackground(new Color(0x303a52));
        codeTextArea.setForeground((new Color(0xf3f4f5)));
        Font codeFont = new Font("Arial", Font.PLAIN, 24);
        codeTextArea.setFont(codeFont);

        openFileButton = new JButton();
        openFileButton.setBounds(20, 20, 250, 50);
        openFileButton.addActionListener(this);
        openFileButton.setText("Open File");
        openFileButton.setFocusable(false);
        openFileButton.setBorder(BorderFactory.createEtchedBorder());
        openFileButton.setBackground(new Color(0x303a52));
        openFileButton.setForeground((new Color(0xf3f4f5)));

        lexicalButton = new JButton();
        lexicalButton.setBounds(20, 80, 250, 50);
        lexicalButton.addActionListener(this);
        lexicalButton.setText("Lexical Analysis");
        lexicalButton.setFocusable(false);
        lexicalButton.setBorder(BorderFactory.createEtchedBorder());
        lexicalButton.setBackground(new Color(0x303a52));
        lexicalButton.setForeground((new Color(0xf3f4f5)));
        lexicalButton.setEnabled(false);

        syntaxButton = new JButton();
        syntaxButton.setBounds(20, 140, 250, 50);
        syntaxButton.addActionListener(this);
        syntaxButton.setText("Syntax Analysis");
        syntaxButton.setFocusable(false);
        syntaxButton.setBorder(BorderFactory.createEtchedBorder());
        syntaxButton.setBackground(new Color(0x303a52));
        syntaxButton.setForeground((new Color(0xf3f4f5)));
        syntaxButton.setEnabled(false);

        semanticButton = new JButton();
        semanticButton.setBounds(20, 200, 250, 50);
        semanticButton.addActionListener(this);
        semanticButton.setText("Semantics Analysis");
        semanticButton.setFocusable(false);
        semanticButton.setBorder(BorderFactory.createEtchedBorder());
        semanticButton.setBackground(new Color(0x303a52));
        semanticButton.setForeground((new Color(0xf3f4f5)));
        semanticButton.setEnabled(false);

        clearButton = new JButton();
        clearButton.setBounds(20, 260, 250, 50);
        clearButton.addActionListener(this);
        clearButton.setText("clear");
        clearButton.setFocusable(false);
        clearButton.setBorder(BorderFactory.createEtchedBorder());
        clearButton.setForeground((new Color(0xf3f4f5)));
        clearButton.setBackground(new Color(0x303a52));

        this.setTitle("Theory of Programming Final Project");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(825, 365);
        this.setVisible(true);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.black);
        this.add(openFileButton);
        this.add(lexicalButton);
        this.add(syntaxButton);
        this.add(semanticButton);
        this.add(clearButton);
        this.add(resultLabel);
        this.add(codeTextArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openFileButton) {
            lexicalButton.setEnabled(true);

            fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    // Get the selected file
                    selectedFile = fileChooser.getSelectedFile();

                    // Read the content of the file
                    reader = new BufferedReader(new FileReader(selectedFile));
                    content = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    reader.close();

                    // Set the content to the JTextArea
                    syntax = content.toString();

                    // Create a new instance of LexicalSyntaxChecker for each file
                    LexicalSyntaxChecker lexicalSyntaxChecker = new LexicalSyntaxChecker(syntax);

                    codeTextArea.setText(" TEXT FILE: \n" + syntax);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == lexicalButton) {
            // Use the instance created for the current file
            resultLabel.setText(" RESULT: " + LexicalSyntaxChecker.output);
            lexicalButton.setEnabled(false);
            syntaxButton.setEnabled(true);

        } else if (e.getSource() == syntaxButton) {
            syntaxButton.setEnabled(false);
            semanticButton.setEnabled(true);
            resultLabel.setText("RESULT: " + LexicalSyntaxChecker.semanticCheck());
            codeTextArea.setText(" TEXT FILE: \n" + syntax);

        } else if (e.getSource() == semanticButton) {
            SemanticAnalysis syntaxAnalysis = new SemanticAnalysis(syntax);
            resultLabel.setText("RESULT: " + syntaxAnalysis.semanticCheck());
            codeTextArea.setText(" TEXT FILE: \n" + syntax);
            semanticButton.setEnabled(false);

        } else if (e.getSource() == clearButton) {
            lexicalButton.setEnabled(false);
            syntaxButton.setEnabled(false);
            semanticButton.setEnabled(false);

            resultLabel.setText("RESULT: ");
            codeTextArea.setText("");
        }
    }
}