package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp()
public class Robot extends OpMode
{
    private DcMotor left_front_motor;
    private DcMotor right_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_back_motor;
    private DcMotor intake_motor;

    @Override
    public void init()
    {
        left_front_motor =hardwareMap.get(DcMotor.class, "front_left_motor");
        right_front_motor =hardwareMap.get(DcMotor.class, "front_right_motor");
        left_back_motor =hardwareMap.get(DcMotor.class, "back_left_motor");
        right_back_motor =hardwareMap.get(DcMotor.class, "back_right_motor");
        intake_motor = hardwareMap.get(DcMotor.class, "intake_motor");

        right_front_motor.setDirection(DcMotor.Direction.REVERSE);
        right_back_motor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()
    {
        double px = gamepad1.left_stick_x;
        double py = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;
        double rt = gamepad1.right_trigger;

        left_front_motor.setPower(py - px - rx);
        right_front_motor.setPower(py + px + rx);
        left_back_motor.setPower(py + px - rx);
        right_back_motor.setPower(py - px + rx);

        if (rt > 0.5)
        {
            intake_motor.setPower(1);
        }
        else
        {
            intake_motor.setPower(0);
        }
        telemetry.addData("intake",intake_motor.getPower());
    }
}
