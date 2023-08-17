package interfaceGrafica;

import java.awt.EventQueue;
import classes.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;

//import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class AplicacaoAprendizagem extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private DataSet data;
	private Classifier C;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacaoAprendizagem frame = new AplicacaoAprendizagem();
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
	public AplicacaoAprendizagem() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(17, 6, 416, 196);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(17, 6, 416, 196);
		contentPane.add(scrollPane);
		
		JFileChooser fileChooser = new JFileChooser();
		
		JButton btnfilechoose = new JButton("Choose File");
		btnfilechoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fileChooser.showOpenDialog(btnfilechoose);
				//dataSet que vamos usar para aprendizagem
				data = new DataSet(fileChooser.getSelectedFile().getAbsolutePath());
				//realizar todas as ações necessárias para criar classificador
				
				int numClasses = data.numberOfClasses();
				
				DataSet[] fibers = new DataSet[numClasses];
				MRFT[] mrfts = new MRFT[numClasses];
				int[] freqs = new int[numClasses];
				
				
				for (int c = 0; c < numClasses; c++) {
					fibers[c] = data.Fiber(c);
					mrfts[c] = new MRFT(fibers[c]);
					freqs[c] = fibers[c].getM();
					}
				
				C = new Classifier(mrfts, freqs);
				
				textArea.setText("File read successfully \n" + data.toString()); //ou em inglês
				
			}
		});
		btnfilechoose.setBounds(68, 214, 117, 29);
		contentPane.add(btnfilechoose);
		
		JFileChooser fileChooser2 = new JFileChooser();
		fileChooser2.setDialogTitle("Specify a file name and directory");  
		
	
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser2.showSaveDialog(btnSave);
				String	path = fileChooser2.getSelectedFile().getAbsolutePath();
				FileOutputStream fileOut;
				try {
					fileOut = new FileOutputStream(path);
					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
					objectOut.writeObject(C);
					objectOut.close();
					textArea.setText("File saved at " + path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(283, 214, 117, 29);
		contentPane.add(btnSave);
		
		
	}
}
