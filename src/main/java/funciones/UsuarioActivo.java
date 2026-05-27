package funciones;

import javax.swing.JLabel;

import modelo.Usuario;

public class UsuarioActivo {
    public void cambiarLabelUsuario(JLabel contenedor, Usuario usuario) {
        contenedor.setText(usuario.getUsername());
    }
}
