package gov.cmcm.service;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.PontosDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.springframework.stereotype.Component;

@Component
public class UploadService {

    public Optional<List<AlfaDTO>> readAlpha(String path) throws FileNotFoundException {
        System.out.println("UploadService.readAlpha():" + path);
        PoijiNumberFormat numberFormat = new PoijiNumberFormat();
        numberFormat.putNumberFormat((short) 47, "dd/mm/yyyy hh:mm");
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().caseInsensitive(true).poijiNumberFormat(numberFormat).build();
        Optional<List<AlfaDTO>> alfaDtos = Optional.empty();
        System.out.println(path.length());
        // path = "/home/justhyno/git/sigem-portal/res/projectos/Projecto banco
        // Mundial/receber/RMD_DUAT.xls";
        // System.out.println(path.length());

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        try {
            alfaDtos = Optional.ofNullable(Poiji.fromExcel(inputStream, PoijiExcelType.XLS, AlfaDTO.class, options));
        } catch (OLE2NotOfficeXmlFileException e) {
            alfaDtos = Optional.ofNullable(Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, AlfaDTO.class, options));
        }

        return alfaDtos;
    }

    public Optional<List<PontosDTO>> readGeo(String path) throws FileNotFoundException {
        PoijiNumberFormat numberFormat = new PoijiNumberFormat();
        numberFormat.putNumberFormat((short) 47, "dd/mm/yyyy hh:mm");
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().caseInsensitive(true).poijiNumberFormat(numberFormat).build();
        Optional<List<PontosDTO>> alfaDtos = Optional.empty();
        System.out.println(path.length());

        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        try {
            alfaDtos = Optional.ofNullable(Poiji.fromExcel(inputStream, PoijiExcelType.XLS, PontosDTO.class, options));
        } catch (OLE2NotOfficeXmlFileException e) {
            alfaDtos = Optional.ofNullable(Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, PontosDTO.class, options));
        }

        return alfaDtos;
    }
}
