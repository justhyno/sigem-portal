package gov.cmcm.service.worker;

import gov.cmcm.service.AlfaService;
import gov.cmcm.service.PontosService;
import gov.cmcm.service.ProjectoService;
import gov.cmcm.service.UploadService;
import gov.cmcm.service.dto.AlfaDTO;
import gov.cmcm.service.dto.PontosDTO;
import gov.cmcm.service.dto.ProjectoDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FIleWatcher implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FIleWatcher.class);
    private static final String BASE_PATH = "res/projectos/";

    private final ProjectoService projectoService;
    private final UploadService uploadService;
    private final AlfaService alfaService;
    private final PontosService pontosService;

    public FIleWatcher(ProjectoService projectoService, UploadService uploadService, AlfaService alfaService, PontosService pontosService) {
        this.projectoService = projectoService;
        this.uploadService = uploadService;
        this.alfaService = alfaService;
        this.pontosService = pontosService;
    }

    private void createFolder() {
        List<ProjectoDTO> projectoDTOs = projectoService.findAllProjects();
        log.info("A validar projectos");
        for (ProjectoDTO projectoDTO : projectoDTOs) {
            File directoryReceber = new File(BASE_PATH + projectoDTO.getNome() + "/receber");
            File directoryEnviar = new File(BASE_PATH + projectoDTO.getNome() + "/processado");

            if (!directoryReceber.exists()) {
                try {
                    FileUtils.forceMkdir(directoryReceber);
                    FileUtils.forceMkdir(directoryEnviar);

                    log.info("Estrutura do projecto '{}' criado com sucesso", projectoDTO.getNome());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                log.info("O projecto {} já existe", projectoDTO.getNome());
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("A catalogar os directorios");
        this.createFolder();

        Thread t = new Thread(new WatchServiceThread(BASE_PATH, this));
        log.info("catalogo concluido");
        t.start();
    }

    private class WatchServiceThread implements Runnable {

        String rootDir;

        private final FIleWatcher fw;

        WatchServiceThread(String rootDir, FIleWatcher fw) {
            this.rootDir = rootDir;
            this.fw = fw;
        }

        @Override
        public void run() {
            Path path = Paths.get(rootDir); // the directory to be watched

            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                registerDirectoriesRecursively(watchService, path);
                while (true) {
                    WatchKey key;
                    try {
                        key = watchService.take();
                    } catch (InterruptedException e) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        Path fullPath = ((Path) key.watchable()).resolve(filename);
                        String extension = FilenameUtils.getExtension(fullPath.toString());

                        if ("xls".equals(extension)) {
                            // The file has a valid extension
                            log.info("novo ficheiro adicionado {}", fullPath.toString());
                            // messageQueue.Offer(new File(fullPath.toString()));
                            fw.uploadFile(fullPath.toString());
                        } else {
                            // The file has an invalid extension
                            log.warn("O ficheiro {} tem uma extensão inválida, {}", fullPath.toString(), extension);
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    // Helper method to register the watch service recursively for a directory and
    // its subdirectories
    private void registerDirectoriesRecursively(WatchService watchService, Path directory) throws IOException {
        Files.walkFileTree(
            directory,
            new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.getFileName().toString().equals("receber")) {
                        // Register the receber directory with the watch service
                        dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
                        log.debug("Catalogado: '{}'", dir.toString());
                        log.debug("Checking if we do have files on folder {}", dir.toString());

                        findIncommingFiles(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            }
        );
    }

    private void findIncommingFiles(Path receberDir) throws IOException {
        Files
            .walk(receberDir)
            .filter(Files::isRegularFile)
            .forEach(file -> {
                log.info("Found file: {}", file.getFileName().toString());

                log.info("Nome completo do fciheiro {}", file.toFile().getAbsolutePath());
                sentToQueue(file);
            });
    }

    private void sentToQueue(Path path) {
        Path fullPath = path.getFileName().toAbsolutePath();
        String extension = FilenameUtils.getExtension(fullPath.toString());

        if ("xls".equals(extension)) {
            // The file has a valid extension
            log.info("a carregar o ficheiro {}", path.toFile().getAbsolutePath());
            // messageQueue.Offer(new File(fullPath.toString()));

            try {
                this.uploadFile(path.toFile().getAbsolutePath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            // The file has an invalid extension
            log.warn("O ficheiro {} tem uma extensão inválida, {}", fullPath.toString(), extension);
        }
    }

    public void uploadFile(String path) throws IOException {
        try {
            Pattern pattern = Pattern.compile("projectos/(.*?)/receber");
            Matcher matcher = pattern.matcher(path);
            ProjectoDTO projecto = new ProjectoDTO();
            if (matcher.find()) {
                String result = matcher.group(1);
                projecto = projectoService.findByName(result);
            }

            switch (readFirstCell(path)) {
                case 1:
                    Optional<List<PontosDTO>> pontos = uploadService.readGeo(path);
                    if (!pontos.isEmpty()) {
                        HashMap<String, Long> parcelIds = new HashMap<>();
                        for (PontosDTO ponto : pontos.get()) {
                            parcelIds.put(ponto.getParcela(), projecto.getId());
                        }
                        pontosService.deleteBySpu(parcelIds);
                        for (PontosDTO ponto : pontos.get()) {
                            pontosService.save(ponto, projecto.getId());
                        }
                    }
                    break;
                case 2:
                    Optional<List<AlfaDTO>> lista = uploadService.readAlpha(path);
                    if (!lista.isEmpty()) {
                        for (AlfaDTO alfa : lista.get()) {
                            alfaService.save(alfa, projecto);
                        }
                    }
                    break;
                default:
                    log.error("ficheiro não válido");
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int readFirstCell(String path) throws IOException {
        File file = new File(path);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        String cellValue = cell.getStringCellValue();
        workbook.close();
        log.info("value readed {}", cellValue);
        if (cellValue.equalsIgnoreCase("Codigo")) return 1; else if (cellValue.equalsIgnoreCase("Codigo da Parcela")) return 2;

        return -1;
    }
}
