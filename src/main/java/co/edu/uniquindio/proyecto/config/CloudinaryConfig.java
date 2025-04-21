package co.edu.uniquindio.proyecto.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {


    
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ds3ftuf6v",
                "api_key", "824188897939395",
                "api_secret", "Khh5P1QeQEdthiHX5J3enM3vAtI",
                "secure", true
        ));
    }
}
