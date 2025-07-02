/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package penjualan;

import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class frmTransaksi extends javax.swing.JFrame {
    Connection Con;
    ResultSet RsBrg;
    ResultSet RsKons;
    Statement stm;
    PreparedStatement pstmt;
    double total=0;
    String tanggal;
    Boolean edit=false;
    DefaultTableModel tableModel = new DefaultTableModel(
        new Object [][] {},
        new String [] {
        "Kd Barang", "Nama Barang","Harga Barang","Jumlah", "Diskon", "Total"
    });
		//Var Pencarian Kode Barang
    String idBrg;
    String namaBrg;
    String hargaBrg;
        
        
    public frmTransaksi() {
      initComponents();
      open_db();
      inisialisasi_tabel();
      aktif(false);
      setTombol(true);
      txtTgl.setEditor(new JSpinner.DateEditor(txtTgl,"yyyy/MM/dd"));         
	  }

    //method hitung penjualan
// MODIFIED: Method to calculate discount and subtotal
private void hitung_jual() {
    double xtot, xhrg, xdiskon;
    int xjml;

    try {
        xhrg = Double.parseDouble(txtHarga.getText());
        xjml = Integer.parseInt(txtJml.getText());

        double diskonPersen = 0;
        if (xjml > 50) {
            diskonPersen = 0.05; // 5% discount
        } else if (xjml > 20) {
            diskonPersen = 0.03; // 3% discount
        } else if (xjml > 10) {
            diskonPersen = 0.02; // 2% discount
        }

        // Calculate the discount amount and the final total for the item
        xdiskon = (xhrg * xjml) * diskonPersen;
        xtot = (xhrg * xjml) - xdiskon;

        // Display the calculated total for the current item
        txtTot.setText(Double.toString(xtot));

    } catch (NumberFormatException e) {
        // Handle cases where text fields are empty or contain invalid numbers
        txtTot.setText("0");
    }
}

//methohd baca data konsumen
private void baca_konsumen()
{
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    try {
        stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        RsKons = stm.executeQuery("SELECT kd_kons FROM konsumen");

        while (RsKons.next()) {
            String kodeKonsumen = RsKons.getString("kd_kons");
            model.addElement(kodeKonsumen);
        }

	RsKons.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    cmbKd_Kons.setModel(model);
}

private void baca_barang() {
DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

try {
    stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    RsBrg = stm.executeQuery("SELECT kd_brg FROM barang");

    while (RsBrg.next()) {
        String kodeBarang = RsBrg.getString("kd_brg");
        model.addElement(kodeBarang);
    }

    RsBrg.close();
} catch (SQLException e) {
    e.printStackTrace();
}

cmbKd_Brg.setModel(model);
}

//method baca barang setelah combo barang di klik
private void detail_barang(String xkode) {            
    String sql = "SELECT * FROM barang WHERE kd_brg = ?";

    try {
        pstmt = Con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstmt.setString(1, xkode);
        RsBrg = pstmt.executeQuery();

        if (RsBrg.next()) {
            String namaBrg = RsBrg.getString("nm_brg");
            int hargaBrg = RsBrg.getInt("harga");

            txtNm_Brg.setText(namaBrg);
            txtHarga.setText(Integer.toString(hargaBrg));
        } else {
            txtNm_Brg.setText("");
            txtHarga.setText("");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    } finally {
        try {
            if (RsBrg != null) RsBrg.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}

//method baca konsumen setelah combo konsumen di klik
private void detail_konsumen(String xkode) {
    String sql = "SELECT * FROM konsumen WHERE kd_kons = ?";

    try {
        pstmt = Con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstmt.setString(1, xkode);
        RsKons = pstmt.executeQuery();

        if (RsKons.next()) {
            String namaKons = RsKons.getString("nm_kons");
            txtNama.setText(namaKons);
        } else {
            txtNama.setText("");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    } finally {
        try {
            if (RsKons != null) RsKons.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}

//method set model tabel
public void inisialisasi_tabel()
{
    tblJual.setModel(tableModel);
}

//method pengkosongan isian
private void kosong()
{
    txtNoJual.setText("");
    txtNama.setText("");
    txtHarga.setText("");
    txtTotal.setText("");
}

//method kosongkan detail jual
private void kosong_detail()
{
    txtNm_Brg.setText("");
    txtHarga.setText("");
    txtJml.setText("");
    txtTot.setText("");
}


private void aktif(boolean x)
{
    txtNoJual.setEnabled(x);
    txtNoJual.setEditable(false);
    
    txtNama.setEnabled(x);
    txtNama.setEditable(false);
    
    txtNm_Brg.setEnabled(x);
    txtNm_Brg.setEditable(false);
    
    txtHarga.setEnabled(x);
    txtHarga.setEditable(false);
    
    txtJml.setEnabled(x);
    txtTot.setEnabled(x);
    txtTot.setEditable(false);
    
    txtTotal.setEnabled(x);
    txtTotal.setEditable(false);
    
    txtBayar.setEnabled(x);
    
    txtKembali.setEnabled(x);
    txtKembali.setEditable(false);
    
    txtTot.setEnabled(x);
    txtTotal.setEnabled(x);
    txtId.setEnabled(x);
    
    cmbKd_Kons.setEnabled(x);
    cmbKd_Brg.setEnabled(x);
    txtTgl.setEnabled(x);
    txtJml.setEditable(x);
}

//method set tombol on/off
private void setTombol(boolean t)
{
    cmdTambah.setEnabled(t);
    cmdSimpan.setEnabled(!t);
    cmdBatal.setEnabled(!t);
    cmdKeluar.setEnabled(t);
    cmdHapusItem.setEnabled(!t);
    btnPilih.setEnabled(!t);
    
}

//method buat nomor jual otomatis
private void nomor_jual()
{
    try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select no_jual from jual");
        int brs=0;

        while(rs.next())
        {
            brs=rs.getRow();
        }
        if(brs==0)
            txtNoJual.setText("1");
        else
        {int nom=brs+1;
            txtNoJual.setText(Integer.toString(nom));
        }
        rs.close();
    }
    catch(SQLException e)
    {
        System.out.println("Error : "+e);
    }
}

//method simpan detail jual di tabel (temporary)
// MODIFIED: Method to save item details including discount to the table
private void simpan_ditabel() {
    try {
        String tKode = cmbKd_Brg.getSelectedItem().toString();
        String tNama = txtNm_Brg.getText();
        double hrg = Double.parseDouble(txtHarga.getText());
        int jml = Integer.parseInt(txtJml.getText());

        double diskonPersen = 0;
        if (jml >= 50) {
            diskonPersen = 0.05;
        } else if (jml >= 20) {
            diskonPersen = 0.03;
        } else if (jml >= 10) {
            diskonPersen = 0.02;
        }

        double diskon = (hrg * jml) * diskonPersen;
        double tot = (hrg * jml) - diskon;

        // Add the new row with discount information
        tableModel.addRow(new Object[]{tKode, tNama, hrg, jml, diskon, tot});
        inisialisasi_tabel();
        recalculate_total(); // Update the grand total
        recalculate_diskon(); // <-- TAMBAHKAN BARIS INI
    } catch (Exception e) {
        System.out.println("Error : " + e);
    }
}

//method simpan transaksi penjualan pada table di MySql 
// MODIFIED: Method to save the transaction to the database
private void simpan_transaksi() {
    // Ensure you have added a 'diskon' column to your 'djual' table
    String sqlInsertJual = "INSERT INTO jual (no_jual, kd_kons, tgl_jual) VALUES (?, ?, ?)";
    String sqlInsertDjual = "INSERT INTO djual (no_jual, kd_brg, harga_jual, jml_jual, diskon) VALUES (?, ?, ?, ?, ?)";

    try {
        Con.setAutoCommit(false); // Start transaction

        String xnojual = txtNoJual.getText();
        format_tanggal();
        String xkode = cmbKd_Kons.getSelectedItem().toString();

        // Insert into 'jual' table
        pstmt = Con.prepareStatement(sqlInsertJual);
        pstmt.setString(1, xnojual);
        pstmt.setString(2, xkode);
        pstmt.setString(3, tanggal);
        pstmt.executeUpdate();

        // Insert into 'djual' table
        pstmt = Con.prepareStatement(sqlInsertDjual);
        for (int i = 0; i < tblJual.getRowCount(); i++) {
            String xkd = (String) tblJual.getValueAt(i, 0);
            double xhrg = (Double) tblJual.getValueAt(i, 2);
            int xjml = (Integer) tblJual.getValueAt(i, 3);
            double xdiskon = (Double) tblJual.getValueAt(i, 4); // Get discount from table

            pstmt.setString(1, xnojual);
            pstmt.setString(2, xkd);
            pstmt.setDouble(3, xhrg);
            pstmt.setInt(4, xjml);
            pstmt.setDouble(5, xdiskon); // Save the discount amount
            pstmt.addBatch();
        }
        pstmt.executeBatch();

        Con.commit(); // Commit transaction
        JOptionPane.showMessageDialog(null, "Data penjualan berhasil disimpan.");

    } catch (SQLException e) {
        try {
            if (Con != null) {
                Con.rollback(); // Rollback on error
            }
        } catch (SQLException ex) {
            System.out.println("Rollback failed: " + ex);
        }
        System.out.println("Error: " + e);
        JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());

    } finally {
        try {
            if (Con != null) {
                Con.setAutoCommit(true); // Restore auto-commit
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error closing resources: " + ex);
        }
    }
}

//method membuat format tanggal sesuai dengan MySQL
private void format_tanggal()
{
    String DATE_FORMAT = "yyyy-MM-dd";
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
    Calendar c1 = Calendar.getInstance();
    int year=c1.get(Calendar.YEAR);
    int month=c1.get(Calendar.MONTH)+1;
    int day=c1.get(Calendar.DAY_OF_MONTH);
    tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
}





// CETAK LAPORAN

private class PrintingTask extends SwingWorker<Object, Object> {
  private final MessageFormat headerFormat;
  private final MessageFormat footerFormat;
  private final boolean interactive;
  private volatile boolean complete = false;
  private volatile String message;

  public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
      this.headerFormat = header;
      this.footerFormat = footer;
      this.interactive = interactive;
  }

  @Override
  protected Object doInBackground() {
      try {
          complete = text.print(headerFormat, footerFormat,
          true, null, null, interactive);
          message = "Printing " + (complete ? "complete" : "canceled");
      } catch (PrinterException ex) {
          message = "Sorry, a printer error occurred";
      } catch (SecurityException ex) {
      message = "Sorry, cannot access the printer due to security reasons";
      }
      return null;
  }
  
  @Override
  protected void done() {
      showMessage(!complete, message);
  }
}

private void showMessage(boolean isError, String message) {
  if (isError) {
      JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  } else {
      JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }
}




// UPDATE
public void itemTerpilih(){
    frmSelectBarang fDB = new frmSelectBarang();
    fDB.fAB = this;              
    txtId.setText(idBrg);
    cmbKd_Brg.setSelectedItem(idBrg);
    txtNm_Brg.setText(namaBrg);
    txtHarga.setText(hargaBrg);
}

//Menghitung Kembalian
private void hitung_bayar(){
    double xtotal,xbayar, xkembali;

    xtotal=Double.parseDouble(txtTotal.getText());
    xbayar=Double.parseDouble(txtBayar.getText());        
    xkembali=xbayar-xtotal;
    String xkembalixx=Double.toString(xkembali);
    txtKembali.setText(xkembalixx);
}

//kosongi table penjualan
private void kosong_table(){
    DefaultTableModel model = (DefaultTableModel) tblJual.getModel();
    model.setRowCount(0); // Menghapus semua baris dalam tabel
}

// NEW: Method to recalculate the grand total
private void recalculate_total() {
    total = 0;
    for (int i = 0; i < tblJual.getRowCount(); i++) {
        // Sum up the 'Total' column (index 5)
        total += (Double) tblJual.getValueAt(i, 5);
    }
    txtTotal.setText(Double.toString(total));
}

// BARU: Metode untuk menghitung ulang total diskon dari tabel
private void recalculate_diskon() {
    double totalDiskon = 0;
    // Loop melalui setiap baris di tabel penjualan
    for (int i = 0; i < tblJual.getRowCount(); i++) {
        // Ambil nilai dari kolom 'Diskon' (indeks ke-4) dan tambahkan ke totalDiskon
        totalDiskon += (Double) tblJual.getValueAt(i, 4);
    }
    // Tampilkan total diskon di JTextField yang baru dibuat
    txtDiskon.setText(Double.toString(totalDiskon));
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNoJual = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtTgl = new javax.swing.JSpinner();
        cmbKd_Kons = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJual = new javax.swing.JTable();
        cmbKd_Brg = new javax.swing.JComboBox<>();
        txtNm_Brg = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtJml = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        cmdHapusItem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        btnPilih = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        txtBayar = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("No Jual");

        jLabel2.setText("Tgl. Jual");

        jLabel3.setText("Kode Konsumen");

        jLabel4.setText("Nama Konsumen");

        txtTgl.setModel(new javax.swing.SpinnerDateModel());

        cmbKd_Kons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKd_KonsActionPerformed(evt);
            }
        });

        tblJual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kd Barang", "Nama Barang", "Harga Barang", "Jumlah", "Diskon", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblJual);

        cmbKd_Brg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0001", "0002", "0003" }));
        cmbKd_Brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKd_BrgActionPerformed(evt);
            }
        });

        txtJml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlActionPerformed(evt);
            }
        });

        cmdHapusItem.setText("Hapus Item");
        cmdHapusItem.setEnabled(false);
        cmdHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusItemActionPerformed(evt);
            }
        });

        jLabel5.setText("Total");

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.setEnabled(false);
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.setEnabled(false);
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        btnPilih.setText("Pilih Barang");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        jLabel6.setText("Bayar");

        jLabel7.setText("Kembali");

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        jLabel8.setText("Diskon");

        txtDiskon.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdTambah)
                        .addGap(37, 37, 37)
                        .addComponent(cmdSimpan)
                        .addGap(46, 46, 46)
                        .addComponent(cmdBatal)
                        .addGap(27, 27, 27)
                        .addComponent(cmdCetak)
                        .addGap(18, 18, 18)
                        .addComponent(cmdKeluar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdHapusItem)
                            .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPilih)
                                    .addComponent(txtNoJual, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 469, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(54, 54, 54)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNama)
                            .addComponent(cmbKd_Kons, 0, 105, Short.MAX_VALUE))))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbKd_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdHapusItem)
                    .addComponent(btnPilih)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdTambah)
                    .addComponent(cmdSimpan)
                    .addComponent(cmdBatal)
                    .addComponent(cmdCetak)
                    .addComponent(cmdKeluar))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKd_KonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKd_KonsActionPerformed
      String kdKons = cmbKd_Kons.getSelectedItem().toString();
      detail_konsumen(kdKons);        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKd_KonsActionPerformed

    private void cmbKd_BrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKd_BrgActionPerformed
        String kdBrg = cmbKd_Brg.getSelectedItem().toString();
        detail_barang(kdBrg);        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKd_BrgActionPerformed

    private void txtJmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlActionPerformed
    hitung_jual();
    simpan_ditabel();
    cmbKd_Brg.requestFocus(); // Set focus back to the item code combo box
    }//GEN-LAST:event_txtJmlActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        aktif(true);
        setTombol(false);
        baca_barang();
        baca_konsumen();
        kosong();
        kosong_detail();
        kosong_table();
        nomor_jual();      
        txtDiskon.setText(""); // <-- TAMBAHKAN BARIS INI
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        simpan_transaksi();
        inisialisasi_tabel();        // TODO add your handling code here:
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
    aktif(false);
    setTombol(true);
    kosong();
    kosong_detail();
    kosong_table();
    text.setText(""); 
    txtDiskon.setText(""); // <-- TAMBAHKAN BARIS INI
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusItemActionPerformed
try {
        int row = tblJual.getSelectedRow();

        if (row != -1) {
            tableModel.removeRow(row);
            inisialisasi_tabel();
            recalculate_total(); // Recalculate total after removal
            recalculate_diskon(); // <-- TAMBAHKAN BARIS INI
        } else {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus");
        }
    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
    }//GEN-LAST:event_cmdHapusItemActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
