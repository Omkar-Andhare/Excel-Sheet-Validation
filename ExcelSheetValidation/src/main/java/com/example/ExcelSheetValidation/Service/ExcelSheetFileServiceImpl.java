package com.example.ExcelSheetValidation.Service;

import com.example.ExcelSheetValidation.Repository.ExcelFileValidationConfigRepo;
import com.example.ExcelSheetValidation.Repository.ExcelSheetFileRepository;
import com.example.ExcelSheetValidation.controller.ExcelSheetFileController;
import com.example.ExcelSheetValidation.enums.FileStatus;
import com.example.ExcelSheetValidation.enums.FileType;
import com.example.ExcelSheetValidation.exceptions.InvalidExcelFileException;
import com.example.ExcelSheetValidation.model.ExcelFileValidationConfig;
import com.example.ExcelSheetValidation.model.ExcelSheetFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;


@Service
public class ExcelSheetFileServiceImpl implements IExcelSheetFileService {

    private static final Logger logger = LogManager.getLogger(ExcelSheetFileController.class);

    @Autowired
    private ExcelSheetFileRepository excelSheetFileRepository;

    @Autowired
    private ExcelFileValidationConfigRepo excelFileValidationConfigRepo;

    /**
     * Adds a comment to the specified cell in the given sheet.
     * @param sheet    The sheet to which the comment will be added.
     * @param cell     The cell to which the comment will be associated.
     * @param workbook The workbook to which the sheet belongs.
     */
    private static void addCommentToCell(Sheet sheet, Cell cell, Workbook workbook) {
        try {
            CreationHelper factory = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = factory.createClientAnchor();
            anchor.setCol1(cell.getColumnIndex());
            anchor.setCol2(cell.getColumnIndex() + 1);
            anchor.setRow1(cell.getRowIndex());
            anchor.setRow2(cell.getRowIndex() + 1);
            Comment comment = drawing.createCellComment(anchor);
            String commentText ;
            switch (cell.getColumnIndex()) {
                case 0:
                    commentText = "Name should be in alphabet only";
                    break;
                case 1:
                    commentText = "Phone no must be only 10 digits";
                    break;
                case 2:
                    commentText = "Email must end with '@gmail.com'";
                    break;
                default:
                    commentText = "Custom comment for column " + cell.getColumnIndex();
                    break;
            }
            comment.setString(factory.createRichTextString(commentText));
            cell.setCellComment(comment);
            logger.info("Comment added to cell: {}", cell.getAddress().formatAsString());
        } catch (Exception e) {
            logger.error("Error occurred while adding comment to cell: {}", cell.getAddress().formatAsString(), e);

        }

    }

    /**
     * Process the uploaded Excel file, validate it, and save metadata accordingly.
     * file     :The Excel file uploaded by the user.
     * fileType :The type of the Excel file (e.g., SCHOOL_STUDENT, etc.).
     * IOException :If an I/O error occurs.
     */
    @Override
    public void processExcelFile(MultipartFile file, FileType fileType) throws IOException, InvalidExcelFileException {
        // Check if the uploaded file is an Excel file
        if (!isExcelFile(file)) {
            throw new InvalidExcelFileException("Invalid file format. Please upload an Excel file.");
        }

        ExcelSheetFile excelSheetFile = new ExcelSheetFile();
        validateAndSaveExcelFile(file, fileType, excelSheetFile);
    }

    /**
     * Validates the uploaded Excel file and saves its metadata.
     *
     * @param file           The Excel file uploaded by the user.
     * @param fileType       The type of the Excel file (e.g., SCHOOL_STUDENT, etc.).
     * @param excelSheetFile The ExcelSheetFile object to save metadata.
     * @throws IOException If an I/O error occurs.
     */
    private void validateAndSaveExcelFile(MultipartFile file, FileType fileType, ExcelSheetFile excelSheetFile) throws IOException {
        logger.info("Validating and saving Excel file: {}", file.getOriginalFilename());

        boolean flag = validateExcelFile(file.getInputStream(), fileType);
        saveExcelFileMetadata(file, fileType, excelSheetFile, flag);
    }

    /**
     * Saves metadata of the Excel file based on validation result.
     *
     * @param file           The Excel file uploaded by the user.
     * @param fileType       The type of the Excel file (e.g., SCHOOL_STUDENT, etc.).
     * @param excelSheetFile The ExcelSheetFile object to save metadata.
     * @param flag           The validation result indicating whether the file is valid.
     */
    private void saveExcelFileMetadata(MultipartFile file, FileType fileType, ExcelSheetFile excelSheetFile, boolean flag) {

        excelSheetFile.setFileName(file.getOriginalFilename());
        excelSheetFile.setFileType(fileType);
        if (flag) {
            excelSheetFile.setStatus(FileStatus.UPLOADED);
            String filePath = "/home/perennial/ExcelSheets/valid Files" + file.getOriginalFilename();
            excelSheetFile.setFilePath(filePath);
        } else {
            excelSheetFile.setStatus(FileStatus.INVALID);
            String filePath = "/home/perennial/ExcelSheets/invalid files" + file.getOriginalFilename();
            excelSheetFile.setFilePath(filePath);
        }
        excelSheetFile.setDateTime(LocalDateTime.now());
        excelSheetFileRepository.save(excelSheetFile);
    }

