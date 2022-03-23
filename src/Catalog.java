import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Catalog implements Iterable<Section> {
    private Section root = null;
    private String name;
    private List<Section> sections;
    private List<Book> books;

    /**
     *konstruktor drzewa
     * @param name - nazwa drzewa
     */
    public Catalog(String name) {
        this.root = new Section(name);
        this.root.setParent(null);
        this.name = name;
        this.sections = new ArrayList<>();
        this.books = new ArrayList<>();
        this.sections.add(root);
    }

    /**
     * funkcja dodaje główne działy do głównego węzła: root
     * @param name - nazwa dodawanego działu
     */
    public void addSection(String name) {
        if (!isSection(name)) {
            Section newSection = new Section(name);
            addChildren(root, newSection, 0);
        } else {
            System.out.println("Dział o nazwie: " + name + " już istnieje");
        }
    }

    /**
     * funkcja dodaje główne działy do głównego węzła: root + jest możliwość nie wypisywania komunikatu
     * @param name - nazwa dodawanego działu
     * @param print - zmienna informująca czy wypisywać komunikat
     */
    public void addSection(String name, boolean print) {
        if (!isSection(name)) {
            Section newSection = new Section(name);
            addChildren(root, newSection, 0);
        } else if (print){
            System.out.println("Dział o nazwie: " + name + " już istnieje");
        }
    }

    /**
     *funkcja dodaje nowy podkatalog do istniejącego już katalogu
     * @param sectionName - nawa działu do którego dodajemy pododdział
     * @param subsectionName - nazwa pododdziału
     */
    public void addSubsection(String sectionName, String subsectionName) {
        if (!isSection(subsectionName)) {
            Section newSubsection = new Section(subsectionName);
            Section SectionToAdd = getSection(sectionName);
            if (SectionToAdd != null) {
                int level = SectionToAdd.getLevel();
                addChildren(SectionToAdd, newSubsection, level);
            } else {
                System.out.println("\nWprowadzono Błędne dane, Nie ma takiego działu: " + sectionName + " : " + subsectionName + "!!!");
            }
        } else {
            System.out.println("Dział o nazwie: " + subsectionName + " już istnieje");
        }
    }

    /**
     *funkcja dodaje nowy podkatalog do istniejącego już katalogu z możliwośćią niewypisywania komunikatu
     * @param sectionName - nawa działu do którego dodajemy pododdział
     * @param subsectionName - nazwa pododdziału
     * @param print - zmienna informująca czy wypisywać komunikat
     */
    public void addSubsection(String sectionName, String subsectionName, boolean print) {
        if (!isSection(subsectionName)) {
            Section newSubsection = new Section(subsectionName);
            Section tempSection = getSection(sectionName);
            if (tempSection != null) {
                int level = tempSection.getLevel();
                addChildren(tempSection, newSubsection, level);
            } else {
                System.out.println("\nWprowadzono Błędne dane, Nie ma takiego działu: " + sectionName + " : " + subsectionName + "!!!");
            }
        } else if (print){
            System.out.println("Dział o nazwie: " + subsectionName + " już istnieje");
        }
    }

    public void removeSection(String sectionName) {
        Section currentSection = getSection(sectionName);
        if (currentSection != null && currentSection.getParent() != null) {
            Section parentSecion = currentSection.getParent();
            parentSecion.getChildren().remove(currentSection);
        } else {
            System.out.println("usuwanie się nie powiodło");
        }
    }

    /**
     * funkcja dodaje dziecko do wskazanego węzła
     * @param current - węzeł do którego będziemy dodawali
     * @param newChildren - nazwa dziecka które dodajemy
     * @param level - głębokość(poziom) drzewa
     */
    private void addChildren(Section current, Section newChildren, int level) {
        current.setChildren(newChildren);
        newChildren.setLevel(level + 1);
        sections.add(newChildren);
        for (int i = 0; i < current.getChildren().size(); i++) {
            current.getChildren().get(i).setParent(current);
        }
    }

    public void editSection(String sectionName, String newSectionName) {
        Section edit = getSection(sectionName);
        if (edit != null) {
            edit.setName(newSectionName);
        } else {
            System.out.println("\nWprowadzono Błędne dane, Nie ma takiego działu!!!");
        }

    }

    public void replaceSection(String replaceSectionName, String targetSectionName) throws NullPointerException{
        Section section1 = getSection(replaceSectionName);
        Section section2 = getSection(targetSectionName);

        if (section1 == null || section2 == null) {
            System.out.println("\nWprowadzono niepoprawną nazwę działu!!!");
        } else if(searchUnderSection(section1, section2)) {
            System.out.println("Nie można przenieść działu, ponieważ dział docelowy zawiera się w przenoszonym dziale lub jest nim!!!");
        } else {
            Section parentSection1 = section1.getParent();
            parentSection1.getChildren().remove(section1);
            section1.setParent(section2);
            section2.setChildren(section1);
            releveling(section1);
            System.out.println();
        }
    }

    void releveling(Section section) throws NullPointerException{
        Section current = null;
        List<Section> currents = null;

        boolean returnIteration = false;

        if((section.getLevel() - section.getParent().getLevel()) != 1) {
            section.setLevel(section.getParent().getLevel() + 1);
        }
        current = section;
        if (current.getChildren().size() > 0) {
            currents = current.getChildren();
            current = currents.get(0);

            while (true) {
                if (current.equals(section) && returnIteration) break;

                if ((current.getLevel() - current.getParent().getLevel()) != 1) {
                    current.setLevel(current.getParent().getLevel() + 1);
                }

                if (current.getChildren().size() > 0 && !returnIteration) { //wejście w kolejne dziecko od lewej
                    currents = current.getChildren();
                    current = currents.get(0);
                } else if (currents.size() > 1 && !current.equals(currents.get(currents.size() - 1))) { //przejście na kolejne dzieckood lewej //current != currents.get(currents.size() - 1) to sprawdza, czy current nie jest już ostatnim elementem na liście, wtedy przechodzimy do rodzica
                    returnIteration = false;
                    int count = 0;
                    for (Section temp : currents) { //wyszukanie poprzedniej lokalizacji działu na podstawie pozycji rodzica
                        if (current.equals(temp)) {
                            current = currents.get(++count);         //przejście na kolejnego rodzica
                            break;
                        }
                        count++;
                    }
                } else {               //powrót do rodzica
                    current = current.getParent();
                    currents = current.getParent().getChildren();
                    returnIteration = true; // zmienna informuje, że current został przypisany do rodzica
                }
            }
        }
    }

    /**
     * funkcja wyszukuje dział section2 pod działem section1.
     * @param sectionToSearch dział poniżej którego przeszukujemy
     * @param sectionToLookFor szukany dział
     * @return true jeśli znajdzie
     */
    public boolean searchUnderSection(Section sectionToSearch, Section sectionToLookFor) throws NullPointerException{
        Section current = null;
        List<Section> currents = null;

        boolean returnIteration = false;

        if(sectionToSearch.equals(sectionToLookFor)) {
            return true;
        }
        current = sectionToSearch;
        if (current.getChildren().size() > 0) {
            currents = current.getChildren();
            current = currents.get(0);

            while (true) {
                if (current.equals(sectionToSearch) && returnIteration) return false;
                if (current.equals(sectionToLookFor)) return true;

                if (current.getChildren().size() > 0 && !returnIteration) { //wejście w kolejne dziecko od lewej
                    currents = current.getChildren();
                    current = currents.get(0);
                } else if (currents.size() > 1 && !current.equals(currents.get(currents.size() - 1))) { //przejście na kolejne dzieckood lewej //current != currents.get(currents.size() - 1) to sprawdza, czy current nie jest już ostatnim elementem na liście, wtedy przechodzimy do rodzica
                    returnIteration = false;
                    int count = 0;
                    for (Section temp : currents) { //wyszukanie poprzedniej lokalizacji działu na podstawie pozycji rodzica
                        if (current.equals(temp)) {
                            current = currents.get(++count);         //przejście na kolejnego rodzica
                            break;
                        }
                        count++;
                    }
                } else {               //powrót do rodzica
                    current = current.getParent();
                    currents = current.getParent().getChildren();
                    returnIteration = true; // zmienna informuje, że current został przypisany do rodzica
                }
            }
        }
        return false;
    }

    //szukanie działu po nazwie
    public Section getSection(String sectionName) throws NullPointerException {
        if (sections.size() > 0) {
            for (Section s : sections) {
                if (s.getName().equals(sectionName)) {
                    return s;
                }
            }
        }
        return null;
    }

    public void showSections() {
        if (sections.size() > 0) {
            for (Section s : sections) {
                System.out.println(s);
            }
        } else {
            System.out.println("Katalog nie zawiera działów");
        }
    }

    public boolean isSection(String sectionName) {
        if (getSection(sectionName) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * sprawdza czy jest drzewo jest puste
     */
    public boolean isEmpty() {
        return root == null;
    }

    public Section getRoot() {
        return root;
    }

    public List<Section> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        for (Section s : sections) {
            books.addAll(s.getBooks());
        }
        return books;
    }

    @Override
    public Iterator<Section> iterator() {
        return new LibraryIterator();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Iterator po drzewie
     */
    class LibraryIterator implements Iterator<Section> {

        private Section current = root;
        private List<Section> currents = null;
        private boolean stopIteration = false, returnIteration = false, nextChildren = false;

        @Override
        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            }
            if (current != null) {
                return !stopIteration;
            } else {
                return false;
            }
        }

        @Override
        public Section next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (true) {
                if (!current.equals(root)) {
                    if (currents.size() > 0 && !returnIteration && !nextChildren) { //przejście przez listę dzieci
                        nextChildren = true;
                        return current;
                    } else if (current.getChildren().size() > 0 && !returnIteration) { //Wejście w kolejne dziecko od lewej
                        nextChildren = false;
                        currents = current.getChildren();
                        current = currents.get(0);
                    } else if (currents.size() > 1 && !current.equals(currents.get(currents.size() - 1))) {//Przejście na kolejnego rodzica
                        returnIteration = false;
                        nextChildren = false;
                        int count = 0;
                        for (Section temp : currents) { //wyszukanie poprzedniej lokalizacji działu na podstawie pozycji rodzica
                            if (current.equals(temp)) {
                                current = currents.get(++count);         //trzeba zmienić żeby brał następną wartość;
                                break;
                            }
                            count++;
                        }
                    } else { //powrót do rodzica
                        if (current.getLevel() > 0) {
                            current = current.getParent();
                            if (current.equals(root)) {
                                stopIteration = true;
                                return null;
                            }
                            currents = current.getParent().getChildren();
                            returnIteration = true;
                            }
                        else {
                            stopIteration = true;
                        }
                    }
                } else { //jeśli nie ma przypisania do current to bierzemy root'a
                    if (root.getChildren().size() > 0) {
                        currents = current.getChildren();
                        current = currents.get(0);
                        return root;
                    } else {
                        stopIteration = true;
                        return root;
                    }
                }
            }
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
}