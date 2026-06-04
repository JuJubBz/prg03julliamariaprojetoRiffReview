package br.com.ifba;

import java.awt.EventQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MainApplication.class)
                .headless(false)
                .run(args);

        // tela do Swing vai rodar na Thread correta de vídeo 
        EventQueue.invokeLater(() -> {
            
            // TelaPrincipal tela = context.getBean(TelaPrincipal.class);
            // tela.setVisible(true);
        });
	}

}
