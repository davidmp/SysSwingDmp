
package pe.com.syscenterlife.jtablecomp;


import java.util.ArrayList;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {

   public List<JButton> buttons=new ArrayList<>();
   public static String[][] metaDataButtons; 
   public static String[] dataA=new String[2];

    public ButtonsPanel() {
        super();
        for (String[] metab : metaDataButtons) {
            JButton bx=new JButton(metab[0]);
            bx.setIcon(new ImageIcon(this.getClass().getResource("/" + metab[1])));
            this.buttons.add(bx);
        }

        setOpaque(true);
        for (JButton b : buttons) {
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            add(b);
        }
    }

}




