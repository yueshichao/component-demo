package com.demo.lsz.filter;

import com.demo.lsz.autoconfigure.CustomAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

import java.util.Arrays;


public class CustomImportFilter implements AutoConfigurationImportFilter {

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        // 从配置中心拿配置
        boolean enable = true;
        String canonicalName = CustomAutoConfiguration.class.getCanonicalName();
        boolean[] result = new boolean[autoConfigurationClasses.length];
        Arrays.fill(result, true);
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            String autoConfigurationClass = autoConfigurationClasses[i];
            if (canonicalName.equals(autoConfigurationClass)) {
                result[i] = enable;
            }
        }
        return result;
    }

}
