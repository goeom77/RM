package org.gyu.solution;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄링 활성화
@MapperScan("org.gyu.solution.*.dao")
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class SolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolutionApplication.class, args);
	}

}
