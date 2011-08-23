package graphicswork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageViewer 
{
    JFrame window, askframe;
    JTextField path, sizeX, sizeY;
    JLabel jfcl, wth, hth;
    JPanel jbp, jip;
    String jpath;
    JButton filechooser, getkoord, ok, get;
    JFileChooser jfc;
    ArrayList<ImageObject> images;
    Toolkit tk;
    Dimension dm;
    ActionListener listener;
    Image image;
    File selected;
    int width, height, alingmentX, alingmentY, current = -1, precurrent;
    
    public ImageViewer() 
    {
        images = new ArrayList<ImageObject>();
        this.width = width;
        this.height = height;
    }
    
    public void start()
    {
        //создание окна задания параметров
        askframe = new JFrame("Welcome!");
        askframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tk = Toolkit.getDefaultToolkit();
        askframe.setBounds(tk.getScreenSize().width/2 - 100, tk.getScreenSize().height/2-75, 200, 150);
        askframe.setResizable(false);
        askframe.setLayout(new FlowLayout());  
        
        //метка и текстовое поле для ввода пути файловика
        jfcl =  new JLabel("JFileChooserPath");
        path = new JTextField(15);
        
        //метки и поля для ввода размеров рабочей области
        wth = new JLabel("Width: ");
        sizeX = new JTextField(3);
        
        hth = new JLabel("Height: ");
        sizeY = new JTextField(3);
        
        //создание кнопки ОК
        ok = new JButton("OK");
        ok.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
           {
               boolean ready = false;
               jpath = path.getText();
               try
               {
                   ready = true;
                   width = Integer.valueOf(sizeX.getText());
                   height = Integer.valueOf(sizeY.getText());
               }catch(NumberFormatException mfe)
               {
                   ready = false;
                   sizeX.setText("");
                   sizeY.setText("");
               }
               if(ready)
                show();
           }
        });
        
        //добавление элементов
        askframe.add(jfcl);
        askframe.add(path);
        askframe.add(wth);
        askframe.add(sizeX);
        askframe.add(hth);
        askframe.add(sizeY);
        askframe.add(ok);
        askframe.setVisible(true);
    }

    private void show()
    {
        //создание главного окна
        creatingMainWindow();
        
        jfc = new JFileChooser(jpath);
        //слушатель для кнопки вызова JFileChooser
        listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                int status = jfc.showDialog(window, "Open");
                if(status == JFileChooser.APPROVE_OPTION)                
                    try
                    {
                        fileChooserEvent();
                    }catch (IOException ioe)
                    {
                        System.err.println("JFileChooserEventExeption!!!");
                    }                
            }
        };     
        
        //параметры кнопки для вызова файловика
        filechooser = new JButton("Show JFileChooser");
        filechooser.setPreferredSize(new Dimension(width-80, 20));
        filechooser.addActionListener(listener);
        
        //кнопка взятия координат и завершения работы приложения
        get = new JButton("Get");
        get.setPreferredSize(new Dimension(55,20));
        get.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae) 
           {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter("koordinaty.txt"));
                    for(int i = 0; i < images.size(); i++)
                    {
                        bw.write(Integer.toString(images.get(i).getx()) + " " + Integer.toString(images.get(i).gety()) + "\r\n");
                    }
                    bw.close();
                } catch (IOException ex) {
                    System.err.println("Error during writing in file!");
                }                 
                
           }
        }); 
               
        //создание панелей для картинок и для кнопок
        creatingJPanels();
                        
        //добавляем исходные элементы
        window.add(jbp);
        window.add(jip);
        window.setVisible(true);
    }
    
    private void creatingMainWindow()
    {
        window = new JFrame("ImageViewer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        tk = Toolkit.getDefaultToolkit();
        dm = tk.getScreenSize();
        alingmentX = dm.height/2 - (height+65)/2;
        alingmentY = dm.width/2 - (width+10)/2;
        window.setBounds(alingmentY, alingmentX, width+10, height+65);
        window.setResizable(false);
    }
   
    private void creatingJPanels()
    {
        //создание панели для кнопки
        jbp = new JPanel();
        jbp.setPreferredSize(new Dimension(width, 25));
        jbp.setLayout(new FlowLayout());
        jbp.add(filechooser);
        jbp.add(get); 
        
        //создание панели для картинок
        jip = new JPanel();
        jip.setPreferredSize(new Dimension(width, height));
        jip.setLayout(null);
        jip.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        jip.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {
                if(current != -1)
                {
                    images.get(current).setx(me.getX());
                    images.get(current).sety(me.getY());
                    images.get(current).setBounds(me.getX(), me.getY(), images.get(current).getWidth(), images.get(current).getHeight());
                }
            }
        }); 
    }
    
    //метод, вызывающий JFileChooser и обрабатывающий его события
    private void fileChooserEvent() throws IOException
    {
        selected = jfc.getSelectedFile();
        image = ImageIO.read(selected);
        final ImageObject io = new ImageObject(image, images.size()*20, images.size()*20);   
        io.setName(Integer.toString(images.size()));
        //добавляем слушателя мыши для активации той или иной картинки
        io.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {                
                precurrent = current;
                current = Integer.valueOf(io.getName());
                if(precurrent != -1)
                {
                    images.get(precurrent).setActiv(false);
                    images.get(precurrent).repaint();
                }
                io.setActiv(true);
                io.repaint();
            }
        });
        images.add(io);        
        jip.add(io);
        jip.setVisible(false);
        jip.setVisible(true);
    }
}