    /**
     * Validates the uploaded Excel file against configured rules.
     *
     * @param fileInputStream The input stream of the uploaded Excel file.
     * @param fileType        The type of the Excel file (e.g., SCHOOL_STUDENT, etc.).
     * @return True if the file is valid, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public boolean validateExcelFile(InputStream fileInputStream, FileType fileType) throws IOException {
        logger.info("Validating Excel file for file type: {}", fileType);

//        List<ExcelFileValidationConfig> validationConfigs = excelFileValidationConfigRepo.findByFileType(String.valueOf(fileType));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        Sheet copySheet = workbook.cloneSheet(0);
        Row copyheaderRow = copySheet.getRow(0);


        boolean flag = validateHeadersAndData(copyheaderRow, workbook, fileType, copySheet);
        return flag;

    }

    // Create invalid cell style
    private CellStyle createInvalidCellStyle(Workbook workbook) {
        CellStyle invalidCellStyle = workbook.createCellStyle();
        invalidCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        invalidCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        return invalidCellStyle;
    }

    /**
     * Validates headers and data of the uploaded Excel file against configured rules.
     *
     * @param copyheaderRow The header row of the copied sheet.
     * @param workbook      The Workbook object representing the Excel file.
     * @param fileType      The type of the Excel file (e.g., SCHOOL_STUDENT, etc.).
     * @param copySheet     The copied sheet for validation.
     * @return True if the file is valid, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    private boolean validateHeadersAndData(Row copyheaderRow, Workbook workbook, FileType fileType, Sheet copySheet) throws IOException {

        List<ExcelFileValidationConfig> validationConfigs = excelFileValidationConfigRepo.findByFileType(String.valueOf(fileType));

        int count = 0;

        // Traverse through each row and column for adding comments
        for (Row row : copySheet) {
            for (Cell cell : row) {
                // Add comment to each cell
                addCommentToCell(copySheet, cell, workbook);
            }
        }

        // Check if the headers in the Excel file match the headers specified in the database
        Iterator<Cell> headerCellIterator = copyheaderRow.cellIterator();
        while (headerCellIterator.hasNext()) {
            Cell headerCell = headerCellIterator.next();
            String headerName = headerCell.getStringCellValue();
            ExcelFileValidationConfig config = null;
            for (ExcelFileValidationConfig c : validationConfigs) {
                if (c.getHeader().equalsIgnoreCase(headerName)) {
                    config = c;
                    break;
                }
            }

            if (config == null) {

                logger.error("Header '{}' does not match configured headers for file type '{}'", headerName, fileType);

                // Handle case where header doesn't match any configured header
                throw new IllegalArgumentException("Header '" + headerName + "' does not match configured headers for file type '" + fileType + "'");
            }

            // Apply regex validation for each header's cell
            int columnIndex = headerCell.getColumnIndex();
            Iterator<Row> rowIterator = copySheet.iterator();
            rowIterator.next(); // Skip header row
            while (rowIterator.hasNext()) {
                Row dataRow = rowIterator.next();

                // Iterate over the cells of the row
                Cell dataCell = dataRow.getCell(columnIndex);

                // Check if the cell is null (empty cell)
                if (dataCell == null) {
                    // Skip validation for empty cells
                    continue;
                }

                String cellValue;
                if (dataCell.getCellType() == CellType.NUMERIC) {
                    // Convert numeric value(for Phone cells) to string
                    cellValue = String.valueOf((int) dataCell.getNumericCellValue());
                } else {
                    cellValue = dataCell.getStringCellValue();
                }

                CellStyle invalidCellStyle = createInvalidCellStyle(workbook);

                // Perform regex validation using config.getRegex() on cellValue
                if (!cellValue.matches(config.getRegex())) {
                    // Handle invalid cell value according to your requirements
//                    throw new IllegalArgumentException("Invalid value '" + cellValue + "' for header '" + headerName + "'");
                    dataCell.setCellStyle(invalidCellStyle);
                    // Save the copy workbook with highlighted cells to a specific path
                    String copyFilePath = "/home/perennial/ExcelSheets/invalid files/copyFile.xlsx";
                    FileOutputStream outputStream = new FileOutputStream(copyFilePath);
                    workbook.write(outputStream);
                    count++;
                } else {
                    String copyFilePath = "/home/perennial/ExcelSheets/valid Files/copyFile.xlsx";
                    FileOutputStream outputStream = new FileOutputStream(copyFilePath);
                    workbook.write(outputStream);
                }
            }
        }

        //this is for updating status if file like UPLOADED, INVALID
        //it counts the invalid cell's regex if it >0 then status will set INVALID
        if (count > 0) return false;
        else return true;


    }

    /**
     * Checks if the uploaded file is an Excel file (XLSX format).
     *
     * @param file The file to check.
     * @return True if the file is an Excel file, false otherwise.
     */
    private boolean isExcelFile(MultipartFile file) {
        // Get the file extension
        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.isEmpty()) {
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            // Check if the file extension is XLSX (Excel)
            return "xlsx".equalsIgnoreCase(fileExtension);
        }
        return false;
    }
}

