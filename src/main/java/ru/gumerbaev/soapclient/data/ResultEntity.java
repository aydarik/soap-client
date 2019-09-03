package ru.gumerbaev.soapclient.data;

import org.hibernate.validator.constraints.Length;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Length(max = 50)
    private String code;
    private Integer number;
    @Length(max = 100)
    private String fileNames;
    @Length(max = 100)
    private String error;

    public ResultEntity() {
    }

    public ResultEntity(Result result, Integer number) {
        this.code = result.getCode();
        this.number = number;

        final List<String> fileNames = result.getFileNames();
        if (fileNames != null && !fileNames.isEmpty()) {
            this.fileNames = String.join(" ", fileNames);
        }

        this.error = result.getError();
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
