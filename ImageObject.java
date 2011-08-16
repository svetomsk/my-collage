package graphicswork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class ImageObject extends JLabel
{
    private Image image;
    private int x, y, imageWidth, imageHeight;
    
    ImageObject(Image value, int x, int y)
    {
        image = value;
        imageWidth = image.getWidth(this);
        imageHeight = image.getHeight(this);
        this.x = x;
        this.y = y;
        setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
        setPreferredSize(new Dimension(imageWidth+30, imageHeight+30));
    }   
    
    
    public void paint(Graphics g)
    {
        g.drawImage(image, x, y, this);
    }
    
    public int getWidth()
    {
        return imageWidth;
    }
    
    public int getHeight()
    {
        return imageHeight;
    }
}
