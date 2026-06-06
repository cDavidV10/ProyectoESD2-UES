package funciones;

import javax.swing.JOptionPane;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import io.github.cdimascio.dotenv.Dotenv;

public class Correos {
    Dotenv dotenv = Dotenv.load();
    String resendKey = dotenv.get("RESEND_KEY");

    public void correoCredenciales(String nombre, String apellido, String user, String password,
            String correo) {
        String body = String.format(
                """
                        <h2><strong>Sistema de Cobro de Agua te da la bienvenida</strong></h2>
                        <p> Hola <strong>%s %s</strong> nos alegra que hayas decidio formar parte de nuestro equipo</p>

                         <p>Tus credenciales para acceder al sistema son las siguientes</p>
                         <p>Usuario: <strong>%s</strong></p>
                         <p>Contraseña: <strong>%s</strong></p>

                         <p>Esperamos que sigas creciendo con nosotros</p>
                              """, nombre, apellido, user, password);
        enviarCorreo(correo, body);

    }

    private void enviarCorreo(String correo, String body) {
        Resend resend = new Resend(resendKey);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("SistemaCobroDeAgua <noreply@sistemaues.me>")
                .to(correo)
                .subject("Credenciales")
                .html(body)
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);

            System.out.println(data.getId());
            JOptionPane.showMessageDialog(null, "Correo enviado exitosamente");

        } catch (ResendException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al enviar el correo");
        }
    }
}
