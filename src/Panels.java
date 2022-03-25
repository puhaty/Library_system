import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*TODO dla Filipa <3
-generalnie uporządkować ładnie bloczki zeby jakos to wyglądało (i ich rozmiary!)
-zrobic tak zeby w oknie wyswietlal sie stan katalogu (np przy wyszukaniu działu)
-przeglądanie w miejscach gdzie wczytujemy dane z pliku (gdy wczytujemy plik zeby mozna było sobie wybrać a nie podawać sciezke) jest to w kom. w odp. miejscach
-ustawić proporcjonalne rozmiary przyciskow i pól tekstowych
-custom (czcionka itp)
-zaimplementowac funkcje usuwania ksiazek, usuwania dzialow, edycji istniejacej ksiązki (jesli Mati je dodał, jest 3:10 i poszedl spać XD)
 */


public class Panels {

    Panel currentPanel; // glowny panel

    Panel helloPanel;
    Panel mainPanel;

    Panel loadFromFile;

    Panel addPanel;
    Panel searchPanel;
    Panel editPanel;

    Panel addingNewBookPanel;
    Panel addingNewSectionPanel;
    Panel addingNewSubSectionPanel;

    Panel replaceSectionPanel;
    Panel changeSectionNamePanel;
    Panel bookEditPanel;
    Panel removeBookPanel;
    Panel removeSectionPanel;


    String title;

    Library library;


    public Panels(ActionListener ac,Library l) {
        library = l;
        makeHelloPanel(ac);
        makeMainPanel(ac);
        currentPanel = helloPanel;
        makeAddPanel(ac);
        makeSearchPanel(ac);
        makeEditPanel(ac);
        makeAddingNewBookPanel(ac);
        makeAddingNewSectionPanel(ac);
        makeAddingNewSubSectionPanel(ac);
        makeReplacingSectionPanel(ac);
        makeChangingSectionNamePanel(ac);
        makeLoadFromFile(ac);
    }

