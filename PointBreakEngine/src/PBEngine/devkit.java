/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
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
package PBEngine;

import JFUtils.Range;
import JFUtils.dVector;
import JFUtils.quickTools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Elias Eskelinen <elias.eskelinen@protonmail.com>
 */
public class devkit extends JFrame{
    public JPanel cont = new JPanel();
    boolean tog = false;
    Supervisor k;
    JButton graphic = new JButton("toggle vector");
    JButton rays = new JButton("toggle rays");
    JLabel time = new JLabel("RAYYYS");
    JTextArea log = new JTextArea("PointBreakEngine devkit");
    JTextField lum = new JTextField(20);
    JScrollPane logs = new JScrollPane(log);
    public devkit(Supervisor k) {
        this.setTitle("PointBreakEngine devkit");
        this.k = k;
        this.setSize(400, 550);
        this.setLocationRelativeTo(k.Logic.window);
        this.setLocation(1080, 0);
        
        cont.setLayout(new BorderLayout());
        graphic.addActionListener(new BListener(9, this));
        rays.addActionListener(new BListener(2, this));
        lum.addActionListener(new BListener(0, this));
        logs.setWheelScrollingEnabled(true);
        //log.setColumns(1);
        log.setRows(15);
        logs.setWheelScrollingEnabled(true);
        //cont.add(graphic, BorderLayout.NORTH);
        //cont.add(rays, BorderLayout.NORTH);
        cont.add(time, BorderLayout.EAST);
        cont.add(logs, BorderLayout.CENTER);
        cont.add(lum, BorderLayout.SOUTH);
        this.add(cont);
        this.setVisible(true);
    }
    public void togG(){
        if(tog){
            k.Logic.vector = 1;
        }
        else{
            k.Logic.vector = 0;
        }
        tog = !tog;
    }
    boolean togV = true;
    public void togV(){
        if(togV){
            k.Logic.renderRays = 0;
        }
        else{
            k.Logic.renderRays = 1;
        }
        togV = !togV;
    }
}
class BListener implements ActionListener{
    boolean abright = false;
    int type;
    devkit k;
    public BListener(int t, devkit d){
        this.type = t;
        this.k = d;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(type == 9){
            System.out.println("Graphics!");
            k.togG();
        }
        if(type == 2){
            System.out.println("Rays!");
            k.togV();
        }
        try{
            String arr[] = k.lum.getText().split(" ", 2);
            
            if(k.lum.getText().charAt(0) == '/'){
                switch(arr[0]){
                    case "/collisions":
                        if(arr[1].matches("true")){k.k.engine_collisions = true;}
                        else if(arr[1].matches("false")){k.k.engine_collisions = false;}
                        else{
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/blur":
                        k.k.Logic.blurStrenght = Integer.parseInt(arr[1]);
                        break;
                    case "/bright":
                        k.k.Logic.global_brightness = Float.parseFloat(arr[1]);
                        break;
                    case "/tp":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            for(gameObject o : k.k.objectManager.getObjectsByTag(values[0])){
                                o.setLocation(new dVector(x, y));
                            }
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/noclip":
                        k.k.Logic.oM.getObjectByTag("player1").noclip();
                        break;
                    case "/abright":
                        abright = !abright;
                        k.k.Logic.abright = abright;
                        System.out.println(abright + ", " + k.k.Logic.abright);
                        break;
                    case "/add":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            String tag = values[0];
                            k.k.objectManager.addObject(new gameObject(x, y, 1, tag, "N", 1, Color.black, 1919, k.k));
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/rm":
                        String values[] = arr[1].split(" ", 2);
                        for(gameObject o : k.k.objectManager.getObjectsByTag(values[0])){
                                k.k.objectManager.removeObject(o);
                            }
                        break;
                    case "/g":
                        String valuesa[] = arr[1].split(" ", 2);
                        try {
                            k.k.engine_gravity = new dVector(Double.parseDouble(valuesa[0]), Double.parseDouble(valuesa[1]));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/relight":
                        VSRadManager vsradm = k.k.rad;
                        vsradm.recalculate("IgnoreRecalculation", 1, true);
                        //vsradm.recalculateParent();
                        break;
                    case "/lvl":
                        Thread a = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    k.k.Logic.loadLevel(arr[1] + ".pblevel");
                                } catch (URISyntaxException ex) {
                                    Logger.getLogger(BListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        a.start();
                        break;
                    case "/lvltest":
                        Thread b = new Thread(){
                            @Override
                            public void run(){
                                boolean map = false;
                                double sum = 0;
                                for(int i : new Range(Integer.parseInt(arr[1]))){
                                    String lo = "out2.txt";
                                    if(map){
                                        lo = "shadingtest.txt";
                                    }
                                    try {
                                        k.k.Logic.loadLevel("!random 25");
                                    } catch (URISyntaxException ex) {
                                        Logger.getLogger(BListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    sum = sum + k.k.Logic.mapLoadTime;
                                    map = !map;
                                }
                                System.out.println("Map loading time test completed in " + sum + " ms!");
                            }
                        };
                        b.start();
                        break;
                    case "/z":
                        k.k.Logic.Vrenderer.factor = Integer.parseInt(arr[1]);
                        break;
                    case "/lum":
                        k.k.Logic.global_brightness = Integer.parseInt(arr[1]);
                        System.out.println(k.k.Logic.global_brightness);
                        break;
                    case "/id":
                        int id = Integer.parseInt(arr[1]);
                        System.out.println("Object ID"+id+" tags:");
                        for(String x : k.k.objectManager.getObjectByID(id).getTag()){
                            System.out.println("    "+x);
                        }
                        break;
                    case "/rc_levelmap":
                        k.k.Logic.constructLevelmap();
                        break;
                    case "/map":
                        k.k.Logic.printLevelmap();
                        break;
                    case "/ps":
                        k.k.Logic.running = !k.k.Logic.running;
                        break;
                    case "/nausea":
                        k.k.Logic.Vrenderer.dispEffectsEnabled = true;
                        break;
                    case "/vfx":
                        k.k.Logic.window.useVFX = !k.k.Logic.window.useVFX;
                        System.out.println("VFX IS NOW: " + k.k.Logic.window.useVFX);
                        break;
                    default:
                        quickTools.alert("devkit", "command not understood");
                        break;
                }
            }
            else{
                System.out.println(k.lum.getText());
            }
        }
        catch(Exception e){}
        k.time.setText(Boolean.toString(k.togV));
    }
    
}
