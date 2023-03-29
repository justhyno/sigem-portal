package gov.cmcm.service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfType0Font;
import com.itextpdf.kernel.font.PdfType1Font;
import com.itextpdf.kernel.font.PdfType3Font;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.dto.TituloDTO;
import gov.cmcm.util.GisUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D;
//import com.itextpdf.layout.Canvas;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.JTS;
//import org.locationtech.proj4j.*;
//import org.locationtech.proj4j.util.CRSCache;
import org.geotools.referencing.CRS;
import org.geotools.referencing.operation.DefaultCoordinateOperationFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;

@Component
public class DuatExporter {

    private static Map<String, Image> imageCache = new HashMap<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void addCellMap(String label, String value, Table table) {
        if (value == null) {
            value = "-";
        }
        Text boldText = new Text(value);
        boldText.setBold();
        Cell cell1 = new Cell().add(new Paragraph(label)).setBorder(Border.NO_BORDER);
        cell1.setWidth(new UnitValue(UnitValue.POINT, 250));

        Cell cell2 = new Cell().add(new Paragraph().add(boldText));
        cell2.setWidth(new UnitValue(UnitValue.POINT, 300));
        cell2.setBorder(null); // Remove all borders
        cell2.setBorderBottom(new SolidBorder(1)); // Set only the bottom border with a width of 1 user unit (point)

        table.addCell(cell1);
        table.addCell(cell2);
    }

    public static void page1(Document document, String nrProcesso) {
        document.add(new Paragraph("\n").setMarginBottom(0));

        Paragraph p1 = new Paragraph();
        Text boldText = new Text(nrProcesso);
        boldText.setBold();
        p1.add(new Tab()).add(boldText);
        p1.setTextAlignment(TextAlignment.CENTER);
        document.add(p1);

        document.add(
            new Paragraph("1")
                .setFixedPosition(1, 500, 15, 20)
                .setFontSize(8)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.RIGHT)
        );
        document.add(new Paragraph("\n").setMarginBottom(0));

        document.add(new AreaBreak());
        insertEmptyLines(33, document);

        document.add(
            new Paragraph()
                .add(new Tab())
                .add(new Tab())
                .add(new Tab())
                .add(new Tab())
                .add(nrProcesso)
                .setTextAlignment(TextAlignment.CENTER)
        );
        document.add(new AreaBreak());

