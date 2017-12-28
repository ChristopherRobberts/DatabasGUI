package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 2017-12-28.
 */
public class NyBevakning {
    private JPanel nyBevakning;
    private JTextField textField1;
    private JButton query;

    public NyBevakning() {
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getJpanel() {
        return nyBevakning;
    }
}
