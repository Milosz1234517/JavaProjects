import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public Double[] a;
	public boolean order;
	public boolean dataSeted = false;

	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 91, 477, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Reverse Sort");
		chckbxNewCheckBox.setBounds(10, 154, 97, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JButton btnNewButton = new JButton("Set Data");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data = textField.getText().split(",");
				a = new Double[data.length];
				for(int i = 0; i < data.length; i++){
					a[i] = Double.valueOf(data[i]);
				}

				if(chckbxNewCheckBox.isSelected()){
					order = false;
				}else{
					order = true;
				}
				dataSeted = true;
			}
		});
		btnNewButton.setBounds(195, 206, 89, 23);
		contentPane.add(btnNewButton);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
}
