package graphicswork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageViewer 
{
    JFrame window;
    JButton filechooser;
    JFileChooser jfc;
    ArrayList<ImageObject> images;
    Toolkit tk;
    Dimension dm;
    ActionListener listener;
    Image image;
    File selected;
    int width, height, alingmentX, alingmentY;
    
    public ImageViewer(int width, int height) 
    {
        images = new ArrayList<ImageObject>();
        this.width = width;
        this.height = height;
    }

    public void show()
    {
        //создание главного окна
        window = new JFrame("ImageViewer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        tk = Toolkit.getDefaultToolkit();
        dm = tk.getScreenSize();
        alingmentX = dm.height/2 - height/2;
        alingmentY = dm.width/2 - width/2;
        window.setBounds(alingmentY, alingmentX, width, height);
        window.setSize(width, height);
        
        jfc = new JFileChooser("D:/");
        listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                int status = jfc.showDialog(window, "Open file");
                if(status == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        JFileChooserEvent();
                    }catch (IOException ioe)
                    {
                        System.err.println("JFileChooserEventExeption!!!");
                    }
                }
            }
        };
        //параметры кнопки для вызова файловика
        filechooser = new JButton("Show JFileChooser");
        filechooser.setPreferredSize(new Dimension(width, 30));
        filechooser.addActionListener(listener);
                
        //добавляем исходные элементы
        window.add(filechooser);
            
        window.setVisible(true);
    }
    
    private void JFileChooserEvent() throws IOException
    {
        System.out.println(images.size());
        selected = jfc.getSelectedFile();
        image = ImageIO.read(selected);
        ImageObject io = new ImageObject(image, images.size()*20, images.size()*20);        
        /*ЛОМАЕТСЯ ВОТ ЗДЕСЬ ↓*/
        io.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {
                System.out.println("Mouse pressed!");
            }
        });
        images.add(io);
        window.add(images.get(images.size()-1));
    }
}
