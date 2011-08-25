package graphicswork;

import java.util.ResourceBundle;

class Text 
{
    private ResourceBundle rb;
    
    Text()
    {
        rb = ResourceBundle.getBundle("graphicswork.collage");
    }
    
    public String settings()
    {
        return rb.getString("collage.settings");
    }
    
    public String path()
    {
        return rb.getString("collage.path");
    }
    
    public String width()
    {
        return rb.getString("collage.width");
    }
    
    public String height()
    {
        return rb.getString("collage.height");
    }
    
    public String ok()
    {
        return rb.getString("collage.ok");
    }
    
    public String filter1()
    {
        return rb.getString("collage.filter1");
    }
    
    public String filter2()
    {
        return rb.getString("collage.filter2");
    }
    
    public String filter3()
    {
        return rb.getString("collage.filter3");
    }
    
    public String open()
    {
        return rb.getString("collage.open");
    }
    
    public String exception()
    {
        return rb.getString("collage.exception");
    }
    
    public String show()
    {
        return rb.getString("collage.show");
    }
    
    public String get()
    {
        return rb.getString("collage.get");
    }    
    
    public String error()
    {
        return rb.getString("collage.error");
    }
    
    public String imageViewer()
    {
        return rb.getString("collage.imageviewer");
    }
}
