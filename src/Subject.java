import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Subject {
    Subject(int type) {
        String[] sub = { "Select Subject", "Math", "OS", "Programing", "CO" };
        JComboBox box = new JComboBox(sub);

        final JFrame content;
        Frame obj = new Frame();
        content = obj.create_frame(350, 200, "Subject");
        content.setLayout(null);

        box.setBounds(80, 20, 180, 40);

        JButton submit = new JButton("submit");
        submit.setBounds(10, 100, 80, 40);

        content.add(box);
        content.add(submit);

        // -----------------------teacher frame-----------------------
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (box.getSelectedIndex() == 1) {
                    content.dispose();
                    new Math(type);
                } else if (box.getSelectedIndex() == 2) {
                    content.dispose();
                    new Os(type);
                } else if (box.getSelectedIndex() == 3) {
                    content.dispose();
                    new Programing(type);
                } else if (box.getSelectedIndex() == 4) {
                    content.dispose();
                    new Co(type);
                }
            }
        });
    }

}
