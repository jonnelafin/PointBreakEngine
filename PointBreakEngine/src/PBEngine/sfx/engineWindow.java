/*
 * The MIT License
 *
 * Copyright 2019 Elias.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package PBEngine.sfx;

import JFUtils.Input;
import JFUtils.dVector;
import PBEngine.Renderer;
import PBEngine.Supervisor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jonnelafin
 */
public class engineWindow extends JFrame{
    Input input = null;
    Renderer Vrenderer;
    public final double h = 540D;
    public final double w = 1080D;
    public final double size = 1D;
    public Supervisor k;
    public BufferedImage actualImage;
    ImagePanel2 out;
    
    public engineWindow(Input in, Supervisor k, Renderer ren){
        this.k = k;
        this.input = in;
        
        this.setTitle("PointBreakEngine");
        this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        Vrenderer = ren;
        Vrenderer.sSi = true;
        clean();
        out = new ImagePanel2(actualImage);
        this.add(out);
        //this.add(Vrenderer);
        clean();
    }
    
    
    public void clean(){
        try {
            actualImage = createImage(Vrenderer);
            out.imagec = actualImage;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        this.revalidate();
        this.repaint();
    }
    
    public BufferedImage createImage(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        g.dispose();
        return bi;
    }
    
    public static void main(String[] args) {
        Supervisor k = new Supervisor(0, true, new dVector(0, 0), 0);
        k.run();
        new engineWindow(k.Logic.input, k, k.Logic.Vrenderer);
    }
}
class ImagePanel2 extends JPanel{

    public BufferedImage imagec;

    public ImagePanel2(BufferedImage img) {
        super();
        this.setImage(img);
    }
    
    public void setImage(BufferedImage img){this.imagec = img;}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagec, 0, 0, null); // see javadoc for more info on the parameters            
    }

}