package graphicswork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

public class ImageObject extends JLabel
{
    private Image image;
    private String name;
    private boolean isactiv;
    private int x, y, imageWidth, imageHeight;
    
    ImageObject(Image value, int x, int y)
    {
        image = value;
        isactiv = false;
        imageWidth = image.getWidth(this);
        imageHeight = image.getHeight(this);
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(imageWidth, imageHeight));
        setBounds(x, y, imageWidth, imageHeight);
    }   
    
    public void setname(String value)
    {
        name = value;
    }
    
    public String getname()
    {
        return name;
    }
    
    public void setx(int value)
    {
        x = value;
    }
    
    public void sety(int value)
    {
        y = value;
    }
    
    public int getx()
    {
        return x;
    }
    
    public int gety()
    {
        return y;
    }
    
    public boolean isActiv()
    {
        return isactiv;
    }
    
    public void setActiv(boolean value)
    {
        isactiv = value;
    }
    
    public void paint(Graphics g)
    {
        g.drawImage(image, 0, 0, this);
        if(isactiv)
        {
            g.setColor(Color.red);
            g.drawRect(0, 0, imageWidth-1, imageHeight-1);
        }
    }
}
