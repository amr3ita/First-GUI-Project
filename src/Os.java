import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JPanel;

public class Os {
    // data base
    static ArrayList<String> post_content = new ArrayList<>();

    static Connection connection;
    static java.sql.Statement statement;
    static ResultSet post_result;

    String post_text = "";
    public static JPanel display = new JPanel();

    Os(int type) {
        db_conection();
        table();

        // page panel
        JPanel page = new JPanel();
        page.setLayout(new BorderLayout());

        // add post button
        JPanel add_post = new JPanel();
        JButton but = new JButton("ADD POST");
        add_post.add(but);
        if (type == 1) {
            but.setVisible(true);
        } else {
            but.setVisible(false);
        }

        // display panel
        display.setLayout(new GridLayout(5, 1));

        page.add(add_post, BorderLayout.NORTH);
        page.add(display, BorderLayout.CENTER);

        final JFrame frame;
        Frame obj = new Frame();
        frame = obj.create_frame(900, 900, "Os");
        frame.setLayout(new GridLayout(1, 1));

        frame.add(new JScrollPane(page));

        for (int i = 0; i < post_content.size(); i++) {
            JTextArea text = new JTextArea(20, 40);
            JButton submit = new JButton("Submit");
            JButton show = new JButton("Show");
            JLabel check = new JLabel("");

            JFrame post = new JFrame();
            post.add(new JScrollPane(text));
            post.add(show);
            post.add(submit);
            post.add(check);

            if (type == 1) {
                submit.setVisible(true);
            } else {
                submit.setVisible(false);
            }
            String data = post_content.get(i).trim();
            Add obj1 = new Add(type);
            if (!data.equals("")) {
                display.add(obj1.post(post_content.get(i)));
            } else {
                check.setForeground(Color.red);
                check.setText("Please Enter Post");
            }
        }

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea text = new JTextArea(20, 40);
                JButton submit = new JButton("Submit");
                JButton show = new JButton("Show");
                JLabel check = new JLabel("");

                JFrame post = new JFrame();
                post.add(new JScrollPane(text));
                post.add(show);
                post.add(submit);
                post.add(check);

                if (type == 1) {
                    submit.setVisible(true);
                } else {
                    submit.setVisible(false);
                }

                post.setLayout(new FlowLayout());
                post.setVisible(true);
                post.setSize(500, 440);
                post.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                post.setLocationRelativeTo(null);

                show.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String fullpost = text.getText() + "\n";
                        JOptionPane.showMessageDialog(null, fullpost, "post", JOptionPane.PLAIN_MESSAGE);
                    }
                });
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String data = text.getText().trim();
                        Add obj = new Add(type);
                        if (!data.equals("")) {
                            post_content.add(text.getText());
                            try {
                                statement.executeUpdate(
                                        "INSERT INTO `project`.`os` (`post`) VALUES ('" + text.getText()
                                                + "');");
                            } catch (SQLException e1) {
                                System.out.println("error in insert");
                            }
                            display.add(obj.post(text.getText()));
                            post.dispose();
                            frame.dispose();
                            frame.revalidate();
                            frame.repaint();
                            frame.setVisible(true);
                            frame.setLocationRelativeTo(null);
                        } else {
                            check.setForeground(Color.red);
                            check.setText("Please Enter Post");
                        }

                        obj.delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                Os.display.remove(obj.post1);
                                post_content.remove(post_content.indexOf(obj.text1.getText()));
                                try {
                                    statement.executeUpdate("DELETE FROM `project`.`os` WHERE (`post` = '"
                                            + obj.text1.getText() + "');");
                                } catch (SQLException e1) {
                                    // TODO Auto-generated catch block
                                    System.out.println("error in delete");
                                }

                                frame.dispose();
                                frame.revalidate();
                                frame.repaint();
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);
                            }
                        });

                        obj.submit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                post_content.set(post_content.indexOf(obj.x), obj.text1.getText());

                                try {
                                    statement.executeUpdate(
                                            "UPDATE `project`.`os` SET `post` = '" + obj.text1.getText()
                                                    + "' WHERE (`post` = '" + obj.x + "');");
                                } catch (SQLException e1) {
                                    // TODO Auto-generated catch block
                                    System.out.println("error in update");
                                }

                                obj.text1.setEditable(false);
                                obj.submit.setVisible(false);
                                frame.dispose();
                                frame.revalidate();
                                frame.repaint();
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);
                            }
                        });
                    }
                });
            }
        });

    }

    public static void table() {
        try {
            statement = connection.createStatement();
            post_result = statement.executeQuery("SELECT * FROM project.os;");

            while (post_result.next()) {
                post_content.add(post_result.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println("Error data table");
        }
    }

    public static void db_conection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "amr", "root");
        } catch (SQLException ex) {
            System.out.println("connection error");
        }
    }

}
