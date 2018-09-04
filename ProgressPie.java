import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProgressPie {


    private int centreX = 50;
    private int centreY = 50;
    private int radius = 50;

    public static void main(String[] args) {

        ProgressPie progressPie = new ProgressPie();
        Scanner sc = null;

        if (args == null || args.length < 1) {
            System.out.println("Usage: java ProgressPie filename");
            return;
        }

        try {
            sc = new Scanner(new File(args[0]));
            String line;
            final int T;
            int caseCounter = 1;
            int curPercentage;
            Point curPoint;

            if (!sc.hasNextLine()) {                                    //No lines in file
                return;
            }
            line = sc.nextLine();
            T = Integer.parseInt(line);

            while (sc.hasNextLine() && caseCounter <= T) {
                line = sc.nextLine();
                String[] splitLine = line.split(" ");
                if (splitLine.length >= 3) {
                    curPercentage = Integer.parseInt(splitLine[0]);
                    curPoint = new Point(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
                    if (progressPie.pointWithinSector(curPoint, curPercentage)) {
                        System.out.println("Case #" + caseCounter + ": black");
                    } else {
                        System.out.println("Case #" + caseCounter + ": white");
                    }
                    caseCounter++;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * Gets the distance from the centre of the progress pie to the point
     * This is the hypotenuse used in angle calculations
     * @param point The point to get the distance of
     * @return The distance between centre of progress pie to point
     */

    private double getDistanceToPoint(Point point) {
        if (point.y == centreY) {
            return Math.abs(point.x - centreX);
        }
        if (point.x == centreX) {
            return Math.abs(point.y - centreY);
        }
        return Math.sqrt((centreX - point.x) * (centreX - point.x)  + (centreY - point.y) * (centreY - point.y));
    }

    /**
     * Determines whether or not the input point is within the given progress pie
     * This is determined by:
     *      Checking if the distance is less than the radius of the progress pie
     *      Checking if the angle of the point is clockwise from the start of the sector
     *      Checking if the angle of the point is counter-clockwise from the end of the sector
     * @param point The point we are checking
     * @param percentage The percentage of the progress pie that is complete
     * @return True if the progress pie contains the point
     *          False if otherwise
     */

    public boolean pointWithinSector(Point point, int percentage) {
        if (percentage > 0) {
            double distanceToPoint = getDistanceToPoint(point);
            if (distanceToPoint <= radius) {
                double pointAngle;
                if (point.y != centreY) {
                    pointAngle = Math.toDegrees(Math.atan2((point.x - centreX), (point.y - centreY)));
                } else {
                    if (point.x > centreX) {
                        pointAngle = 90;
                    } else if (point.x < centreX) {
                        pointAngle = 270;
                    } else {
                        pointAngle = 0;
                    }
                }
                if (pointAngle < 0) {
                    pointAngle += 360;
                }
                double endAngle = (360 * percentage) / 100;
                return pointAngle >= 0 && pointAngle <= endAngle;
            }
        }
        return false;
    }
}
