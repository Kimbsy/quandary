package com.kimbsy.quandary;

import com.kimbsy.sim.Sim;

/**
 * @author kimbsy
 */
public class QuandaryApp {

    public static void main(String[] args) {
        Sim quandarySim = new QuandarySim();
        quandarySim.setWindowSize(840, 840);
        quandarySim.setTitle("Quandary");
        quandarySim.setFrameDelay(50);

        quandarySim.init();
    }
}
