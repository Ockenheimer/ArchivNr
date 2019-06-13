/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.print.*;

public class JavaPrinter implements Printable {

    static String number;

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) {
            /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;

        //Schirftarten für Beschriftung
        Font font = new Font("SansSerif", Font.PLAIN, 55);
        Font font2 = new Font("SansSerif", Font.PLAIN, 8);

        //Übersetzung der PaperFormat-Beschriftung für g2d
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        //erste Zeile
        g2d.setFont(font2);
        g2d.drawString("Archiv Mainz                                                                         Archiv Mainz", 35, 15);
        /*
        Das die Leerzeichen nicht hübsch sind, ist mir auch klar.
        Leider machte der Drucker einen Glitch, wenn ich versuchte das draw String übereinander auszuführen.
        Nicht schön aber selten.
        
        Ich nenn es "Best Practice"
        
        */

        //zweite Zeile        
        g2d.setFont(font);
        //zweiter Int ist Abstand von oben (1.Zeile + Lücke)
        g2d.drawString(number, 10, 75);
        g2d.drawString(number, 300, 75);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void drucken(String s) {
        number = s;

        PrinterJob job = PrinterJob.getPrinterJob();

        job.setJobName("Archiv-Nr Druck");

        PageFormat pf = job.defaultPage();

        //Paper für Ausdruck
        Paper paper = new Paper();

        paper.setSize(105, 560);
        paper.setImageableArea(5, 70, paper.getWidth(), paper.getHeight());

        pf.setPaper(paper);

        //Eindrehen des Drucks auf Querformat
        pf.setOrientation(PageFormat.REVERSE_LANDSCAPE);
        job.validatePage(pf);
        //übergeben des Druckjobs an Drucker mit geändertem PageFormat
        job.setPrintable(this, pf);

        try {

            //Ausdruck starten
            job.print();

        } catch (PrinterException ex) {
            /* The job did not successfully complete */
        }
        // }
    }

}
