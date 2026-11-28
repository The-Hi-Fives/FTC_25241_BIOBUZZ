package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "shoot park auto", group = "Autonomous")
public class shoot_park_auto extends LinearOpMode {

    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
    private DcMotor pollen_shooter;
    private DcMotor nectar_shooter;
    private CRServo pollen_gate_servo;

    @Override
    public void runOpMode() {
        // Hardware Mapping
        left_front_motor  = hardwareMap.get(DcMotor.class, "left_front_motor");
        right_front_motor = hardwareMap.get(DcMotor.class, "right_front_motor");
        left_back_motor   = hardwareMap.get(DcMotor.class, "left_back_motor");
        right_back_motor  = hardwareMap.get(DcMotor.class, "right_back_motor");
        pollen_shooter    = hardwareMap.get(DcMotor.class, "pollen_shooter");
        nectar_shooter    = hardwareMap.get(DcMotor.class, "nectar_shooter");
        pollen_gate_servo = hardwareMap.get(CRServo.class, "pollen_servo");

        // Motor Directions
        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
        right_front_motor.setDirection(DcMotor.Direction.REVERSE);
        pollen_shooter.setDirection(DcMotor.Direction.REVERSE);

        // Active Braking
        setBrakeBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // --- STEP 1: OPEN POLLEN GATE & SHOOT ---
            pollen_gate_servo.setPower(0.2);
            sleep(200);
            pollen_gate_servo.setPower(0.0);

            pollen_shooter.setPower(1.0);
            nectar_shooter.setPower(-1.0);
            sleep(5000);

            pollen_shooter.setPower(0.0);
            nectar_shooter.setPower(0.0);
            sleep(200);

            // --- STEP 2: DRIVE STRAIGHT FORWARD 24 INCHES (1 TILE) ---
            // Negative py = forward. ~1300ms at 40% power covers ~24 inches.
            setMecanumPower(-0.4, 0.0, 0.0);
            sleep(1300);
            stopDriving();
            sleep(200);

            // --- STEP 3: DRIVE DIAGONALLY FORWARD-LEFT INTO PARK ---
            // py = -0.4 (forward) and px = -0.4 (left) moves at a 45-degree diagonal.
            // (On a Mecanum drive, diagonal movement runs only two opposing wheels,
            //  so it needs a longer timer to cross the remaining ~2 tiles diagonally.)
            setMecanumPower(-0.4, -0.4, 0.0);
            sleep(2800);
            stopDriving();
        }
    }

    private void setMecanumPower(double py, double px, double rx) {
        left_front_motor.setPower(py + px + rx);
        right_front_motor.setPower(py - px - rx);
        left_back_motor.setPower(py - px + rx);
        right_back_motor.setPower(py + px - rx);
    }

    private void stopDriving() {
        setMecanumPower(0.0, 0.0, 0.0);
    }

    private void setBrakeBehavior(DcMotor.ZeroPowerBehavior behavior) {
        left_front_motor.setZeroPowerBehavior(behavior);
        right_front_motor.setZeroPowerBehavior(behavior);
        left_back_motor.setZeroPowerBehavior(behavior);
        right_back_motor.setZeroPowerBehavior(behavior);
    }
}

