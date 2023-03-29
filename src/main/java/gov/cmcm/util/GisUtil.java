package gov.cmcm.util;

import gov.cmcm.service.dto.PontosDTO;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.coords.TMCoord;
import gov.nasa.worldwind.geom.coords.UTMCoord;
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.Globe;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GisUtil {

    private static Logger logger = LoggerFactory.getLogger(GisUtil.class);
    static final String UTM_BAND_CHARS = "CDEFGHJKLMNPQRSTUVWXX";

    static final int MAXSIDES = 200;

    static class Corner {

        double x, y;
    }

    public double[][] coordendasToDouble(List<PontosDTO> coordenadas) {
        // coordenadas.size() -1 to ignore last record
        double[][] polygon = new double[coordenadas.size() - 1][coordenadas.size() - 1];
        for (int index = 0; index < polygon.length; index++) {
            polygon[index][0] = (coordenadas.get(index).getPointX()).doubleValue();
            polygon[index][1] = (coordenadas.get(index).getPointY()).doubleValue();
        }
        return polygon;
    }

    public double polyArea(List<PontosDTO> coordenadas) {
        Double area = polyArea(coordendasToDouble(coordenadas));
        return area;
    }

    public double polyArea(double polygon[][]) {
        Polygon p = new Polygon();
        p.n = polygon.length;
        // Taking the coordinates of each Corner
        for (int i = 0; i < p.n; i++) {
            p.p[i].x = polygon[i][0];
            p.p[i].y = polygon[i][1];
        }
        double area = area(p);
        if (area < 0) return -1 * area; else return area;
    }

    static class Polygon {

        Corner p[] = new Corner[MAXSIDES];
        int n;

        Polygon() {
            for (int i = 0; i < MAXSIDES; i++) p[i] = new Corner();
        }
    }

    // calculating area with Slicker Algorithm
    static double area(Polygon p) {
        double total = 0;
        for (int i = 0; i < p.n; i++) {
            int j = (i + 1) % p.n;
            total += (p.p[i].x * p.p[j].y) - (p.p[j].x * p.p[i].y);
        }
        return total / 2;
    }

    public Coordinate latLon(double x, Double y, int zone, char letter) {
        logger.debug("Parsing UTM coordinates X={} : Y={} : Zone={}, letter={}", x, y, zone, letter);
        return latLon(new Coordinate(x, y), zone, letter);
    }

    public Coordinate latLon(Coordinate ponto, int zone, char letter) {
        LatLon latlon = UTMCoord.locationFromUTMCoord(zone, AVKey.SOUTH, ponto.x, ponto.y, null);
        double lat = latlon.latitude.degrees;
        double lon = latlon.longitude.degrees;
        logger.debug("returning WGS lat:{} long:{}", lat, lon);
        return new Coordinate(lon, lat);
    }

    public Coordinate utmToGps(Coordinate coordinate, int zone) {
        MathTransform transform;
        Coordinate coordinateUTM = new Coordinate(coordinate.getX(), coordinate.getY());
        try {
            transform = CRS.findMathTransform(CRS.decode("EPSG:4326"), CRS.decode("EPSG:30" + zone), false);
            // coordinateUTM = JTS.transform(coordinate, coordinate, transform);

            for (String s : CRS.getSupportedCodes("EPSG")) {
                //    System.out.println("s is : " + s);
            }

            CRS.getSupportedCodes("EPSG");
        } catch (FactoryException e) {
            e.printStackTrace();
        }

        return coordinateUTM;
    }

    public Coordinate utmToGps(Double x, Double y, int zone) {
        return utmToGps(new Coordinate(x, y), zone);
    }

    public Coordinate utmToGps(Double x, Double y, String zone) {
        int value = Integer.parseInt(zone.replaceAll("[^0-9]", ""));
        return utmToGps(new Coordinate(x, y), value);
    }

    public String toString(List<PontosDTO> array) {
        String result = "";
        StringBuilder sb = new StringBuilder();

        for (PontosDTO coordenadasDTO : array) {
            String s = coordenadasDTO.getPointX() + " " + coordenadasDTO.getPointX();
            sb.append(s).append(",");
        }
        result = sb.deleteCharAt(sb.length() - 1).toString();
        return result;
    }

    public PontosDTO getPointByOrdem(int ordem, List<PontosDTO> c) {
        for (int i = 0; i < c.size(); i++) {
            logger.debug("procurando por {}: {} --{}", ordem, c.get(i).getOrdem(), c);
            if (c.get(i).getOrdem() == ordem + 1) { // logger.info("Encontrado!!!");
                return c.get(i);
            }
        }
        logger.error("nenhum dado encontrado");

        return null;
    }

    public org.locationtech.jts.geom.Polygon toPolygon(List<PontosDTO> c) throws IllegalAccessException {
        ArrayList<Coordinate> points = new ArrayList<Coordinate>();
        // Arrays.sort(c,Comparator.comparing(CoordenadasDTO::getOrdem));
        // CoordenadasDTO[] cDtos = new CoordenadasDTO[points.size()];

        for (int i = 0; i < c.size(); i++) {
            PontosDTO cs = new PontosDTO();
            cs = getPointByOrdem(i, c);

            points.add(
                latLon(
                    (cs.getPointX()).doubleValue(),
                    (cs.getPointY().doubleValue()),
                    Integer.parseInt(cs.getEpsg().substring(0, 2)),
                    cs.getEpsg().charAt(2)
                )
            );
        }
        /**
         * c.stream().forEach(ponto -> {
         * points.add(latLon(ponto.getPointX().doubleValue(),
         * ponto.getPointY().doubleValue(),
         * Integer.parseInt(ponto.getEpsg().substring(0, 2)),
         * ponto.getEpsg().charAt(2)));
         * });
         */
        org.locationtech.jts.geom.GeometryFactory gf = new org.locationtech.jts.geom.GeometryFactory();

        Coordinate[] coordenada = new Coordinate[points.size()];
        String parcela = c.get(0).getParcela();
        Long projectRduatId = c.get(0).getSpatialUnit().getId();
        for (int index = 0; index < coordenada.length; index++) coordenada[index] = points.get(index);

        try {
            org.locationtech.jts.geom.Polygon p = gf.createPolygon(coordenada);
            return p;
        } catch (IllegalArgumentException ex) {
            logger.error(
                "Erro ao converter polígono da parcela '{}', coordenadas {} - trace {},{}",
                parcela,
                coordenada,
                ex.getMessage(),
                ex.getLocalizedMessage()
            );

            printCoordenadas(c);
        }
        return null;
    }

    private void printCoordenadas(List<PontosDTO> cs) {
        for (int i = 0; i < cs.size(); i++) {
            logger.info("X:{} - Y:{} - ordem {}", cs.get(i).getPointX(), cs.get(i).getPointY(), cs.get(i).getOrdem());
        }
    }

    public static double[] toLatLng(double easting, double northing, char band, int zone) {
        int bandIdx = UTM_BAND_CHARS.indexOf(band);
        double lat = bandIdx * 8 - 76;
        double lng = ((zone - 1) * 6) - 177;
        double falseEasting = 500000.0;
        double falseNorthing = 10000000;
        double Scale = 0.9996;
        double latitude;
        double longitude;

        Globe myGlobe = new Earth();

        TMCoord TM = TMCoord.fromTM(
            easting,
            northing,
            myGlobe,
            Angle.fromRadians(lat),
            Angle.fromRadians(lng),
            falseEasting,
            10000000,
            Scale
        );
        latitude = TM.getLatitude().radians;
        longitude = TM.getLongitude().radians;

        // double centralMeridian = lng - Math.PI; // calculate central meridian in
        // radians
        // double scaleFactor = 1.0; // set the scale factor
        // Globe myGlobe = new Earth();

        // TMCoord TM = TMCoord.fromTM(easting, northing, myGlobe, Angle.ZERO,
        // centralMeridian, scaleFactor, falseEasting, falseNorthing);

        latitude = TM.getLatitude().radians;
        longitude = TM.getLongitude().radians;

        return new double[] { latitude, longitude };
    }
}
