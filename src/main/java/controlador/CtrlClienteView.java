package controlador;

import arboles.ArbolB;
import dao.ContratoDAO;
import dao.FacturaDAO;
import dao.MedidorDAO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import funciones.Paneles;
import funciones.UsuarioActivo;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import modelo.Contrato;
import modelo.Factura;
import modelo.Medidor;
import modelo.Usuario;
import vista.ClienteView;
import vista.ItemFacturaPanel;
import vista.Login;
import vista.ViewDetalleFactura;

public class CtrlClienteView {

    private ClienteView clienteView;
    private Usuario usuario;
    private Login login;
    private Paneles paneles;
    private ItemFacturaPanel pFacturasCliente;
    private ViewDetalleFactura viewDetalle;
    private FacturaDAO facturaDAO;
    private List<Factura> datos;
    private Contrato resultContrato;
    private Medidor resultMedidor;
    private ArbolB resultFact;
    private JLabel lblMensaje;

    public CtrlClienteView(ClienteView clienteView, Usuario usuario, Login login) {
        this.clienteView = clienteView;
        this.usuario = usuario;
        this.login = login;
        paneles = new Paneles();

        datos = new ArrayList<>();
        resultFact = new ArbolB(2);
        resultMedidor = new Medidor();

        this.clienteView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(clienteView.getTxtUser(), usuario);
            }

        });

        this.clienteView.getBtnFactura().addActionListener(e -> {
            clienteView.getBtnFactura().setEnabled(false);
            lblMensaje = new JLabel("Cargando Facturas...");
            clienteView.getBgPanel().add(lblMensaje);
            lblMensaje.setLocation(100, 100);
            try {
                buscarMedidor();
            } catch (Exception ex) {
                
                System.getLogger(CtrlClienteView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        this.clienteView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                login.setVisible(true);
            }
        });

    }

    private void buscarMedidor() throws Exception {

        clienteView.getBgPanel().add(lblMensaje);
        lblMensaje.setLocation(100, 80);
        resultContrato = new ContratoDAO().buscarContratoCliente(usuario);
        if (resultContrato != null && resultContrato.getMedidor() != null) {
            try {
                resultMedidor = new MedidorDAO().buscarPorCodigo(resultContrato.getMedidor().getCodigo());
                if (resultMedidor != null) {
                    buscarFacturasMedidor();
                } else {
                    clienteView.getBtnFactura().setEnabled(true);
                    JOptionPane.showMessageDialog(null, "No hay resultado");
                }
            } catch (Exception ex) {
                clienteView.getBtnFactura().setEnabled(true);
                System.getLogger(CtrlBotonesMedidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else {
            clienteView.getBtnFactura().setEnabled(true);
            JOptionPane.showMessageDialog(null, "No existe un contrato válido con medidor asociado.");
        }

    }

    private void buscarFacturasMedidor() throws Exception {
        resultFact = new FacturaDAO().obtenerFacturasMedidor(resultMedidor);
        mostrarFacturasMedidor();
    }

    private void mostrarFacturasMedidor() {
        datos = resultFact.obtenerDatosArbolB();

        JPanel panelFacturas = new JPanel();
        panelFacturas = clienteView.getBgPanel();
        panelFacturas.removeAll();
        
        panelFacturas.setLayout(new BoxLayout(panelFacturas, BoxLayout.Y_AXIS));

        if (!datos.isEmpty()) {
            for (Factura f : datos) {
                ItemFacturaPanel fila = new ItemFacturaPanel(f);
                panelFacturas.add(fila);
                panelFacturas.add(Box.createRigidArea(new Dimension(0, 5)));
                if (f.getPago().getEstado().equalsIgnoreCase("Pendiente")) {
                    fila.setBackground(Color.pink);
                }
                fila.getBtnDetalleFact().addActionListener(e -> {
                    viewDetalle = new ViewDetalleFactura();
                    clienteView.getBgPanel().removeAll();
                    clienteView.getBgPanel().add(viewDetalle);
                    clienteView.getBgPanel().revalidate();
                    clienteView.getBgPanel().repaint();

                    try {
                        new CtrlDetalleFactura(f, viewDetalle, null, clienteView, ()-> mostrarFacturasMedidor());
                    } catch (Exception ex) {
                        System.getLogger(CtrlBotonesMedidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
        }else{
            panelFacturas.add(new JLabel("No hay facturas disponibles."));
        }

        panelFacturas.revalidate();
        panelFacturas.repaint();
        
        clienteView.getBtnFactura().setEnabled(true);
    }

}
