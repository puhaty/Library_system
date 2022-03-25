import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindowChooseCatalog extends JFrame{
    private JButton buttonClose;
    private JPanel panelChoose;
    private JList<Object> listCatalog;
    private Library library;
    private List<String> catalogs;

    public WindowChooseCatalog(Library library) throws IOException {

        super("Dodawanie katalogu");
        this.library = library;
        this.setContentPane(panelChoose);
        this.setSize(500, 500);
        this.setVisible(true);
        catalogs = new ArrayList<>();

        library.addCatalog(library.readCatalogFromFile(":", "files/catalogToRead.txt"));

        for (Catalog c : library.getCatalogs()) {
            catalogs.add(c.getName());
        }
        listCatalog.setListData(catalogs.toArray());

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
