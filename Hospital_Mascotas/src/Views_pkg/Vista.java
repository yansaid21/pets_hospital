/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views_pkg;

import Controller_pkg.Conexion;
import java.awt.HeadlessException;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JEAN SAID
 */
public class Vista extends javax.swing.JFrame {
        Conexion con = new Conexion();
        Connection cn;
        Statement st;
        ResultSet rs;
        DefaultTableModel modelo;
        DefaultTableModel modeloA;
    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();
        show_Owners();
        show_Pets();
        box();
        setLocationRelativeTo(null);
        
    }
    void show_Owners(){
String sql = "SELECT * FROM tb_pet_owners";
try{
cn = con.getConnection();
st = cn.createStatement();
rs = st.executeQuery(sql);
//Los datos que devuelve la consulta se muestran en la tabla
Object[]owner = new Object[7];
modelo = (DefaultTableModel)tbl_Owners.getModel();
while(rs.next()){
owner[0] = rs.getInt("id");
owner[1] = rs.getString("owner");
owner[2] = rs.getInt("id_document");
owner[3] = rs.getString("document_type");
owner[4] = rs.getString("document");
owner[5] = rs.getString("contact");
owner[6] = rs.getString("gender");

modelo.addRow(owner);
//System.out.println(rs.getInt("id"));
}
tbl_Owners.setModel(modelo);
}catch(SQLException e){
}
}
    void add_Owner(){
String name = Txt_Owner.getText();
int id_document = Integer.parseInt(Txt_id_Document.getText());
String document_type = Txt_Document_Type.getText();
String document = Txt_Document.getText();
String contact = Txt_Contact.getText();
String gender = Txt_Gender.getText();

if (name.isEmpty() || Txt_id_Document.getText().isEmpty() || document_type.isEmpty() || document.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
JOptionPane.showMessageDialog(this, "Falta ingresar algún campo del Dueño");
}else{
String query = "INSERT INTO `tb_pet_owners`(`owner`,`id_document`,`document_type`,`document`,`contact`,`gender`)" + " VALUES('" + name + "'," + id_document + ",'" + document_type + "','" + document + "','" + contact + "','" + gender + "')";
try{
cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "El Dueño ha sido creado");
clear_rows_tb_Owners();
show_Owners();
}catch(HeadlessException | SQLException e){
JOptionPane.showMessageDialog(this, "No se pudo crear el Dueño");
}
}
}
    void clear_rows_tb_Owners(){
for (int i = 0; i < tbl_Owners.getRowCount(); i++) {
modelo.removeRow(i);
i = i-1;
} 
//while(modelo.getRowCount()>0){
   //  modelo.removeRow(0);
 //}
txt_id.setText("");
Txt_Owner.setText("");
Txt_id_Document.setText("");
Txt_Document_Type.setText("");
Txt_Document.setText("");
Txt_Contact.setText("");
Txt_Gender.setText("");
    }
    
    void edit_Owner(){
//Hacemos nuevamente lectura de los valores contenidos en los JTextField
//Para identificar si el usuario modifico algún valor
int id = Integer.parseInt(txt_id.getText());
String name = Txt_Owner.getText();
int id_document = Integer.parseInt(Txt_id_Document.getText());
String document_type = Txt_Document_Type.getText();
String document = Txt_Document.getText();
String contact = Txt_Contact.getText();
String gender = Txt_Gender.getText();
if (name.isEmpty() || Txt_id_Document.getText().isEmpty() || document_type.isEmpty() || document.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
JOptionPane.showMessageDialog(this, "Falta ingresar un campo del Dueño");
}else{
String query = "UPDATE tb_pet_owners SET id_document = " + id_document + ", owner= '" + name + "',document_type= '" + document_type + "' ,document = '" + document + 
        "',contact= ' " + contact + "', gender = '" + gender + 
        "' WHERE id = " + id;
//UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
try{
    cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "El Dueño ha sido modificado con éxito");
clear_rows_tb_Owners();
show_Owners();
}catch(HeadlessException | SQLException e){
JOptionPane.showMessageDialog(this, "No se pudo modificar el Dueño");
    System.out.println(">>>>> " + e);
}
}
}

 void delete_Owner(){
int fila = tbl_Owners.getSelectedRow();
int id = Integer.parseInt(txt_id.getText());


if (fila == -1 ) {
JOptionPane.showMessageDialog(this, "No has seleccionado un dueño");
}else{
//System.out.println("ID: " + id);
String query = "DELETE FROM tb_pet_owners WHERE id = " + id;
try{
cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "El Dueño ha sido eliminado con exito");
clear_rows_tb_Owners();
    show_Owners();
}catch(HeadlessException | SQLException e){
    System.out.println(" No se pudo Eliminar, error:  >>> " + e );
}
}
}
 /**>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  * Pets/
  * 
  */
 void show_Pets(){
String sql = "SELECT * FROM tb_pet";
try{
cn = con.getConnection();
st = cn.createStatement();
rs = st.executeQuery(sql);
//Los datos que devuelve la consulta se muestran en la tabla
Object[]pet = new Object[6];
modeloA = (DefaultTableModel)tbl_Pet.getModel();
while(rs.next()){
 //contador++;
    int id_pet=rs.getInt("id");
    pet[0] = id_pet;
    pet[1] = rs.getString("name");
    pet[2] = rs.getString("breed");
    int idowner=rs.getInt("id_owner_pet");
    //System.out.println(" rs.next()  >>>>>>>>>>> ");
    String pet_owner=search_pet_owner(idowner);
    pet[3] = pet_owner;
    pet[4] = idowner;
    String name_Hospital= get_Hospital_name(id_pet);
    pet[5] = name_Hospital;

modeloA.addRow(pet);
//System.out.println(rs.getInt("id"));
}
tbl_Pet.setModel(modeloA);
}catch(SQLException e){
    System.out.println(" error en table pet >>>>>>" + e);
}
}
    void add_Pets(){
String name = txt_Pet_Name.getText();
String breed = txt_Breed.getText();
int pet_owner_id = Integer.parseInt(txt_Owner_id.getText());

if (name.isEmpty() || breed.isEmpty() || txt_Owner_id.getText().isEmpty()) {
JOptionPane.showMessageDialog(this, "Falta ingresar algún campo de la mascota ");
}else{
String query = "INSERT INTO `tb_pet`(`name`,`breed`,id_owner_pet)" + " VALUES('" + name + "','" + breed + "'," + pet_owner_id + ")";
try{
cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "La mascota ha sido creada con éxito");
clear_rows_tb_Pets();
show_Pets();
}catch(HeadlessException | SQLException e){
JOptionPane.showMessageDialog(this, "No se pudo crear la mascota");
}
}
}
    void clear_rows_tb_Pets(){
for (int i = 0; i < tbl_Pet.getRowCount(); i++) {
modeloA.removeRow(i);
i = i-1;
}
txt_id_Pet.setText("");
txt_Pet_Name.setText("");
txt_Breed.setText("");
txt_Owner_id.setText("");
    }
    
    void edit_Pets(){
//Hacemos nuevamente lectura de los valores contenidos en los JTextField
//Para identificar si el usuario modifico algún valor
String name = txt_Pet_Name.getText();
int id_pet = Integer.parseInt(txt_id_Pet.getText());
String breed = txt_Breed.getText();
int pet_owner_id = Integer.parseInt(txt_Owner_id.getText());
if (name.isEmpty() || txt_id_Pet.getText().isEmpty() || breed.isEmpty() || txt_Owner_id.getText().isEmpty()) {
JOptionPane.showMessageDialog(this, "Falta ingresar un campo de la mascota");
}else{
String query = "UPDATE tb_pet SET name = '" + name + "', breed = '" + breed + "',id_owner_pet = " + pet_owner_id + 
        " WHERE id = " + id_pet;
//UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
try{
    cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
    update_Hospital_pet(id_pet);
JOptionPane.showMessageDialog(this, "la mascota ha sido modificada con éxito");
clear_rows_tb_Pets();
show_Pets();
}catch(HeadlessException | SQLException e){
JOptionPane.showMessageDialog(this, "No se pudo modificar la mascota");
    System.out.println(">>>>> " + e);
}
}
}

 void delete_Pets(){
int fila = tbl_Pet.getSelectedRow();
int id = Integer.parseInt(txt_id_Pet.getText());


if (fila == -1) {
JOptionPane.showMessageDialog(this, "No has seleccionado una mascota");
}else{
System.out.println("ID: " + id);
String query = "DELETE FROM tb_pet WHERE id = " + id;
try{
cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "La mascota ha sido eliminada con exito");
clear_rows_tb_Pets();
    show_Pets();
    this.combo_1.setEnabled(false);
}catch(HeadlessException | SQLException e){
    System.out.println(" No se pudo Eliminar, error:  >>> " + e );
}
}
}
 
 String search_pet_owner(int id){
     String nombresito="";
     try{
       String sql = "SELECT * from tb_pet_owners WHERE id = " + id;
    cn = con.getConnection();
    st = cn.createStatement();
    ResultSet res = st.executeQuery(sql);
    while(res.next()){
    nombresito=res.getString("owner");
    
     }
     }catch(HeadlessException | SQLException e){
         System.out.println("error en nombre dueño mascota >>>>>> "+ e);
     }
     return nombresito;
    }
    
 int get_id_Hospital(int id_pet){
    int respuesta=0;
    
    String sql = "SELECT * FROM tb_pet_hospital WHERE id_pet= " + id_pet;
    try{
    cn = con.getConnection();
    st = cn.createStatement();
    ResultSet res = st.executeQuery(sql);
    while(res.next()){
        respuesta= res.getInt("id_hospital");
    }
    }catch(HeadlessException | SQLException e){
      System.out.println("errror en id hospital >>>>>> "+ e);
     }
    return respuesta;
 }
 
    String get_Hospital_name(int id_pet){
        String respuesta="";
        int id_Hospital=get_id_Hospital(id_pet);
        String sql = "SELECT * FROM tb_hospital WHERE id= " + id_Hospital;
        try{
    cn = con.getConnection();
    st = cn.createStatement();
    ResultSet res = st.executeQuery(sql);
    while(res.next()){
        respuesta= res.getString("name");
    }
    }catch(HeadlessException | SQLException e){
      System.out.println("errror en name hospital >>>>>> "+ e);
     }
        return respuesta;
    }
    
    void box (){
        
        this.combo_1.removeAllItems();
        this.combo_1.addItem("San Miguel");
        this.combo_1.addItem("Mascoticas");
        txt_id.setEnabled(false);
        txt_id_Pet.setEnabled(false);
        this.combo_1.setEnabled(false);
    }
    void update_Hospital_pet(int id_pet){
        String sql = "UPDATE tb_pet_hospital SET id_hospital = " + (this.combo_1.getSelectedIndex()+1)+  " WHERE id_pet = " + id_pet;
        try{
    cn = con.getConnection();
    st = cn.createStatement();
    st.executeUpdate(sql);
    }catch(HeadlessException | SQLException e){
      System.out.println("errror en actualizar el hospital >>>>>> "+ e);
     }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btn_Add_Owner = new javax.swing.JButton();
        btn_Update_Owner = new javax.swing.JButton();
        btn_Delete_Owner = new javax.swing.JButton();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Owners = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Txt_Owner = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        Txt_id_Document = new javax.swing.JTextField();
        Txt_Document_Type = new javax.swing.JTextField();
        Txt_Document = new javax.swing.JTextField();
        Txt_Contact = new javax.swing.JTextField();
        Txt_Gender = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        scrollPane2 = new java.awt.ScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Pet = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txt_id_Pet = new javax.swing.JTextField();
        txt_Pet_Name = new javax.swing.JTextField();
        txt_Breed = new javax.swing.JTextField();
        txt_Owner_id = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        combo_1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_Add_Owner.setText("Añadir Dueño");
        btn_Add_Owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Add_OwnerActionPerformed(evt);
            }
        });

        btn_Update_Owner.setText("Cambiar Información");
        btn_Update_Owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Update_OwnerActionPerformed(evt);
            }
        });

        btn_Delete_Owner.setText("Eliminar Dueño");
        btn_Delete_Owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Delete_OwnerActionPerformed(evt);
            }
        });

        tbl_Owners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Dueño", "Id_Documento", "Tipo Documento", "Num_Documento", "Contacto", "Género"
            }
        ));
        tbl_Owners.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_OwnersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Owners);
        if (tbl_Owners.getColumnModel().getColumnCount() > 0) {
            tbl_Owners.getColumnModel().getColumn(1).setResizable(false);
        }

        scrollPane1.add(jScrollPane1);

        jLabel1.setText("DUEÑOS DE LAS MASCOTAS");

        Txt_Owner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Txt_OwnerActionPerformed(evt);
            }
        });

        jLabel2.setText("Dueño");

        jLabel3.setText("Código");

        jLabel4.setText("Id_Documento");

        jLabel5.setText("Tipo Documento");

        jLabel6.setText("Num_Documento");

        jLabel7.setText("Contacto");

        jLabel8.setText("Género");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 230, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_Add_Owner)
                                .addGap(102, 102, 102)
                                .addComponent(btn_Update_Owner)
                                .addGap(105, 105, 105)
                                .addComponent(btn_Delete_Owner)
                                .addGap(208, 208, 208))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2))
                                .addGap(137, 137, 137)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Txt_Owner, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_id, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                        .addComponent(Txt_id_Document)
                                        .addComponent(Txt_Document_Type)
                                        .addComponent(Txt_Document)
                                        .addComponent(Txt_Contact)
                                        .addComponent(Txt_Gender)))
                                .addGap(237, 237, 237))))
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Txt_Owner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(Txt_id_Document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txt_Document_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txt_Document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Txt_Contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Txt_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Add_Owner)
                            .addComponent(btn_Update_Owner)
                            .addComponent(btn_Delete_Owner))
                        .addGap(99, 99, 99)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dueños", jPanel2);

        tbl_Pet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Raza", "Dueño", "Codigo_Dueño", "Hospital"
            }
        ));
        tbl_Pet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PetMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_Pet);

        scrollPane2.add(jScrollPane2);

        jLabel9.setText("MASCOTAS");

        jLabel10.setText("Código");

        jLabel11.setText("Nombre");

        jLabel12.setText("Raza");

        jLabel13.setText("Codigo_Dueño");

        jToggleButton1.setText("Añadir Mascota");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Editar Mascota");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton3.setText("Eliminar Mascota");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jLabel14.setText("Hospital");

        combo_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Añadir Hospital");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(465, 465, 465)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(155, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(267, 267, 267)
                        .addComponent(combo_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Owner_id, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Breed, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Pet_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(208, 208, 208)
                            .addComponent(txt_id_Pet, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jToggleButton2)
                        .addGap(70, 70, 70)
                        .addComponent(jToggleButton3)
                        .addGap(85, 85, 85)
                        .addComponent(jButton1)))
                .addGap(179, 179, 179))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_id_Pet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Pet_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(33, 33, 33)
                        .addComponent(txt_Breed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Owner_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(combo_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2)
                    .addComponent(jToggleButton3)
                    .addComponent(jButton1))
                .addGap(90, 90, 90)
                .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Mascotas", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Delete_OwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Delete_OwnerActionPerformed
        // TODO add your handling code here:
        delete_Owner();
    }//GEN-LAST:event_btn_Delete_OwnerActionPerformed

    private void Txt_OwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Txt_OwnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Txt_OwnerActionPerformed

    private void btn_Add_OwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Add_OwnerActionPerformed
        // TODO add your handling code here:
        add_Owner();
    }//GEN-LAST:event_btn_Add_OwnerActionPerformed

    private void btn_Update_OwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Update_OwnerActionPerformed
        // TODO add your handling code here:
        edit_Owner();
    }//GEN-LAST:event_btn_Update_OwnerActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        add_Pets();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        edit_Pets();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
        delete_Pets();
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void tbl_PetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PetMouseClicked
       int row = tbl_Pet.getSelectedRow();
