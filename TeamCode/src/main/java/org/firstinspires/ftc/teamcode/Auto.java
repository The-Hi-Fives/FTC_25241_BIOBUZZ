package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto 1", group = "BIOBUZZ")
public class Auto extends LinearOpMode {

    // Drivetrain motors
private DcMotor left_front_motor;
private DcMotor right_front_motor;
private DcMotor left_back_motor;
private DcMotor right_back_motor;

// Intake & Shooter hardware (Servos removed)
private DcMotor right_intake_motor;
private DcMotor left_intake_motor;
private DcMotor pollen_shooter;
private DcMotor nectar_shooter;
//
final long BACKUP_5_INCHES_MS = 300;
final long STRAFE_TO_LOADING_MS = 1400;
final long DRIVE_INTO_INTAKE_MS = 400;

@Override
public void runOpMode() {
// 1. Hardware Mapping
left_front_motor = hardwareMap.get(DcMotor.class, "left_front_motor");
right_front_motor = hardwareMap.get(DcMotor.class, "right_front_motor");
left_back_motor = hardwareMap.get(DcMotor.class, "left_back_motor");
right_back_motor = hardwareMap.get(DcMotor.class, "right_back_motor");

right_intake_motor = hardwareMap.get(DcMotor.class, "right_intake_motor");
left_intake_motor = hardwareMap.get(DcMotor.class, "left_intake_motor");
nectar_shooter = hardwareMap.get(DcMotor.class, "nectar_shooter");
pollen_shooter = hardwareMap.get(DcMotor.class, "pollen_shooter");

// 2. Motor Directions
right_back_motor.setDirection(DcMotor.Direction.REVERSE);
right_front_motor.setDirection(DcMotor.Direction.REVERSE);
right_intake_motor.setDirection(DcMotor.Direction.REVERSE);
pollen_shooter.setDirection(DcMotor.Direction.REVERSE);

// Brake mode helps stop immediately
left_front_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
right_front_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
left_back_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
right_back_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

telemetry.addData("Status", "Initialized and Ready (Auto 1)");
telemetry.update();

waitForStart();

if (opModeIsActive()) {

// -----------------------------------------------------------------
// STEP 1: Shoot Pre-Loaded Nectar (5 seconds)
// -----------------------------------------------------------------
telemetry.addData("Step", "1: Shooting Pre-load Nectar");
telemetry.update();

pollen_shooter.setPower(-1.0);
sleep(5000); // 5 seconds of shooting
pollen_shooter.setPower(0.0);

// -----------------------------------------------------------------
// STEP 2: Drive Backwards 5 Inches
// -----------------------------------------------------------------
telemetry.addData("Step", "2: Backing up 5 inches");
telemetry.update();

drive(0.3, 0.3, 0.3, 0.3);
sleep(BACKUP_5_INCHES_MS);
stopDrive();
sleep(150);


}
}

private void drive(double lf, double rf, double lb, double rb) {
left_front_motor.setPower(lf);
right_front_motor.setPower(rf);
left_back_motor.setPower(lb);
right_back_motor.setPower(rb);
}

private void stopDrive() {
drive(0, 0, 0, 0);
}
}
