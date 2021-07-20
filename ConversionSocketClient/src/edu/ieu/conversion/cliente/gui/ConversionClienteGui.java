package edu.ieu.conversion.cliente.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextPane;

import edu.ieu.conversion.cliente.socket.ConversionCliente;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ConversionClienteGui {

	private JFrame frame;
	private JButton btnConvertir;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JTextField textField;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConversionClienteGui window = new ConversionClienteGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConversionClienteGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_central = new JPanel();
		panel_central.setBackground(Color.WHITE);
		panel_central.setBorder(new LineBorder(new Color(0, 0, 0), 7));
		frame.getContentPane().add(panel_central, BorderLayout.CENTER);
		panel_central.setLayout(new GridLayout(3, 5, 5, 5));
		
		JLabel lblNewLabel = new JLabel("             Moneda origen");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel_central.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(Color.BLACK);
		comboBox.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		comboBox.addItem("USD");
		comboBox.addItem("EUR");
		comboBox.addItem("HKD");
		panel_central.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("            Moneda Destino");		
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel_central.add(lblNewLabel_1);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setForeground(Color.WHITE);
		comboBox_1.setBackground(Color.BLACK);
		comboBox_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		comboBox_1.addItem("USD");
		comboBox_1.addItem("EUR");
		comboBox_1.addItem("HKD");
		panel_central.add(comboBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("              Cantidad");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel_central.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		textField.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panel_central.add(textField);
		textField.setColumns(10);
		
		JPanel panel_derecha = new JPanel();
		panel_derecha.setBorder(new LineBorder(Color.WHITE, 7));
		frame.getContentPane().add(panel_derecha, BorderLayout.EAST);
		
		btnConvertir = new JButton("Convertir");
		btnConvertir.setBackground(Color.WHITE);
		btnConvertir.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String monedaOrigen = (String) comboBox.getSelectedItem();
				String monedaDestino = (String) comboBox_1.getSelectedItem();
				String cantidad = textField.getText();
				enviarDatosServidor(monedaOrigen, monedaDestino, cantidad);				
			}
		});
		panel_derecha.setLayout(new GridLayout(0, 1, 0, 0));
		panel_derecha.add(btnConvertir);
		
		JButton btSalir = new JButton("Salir");
		btSalir.setForeground(Color.WHITE);
		btSalir.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btSalir.setBackground(Color.BLACK);
		panel_derecha.add(btSalir);
		
		textPane = new JTextPane();
		textPane.setBackground(Color.GRAY);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(textPane, BorderLayout.SOUTH);
	}

	public void enviarDatosServidor(String origen,String destino,String cantidad) {
		try {
			double cantidadDouble = Double.parseDouble(cantidad);
			FutureTask<String> task = 
					new FutureTask (
							new ConversionCliente(origen, destino, cantidadDouble)
					);
			ExecutorService es = Executors.newSingleThreadExecutor ();
			es.submit(task);
			while(true) {
				if(task.isDone()) {
					String valor= task.get();						
					textPane.setText( valor );
					break;
				}
			}
		}catch(Exception ex) {
			mostrarDialogoError(ex.getMessage());
			ex.printStackTrace();
			return;
		}		
	}
	
	public void mostrarDialogoError(String msg) {
		 // create a dialog Box
        JDialog d = new JDialog(frame, "Error");
        // create a label
        JLabel l = new JLabel("Error: " + msg);
        d.getContentPane().add(l);
        // setsize of dialog
        d.setSize(100, 100);
        // set visibility of dialog
        d.setVisible(true);
	}

}
