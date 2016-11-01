/*
 * Created by JFormDesigner on Wed Oct 19 21:58:59 CST 2016
 */

package com.chance.ui;

import com.chance.util.Attr;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.table.*;
import javax.swing.tree.*;

/**
 * @author Chance
 */
public class ZJPecker extends JPanel {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame jf = new JFrame("ZJPecker3.0");
        jf.setContentPane(new ZJPecker());
        jf.setIconImage(Attr.getImgRes("zjft.png").getImage());
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.pack();
        for (Component c : jf.getRootPane().getContentPane().getComponents()) System.out.println(c.getClass());
        jf.setVisible(true);
    }

    public ZJPecker() {
        initComponents();
//        toolBar1.setFloatable(false);

//        JButton[] bts = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10};
//        for (int i = 0; i < bts.length; i++) {
//            bts[i].setIcon(Attr.getImgRes("pic" + i + ".png"));
//            bts[i].setText("");
//        }

//        splitPane1.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                splitPane1.setDividerLocation(1.0 / 5.0);
//            }
//        });
//        splitPane2.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                splitPane2.setDividerLocation(4.0 / 5.0);
//            }
//        });

        textArea1.setText(new Date().toLocaleString() + " > ZJPecker3.0初始化完成");
        updateClickInfo(this);
    }

    public void updateClickInfo(JComponent c) {
        System.out.println(c.getClass());
        addMyMouseListener(c);

        Component cs[] = c.getComponents();
        if (cs.length == 0) return;
        for (Component c1 : cs) {
            if (c1 instanceof JComponent) {
                updateClickInfo((JComponent) c1);
            } else {
                System.out.println(c1.getClass());
                addMyMouseListener(c1);
            }

        }
    }

    public void addMyMouseListener(Component c){
        c.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getComponent().getClass());
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void splitPane1ComponentResized(ComponentEvent e) {
        splitPane1.setDividerLocation(1.0 / 5.0);
    }

    private void splitPane2ComponentResized(ComponentEvent e) {
        splitPane2.setDividerLocation(4.0 / 5.0);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menu2 = new JMenu();
        menuItem7 = new JMenuItem();
        menuItem8 = new JMenuItem();
        menuItem9 = new JMenuItem();
        menuItem10 = new JMenuItem();
        menuItem11 = new JMenuItem();
        menuItem12 = new JMenuItem();
        menu3 = new JMenu();
        menuItem13 = new JMenuItem();
        menuItem14 = new JMenuItem();
        menuItem15 = new JMenuItem();
        menuItem16 = new JMenuItem();
        menuItem17 = new JMenuItem();
        menuItem18 = new JMenuItem();
        menuItem19 = new JMenuItem();
        menuItem20 = new JMenuItem();
        menu4 = new JMenu();
        menu5 = new JMenu();
        menuItem21 = new JMenuItem();
        menu6 = new JMenu();
        menu7 = new JMenu();
        menuItem22 = new JMenuItem();
        menuItem23 = new JMenuItem();
        menuItem24 = new JMenuItem();
        toolBar1 = new JToolBar();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();
        button10 = new JButton();
        splitPane2 = new JSplitPane();
        splitPane1 = new JSplitPane();
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();
        scrollPane4 = new JScrollPane();
        tree2 = new JTree();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        scrollPane3 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u5de5\u7a0b(P)");

                //---- menuItem1 ----
                menuItem1.setText("\u65b0\u5efa(N)");
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("\u6253\u5f00(O)");
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("\u5173\u95ed(C)");
                menu1.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText("\u5c5e\u6027(A)");
                menu1.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText("\u5bfc\u51fa\u62a5\u8868(R)");
                menu1.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText("\u9000\u51fa(X)");
                menu1.add(menuItem6);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("\u6d4b\u8bd5(T)");

                //---- menuItem7 ----
                menuItem7.setText("\u5f00\u59cb(S)");
                menu2.add(menuItem7);

                //---- menuItem8 ----
                menuItem8.setText("\u505c\u6b62(E)");
                menu2.add(menuItem8);

                //---- menuItem9 ----
                menuItem9.setText("\u624b\u673a\u65e5\u5fd7(C)");
                menu2.add(menuItem9);

                //---- menuItem10 ----
                menuItem10.setText("\u622a\u5c4f(P)");
                menu2.add(menuItem10);

                //---- menuItem11 ----
                menuItem11.setText("\u4e0b\u8f7d(D)");
                menu2.add(menuItem11);

                //---- menuItem12 ----
                menuItem12.setText("\u4e0a\u4f20(U)");
                menu2.add(menuItem12);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("\u6848\u4f8b(E)");

                //---- menuItem13 ----
                menuItem13.setText("\u4e0a\u79fb(U)");
                menu3.add(menuItem13);

                //---- menuItem14 ----
                menuItem14.setText("\u4e0b\u79fb(D)");
                menu3.add(menuItem14);

                //---- menuItem15 ----
                menuItem15.setText("\u6dfb\u52a0(A)");
                menu3.add(menuItem15);

                //---- menuItem16 ----
                menuItem16.setText("\u5220\u9664(D)");
                menu3.add(menuItem16);

                //---- menuItem17 ----
                menuItem17.setText("\u9009\u4e2d\u6240\u6709(S)");
                menu3.add(menuItem17);

                //---- menuItem18 ----
                menuItem18.setText("\u53d6\u6d88\u9009\u4e2d(C)");
                menu3.add(menuItem18);

                //---- menuItem19 ----
                menuItem19.setText("\u5386\u53f2\u8bb0\u5f55(H)");
                menu3.add(menuItem19);

                //---- menuItem20 ----
                menuItem20.setText("\u5c5e\u6027(R)");
                menu3.add(menuItem20);
            }
            menuBar1.add(menu3);

            //======== menu4 ========
            {
                menu4.setText("\u56fe\u8868(C)");
            }
            menuBar1.add(menu4);

            //======== menu5 ========
            {
                menu5.setText("\u7cfb\u7edf(S)");

                //---- menuItem21 ----
                menuItem21.setText("\u914d\u7f6e(C)");
                menu5.add(menuItem21);
            }
            menuBar1.add(menu5);

            //======== menu6 ========
            {
                menu6.setText("\u7a97\u53e3(W)");
            }
            menuBar1.add(menu6);

            //======== menu7 ========
            {
                menu7.setText("\u5e2e\u52a9(H)");

                //---- menuItem22 ----
                menuItem22.setText("\u5e2e\u52a9(P)");
                menu7.add(menuItem22);

                //---- menuItem23 ----
                menuItem23.setText("\u5173\u4e8e(A)");
                menu7.add(menuItem23);

                //---- menuItem24 ----
                menuItem24.setText("\u6ce8\u518c\u5e8f\u53f7(R)");
                menu7.add(menuItem24);
            }
            menuBar1.add(menu7);
        }

        //======== toolBar1 ========
        {
            toolBar1.setFloatable(false);

            //---- button1 ----
            button1.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic0.png")));
            toolBar1.add(button1);
            toolBar1.addSeparator();

            //---- button2 ----
            button2.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic1.png")));
            toolBar1.add(button2);

            //---- button3 ----
            button3.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic2.png")));
            toolBar1.add(button3);
            toolBar1.addSeparator();

            //---- button4 ----
            button4.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic3.png")));
            toolBar1.add(button4);

            //---- button5 ----
            button5.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic4.png")));
            toolBar1.add(button5);

            //---- button6 ----
            button6.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic5.png")));
            toolBar1.add(button6);
            toolBar1.addSeparator();

            //---- button7 ----
            button7.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic6.png")));
            toolBar1.add(button7);

            //---- button8 ----
            button8.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic7.png")));
            toolBar1.add(button8);

            //---- button9 ----
            button9.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic8.png")));
            toolBar1.add(button9);

            //---- button10 ----
            button10.setIcon(new ImageIcon(getClass().getResource("/com/chance/res/pic9.png")));
            toolBar1.add(button10);
        }

        //======== splitPane2 ========
        {
            splitPane2.setDividerLocation(500);
            splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
            splitPane2.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    splitPane2ComponentResized(e);
                }
            });

            //======== splitPane1 ========
            {
                splitPane1.setDividerLocation(200);
                splitPane1.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        splitPane1ComponentResized(e);
                    }
                });

                //======== tabbedPane1 ========
                {

                    //======== scrollPane1 ========
                    {

                        //---- tree1 ----
                        tree1.setModel(new DefaultTreeModel(
                            new DefaultMutableTreeNode("Project") {
                                {
                                    DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Module1");
                                        node1.add(new DefaultMutableTreeNode("Class1"));
                                        node1.add(new DefaultMutableTreeNode("Class2"));
                                        node1.add(new DefaultMutableTreeNode("Class3"));
                                    add(node1);
                                    node1 = new DefaultMutableTreeNode("Module2");
                                        node1.add(new DefaultMutableTreeNode("Class1"));
                                        node1.add(new DefaultMutableTreeNode("Class2"));
                                    add(node1);
                                    node1 = new DefaultMutableTreeNode("Module3");
                                        node1.add(new DefaultMutableTreeNode("Class1"));
                                    add(node1);
                                }
                            }));
                        scrollPane1.setViewportView(tree1);
                    }
                    tabbedPane1.addTab("\u5de5\u7a0b\u7ed3\u6784", scrollPane1);

                    //======== scrollPane4 ========
                    {

                        //---- tree2 ----
                        tree2.setModel(new DefaultTreeModel(
                            new DefaultMutableTreeNode("Tools") {
                                {
                                    DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Catagory1");
                                        node1.add(new DefaultMutableTreeNode("Tool1"));
                                        node1.add(new DefaultMutableTreeNode("Tool2"));
                                        node1.add(new DefaultMutableTreeNode("Tool3"));
                                    add(node1);
                                    node1 = new DefaultMutableTreeNode("Catagory2");
                                        node1.add(new DefaultMutableTreeNode("Tool1"));
                                        node1.add(new DefaultMutableTreeNode("Tool2"));
                                    add(node1);
                                    node1 = new DefaultMutableTreeNode("Catagory3");
                                        node1.add(new DefaultMutableTreeNode("Tool1"));
                                    add(node1);
                                }
                            }));
                        scrollPane4.setViewportView(tree2);
                    }
                    tabbedPane1.addTab("\u57fa\u672c\u5de5\u5177", scrollPane4);
                }
                splitPane1.setLeftComponent(tabbedPane1);

                //======== scrollPane2 ========
                {

                    //---- table1 ----
                    table1.setModel(new DefaultTableModel(
                        new Object[][] {
                            {"1", "11", "111", "1111", null},
                            {"2", "22", "222", "2222", null},
                            {"3", "33", "333", "3333", null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                            {null, null, null, null, null},
                        },
                        new String[] {
                            "\u6848\u4f8b\u540d\u79f0", "\u6d4b\u8bd5\u7ed3\u679c", "\u5907\u6ce8", "\u65f6\u95f4\u70b9", "\u65e5\u5fd7\u622a\u56fe"
                        }
                    ));
                    table1.setRowHeight(20);
                    table1.setAutoCreateRowSorter(true);
                    table1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    table1.setFillsViewportHeight(true);
                    scrollPane2.setViewportView(table1);
                }
                splitPane1.setRightComponent(scrollPane2);
            }
            splitPane2.setTopComponent(splitPane1);

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(textArea1);
            }
            splitPane2.setBottomComponent(scrollPane3);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(menuBar1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(toolBar1, GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                        .addComponent(splitPane2, GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(menuBar1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(toolBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(splitPane2, GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenu menu2;
    private JMenuItem menuItem7;
    private JMenuItem menuItem8;
    private JMenuItem menuItem9;
    private JMenuItem menuItem10;
    private JMenuItem menuItem11;
    private JMenuItem menuItem12;
    private JMenu menu3;
    private JMenuItem menuItem13;
    private JMenuItem menuItem14;
    private JMenuItem menuItem15;
    private JMenuItem menuItem16;
    private JMenuItem menuItem17;
    private JMenuItem menuItem18;
    private JMenuItem menuItem19;
    private JMenuItem menuItem20;
    private JMenu menu4;
    private JMenu menu5;
    private JMenuItem menuItem21;
    private JMenu menu6;
    private JMenu menu7;
    private JMenuItem menuItem22;
    private JMenuItem menuItem23;
    private JMenuItem menuItem24;
    private JToolBar toolBar1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JSplitPane splitPane2;
    private JSplitPane splitPane1;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JScrollPane scrollPane4;
    private JTree tree2;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JScrollPane scrollPane3;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
