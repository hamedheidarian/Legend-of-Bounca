package com.example.legendofbounca;


public abstract class PhysicsAssistant {
    private double Vx0, preA, preA2;
    private double Vy0 = 0;
    private double ballMass = 0.01;
    private double staticFC = 0.15;
    private double kineticFC = 0.07;
    private float dX, dY;
    private float xPos, yPos, aX, aY;
    private int C = 150;

    public float getXs() {  return xPos; }
    public float getYs() {  return yPos; }

    public void init(float X, float Y) {
        Vx0 = 0;
        aX = 0;
        aY = 0;
        xPos = X;
        yPos = Y;
    }

    public void move(double axisXData, double axisYData, float dT, float screenHeight, float screenWidth, boolean jump_x, boolean jump_y) {
        //  F = m * a
        //  Fx = m * ax
        //  - xN * kineticFC + ax * m = a * m
        //  deltaX = 1/at^2 + v0xt

        double Ax = calculateAx(axisXData, dT);
        double Ay = calculateAy(axisYData, dT);


        double xN = ballMass*Ax;
        double yN = ballMass*Ay;

        if(Vx0 == 0) {
            if(Math.abs(ballMass * Ax) >= Math.abs(xN * staticFC)) {
                aX = (float) (((-1 * xN * staticFC) + xN) / ballMass);
                dX = (float) (aX * Math.pow(dT, 2) / 2 + Vx0 * dT)*C;
                Vx0 += ((aX * dT));
                xPos += dX;
            }
        }
        else {
            aX = (float) (((-1 * yN * kineticFC) + xN) / ballMass);
            dX = (float) (aX * Math.pow(dT, 2) / 2 + Vx0 * dT)*C;
            Vx0 += ((aX * dT));
            xPos += dX;
        }
        if(Vy0 == 0) {
            if (Math.abs(ballMass * Ay) >= Math.abs(yN * staticFC)) {
                aY = (float) (((-1 * yN * staticFC) + yN) / ballMass);
                dY = (float) (aY * Math.pow(dT, 2) / 2 + Vy0 * dT) * C;
                Vy0 += ((aY * dT));
                yPos += dY;
            }
        }
        else {
            aY = (float) (((-1 * xN * kineticFC) + yN) / ballMass);
            dY = (float) (aY * Math.pow(dT, 2) / 2 + Vy0 * dT)*C;
            Vy0 += ((aY * dT));
            yPos += dY;
        }

        double speedChange = -1* Math.pow(0.9, 2);

        if (xPos >= (screenWidth-100)){
            xPos = (screenWidth-100);
            Vx0 *= speedChange;
        }
        else if(xPos <= 0){
            xPos = 0;
            Vx0 *= speedChange;
        }

        if (yPos >= (screenHeight-200)){
            yPos = (screenHeight-200);
            Vy0 *= speedChange;
        }
        else if(yPos <= 0){
            yPos = 0;
            Vy0 *= speedChange;
        }
        if(jump_x == true){
            Vx0 = 10;
        }
        else if (jump_y == true){
            Vy0 = 10;
        }
    }

    public abstract double calculateAx(double data, float dT);
    public abstract double calculateAy(double data, float dT);
}