// System.exit(0);
    dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
format_tanggal();
    String ctk = "Nota Penjualan\nNo:" + txtNoJual.getText() + "\nTanggal : " + tanggal;
    ctk += "\n" + "-----------------------------------------------------------------------------------------------------------------------";
    ctk += "\n" + "Kode\tNama Barang\tHarga\tJml\tDiskon\tTotal";
    ctk += "\n" + "-----------------------------------------------------------------------------------------------------------------------";

    for (int i = 0; i < tblJual.getRowCount(); i++) {
        String xkd = (String) tblJual.getValueAt(i, 0);
        String xnama = (String) tblJual.getValueAt(i, 1);
        double xhrg = (Double) tblJual.getValueAt(i, 2);
        int xjml = (Integer) tblJual.getValueAt(i, 3);
        double xdiskon = (Double) tblJual.getValueAt(i, 4); // Get discount
        double xtot = (Double) tblJual.getValueAt(i, 5);    // Get subtotal
        ctk += "\n" + xkd + "\t" + xnama + "\t" + xhrg + "\t" + xjml + "\t" + xdiskon + "\t" + xtot;
    }

    ctk += "\n" + "-----------------------------------------------------------------------------------------------------------------------";
    ctk += "\n\t\t\t\tTotal: \t" + txtTotal.getText();
    ctk += "\n\t\t\t\tBayar: \t" + txtBayar.getText();
    ctk += "\n\t\t\t\tKembali: \t" + txtKembali.getText();
    text.setText(ctk);

      String headerField="";
      String footerField="";
      MessageFormat header = new MessageFormat(headerField);
      MessageFormat footer = new MessageFormat(footerField);
      boolean interactive = true;//interactiveCheck.isSelected();
      boolean background = true;//backgroundCheck.isSelected();
      PrintingTask task = new PrintingTask(header, footer, interactive);

      if (background) {
          task.execute();
      } else {
          task.run();
      }        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCetakActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
    frmSelectBarang fDB = new frmSelectBarang();
    fDB.fAB = this;
    fDB.setVisible(true);
    fDB.setResizable(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btnPilihActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
    hitung_bayar();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBayarActionPerformed

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
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPilih;
    private javax.swing.JComboBox<String> cmbKd_Brg;
    private javax.swing.JComboBox<String> cmbKd_Kons;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdHapusItem;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblJual;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtJml;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNm_Brg;
    private javax.swing.JTextField txtNoJual;
    private javax.swing.JSpinner txtTgl;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private void open_db() {
    try{
        KoneksiMysql kon = new KoneksiMysql("localhost","root","","pbo");
        Con = kon.getConnection();
    }
    catch (Exception e) {
        System.out.println("Error : "+e);
    }
} 






}
