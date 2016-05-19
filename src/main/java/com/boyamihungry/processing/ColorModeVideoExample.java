package com.boyamihungry.processing;

import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Slider;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;


public class ColorModeVideoExample extends PApplet {

    Capture cam;
    PImage camFrame;
    ControlP5 cp5;

    int camHeight = 0;
    int camWidth = 0;

    public static final int SLIDER_HEIGHT=40;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;



    public int addControl(ControlGroup g, Slider slider) {
        return 9;
    }

    public interface sliderAdder {
        public int addToGroup(ControlGroup g, Slider s);
    }

    public void setup() {

        System.out.println("just making sure");

        //this.frameRate(30);

        PApplet app = (PApplet)this;

        camWidth = (int)(width / 3);
        camHeight = (int)(height / 3);


        // set up contol panel
        cp5 = new ControlP5(this);
        cp5.setAutoDraw(false);

        int margin = 40;

        Group rgbGroup = cp5.addGroup("rgbGroup")
                .setPosition(0,camHeight + margin)
                .setWidth(camWidth-margin)
                //.setHeight((SLIDER_HEIGHT + margin) * 3 + ( 2 * margin) )
                .activateEvent(true)
                .setBackgroundColor(color(0,100))
                .setBackgroundHeight(SLIDER_HEIGHT * 3 + ( 3 * margin) )
                .setLabel("RGB")
                ;

        Group hsbGroup = cp5.addGroup("hsbGroup")
                .setPosition(camWidth + margin, camHeight + margin)
                .setWidth(camWidth-margin)
                //.setHeight((SLIDER_HEIGHT + margin) * 3 + ( 2 * margin) )
                .activateEvent(true)
                .setBackgroundColor(color(66,80))
                .setBackgroundHeight((SLIDER_HEIGHT + margin) * 3 + ( 2 * margin) )
                .setLabel("HSB")
                ;

        Group cmykGroup = cp5.addGroup("cmykGroup")
                .setPosition(2 * (camWidth + margin), camHeight + margin)
                .setWidth(camWidth-margin)
                //.setHeight((SLIDER_HEIGHT + margin) * 4 + ( 2 * margin) )
                .activateEvent(true)
                .setBackgroundColor(color(66,80))
                .setBackgroundHeight((SLIDER_HEIGHT + margin) * 4 + ( 2 * margin) )
                .setLabel("CMYK")
                ;

        SliderToGroupAdder groupCreator = (sliderMaker, group, initY, sliderVars ) -> {

            int yPos = initY;

            for ( String var : sliderVars) {
                sliderMaker.createSlider(var, margin/2, yPos, camWidth - (2 * margin), SLIDER_HEIGHT, group);
                yPos += SLIDER_HEIGHT + margin;
            }
        };


        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    cp5.addSlider(sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,255)
                            .setSize(w,h)
                            .setGroup(group);
                    cp5.getController(sliderVar).getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                    cp5.getController(sliderVar).getCaptionLabel().align(ControlP5.RIGHT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                },
                rgbGroup,
                0,
                new String[] {"rgb_r","rgb_g","rgb_b"});

        groupCreator.addSlidersToGroup(
                (sliderVar, xPos, yPos, w, h, group) -> {
                    cp5.addSlider(sliderVar)
                            .setPosition(xPos,yPos)
                            .setRange(0,360)
                            .setSize(w,h)
                            .setGroup(group);
                    cp5.getController(sliderVar).getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                    cp5.getController(sliderVar).getCaptionLabel().align(ControlP5.RIGHT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                },
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
                    cp5.getController(sliderVar).getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                    cp5.getController(sliderVar).getCaptionLabel().align(ControlP5.RIGHT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
                },
                hsbGroup,
                SLIDER_HEIGHT + margin,
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
            background(0);
            image(cam,0,0);
            image(cam,camWidth,0);
            image(cam,camWidth*2,0);
            cp5.draw();
        }
    }

    public void settings() {
        size(WIDTH,HEIGHT);
    }

    @FunctionalInterface
    public interface SliderCreator {
        public void createSlider(String sliderVar, int xPos, int yPos, int width, int height, Group group);
    }

    @FunctionalInterface
    public interface SliderToGroupAdder {
        public void addSlidersToGroup(SliderCreator sliderCreator, Group group, int startAtY, String[] sliderVars );
    }

    public void rgb_r(int pow) {
        System.out.println(pow);
    }
    public void rgb_g(int pow) {
        System.out.println(pow);
    }
    public void rgb_b(int pow) {
        System.out.println(pow);
    }

    public void hsb_h(int pow) {
        System.out.println(pow);
    }
    public void hsb_s(int pow) {
        System.out.println(pow);
    }
    public void hsb_b(int pow) {
        System.out.println(pow);
    }

    public void cmyk_c(int pow) {
        System.out.println(pow);
    }
    public void cmyk_m(int pow) {
        System.out.println(pow);
    }
    public void cmyk_y(int pow) {
        System.out.println(pow);
    }
    public void cmyk_k(int pow) {
        System.out.println(pow);
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