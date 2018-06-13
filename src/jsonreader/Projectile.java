/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonreader;

/**
 *
 * @author guojef18
 */

//andrew said he may needed so I included this.
public class Projectile {
    
    private double P = 0;
    private double I = 0;
    private double D = 0;
    private double F = 0;
    private double maxIOutput = 0;
    private double maxError = 0;
    private double errorSum = 0;
    private double maxOutput = 0;
    private double minOutput = 0;
    private double setpoint = 0;
    private double lastActual = 0;
    private boolean firstRun = true;
    private boolean reversed = false;
    private double outputRampRate = 0;
    private double lastOutput = 0;
    private double Filter = 0;
    private double setpointRange = 40;

    public Projectile(double p, double i, double d) {
        P = p;
        I = i;
        D = d;
        checkSigns();
    }

    public double Output(double actual, double setpoint) {
        double o;
        double Po;
        double Io;
        double Do;
        double Fo;
        this.setpoint = setpoint;
        if (setpointRange != 0) {
            setpoint = constrain(setpoint, actual - setpointRange, actual + setpointRange);
        }
        double error = setpoint - actual;
        Fo = F * setpoint;
        Po = P * error;
        if (firstRun) {
            lastActual = actual;
            lastOutput = Po + Fo;
            firstRun = false;
        }
        Do = -D * (actual - lastActual);
        lastActual = actual;
        Io = I * errorSum;
        if (maxIOutput != 0) {
            Io = constrain(Io, -maxIOutput, maxIOutput);
        }
        o = Fo + Po + Io + Do;
        if (minOutput != maxOutput && !bounded(o, minOutput, maxOutput)) {
            errorSum = error;
        } else if (outputRampRate != 0 && !bounded(o, lastOutput - outputRampRate, lastOutput + outputRampRate)) {
            errorSum = error;
        } else if (maxIOutput != 0) {
            errorSum = constrain(errorSum + error, -maxError, maxError);
        } else {
            errorSum += error;
        }
        if (outputRampRate != 0) {
            o = constrain(o, lastOutput - outputRampRate, lastOutput + outputRampRate);
        }
        if (minOutput != maxOutput) {
            o = constrain(o, minOutput, maxOutput);
        }
        if (Filter != 0) {
            o = lastOutput * Filter + o * (1 - Filter);
        }
        lastOutput = o;
        return o;
    }

    public void setR(double range) {
        setpointRange = range;
    }

    private double constrain(double value, double min, double max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }

    private boolean bounded(double value, double min, double max) {
        return (min < value) && (value < max);
    }

    private void checkSigns() {
        if (reversed) {
            if (P > 0) {
                P *= -1;
            }
            if (I > 0) {
                I *= -1;
            }
            if (D > 0) {
                D *= -1;
            }
            if (F > 0) {
                F *= -1;
            }
        } else {
            if (P < 0) {
                P *= -1;
            }
            if (I < 0) {
                I *= -1;
            }
            if (D < 0) {
                D *= -1;
            }
            if (F < 0) {
                F *= -1;
            }
        }
    }

    public double getP() {
        return P;
    }

    public double getI() {
        return I;
    }

    public double getD() {
        return D;
    }

    public double getF() {
        return F;
    }
}
