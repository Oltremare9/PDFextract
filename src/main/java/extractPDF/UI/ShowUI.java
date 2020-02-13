package extractPDF.UI;

import com.csvreader.CsvReader;
import extractPDF.CSV.WriteCSV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import static extractPDF.exeFile.extractFile;
import static extractPDF.exeFile.readFile;


public class ShowUI {
    private static int type = -1;
    private static String pdfURL = "";
    private static String txtURL = "";
    private static String excelURL = "";

    public static void show() {

        JPanel top = new JPanel();
        JPanel mid = new JPanel();
        JPanel bottom = new JPanel();
        JPanel buttons = new JPanel();
        //提取参数设置
        JLabel magName = new JLabel("新期刊名");

        Label label1 = new Label();
        label1.setText("x");
        Label label2 = new Label();
        label2.setText("y");
        Label label3 = new Label();
        label3.setText("width");
        Label label4 = new Label();
        label4.setText("height");
        JTextField x = new JTextField();
        JTextField y = new JTextField();
        JTextField width = new JTextField();
        JTextField height = new JTextField();
        JTextField magazine = new JTextField();
        x.setPreferredSize(new Dimension(40, 20));
        y.setPreferredSize(new Dimension(40, 20));
        width.setPreferredSize(new Dimension(40, 20));
        height.setPreferredSize(new Dimension(40, 20));
        magazine.setPreferredSize(new Dimension(80, 20));
        top.setLayout(new FlowLayout());
        top.add(magName);
        top.add(magazine);
        top.add(label1);
        top.add(x);
        top.add(Box.createHorizontalGlue());
        top.add(label2);
        top.add(y);
        top.add(Box.createHorizontalGlue());
        top.add(label3);
        top.add(width);
        top.add(Box.createHorizontalGlue());
        top.add(label4);
        top.add(height);


        //文件路径选择
        //pdf路径
        JPanel PDFpath = new JPanel();
        PDFpath.setLayout(new FlowLayout());
        JLabel label5 = new JLabel("PDF文件路径");
        JButton choosePDF = new JButton("选择文件");
        JLabel showPDF = new JLabel("未选中pdf文件目录");
        choosePDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("D:\\LDA\\金融论文");             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = chooser.showOpenDialog(choosePDF);        //是否打开文件选择框
                System.out.println("returnVal=" + returnVal);

                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型

                    pdfURL = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    if (pdfURL.length() > 0) {
                        showPDF.setText(pdfURL);
                    }
                    System.out.println(pdfURL);
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());  //输出相对路径
                }
            }
        });

        PDFpath.add(label5);
        PDFpath.add(choosePDF);
        PDFpath.add(showPDF);

        JPanel TXTpath = new JPanel();
        PDFpath.setLayout(new FlowLayout());
        JLabel label6 = new JLabel("txt文件路径");
        JButton choosetxt = new JButton("选择文件");
        JLabel showTXT = new JLabel("未选中txt文件目录");
        choosetxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("D:\\LDA");             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = chooser.showOpenDialog(choosetxt);        //是否打开文件选择框
                System.out.println("returnVal=" + returnVal);

                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型

                    txtURL = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    if (txtURL.length() > 0) {
                        showTXT.setText(txtURL);
                    }
                    System.out.println(txtURL);
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());  //输出相对路径
                }
            }
        });

        TXTpath.add(label6);
        TXTpath.add(choosetxt);
        TXTpath.add(showTXT);

        JPanel EXLpath = new JPanel();
        PDFpath.setLayout(new FlowLayout());
        JLabel label8 = new JLabel("输出excel文件路径");
        JButton chooseExcel = new JButton("选择文件");
        JLabel showEXCEL = new JLabel("未选中输出excel文件");
        chooseExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("D:\\");             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = chooser.showOpenDialog(chooseExcel);        //是否打开文件选择框
                System.out.println("returnVal=" + returnVal);

                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型

                    excelURL = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    if (excelURL.length() > 0) {
                        showEXCEL.setText(excelURL);
                    }
                    System.out.println(excelURL);
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());  //输出相对路径
                }
            }
        });

        EXLpath.add(label8);
        EXLpath.add(chooseExcel);
        EXLpath.add(showEXCEL);

        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.add(PDFpath);
        mid.add(TXTpath);
        mid.add(EXLpath);
        //提取模式设置
        JLabel label7 = new JLabel("提取方式");
        JRadioButton jradio1 = new JRadioButton("从pdf中提取全文至txt");
        JRadioButton jradio2 = new JRadioButton("从pdf中部分拆分");
        JRadioButton jradio3 = new JRadioButton("直接从txt文本中拆分");
        ButtonGroup group = new ButtonGroup();
        bottom.add(label7);
        bottom.add(jradio1);
        bottom.add(jradio2);
        bottom.add(jradio3);
        group.add(jradio1);
        group.add(jradio2);
        group.add(jradio3);
        jradio1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == jradio1) {
                    type = 1;
//                    System.out.println(type);
                }
            }
        });
        jradio2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == jradio2) {
                    type = 2;
//                    System.out.println(type);
                }
            }
        });
        jradio3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == jradio3) {
                    type = 3;
//                    System.out.println(type);
                }
            }
        });


        //按钮pannel
        buttons.setLayout(new FlowLayout());
        JButton submitInfo = new JButton("提交提取规则");
        JButton extract = new JButton("开始提取");
        buttons.add(submitInfo);
        buttons.add(Box.createGlue());
        buttons.add(extract);
        extract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (type == -1)
                    JOptionPane.showMessageDialog(null, "没有选择处理类型哦",
                            "错误", JOptionPane.ERROR_MESSAGE);

                switch (type) {
                    case 1:
                        if (pdfURL.length() == 0 || txtURL.length() == 0) {
                            JOptionPane.showMessageDialog(null, "没有选择路径",
                                    "错误", JOptionPane.ERROR_MESSAGE);
                        } else {
                            long start = System.currentTimeMillis();
                            readFile(pdfURL, txtURL);
                            JOptionPane.showMessageDialog(null, "处理完成",
                                    "完成", JOptionPane.INFORMATION_MESSAGE);
                            long end = System.currentTimeMillis();
                            System.out.println("start time:" + start +
                                    "; end time:" + end +
                                    "; Run Time:" + (end - start) / 1000 + "(s)   "
                                    + (end - start) / 60000 + "（mins）"
                                    + ((end - start) / 1000) % 60 + "s");
                        }

                        break;
                    case 2:
                        if (pdfURL.length() == 0 || txtURL.length() == 0 || excelURL.length() == 0) {
                            JOptionPane.showMessageDialog(null, "没有选择路径",
                                    "错误", JOptionPane.ERROR_MESSAGE);
                        } else {
                            long start = System.currentTimeMillis();
                            readFile(pdfURL, txtURL);
                            WriteCSV writeCSV1 = new WriteCSV(excelURL + "\\res.xls");
                            extractFile(txtURL, writeCSV1);
                            writeCSV1.write();
                            writeCSV1.close();
                            long end = System.currentTimeMillis();
                            System.out.println("start time:" + start +
                                    "; end time:" + end +
                                    "; Run Time:" + (end - start) / 1000 + "(s)   "
                                    + (end - start) / 60000 + "（mins）"
                                    + ((end - start) / 1000) % 60 + "s");
                        }
                        break;
                    case 3:
                        if (txtURL.length() == 0 || excelURL.length() == 0) {
                            JOptionPane.showMessageDialog(null, "没有选择路径",
                                    "错误", JOptionPane.ERROR_MESSAGE);
                        } else {
                            long start = System.currentTimeMillis();
                            WriteCSV writeCSV2 = new WriteCSV(excelURL + "\\res.xls");
                            extractFile(txtURL, writeCSV2);
                            writeCSV2.write();
                            writeCSV2.close();
                            long end = System.currentTimeMillis();
                            System.out.println("start time:" + start +
                                    "; end time:" + end +
                                    "; Run Time:" + (end - start) / 1000 + "(s)   "
                                    + (end - start) / 60000 + "（mins）"
                                    + ((end - start) / 1000) % 60 + "s");
                        }
                        break;
                }

            }
        });
        submitInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CsvReader csvReader = new CsvReader("D:\\rule.csv", ',', Charset.forName("UTF-8"));
                    while (csvReader.readRecord()) {
                        String text = magazine.getText();
                        String newtitle = text.substring(0, text.length() - 4);
                        String newyear = text.substring(text.length() - 4);
                        String str = csvReader.get(0);
                        String year = str.substring(str.length() - 4);
                        String title = str.substring(0, str.length() - 4);
//                        System.out.println(year+"^^"+title);

                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        JFrame jf = new JFrame("PDF提取工具");
        jf.getContentPane().setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));

        jf.add(top);
        jf.add(mid);
        jf.add(bottom);
        jf.add(buttons);
        jf.setSize(600, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }

    public static void main(String args[]) {
        show();
    }

}
