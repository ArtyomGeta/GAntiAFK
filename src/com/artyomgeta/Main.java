package com.artyomgeta;

import org.osgi.framework.Version;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements Runnable {

    private final Version version = new Version(1, 4, 1);
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private final JMenuBar menuBar = new JMenuBar();
    private JPanel panel1;
    private JButton startButton;
    private JComboBox<String> comboBox1;
    private JCheckBox useOnStartCheckBox;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox altTabCheckBox;
    private JButton secondsButton;
    private JButton minutesButton;
    private JTextField textField3;
    private JLabel versionLabel;
    private JLabel workingLabel;
    private JCheckBox minimumCheckBox;
    private Robot robot;
    private final JMenuItem menuItem = new JMenuItem("Help");
    private boolean isWorking = false;
    private boolean minimum = false;


    @Override
    public void run() {
        setJMenuBar(menuBar);
        menuBar.add(menuItem);
        setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 315, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 65, 630, 230));
        setTitle("GAntiAFK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(panel1);
        setResizable(false);
        setDefault();
    }


    public void setDefault() {
        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<>();
        defaultComboBoxModel.addElement("Enter");
        defaultComboBoxModel.addElement("Space");
        defaultComboBoxModel.addElement("W");
        versionLabel.setText("Version: " + version);
        textField1.setText("1000");
        comboBox1.setModel(defaultComboBoxModel);
        defaultComboBoxModel.setSelectedItem("Space");
        this.getRootPane().setDefaultButton(startButton);
        textField2.setText("100");
        minimumCheckBox.setSelected(minimum);
        startButton.addActionListener(e -> {
            if (!isWorking) {
                start(Integer.parseInt(textField1.getText()), comboBoxElementToInt(String.valueOf(comboBox1.getSelectedItem())), useOnStartCheckBox.isSelected(), Integer.parseInt(textField2.getText()), altTabCheckBox.isSelected(), Integer.parseInt(textField3.getText()), minimum);
            } else {
                isWorking = false;
            }
        });

        minimumCheckBox.addActionListener(e -> minimum = !minimum);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isWorking = false;
                System.exit(0);
            }
        });
        altTabCheckBox.setSelected(true);
        secondsButton.addActionListener(e -> {
            try {
                int result = Integer.parseInt(JOptionPane.showInputDialog(this, "How many seconds do you want to convert to millis?"));
                JOptionPane.showMessageDialog(this, (result * 1000) + "");
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Enter the number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        minutesButton.addActionListener(e -> {
            int result;
            try {
                result = Integer.parseInt(JOptionPane.showInputDialog(this, "How many minutes do you want to convert to millis?"));
                JOptionPane.showMessageDialog(this, (result * 60000) + "");
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Enter the number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        menuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "\"delay\" - time after which the cycle will be repeated\n" +
                "\"use on start\" - at start, the cycle will wait 1 period before starting to run\n" +
                "\"button\" - button, emulation of clicking on which will work\n" +
                "\"time to repeat\" - how many times the cycle repeats\n" +
                "\"conventer\" - use this to convert minutes and seconds to milliseconds"));
        textField3.setText("500");


        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    startButton.setEnabled(Integer.parseInt(textField1.getText()) < 6001);
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    if (Integer.parseInt(textField1.getText()) < 6001) {
                        startButton.setEnabled(true);
                    }
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (Integer.parseInt(textField1.getText()) < 6001) {
                        startButton.setEnabled(true);
                    }
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }
        });
        listenToChange(textField2);

        listenToChange(textField3);
    }

    public void setWorking(boolean flag) {
        isWorking = flag;
    }

    private void listenToChange(JTextField textField2) {
        textField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (Integer.parseInt(textField1.getText()) < 6001) {
                        Integer.parseInt(textField2.getText());
                        startButton.setEnabled(true);
                        workingLabel.setText("0 / " + textField2.getText());
                    }
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    if (Integer.parseInt(textField1.getText()) < 6001) {
                        Integer.parseInt(textField2.getText());
                        startButton.setEnabled(true);
                        workingLabel.setText("0 / " + textField2.getText());
                    }
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (Integer.parseInt(textField1.getText()) < 6001) {
                        Integer.parseInt(textField2.getText());
                        startButton.setEnabled(true);
                        workingLabel.setText("0 / " + textField2.getText());
                    }
                } catch (NumberFormatException e1) {
                    startButton.setEnabled(false);
                }
            }
        });
    }

    private int comboBoxElementToInt(String element) {
        switch (element) {
            case "Enter":
                return KeyEvent.VK_ENTER;
            case "Space":
                return KeyEvent.VK_SPACE;
            case "W":
                return KeyEvent.VK_W;
        }
        return 0;
    }


    private void start(int millis, int key, boolean onStart, int times, boolean alt_tab, int hold_delay, boolean minimum) {
        Minimum minimumWindow = new Minimum(this);
        if (!isWorking) {
            Thread thread = new Thread(() -> {
                if (hold_delay > 5000)
                    JOptionPane.showMessageDialog(null, "Big delay will cause suspicion", "Warning", JOptionPane.WARNING_MESSAGE);
                try {
                    robot = new Robot();
                    if (minimum) {
                        setVisible(false);
                        minimumWindow.stopButton.addActionListener(e -> {
                            minimumWindow.dispose();
                            setWorking(false);
                        });
                        new Thread(minimumWindow).start();
                    }
                    if (alt_tab) {
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_TAB);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_TAB);
                    }
                    if (onStart) {
                        robot.delay(millis);
                    }
                    System.out.println("| Started cycle with button " + ANSI_GREEN + comboBox1.getSelectedItem() + ANSI_RESET + " to " + ANSI_GREEN + textField2.getText() + ANSI_RESET + " times, with delay " + ANSI_GREEN + textField2.getText() + ANSI_RESET);
                    isWorking = true;
                    SwingUtilities.invokeLater(() -> {
                        startButton.setText(isWorking ? "Stop" : "Start");
                        textField1.setEnabled(false);
                        textField2.setEnabled(false);
                        textField3.setEnabled(false);
                        comboBox1.setEnabled(false);
                        minimumWindow.setEnabled(false);
                        useOnStartCheckBox.setEnabled(false);
                        altTabCheckBox.setEnabled(false);
                    });
                    for (int i = 0; i < times; i++) {
                        if (isWorking) {
                            if (!this.isFocused()) {
                                robot.keyPress(key);
                                robot.delay(hold_delay);
                                robot.keyRelease(key);
                                System.out.println("| Key " + ANSI_GREEN + comboBox1.getSelectedItem() + ANSI_RESET + " has been pressed: " + ANSI_GREEN + (i + 1) + ANSI_RESET);
                                int finalI = i;
                                SwingUtilities.invokeLater(() -> workingLabel.setText(finalI + 1 + " / " + textField2.getText()));
                                if (minimum) {
                                    SwingUtilities.invokeLater(() -> minimumWindow.update(finalI, Integer.parseInt(textField2.getText())));
                                }
                            } else {
                                i--;
                            }
                        } else {
                            System.out.println("| The cycle was successfully completed " + ANSI_GREEN + i + ANSI_RESET + " times");
                            break;
                        }
                        robot.delay(millis);
                    }
                    isWorking = false;
                    SwingUtilities.invokeLater(() -> {
                        startButton.setText(isWorking ? "Stop" : "Start");
                        textField1.setEnabled(true);
                        textField2.setEnabled(true);
                        textField3.setEnabled(true);
                        comboBox1.setEnabled(true);
                        useOnStartCheckBox.setEnabled(true);
                        altTabCheckBox.setEnabled(true);
                        minimumCheckBox.setEnabled(true);
                        setVisible(true);
                        minimumWindow.dispose();
                    });
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        System.out.println(e.getComponent().getSize());
                    }
                });
            });
            thread.start();
        } else {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(" ______________________________");
        System.out.println("| " + ANSI_BLUE + "©ArtyomGeta" + ANSI_RESET);
        System.out.println("| " + ANSI_RED + "TO STOP USE " + ANSI_RESET + ANSI_GREEN + "CTR+C" + ANSI_RESET);
        Thread thread = new Thread(new Main());
        thread.start();
    }

}
