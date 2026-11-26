package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class limelight {
    private Limelight3A camera;

    // Constructor to initialize the hardware
    public limelight(HardwareMap hardwareMap) {
        camera = hardwareMap.get(Limelight3A.class, "limelight");
        camera.setPollRateHz(100);
        camera.start();
        camera.pipelineSwitch(0);
    }

    // Method to check if a valid target is seen
    public boolean hasTarget() {
        LLResult result = camera.getLatestResult();
        return result != null && result.isValid();
    }

    // Method to get horizontal offset (left/right angle to target)
    public double getTx() {
        LLResult result = camera.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx();
        }
        return 0.0;
    }

    // Method to get vertical offset (up/down angle to target)
    public double getTy() {
        LLResult result = camera.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTy();
        }
        return 0.0;
    }

    // Method to get target area (how big the target is on screen)
    public double getTa() {
        LLResult result = camera.getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTa();
        }
        return 0.0;
    }
}