    private void makeHelloPanel(ActionListener ac) {
        helloPanel = new Panel(new GridLayout(0, 1));
        helloPanel.setPreferredSize(new Dimension(100, 100));
        JLabel tytul = new JLabel("SYSTEM BIBLIOTECZNY",SwingConstants.CENTER);
        tytul.setFont(new Font("Arial", Font.ITALIC, 16));
        helloPanel.add(tytul);

        Button stworzNowyKatalog;
        TextField stworzKatalog;
        Button wczytajKatalog;
        Button wyjdz;

        helloPanel.add(stworzNowyKatalog = new Button("STWÓRZ NOWY KATALOG"));
        helloPanel.add(stworzKatalog = new TextField("Nazwa Twojego Nowego Katalogu"));
        stworzNowyKatalog.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.addCatalog(stworzKatalog.getText());
                currentPanel = mainPanel;
                title = "System Biblioteczny";
            }
        });

        helloPanel.add(wczytajKatalog = new Button("WCZYTAJ KATALOG Z PLIKU"));
        wczytajKatalog.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //library.addToCatalogFromList();  POTRZEBA DODAC PRZEGLADANIE
                currentPanel = loadFromFile;
                title = "Wczytaj z plikow";
            }
        });

        helloPanel.add(wyjdz = new Button("WYJDŹ"));
        wyjdz.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });


    }

    private void makeMainPanel(ActionListener ac) {
        mainPanel = new Panel(new GridLayout(0, 1));
        mainPanel.setPreferredSize(new Dimension(300, 300));
        JLabel tytul = new JLabel("SYSTEM BIBLIOTECZNY",SwingConstants.CENTER);
        tytul.setFont(new Font("Arial", Font.ITALIC, 16));
        mainPanel.add(tytul);

        Button dodaj;

        mainPanel.add(dodaj = new Button("Dodaj"));
        dodaj.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addPanel;
                title = "Okno dodawania";
            }
        });

        Button edit = new Button("Edycja");
        mainPanel.add(edit);
        edit.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = editPanel;
                title = "Okno edycji";
            }
        });
        Button wyszukaj;

        mainPanel.add(wyszukaj = new Button("Wyszukaj"));
        wyszukaj.setPreferredSize(new Dimension(5, 5)); //NIE DZIAŁA
        wyszukaj.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentPanel = searchPanel;
                title = "Okno wyszukiwania";
            }
        });

        Button zapiszStan;

        mainPanel.add(zapiszStan = new Button("Zapisz aktualny stan katalogu"));
        zapiszStan.setPreferredSize(new Dimension(10, 3));
        zapiszStan.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    library.saveCatalogStructureToFile("Katalog.txt", library.catalogs.get(0)); //może dodać, żeby samemu można było nazwe wybrać
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button wyczyśćKatalog;
        mainPanel.add(wyczyśćKatalog = new Button("Porzuć aktualny katalog"));
        wyczyśćKatalog.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // funkcja do usuwania katalogu
                //library.addCatalog();  chyba dobrze, żeby od razu tworzył się nowy

            }
        });

        Button back;
        mainPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = helloPanel;
                title = "SYSTEM BIBLIOTECZNY";
            }
        });


    }

    private void makeAddPanel(ActionListener ac) {
        addPanel = new Panel(new GridLayout(0, 1));
        addPanel.setPreferredSize(new Dimension(200, 200));

        Button dodajDzial;
        Button dodajKsiazke;
        Button dodajPoddzial;
        Button dodajDaneZPliku;

        addPanel.add(dodajDzial = new Button("Dodaj Dział"));

        dodajDzial.addActionListener(ac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentPanel = addingNewSectionPanel;
                        title = "Dodaj dział";

                    }

                }
        );

        addPanel.add(dodajPoddzial = new Button("Dodaj poddział"));
        dodajPoddzial.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addingNewSubSectionPanel;
                title = "Dodaj poddział";
            }
        });

        addPanel.add(dodajKsiazke = new Button("Dodaj Książkę"));
        dodajKsiazke.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addingNewBookPanel;
                title = "Dodaj książkę";


            }
        });
        addPanel.add(dodajDaneZPliku = new Button("Dodaj z pliku"));
        dodajDaneZPliku.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //library.addToCatalogFromList(); dodać przeglądanie
                currentPanel = addPanel;
                title = "Dodaj z pliku";

            }
        });
        Button back;
        addPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = mainPanel;
                title = "System Biblioteczny";
            }
        });


    }

    private void makeAddingNewBookPanel(ActionListener ac) {
        addingNewBookPanel = new Panel(new GridLayout(0, 1));
        addingNewBookPanel.setPreferredSize(new Dimension(50, 100));

        TextField doJakiegoDziału;
        TextField isbn;
        TextField tytul;
        TextField autor;
        Button dodaj;


        addingNewBookPanel.add(doJakiegoDziału = new TextField("dział", 10));
        addingNewBookPanel.add(isbn = new TextField("ISBN", 10));
        addingNewBookPanel.add(tytul = new TextField("Tytuł", 10));
        addingNewBookPanel.add(autor = new TextField("Autor", 10));
        addingNewBookPanel.add(dodaj = new Button("DODAJ"));

        dodaj.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.addNewBook(library.catalogs.get(0), doJakiegoDziału.getText(), Long.parseLong(isbn.getText()), tytul.getText(), autor.getText());

            }

        });

        Button back;
        addingNewBookPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addPanel;
                title = "Okno edycji";
            }
        });
    }


    private void makeAddingNewSectionPanel(ActionListener ac) {
        addingNewSectionPanel = new Panel(new GridLayout(0, 1));
        addingNewBookPanel.setPreferredSize(new Dimension(50, 100));
        TextField nazwaNowegoDzialu;
        Button dodaj;

        addingNewSectionPanel.add(nazwaNowegoDzialu = new TextField("nazwa nowego działu", 10));
        addingNewSectionPanel.add(dodaj = new Button("DODAJ"));

        dodaj.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.catalogs.get(0).addSection(nazwaNowegoDzialu.getText());
            }
        });
        Button back;
        addingNewSectionPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addPanel;
                title = "Okno edycji";
            }
        });
    }

    private void makeAddingNewSubSectionPanel(ActionListener ac) {
        addingNewSubSectionPanel = new Panel(new GridLayout(0, 1));
        addingNewSubSectionPanel.setPreferredSize(new Dimension(50, 100));

        TextField nazwaDzialuDoKtoregoDodajemy;
        TextField nazwaNowegoPoddzialu;
        Button dodaj;

        addingNewSubSectionPanel.add(nazwaDzialuDoKtoregoDodajemy = new TextField("Do jakiego działu", 10));
        addingNewSubSectionPanel.add(nazwaNowegoPoddzialu = new TextField("nazwa nowego działu", 10));
        addingNewSubSectionPanel.add(dodaj = new Button("DODAJ"));

        dodaj.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.catalogs.get(0).addSubsection(nazwaDzialuDoKtoregoDodajemy.getText(), nazwaNowegoPoddzialu.getText());
            }
        });
        Button back;
        addingNewSubSectionPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = addPanel;
                title = "Okno edycji";
            }
        });
    }


    private void makeEditPanel(ActionListener ac) {
        editPanel = new Panel(new GridLayout(0, 1));
        editPanel.setPreferredSize(new Dimension(200, 200));


        Button przeniesDzial;
        Button zmienNazweDzialu;
        Button edytuIstniejacaKsiazke;
        Button usunDzial;
        Button usunKsiazke;


        //TODO
        //usunDzial, edytujIstnKsiazke


        editPanel.add(przeniesDzial = new Button("Przenieś Dział"));
        przeniesDzial.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = replaceSectionPanel;
                title = "Przenieś dział";
            }
        });

        editPanel.add(zmienNazweDzialu = new Button("Zmień nazwę działu"));
        zmienNazweDzialu.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = changeSectionNamePanel;
                title = "Zmień nazwę działu";
            }
        });


        editPanel.add(edytuIstniejacaKsiazke = new Button("Edytuj istniejącą książkę"));
        edytuIstniejacaKsiazke.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        editPanel.add(usunDzial = new Button("Usuń dział"));
        usunDzial.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FUNKCJA USUWAJACA
            }
        });

        editPanel.add(usunKsiazke = new Button("Usuń książkę"));
        usunKsiazke.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FUNKCJA USUWAJACA
            }
        });


        Button back;
        editPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = mainPanel;
                title = "System Biblioteczny";
            }
        });


    }


    private void makeReplacingSectionPanel(ActionListener ac) {
        replaceSectionPanel = new Panel(new GridLayout(0, 1));
        replaceSectionPanel.setPreferredSize(new Dimension(100, 30));

        TextField przenoszonyDzial;
        TextField doJakiegoDziału;
        Button przenies;

        replaceSectionPanel.add(przenoszonyDzial = new TextField("Dział przenoszony", 5));
        replaceSectionPanel.add(doJakiegoDziału = new TextField("Dział docelowegy", 5));
        replaceSectionPanel.add(przenies = new Button("PRZENIEŚ"));

        przenies.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.catalogs.get(0).replaceSection(przenoszonyDzial.getText(), doJakiegoDziału.getText());
            }
        });
        Button back;
        replaceSectionPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = editPanel;
                title = "Okno edycji";
            }
        });
    }

    private void makeChangingSectionNamePanel(ActionListener ac) {
        changeSectionNamePanel = new Panel(new GridLayout(0, 1));
        changeSectionNamePanel.setPreferredSize(new Dimension(100, 30));

        TextField staraNazwa;
        TextField nowaNazwa;
        Button zmien;

        changeSectionNamePanel.add(staraNazwa = new TextField("Stara nazwa", 5));
        changeSectionNamePanel.add(nowaNazwa = new TextField("Nowa nazwa", 5));
        changeSectionNamePanel.add(zmien = new Button("ZMIEŃ NAZWĘ"));

        zmien.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.catalogs.get(0).editSection(staraNazwa.getText(), nowaNazwa.getText());
            }
        });
        Button back;
        changeSectionNamePanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = editPanel;
                title = "Okno edycji";
            }
        });

    }

    private void makeBookEditPanel(ActionListener ac) {
        bookEditPanel = new Panel(new GridLayout(0, 1));
        bookEditPanel.setPreferredSize(new Dimension(100, 100));


        Button back;
        bookEditPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //funkcja do edycji
                currentPanel = editPanel;
                title = "Okno edycji";
            }
        });


    }

    private void makeLoadFromFile(ActionListener ac) {
        loadFromFile = new Panel();
        loadFromFile.setPreferredSize(new Dimension(100, 100));
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("./files"));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);

        Button back = new Button("Powrot");
        loadFromFile.add(chooser);
        loadFromFile.add(back);
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //funkcja do edycji
                currentPanel = helloPanel;
                title = "System Biblioteczny";
            }
        });
        chooser.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(loadFromFile) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    String fileName = chooser.getSelectedFile().getPath();
                    try {
                        library.addCatalog(library.readCatalogFromFile(":", fileName));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    chooser.setSelectedFile(new File(""));
                    currentPanel = mainPanel;
                    title = "System Biblioteczny";
                }else if((chooser.showOpenDialog(loadFromFile) == JFileChooser.CANCEL_OPTION)){
                    currentPanel = helloPanel;
                    title = "System Biblioteczny";
                }
            }

        });
    }


    private void makeRemoveBookPanel(ActionListener ac) {
        removeBookPanel = new Panel(new GridLayout(0, 1));
        removeBookPanel.setPreferredSize(new Dimension(100, 100));

        TextField nazwaKsiazki;
        Button usun;

        //ponizsza funkcja zalezy od funkcji usuwania
        removeBookPanel.add(nazwaKsiazki = new TextField("Nazwa ksiązki"));

        removeBookPanel.add(usun = new Button("USUŃ"));
        usun.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FUNKCJA USUWANIA
            }
        });

        Button back;
        removeBookPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = editPanel;
                title = "Okno Edycji";
            }
        });
    }

    private void makeRemoveSectionPanel(ActionListener ac) {
        removeSectionPanel = new Panel(new GridLayout(0, 1));
        removeSectionPanel.setPreferredSize(new Dimension(100, 50));

        TextField nazwaDzialu;
        Button usun;

        removeSectionPanel.add(nazwaDzialu = new TextField("Nazwa działu lub poddzialu"));

        removeSectionPanel.add(usun = new Button("USUŃ"));
        usun.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FUNKCJA USUWANIA
            }
        });

        Button back;
        removeSectionPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = editPanel;
                title = "Okno Edycji";
            }
        });


    }


    private void makeSearchPanel(ActionListener ac) {
        searchPanel = new Panel(new GridLayout(0, 1));
        searchPanel.setPreferredSize(new Dimension(200, 200));

        Button wyszukajKsiazke;
        Button wyszukajDzial;
        Button back;
        JComboBox Opcje;

        TextField wyszukiwanaKsiazka;
        TextField wyszukiwanyDzial;

        Label filtrWyszukiwania;
        String[] OpcjeStrings = {"Autor", "ISBN", "Tytuł"};
        searchPanel.add(filtrWyszukiwania = new Label("FILTR WYSZUKIWANIA"));
        searchPanel.add(Opcje = new JComboBox(OpcjeStrings));
        Opcje.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        searchPanel.add(wyszukiwanaKsiazka = new TextField(" "));
        searchPanel.add(wyszukajKsiazke = new Button("WYSZUKAJ KSIĄŻKĘ"));
        wyszukajKsiazke.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Opcje.getSelectedIndex() == 0) {
                    library.searchBookForAuthor(wyszukiwanaKsiazka.getText(), library.catalogs.get(0));
                }
                if (Opcje.getSelectedIndex() == 1) {
                    library.searchBookForIsbn(Long.parseLong(wyszukiwanaKsiazka.getText()), library.catalogs.get(0));
                }
                if (Opcje.getSelectedIndex() == 2) {
                    library.searchBookForTittle(wyszukiwanaKsiazka.getText(), library.catalogs.get(0));
                }
                //czy tu sie w oknie cos pojawi? dodać
            }
        });


        searchPanel.add(wyszukiwanyDzial = new TextField(" "));
        searchPanel.add(wyszukajDzial = new Button("WYSZUKAJ DZIAŁ"));
        wyszukajDzial.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.catalogs.get(0).getSection(wyszukiwanyDzial.getText()); /*tu trzeba dodac
                aby w oknie printowala sie zawartosc dzialu)*/
            }
        });

        searchPanel.add(back = new Button("Powrót"));
        back.addActionListener(ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel = mainPanel;
                title = "System Biblioteczny";
            }
        });


    }


    public String getTitle() {
        return title;
    }

    public Panel getCurrentPanel() {
        return currentPanel;
    }

}
