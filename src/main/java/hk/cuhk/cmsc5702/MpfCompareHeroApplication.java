package hk.cuhk.cmsc5702;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import hk.cuhk.cmsc5702.util.MpfWebScrapper;

@SpringBootApplication
public class MpfCompareHeroApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MpfCompareHeroApplication.class, args);
	}
	
}
