package gov.cmcm.service;

import com.poiji.bind.Poiji;
import com.poiji.bind.mapping.PoijiNumberFormat;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import gov.cmcm.service.dto.AlfaDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UploadService {

    public Optional<List<AlfaDTO>> readAlpha(String path) throws FileNotFoundException {
        PoijiNumberFormat numberFormat = new PoijiNumberFormat();
        numberFormat.putNumberFormat((short) 47, "dd/mm/yyyy hh:mm");
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().caseInsensitive(true).poijiNumberFormat(numberFormat).build();
        Optional<List<AlfaDTO>> alfaDtos = Optional.empty();

        File file = new File("path");
        InputStream inputStream = new FileInputStream(file);

        alfaDtos = Optional.ofNullable(Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, AlfaDTO.class, options));

        return alfaDtos;
    }
}
