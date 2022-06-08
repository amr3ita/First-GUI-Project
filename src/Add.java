import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

public class Add {
    static int c = 0;
    JLabel comments[] = new JLabel[10];
    int type;
    public JButton delete = new JButton("Delete");
    public JPanel post1 = new JPanel();
    public JButton submit = new JButton("submit");
    public JTextArea text1 = new JTextArea();
    public JButton edit = new JButton("Edit");
    public String x = text1.getText();

    public Add(int t) {
        type = t;
    }

    public int count() {
        return c++;
    }

    public JPanel post(String s) {

        String str = s;

        // post1 panel

        post1.setLayout(new BorderLayout());

        // textarea
        JPanel Etxt = new JPanel();
        Etxt.setLayout(new BorderLayout());

        JPanel Ebut = new JPanel();
        Ebut.setLayout(new FlowLayout());

        submit.setVisible(false);
        Ebut.add(edit);
        Ebut.add(submit);
        Ebut.add(delete);

        if (type == 1) {
            edit.setVisible(true);
            delete.setVisible(true);
        } else {
            edit.setVisible(false);
            delete.setVisible(false);
        }

        // textarea1
        text1.setEditable(false);
        text1.setText(str);
        text1.setSize(400, 300);
        text1.setLineWrap(true);
        text1.setWrapStyleWord(true);

        Etxt.add(Ebut, BorderLayout.NORTH);
        Etxt.add(new JScrollPane(text1), BorderLayout.CENTER);

        // display comments
        JPanel show_comment = new JPanel();
        show_comment.setLayout(new BorderLayout());

        // comments textarea
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(10, 1));
        show_comment.add(pane, BorderLayout.CENTER);

        for (int i = 0; i < 10; i++) {
            comments[i] = new JLabel("");
            pane.add(comments[i]);
        }

        // add comment and comment textfield
        JPanel add_comment = new JPanel();
        add_comment.setLayout(new FlowLayout());
        JTextField addcomment = new JTextField(70);

        JButton send = new JButton("Add");

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addcomment.getText().equals("")) {
                    comments[count()].setText(addcomment.getText());
                    addcomment.setText("");
                }
            }
        });
        // before
        x = text1.getText();
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.setVisible(true);
                text1.setEditable(true);
            }
        });

        add_comment.add(addcomment);
        add_comment.add(send);

        show_comment.add(add_comment, BorderLayout.NORTH);

        add_comment.setBorder(
                new CompoundBorder(BorderFactory.createTitledBorder("Add Comment"), add_comment.getBorder()));

        post1.add(new JScrollPane(Etxt), BorderLayout.NORTH);
        post1.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Post"), post1.getBorder()));
        post1.add(show_comment, BorderLayout.CENTER);
        pane.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Comments"), pane.getBorder()));

        return post1;
    }
}
