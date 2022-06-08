
import java.awt.Color;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.*;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Login extends JFrame {

    static ArrayList<String> teacher_name = new ArrayList<>();
    static ArrayList<String> teacher_pass = new ArrayList<>();

    static ArrayList<String> student_name = new ArrayList<>();
    static ArrayList<String> student_pass = new ArrayList<>();

    static Connection connection;
    static Statement statement;
    static ResultSet teach_result;
    static ResultSet stud_result;

    Login() {
        db_conection();
        table();

        JPanel home = new JPanel();
        home.setLayout(new FlowLayout());

        JLabel user = new JLabel("Username");
        JTextField userI = new JTextField(15);

        home.add(user);
        home.add(userI);

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());

        JLabel pass = new JLabel("Password ");
        JPasswordField passI = new JPasswordField(15);

        center.add(pass);
        center.add(passI);

        JPanel end = new JPanel();
        end.setLayout(new FlowLayout());
        JButton login = new JButton("Login");
        JButton signup = new JButton("Signup");

        end.add(login);
        end.add(signup);

        JPanel last = new JPanel();
        JLabel error = new JLabel();

        last.add(error);

        final JFrame frame;
        Frame obj = new Frame();
        frame = obj.create_frame(300, 250, "Login");
        frame.setLayout(new GridLayout(4, 1));

        frame.add(home);
        frame.add(center);
        frame.add(end);
        frame.add(last);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String U = userI.getText();
                String P = passI.getText();
                int c = 0;
                int u, p;

                u = teacher_name.indexOf(U);// 2
                p = teacher_pass.indexOf(P);// 2
                if (u != -1 && u == p) {
                    c = 1;
                    frame.dispose();
                    new Subject(1);
                }
                if (c == 0) {
                    u = student_name.indexOf(U);// 0
                    p = student_pass.indexOf(P);// 0
                    if (u != -1 && u == p) {
                        c = 2;
                        frame.dispose();
                        new Subject(2);
                    }
                }

                if (c == 0) {
                    error.setForeground(Color.red);
                    error.setText("invalid Account");
                }
            }
        });

        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField firstName = new JTextField();
                JPasswordField password = new JPasswordField();

                ButtonGroup type = new ButtonGroup();
                JRadioButton type1 = new JRadioButton("Teacher");
                type1.setSelected(true);
                JRadioButton type2 = new JRadioButton("Student");
                type.add(type1);
                type.add(type2);

                final JComponent[] inputs = new JComponent[] {
                        new JLabel("Name"),
                        firstName,
                        new JLabel("Password"),
                        password,
                        type1, type2
                };
                int result = JOptionPane.showConfirmDialog(null, inputs, "Signup", JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if (type1.isSelected()) {
                        try {
                            int u = teacher_name.indexOf(firstName.getText());
                            int p = teacher_pass.indexOf(password.getText());
                            if (u != -1 && u == p) {
                                JOptionPane.showMessageDialog(null, "this account exist", "Error!",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                statement.executeUpdate("INSERT INTO `project`.`teacher` (`name`, `password`) VALUES ('"
                                        + firstName.getText() + "', '" + password.getText() + "');");
                                teacher_name.add(firstName.getText());
                                teacher_pass.add(password.getText());
                            }

                        } catch (SQLException ex) {
                            System.out.println("Error in insert");
                        }
                    } else {
                        try {
                            int u = student_name.indexOf(firstName.getText());
                            int p = student_pass.indexOf(password.getText());
                            if (u != -1 && u == p) {
                                JOptionPane.showMessageDialog(null, "this account exist", "Error!",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                statement.executeUpdate("INSERT INTO `project`.`student` (`name`, `password`) VALUES ('"
                                        + firstName.getText() + "', '" + password.getText() + "');");
                                student_name.add(firstName.getText());
                                student_pass.add(password.getText());
                            }
                        } catch (SQLException ex) {
                            System.out.println("Error in insert");
                        }
                    }
                }
            }
        });

    }

    public static void table() {
        try {
            statement = connection.createStatement();
            teach_result = statement.executeQuery("SELECT * FROM project.teacher");

            while (teach_result.next()) {
                teacher_name.add(teach_result.getString(2));
                teacher_pass.add(teach_result.getString(3));
            }

            stud_result = statement.executeQuery("SELECT * FROM project.student");

            while (stud_result.next()) {
                student_name.add(stud_result.getString(2));
                student_pass.add(stud_result.getString(3));
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
