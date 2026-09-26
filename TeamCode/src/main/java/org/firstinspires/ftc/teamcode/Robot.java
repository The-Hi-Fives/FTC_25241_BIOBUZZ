package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp()
public class Robot extends OpMode
{
    private DcMotor left_front_motor;
    private DcMotor left_back_motor;
    private DcMotor right_front_motor;
    private DcMotor right_back_motor;

    @Override
    public void init()
    {
        left_front_motor = hardwareMap.get(DcMotor.class, "lfmotor");
        left_back_motor = hardwareMap.get(DcMotor.class, "lbmotor");
        right_front_motor = hardwareMap.get(DcMotor.class, "rfmotor");
        right_back_motor = hardwareMap.get(DcMotor.class, "rbmotor");

        left_front_motor.setDirection(DcMotor.Direction.REVERSE);
        left_back_motor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()
    {
        double mysuperfavoritenumber = gamepad1.left_stick_x;
        double py = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;


        left_front_motor.setPower(py - mysuperfavoritenumber - rx);
        left_back_motor.setPower(py + mysuperfavoritenumber - rx);
        right_front_motor.setPower(py + mysuperfavoritenumber + rx);
        right_back_motor.setPower(py - mysuperfavoritenumber + rx);

    }
}
