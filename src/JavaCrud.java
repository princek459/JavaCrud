import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField textName;
    private JTextField textPrice;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField textQty;
    private JTextField textpid;
    private JButton searchButton;

    private static String USERNAME = "root";
    private static String PASSWORD = "";
    public static final String URL = "jdbc:mysql://localhost:8081/gbproducts";

    PreparedStatement pst;
    private Connection conn;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public JavaCrud() {
        open();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, price, qty;

                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();

                try {
                    pst = conn.prepareStatement("INSERT INTO products(pname,price,qty)VALUES(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pid = textpid.getText();
                    pst = conn.prepareStatement("SELECT pname,price,qty FROM products WHERE pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);
                        textName.setText(name);
                        textPrice.setText(price);
                        textQty.setText(qty);
                    }
                    else
                    {
                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid, name, price, qty;

                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();
                pid = textpid.getText();

                try {
                    pst = conn.prepareStatement("UPDATE products SET pname = ?,price = ?,qty = ? WHERE pid = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!!!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                }
                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bid;

                bid = textpid.getText();

                try {
                    pst = conn.prepareStatement("DELETE FROM products  WHERE pid = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!!!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}


//        editButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String name, price, qty;
//
//                name = textName.getText();
//                price = textPrice.getText();
//                qty = textQty.getText();
//
//
//
//            }
//        });


//    Connection conn;
//    PreparedStatement pst;

//    public void Connect() {
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts", "root","");
//            System.out.println("Success");
//        }
//        catch (SQLException ex)
//        {
//            ex.printStackTrace();
//        }
//    }



