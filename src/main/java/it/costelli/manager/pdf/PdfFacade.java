package it.costelli.manager.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import it.costelli.manager.config.Conf;
import it.costelli.manager.model.FieldType;
import it.costelli.manager.model.PdfField;
import it.costelli.manager.util.StuffUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import xxx.joker.libs.javalibs.utils.JkConverter;
import xxx.joker.libs.javalibs.utils.JkFiles;
import xxx.joker.libs.javalibs.utils.JkStrings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xxx.joker.libs.javalibs.utils.JkConsole.displayln;
import static xxx.joker.libs.javalibs.utils.JkStrings.strf;

/**
 * Created by f.barbano on 17/05/2018.
 */
public class PdfFacade {

	private static Map<FieldType,PdfField> fieldMap;

	public static PdfField getFieldPosition(FieldType fieldType) throws IOException {
		if(fieldMap == null) {
			fieldMap = new HashMap<>();
			List<String> lines = Files.readAllLines(Conf.RESOURCE_FIELDS_POS);
			lines.removeIf(StringUtils::isBlank);
			for (String line : lines) {
				String[] split = JkStrings.splitAllFields(line, ";");
				Integer gnum = JkConverter.stringToInteger(split[0]);
				if (gnum != null) {
					FieldType ft = FieldType.getByGrossoNum(gnum);
					if (ft != null) {
						fieldMap.put(ft, new PdfField(split[1], split[2], split[3], split[4], split[5]));
					} else {
						displayln("File '%s' not found. Unable to read PDF fields positions.", Conf.RESOURCE_FIELDS_POS);
					}
				}
			}
		}

		return fieldMap.get(fieldType);
	}

	public static void updatePositions(Map<FieldType,PdfField> newPos) throws IOException {
		fieldMap = newPos;

		List<String> lines = fieldMap.entrySet().stream()
			.sorted(Comparator.comparing(e -> e.getKey().grossoNum()))
			.map(e -> strf("%d;%s;%s;%s;%s;%d",
				e.getKey().grossoNum(),
				StuffUtils.viewDouble(e.getValue().getX()),
				StuffUtils.viewDouble(e.getValue().getY()),
				StuffUtils.viewDouble(e.getValue().getEndX()),
				StuffUtils.viewDouble(e.getValue().getEndY()),
				e.getValue().getAlignment().getNum()
			)).collect(Collectors.toList());

		JkFiles.writeFile(Conf.RESOURCE_FIELDS_POS, lines, true);
		displayln("Updated field positions");
	}

	public static void fillPDFFields(Path outPath, PDFFont pdfFont, int fontSize, Map<FieldType,String> pdfData) throws IOException, DocumentException {
		PdfReader reader = null;
		PdfStamper stamper = null;

		try {
			reader = new PdfReader(Conf.RESOURCE_TEMPLATE_TEST_SHEET.toString());
			stamper = new PdfStamper(reader, new FileOutputStream(outPath.toFile()));

			BaseFont bf = BaseFont.createFont(pdfFont.label(), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			// Get first page
			PdfContentByte over = stamper.getOverContent(1);

			for(Map.Entry<FieldType,String> entry : pdfData.entrySet()) {
				PdfField fpos = getFieldPosition(entry.getKey());
				if(StringUtils.isNotBlank(entry.getValue())) {
					if(entry.getKey().isTextArea()) {
						writeWrappedText(over, bf, fontSize, fpos, entry.getValue());
					} else {
						writeInlineText(over, bf, fontSize, fpos, entry.getValue());
					}
				}
			}

			displayln(strf("Created new PDF test sheet at '%s'", outPath));

		} finally {
			if(stamper != null)	stamper.close();
			if(reader != null)	reader.close();
		}
	}
	public static void writOnPDF(Path outPath, PDFFont pdfFont, int fontSize, Pair<PdfField,String> pdfData) throws IOException, DocumentException {
		if(StringUtils.isBlank(pdfData.getValue())) {
			return;
		}

		PdfReader reader = null;
		PdfStamper stamper = null;

		try {
			reader = new PdfReader(Conf.RESOURCE_TEMPLATE_TEST_SHEET.toString());
			stamper = new PdfStamper(reader, new FileOutputStream(outPath.toFile()));

			BaseFont bf = BaseFont.createFont(pdfFont.label(), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			// Get first page
			PdfContentByte over = stamper.getOverContent(1);
			writeInlineText(over, bf, fontSize, pdfData.getKey(), pdfData.getValue());
			over.rectangle(pdfData.getKey().getX(), pdfData.getKey().getY(), pdfData.getKey().getWidth(), pdfData.getKey().getHeight());
			over.stroke();

			displayln("Created new PDF at '%s'", outPath);

		} finally {
			if(stamper != null)	stamper.close();
			if(reader != null)	reader.close();
		}
	}

	private static void writeInlineText(PdfContentByte over, BaseFont baseFont, int fontSize, PdfField pdfField, String text) {
		float x;
		if(pdfField.getAlignment() == PDFAlignment.RIGHT) {
			x = pdfField.getEndX();
		} else if(pdfField.getAlignment() == PDFAlignment.CENTER) {
			x = pdfField.getX() + pdfField.getWidth() / 2;
		} else {
			x = pdfField.getX();
		}

		over.beginText();
		over.setFontAndSize(baseFont, fontSize);
		over.showTextAligned(pdfField.getAlignment().getNum(), text, x, pdfField.getY(), 0);
		over.endText();
	}

	private static void writeWrappedText(PdfContentByte over, BaseFont baseFont, int fontSize, PdfField pdfField, String text) throws DocumentException {
		ColumnText ct = new ColumnText(over);
		Phrase phrase = new Phrase(text, new Font(baseFont, fontSize));
		ct.setSimpleColumn(phrase, pdfField.getX(), pdfField.getY(), pdfField.getEndX(), pdfField.getEndY(), fontSize, pdfField.getAlignment().getNum());
		ct.setText(phrase);
		ct.go();
	}

}
