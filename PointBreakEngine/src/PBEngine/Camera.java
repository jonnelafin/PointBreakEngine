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

import PBEngine.gameObjects.gameObject;
import java.util.Objects;

/**
 *
 * @author Elias Eskelinen (Jonnelafin)
 */
public class Camera {
    public double x;
    public double y;
    public gameObject target;
    public Camera(double x, double y, gameObject t){
        this.x = x;
        this.y = y;
        this.target = t;
    }
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void update(){
        if(!Objects.isNull(target)){
            this.setLocation(target.getX(), target.getY());
        }
    }
    @Override
    public Camera clone(){
        return new Camera(this.x, this.y, this.target);
    }
}
