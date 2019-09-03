package ru.gumerbaev.soapclient.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for result entities.
 */

@Repository
public interface ResultRepository extends CrudRepository<ResultEntity, Long> {
}
