// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.DifferentialDriveMod;
import frc.robot.sensors.RomiGyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private static final double kCountsPerRevolution = 1440.0;
  private static final double kWheelDiameterInch = 2.75591; // 70 mm
  public static double initialDistancePerPulse = (Math.PI * kWheelDiameterInch) / kCountsPerRevolution;

  // The Romi has the left and right motors set to
  // PWM channels 0 and 1 respectively
  private final Spark m_leftSpark = new Spark(0);
  private final Spark m_rightSpark = new Spark(1);

  // The Romi has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private final Encoder m_leftEncoder = new Encoder(4, 5);
  private final Encoder m_rightEncoder = new Encoder(6, 7);

  // Set up the differential drive controller
  // private final DifferentialDriveMod m_diffDriveMod = new
  // DifferentialDriveMod(m_leftSpark, m_rightSpark);
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_leftSpark, m_rightSpark);

  // Set up the RomiGyro
  private final RomiGyro m_gyro = new RomiGyro();

  private double currentAngle = 0;
  private double angleMod = 0;
  public double startingAngle; // = getGyroAngleZ();
  private double deadband = 0.20975;
  public static double speedMultiplier = 0.6;
  public static double speedMultiplierAuto = 1.0; // .8
  private boolean sideLeft;

  // Set up the BuiltInAccelerometer
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // Use inches as unit for encoder distances
    m_leftEncoder.setDistancePerPulse(initialDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(initialDistancePerPulse);
    resetEncoders();
  }

  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void tankDrive(double leftMotorSpeed, double rightMotorSpeed) {
    m_diffDrive.tankDrive(leftMotorSpeed, rightMotorSpeed);
  }

  public void arcadeDriveGyro(double xaxisSpeed, double zaxisRotate, double angle) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public double setStartingAngle() {
    return getGyroAngleZ();
  }

  public void tankDriveAuto(double leftMotorSpeed, double rightMotorSpeed, double startingAngle) {
    currentAngle = getGyroAngleZ();

    System.out.println("CHECKING FOR DRIFT " + startingAngle + " " + currentAngle);
    // double currentChange = currentAngle - lastAngle;

    if (currentAngle < (startingAngle - deadband)) {
      angleMod = 0.017;
      sideLeft = true;
    } else if (currentAngle > (startingAngle + deadband)) {
      angleMod = 0.017;
      sideLeft = false;
    } else {
      angleMod = 0;
    }
    if (sideLeft == true) {
      tankDrive((speedMultiplierAuto * leftMotorSpeed + angleMod), (speedMultiplierAuto * rightMotorSpeed));
    } else if (sideLeft == false) {
      tankDrive((speedMultiplierAuto * leftMotorSpeed), (speedMultiplierAuto * rightMotorSpeed + angleMod));
    }
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public int getLeftEncoderCount() {
    return m_leftEncoder.get();
  }

  public int getRightEncoderCount() {
    return m_rightEncoder.get();
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getDistance();
  }

  public double getAverageDistanceInch() {
    return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
  }

  /**
   * The acceleration in the X-axis.
   *
   * @return The acceleration of the Romi along the X-axis in Gs
   */
  public double getAccelX() {
    return m_accelerometer.getX();
  }

  /**
   * The acceleration in the Y-axis.
   *
   * @return The acceleration of the Romi along the Y-axis in Gs
   */
  public double getAccelY() {
    return m_accelerometer.getY();
  }

  /**
   * The acceleration in the Z-axis.
   *
   * @return The acceleration of the Romi along the Z-axis in Gs
   */
  public double getAccelZ() {
    return m_accelerometer.getZ();
  }

  /**
   * Current angle of the Romi around the X-axis.
   *
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleX() {
    return m_gyro.getAngleX();
  }

  /**
   * Current angle of the Romi around the Y-axis.
   *
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleY() {
    return m_gyro.getAngleY();
  }

  /**
   * Current angle of the Romi around the Z-axis.
   *
   * @return The current angle of the Romi in degrees
   */
  public double getGyroAngleZ() {
    return m_gyro.getAngleZ();
  }

  public double getGyroRateZ() {
    return m_gyro.getRateZ();
  }

  /** Reset the gyro. */
  public void resetGyro() {
    m_gyro.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
