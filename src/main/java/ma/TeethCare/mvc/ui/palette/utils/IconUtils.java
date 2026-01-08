package ma.TeethCare.mvc.ui.palette.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class IconUtils {

    public enum IconType {
        MENU, CLOSE, SEARCH, PLUS, TRASH, EDIT, EYE,
        USER, USERS, SHIELD, ACTIVITY, PILL, FILE_WARNING, BUILDING, LOCK,
        HOME, PATIENT, STETHOSCOPE, CLIPBOARD, CALENDAR, EURO, FILE_TEXT, CHEVRON_RIGHT
    }

    private static final Map<String, ImageIcon> cache = new HashMap<>();

    public static Icon getIcon(IconType type, int size, Color color) {
        String key = type + "_" + size + "_" + color.getRGB();
        if (cache.containsKey(key)) return cache.get(key);

        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(Math.max(1.5f, size / 12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        float s = size;
        float pad = s * 0.15f; // Padding
        float w = s - 2 * pad;
        float h = s - 2 * pad;
        float x = pad;
        float y = pad;

        switch (type) {
            case MENU:
                g2.draw(new Line2D.Float(x, y + h * 0.2f, x + w, y + h * 0.2f));
                g2.draw(new Line2D.Float(x, y + h * 0.5f, x + w, y + h * 0.5f));
                g2.draw(new Line2D.Float(x, y + h * 0.8f, x + w, y + h * 0.8f));
                break;
            case CLOSE:
                g2.draw(new Line2D.Float(x, y, x + w, y + h));
                g2.draw(new Line2D.Float(x + w, y, x, y + h));
                break;
            case SEARCH:
                g2.draw(new Ellipse2D.Float(x, y, w * 0.7f, h * 0.7f));
                g2.draw(new Line2D.Float(x + w * 0.6f, y + h * 0.6f, x + w, y + h));
                break;
            case PLUS:
                g2.draw(new Line2D.Float(x, y + h / 2, x + w, y + h / 2));
                g2.draw(new Line2D.Float(x + w / 2, y, x + w / 2, y + h));
                break;
            case TRASH:
                g2.draw(new Line2D.Float(x, y, x + w, y)); // Lid
                g2.draw(new Line2D.Float(x + w * 0.2f, y, x + w * 0.2f, y + h)); // Body L
                g2.draw(new Line2D.Float(x + w * 0.8f, y, x + w * 0.8f, y + h)); // Body R
                g2.draw(new Line2D.Float(x + w * 0.2f, y + h, x + w * 0.8f, y + h)); // Bottom
                break;
            case EDIT:
                Path2D pEdit = new Path2D.Float();
                pEdit.moveTo(x + w * 0.6f, y);
                pEdit.lineTo(x + w, y + h * 0.4f);
                pEdit.lineTo(x + w * 0.4f, y + h);
                pEdit.lineTo(x, y + h);
                pEdit.lineTo(x, y + h * 0.6f);
                pEdit.closePath();
                g2.draw(pEdit);
                break;
            case EYE:
                g2.draw(new Ellipse2D.Float(x, y + h * 0.2f, w, h * 0.6f));
                g2.fill(new Ellipse2D.Float(x + w * 0.4f, y + h * 0.4f, w * 0.2f, h * 0.2f));
                break;
            case HOME:
                Path2D pHome = new Path2D.Float();
                pHome.moveTo(x, y+h*0.4f);
                pHome.lineTo(x+w/2, y);
                pHome.lineTo(x+w, y+h*0.4f);
                pHome.lineTo(x+w, y+h);
                pHome.lineTo(x+w*0.6f, y+h);
                pHome.lineTo(x+w*0.6f, y+h*0.6f);
                pHome.lineTo(x+w*0.4f, y+h*0.6f);
                pHome.lineTo(x+w*0.4f, y+h);
                pHome.lineTo(x, y+h);
                pHome.closePath();
                g2.draw(pHome);
                break;
            case USER:
                g2.draw(new Ellipse2D.Float(x+w*0.25f, y, w*0.5f, h*0.5f)); // Head
                g2.draw(new Arc2D.Float(x, y+h*0.5f, w, h, 0, 180, Arc2D.OPEN)); // Body
                break;
            case USERS:
                g2.draw(new Ellipse2D.Float(x+w*0.1f, y, w*0.4f, h*0.4f)); 
                g2.draw(new Arc2D.Float(x, y+h*0.4f, w*0.6f, h*0.6f, 0, 180, Arc2D.OPEN));
                g2.draw(new Ellipse2D.Float(x+w*0.5f, y, w*0.4f, h*0.4f));
                g2.draw(new Arc2D.Float(x+w*0.4f, y+h*0.4f, w*0.6f, h*0.6f, 0, 180, Arc2D.OPEN));
                break;
            case CLIPBOARD:
                g2.draw(new RoundRectangle2D.Float(x+w*0.1f, y, w*0.8f, h, 2, 2));
                g2.fill(new Rectangle2D.Float(x+w*0.3f, y, w*0.4f, h*0.15f));
                g2.drawLine((int)(x+w*0.3f), (int)(y+h*0.4f), (int)(x+w*0.7f), (int)(y+h*0.4f));
                g2.drawLine((int)(x+w*0.3f), (int)(y+h*0.6f), (int)(x+w*0.7f), (int)(y+h*0.6f));
                break;
            case STETHOSCOPE:
                g2.draw(new Arc2D.Float(x, y, w*0.4f, h*0.5f, 0, 180, Arc2D.OPEN)); // Left ear
                g2.draw(new Arc2D.Float(x+w*0.6f, y, w*0.4f, h*0.5f, 0, 180, Arc2D.OPEN)); // Right ear
                g2.draw(new Arc2D.Float(x+w*0.1f, y+h*0.2f, w*0.8f, h*0.6f, 180, 180, Arc2D.OPEN)); // Tube U
                g2.fill(new Ellipse2D.Float(x+w*0.4f, y+h*0.8f, w*0.2f, h*0.2f)); // Bell
                break;
            case PILL:
                g2.rotate(Math.toRadians(45), x+w/2, y+h/2);
                g2.draw(new RoundRectangle2D.Float(x, y+h*0.3f, w, h*0.4f, h*0.4f, h*0.4f));
                g2.drawLine((int)(x+w/2), (int)(y+h*0.3f), (int)(x+w/2), (int)(y+h*0.7f));
                g2.rotate(-Math.toRadians(45), x+w/2, y+h/2);
                break;
            case CALENDAR:
                g2.draw(new RoundRectangle2D.Float(x, y+h*0.1f, w, h*0.9f, 2, 2));
                g2.drawLine((int)x, (int)(y+h*0.3f), (int)(x+w), (int)(y+h*0.3f));
                g2.drawLine((int)(x+w*0.3f), (int)y, (int)(x+w*0.3f), (int)(y+h*0.2f)); // loop 1
                g2.drawLine((int)(x+w*0.7f), (int)y, (int)(x+w*0.7f), (int)(y+h*0.2f)); // loop 2
                break;
            case FILE_WARNING:
                Path2D pFile = new Path2D.Float();
                pFile.moveTo(x, y);
                pFile.lineTo(x+w*0.7f, y);
                pFile.lineTo(x+w, y+h*0.3f);
                pFile.lineTo(x+w, y+h);
                pFile.lineTo(x, y+h);
                pFile.closePath();
                g2.draw(pFile);
                g2.drawLine((int)(x+w/2), (int)(y+h*0.4f), (int)(x+w/2), (int)(y+h*0.7f)); // !
                g2.fillOval((int)(x+w/2)-1, (int)(y+h*0.8f), 2, 2);
                break;
            case FILE_TEXT:
                Path2D pTx = new Path2D.Float();
                pTx.moveTo(x, y);
                pTx.lineTo(x+w*0.7f, y);
                pTx.lineTo(x+w, y+h*0.3f);
                pTx.lineTo(x+w, y+h);
                pTx.lineTo(x, y+h);
                pTx.closePath();
                g2.draw(pTx);
                g2.drawLine((int)(x+w*0.3f), (int)(y+h*0.5f), (int)(x+w*0.7f), (int)(y+h*0.5f));
                g2.drawLine((int)(x+w*0.3f), (int)(y+h*0.7f), (int)(x+w*0.7f), (int)(y+h*0.7f));
                break;
            case BUILDING: // Financial
                g2.draw(new Rectangle2D.Float(x+w*0.1f, y+h*0.2f, w*0.8f, h*0.8f));
                Path2D pRoof = new Path2D.Float();
                pRoof.moveTo(x+w*0.1f, y+h*0.2f); 
                pRoof.lineTo(x+w*0.5f, y); 
                pRoof.lineTo(x+w*0.9f, y+h*0.2f);
                g2.draw(pRoof);
                g2.draw(new Rectangle2D.Float(x+w*0.3f, y+h*0.5f, w*0.15f, h*0.2f)); // Window 1
                g2.draw(new Rectangle2D.Float(x+w*0.55f, y+h*0.5f, w*0.15f, h*0.2f)); // Window 2
                break;
            case EURO:
                g2.draw(new Arc2D.Float(x, y, w*0.8f, h, 45, 270, Arc2D.OPEN));
                g2.drawLine((int)x, (int)(y+h*0.4f), (int)(x+w*0.6f), (int)(y+h*0.4f));
                g2.drawLine((int)x, (int)(y+h*0.6f), (int)(x+w*0.6f), (int)(y+h*0.6f));
                break;
            case SHIELD:
                Path2D pShield = new Path2D.Float();
                pShield.moveTo(x+w/2, y);
                pShield.lineTo(x+w, y+h*0.2f);
                pShield.curveTo(x+w, y+h*0.6f, x+w/2, y+h, x+w/2, y+h);
                pShield.curveTo(x+w/2, y+h, x, y+h*0.6f, x, y+h*0.2f);
                pShield.closePath();
                g2.draw(pShield);
                break;
            case LOCK:
                 g2.draw(new RoundRectangle2D.Float(x+w*0.2f, y+h*0.4f, w*0.6f, h*0.6f, 2, 2));
                 g2.draw(new Arc2D.Float(x+w*0.3f, y, w*0.4f, h*0.5f, 0, 180, Arc2D.OPEN));
                 break;
            case ACTIVITY:
                 Path2D pAct = new Path2D.Float();
                 pAct.moveTo(x, y+h/2);
                 pAct.lineTo(x+w*0.2f, y+h/2);
                 pAct.lineTo(x+w*0.35f, y+h*0.1f);
                 pAct.lineTo(x+w*0.65f, y+h*0.9f);
                 pAct.lineTo(x+w*0.8f, y+h/2);
                 pAct.lineTo(x+w, y+h/2);
                 g2.draw(pAct);
                 break;
            case CHEVRON_RIGHT:
                g2.draw(new Line2D.Float(x + w * 0.3f, y, x + w * 0.7f, y + h / 2));
                g2.draw(new Line2D.Float(x + w * 0.7f, y + h / 2, x + w * 0.3f, y + h));
                break;
            default:
                g2.draw(new Rectangle2D.Float(x, y, w, h));
                g2.drawLine((int)x, (int)y, (int)(x+w), (int)(y+h));
                g2.drawLine((int)(x+w), (int)y, (int)x, (int)(y+h));
                break;
        }

        g2.dispose();
        ImageIcon icon = new ImageIcon(img);
        cache.put(key, icon);
        return icon;
    }
}
