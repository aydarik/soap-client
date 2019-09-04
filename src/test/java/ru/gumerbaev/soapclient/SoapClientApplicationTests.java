package ru.gumerbaev.soapclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gumerbaev.soapclient.enums.StatusCode;
import ru.gumerbaev.soapclient.filesearch.Result;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:filereadertest.properties")
public class SoapClientApplicationTests {

    @Autowired
    private FileSearchReader fileSearchReader;

    @Test
    public void fileSearchReaderTest() {
        final Result result = fileSearchReader.findNumber(23);
        assertThat(result.getCode()).isEqualTo(StatusCode.OK.toString());
        assertThat(result.getFileNames()).hasSize(1);
        assertThat(result.getFileNames().get(0)).isEqualTo("test_1.txt");
    }
}
