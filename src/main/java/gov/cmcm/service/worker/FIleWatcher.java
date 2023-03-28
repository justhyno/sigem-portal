package gov.cmcm.service.worker;

import java.io.File;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FIleWatcher implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FIleWatcher.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("A catalogar os directorios");
        Thread t = new Thread(new WatchServiceThread("res/projectos/"));
        log.info("catalogo concluido");

        t.start();
    }

    private class WatchServiceThread implements Runnable {

        String rootDir;

        WatchServiceThread(String rootDir) {
            this.rootDir = rootDir;
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

                        if ("xls".equals(extension) || "xlsx".equals(extension) || "csv".equals(extension)) {
                            // The file has a valid extension
                            log.info("novo ficheiro adicionado {}", fullPath.toString());
                            messageQueue.Offer(new File(fullPath.toString()));
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

        if ("xls".equals(extension) || "xlsx".equals(extension) || "csv".equals(extension)) {
            // The file has a valid extension
            log.info("novo ficheiro adicionado {}", fullPath.toString());
            messageQueue.Offer(new File(fullPath.toString()));
        } else {
            // The file has an invalid extension
            log.warn("O ficheiro {} tem uma extensão inválida, {}", fullPath.toString(), extension);
        }
    }
}