System.out.println(row);
if (row < 0) {
JOptionPane.showMessageDialog(this, "Debes seleccionar un departamento");
} else {
int id = Integer.parseInt((String) tbl_Pet.getValueAt(row, 0).toString());
String raza = (String) tbl_Pet.getValueAt(row, 2).toString();
int dueño = Integer.parseInt((String) tbl_Pet.getValueAt(row,4).toString());
String name = (String) tbl_Pet.getValueAt(row, 1);
//System.out.println(id + " - " + name + " - " + raza+ " - " +dueño);
txt_id_Pet.setText("" + id);
txt_Breed.setText(raza);
txt_Owner_id.setText("" + dueño);
txt_Pet_Name.setText(name);
this.combo_1.setEnabled(true);
}
    }//GEN-LAST:event_tbl_PetMouseClicked

    private void tbl_OwnersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_OwnersMouseClicked
        // TODO add your handling code here:
        int row = tbl_Owners.getSelectedRow();
System.out.println(row);
if (row < 0) {
JOptionPane.showMessageDialog(this, "Debes seleccionar un departamento");
} else {
int id = Integer.parseInt((String) tbl_Owners.getValueAt(row, 0).toString());
int id_document = Integer.parseInt((String) tbl_Owners.getValueAt(row, 2).toString());
String documento = (String) tbl_Owners.getValueAt(row,4).toString();
String tip_doc= (String) tbl_Owners.getValueAt(row, 3);
String name = (String) tbl_Owners.getValueAt(row, 1);
String contacto = (String) tbl_Owners.getValueAt(row, 5);
String genero = (String) tbl_Owners.getValueAt(row, 6);

//System.out.println(id + " - " + name + " - " + document+ " - " +department);
txt_id.setText("" + id);
Txt_id_Document.setText("" + id_document);
Txt_Document.setText("" + documento);
Txt_Owner.setText(name);
Txt_Document_Type.setText(tip_doc);
Txt_Contact.setText(contacto);
Txt_Gender.setText(genero);
}
    }//GEN-LAST:event_tbl_OwnersMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int fila = tbl_Pet.getSelectedRow();
