package it.costelli.manager.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import it.costelli.manager.logger.LogService;
import it.costelli.manager.logger.SimpleLog;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.model.PdfField;
import it.costelli.manager.util.Converter;
import it.costelli.manager.util.FileUtils;
import it.costelli.manager.util.StrUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.costelli.manager.util.StrUtils.strf;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class PdfFacade {

	private static final SimpleLog logger = LogService.getLogger(PdfFacade.class);
	private static final String POSITIONS_RESOURCE = "/pdf/fieldsPositions.csv";

	private static Map<FieldType,PdfField> fieldMap;

	public static PdfField getTextMatrix(FieldType fieldType) throws IOException {
		if(fieldMap == null) {
			fieldMap = new HashMap<>();
			List<String> lines = FileUtils.readAllLines(PdfFacade.class.getResourceAsStream(POSITIONS_RESOURCE));
			lines.removeIf(StringUtils::isBlank);
			for (String line : lines) {
				String[] split = StrUtils.splitAllFields(line, ";");
				Integer gnum = Converter.stringToInteger(split[0]);
				if (gnum != null) {
					FieldType ft = FieldType.getByGrossoNum(gnum);
					if (ft != null) {
						Double x = Converter.stringToDouble(split[1]);
						Double y = Converter.stringToDouble(split[2]);
						Double endX = Converter.stringToDouble(split[3]);
						Double endY = Converter.stringToDouble(split[4]);
						fieldMap.put(ft, new PdfField(x, y, endX, endY));
					} else {
						logger.warning(strf("File '%s' not found. Unable to read PDF fields positions.", POSITIONS_RESOURCE));
					}
				}
			}
		}

		return fieldMap.get(fieldType);
	}

	public static void writePDF(Path pdfTemplate, Path outPath, PDFFont pdfFont, int fontSize, Map<FieldType,String> pdfOutList) throws IOException, DocumentException {
		PdfReader reader = null;
		PdfStamper stamper = null;

		try {
			reader = new PdfReader(pdfTemplate.toString());
			stamper = new PdfStamper(reader, new FileOutputStream(outPath.toFile()));

			BaseFont bf = BaseFont.createFont(pdfFont.label(), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			// Get first page
			PdfContentByte over = stamper.getOverContent(1);

			for(Map.Entry<FieldType,String> entry : pdfOutList.entrySet()) {
				if(StringUtils.isNotBlank(entry.getValue())) {
					PdfField matrix = getTextMatrix(entry.getKey());
					if(matrix != null) {
						over.beginText();
						over.setFontAndSize(bf, fontSize);
						over.setTextMatrix(matrix.getX(), matrix.getY());
						over.showText(entry.getValue());
						over.endText();
					}
				}
			}

			logger.info(strf("Created new PDF at '%s'", outPath));

		} finally {
			if(stamper != null)	stamper.close();
			if(reader != null)	reader.close();
		}
	}

}
