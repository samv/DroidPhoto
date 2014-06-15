package photo.droid;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import photo.droid.ImageResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    }
}
