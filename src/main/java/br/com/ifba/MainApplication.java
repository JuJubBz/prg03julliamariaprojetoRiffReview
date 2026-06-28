package br.com.ifba;

//import br.com.ifba.banda.view.BandaSave;
import br.com.ifba.usuario.view.UsuarioView;
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
           UsuarioView telaLogin = context.getBean(UsuarioView.class);
            
            telaLogin.setLocationRelativeTo(null); 
            
            telaLogin.setVisible(true);
        });
    }
}