        document.add(
            new Paragraph("4")
                .setFixedPosition(2, 500, 15, 20)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Tab())
                .add(new Tab())
        );

        document.add(
            new Paragraph("2")
                .setFixedPosition(3, 500, 15, 20)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Tab())
                .add(new Tab())
        );

        document.add(
            new Paragraph("3")
                .setFixedPosition(4, 500, 15, 20)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Tab())
                .add(new Tab())
        );
    }

    public static void page2(
        Document document,
        String nrProcesso,
        String despacho,
        String identificacao,
        Table table,
        Table table2,
        String localizacao,
        String boldText,
        TituloDTO titulo
    ) throws IOException {
        GisUtil gisUtil = new GisUtil();
        Paragraph p1 = new Paragraph();
        // PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA,
        // PdfEncodings.IDENTITY_H, true);

        Text pro = new Text(titulo.getProcesso());
        pro.setBold();
        p1.add(pro).setTextAlignment(TextAlignment.CENTER);
        document.add(p1);
        document.add(new Paragraph("\n").setMarginBottom(0));

        Paragraph p3 = new Paragraph();
        p3.add(boldText);

        p3.setTextAlignment(TextAlignment.CENTER);
        document.add(p3);
        Paragraph p4 = new Paragraph();
        Text des = new Text(despacho);
        p4.add(des);
        document.add(p4);
        document.add(new Paragraph("\n").setMarginBottom(0));
        // lets write identificacao de titulares
        document.add(new Paragraph(identificacao).setBold());

        LineSeparator bottomLine = new LineSeparator(new SolidLine());

        addCellMap("NOME COMPLETO", titulo.getNomeCompleto(), table);

        addCellMap("DOCUMENTO DE IDENTIFICAÇÃO", titulo.getDocumentoIdentificacao(), table);
        addCellMap("DATA DE NASCIMENTO", titulo.getDataNascimento(), table);
        addCellMap("NACIONALIDADE", "MOÇAMNBICANA", table);
        addCellMap("ESTADO CIVIL", titulo.getEstadoCivil(), table);
        addCellMap("DISTRITO MUNICIPAL", titulo.getDistritoMunicipal(), table2);

        addCellMap("BAIRRO:", titulo.getBairro(), table2);
        addCellMap("NÚMERO DA PARCELA:", titulo.getProcesso(), table2);
        addCellMap("SUPERFICIE DA PARCELA:", gisUtil.polyArea(titulo.getPontos()) + " m2", table2);
        addCellMap("ÄVENIDA/RUA", titulo.getAvenida(), table2);
        addCellMap("FINALIDADE", titulo.getFinalidade(), table2);
        addCellMap("DATA EMISSÃO", titulo.getDataEmissao(), table2);
        addCellMap("VALIDADE", "Sem prazo de validade", table2);
        document.add(table);
        // line break
        document.add(new Paragraph("\n").setMarginBottom(0));

        // lets now add dados de localização

        document.add(new Paragraph(localizacao).setBold());

        document.add(table2);
        String textoCentralizado = "Maputo aos 20 de Março de 2023";
        Paragraph p2 = new Paragraph();
        p2.add(textoCentralizado);
        p2.setTextAlignment(TextAlignment.CENTER);
        document.add(new Paragraph("\n").setMarginBottom(0));
        document.add(new Paragraph("\n").setMarginBottom(0));
        document.add(new Paragraph("\n").setMarginBottom(0));
        document.add(p2);
        String textoEntidade = "O presidente";
        // document.add(new AreaBreak());
        Paragraph pSigna = new Paragraph();
        pSigna.add(textoEntidade);
        pSigna.setTextAlignment(TextAlignment.CENTER);
        document.add(pSigna);
        document.add(new Paragraph("\n").setMarginBottom(0));
        document.add(new Paragraph("________________________________").setTextAlignment(TextAlignment.CENTER));
    }

    public static void page3(
        Document document,
        String nrProcesso,
        double latitude,
        double longitude,
        int zoom,
        int width,
        int height,
        TituloDTO titulo
    ) throws NoSuchAuthorityCodeException, FactoryException {
        document.add(new AreaBreak());
        // ... (existing code for page 3) ...

        Paragraph p1 = new Paragraph();
        Text pro = new Text(nrProcesso);
        pro.setBold();
        p1.add(pro);
        p1.setTextAlignment(TextAlignment.CENTER);
        document.add(p1);

        String norte = "NORTE";
        String sul = "   SUL";
        String este = "ESTE";
        String oeste = "   OESTE";

        List<String> utmCoordinates = pontosString(titulo.getPontos());

        try {
            // Retrieve the OpenStreetMap image
            // Image mapImage = getOpenStreetMapImage(latitude, longitude, zoom, width,
            // height);

            Image mapImage = getOpenStreetMapImageWithPolygon(utmCoordinates, zoom, width, height);
            // Image mapImage = new Image(mapImageData);
            mapImage.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Table table_ = new Table(1);
            Table tableCoordinates = new Table(4);
            table_.setHorizontalAlignment(HorizontalAlignment.CENTER);

            Cell c1 = new Cell().add(new Paragraph(norte));
            c1.setBorder(null); // Remove all borders
            Cell cell2 = new Cell().add(new Paragraph().add("vizinho norte"));
            removeBorder(cell2, true);
            Cell c3 = new Cell().add(new Paragraph(sul));
            removeBorder(c3, false);
            Cell c4 = new Cell().add(new Paragraph().add("vizinho sul"));
            removeBorder(c4, true);
            Cell c5 = new Cell().add(new Paragraph(este));
            removeBorder(c5, false);
            Cell c6 = new Cell().add(new Paragraph().add("vizinho este"));
            removeBorder(c6, true);
            Cell c7 = new Cell().add(new Paragraph(oeste));
            removeBorder(c7, false);
            Cell c8 = new Cell().add(new Paragraph().add("vizinho oeste"));
            removeBorder(c8, true);

            tableCoordinates.addCell(c1);
            tableCoordinates.addCell(cell2);
            tableCoordinates.addCell(c3);
            tableCoordinates.addCell(c4);
            tableCoordinates.addCell(c5);
            tableCoordinates.addCell(c6);
            tableCoordinates.addCell(c7);
            tableCoordinates.addCell(c8);

            Cell imageCell = new Cell().add(mapImage).setTextAlignment(TextAlignment.CENTER);
            table_.addCell(imageCell);
            // insertEmptyLines(1, document);
            document.add(table_); // Draw the image
            insertEmptyLines(1, document);
            document.add(tableCoordinates);

            table_.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER);
            table_.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);

            double[][] polygonCoordinates = new double[][] {
                { 37.422673, -122.083560 },
                { 37.421223, -122.084359 },
                { 37.420609, -122.082710 },
                { 37.422049, -122.081930 },
            };

            // Convert the polygon coordinates to pixel coordinates
            List<Point> polygonPoints = new ArrayList<>();
            for (double[] coords : polygonCoordinates) {
                Point point = getPixelCoordinates(coords[0], coords[1], latitude, longitude, zoom, width, height);
                polygonPoints.add(point);
            }

            double metersPerPixel = 156543.03392 * Math.cos(latitude * Math.PI / 180) / Math.pow(2, zoom);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Table table = new Table(4); // 4 columns
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        insertEmptyLines(2, document);
        DeviceRgb colorTableHeader = new DeviceRgb(188, 204, 228);
        // First row

        Cell cell = new Cell(2, 1).add(new Paragraph("Pontos")).setBackgroundColor(colorTableHeader);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(cell);
        Cell celw = new Cell(1, 2).add(new Paragraph("Coordenadas UTM")).setBackgroundColor(colorTableHeader);
        celw.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(celw);
        Cell marcos = new Cell().add(new Paragraph("Marcos")).setBackgroundColor(colorTableHeader);
        table.addCell(marcos);
        table.addCell(
            new Cell().add(new Paragraph("X")).setBackgroundColor(colorTableHeader).setHorizontalAlignment(HorizontalAlignment.CENTER)
        );
        table.addCell(
            new Cell().add(new Paragraph("Y")).setBackgroundColor(colorTableHeader).setHorizontalAlignment(HorizontalAlignment.CENTER)
        );
        table.addCell(
            new Cell()
                .add(new Paragraph("Descricao dos vertices das parcelas"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBackgroundColor(colorTableHeader)
        );

        List<PontosDTO> coordinates = titulo.getPontos();

        for (int i = 0; i < coordinates.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(i + 1 + ""))).setBackgroundColor(new DeviceRgb(255, 255, 255));
            table
                .addCell(new Cell().add(new Paragraph(coordinates.get(i).getPointX() + "")))
                .setBackgroundColor(new DeviceRgb(255, 255, 255));
            table
                .addCell(new Cell().add(new Paragraph(coordinates.get(i).getPointY() + "")))
                .setBackgroundColor(new DeviceRgb(255, 255, 255));
            table.addCell(new Cell().add(new Paragraph(coordinates.get(i).getMarco()))).setBackgroundColor(new DeviceRgb(255, 255, 255));
        }

        document.add(table);
    }

    public static void page4(Document document) {
        document.add(new AreaBreak());
        document.add(new Paragraph("Page 4").setFixedPosition(4, 10, 10, 20).setFontSize(8).setTextAlignment(TextAlignment.RIGHT));
    }

    public static void init(String path, TituloDTO titulo)
        throws FileNotFoundException, MalformedURLException, NoSuchAuthorityCodeException, FactoryException {
        String dest = path + "/" + titulo.getNomeCompleto() + "ProcessoDuat_" + titulo.getProcesso() + ".pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        String despacho =
            "Por despacho de 06/02 de 2014 de sua Excia o Presidente do CMM, Dr. Eneas Comiche, nos termos do artigo 23 da L nº 19/97, de 01 de Outubro, o Direito de Uso e Aproveitamento de Terreno Urbano, foi atribuído ao:";
        String identificacao = "a) IDENTIFICAÇÃO DOS TITULARES";
        String localizacao = "b) LOCALIZADO EM";
        String nrProcesso = "0923837";

        Table table = new Table(2);
        // table.setWidth(100);
        table.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.LEFT);
        table.setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);

        Table table2 = new Table(2);
        // table.setWidth(100);
        table2.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.LEFT);
        table2.setTextAlignment(com.itextpdf.layout.property.TextAlignment.LEFT);
        // Page 3
        double latitude = -25.9573401;
        double longitude = 32.577703;

        int zoom = 18;
        int width = 550;
        int height = 500;

        page1(document, nrProcesso);
        // page4(document);
        try {
            page2(document, nrProcesso, despacho, identificacao, table, table2, localizacao, nrProcesso, titulo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        page3(document, nrProcesso, latitude, longitude, zoom, width, height, titulo);

        document.close();
    }

    public static Image getGoogleMapsImage(double latitude, double longitude, int zoom, int width, int height, String apiKey)
        throws IOException {
        int scale = 2;
        String center = latitude + "," + longitude;
        String url = String.format(
            "https://maps.googleapis.com/maps/api/staticmap?center=%s&zoom=%d&size=%dx%d&scale=%d&key=%s",
            center,
            zoom,
            width,
            height,
            scale,
            apiKey
        );
        System.out.println("URL: " + url);
        URL imageUrl = new URL(url);
        URLConnection connection = imageUrl.openConnection();
        connection.setRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
        );
        connection.setConnectTimeout(20000); // Set connection timeout to 20 seconds
        connection.setReadTimeout(10000); // Set read timeout to 10 seconds

        try (InputStream is = connection.getInputStream()) {
            ImageData imageData = ImageDataFactory.create(is.readAllBytes());
            // Create a PdfImageXObject with the image data
            PdfImageXObject imageXObject = new PdfImageXObject(imageData);
            // Create an Image object with the PdfImageXObject
            Image image = new Image(imageXObject);
            // Set the width of the image to half of the page width
            float halfPageWidth = (PageSize.A4.getWidth() / 1.50f);
            image.setWidth(halfPageWidth);
            // Set the height of the image proportionally to maintain aspect ratio
            float aspectRatio = (float) imageData.getWidth() / (float) imageData.getHeight();
            float halfPageHeight = halfPageWidth / aspectRatio;
            image.setHeight(halfPageHeight);
            image.scaleToFit(width, height);
            // Add a black border around the image
            image.setBorder(new SolidBorder(2));
            return image;
            // return new Image(imageData);
        } catch (IOException e) {
            System.err.println("Error retrieving image: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static Image getOpenStreetMapImage(double latitude, double longitude, int zoom, int width, int height) throws IOException {
        String cacheKey = String.format("%f_%f_%d_%d_%d", latitude, longitude, zoom, width, height);
        Image cachedImage = imageCache.get(cacheKey);
        if (cachedImage != null) {
            System.out.println("returning from cache");
            return cachedImage;
        }

        System.out.println("trying to get image");
        int[] tileXY = latLonToTileXY(latitude, longitude, zoom);
        String url = String.format("https://tile.openstreetmap.org/%d/%d/%d.png", zoom, tileXY[0], tileXY[1]);
        for (int i = 0; i < 3; i++) {
            URL imageUrl = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
            );
            connection.setConnectTimeout(10000); // Set connection timeout to 10 seconds
            connection.setReadTimeout(3000); // Set read timeout to 3 seconds
            System.err.println(url);

            try (InputStream is = connection.getInputStream()) {
                ImageData imageData = ImageDataFactory.create(is.readAllBytes());
                // Create a PdfImageXObject with the image data
                PdfImageXObject imageXObject = new PdfImageXObject(imageData);
                // Create an Image object with the PdfImageXObject
                Image image = new Image(imageXObject);
                // Set the width of the image to half of the page width
                float halfPageWidth = (PageSize.A4.getWidth() / 1.50f);
                image.setWidth(halfPageWidth);
                // Set the height of the image proportionally to maintain aspect ratio
                float aspectRatio = (float) imageData.getWidth() / (float) imageData.getHeight();
                float halfPageHeight = halfPageWidth / aspectRatio;
                image.setHeight(halfPageHeight);
                image.scaleToFit(width, height);
                // Add a black border around the image
                image.setBorder(new SolidBorder(2));
                imageCache.put(cacheKey, image);
                return image;
                // return new Image(imageData);
            } catch (IOException e) {
                System.err.println("Error retrieving image: " + e.getMessage());
                e.printStackTrace();
            }
        }

        throw new IOException("Failed to retrieve image after three attempts.");
    }

    public static int[] latLonToTileXY(double lat, double lon, int zoom) {
        int xTile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        int yTile = (int) Math.floor(
            (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom)
        );

        return new int[] { xTile, yTile };
    }

    public static Point getPixelCoordinates(double lat, double lon, double centerLat, double centerLon, int zoom, int width, int height) {
        double tileCount = Math.pow(2, zoom);
        double centerX = tileCount * ((centerLon + 180) / 360);
        double centerY =
            tileCount * (1 - (Math.log(Math.tan(Math.toRadians(centerLat)) + 1 / Math.cos(Math.toRadians(centerLat))) / Math.PI)) / 2;

        double x = tileCount * ((lon + 180) / 360);
        double y = tileCount * (1 - (Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI)) / 2;

        int pixelX = (int) ((x - centerX) * 256 + width / 2);
        int pixelY = (int) ((y - centerY) * 256 + height / 2);

        return new Point(pixelX, pixelY);
    }

    public static void removeBorder(Cell cell, boolean borderBottom) {
        cell.setBorder(null);
        if (borderBottom) {
            cell.setBorderBottom(new SolidBorder(1)); // Set only the bottom border with a width of 1 user unit (point)
            cell.setWidth(new UnitValue(UnitValue.POINT, 185));
        }
    }

    public static void insertEmptyLines(int lines, Document document) {
        for (int i = 1; i < lines; i++) {
            document.add(new Paragraph("\n").setMarginBottom(0));
        }
    }

    public static Image getOpenStreetMapImageWithPolygon(List<String> utmCoordinates, int zoom, int width, int height)
        throws IOException, NoSuchAuthorityCodeException, FactoryException {
        // Convert UTM coordinates to lat/long coordinates
        List<Point2D.Double> latLongCoordinates = convertUtmToLatLong(utmCoordinates);

        // Find the center of the polygon
        Point2D.Double center = findCenterOfPolygon(latLongCoordinates);

        // Retrieve the OSM image with the center of the polygon

        // Image image = getGoogleMapsImage(center.y, center.x, zoom, width, height);

        Image image = getOpenStreetMapImage(center.y, center.x, zoom, width, height);

        // Draw the polygon on the image
        drawPolygonOnImage(convertImageToBufferedImage(image), latLongCoordinates, zoom, center);
        // private static Image drawPolygonOnImage(BufferedImage image,
        // List<Point2D.Double> latLongCoordinates, int zoom, Point2D.Double center)
        // throws IOException {

        return image;
    }

    private static List<Point2D.Double> convertUtmToLatLong(List<String> utmCoordinates) {
        // ProjCoordinate coord = new ProjCoordinate();
        // ProjCoordinate latLonCoord = new ProjCoordinate();
        List<Point2D.Double> latLongCoordinates = new ArrayList<>();

        // org.opengis.referencing.crs.CoordinateReferenceSystem utmCRS = null;
        // org.opengis.referencing.crs.CoordinateReferenceSystem wgs84CRS = null;
        // try {
        // utmCRS = CRS.decode("EPSG:32736");
        // wgs84CRS = CRS.decode("EPSG:4326");
        // } catch (FactoryException e) {
        // e.printStackTrace();
        // }

        // MathTransform transform = null;
        // try {
        // transform = CRS.findMathTransform(utmCRS, wgs84CRS, true);
        // } catch (FactoryException e) {
        // e.printStackTrace();
        // }
        GisUtil gisUtil = new GisUtil();

        for (String utmCoordinate : utmCoordinates) {
            String[] parts = utmCoordinate.split(" ");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);

            int zone = 36;
            char letter = 'S';
            Coordinate c = gisUtil.latLon(x, y, zone, letter);

            // Coordinate c = //gisUtil.utmToGps(easting, northing, 36);
            latLongCoordinates.add(new Point2D.Double(c.x, c.y));
        }

        return latLongCoordinates;
    }

    private static List<Point2D.Double> transformCoordinates(List<Point2D.Double> coordinates, CoordinateTransform transform) {
        List<Point2D.Double> transformedCoordinates = new ArrayList<>();

        for (Point2D.Double coordinate : coordinates) {
            ProjCoordinate input = new ProjCoordinate(coordinate.x, coordinate.y);
            ProjCoordinate output = new ProjCoordinate();
            try {
                JTS.transform(new Coordinate(input.x, input.y), new Coordinate(output.x, output.y), (MathTransform) transform);
            } catch (TransformException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            transformedCoordinates.add(new Point2D.Double(output.x, output.y));
        }

        return transformedCoordinates;
    }

    private static Point2D.Double findCenterOfPolygon(List<Point2D.Double> latLongCoordinates) {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Point2D.Double point : latLongCoordinates) {
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
        }

        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;

        return new Point2D.Double(centerX, centerY);
    }

    private static Image drawPolygonOnImage(BufferedImage image, List<Point2D.Double> latLongCoordinates, int zoom, Point2D.Double center)
        throws IOException {
        // Create a Graphics2D object from the BufferedImage
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));

        // Draw the polygon
        Path2D.Double path = new Path2D.Double();
        for (int i = 0; i < latLongCoordinates.size(); i++) {
            Point2D.Double latLon = latLongCoordinates.get(i);
            int[] tileXY = latLonToTileXY(latLon.y, latLon.x, zoom);
            int[] centerTileXY = latLonToTileXY(center.y, center.x, zoom);
            int x = (tileXY[0] - centerTileXY[0]) * 256 + image.getWidth() / 2;
            int y = (tileXY[1] - centerTileXY[1]) * 256 + image.getHeight() / 2;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        g2d.draw(path);

        // Clean up the Graphics2D object
        g2d.dispose();

        // Create an Image object from the BufferedImage
        ByteArrayOutputStream osModified = new ByteArrayOutputStream();
        ImageIO.write(image, "png", osModified);
        ByteArrayInputStream isModified = new ByteArrayInputStream(osModified.toByteArray());
        ImageData imageData = ImageDataFactory.create(isModified.readAllBytes());
        PdfImageXObject imageXObject = new PdfImageXObject(imageData);
        Image modifiedImage = new Image(imageXObject);

        System.out.println("modified image: " + modifiedImage.hashCode());

        return modifiedImage;
    }

    private static Image drawPolygonOnImage1(Image image, List<Point2D.Double> latLongCoordinates, int zoom, Point2D.Double center)
        throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // ImageIO.write((BufferedImage) image.getBufferedImage(), "png", os);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BufferedImage bufferedImage = ImageIO.read(is);

        // Create a Graphics2D object from the BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));

        // Draw the polygon
        Path2D.Double path = new Path2D.Double();
        for (int i = 0; i < latLongCoordinates.size(); i++) {
            Point2D.Double latLon = latLongCoordinates.get(i);
            int[] tileXY = latLonToTileXY(latLon.y, latLon.x, zoom);
            int[] centerTileXY = latLonToTileXY(center.y, center.x, zoom);
            int x = (tileXY[0] - centerTileXY[0]) * 256 + bufferedImage.getWidth() / 2;
            int y = (tileXY[1] - centerTileXY[1]) * 256 + bufferedImage.getHeight() / 2;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        g2d.draw(path);

        // Clean up the Graphics2D object
        g2d.dispose();

        // Create an Image object from the BufferedImage
        ByteArrayOutputStream osModified = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", osModified);
        ByteArrayInputStream isModified = new ByteArrayInputStream(osModified.toByteArray());
        ImageData imageData = ImageDataFactory.create(isModified.readAllBytes());
        PdfImageXObject imageXObject = new PdfImageXObject(imageData);
        Image modifiedImage = new Image(imageXObject);

        return modifiedImage;
    }

    public static BufferedImage convertImageToBufferedImage(Image image) throws IOException {
        PdfXObject xObject = image.getXObject();
        PdfObject pdfObject = xObject.getPdfObject();
        if (pdfObject instanceof PdfStream) {
            PdfStream stream = (PdfStream) pdfObject;
            byte[] imageBytes = stream.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            return bufferedImage;
        }
        System.out.println("returning null");
        return null;
    }

    public static List<String> pontosString(List<PontosDTO> pontos) {
        return pontos.stream().map(p -> p.getPointX() + " " + p.getPointY()).collect(Collectors.toList());
    }
}
