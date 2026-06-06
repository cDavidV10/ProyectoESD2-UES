package controlador;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import funciones.UsuarioActivo;
import modelo.Usuario;
import vista.ClienteView;
import vista.Login;

public class CtrlClienteView {
    private ClienteView clienteView;
    private Usuario usuario;
    private Login login;

    public CtrlClienteView(ClienteView clienteView, Usuario usuario, Login login) {
        this.clienteView = clienteView;
        this.usuario = usuario;
        this.login = login;

        this.clienteView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(clienteView.getTxtUser(), usuario);
            }

        });

        this.clienteView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                login.setVisible(true);
            }
        });

    }

}
