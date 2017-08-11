package main;

import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String selected_folder = "";
	private boolean isSelected = false;
		   
	public boolean isSelected() {
		return isSelected;
	}

	public String getSelected_folder() {
		return selected_folder;
	}

	public FileChooser() {
		this.setCurrentDirectory(new java.io.File("."));
		this.setDialogTitle("폴더 선택");
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
		    //    
		if (this.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			selected_folder = this.getSelectedFile().getPath();
			isSelected = true;
		}else {
			isSelected = false;
		}
	}
}
