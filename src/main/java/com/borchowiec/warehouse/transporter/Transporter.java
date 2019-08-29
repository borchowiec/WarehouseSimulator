package com.borchowiec.warehouse.transporter;

import com.borchowiec.warehouse.WarehouseModel;
import com.borchowiec.warehouse.interfaces.Clickable;
import com.borchowiec.warehouse.jobs.Job;
import com.borchowiec.warehouse.jobs.JobProducer;
import com.borchowiec.warehouse.shelves.Product;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.*;

/**
 * This class represents transporter, which is a sort of vehicle that do {@link Job jobs} such as exporting or
 * importing {@link Product products}.
 * @author Patryk Borchowiec
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Transporter implements ApplicationContextAware, Clickable {
    private Transporter transporter = this;

    private double x;
    private double y;
    private double rotation = 0;
    public final double SIZE;
    private final double SPEED;
    private final int DELAY;
    private String status;

    private static int nextID = 0;
    public final int ID;

    private Color BG_COLOR = new Color(217, 159, 46);
    private Color BORDER_COLOR = new Color(255, 199, 44);
    private Color DETAILS_COLOR = new Color(153, 1, 0);

    private WarehouseModel warehouseModel;
    private Job job;
    private ApplicationContext context;

    private Arm arm;

    private boolean isRunning = false;

    /**
     * Main constructor
     * @param transportersSize Size of transporter's edge.
     * @param speed Speed of transporter.
     * @param delay Delay of thread's loop.
     */
    public Transporter(@Value("${warehouse.transporter.size}") int transportersSize,
                       @Value("${warehouse.transporter.speed}") double speed,
                       @Value("${warehouse.transporter.delay}") int delay) {

        ID = nextID++;
        SIZE = transportersSize;
        SPEED = speed;
        DELAY = delay;
        arm = new Arm();
    }

    /**
     * This method starts thread loop. This loop is responsible for executing job and getting new one if previous
     * is finished.
     */
    public void start() {
        if (!isRunning) {
            job = JobProducer.getJob(this, warehouseModel);

            new Thread(() -> {
                while (true) {
                    boolean done = job.doJob();
                    if (done)
                        job = JobProducer.getJob(transporter, warehouseModel);
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Sets coordinate x of transporter.
     * @param x X coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return coordinate x of transporter.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets coordinate y of transporter.
     * @param y Y coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return coordinate y of transporter.
     */
    public double getY() {
        return y;
    }

    /**
     * @return center coordinate x of transporter.
     */
    public double getCenterX() {
        return x + SIZE / 2.0;
    }

    /**
     * @return center coordinate y of transporter.
     */
    public double getCenterY() {
        return y + SIZE / 2.0;
    }

    /**
     * This method draws the transporter at transporter's position.
     * @param g Graphic that draws transporter.
     */
    public void paint(Graphics2D g) {
        paint(g, x, y);
    }

    /**
     * This method draws the transporter at specific coordinates.
     * @param g Graphic that draws transporter.
     * @param aX X coordinate where the transporter will be drawn.
     * @param aY Y coordinate where the transporter will be drawn.
     */
    public void paint(Graphics2D g, double aX, double aY) {
        int BORDER_WIDTH = 4;

        Rectangle2D rect = new Rectangle2D.Double(aX, aY, SIZE, SIZE);
        Line2D line = new Line2D.Double(aX - 1, aY + 2, aX - 1, aY + SIZE - 3);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(rotation), rect.getCenterX(), rect.getCenterY());

        g.setColor(BORDER_COLOR);
        g.fill(tx.createTransformedShape(rect));

        g.setColor(BG_COLOR);
        rect.setRect(rect.getMinX() + BORDER_WIDTH / 2.0, rect.getMinY() + BORDER_WIDTH / 2.0,
                SIZE - BORDER_WIDTH, SIZE - BORDER_WIDTH);
        g.fill(tx.createTransformedShape(rect));

        g.setColor(DETAILS_COLOR);
        g.draw(tx.createTransformedShape(line));

        arm.paint(g, aX + SIZE / 2.0, aY + SIZE / 2.0);
    }

    /**
     * This method moves transporter to the destination. First, it tries to rotate transporter to the destination's
     * direction, then transporter goes to the destination. If destination is reached, returns true.
     * @param destX X coordinate of destination.
     * @param destY Y coordinate of destination.
     * @return True if destination was reached.
     */
    public boolean goTo(double destX, double destY) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
        double angle = Math.toDegrees(Math.atan2(rect.getCenterY() - destY, rect.getCenterX() - destX));
        double aX = rect.getCenterX() - destX;
        double aY = rect.getCenterY() - destY;
        double distance = Math.sqrt(aX * aX + aY * aY);

        //angle range
        if (rotation < -180)
            rotation += 360;
        else if (rotation > 180)
            rotation -= 360;

        //if distance is less than speed, set destination's coordinates
        if (distance <= SPEED) {
            x = destX - SIZE / 2.0;
            y = destY - SIZE / 2.0;
            return true;
        } //rotate
        else if (rotation != angle) {
            if (Math.abs(rotation - angle) <= SPEED)
                rotation = angle;
            else if (rotation < angle) {
                if (Math.abs(rotation - angle) < 180)
                    rotation += SPEED;
                else rotation -= SPEED;
            } else {
                if (Math.abs(rotation - angle) < 180)
                    rotation -= SPEED;
                else rotation += SPEED;
            }
        } //move
        else {
            //collision detection
            boolean collide = false;
            for (Transporter t : warehouseModel.TRANSPORTERS) {
                if (t != null && !t.equals(this) && getDetector().intersects(t.getRectangle())) {
                    collide = true;
                    break;
                }
            }

            //if doesn't collide, move
            if (!collide) {
                x -= Math.cos(Math.toRadians(rotation)) * SPEED;
                y -= Math.sin(Math.toRadians(rotation)) * SPEED;
            }
        }
        return false;
    }

    /**
     * @return Rectangle with size adn position of transporter.
     */
    private Rectangle2D getRectangle() {
        return new Rectangle2D.Double(x, y, SIZE, SIZE);
    }

    /**
     * Sets current warehouse model.
     * @param warehouseModel Current warehouse model.
     */
    public void setWarehouseModel(WarehouseModel warehouseModel) {
        this.warehouseModel = warehouseModel;
    }

    /**
     * This method changes length of arm. If the arm will has a given length, return true.
     * @param finalLength Desired arm length.
     * @return True, if arm will has a given length.
     */
    public boolean propoundTheArm(double finalLength) {
        return arm.propound(finalLength);
    }

    /**
     * This method attaches product to the arm. You can do it if you want to transport product.
     * @param product Product that will be attached to the arm.
     */
    public void attachToTheArm(Product product) {
        arm.attach(product);
    }

    /**
     * This method detaches product from the arm. You can do it if you doesn't want to transport product anymore and
     * e.g. leave it on the shelf.
     * @return Detached product.
     */
    public Product detachProduct() {
        return arm.detach();
    }

    /**
     * @return Current context.
     */
    public ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * This method creates shape of detector that is placed in front of transporter. You can see it if you set
     * <code>dev.isVisible</code> property on true in settings.properties file. This detector is using in detecting
     * other transporters to avoid collisions.
     * @return Shape of detector.
     */
    public Shape getDetector() {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), getCenterX(), getCenterY());
        return transform.createTransformedShape(new Rectangle2D.Double(x - SIZE, y, SIZE, SIZE));
    }

    @Override
    public boolean isClicked(Point2D clickPosition) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(rotation), rect.getCenterX(), rect.getCenterY());

        return rect.contains(clickPosition);
    }

    /**
     * @return Status of transporter. E.g. <code>Going to (10|10)</code>.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status of transporter. E.g. <code>Going to (10|10)</code>.
     * @param status Status of transporter.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Length of transporter's arm.
     */
    public double getArmLength() {
        return arm.getLength();
    }

    /**
     * @return Value of transporter's rotation.
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * @return Product that is transporting by transporter or null if transporter is empty.
     */
    public Product getProduct() {
        return arm.product;
    }

    /**
     * This inner-class is using to taking of, placing or holding {@link Product product}.
     */
    private class Arm {
        private double length = 0;
        private Color ARM_COLOR = new Color(0, 121, 153);
        private final double RADIUS = 3;
        private Product product;

        /**
         * This method paints transporter's arm.
         * @param g Graphic that draws arm.
         * @param centerX center x coordinate of transporter. There is the place where arm is attached to transporter.
         * @param centerY center y coordinate of transporter. There is the place where arm is attached to transporter.
         */
        void paint(Graphics2D g, double centerX, double centerY) {
            if (product != null)
                product.paint(g, centerX - product.SIZE / 2.0, centerY - product.SIZE / 2.0 + length, rotation);


            Line2D line = new Line2D.Double(centerX, centerY, centerX, centerY + length);
            Ellipse2D circle = new Ellipse2D.Double(centerX - RADIUS, centerY - RADIUS + length,
                    RADIUS*2, RADIUS*2);
            g.setColor(ARM_COLOR);
            g.draw(line);
            g.fill(circle);
        }

        /**
         * @return Length of arm.
         */
        private double getLength() {
            return length;
        }

        /**
         * This method changes length of arm. If the arm will has a given length, return true.
         * @param finalLength Desired arm length.
         * @return True, if arm will has a given length.
         */
        public boolean propound(double finalLength) {
            if (Math.abs(finalLength - length) <= SPEED) {
                length = finalLength;
                return true;
            }
            if (finalLength > length)
                length += SPEED;
            else
                length -= SPEED;
            return false;
        }

        /**
         * This method attaches product to the arm. You can do it if you want to transport product.
         * @param product Product that will be attached to the arm.
         */
        public void attach(Product product) {
            this.product = product;
        }

        /**
         * This method detaches product from the arm. You can do it if you doesn't want to transport product anymore and
         * e.g. leave it on the shelf.
         * @return Detached product.
         */
        public Product detach() {
            Product temp = product;
            product = null;
            return temp;
        }
    }
}