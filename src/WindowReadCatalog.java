import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowReadCatalog extends JFrame{
    private JButton buttonClose;
    private JPanel panelRead;
    private Library library;

    public WindowReadCatalog(Library library) {

        super("Wczytywanie katalogu");
        this.library = library;
        this.setContentPane(panelRead);
        this.setSize(500, 500);
        this.setVisible(true);



        buttonClose.addActionListener(e -> dispose());
    }


}
