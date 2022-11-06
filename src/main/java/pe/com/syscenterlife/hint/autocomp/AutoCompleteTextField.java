
package pe.com.syscenterlife.hint.autocomp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class AutoCompleteTextField {
    public static ModeloDataAutocomplet dataGetReturnet;
    private static boolean isAdjusting(JComboBox cbInput) {
        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
            return (Boolean) cbInput.getClientProperty("is_adjusting");
        }
        return false;
    }

    private static void setAdjusting(JComboBox cbInput, boolean adjusting) {
        cbInput.putClientProperty("is_adjusting", adjusting);
    }

    public static void setupAutoComplete(final JTextField txtInput, final List<ModeloDataAutocomplet> items) {
        String valor="";
        final DefaultComboBoxModel model = new DefaultComboBoxModel();
        final JComboBox cbInput = new JComboBox(model) {
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 0);
            }
        };
        setAdjusting(cbInput, false);
        for (Object item : items) {
            model.addElement(item);
        }
        cbInput.setSelectedItem(null);
        cbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAdjusting(cbInput)) {
                    if (cbInput.getSelectedItem() != null) {
                        txtInput.setText(cbInput.getSelectedItem().toString());
                        
                    }
                }
            }
        });
        
        cbInput.addMouseListener(new MouseAdapter() {
            
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("VER:"+e.getClickCount());
                 if(cbInput.getSelectedItem()!=null){
                        txtInput.setText(cbInput.getSelectedItem().toString());//Ojo
                        dataGetReturnet=new ModeloDataAutocomplet();
                        dataGetReturnet.setIdx(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getIdx());
                        dataGetReturnet.setNombreDysplay(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getNombreDysplay());
                        dataGetReturnet.setOtherData(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getOtherData());
                        cbInput.setPopupVisible(false);
                }
            }            
        });

        txtInput.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                setAdjusting(cbInput, true);
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_CLEAR) {
                    if (cbInput.isPopupVisible()) {
                        e.setKeyCode(KeyEvent.VK_ENTER);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.setSource(cbInput);
                    cbInput.dispatchEvent(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if(cbInput.getSelectedItem()!=null){
                        txtInput.setText(cbInput.getSelectedItem().toString());//Ojo
                        dataGetReturnet=new ModeloDataAutocomplet();
                        dataGetReturnet.setIdx(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getIdx());
                        dataGetReturnet.setNombreDysplay(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getNombreDysplay());
                        dataGetReturnet.setOtherData(((ModeloDataAutocomplet)cbInput.getSelectedItem()).getOtherData());
                        cbInput.setPopupVisible(false);
                        }
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cbInput.setPopupVisible(false);
                }
                setAdjusting(cbInput, false);
            }
        });
        
        
        
        txtInput.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateList();
            }

            public void removeUpdate(DocumentEvent e) {
                updateList();
            }

            public void changedUpdate(DocumentEvent e) {
                updateList();
            }

            private void updateList() {
                setAdjusting(cbInput, true);
                model.removeAllElements();
                String input = txtInput.getText();
                if (!input.isEmpty()) {
                    for (ModeloDataAutocomplet item : items) {
                        if (item.nombreDysplay.toLowerCase().startsWith(input.toLowerCase())) {
                            model.addElement(item);
                        }
                    }
                }
                cbInput.setPopupVisible(model.getSize() > 0);
                setAdjusting(cbInput, false);
            }
        });
        txtInput.setLayout(new BorderLayout());
        txtInput.add(cbInput, BorderLayout.SOUTH);
                
    }    
}
