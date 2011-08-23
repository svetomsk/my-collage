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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageViewer 
{
    JFrame window;
    JPanel jbp, jip;
    JButton filechooser, getkoord;
    JFileChooser jfc;
    ArrayList<ImageObject> images;
    Toolkit tk;
    Dimension dm;
    ActionListener listener;
    Image image;
    File selected;
    int width, height, alingmentX, alingmentY, current = -1, precurrent;
    
    public ImageViewer(int width, int height) 
    {
        images = new ArrayList<ImageObject>();
        this.width = width;
        this.height = height;
    }

    public void show()
    {
        //создание главного окна
        creatingMainWindow();
        
        jfc = new JFileChooser("D:/");
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
        filechooser.setPreferredSize(new Dimension(width, 20));
        filechooser.addActionListener(listener);
               
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
        alingmentX = dm.height/2 - (height+60)/2;
        alingmentY = dm.width/2 - (width+10)/2;
        window.setBounds(alingmentY, alingmentX, width+10, height+60);
        window.setResizable(false);
    }
   
    private void creatingJPanels()
    {
        //создание панели для кнопки
        jbp = new JPanel();
        jbp.setPreferredSize(new Dimension(width, 20));
        jbp.setLayout(new BorderLayout());
        jbp.add(filechooser);
        
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
                    images.get(current).setBounds(me.getX(), me.getY(), images.get(current).getWidth(), images.get(current).getHeight());
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
