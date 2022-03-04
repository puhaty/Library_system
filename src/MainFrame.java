import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    Library library;
    final private Frame f;
    Panels p;
    Panel lastPanel;
    Font font;

    Thread checker = new Thread() {
        public void run() {
            while (true) {
                if (lastPanel != p.getCurrentPanel()) {
                    f.remove(lastPanel);
                    lastPanel = p.getCurrentPanel();
                    f.add(lastPanel);
                    f.setTitle(p.getTitle());
                    f.setVisible(true);


                }
                try {

                    Thread.sleep(100);

                } catch (InterruptedException e) {

                }
            }
        }
    };


    public MainFrame(Library l) {
        f = new Frame("System Bilbioteczny");
        library = l;
        p = new Panels(ac,library);
    }

    static ActionListener ac;

    public void launchFrame() {
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });


        lastPanel = p.getCurrentPanel();
        f.setBounds(500, 500, 700, 500);
        f.add(lastPanel);
        f.setVisible(true);

        f.setFont(font = new Font("Arial", Font.BOLD, 15)); //nie dziala, zmienia sie tylko rozmiar


        checker.start();
    }

    public static void main(String[] args) {
        Library library = new Library();
        MainFrame guiWindow = new MainFrame(library);
        guiWindow.launchFrame();
    }
}