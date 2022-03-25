import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
    private WindowChooseCatalog windowAdd;
    private WindowReadCatalog windowReadCatalog;
    private JFileChooser fc;
    private Library library;

    public WindowMain() {
        super("projekt systemu bibliotecznego");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        library = new Library();

        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("/home/puhaty/Documents/Java/projects/library_system"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Pliki tekstowe","txt"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Pliki csv","csv"));

        buttonClose.addActionListener(e -> dispose());

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonChooseCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    windowAdd = new WindowChooseCatalog(library);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonReadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromFile(fc);
            }
        });

        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.showOpenDialog(panelMain);
                saveFile(fc);
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

    private void readFromFile(JFileChooser fc) {
        windowReadCatalog = new WindowReadCatalog(library);
        //fc.showOpenDialog(panelMain);
        //readFile(fc);
    }

    private void saveFile(JFileChooser fc) {
        File file = fc.getSelectedFile();

    }

    private void readFile(JFileChooser fc) {
        File file = fc.getSelectedFile();
        //System.out.println(file.getName());
    }
}
