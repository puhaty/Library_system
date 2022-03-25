import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowMain extends JFrame{
    private JPanel panelMain;
    private JPanel panelCard;
    private JPanel panelRight;
    private JPanel panelOptions;
    private JPanel panelClose;
    private JButton buttonClose;
    private JButton buttonChooseCatalog;
    private JButton buttonReadFromFile;
    private JButton buttonSaveToFile;
    private JButton button4;
    private JButton button5;
    private JButton buttonSave;
    private JPanel panelSwitchedCard;
    private JButton butttonPanel1;
    private JButton butttonPanel2;
    private JButton butttonPanel3;
    private JTree tree1;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    public WindowMain() {
        super("projekt systemu bibliotecznego");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonChooseCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonReadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        butttonPanel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCard.removeAll();
                panelCard.add(panel1);
                panelCard.repaint();
                panelCard.revalidate();
            }
        });

        butttonPanel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCard.removeAll();
                panelCard.add(panel2);
                panelCard.repaint();
                panelCard.revalidate();
            }
        });

        butttonPanel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCard.removeAll();
                panelCard.add(panel3);
                panelCard.repaint();
                panelCard.revalidate();
            }
        });
    }
}
