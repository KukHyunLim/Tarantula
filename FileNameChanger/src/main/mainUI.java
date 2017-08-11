package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class mainUI {

	private JFrame frame;
	private FileChooser chooser;
	
	String rootPath= "";
	Object[][] filelist;
	File[] fileList;
	
	private JTable table;
	private DefaultTableModel model;
	ButtonGroup group = new ButtonGroup();
	int selected_rbtn_index = -1;

	public static void main(String[] args) {
		try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		} 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mainUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		
		JButton btn_fileDialog = new JButton("\uBAA9\uB85D \uC5F4\uAE30");
		northPanel.add(btn_fileDialog);
		btn_fileDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click_btn_fileDialog();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		model = new DefaultTableModel(new Object[]{"변경전", "변경후"},0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JPanel southPanel = new JPanel();
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JButton btn_save = new JButton("\uC800\uC7A5");
		southPanel.add(btn_save);
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click_btn_save();
			}
		});
		
		JPanel optionPanel = new JPanel();
		frame.getContentPane().add(optionPanel, BorderLayout.EAST);
		optionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel apply_panel = new JPanel();
		optionPanel.add(apply_panel, BorderLayout.SOUTH);
		
		JButton apply_btn = new JButton("\uC801\uC6A9");
		apply_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click_btn_accept();
			}
		});
		apply_panel.add(apply_btn);
		
		JPanel option_Main = new JPanel();
		optionPanel.add(option_Main);
		option_Main.setLayout(new BoxLayout(option_Main, BoxLayout.Y_AXIS));
		{
			JRadioButton extantion_rbtn = new JRadioButton("\uD655\uC7A5\uC790 \uBC14\uAFB8\uAE30");
			extantion_rbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selected_rbtn_index = 0;
				}
			});
			option_Main.add(extantion_rbtn);
			group.add(extantion_rbtn);
			
			JRadioButton add_pre_text_rbtn = new JRadioButton("\uC811\uB450\uC0AC \uBD99\uC774\uAE30");
			add_pre_text_rbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selected_rbtn_index=1;
				}
			});
			option_Main.add(add_pre_text_rbtn);
			group.add(add_pre_text_rbtn);
			
			JRadioButton add_fore_text_rbtn = new JRadioButton("\uC811\uBBF8\uC0AC \uBD99\uC774\uAE30");
			add_fore_text_rbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selected_rbtn_index=2;
				}
			});
			option_Main.add(add_fore_text_rbtn);
			group.add(add_fore_text_rbtn);
			
			JRadioButton exchange_text_rbtn = new JRadioButton("\uD2B9\uC815\uBB38\uC790\uBC14\uAFB8\uAE30");
			exchange_text_rbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selected_rbtn_index=3;
				}
			});
			option_Main.add(exchange_text_rbtn);
			group.add(exchange_text_rbtn);
			
			
		}
	}
	
	private void click_btn_fileDialog(){
		chooser = new FileChooser();
		if(!chooser.isSelected()){
			return;
		}
		
		if (model.getRowCount() > 0) {
		    for (int i = model.getRowCount() - 1; i > -1; i--) {
		    	model.removeRow(i);
		    }
		}

		File dirFile=new File(chooser.getSelected_folder());
		fileList=dirFile.listFiles();
		rootPath=fileList[0].getParent();

		if(fileList.length > 0){
			for(File tempFile : fileList) {
				if(tempFile.isFile()) {
				    model.addRow(new Object[]{tempFile.getName(),""});
				}
			}
		}
		
		//btn_setRole.setEnabled(true);
	}
	
	private void click_btn_save(){
		if (!(model.getRowCount() > 0)) {
			JOptionPane.showMessageDialog(frame, "파일 목록이 없습니다.");
		    return;
		}
		//중복처리 #체크1
		
		for(File tempFile : fileList) {
			if(tempFile.isFile()) {
				String tempnewfilename = model.getValueAt(0, 1).toString();
				if(tempnewfilename.trim().length() > 0){
					tempFile.renameTo(new File(rootPath+"/"+tempnewfilename));
				}else{
					tempFile.renameTo(new File(rootPath+"/"+tempFile.getName()));
				}
			}
			model.removeRow(0);
		}
		JOptionPane.showMessageDialog(frame, "저장이 완료되었습니다.");
	}

	private void click_btn_accept(){
		if (!(model.getRowCount() > 0)) {
			JOptionPane.showMessageDialog(frame, "파일 목록이 없습니다.");
		    return;
		}
		switch(selected_rbtn_index){
			case 0:
				click_extection_rbtn();
				break;
			case 1:
				click_pre_text_rbtn();
				break;
			case 2:
				click_fore_text_rbtn();
				break;
			case 3:
				click_exchange_text_rbtn();
				break;
			case -1:
				JOptionPane.showMessageDialog(frame, "옵션을 정해주세요.");
				break;
		}
	}

	private void click_extection_rbtn(){
		String extention_text = JOptionPane.showInputDialog("확장자를 입력해 주세요");
		if(!(extention_text.trim().length() > 0)){
			JOptionPane.showMessageDialog(frame, "확장자를 입력해 주세요.");
			return;
		}
		
		int index = 0;
		for(File tempFile : fileList) {
			if(tempFile.isFile()) {
				String tempnewfilename = model.getValueAt(index, 0).toString();
				int extensionIndex = tempnewfilename.lastIndexOf(".");
			    if (extensionIndex == -1){
			    }else{
			    	tempnewfilename = tempnewfilename.substring(0, extensionIndex);
			    }
			    
				if(tempnewfilename.trim().length() > 0){
					model.setValueAt(tempnewfilename+"."+extention_text, index, 1);
				}else{
					model.setValueAt("", index, 1);
				}
			}
			
			index++;
		}
	}
	
	private void click_pre_text_rbtn() {
		String pre_text = JOptionPane.showInputDialog("접두사를 입력해주세요.");
		if(!(pre_text.trim().length() > 0)){
			JOptionPane.showMessageDialog(frame, "접두사를 입력해주세요.");
			return;
		}
		
		int index = 0;
		for(File tempFile : fileList) {
			if(tempFile.isFile()) {
				String tempnewfilename = model.getValueAt(index, 0).toString();
				if(tempnewfilename.trim().length() > 0){
					model.setValueAt(pre_text+tempnewfilename, index, 1);
				}else{
					model.setValueAt("", index, 1);
				}
			}
			
			index++;
		}
	}
	
	private void click_fore_text_rbtn(){
		String fore_text = JOptionPane.showInputDialog("접미사를 입력해 주세요.");
		if(!(fore_text.trim().length() > 0)){
			JOptionPane.showMessageDialog(frame, "접미사를 입력해 주세요.");
			return;
		}
		
		int index = 0;
		for(File tempFile : fileList) {
			if(tempFile.isFile()) {
				String tempnewfilename = model.getValueAt(index, 0).toString();
				int extensionIndex = tempnewfilename.lastIndexOf(".");
			    
				if(extensionIndex != -1 && tempnewfilename.trim().length() > 0){
					model.setValueAt(tempnewfilename.substring(0, extensionIndex)+fore_text+"."+tempnewfilename.substring(extensionIndex, tempnewfilename.length()), index, 1);
				}else{
					model.setValueAt("", index, 1);
				}
			}
			
			index++;
		}
	}
	
	private void click_exchange_text_rbtn(){
	    JTextField xField = new JTextField(5);
	    JTextField yField = new JTextField(5);

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("대상:"));
	    myPanel.add(xField);
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("목적:"));
	    myPanel.add(yField);
	    
	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
              "대상 문자와 목적문자를 입력해 주세요.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	         System.out.println("x value: " + xField.getText());
	         System.out.println("y value: " + yField.getText());
	    }else if( result == JOptionPane.CANCEL_OPTION){
	    	return;
	    }
	    
		int index = 0;
		for(File tempFile : fileList) {
			if(tempFile.isFile()) {
				String tempnewfilename = model.getValueAt(index, 0).toString();
				tempnewfilename = tempnewfilename.replaceAll(xField.getText(), yField.getText());
				
				if(tempnewfilename.trim().length() > 0){
					model.setValueAt(tempnewfilename, index, 1);
				}else{
					model.setValueAt("", index, 1);
				}
			}
			
			index++;
		}
	}
}
