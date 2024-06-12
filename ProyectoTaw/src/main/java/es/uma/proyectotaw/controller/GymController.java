package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.ClienteRepository;
import es.uma.proyectotaw.dao.TipoentrenamientoRepository;
import es.uma.proyectotaw.dao.TrolRepository;
import es.uma.proyectotaw.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.dao.UsuarioRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class GymController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TrolRepository trolRepository;

    @Autowired
    private TipoentrenamientoRepository tipoentrenamientoRepository;

    @GetMapping("/")
    public String redirectToInicio(Model model) {
        return "redirect:/inicio";
    }
    @GetMapping("/inicio")
    public String doInicio(Model model) {
        return "inicio";
    }
    @GetMapping("/trabaja")
    public String doTrabaja(Model model) {
        List<TipoentrenamientoEntity> tiposEntrenamientos = tipoentrenamientoRepository.findAll();
        model.addAttribute("tiposEntrenamientos", tiposEntrenamientos);
        return "trabaja";
    }
    @GetMapping("/acceso")
    public String doAcceso(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "acceso";
    }
    @GetMapping("/registro")
    public String doRegistro(Model model) {

        return "registro";
    }

    @PostMapping("/registerEntrenador")
    public String registerEntrenador(@RequestParam(value = "nombre", required = false) String nombre, @RequestParam("apellidos") String apellidos,
                           @RequestParam("Fecha_nacimiento") String FechaNac, @RequestParam("DNI") String DNI,
                           @RequestParam("eMail") String eMail, @RequestParam("telefono") String telefono, @RequestParam("sexo") String sexo,
                           @RequestParam("tipoentrenamiento") TipoentrenamientoEnum tipoEntrenamiento,
                           @RequestParam("usuario") String nombre_usuario, @RequestParam("contraseña") String contraseña, Model model) throws ParseException {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(FechaNac, formato);
        Date fechaSql = Date.valueOf(fecha);
        usuario.setFechaNacimiento(fechaSql);
        usuario.setDni(DNI);
        usuario.setCorreo(eMail);
        usuario.setTelefono(telefono);
        usuario.setNombreUsuario(nombre_usuario);
        usuario.setContraseña(contraseña);
        usuario.setGenero(sexo);
        usuario.setRol(this.trolRepository.findById(3).get());

        TipoentrenamientoEntity tipoent = new TipoentrenamientoEntity();

        tipoent.setTipo(tipoEntrenamiento);
        this.tipoentrenamientoRepository.save(tipoent);
        usuario.setTipoEntrenamiento(tipoent);
        this.usuarioRepository.save(usuario);

        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") Usuario user,
                        Model model, HttpSession session) {
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuarioAndContraseña(user.getUser(), user.getPassword());
        if (usuario != null) {
            RolEnum rol = usuario.getRol().getRol();
            session.setAttribute("usuario", usuario);
            if(rol == RolEnum.admin) {
                return "redirect:/adminMain";
            }else if(rol == RolEnum.entrenador) {
                return "redirect:/entrenadorMain";
            }else if(rol == RolEnum.cliente){
                ClienteEntity cliente = this.clienteRepository.findById(usuario.getId()).orElse(null);
                session.setAttribute("cliente", cliente);
                    return "redirect:/clienteMain";
            }
        }
        model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
        return "acceso"; // Reenvía al mismo formulario de inicio de sesión
    }


    @PostMapping("/register")
    public String register(@RequestParam(value = "nombre", required = false) String nombre, @RequestParam("apellidos") String apellidos,
                           @RequestParam("Fecha_nacimiento") String FechaNac, @RequestParam("DNI") String DNI,
                           @RequestParam("eMail") String eMail, @RequestParam("telefono") String telefono, @RequestParam("altura") String altura,
                           @RequestParam("peso") String peso, @RequestParam("sexo") String sexo,
                           @RequestParam("tipoEntrenamiento") TipoentrenamientoEnum tipoEntrenamiento, @RequestParam("objetivos") String objetivos,
                           @RequestParam("usuario") String nombre_usuario, @RequestParam("contraseña") String contraseña, Model model) throws ParseException {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(FechaNac, formato);
        Date fechaSql = Date.valueOf(fecha);
        usuario.setFechaNacimiento(fechaSql);
        usuario.setDni(DNI);
        usuario.setCorreo(eMail);
        usuario.setTelefono(telefono);
        usuario.setNombreUsuario(nombre_usuario);
        usuario.setContraseña(contraseña);
        usuario.setGenero(sexo);
        usuario.setRol(this.trolRepository.findById(2).get());

        TipoentrenamientoEntity tipoent = new TipoentrenamientoEntity();

        tipoent.setTipo(tipoEntrenamiento);
        this.tipoentrenamientoRepository.save(tipoent);
        usuario.setTipoEntrenamiento(tipoent);

        ClienteEntity cliente = new ClienteEntity();
        cliente.setAltura(Float.parseFloat(altura));
        cliente.setPeso(Float.parseFloat(peso));
        cliente.setObjetivos(objetivos);
        cliente.setUsuario(usuario);
        this.clienteRepository.save(cliente);

        return "redirect:/";
    }
    @GetMapping("/adminMain")
    public String doAdminMain() {
        // Aquí puedes agregar lógica para obtener datos necesarios para la vista de administrador
        return "mainAdmin"; // Retorna el nombre de la vista de administrador
    }







}
