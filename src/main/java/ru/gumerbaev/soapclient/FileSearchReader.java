package ru.gumerbaev.soapclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.gumerbaev.soapclient.enums.StatusCode;
import ru.gumerbaev.soapclient.filesearch.Result;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Component for number search in given files.
 */

@Component
public class FileSearchReader {

    @Value("${file-reader.buffer-size:262144}")
    private Integer bufferSize;

    @Value("${file-reader.files.location}")
    private String filesPath;

    @Value("${file-reader.files.extension:txt}")
    private String filesExtension;

    private static final Logger log = LoggerFactory.getLogger(FileSearchReader.class);

    /**
     * Search for a specified number in files.
     *
     * @param number what to search
     * @return result object
     */
    Result findNumber(Integer number) {
        final String numberStr = number.toString();
        long start = Instant.now().getEpochSecond();

        final Result result = new Result();
        final AtomicBoolean hasError = new AtomicBoolean(false);
        final List<String> resultFiles = Collections.synchronizedList(result.getFileNames());

        List<Path> files = null;
        try {
            files = Files.walk(Paths.get(filesPath))
                    .filter(Files::isRegularFile).filter(Files::isReadable)
                    .filter(f -> f.toFile().getName().endsWith("." + filesExtension))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            hasError.set(true);
            log.error("Invalid files path", e);
            result.setError(e.toString());
        }

        if (!hasError.get() && files != null) {
            log.debug("{} file(s) found", files.size());

            files.parallelStream().forEach(f -> {
                if (hasError.get()) return;

                final File file = f.toFile();
                long fileLength = file.length();
                int offset = 0;
                log.debug("File read started: {}", file.getName());

                try {
                    RandomAccessFile raf = new RandomAccessFile(file, "r");
                    while (true) {
                        if (offset >= fileLength) break;
//                        log.debug("File read progress: {} [{} / {}]", file.getName(), offset, file.length());

                        long remainSize = fileLength - offset;
                        boolean isLast = remainSize <= bufferSize;

                        byte[] buffer = new byte[isLast ? (int) remainSize : bufferSize];
                        raf.seek(offset);
                        raf.read(buffer);
                        String line = new String(buffer);
                        String[] split = line.split(",");

                        if (Arrays.stream(split)
                                .skip(offset > 0 ? 1 : 0)
                                .limit(isLast ? split.length : split.length - 1)
                                .anyMatch(numberStr::equals)) {
                            resultFiles.add(file.getName());
                            break;
                        }
                        if (hasError.get()) break;
                        offset += buffer.length - (isLast ? 0 : 11); // 10 bytes for Integer.MAX_VALUE + 1 byte for delimiter
                    }
                    raf.close();
                } catch (Exception e) {
                    hasError.set(true);
                    log.error("File read error", e);
                    log.debug("File: {}, offset: {}", file.getName(), offset);
                    result.setError(e.toString());
                }

                log.debug("File read complete: {}", file.getName());
            });
            log.debug("{} file(s) found", resultFiles.size());
        }

        long end = Instant.now().getEpochSecond();
        log.info("Completed in {} seconds", (end - start));

        // Put correct status to the result object
        if (hasError.get()) {
            result.setCode(StatusCode.ERROR.toString());
        } else if (resultFiles.isEmpty()) {
            result.setCode(StatusCode.NOT_FOUND.toString());
        } else {
            result.setCode(StatusCode.OK.toString());
        }
        return result;
    }
}
