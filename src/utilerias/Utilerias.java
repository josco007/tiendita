/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Noe
 */
public class Utilerias {

    public static String toSha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static void showMessage(String pTitle, String pMensaje, int pTipoError) {

        JTextArea textArea = new JTextArea(pMensaje);
        textArea.setColumns(30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(textArea.getPreferredSize().width, 1);
        JOptionPane.showMessageDialog(
                null, textArea, pTitle, JOptionPane.WARNING_MESSAGE);

    }

    public static String emptyToNull(String pString) {

        if (pString.equalsIgnoreCase("")) {
            return null;
        }

        return pString;

    }

    public static boolean checkEmptyFieldStr(Object pObj, String pNombreCampo) throws Exception {

        if (pObj.getClass() == JTextField.class) {
            if (((JTextField) pObj).getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "El campo " + pNombreCampo + " no puede estar vacio", "Alerta", JOptionPane.INFORMATION_MESSAGE);
                ((JTextField) pObj).grabFocus();
                throw new Exception("campo " + pNombreCampo + " vacio");
            } else {
                return true;
            }
        } else if (pObj.getClass() == JPasswordField.class) {
            if (((JPasswordField) pObj).getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "El campo " + pNombreCampo + " no puede estar vacio", "Alerta", JOptionPane.INFORMATION_MESSAGE);
                ((JPasswordField) pObj).grabFocus();
                throw new Exception("campo " + pNombreCampo + " vacio");
            } else {
                return true;
            }
        }

        return false;

    }

    /**
     * Decode string to image
     *
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            System.out.println("caca" + bis);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            System.out.println("Exception decodeImage "+e);
        }
        return image;
    }

    /**
     * Encode image to string
     *
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (Exception e) {
            System.out.println("exception encodeToString "+e.getMessage());
        }
        return imageString;
    }

    //metodo que dada una cadena de bytes la convierte en una imagen con extension jpeg
    public static Image toImagen(byte[] bytes, String pTipoImg) {

        BufferedImage vlResultadoBie = null;
        try {

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Iterator readers = ImageIO.getImageReadersByFormatName(pTipoImg);
            ImageReader reader = (ImageReader) readers.next();
            Object source = bis; // File or InputStream

            ImageInputStream iis = ImageIO.createImageInputStream(source);

            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();

            vlResultadoBie = reader.read(0, param);

        } catch (IOException ex) {
            System.out.println("execpcion in toImagen");
        } catch (Exception e) {
            System.out.println("execpcion in toImagen");
        }

        return vlResultadoBie;
    }

    public static BufferedImage toBufferedImage(Image img) {

        BufferedImage bimage = null;

        try {
            if (img instanceof BufferedImage) {
                return (BufferedImage) img;
            }

            // Create a buffered image with transparency
            bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();


        } catch (Exception e) {
            System.out.println("exception "+e.getMessage());
        }

        // Return the buffered image
        return bimage;
    }
    
    public static void onlyFloatTextFieldEvent(KeyEvent e){
        
        char c = e.getKeyChar(); // Get the typed character  
            // Don't ignore backspace or delete  
            if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                // If the key was not a number then discard it (this is a sloppy way to check)  
                if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
                        || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.')) {
                    e.consume();  // Ignore this key  
                }
                else if(c == '.'){
                    //si es el puno verifica si ya hay punto
                    if(((JTextField)e.getComponent()).getText().contains(".")){
                        e.consume();
                    }
                }
            }
    }
    
    public static String emptyTo(String pStringCheck,String pStringReplace) {

        if (pStringCheck.equalsIgnoreCase("")) {
            return pStringReplace;
        }

        return pStringCheck;

    }
    
     public static String ifNull(String pStringCheck,String pStringReplace) {

        if (pStringCheck == null) {
            return pStringReplace;
        }

        return pStringCheck;

    }

    /**
     * regresa una cadena vacia si el string recivido es null
     *
     * @param pString
     * @return string
     */
    public static String nullToEmptyString(String pString) {
      
        if (pString == null) {
            return "";
        }
        
        return pString;
    }
    
}
