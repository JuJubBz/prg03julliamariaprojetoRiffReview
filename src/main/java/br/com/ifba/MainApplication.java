package br.com.ifba;

import br.com.ifba.banda.view.BandaSave;
import java.awt.EventQueue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MainApplication.class)
                .headless(false)
                .run(args);

     
            // 2. Pede para o Spring pegar a instância da sua tela que já está com tudo injetado
        java.awt.EventQueue.invokeLater(() -> {
            BandaSave telaCadastro = context.getBean(BandaSave.class);
            telaCadastro.setVisible(true); // Faz a mágica acontecer e abre a tela!
        });
    }
}
