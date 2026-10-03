package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;

// limelight imports
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp()
public class Robot_sensors extends OpMode
{
    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
    private DcMotor right_intake_motor;
    private DcMotor left_intake_motor;
    private Servo nectar_gate_servo;
    private Servo pollen_gate_servo;
    private DcMotor pollen_shooter;
    private DcMotor nectar_shooter;

    // sensors and camera
    private DistanceSensor distanceSensor;
    private Limelight3A limelight;



    @Override
    public void init()
    {
        left_front_motor =hardwareMap.get(DcMotor.class, "front_left_motor");
        right_front_motor =hardwareMap.get(DcMotor.class, "front_right_motor");
        left_back_motor =hardwareMap.get(DcMotor.class, "back_left_motor");
        right_back_motor =hardwareMap.get(DcMotor.class, "back_right_motor");
        right_intake_motor = hardwareMap.get(DcMotor.class, "right_intake_motor");
        left_intake_motor = hardwareMap.get(DcMotor.class, "left_intake_motor");
        nectar_gate_servo = hardwareMap.get(Servo.class, "nectar_gate_servo");
        pollen_gate_servo = hardwareMap.get(Servo.class, "pollen_gate_servo");
        pollen_shooter = hardwareMap.get(DcMotor.class, "pollen_shooter");
        nectar_shooter = hardwareMap.get(DcMotor.class, "nectar_shooter");

        right_front_motor.setDirection(DcMotor.Direction.REVERSE);
        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
        right_intake_motor.setDirection(DcMotor.Direction.REVERSE);

        //hardware map - distance sensor
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distance_sensor");

        // Hardware Map - Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
        //The zero needs to be set up for the April tags
    }

    @Override
    public void loop()
    {
        double px = gamepad1.left_stick_x;
        double py = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;
        double rt = gamepad1.right_trigger;
        boolean pg = gamepad1.a;
        boolean ng = gamepad1.b;
        boolean ps = gamepad1.left_bumper;
        boolean ns = gamepad1.right_bumper;

        left_front_motor.setPower(py - px - rx);
        right_front_motor.setPower(py + px + rx);
        left_back_motor.setPower(py + px - rx);
        right_back_motor.setPower(py - px + rx);

        if (rt > 0.5)
        {
           right_intake_motor.setPower(1);
           left_back_motor.setPower(1);
        }
        else
        {
            right_intake_motor.setPower(0);
            left_intake_motor.setPower(0);
        }

        nectar_gate_servo.setPosition(ng ? 0.3 : 0.0);
        pollen_gate_servo.setPosition(pg ? 0.3 : 0.0);

        pollen_shooter.setPower(ps ? 1.0 : 0.0);
        nectar_shooter.setPower(ns ? 1.0 : 0.0);

        // - DISTANCE SENSOR (LiDAR)
        double distanceInches = distanceSensor.getDistance(DistanceUnit.INCH);
        telemetry.addData("LiDAR Distance (in)", "%.2f", distanceInches);

        // - LIMELIGHT APRILTAG READINGS
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            telemetry.addData("Limelight Tx", result.getTx());
            telemetry.addData("Limelight Ty", result.getTy());

            for (FiducialResult fiducial : result.getFiducialResults()) {
                telemetry.addData("Detected Tag ID", fiducial.getFiducialId());
            }
        } else {
            telemetry.addData("Limelight", "No tag visible");
        }
        telemetry.update();
    }
}
