import javax.swing.JFrame;

public class Frame {

    public JFrame create_frame(int x, int y, String s) {
        JFrame frame = new JFrame(s);

        frame.setVisible(true);
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
