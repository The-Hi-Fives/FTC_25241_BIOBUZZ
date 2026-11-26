package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.DistanceSensor;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp()
public class Robot_sensors extends OpMode
{
    private limelight vision;
    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
//    private DcMotor right_intake_motor;
    private DcMotor left_intake_motor;
    private CRServo nectar_gate_servo;
    private CRServo pollen_gate_servo;

    private DcMotor pollen_shooter;
    private DcMotor nectar_shooter;

    private boolean lastXState = false;
    private boolean nectarGateOpen = false;
    private ElapsedTime nectarTimer = new ElapsedTime();
    private boolean nectarMoving = false;

    private boolean lastYState = false;
    private boolean pollenGateOpen = false;
    private ElapsedTime pollenTimer = new ElapsedTime();
    private boolean pollenMoving = false;


    // sensors and camera
//    private DistanceSensor distanceSensor;
//    private Limelight3A limelight;



    @Override
    public void init()
    {
        vision = new limelight(hardwareMap);
        // tekking the robot what is plugged into what
        left_front_motor =hardwareMap.get(DcMotor.class, "left_front_motor");
        right_front_motor =hardwareMap.get(DcMotor.class, "right_front_motor");
        left_back_motor =hardwareMap.get(DcMotor.class, "left_back_motor");
        right_back_motor =hardwareMap.get(DcMotor.class, "right_back_motor");
//        right_intake_motor = hardwareMap.get(DcMotor.class, "right_intake_motor");
        left_intake_motor = hardwareMap.get(DcMotor.class, "left_intake_motor");
        nectar_gate_servo = hardwareMap.get(CRServo.class, "nectar_servo");
        pollen_gate_servo = hardwareMap.get(CRServo.class, "pollen_servo");
        nectar_shooter = hardwareMap.get(DcMotor.class, "nectar_shooter");
        pollen_shooter = hardwareMap.get(DcMotor.class, "pollen_shooter");

        // setting direction of motors
        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
        right_front_motor.setDirection(DcMotor.Direction.REVERSE);

//        right_intake_motor.setDirection(DcMotor.Direction.REVERSE);
        pollen_shooter.setDirection(DcMotor.Direction.REVERSE);

        //hardware map - distance sensor
//        distanceSensor = hardwareMap.get(DistanceSensor.class, "distance_sensor");


    }

    @Override
    public void loop()
    {
        // this is for driving
        double px = gamepad1.left_stick_x;
        double py = gamepad1.left_stick_y;
        double rx = -gamepad1.right_stick_x;
        // this is for intake and outtake
        double rt = gamepad2.right_trigger;
        double lt = gamepad2.left_trigger;
        //these are the servos
        boolean nectar_forward = gamepad2.x; // Hold X for Nectar forward/in
        boolean nectar_backward = gamepad2.a; // Hold A for Nectar backward/out
        boolean pollen_forward = gamepad2.y; // Hold Y for Pollen forward
        boolean pollen_backward = gamepad2.b; // Hold B for Pollen backward
        // these are the shooters
        boolean ps = gamepad2.left_bumper;
        boolean ns = gamepad2.right_bumper;

        // this is driving
        left_front_motor.setPower(py + px +rx);
        right_front_motor.setPower(py - px - rx);
        left_back_motor.setPower(py - px + rx);
        right_back_motor.setPower(py + px - rx);


        // this is for intaking
        if (rt > 0.5)
        {
//           right_intake_motor.setPower(-1);
           left_intake_motor.setPower(-1);
        }
        else
        {
//            right_intake_motor.setPower(0);
            left_intake_motor.setPower(0);
        }


        // this is for outake
        if (lt > 0.5)
        {
//            right_intake_motor.setDirection(DcMotor.Direction.REVERSE);
            left_intake_motor.setDirection(DcMotorSimple.Direction.FORWARD);
//            right_intake_motor.setPower(1);
            left_intake_motor.setPower(1);
        }
        else
        {
//            right_intake_motor.setPower(0);
            left_intake_motor.setPower(0);
        }

        //pollen gate
        boolean currentYState = gamepad2.y;
        if (currentYState && !lastYState) {
            pollenGateOpen = !pollenGateOpen;
            pollenTimer.reset();
            pollenMoving = true;
        }
        lastYState = currentYState;

        if (pollenMoving) {
            if (pollenTimer.seconds() < 0.5) {
                pollen_gate_servo.setPower(pollenGateOpen ? 0.5 : -0.5);
            } else {
                pollen_gate_servo.setPower(0.0);
                pollenMoving = false;
            }
        }

        //nectar gate
        boolean currentXState = gamepad2.x;
        if (currentXState && !lastXState) {
            nectarGateOpen = !nectarGateOpen;
            nectarTimer.reset();
            nectarMoving = true;
        }
        lastXState = currentXState;

        if (nectarMoving) {
            if (nectarTimer.seconds() < 0.5) {
                nectar_gate_servo.setPower(nectarGateOpen ? 0.5 : -0.5);
            } else {
                nectar_gate_servo.setPower(0.0);
                nectarMoving = false;
            }
        }




        // this is for the shooters
        pollen_shooter.setPower(ps ? 1.0 : 0.0);
        nectar_shooter.setPower(ns ? -1.0 : 0.0);

        // - DISTANCE SENSOR (LiDAR)
//        double distanceInches = distanceSensor.getDistance(DistanceUnit.INCH);
//        telemetry.addData("LiDAR Distance (in)", "%.2f", distanceInches);
        //limelight
        if (vision.hasTarget()) {
            double tx = vision.getTx();
            double ty = vision.getTy();
            double ta = vision.getTa();
            telemetry.addData("Limelight Target", "Found");
            telemetry.addData("TX (Horizontal)", "%.2f", tx);
            telemetry.addData("TY (Vertical)", "%.2f", ty);
            telemetry.addData("TA (Area)", "%.2f", ta);
            if (gamepad2.a) {
                telemetry.addData("Auto-Aim", "Active (Holding Gamepad 2 A)");
            }
        } else {
            telemetry.addData("Limelight Target", "Searching...");
        }
        telemetry.update();


    }
}