boolean bandera=true;
    int id = Integer.parseInt(txt_id_Pet.getText());

if (fila == -1 || bandera== false ) {
JOptionPane.showMessageDialog(this, "No has seleccionado una mascota para añadirle el hospital");
}else{
System.out.println("ID: " + id);
String query = "INSERT INTO tb_pet_hospital (id_pet,id_hospital) VALUES (" + id+" , " + (this.combo_1.getSelectedIndex()+1) + ");";
try{
cn = con.getConnection();
st = cn.createStatement();
st.executeUpdate(query);
JOptionPane.showMessageDialog(this, "La mascota ya tiene un hospital :) ");
clear_rows_tb_Pets();
    show_Pets();
    this.combo_1.setEnabled(false);
}catch(HeadlessException | SQLException e){
    System.out.println(" No se pudo agregar el hospital , error:  >>> " + e );
}
}
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Txt_Contact;
    private javax.swing.JTextField Txt_Document;
    private javax.swing.JTextField Txt_Document_Type;
    private javax.swing.JTextField Txt_Gender;
    private javax.swing.JTextField Txt_Owner;
    private javax.swing.JTextField Txt_id_Document;
    private javax.swing.JButton btn_Add_Owner;
    private javax.swing.JButton btn_Delete_Owner;
    private javax.swing.JButton btn_Update_Owner;
    private javax.swing.JComboBox<String> combo_1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private java.awt.ScrollPane scrollPane1;
    private java.awt.ScrollPane scrollPane2;
    private javax.swing.JTable tbl_Owners;
    private javax.swing.JTable tbl_Pet;
    private javax.swing.JTextField txt_Breed;
    private javax.swing.JTextField txt_Owner_id;
    private javax.swing.JTextField txt_Pet_Name;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_id_Pet;
    // End of variables declaration//GEN-END:variables
}
