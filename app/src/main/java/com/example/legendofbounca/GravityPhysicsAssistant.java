package com.example.legendofbounca;

public class GravityPhysicsAssistant extends  PhysicsAssistant{
    @Override
    public double calculateAx(double data, float dT) {
        return -1 * data;
    }
    @Override
    public double calculateAy(double data, float dT) {
        return data;
    }
}
