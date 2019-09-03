package ru.gumerbaev.soapclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gumerbaev.soapclient.data.ResultEntity;
import ru.gumerbaev.soapclient.filesearch.FindNumberRequest;
import ru.gumerbaev.soapclient.filesearch.FindNumberResponse;
import ru.gumerbaev.soapclient.filesearch.Result;

/**
 * SOAP service endpoint.
 */

@Endpoint
public class FileSearchEndpoint {

    private static final String NAMESPACE_URI = "http://gumerbaev.ru/soapclient/filesearch";
    private static final Logger log = LoggerFactory.getLogger(FileSearchEndpoint.class);

    private FileSearchReader fileSearchReader;
    private FileSearchDataService service;

    @Autowired
    public FileSearchEndpoint(FileSearchReader fileSearchReader, FileSearchDataService service) {
        this.fileSearchReader = fileSearchReader;
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findNumberRequest")
    @ResponsePayload
    @SuppressWarnings("unused")
    public FindNumberResponse findNumber(@RequestPayload FindNumberRequest request) {
        int number = request.getNumber();
        log.info("Request received with number {}", number);

        final Result result = fileSearchReader.findNumber(number);
        log.debug("Request processed: {}", result.getCode());

        final ResultEntity resultEntity = service.saveResult(result, number);
        log.info("Result saved to DB with ID: {}", resultEntity.getId());

        final FindNumberResponse response = new FindNumberResponse();
        response.setResult(result);

        return response;
    }
}
