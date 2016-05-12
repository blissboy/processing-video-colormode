package com.boyamihungry.processing;

import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Slider;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

import java.util.function.BiConsumer;

public class ColorModeVideoExample extends PApplet {

    Capture cam;
    PImage camFrame;
    ControlP5 cp5;

    int camHeight = 0;
    int camWidth = 0;

    int rgb_r = 9;

    public static final int SLIDER_HEIGHT=40;



    public int addControl(ControlGroup g, Slider slider) {
        return 9;
    }

    public interface sliderAdder {
        public int addToGroup(ControlGroup g, Slider s);
    }

    public void setup() {

        this.frameRate(30f);

        PApplet app = (PApplet)this;

        camWidth = width / 3;
        camHeight = height / 3;


        // set up contol panel
        cp5 = new ControlP5(this);
        cp5.setAutoDraw(false);

        int sliderHeight = 40;
        int margin = 40;

        BiConsumer<Group, String[]> createSliders =
                (group, variables) -> {
                    int yPos = 0;

                    BiConsumer<String, Integer> createSlider =
                            (sliderVar, sliderY) -> {
                                new Slider(cp5, "rgb_r")
                                        .setPosition(0,sliderY)
                                        .setRange(0,255)
                                        .setSize(camWidth - margin, sliderHeight)
                                        .setGroup(group)
                                ;

                            };

                    for ( String var : variables) {
                        createSlider.accept(var, yPos);
                        yPos += sliderHeight;
                    }

                };



        Group rgbGroup = cp5.addGroup("rgbGroup")
                .setPosition(0,camHeight + margin)
                .setWidth(camWidth - margin)
                .activateEvent(true)
                .setBackgroundColor(color(44,80))
                .setBackgroundHeight(100)
                .setLabel("RGB")
                ;

        Group hsbGroup = cp5.addGroup("hsbGroup")
                .setPosition(camWidth + margin, camHeight + margin)
                .setWidth(camWidth - margin)
                .activateEvent(true)
                .setBackgroundColor(color(66,80))
                .setBackgroundHeight(100)
                .setLabel("HSB")
                ;

        Group cmykGroup = cp5.addGroup("cmykGroup")
                .setPosition(camWidth + margin, camHeight + margin)
                .setWidth(camWidth - margin)
                .activateEvent(true)
                .setBackgroundColor(color(66,80))
                .setBackgroundHeight(100)
                .setLabel("CMYK")
                ;


        int yPos = 0;
        new Slider(cp5, "rgb_r")
                .setPosition(0,yPos)
                .setRange(0,255)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(rgbGroup)
        ;
        new Slider(cp5, "hsb_h")
                .setPosition(0,yPos)
                .setRange(0,360)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(hsbGroup)
        ;
        new Slider(cp5, "cmyk_c")
                .setPosition(0,yPos)
                .setRange(0,100)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(cmykGroup)
        ;

        yPos += sliderHeight;
        new Slider(cp5, "rgb_g")
                .setPosition(0,yPos)
                .setRange(0,255)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(rgbGroup)
        ;
        new Slider(cp5, "hsb_s")
                .setPosition(0,yPos)
                .setRange(0,360)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(hsbGroup)
        ;
        new Slider(cp5, "cmyk_m")
                .setPosition(0,yPos)
                .setRange(0,100)
                .setSize(camWidth - margin, sliderHeight)
                .setGroup(cmykGroup)
        ;

        new Slider(cp5, "RGB-g").setRange(0,255).setPosition(0,yPos).setGroup(rgbGroup);
        new Slider(cp5, "HSB-s").setRange(0,360).setPosition(camWidth,yPos).setGroup(hsbGroup);
        new Slider(cp5, "CMYK-m").setRange(0,100).setPosition(2*camWidth,yPos).setGroup(cmykGroup);
        yPos += sliderHeight;
        new Slider(cp5, "RGB-b").setRange(0,255).setPosition(0,yPos).setGroup(rgbGroup);
        new Slider(cp5, "HSB-b").setRange(0,360).setPosition(camWidth,yPos).setGroup(hsbGroup);
        new Slider(cp5, "CMYK-y").setRange(0,100).setPosition(2*camWidth,yPos).setGroup(cmykGroup);
        yPos += sliderHeight;
        new Slider(cp5, "CMYK-k").setRange(0,100).setPosition(2*camWidth,yPos).setGroup(cmykGroup);

        cp5.addGroup(rgbGroup, "RGB");
        cp5.addGroup(hsbGroup, "HSB");
        cp5.addGroup(cmykGroup, "CMYK");


        // img capture
        //camHeight = width / 3;
        cam = new Capture(this,camWidth,camHeight);
        cam.start();
    }

    public void draw() {
        if ( cam.available() ) {
            cam.read();
            cam.loadPixels();
            image(cam,0,0);
            image(cam,camWidth,0);
            image(cam,camWidth*2,0);
            cp5.draw();
        }
    }

    public void settings() {
        size(1920,1440);
    }





    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "com.boyamihungry.processing.ColorModeVideoExample"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}