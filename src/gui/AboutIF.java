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

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.beans.PropertyVetoException;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JScrollPane;

/*
 *  JInternalFrame that contains the "about" help
 */


class AboutIF extends JInternalFrame{
	
	private static MainFrame mainFrame = MainFrame.getInstance();
	private static final long serialVersionUID = 1L;
	private static AboutIF instance = null;
	
	static void open() {
		if(instance == null) {
			instance = new AboutIF();
		}else {
			instance.init();
		}
	}
	
	private AboutIF() {
		firstTimeInit();
	}
	
	private void firstTimeInit() {
		setTitle("About");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(30, 30,526,410);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("General", null, panel_2, null);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2, BorderLayout.CENTER);
        
        JTextPane txtpnIosu = new JTextPane();
        txtpnIosu.setEditable(false);
        txtpnIosu.setText("Revamp Framework- 2019");
        scrollPane_2.setViewportView(txtpnIosu);
        mainFrame.getContentPane().add(this);
        init();
        
	}
	
	private void init() {
        setVisible(true);
        try {
        	setIcon(false);
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
        moveToFront();
	}
	
}
