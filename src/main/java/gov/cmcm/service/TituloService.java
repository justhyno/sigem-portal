package gov.cmcm.service;

import gov.cmcm.domain.Alfa;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.FichaDTO;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.dto.ProjectoDTO;
import gov.cmcm.service.dto.TituloDTO;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.opengis.referencing.FactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TituloService {

    private final FichaService fichaService;
    private final PontosService pontosService;
    private final AlfaService alfaService;
    private final DuatExporter duatExporter;
    private final Logger log = LoggerFactory.getLogger(TituloService.class);

    public TituloService(FichaService fichaService, PontosService pontosService, AlfaService alfaService, DuatExporter duatExporter) {
        this.fichaService = fichaService;
        this.pontosService = pontosService;
        this.alfaService = alfaService;
        this.duatExporter = duatExporter;
    }

    public void fetchTitulo(Long projectID) {
        List<FichaDTO> fichas = fichaService.findByProject(projectID);
        List<TituloDTO> tituloDto = new ArrayList<>();

        if (fichas.size() == 0) {
            log.info("sem fichas disponiveis");
        } else {
            for (FichaDTO fichaDTO : fichas) {
                // get alfa from ficha
                Optional<AlfaDTO> alfa = alfaService.findByFicha(fichaDTO.getId());
                Optional<List<PontosDTO>> pontos = pontosService.findByFicha(fichaDTO.getId());
                if (alfa.isPresent() && pontos.isPresent()) {
                    log.info("a gerar titulos do processo {}", fichaDTO.getProcesso());
                    tituloDto.add(matchFichaTituloCoordenadas(fichaDTO, pontos.get(), alfa.get()));
                } else {
                    if (!alfa.isPresent()) {
                        log.info("Dados alfanumerico não encontrado com a processo {}", fichaDTO.getProcesso());
                    }
                    if (!pontos.isPresent()) {
                        log.info("Dados espaciais não encontrado com a processo {}", fichaDTO.getProcesso());
                    }
                }
            }

            for (TituloDTO titulo : tituloDto) {
                try {
                    duatExporter.init("res/projectos", titulo);
                } catch (FileNotFoundException | MalformedURLException | FactoryException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private String formatedDate(LocalDate data) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);

        return formattedDate;
    }

    public TituloDTO matchFichaTituloCoordenadas(FichaDTO ficha, List<PontosDTO> pontos, AlfaDTO alfa) {
        TituloDTO titulo = new TituloDTO();
        titulo.setAvenida(alfa.getAvenida());
        titulo.setBairro(alfa.getBairro());
        titulo.setDataEmissao("xx-xx-2022");
        titulo.setDistritoMunicipal(alfa.getDistritoMunicipal());
        titulo.setProcesso(ficha.getProcesso());
        titulo.setDataNascimento(formatedDate(alfa.getDataNascimento()));
        titulo.setDocumentoIdentificacao(alfa.getDocumento());
        titulo.setEstadoCivil(alfa.getEstadoCivil());
        titulo.setNomeCompleto(alfa.getNomeTitular());
        titulo.setNumeroParcela(ficha.getParcela());
        titulo.setSuperficieParcela("-");
        titulo.setPontos(pontos);
        log.info("Dados do titulo {}", titulo.toString());
        return titulo;
    }
}
