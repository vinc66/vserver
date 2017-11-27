package cn.vinc.core.support;

import com.google.common.hash.Hashing;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.UUID;

public final class IDGenerator {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

    public static int generate(String id) {
        if (StringUtils.isEmpty(id)) {
            return 0;
        }
        return Hashing.murmur3_32().hashString(id, Charset.defaultCharset()).asInt();
    }

    public static int generateID() {
       return generate(generate());
    }


}
