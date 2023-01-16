package com.example.legendofbounca;

public class GyroscopePhysicsAssistant extends PhysicsAssistant{
    private float thetaX, thetaY;

    @Override
    public void init(float X, float Y) {
        super.init(X, Y);
        thetaX = 0;
        thetaY = 0;
    }

    @Override
    public double calculateAx(double data, float dT) {
        double dThetaX = data * dT;
        thetaX += dThetaX;
        return 9.8 * Math.sin(thetaY);
    }

    @Override
    public double calculateAy(double data, float dT) {
        double dThetaY = data * dT;
        thetaY += dThetaY;
        return 9.8 * Math.sin(thetaX);
    }
}
