package org.mc.connectx.Configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Projectconfigs {



    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dv3p7g0cd",
                "api_key", "121292769234156",
                "api_secret", "yHYGfn8HUMflocmsbSfBGevftmw",
                "secure", true));
    }
}

