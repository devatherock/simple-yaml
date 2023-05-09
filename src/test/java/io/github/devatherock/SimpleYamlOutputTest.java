package io.github.devatherock;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.devatherock.simpleyaml.SimpleYamlOutput;

/**
 * Unit tests for {@link SimpleYamlOutput}
 *
 * @author devaprasadh
 */
public class SimpleYamlOutputTest {

    @Test
    public void testReadmeNoOptions() throws IOException, URISyntaxException {
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("version", "1");
        map.put("colors", Arrays.asList("red", "blue"));
        String output = SimpleYamlOutput.toYaml(map);

        Assertions.assertEquals(getContent("readme-no-options.yml"), output);
    }

    @Test
    public void testReadmeAllOptions() throws IOException, URISyntaxException {
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("version", "1");
        map.put("colors", Arrays.asList("red", "blue"));
        SimpleYamlOutput yaml = SimpleYamlOutput.builder()
                .numericFieldToQuote("version")
                .flowStyleArrayField("colors")
                .indentArrays(false)
                .indentSize(3)
                .quoteType(SimpleYamlOutput.QuoteType.SINGLE)
                .build();
        String output = yaml.dump(map);

        Assertions.assertEquals(getContent("readme-all-options.yml"), output);
    }

    private String getContent(String fileName) throws IOException, URISyntaxException {
        return Files
                .readAllLines(
                        new File(SimpleYamlOutputTest.class.getClassLoader().getResource(fileName).toURI()).toPath())
                .stream().collect(Collectors.joining("\n"));
    }
}
