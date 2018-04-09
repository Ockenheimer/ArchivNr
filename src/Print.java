
import java.awt.*;
import java.awt.print.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author advmz1
 */
public class Print {

    public static String ausgabetext;

    public void print(String text) {
        ausgabetext = text;

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new Printable() {

            public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Font font = new Font("Monospaced", Font.PLAIN, 20);

                JFrame f = new JFrame();

                Paper paper = new Paper();

                paper.setSize(263, 107);
                paper.setImageableArea(12, 6, 239, 95);

                pf.setPaper(paper);
                pf.setOrientation(1);
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());
                g.setFont(font);
                System.out.println(g.hashCode());

                g.drawString("hier könnte Ihre Werbung stehen", 40, 10);
                System.out.println(g.hashCode());
                //g.setFont(font);
                return Printable.PAGE_EXISTS;
            }
        });

        pj.setCopies(2);
        // pj.printDialog();

        try {
            pj.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static class Drucker1 implements Printable {

        Font font = new Font("Monospaced", Font.PLAIN, 20);

        @Override
        public int print(Graphics g, PageFormat pf, int pageIndex) {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            JFrame f = new JFrame();
            Paper paper = new Paper();

            paper.setSize(263, 107);
            paper.setImageableArea(12, 6, 239, 95);

            pf.setPaper(paper);
            pf.setOrientation(1);
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            g.setFont(font);
            System.out.println(g.hashCode());

            g.drawString("hier könnte Ihre Werbung stehen", 40, 10);
            System.out.println(g.hashCode());
            //g.setFont(font);
            return Printable.PAGE_EXISTS;
        }
    }

   
        public PrintService findPrintService(String printerName) {
            for (PrintService service : PrinterJob.lookupPrintServices()) {
                if (service.getName().equalsIgnoreCase(printerName)) {
                    return service;
                }
            }

            return null;
        }
    }
