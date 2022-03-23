import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperator {
    /**
     * funkcja zapisuje strukturę katalogu do pliku
     * @param fileName
     * @param catalog
     * @throws IOException
     */
    void saveCatalogStructureToFile(String fileName, Catalog catalog) throws IOException {

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));

        String indentation = "______", tabulation = "  ";
        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            //System.out.println();
            for (Section i : catalog) {
                if (i == null) break; //wyrzucenie działu: STOP, który został utworzony tylko dla zatrzymania iteracji
                if (i.equals(catalog.getRoot())) {
                    printWriter.println(i);
                } else {
                    if (i.getLevel() == 1) {
                        tabulation = "  ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            printWriter.print(tabulation);
                        }
                    } else {
                        tabulation = "    ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            printWriter.print(tabulation);
                        }
                    }
                    printWriter.print("|");
                    printWriter.print(indentation);
                    printWriter.print(" " + i);
                    printWriter.println("");
                }
            }
            printWriter.println();
        }
        printWriter.close();
    }

    /**
     * funkcja zapisuje strukturę katalogu z książkami do pliku
     * @param fileName
     * @param catalog
     * @throws IOException
     */
    void saveCatalogStructureWithBooksToFile(String fileName, Catalog catalog) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));

        String indentation = "______", tabulation = "  ", sign = "* ";
        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            //System.out.println();
            for (Section i : catalog) {
                if (i == null) break; //zatrzymanie iteracji
                if (i.equals(catalog.getRoot())) {
                    printWriter.println(i);
                    for (Book b : i.getBooks()) {
                        printWriter.println(tabulation + tabulation + sign + b);
                    }
                } else {
                    if (i.getLevel() == 1) {
                        tabulation = "  ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            printWriter.print(tabulation);
                        }
                    } else {
                        tabulation = "       ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            printWriter.print(tabulation);
                        }
                    }
                    printWriter.print("|");
                    printWriter.print(indentation);
                    printWriter.print(" " + i);
                    for (Book b : i.getBooks()) {
                        if (i.getLevel() == 1) {
                            printWriter.println();
                            tabulation = "   ";
                            for (int j = 0; j < i.getLevel(); j++) {
                                printWriter.print(tabulation);
                            }
                        } else {
                            printWriter.println();
                            tabulation = "       ";
                            for (int j = 0; j < i.getLevel(); j++) {
                                printWriter.print(tabulation);
                            }
                        }
                        printWriter.println(tabulation + sign + b);
                    }
                    printWriter.println("");
                }
            }
            printWriter.println();
        }
        printWriter.close();
    }

    /**
     * funkcja zapisuje strukturę katalogu jako plik z separatorami
     * @param fileName
     * @param catalog
     * @throws IOException
     */
    void saveCatalogListToFile(String fileName, Catalog catalog) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));

        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            for (Section i : catalog) {
                if (i == null) break;
                if (i.equals(catalog.getRoot())) {
                    printWriter.println("root" + ":" + i);
                } else {

                    printWriter.println(i.getParent() + ":" + i);
                }
            }
        }
        printWriter.close();

    }

    /**
     * funkcja zapisuje książki do pliku z separatorami
     * @param fileName
     * @param catalog
     * @throws IOException
     */
    public void saveBookListToFile(String fileName, Catalog catalog) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));

        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            for (Section s :catalog) {
                if (s != null) {
                    for (Book b : s.getBooks()) {
                        printWriter.println(b.getSection() + ":" + b.getTitle() + ":" + b.getAuthor() + ":" + b.getIsbn());
                    }
                } else {
                    break;
                }
            }
        }
        printWriter.close();
    }

    List<String> readCatalogFromFileToList(String fileName) throws IOException, FileNotFoundException {
        BufferedReader bufferedReader = null;
        List<String> listCatalogFromFile = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            listCatalogFromFile = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listCatalogFromFile.add(line);
                //System.out.println(line);
            }
        }
        catch (IOException e) {
            System.err.println("Wystapil blad przy wczytywaniu danych");
            //e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return listCatalogFromFile;
    }

    /**
     * funkcja służy do wczytywania stanu katalogu z pliku i tworzenia nowego katalogu
     * @param separator
     * @param filename
     * @return
     * @throws IOException
     */
    Catalog addToCatalogFromList(String separator, String filename) throws IOException {
        List<String> list = readCatalogFromFileToList(filename);
        List<String[]> arrayList;
        if (list != null && !list.isEmpty() && list.get(0).length() > 1) {
            arrayList = new ArrayList<>();
            Catalog catalog = new Catalog(null);

            for (String s : list) {
                String[] strip = s.split(separator);
                if (strip.length > 1) {
                    strip[0] = strip[0].strip();
                    strip[1] = strip[1].strip();
                }
                arrayList.add(strip);
            }

            for (String[] t : arrayList) {
                if (t.length > 1) {
                    if (t[0].equals("root")) {
                        catalog = new Catalog(t[1]);
                        //catalogs.add(catalog);
                    } else {
                        catalog.addSubsection(t[0], t[1]);
                    }
                }
            }
            return catalog;
        }
        else {
            return null;
        }
    }

    /**
     * funkcja służy do nadpisywania struktury istniejπącego katalogu na podstawie pliku
     * @param separator
     * @param filename
     * @return
     * @throws IOException
     */
    Catalog addToCatalogFromList(String separator, String filename, Catalog catalog) throws IOException {
        List<String> list = readCatalogFromFileToList(filename);
        List<String[]> arrayList;
        if (list != null && !list.isEmpty() && list.get(0).length() > 1) {
            arrayList = new ArrayList<>();

            for (String s : list) {
                String[] strip = s.split(separator);
                if (strip.length > 1) {
                    strip[0] = strip[0].strip();
                    strip[1] = strip[1].strip();
                }
                arrayList.add(strip);
            }

            if (arrayList.get(0)[0].equals("root") && arrayList.get(0)[1].equals(catalog.getName())) {
                for (int i = 1; i < arrayList.size(); i++) {
                    if (arrayList.get(i).length > 1) {
                        catalog.addSubsection(arrayList.get(i)[0], arrayList.get(i)[1], false);
                    }
                }
                return catalog;
            } else {
                System.out.println("niepoprawny plik");
                return catalog;
            }
        }
        else {
            return catalog;
        }
    }

    List<String> readBooksFromFileToList(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        List<String> BooksListFromFile = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            BooksListFromFile = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                BooksListFromFile.add(line);
                //System.out.println(line);
            }
        }
        catch (Exception e) {
            System.err.println("Wystapil blad przy wczytywaniu danych");
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return BooksListFromFile;
    }

    void addBooksFromList(String separator, String fileName, Catalog catalog) throws IOException {
        List<String> list = readBooksFromFileToList(fileName);
        List<String[]> arrayList;
        if (list != null && !list.isEmpty() && list.get(0).length() > 1) {
            arrayList = new ArrayList<>();
            String sectionName = null, tittle = null, isbn = null, author = null;
            for (String s : list) {
                String[] strip = s.split(separator);
                if (strip.length > 3) {
                    for (String t : strip) {
                        t = t.strip();
                    }
                    arrayList.add(strip);
                }
            }

            for (String[] t : arrayList) {
                if (t.length > 3) {
                    sectionName = t[0];
                    tittle = t[1];
                    author = t[2];
                    isbn = t[3];
                    Section section = catalog.getSection(sectionName);
                    if (section != null) {
                        section.addBook(sectionName, Long.parseLong(isbn), tittle, author);
                    } else {
                        System.out.println("niepoprawna nazwa działu, sprawdź czy dział znajduje się w katalogu");
                    }

                }
            }
        } else {
            System.out.println("brak danych z pliku lub niepoprawne dane!!!");
        }
    }
}
