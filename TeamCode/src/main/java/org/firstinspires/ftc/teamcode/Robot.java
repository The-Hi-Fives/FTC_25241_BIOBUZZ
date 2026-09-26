package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class Robot extends OpMode
{
    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
    private DcMotor right_intake_motor;
    private DcMotor left_intake_motor;
    private Servo nectar_gate_servo;
    private Servo pollen_gate_servo;

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

        right_front_motor.setDirection(DcMotor.Direction.REVERSE);
        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
        right_intake_motor.setDirection(DcMotor.Direction.REVERSE);
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


        left_front_motor.setPower(py - px - rx);
        right_front_motor.setPower(py + px + rx);
        left_back_motor.setPower(py + px - rx);
        right_back_motor.setPower(py - px + rx);

        if (rt > 0.5)
        {
            right_intake_motor.setPower(1);
            left_intake_motor.setPower(1);
        }
        else
        {
            right_intake_motor.setPower(0);
            left_intake_motor.setPower(0);
        }
        telemetry.addData("right intake",right_intake_motor.getPower());
        telemetry.addData("left intake",right_intake_motor.getPower());

        if (ng)
        {
            nectar_gate_servo.setPosition(0.3);
        }
        else
        {
            nectar_gate_servo.setPosition(0);
        }

        telemetry.addData("nectar gate",nectar_gate_servo.getPosition());

        if (pg)
        {
            pollen_gate_servo.setPosition(0.3);
        }
        else
        {
            pollen_gate_servo.setPosition(0);
        }

        telemetry.addData("pollen gate",pollen_gate_servo.getPosition());

    }
}
