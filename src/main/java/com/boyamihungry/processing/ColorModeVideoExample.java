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
                .setPosition(2 * (camWidth + margin), camHeight + margin)
                .setWidth(camWidth - margin)
                .activateEvent(true)
                .setBackgroundColor(color(66,80))
                .setBackgroundHeight(100)
                .setLabel("CMYK")
                ;

        SliderToGroupAdder groupCreator = (sliderMaker, group, initY, sliderVars ) -> {

            int yPos = initY;

            for ( String var : sliderVars) {
                sliderMaker.createSlider(var, margin,yPos, camWidth - margin, sliderHeight, group);
                yPos += sliderHeight;
            }
        };


        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    new Slider(cp5, sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,255)
                            .setSize(w,h)
                            .setGroup(group);},
                rgbGroup,
                0,
                new String[] {"rgb-r","rgb_g","rgb_b"});

        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    new Slider(cp5, sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,360)
                            .setSize(w,h)
                            .setGroup(group);},
                hsbGroup,
                0,
                new String[] {"hsb_h"});

        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    new Slider(cp5, sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,100)
                            .setSize(w,h)
                            .setGroup(group);
                },
                hsbGroup,
                sliderHeight,
                new String[] {"hsb_s","hsb_b"});

        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    new Slider(cp5, sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,100)
                            .setSize(w,h)
                            .setGroup(group);
                },
                cmykGroup,
                0,
                new String[] {"cmyk_c","cmyk_m","cmyk_y", "cmyk_k"});



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


    @FunctionalInterface
    public interface SliderCreator {
        public void createSlider(String sliderVar, int xPos, int yPos, int width, int height, Group group);
    }

    @FunctionalInterface
    public interface SliderToGroupAdder {
        public void addSlidersToGroup(SliderCreator sliderCreator, Group group, int startAtY, String[] sliderVars );
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