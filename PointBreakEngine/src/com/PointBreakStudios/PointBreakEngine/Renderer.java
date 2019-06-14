/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.Math.round;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 *
 * @author Jonnelafin
 */
public class Renderer {
    private int a;
    private int b;
    private String[][] space;
    private Color[][] colors;
    //public vCanvas canvas = new vCanvas();
    private int y;
    private int x;
    private JFrame frame;
    colorParser cP;
    int cW;
    int cH;
    dVector cSize;
    
    public void init(int x, int y, JFrame f){
        this.frame = f;
        cP = new colorParser();
        this.x = y;
        this.y = x;
        this.space = new String[y][x];
//        this.colors = new Color[x][y];
        this.a = (space.length);
        this.b = (space[0].length);
//        colorFill(Color.white);
        fill("█", Color.black, "null");
        initVector(767, 562);
    }
    public void initVector(int x, int y){
        cSize = new dVector(x, y);
        //canvas.setSize(x, y);
        //canvas.setMaximumSize(new Dimension(x,y));
        //canvas.setMinimumSize(new Dimension(x,y));
    }
    
    public String[][] gets(){return(this.space);}
    public Color[][] getc(){return(this.colors);}
    
    void fill(String goal, Color gc, String style){
        String[][] tmp;
        tmp = this.space;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.space[c][u] = cP.parse("█",  gc, style);
                
            }
        }
//        this.space = tmp;
    }
    /*void vectorFill(Color co, int vec){
        //BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.red);
        g.drawRect(0, 0, 767, 562);
        g.setColor(co);
        g.clearRect(0, 0, 767, 562);
        //canvas.setSize(767, 562);
        g.dispose();
        bs.show();
    }*/
    void colorFill(Color goal){
        Color[][] tmp;
        tmp = this.colors;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.colors[c][u] = goal;
            }
        }
    }
    void scan(int fx,int fy,int tx,int ty){
        
    }
    void change(int locy,int locx,String to, Color c, String style){
        try{
            this.space[locx][locy] = cP.parse(to,  c, style);
            
            //this.colors[locx][locy] = c;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    void vChange(float locx,float locy, Color c){
        try{
            //canvas.render(locx, locy, c);
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    
    public int sizey(){return(this.x);}
    public int sizex(){return(this.y);}
    
    
    
    

    

    
}
class vectorArea extends JPanel{
    private Color[][] master;
    public int blur = 0;
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    float x = 15.34F;
    float y = 22.48F;
    float factor = 20F;
    private int w = 0;
    private int h = 0;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        master = gridEffects.zero(master);
        for(int i : new Range(points.size())){
            Vector rl = points.get(i);
            Color c = colors.get(i);
            master[Math.round(rl.x)][Math.round(rl.y)] = c;
            
        }
        float[][] rs = gridEffects.separateRGB(master, w, h).get(0);
        float[][] gs = gridEffects.separateRGB(master, w, h).get(1);
        float[][] bs = gridEffects.separateRGB(master, w, h).get(2);
        rs = gridEffects.blur(rs, w, h, blur);
        gs = gridEffects.blur(rs, w, h, blur);
        bs = gridEffects.blur(rs, w, h, blur);
        master = gridEffects.parseColor(w, h, rs, gs, bs);
        for(int x : new Range(w)){
            for(int y : new Range(h)){
                Vector rm = new Vector(x, y);
                Color c = master[x][y];
                g.setColor(c);
                g.fillRect((int)(rm.x*factor),(int) (rm.y*factor), (int) factor, (int) factor);
                //g.drawRect((int)(r.x*factor),(int) (r.y*factor), (int) factor, (int) factor);
            }
        }
    }
    public void update(LinkedList<Vector> p,LinkedList<Color> c, float f){
        this.points = p;
        this.colors = c;
        this.factor = f;
    }
    public void init(int w, int h){
        this.w = w;
        this.h = h;
        this.setSize(w, h);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(w,h));
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        master = new Color[w][h];
        master = gridEffects.zero(master);
        //this.setBorder();
    }
}
