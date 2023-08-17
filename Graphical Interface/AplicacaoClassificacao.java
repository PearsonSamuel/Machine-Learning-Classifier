package interfaceGrafica;

import java.awt.EventQueue;
import classes.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import classes.Classifier;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class AplicacaoClassificacao extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private Classifier C;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacaoClassificacao frame = new AplicacaoClassificacao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AplicacaoClassificacao() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 19, 405, 40);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(22, 19, 405, 40);
		contentPane.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(22, 127, 405, 111);
		contentPane.add(textArea_1);
		
		JScrollPane scrollPane2 = new JScrollPane(textArea_1);
		scrollPane2.setBounds(22, 127, 405, 111);
		contentPane.add(scrollPane2);
		
		JFileChooser fileChooser = new JFileChooser();
		
		JButton btnReadFile = new JButton("Read File");
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(btnReadFile);
				String	path = fileChooser.getSelectedFile().getAbsolutePath();
				
				FileInputStream fileIn;
				try {
					fileIn = new FileInputStream(path);
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					C = (Classifier) objectIn.readObject();
					objectIn.close();
					textArea_1.setText("File read\n" + C);
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReadFile.setBounds(61, 71, 117, 29);
		contentPane.add(btnReadFile);
		
		JButton btnClassify = new JButton("Classify");
		btnClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] vec = DataSet.convert(textArea.getText());
				textArea_1.setText("" + C.strClassify(vec));
			};
		});
		btnClassify.setBounds(284, 71, 117, 29);
		contentPane.add(btnClassify);
		
		
	}
}
