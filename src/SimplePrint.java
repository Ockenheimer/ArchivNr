
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PageRanges;

public class SimplePrint implements Printable {

    private PrintService[] printService;
    private String text;

    public SimplePrint() {
        this.printService = PrinterJob.lookupPrintServices();
    }

    public void printString(String input) {

        this.text = input;

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new PageRanges(1, 1));
        aset.add(new Copies(2));

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        try {
            printJob.setPrintService(printService[0]);
            //index of installed printers on you system
            //not sure if default-printer is always '0'

            printJob.print(aset);
        } catch (PrinterException err) {
            System.err.println(err);
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
         
        Paper paper = new Paper();
        int heightInMm = 39;
        int widthInMm = 91;
     //   paper.setSize((Int)(heightInMm * 2,6), 263);
        paper.setImageableArea(6, 6, 101, 257);
        pf.setPaper(paper);
        pf.setOrientation(0);
        
        
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g.drawString(String.valueOf(this.text), 10, 10);

        //   pf.setOrientation(0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        return PAGE_EXISTS;
    }
}
