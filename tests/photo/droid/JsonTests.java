package photo.droid;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.skyscreamer.jsonassert.JSONAssert;

import photo.droid.ImageResult;
import photo.droid.ImageSearchRSData;

public class JsonTests {
    ObjectMapper mapper;

    public static final String FIXT_DIR = "tests/fixtures/";

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void testReadValue() throws IOException {
        ImageResult ir = mapper.readValue
            (new File(FIXT_DIR + "obj_ImageResult.json"), ImageResult.class);

        assertEquals(ir.getFullUrl(), "http://davidfeldmanshow.com/wp-content/uploads/2014/01/dogs-wallpaper.jpg");
        assertEquals(ir.getThumbUrl(), "http://t2.gstatic.com/images?q\u003dtbn:ANd9GcSMs-8bRX-7jTqcQauH1Md1uKIHNqXRXsnrdKUEyDBZ6XHNvSfuIeYTB9IP");

        ImageSearchRSData results = mapper.readValue
            (new File(FIXT_DIR + "rs_image_search_data.json"),
             ImageSearchRSData.class);

        assertEquals(4, results.results.size());
    }

    @Test
    public void testWriteValue() throws JsonProcessingException {
        ImageResult ir = new ImageResult("http://cat.jpg.to/", "http://cat.jpg.to/icon+png");
        String json = mapper.writeValueAsString(ir);
        JSONAssert.assertEquals
            ("{\"url\":\"http://cat.jpg.to/\", " +
             "\"tbUrl\": \"http://cat.jpg.to/icon+png\"},",
             json, false);
    }
}
