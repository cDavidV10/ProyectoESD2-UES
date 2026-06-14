package controlador;

import dao.DireccionDAO;
import dao.DistritoDAO;
import dao.MedidorDAO;
import dao.MunicipioDAO;
import interfaz.IDireccionDAO;
import interfaz.IDistritoDAO;
import interfaz.IMunicipioDAO;
import arboles.ArbolBinarioAVL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Direccion;
import modelo.Distrito;
import modelo.Medidor;
import modelo.Municipio;
import vista.ViewRegistroMedidor;

public class CtrlRegistroMedidor {

    private ViewRegistroMedidor vista;
    private IMunicipioDAO municipioDAO;
    private IDistritoDAO distritoDAO;
    private IDireccionDAO direccionDAO;

    private String zonaTxt;
    private String numCasaTxt;
    private Distrito distritoSeleccionado;
    private Municipio municipioSeleccionado;
    private Direccion d;

    public CtrlRegistroMedidor(ViewRegistroMedidor vista) {
        this.vista = vista;
        this.municipioDAO = new MunicipioDAO();
        this.distritoDAO = new DistritoDAO();
        this.direccionDAO = new DireccionDAO();
        cargarCombosMed();
        cargarMunicipios();
        events();
    }

    private void cargarMunicipios() {
        try {
            ArbolBinarioAVL arbolMunicipios = municipioDAO.listar();
            vista.getCbMunicipio().removeAllItems();

            Municipio vacio = new Municipio();
            vacio.setNombre("Seleccione...");
            vista.getCbMunicipio().addItem(vacio);

            if (arbolMunicipios != null) {
                ArrayList lista = arbolMunicipios.IND(); // in orden

                if (lista != null) {
                    for (Object obj : lista) {
                        Municipio m = (Municipio) obj;
                        vista.getCbMunicipio().addItem(m);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "[ERROR] No se han podido cargar los municipios.\n" + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void events() {
        this.vista.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
        
        this.vista.getBtnCancelar().addActionListener(e ->{
            vista.setVisible(false);
        });

        this.vista.getCbMunicipio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDistritos();

            }
        });
    }

    private void cargarDistritos() {
        if (vista.getCbMunicipio().getSelectedItem() == null) {
            return;
        }

        Municipio municipioSeleccionado = (Municipio) vista.getCbMunicipio().getSelectedItem();
        vista.getCbDistrito().removeAllItems();

        Distrito vacio = new Distrito();
        vacio.setNombre("Seleccione...");
        vista.getCbDistrito().addItem(vacio);

        if (municipioSeleccionado != null && municipioSeleccionado.getId() != 0) {
            try {
                ArbolBinarioAVL arbolDistritos = distritoDAO.listarPorMunicipio(municipioSeleccionado.getId());

                if (arbolDistritos != null) {
                    ArrayList lista = arbolDistritos.IND();

                    if (lista != null) {
                        for (Object obj : lista) {
                            Distrito d = (Distrito) obj;
                            vista.getCbDistrito().addItem(d);
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista,
                        "[ERROR] No se han podido cargar los distritos.\n" + ex.getMessage(), "Error BD",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardar() {
        zonaTxt = vista.getTxtZona().getText().trim();
        numCasaTxt = vista.getTxtNumCasa().getText().trim();
        distritoSeleccionado = (Distrito) vista.getCbDistrito().getSelectedItem();
        municipioSeleccionado = (Municipio) vista.getCbMunicipio().getSelectedItem();

        if (municipioSeleccionado == null || municipioSeleccionado.getId() == 0 || distritoSeleccionado == null
                || distritoSeleccionado.getId() == 0) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Seleccione un municipio y un distrito válidos.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (zonaTxt.isEmpty() || numCasaTxt.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Complete todos los campos de la dirección.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (zonaTxt.length() > 50) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: La zona no puede tener más de 50 caracteres.", "Mucho texto",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (numCasaTxt.length() > 20) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: El N° de casa no puede tener más de 20 caracteres.",
                    "Mucho texto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!zonaTxt.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,\\- ]+$")) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Ingrese solo caracteres válidos en la zona.",
                    "Caracteres invalidos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!numCasaTxt.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,\\-#/ ]+$")) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Ingrese solo caracteres válidos en el número de casa.",
                    "Caracteres invalidos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        d = new Direccion();
        d.setZona(zonaTxt);
        d.setNumeroCasa(numCasaTxt);
        d.setDistrito(distritoSeleccionado);

        try {
            Medidor medidor = new Medidor();
            medidor.setCodigo(vista.getTxtCodigo().getText());
            medidor.setDiametroNomila(vista.getCmbDiametroNomnal().getSelectedItem().toString());
            medidor.setUnidadMedida(vista.getCmbUnidadMedida().getSelectedItem().toString());

            Distrito distrito = new Distrito();
            distrito.setId(distritoSeleccionado.getId());
            distrito.setNombre(distritoSeleccionado.getNombre());
            distrito.setMunicipio(municipioSeleccionado);

            medidor.setDireccion(d);
            medidor.setLecturas(new ArrayList());

            new MedidorDAO().crearRegistro(medidor);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se guardo el registro medidor");
            e.printStackTrace();
        }
    }

    
    private void cargarCombosMed() {
        vista.getCmbDiametroNomnal().removeAllItems();
        vista.getCmbDiametroNomnal().addItem("Diametro nominal");
        vista.getCmbDiametroNomnal().addItem("1/2 Pulgada");
        vista.getCmbDiametroNomnal().addItem("3/4 Pulgada");
        vista.getCmbDiametroNomnal().addItem("1 Pulgada");

        vista.getCmbUnidadMedida().removeAllItems();
        vista.getCmbUnidadMedida().addItem("metro cubico");
        vista.getCmbUnidadMedida().addItem("pie cubico");
    }

    private void limpiar() {
        vista.getTxtZona().setText("");
        vista.getTxtNumCasa().setText("");
        if (vista.getCbMunicipio().getItemCount() > 0) {
            vista.getCbMunicipio().setSelectedIndex(0);
        }
        vista.getCbDistrito().removeAllItems();
    }
}
