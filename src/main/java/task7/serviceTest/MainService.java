package task7.serviceTest;

import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import task7.dto.MeterDto;
import task7.dto.MeterGroupDto;
import task7.dto.PostDataJson;
import task7.dto.mapper.DtoMapper;
import task7.dto.report.GroupReport;
import task7.dto.report.ReadingReport;
import task7.model.Meter;
import task7.model.MeterReading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final MeterService meterService;
    private final MeterReadingService meterReadingService;
    private final DtoMapper dtoMapper;

    public MeterReading postReadingFromJson(PostDataJson reportJson) throws Exception {
        Meter meter = meterService.findById(reportJson.getMeterId());

        if (meter == null) {
            throw new NullPointerException();
        } else if (!meter.getType().equals(reportJson.getType())
                    || !meter.getMeterGroup().getName().equals(reportJson.getMeterGroup())) {
            throw new Exception("Type and group do not match");
        }

        MeterReading reading = new MeterReading();
        reading.setMeter(meter);
        reading.setCurrentReading(reportJson.getCurrentReading());
        reading.setTime(reportJson.getTimeStamp());
        meterReadingService.save(reading);
        return reading;
    }

    //todo: test
    public ByteArrayResource groupRepostToExcelFile(List<GroupReport> groupReports) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int row = 0;
        int totalConsumption = 0;

//        Header
        Row headerRow = sheet.createRow(row);
        headerRow.createCell(0).setCellValue("Группа");
        headerRow.createCell(1).setCellValue("Min показание");
        headerRow.createCell(2).setCellValue("Max показание");
        headerRow.createCell(3).setCellValue("Расход");

        for (GroupReport groupReport : groupReports) {
//            Group name
            Row groupRow = sheet.createRow(++row);
            groupRow.createCell(0).setCellValue(groupReport.getMeterGroup().getName());
            sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 3));
//            Readings
            List<ReadingReport> readings = groupReport.getReadings();
            for (ReadingReport readingReport : readings) {
                Row readingRow = sheet.createRow(++row);
                MeterDto meterDto = readingReport.getMeter();
                readingRow.createCell(0).setCellValue(String.format("сч. %s (%s)", meterDto.getId(), meterDto.getType()));
                readingRow.createCell(1).setCellValue(readingReport.getMinReading());
                readingRow.createCell(2).setCellValue(readingReport.getMaxReading());
                readingRow.createCell(3).setCellValue(readingReport.getConsumption());
            }
//            Group total
            Row groupTotalRow = sheet.createRow(++row);
            groupTotalRow.createCell(0).setCellValue(String.format("Итого %s:", groupReport.getMeterGroup().getName()));
            groupTotalRow.createCell(3).setCellValue(groupReport.getConsumption());
            totalConsumption += groupReport.getConsumption();
            sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 2));
        }
//        Total
        Row totalRow = sheet.createRow(++row);
        totalRow.createCell(0).setCellValue("Всего:");
        totalRow.createCell(3).setCellValue(totalConsumption);
        sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 2));

//        Style
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(new CellRangeAddress(0, row, 0, 3),
             BorderStyle.THIN, BorderExtent.ALL);
        propertyTemplate.drawBorders(new CellRangeAddress(0, row, 0, 3),
             BorderStyle.MEDIUM, BorderExtent.OUTSIDE);
        propertyTemplate.applyBorders(sheet);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    //todo: test
    public List<ReadingReport> getReadingReports() {
        List<MeterReading> readings = meterReadingService.findMaxMinReadings();
        List<ReadingReport> readingReports = new ArrayList<>();

        readings.forEach(r -> {
            MeterDto meterDto = dtoMapper.toDTO(r.getMeter());
            ReadingReport readingReport = readingReports.stream().filter(rp -> rp.getMeter().equals(meterDto))
                 .findFirst().orElse(null);
            if (readingReport == null) {
                readingReport = new ReadingReport();
                readingReport.setMeter(meterDto);
                readingReport.setMaxReading(r.getCurrentReading());
                readingReports.add(readingReport);
            } else {
                int max = readingReport.getMaxReading();
                int reading = r.getCurrentReading();
                if (max < reading) {
                    readingReport.setMaxReading(reading);
                    readingReport.setMinReading(max);
                    readingReport.setConsumption(reading - max);
                } else {
                    readingReport.setMinReading(reading);
                    readingReport.setConsumption(max - reading);
                }
            }
        });

        readingReports.stream().filter(r -> r.getMinReading() == 0).forEach(r -> r.setConsumption(r.getMaxReading()));

        return readingReports;
    }

    //todo: test
    public List<GroupReport> getGroupReports(List<ReadingReport> readingReports) {
        List<GroupReport> groupReports = new ArrayList<>();

        readingReports.forEach(r -> {
            MeterGroupDto groupDto = r.getMeter().getMeterGroup();
            GroupReport groupReport = groupReports.stream().filter(gr -> gr.getMeterGroup().equals(groupDto))
                 .findFirst().orElse(null);
            if (groupReport == null) {
                groupReport = new GroupReport();
                groupReport.setMeterGroup(groupDto);
                groupReport.setReadings(List.of(r));
                groupReport.setConsumption(r.getConsumption());
                groupReports.add(groupReport);
            } else {
                List<ReadingReport> list = new ArrayList<>(groupReport.getReadings());
                list.add(r);
                groupReport.setReadings(list);
                groupReport.setConsumption(groupReport.getConsumption() + r.getConsumption());
            }
        });

        return groupReports;
    }

}
