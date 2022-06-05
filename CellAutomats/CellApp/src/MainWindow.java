import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(50, 87, 132, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Size X:");
		lblNewLabel.setBounds(50, 62, 132, 14);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(222, 87, 132, 20);
		contentPane.add(textField_1);
		
		JLabel lblSizeY = new JLabel("Size Y:");
		lblSizeY.setBounds(222, 62, 132, 14);
		contentPane.add(lblSizeY);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(50, 170, 132, 20);
		contentPane.add(textField_2);
		
		JLabel lblCount = new JLabel("Square Size");
		lblCount.setBounds(50, 145, 132, 14);
		contentPane.add(lblCount);

		JSlider slider = new JSlider();
		slider.setMinimum(0);
		slider.setMaximum(999);
		slider.setBounds(192, 184, 200, 26);
		contentPane.add(slider);

		JLabel lblSpeed = new JLabel("Speed");
		lblSpeed.setBounds(222, 145, 132, 14);
		contentPane.add(lblSpeed);

		JButton btnLoadScript = new JButton("Start Ants Script");
		btnLoadScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new DisplayWindow(Integer.parseInt(
							textField.getText()),
							Integer.parseInt(textField_1.getText()),
							slider.getValue()+1,
							Integer.parseInt(textField_2.getText()),
							true
					);
				} catch (FileNotFoundException | ScriptException | NoSuchMethodException ex) {
					ex.printStackTrace();
				}

			}
		});
		btnLoadScript.setBounds(222, 262, 132, 23);
		contentPane.add(btnLoadScript);

		JButton btnLoadGolScript = new JButton("Start GOL Script");
		btnLoadGolScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new DisplayWindow(Integer.parseInt(
							textField.getText()),
							Integer.parseInt(textField_1.getText()),
							slider.getValue()+1,
							Integer.parseInt(textField_2.getText()),
							false
					);
				} catch (FileNotFoundException | ScriptException | NoSuchMethodException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnLoadGolScript.setBounds(50, 262, 132, 23);
		contentPane.add(btnLoadGolScript);

	}




	public List<Integer> getDataFromCSVFile(String pth) {

		String A = pth;
		List coordinates=new ArrayList<Integer>();
		boolean first;
		try {
			BufferedReader br = new BufferedReader(new FileReader(A));
			String fileRead = br.readLine();
			while (fileRead != null) {
				String[] tokenize = fileRead.split(",");
				for (int i = 0; i < tokenize.length; i++) {
					coordinates.add(tokenize[i]);

					/*

					pierwsze 3liczby to kwadraty x,  kwadraty y i wielkość kwadratu
					potem współrzędne



					 */




				}
				fileRead = br.readLine();
			}

			br.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("file not found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return coordinates;
	}

}
