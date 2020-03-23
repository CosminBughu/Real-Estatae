import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.security.auth.Subject;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public class
Table extends JDialog implements ActionListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldID;
    private JTextField textFieldName;
    private JTextField textFieldPret;
    private JLabel id;
    private JLabel area;
    private JLabel nume;
    private JComboBox comboBoxArea;
    private JLabel address;
    private JLabel price;
    private JLabel year;
    private JComboBox comboBoxYear;
    private JLabel rooms;
    private JComboBox comboBoxRooms;
    private JTextField textFieldPropriety;
    private JButton uploadPictureButton;
    private JButton deleteItemButton;
    private JButton modifyItemButton;
    private JTextArea textAreaAdresa;
    private JLabel property;
    private JPanel panel;
    private JTable table1;
    private JLabel poza;

    DefaultTableModel model;
    String name = "";
    String idul = "";
    String adresa = "";
    String pret = "";
    String proprietate = "";
    String zona = "";
    String camere = "";
    String an = "";
    String selectedImagePath = "";
    FileWriter fileWriter;
    FileReader fr;
    File f;
    FileReader fileReader;


    public Table() throws IOException {

        setContentPane(contentPane);
        buttonOK.addActionListener(this);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        addTableHeader();


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                name = textFieldName.getText();
                idul = textFieldID.getText();
                adresa = textAreaAdresa.getText();
                pret = textFieldPret.getText();
                proprietate = textFieldPropriety.getText();
                zona = comboBoxArea.getSelectedItem().toString();
                camere = comboBoxRooms.getSelectedItem().toString();
                an = comboBoxYear.getSelectedItem().toString();


                JLabel imageLabel = new JLabel();
                ImageIcon imageIcon = new ImageIcon(selectedImagePath);
                Image imageIc = imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(imageIc));

                if (name.isEmpty() || idul.isEmpty() || adresa.isEmpty() || pret.isEmpty() || proprietate.isEmpty() || zona.isEmpty() || camere.isEmpty() || an.isEmpty())
                    JOptionPane.showMessageDialog(null, "\"Toate campurile sunt obligatorii\"");
                else
                    model.addRow(new Object[]{name, idul, adresa, pret, proprietate, zona, camere, an, imageLabel});


                if (ae.getActionCommand() == buttonOK.getActionCommand()) {
                    try {
                        String numeFisier = "C:\\Users\\cosmi\\Desktop/Database/" + textFieldID.getText();
                        fileWriter = new FileWriter(numeFisier);
                        fileWriter.write(id.getText() + ": " + textFieldID.getText() + System.lineSeparator());
                        fileWriter.write(nume.getText() + " : " + textFieldName.getText() + System.lineSeparator());
                        fileWriter.write(area.getText() + ": " + comboBoxArea.getSelectedItem() + System.lineSeparator());
                        fileWriter.write(address.getText() + " : " + textAreaAdresa.getText() + System.lineSeparator());
                        fileWriter.write(price.getText() + ": " + textFieldPret.getText() + System.lineSeparator());
                        fileWriter.write(property.getText() + " : " + textFieldPropriety.getText() + System.lineSeparator());
                        fileWriter.write(rooms.getText() + ": " + comboBoxRooms.getSelectedItem() + System.lineSeparator());
                        fileWriter.write(year.getText() + ": " + comboBoxYear.getSelectedItem() + System.lineSeparator());
                        fileWriter.close();



                        //////////////WRITING THAT IMAGE TO THE FOLDER IN "BMP" FORMAT AND GETTING THE ID NAME///////////////////////

                        Image img = imageIcon.getImage();

                        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null),
                                BufferedImage.SCALE_SMOOTH);

                        Graphics2D g2 = bi.createGraphics();
                        g2.drawImage(img, 0, 0, null);
                        g2.dispose();
                        ImageIO.write(bi, "jpg",
                                new File("C:\\Users\\cosmi\\Desktop/Database/" + textFieldID.getText() + ".bmp"));

                        JOptionPane.showMessageDialog(null, "Informatie salvata!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e + "");

                    }
                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////////

                // onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

////////////////////UPLOAD BUTTON ACTION LISTENER///////////////////////////////////////////////////////////////////////

        uploadPictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser browseImageFile = new JFileChooser("C:\\Users\\cosmi\\Pictures\\Wallpapers");

                FileNameExtensionFilter fnef = new FileNameExtensionFilter("images", "png", "jpeg", "jpg");
                browseImageFile.addChoosableFileFilter(fnef);
                int showOpenDialogue = browseImageFile.showOpenDialog(null);

                if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
                    File selectedImageFile = browseImageFile.getSelectedFile();
                    selectedImagePath = selectedImageFile.getAbsolutePath();


                    ImageIcon i1 = new ImageIcon(selectedImagePath);
                    Image image = i1.getImage().getScaledInstance(poza.getWidth(), poza.getHeight(),
                            Image.SCALE_SMOOTH);
                    poza.setIcon(new ImageIcon(image));
                    JOptionPane.showMessageDialog(null, "Image uploaded: " + selectedImagePath);
                    BufferedReader reader;


                }
            }
        });


        modifyItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // readFile();



                String FolderName="C:\\Users\\cosmi\\Desktop/Database";
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + FolderName);
                } catch (IOException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

/////////////////////////////////////////////////TABLE HEADER///////////////////////////////////////////////////////////

    public void addTableHeader() {
        model = (DefaultTableModel) table1.getModel();
        Object Idetifiers[] = new Object[]{"Name", "Id", "Address", "Price", "Type", "Area", "Rooms No.", "Year",
                "Picture"};

        model.setColumnIdentifiers(Idetifiers);
        table1.getColumn("Picture").setCellRenderer(new MyTableCellRenderer());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }


    class MyTableCellRenderer implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            TableColumn tb = table1.getColumn("Picture");
            tb.setMaxWidth(60);
            tb.setMinWidth(60);
            table1.setRowHeight(60);
            return (Component) value;
        }
    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


////////////////////READ DATA FROM FILE , BUT ONLY READS IT (CANNOT BE MODIFIED)////////////////////////////////////////

//    private void readFile(){
//
//        f= new File("C:\\Users\\cosmi\\Desktop/Database");
//        try{
//            fr = new FileReader(f);
//            BufferedReader br = new BufferedReader(fr);
//            while(br.ready()){
//                System.out.println(br.readLine());
//            }
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Table dialog = new Table();
        dialog.pack();
        dialog.setVisible(true);


        System.exit(0);
    }
}
