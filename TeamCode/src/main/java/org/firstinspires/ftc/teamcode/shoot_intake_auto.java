package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "shoot intake auto", group = "Autonomous")
public class shoot_intake_auto extends LinearOpMode {

    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
    private DcMotor left_intake_motor;
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
        left_intake_motor = hardwareMap.get(DcMotor.class, "left_intake_motor");
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

            // --- STEP 1: OPEN POLLEN GATE & SHOOT (5 Seconds) ---
            pollen_gate_servo.setPower(0.2); // Open pollen gate
            pollen_shooter.setPower(1.0);
            nectar_shooter.setPower(-1.0);
            sleep(5000);
            // Stop shooters and close gate
            pollen_shooter.setPower(0.0);
            nectar_shooter.setPower(0.0);
            pollen_gate_servo.setPower(0.0);
            sleep(300);

            // --- STEP 2: DRIVE BACKWARDS 8 INCHES ---
            // Positive power drives backward on your configuration
            setMecanumPower(0.3, 0.0, 0.0);
            sleep(750); // Tune sleep time for ~8 inches
            stopDriving();
            sleep(200);

            // --- STEP 3: STRAFE LEFT FOR 2 SQUARES (48 inches) ---
            // Negative px strafes left
            setMecanumPower(0.0, -0.3, 0.0);
            sleep(3000); // Tune sleep time for 2 full tiles width
            stopDriving();
            sleep(200);

            // --- STEP 4: DRIVE FORWARD 8 INCHES WHILE INTAKING ---
            left_intake_motor.setPower(-1.0); // Intake ON
            pollen_gate_servo.setPower(0.2);  // Open gate slightly

            // Negative py drives forward
            setMecanumPower(-0.3, 0.0, 0.0);
            sleep(750); // Tune sleep time for ~8 inches

            // Stop intake and close gate
            left_intake_motor.setPower(0.0);
            pollen_gate_servo.setPower(0.0);
            stopDriving();
            sleep(200);

            // --- STEP 5: DRIVE BACKWARDS 8 INCHES ---
            setMecanumPower(0.3, 0.0, 0.0);
            sleep(750);
            stopDriving();
            sleep(200);

            // --- STEP 6: STRAFE RIGHT FOR 2 SQUARES (Return) ---
            // Positive px strafes right
            setMecanumPower(0.0, 0.3, 0.0);
            sleep(3000);
            stopDriving();
            sleep(200);

            // --- STEP 7: DRIVE FORWARD 8 INCHES ---
            setMecanumPower(-0.3, 0.0, 0.0);
            sleep(750);
            stopDriving();
            sleep(200);

            // --- STEP 8: SHOOT AGAIN (5 Seconds) ---
            pollen_gate_servo.setPower(0.2);
            pollen_shooter.setPower(1.0);
            nectar_shooter.setPower(-1.0);
            sleep(5000);
            pollen_shooter.setPower(0.0);
            nectar_shooter.setPower(0.0);
            pollen_gate_servo.setPower(0.0);
            sleep(200);

            // --- STEP 9: DRIVE FORWARD 3 INCHES ---
            setMecanumPower(-0.3, 0.0, 0.0);
            sleep(300); // Short burst for ~3 inches
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

