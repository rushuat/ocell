package com.ua.rush.doc.ocell.document;

import static org.testng.Assert.assertEquals;

import com.ua.rush.doc.ocell.Report;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DocumentTest {

  private Report[] reports;

  @BeforeTest
  public void before() {
    reports = new Report[]{
        Report.builder()
            .id(0)
            .build(),
        Report.builder()
            .id(1)
            .userName("User")
            .dateOfBirth(new Date(123456789))
            .rate(0.2525)
            .isNew(true)
            .build()
    };
  }

  @Test
  public void shouldCreateAndLoadDocument() throws IOException {
    //WHEN
    byte[] documentData;
    try (Document document = new Document()) {
      document.addSheet(reports);
      document.addSheet("Sheet", reports);
      documentData = document.toBytes();
    }

    List<Report> sheetUnnamedReportDocuments;
    List<Report> sheetNamedReportDocuments;
    try (Document document = new Document()) {
      document.fromBytes(documentData);
      sheetUnnamedReportDocuments = document.getSheet(Report.class);
      sheetNamedReportDocuments = document.getSheet("Sheet", Report.class);
    }

    //THEN
    assertEquals(sheetUnnamedReportDocuments.size(), reports.length);
    assertEquals(sheetNamedReportDocuments.size(), reports.length);

    assertEquals(
        sheetUnnamedReportDocuments.get(0),
        Report.builder()
            .id(0)
            .userName("User Name")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .rate(0.1234)
            .isNew(false)
            .build()
    );
    assertEquals(sheetUnnamedReportDocuments.get(1), reports[1]);

    assertEquals(
        sheetNamedReportDocuments.get(0),
        Report.builder()
            .id(0)
            .userName("User Name")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .rate(0.1234)
            .isNew(false)
            .build()
    );
    assertEquals(sheetNamedReportDocuments.get(1), reports[1]);
  }

  @Test
  public void shouldCreateAndLoadEncryptedDocument() throws IOException {
    //WHEN
    byte[] documentData;
    String password = "password";
    try (Document document = new Document(password)) {
      document.addSheet(Collections.emptyList());
      document.addSheet(reports);
      document.addSheet("Sheet", reports);
      documentData = document.toBytes();
    }

    List<Object> sheetEmptyDocumentReports;
    List<Report> sheetUnnamedReportDocuments;
    List<Report> sheetNamedReportDocuments;
    try (Document document = new Document(password)) {
      document.fromBytes(documentData);
      sheetEmptyDocumentReports = document.getSheet(Object.class);
      sheetUnnamedReportDocuments = document.getSheet(Report.class);
      sheetNamedReportDocuments = document.getSheet("Sheet", Report.class);
    }

    //THEN
    assertEquals(sheetEmptyDocumentReports.size(), Collections.emptyList().size());

    assertEquals(sheetUnnamedReportDocuments.size(), reports.length);
    assertEquals(sheetNamedReportDocuments.size(), reports.length);

    assertEquals(
        sheetUnnamedReportDocuments.get(0),
        Report.builder()
            .id(0)
            .userName("User Name")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .rate(0.1234)
            .isNew(false)
            .build()
    );
    assertEquals(sheetUnnamedReportDocuments.get(1), reports[1]);

    assertEquals(
        sheetNamedReportDocuments.get(0),
        Report.builder()
            .id(0)
            .userName("User Name")
            .dateOfBirth(new GregorianCalendar(1991, Calendar.AUGUST, 24, 1, 2, 3).getTime())
            .rate(0.1234)
            .isNew(false)
            .build()
    );
    assertEquals(sheetNamedReportDocuments.get(1), reports[1]);
  }
}