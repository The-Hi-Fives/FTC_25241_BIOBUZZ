package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "park auto", group = "Autonomous")
public class park_auto extends LinearOpMode {

    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;

    @Override
    public void runOpMode() {
        left_front_motor  = hardwareMap.get(DcMotor.class, "left_front_motor");
        right_front_motor = hardwareMap.get(DcMotor.class, "right_front_motor");
        left_back_motor   = hardwareMap.get(DcMotor.class, "left_back_motor");
        right_back_motor  = hardwareMap.get(DcMotor.class, "right_back_motor");

        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
        right_front_motor.setDirection(DcMotor.Direction.REVERSE);

        // BRAKE mode is essential for short movements so the robot stops dead on the tape
        left_front_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Ready - Short Park");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Low power (25%) prevents wheel spin and keeps the stop crisp
            // Flip to -0.25 if it drives backward
            driveForward(0.25);

            // Start at 650ms (0.65 seconds) to crawl ~6-8 inches over the 1" tape.
            // Adjust by 50ms increments until your bumper/chassis sits cleanly past the tape.
            sleep(650);

            // Stop immediately
            driveForward(0.0);
        }
    }

    private void driveForward(double power) {
        left_front_motor.setPower(power);
        right_front_motor.setPower(power);
        left_back_motor.setPower(power);
        right_back_motor.setPower(power);
    }
}

