package edu.ieu.conversion.gui;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import edu.ieu.conversion.socket.server.ConversionServer;


import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ServidorGUI {

	private ConversionServer conversionServer = new ConversionServer(6000);
	private JFrame frmServidorConversion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServidorGUI window = new ServidorGUI();
					window.frmServidorConversion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServidorGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServidorConversion = new JFrame();
		frmServidorConversion.setTitle("Servidor Conversion");
		frmServidorConversion.setBounds(100, 100, 520, 167);
		frmServidorConversion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane textPaneSalida = new JTextPane();
		textPaneSalida.setForeground(Color.WHITE);
		textPaneSalida.setBackground(Color.BLACK);
		textPaneSalida.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		frmServidorConversion.getContentPane().add(textPaneSalida, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frmServidorConversion.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.setForeground(Color.WHITE);
		btnIniciarServidor.setBackground(Color.BLACK);
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Servicio inicializado...");
				textPaneSalida.setText( textPaneSalida.getText() + "\n Servicio inicializado... \n");
				conversionServer.start();								
			}
		});
		btnIniciarServidor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel.add(btnIniciarServidor);
		
		JButton btnDetenerServidor = new JButton("Detener Servidor");
		btnDetenerServidor.setBackground(Color.BLACK);
		btnDetenerServidor.setForeground(Color.WHITE);
		btnDetenerServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {							
				conversionServer.stopServer();;
				textPaneSalida.setText( textPaneSalida.getText() + "\n Servicio Detenido... \n");
				System.out.println("Servicio Detenido...");
			}
		});
		btnDetenerServidor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel.add(btnDetenerServidor);
		
		JButton btnSalirApp = new JButton("Salir de la App");
		btnSalirApp.setForeground(Color.WHITE);
		btnSalirApp.setBackground(Color.BLACK);
		btnSalirApp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel.add(btnSalirApp);
	}

}
