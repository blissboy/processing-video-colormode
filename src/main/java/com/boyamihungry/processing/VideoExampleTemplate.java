package com.boyamihungry.processing;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

public class VideoExampleTemplate extends PApplet {

    Capture cam;
    PImage camFrame;

    public void setup() {

        this.frameRate(30f);

        PApplet app = (PApplet)this;

        cam = new Capture(this,width,height);
        cam.start();
    }

    public void draw() {
            if ( cam.available() ) {
                cam.read();
                cam.loadPixels();
                image(cam,0,0);
            }
    }

    public void settings() {
        size(1024,768);
    }





    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "com.boyamihungry.processing.VideoExampleTemplate"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}