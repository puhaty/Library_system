import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    List<Catalog> catalogs = new ArrayList<>();

    public void addCatalog(String catalogName) {
        catalogs.add(new Catalog(catalogName));
    }

    public void addCatalog(Catalog catalog) {
        catalogs.add(catalog);
    }

    public void removeCatalog(Catalog catalog) {
        if (catalogs.contains(catalog)) {
            catalogs.remove(catalog);
        }
    }

    public void addNewBook(Catalog catalog, String sectionName, long isbn, String title, String author) {
        boolean ifAdded = false;
        for (Section i : catalog) {
            if (i != null) {
                if (i.getName().equals(sectionName)) {
                    i.addBook(sectionName, isbn, title, author);
                    ifAdded = true;
                }
            }
        }
        if (!ifAdded) System.out.println("nie ma takiego działu!!!");
    }

    public void replaceBook(Catalog catalog, String newSesctionName, String tittle) {
        if (isBook(catalog, tittle)) {
            if (isSection(catalog, newSesctionName)) {
                Book book = getBook(catalog, tittle);
                getSection(catalog, book.getSection()).removeBook(tittle);
                book.setSection(newSesctionName);
                getSection(catalog, newSesctionName).addBook(book);
            }
        }
    }

    /**
     * funkcja usuwa po nazwie książki
     * @param catalog - nazwa katalogu
     * @param tittle - tytuł książki
     */
    public void removeBook(Catalog catalog, String tittle) {
        if (isBook(catalog, tittle)) {
            Book book = getBook(catalog, tittle);
            Section section = getSection(catalog, book.getSection());
            section.removeBook(book);
        }
    }

    /**
     * funkcja usuwa przekazany obiekt książki
     * @param catalog - nazwa katalogu
     * @param book - przekazanie obiektu książki do usunięcia
     */
    public void removeBook(Catalog catalog, Book book) {
        if (isBook(catalog, book)) {
            Section section = getSection(catalog, book.getSection());
            section.removeBook(book);
        }
    }

    public void showCatalogStructure(Catalog catalog) throws NullPointerException {
        String indentation = "______", tabulation = "  ";
        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            System.out.println();
            for (Section i : catalog) {
                if (i == null) break;           //zatrzymanie iteracji
                if (i.equals(catalog.getRoot())) {
                    System.out.println(i);
                } else {
                    if (i.getLevel() == 1) {
                        tabulation = "  ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            System.out.print(tabulation);
                        }
                    } else {
                        tabulation = "    ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            System.out.print(tabulation);
                        }
                    }
                    System.out.print("|");
                    System.out.print(indentation);
                    System.out.print(" " + i);
                    System.out.println();
                }
            }
        }
    }

    public void showCatalogStructureWithBooks(Catalog catalog) throws NullPointerException {
        String indentation = "______", tabulation = "  ", sign = "* ";
        if (catalog.isEmpty()) {
            System.out.println("\nkatalog jest pusty!!");
        } else {
            System.out.println();
            for (Section i : catalog) {
                if (i == null) break; //wyrzucenie działu: STOP, który został utworzony tylko dla zatrzymania iteracji
                if (i.equals(catalog.getRoot())) {
                    System.out.println(i);
                    for (Book b : i.getBooks()) {
                        System.out.println(tabulation + tabulation + sign + b);
                    }
                } else {
                    if (i.getLevel() == 1) {
                        tabulation = "  ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            System.out.print(tabulation);
                        }
                    } else {
                        tabulation = "       ";
                        for (int j = 0; j < i.getLevel(); j++) {
                            System.out.print(tabulation);
                        }
                    }
                    System.out.print("|");
                    System.out.print(indentation);
                    System.out.print(" " + i);
                    for (Book b : i.getBooks()) {
                        if (i.getLevel() == 1) {
                            System.out.println();
                            tabulation = "   ";
                            for (int j = 0; j < i.getLevel(); j++) {
                                System.out.print(tabulation);
                            }
                        } else {
                            System.out.println();
                            tabulation = "       ";
                            for (int j = 0; j < i.getLevel(); j++) {
                                System.out.print(tabulation);
                            }
                        }
                        System.out.print(tabulation + sign + b);
                    }
                    System.out.println();
                }
            }
        }
    }

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



    List<Book> searchBookForAuthor(String author, Catalog catalog) {
        List<Book> list = new ArrayList<>();
        for (Section s : catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.getAuthor().equals(author)) {
                        list.add(b);
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            return list;
        } else {
            System.out.println("nie ma takiej książki w tym katalogu!!");
            return null;
        }
    }

    List<Book> searchBookForIsbn(long isbn, Catalog catalog) {
        List<Book> list = new ArrayList<>();
        for (Section s : catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.getIsbn() == isbn) {
                        list.add(b);
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            return list;
        } else {
            System.out.println("nie ma takiej książki w tym katalogu!!");
            return null;
        }
    }

    List<Book> searchBookForTittle(String tittle, Catalog catalog) {
        List<Book> list = new ArrayList<>();
        for (Section s : catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.getTitle().equals(tittle)) {
                        list.add(b);
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            return list;
        } else {
            System.out.println("nie ma takiej książki w tym katalogu!!");
            return null;
        }
    }

    void searchFilter(String filter, Catalog catalog) {
        List<Book> filterBooks;
        Scanner in = new Scanner(System.in);
        switch (filter) {
            case "author" :
                System.out.print("podaj autora: ");
                String author = in.nextLine();
                filterBooks = searchBookForAuthor(author, catalog);
                if (filterBooks != null){
                for (Book book : filterBooks) {
                    System.out.println(book);
                }
            }
                break;
            case "tittle" :
                System.out.print("podaj tytuł: ");
                String tittle = in.nextLine();
                filterBooks = searchBookForTittle(tittle, catalog);
                if (filterBooks != null) {
                    for (Book book : filterBooks) {
                        System.out.println(book);
                    }
                }
                break;
            case "isbn" :
                System.out.print("podaj nr ISBN: ");
                String isbn = in.nextLine();
                if (isbn.matches("[0-9]+") && isbn.length() == 13) {
                    filterBooks = searchBookForIsbn(Long.parseLong(isbn), catalog);
                    if (filterBooks != null) {
                        for (Book book : filterBooks) {
                            System.out.println(book);
                        }
                    }
                } else {
                    System.out.println("niepoprawny nr ISBN: " + isbn);
                }
                break;
            default:
                System.out.println("niepoprawna opcja!!!");
        }
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public Catalog getCatalog(String catalogName) {
        for (Catalog c : catalogs) {
            if (c.getName().equals(catalogName)) {
                return c;
            } else {
                System.out.println("nie ma takiego katalogu");
                return null;
            }
        }
        return null;
    }


    public void showCatalogs() {
        if (catalogs.size() > 0) {
            for (Catalog c : catalogs) {
                System.out.println(c);
            }
        } else {
            System.out.println("brak katalogów");
        }
    }

    public boolean isCatalog(String catalogName) {
        for (Catalog c : catalogs) {
            if (c.getName().equals(catalogName)) {
                return true;
            } else { return false; }
        }
        return false;
    }

    public void showSections(Catalog catalog) {
        for (Section s : catalog) {
            if (s!= null) {
                System.out.println(s);
            }
        }
    }

    public boolean isSection(Catalog catalog, String sectionName) {
        for (Section s : catalog) {
            if (s != null) {
                if (s.getName().equals(sectionName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Section getSection(Catalog catalog, String sectionName) {
        for (Section s : catalog) {
            if (s != null) {
                if (s.getName().equals(sectionName)) {
                    return s;
                }
            }
        }
        return null;
    }

    public void showBooks(Catalog catalog) {
        for (Section s : catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    System.out.println(b);
                }
            }
        }
    }

    public Book getBook(Catalog catalog, String tittle) {
        for (Section s :catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.getTitle().equals(tittle)) {
                        return b;
                    }
                }
            }
        }
        return null;
    }

    public boolean isBook(Catalog catalog, String tittle) {
        for (Section s :catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.getTitle().equals(tittle)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isBook(Catalog catalog, Book book) {
        for (Section s :catalog) {
            if (s != null) {
                for (Book b : s.getBooks()) {
                    if (b.equals(book)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
