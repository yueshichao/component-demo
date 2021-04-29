import com.alibaba.fastjson.JSON;
import com.demo.lsz.entity.GeoEntity;
import com.demo.lsz.util.GenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilTest {

    @Test
    public void testGeo() {
        double latitude = GenUtil.getLatitude();
        double longitude = GenUtil.getLongitude();
        GeoEntity geoEntity = new GeoEntity(longitude, latitude);
        log.info("{}", JSON.toJSONString(geoEntity));
    }

}
