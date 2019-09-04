package ru.gumerbaev.soapclient.data;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gumerbaev.soapclient.filesearch.Result;

import javax.persistence.*;
import java.util.List;

/**
 * Result entity for DB.
 */

@Entity
@Table(name = "results")
@SuppressWarnings("unused")
public class ResultEntity {

    private static final int STATUS_CODE_MAX_SIZE = 50;
    private static final int FILE_NAMES_MAX_SIZE = 100;
    private static final int ERROR_MAX_SIZE = 100;

    private static final Logger log = LoggerFactory.getLogger(ResultEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Length(max = STATUS_CODE_MAX_SIZE)
    private String code;
    private Integer number;
    @Length(max = FILE_NAMES_MAX_SIZE)
    private String fileNames;
    @Length(max = ERROR_MAX_SIZE)
    private String error;

    public ResultEntity() {
    }

    public ResultEntity(Result result, Integer number) {
        this.code = result.getCode();
        this.number = number;

        final List<String> fileList = result.getFileNames();
        if (fileList != null && !fileList.isEmpty()) {
            final String fileNames = String.join(" ", fileList);
            if (fileNames.length() > FILE_NAMES_MAX_SIZE) {
                log.warn("File names are not fully saved in DB");
                this.fileNames = fileNames.substring(0, FILE_NAMES_MAX_SIZE);
            } else {
                this.fileNames = fileNames;
            }
        }

        final String error = result.getError();
        if (error.length() > ERROR_MAX_SIZE) {
            log.warn("Error is not fully saved in DB");
            this.error = error.substring(0, ERROR_MAX_SIZE);
        } else {
            this.error = error;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
