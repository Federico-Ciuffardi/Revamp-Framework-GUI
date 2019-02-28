/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright (C) 2019  Federico Ciuffardi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact me (federico.ciuffardi@outlook.com) if you need 
 * additional information or have any questions.
 */

package gui;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import com.github.federico_ciuffardi.util.Prefs;


/* 
 * Main JFrame
 */

class MainFrame extends JFrame{
	private static String revampFramework;
	private Path projRoot;
	private static String rfprojName;
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	
	public static void main(String[] args) {
		//find the correct version of the revamp-framework
		File folder = new File("./libraries");
		File[] listOfFiles = folder.listFiles();
		for (File f: listOfFiles) {
			if(f.getName().matches("revamp-framework-.*")) {
				revampFramework = f.getName();
				break;
			}
		}
		Prefs prefs = new Prefs("revamp-framework");
		prefs.setDefault("theme", UIManager.getSystemLookAndFeelClassName());
		try {
			UIManager.setLookAndFeel(prefs.get("theme"));		
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e1) {
				e.printStackTrace();
			}
		}
		instance = new MainFrame();
	}
	
	static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	private MainFrame() {
		setContentPane(new JDesktopPane());
		setTitle("Revamp Framework");
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProject = new JMenu("Project");
		menuBar.add(mnProject);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        ".rfproj", "rfproj");
                    chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
    				 try {
    					 if(!Files.exists(Paths.get(chooser.getSelectedFile().getAbsolutePath()))) {
    						 Path file = Paths.get(chooser.getSelectedFile().getAbsolutePath()+".rfproj");
	    					 projRoot = file.getParent();
	    					 List<String> lines = Arrays.asList("");
	    					 Files.write(file, lines, Charset.forName("UTF-8"));
	    					 //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    					 }else {
    						 JOptionPane.showMessageDialog(instance, "Project already exist", "ERROR", JOptionPane.ERROR_MESSAGE);
    					 }
    				 } catch (IOException e1) {
    				 	e1.printStackTrace();
    				 }
                } 
			}
		});
		mnProject.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        ".rfproj", "rfproj");
                    chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
    				 if(Files.exists(Paths.get(chooser.getSelectedFile().getAbsolutePath()))) {
						 Path file = Paths.get(chooser.getSelectedFile().getAbsolutePath()+".rfproj");
						 projRoot = file.getParent();
					 }else {
						 JOptionPane.showMessageDialog(instance, "Project does not exist", "ERROR", JOptionPane.ERROR_MESSAGE);
					 }
                } 
			}
		});
		mnProject.add(mntmOpen);
		
		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);
		
		JMenuItem mntmRun = new JMenuItem("Run last");
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec("java -jar ./libraries/"+ revampFramework);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mnRun.add(mntmRun);
		
		JMenuItem mntmRun_1 = new JMenuItem("Run...");
		mntmRun_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = JOptionPane.showInputDialog("inital room");
				String cmd1 = "/usr/bin/javac -classpath "+(new File("")).getAbsolutePath()+"/libraries/"+ revampFramework +" -d "+projRoot.toAbsolutePath().toString()+"/bin/  "+projRoot.toAbsolutePath().toString()+"/src/*/*.java";
				String cmd2 = "/usr/bin/jar cvf "+projRoot.getFileName().toString()+".jar -C "+projRoot.toAbsolutePath().toString()+"/bin .";
				String cmd3 = "/usr/bin/java -jar "+(new File("")).getAbsolutePath()+"/libraries/"+ revampFramework +" ~/"+projRoot.getFileName().toString()+".jar "+s;
				//searchAndCompile(projRoot.toAbsolutePath().toString());
				try {
					
					runCommand("javac -classpath "+(new File("")).getAbsolutePath()+"/libraries/"+ revampFramework +" -d "+projRoot.toAbsolutePath().toString()+"/bin "+projRoot.toAbsolutePath().toString()+"/src/*/*");
					runCommand(cmd2);
					runCommand(cmd3);		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnRun.add(mntmRun_1);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmKeyboardShorcuts = new JMenuItem("Keyboard Shorcuts");
		mntmKeyboardShorcuts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShortcutsIF.open();
			}
		});
		mnHelp.add(mntmKeyboardShorcuts);
		
		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutIF.open();
			}
		});
		mnHelp.add(mntmAbout);
		setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		setVisible(true);
	}
	private void searchAndCompile(String path) {
		File file = new File(path);
		if(!file.isDirectory()) {
			if(file.getName().matches(".*\\.java")) {
				try {
					runCommand("javac -classpath "+(new File("")).getAbsolutePath()+"/libraries/"+ revampFramework +" -d "+projRoot.toAbsolutePath().toString()+"/bin "+file.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			File[] listOfFiles = file.listFiles();
			for (File f: listOfFiles) {
				searchAndCompile(f.getAbsolutePath());
			}
		}
	}
	private void runCommand(String cmd) throws IOException {
		System.out.println(">"+cmd);
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(cmd);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		System.out.println();
		String ss = null;
		while ((ss = stdInput.readLine()) != null) {
		    System.out.println(ss);
		}

		// read any errors from the attempted command
		System.out.println();
		while ((ss = stdError.readLine()) != null) {
		    System.out.println(ss);
		}
	}
}
