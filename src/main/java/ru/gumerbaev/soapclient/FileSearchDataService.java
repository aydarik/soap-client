package ru.gumerbaev.soapclient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gumerbaev.soapclient.data.ResultEntity;
import ru.gumerbaev.soapclient.data.ResultRepository;
import ru.gumerbaev.soapclient.filesearch.Result;

/**
 * Service for some actions with the result object.
 */

@Service
public class FileSearchDataService {

    private final ResultRepository repository;

    public FileSearchDataService(ResultRepository repository) {
        this.repository = repository;
    }

    /**
     * Save the entity to the DB
     * @param result object to save
     * @param number number was searched
     */
    @Transactional
    public ResultEntity saveResult(Result result, Integer number) {
        ResultEntity entity = new ResultEntity(result, number);
        return repository.save(entity);
    }
}
