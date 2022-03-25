import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindowChooseCatalog extends JFrame{
    private JButton buttonClose;
    private JPanel panelChoose;
    private JList<Catalog> listCatalog;
    private Library library;
    private List<Catalog> catalogs;
    private DefaultListModel<Catalog> model;

    public WindowChooseCatalog(Library library) throws IOException {

        super("Dodawanie katalogu");
        this.library = library;
        this.setContentPane(panelChoose);
        this.setSize(500, 500);
        this.setVisible(true);
        catalogs = new ArrayList<>();

        library.addCatalog(library.readCatalogFromFile(":", "files/catalogToRead.txt"));

        catalogs = library.getCatalogs();
        model = new DefaultListModel<>();
        model.addAll(catalogs);
/*
        for (Catalog c : library.getCatalogs()) {
            catalogs.add(c.getName());
        }
*/
        listCatalog.setModel(model);

        listCatalog.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
