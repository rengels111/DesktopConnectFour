package four;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    static Color background = new Color(128, 200, 255);

    public Cell(String label) {
        super(" "); // Setzt das Label auf die Zelle

        // Schriftgröße und Schriftart anpassen
        setFont(new Font("Arial", Font.BOLD, 40));

        // Deaktiviert das Highlighting beim Klicken
        setFocusPainted(false);

        // Hintergrundfarbe setzen
        setBackground(background);

        // Setzt den Namen im Format ButtonA1, ButtonB2, etc.
        setName("Button" + label);
    }
}
