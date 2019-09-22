package net.streets.web.common;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
public class ExportOptions {

    private PDFOptions pdfOptions;
    private ExcelOptions excelOptions;

    @PostConstruct
    public void init() {
        excelOptions = new ExcelOptions();
        excelOptions.setFacetFontStyle("BOLD");

        pdfOptions = new PDFOptions();
        pdfOptions.setCellFontSize("7");
        pdfOptions.setFacetFontSize("8");
        pdfOptions.setFacetFontStyle("BOLD");
    }

    public PDFOptions getPdfOptions() {
        return pdfOptions;
    }

    public void setPdfOptions(PDFOptions pdfOptions) {
        this.pdfOptions = pdfOptions;
    }

    public ExcelOptions getExcelOptions() {
        return excelOptions;
    }

    public void setExcelOptions(ExcelOptions excelOptions) {
        this.excelOptions = excelOptions;
    }
}
