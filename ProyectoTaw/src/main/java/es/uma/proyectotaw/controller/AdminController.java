package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.*;
import es.uma.proyectotaw.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// Realizado por Carlos Gálvez Bravo
@Controller
public class AdminController extends BaseController{

    @Autowired
    protected UsuarioService usuarioService;

    // BORRAR ///////////////////////////////////////////////
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Autowired
    private TipoentrenamientoRepository tipoentrenamientoEntity;

    @Autowired
    private TipoejerciciobodubuildingRepository tipoejerciciobodubuildingRepository;

    @Autowired
    private TipoejerciciocrosstrainingRepository tipoejerciciocrosstrainingRepository;

    /////////////////////////////////////////////////////////

    @GetMapping("/adminMain/inicio")
    public String doAdminMain() {
        return "mainAdmin";
    }

    // RAMA DE LISTA DE ENTRENADORES //////////////////////////////////////////////////////////////////

    @GetMapping("/adminMain/entrenadores")
    public String doEntrenadores(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        List<UsuarioDTO> entrenadoresBodyBuilding = usuarioService.listarEntrenadoresBodyBuilding();
        List<UsuarioDTO> entrenadoresCrossTraining = usuarioService.listarEntrenadoresCrossTrainig();

        model.addAttribute("entrenadoresBodyBuilding", entrenadoresBodyBuilding);
        model.addAttribute("entrenadoresCrossTraining", entrenadoresCrossTraining);

        return "listaEntrenadores";
    }

    @GetMapping("/adminMain/borrarEntrenador/{id}")
    public String borrarEntrenador(@PathVariable("id") int id){
        usuarioService.borrarEntrenador(id);
        return "redirect:/adminMain/entrenadores";
    }

    @GetMapping("/adminMain/clientesEntrenador/{id}")
    public String doClientesEntrenador(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        List<UsuarioDTO> listaClientes = usuarioService.buscarClientesPorEntrenador(id);
        UsuarioDTO entrenador = usuarioService.buscarUsuario(id);

        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaClientes);

        return "clientesAsignadosEntrenador";
    }

    @GetMapping("/adminMain/entrenador/desasignarCliente/{id}")
    public String desasignarCliente(@PathVariable("id") int id, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";

        int idEntrenador = usuarioService.buscarEntrenadorDeCliente(id).getId();
        usuarioService.desasignarEntrenadorACliente(id);

        return "redirect:/adminMain/clientesEntrenador/"+idEntrenador;
    }

    @PostMapping("/adminMain/filtrar/clientesAsignados")
    public String filtrarClientesAsignados(@RequestParam("idEntrenador") int idEntrenador, @RequestParam("filtro") String filtro, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";

        UsuarioDTO entrenador = usuarioService.buscarUsuario(idEntrenador);
        // si la lista filtrada está vacía, se mostrará la lista completa
        List<UsuarioDTO> listaFiltrada = usuarioService.fliltrarClientesAsignadosEntrenador(idEntrenador, filtro);

        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaFiltrada);

        return "clientesAsignadosEntrenador";
    }

    @GetMapping("/adminMain/nuevosClientesEntrenador/{id}")
    public String listaClientesSinAsignar(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        UsuarioDTO entrenador = usuarioService.buscarUsuario(id);
        List<UsuarioDTO> listaClientes = usuarioService.buscarClientesSinEntrenadorPorTipoEntrenamiento(entrenador.getTipoEntrenamiento().getTipo().name());

        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaClientes);

        return "clientesSinEntrenador";
    }

    @GetMapping("/adminMain/asignarClienteEntrenador")
    public String asignarClienteEntrenador(@RequestParam("idEntrenador") int idEntrenador, @RequestParam("idCliente") int idCliente, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";

        UsuarioDTO entrenador = usuarioService.buscarUsuario(idEntrenador);
        usuarioService.asignarEntrenadorACliente(idEntrenador, idCliente);

        return "redirect:/adminMain/clientesEntrenador/"+entrenador.getId();
    }

    @PostMapping("/adminMain/filtrar/clientesSinAsignar")
    public String filtrarClientesSinAsignar(@RequestParam("idEntrenador") int idEntrenador, @RequestParam("filtro") String filtro, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";

        UsuarioDTO entrenador = usuarioService.buscarUsuario(idEntrenador);
        List<UsuarioDTO> listaFiltrada = usuarioService.filtrarClientesSinEntrenadorPorTipoEntrenamiento(entrenador.getTipoEntrenamiento().getTipo().name(), filtro);

        model.addAttribute("entrenador", entrenador);
        model.addAttribute("listaClientes", listaFiltrada);
        return "clientesSinEntrenador";
    }

    // RAMA DE LISTA DE CLIENTES //////////////////////////////////////////////////////////////////////

    @GetMapping("/adminMain/clientes")
    public String doClientes(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosByRol("cliente");
        List<UsuarioEntity> listaEntrenadores = usuarioRepository.findUsuariosByRol("entrenador");
        List<UsuarioEntity> entrenadoresBodyBuilding = new ArrayList<>();
        List<UsuarioEntity> entrenadoresCrossTraining = new ArrayList<>();
        for(UsuarioEntity usuario : listaEntrenadores) {
            if(usuario.getTipoEntrenamiento().getId() == 1) {
                entrenadoresBodyBuilding.add(usuario);
            } else {
                entrenadoresCrossTraining.add(usuario);
            }
        }
        model.addAttribute("entrenadoresBodyBuilding", entrenadoresBodyBuilding);
        model.addAttribute("entrenadoresCrossTraining", entrenadoresCrossTraining);
        model.addAttribute("listaClientes", listaClientes);
        return "listaClientes";
    }

    @PostMapping("/adminMain/editarCliente")
    public String editarCliente(@RequestParam("idEntrenador") int idEntrenador, @RequestParam("idCliente") int idCliente, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity cliente = usuarioRepository.findById(idCliente).get();
        if(idEntrenador == 0){ // el entrenador es nulo
            cliente.setEntrenador(null);
        } else {
            UsuarioEntity entrenador = usuarioRepository.findById(idEntrenador).get();
            cliente.setEntrenador(entrenador);
        }
        usuarioRepository.save(cliente);
        return "redirect:/adminMain/clientes";
    }

    @GetMapping("/adminMain/cliente/borrar/{id}")
    public String borrarCliente(@PathVariable("id") int id){

        clienteRepository.deleteById(id);
        usuarioRepository.deleteById(id);

        return "redirect:/adminMain/clientes";
    }

    @PostMapping("/adminMain/filtrar/clientes")
    public String filtrarClientesAsignados(@RequestParam("filtro") String filtro, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";

        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosByRol("cliente");
        List<UsuarioEntity> listaFiltrada = new ArrayList<>();
        for (UsuarioEntity cliente : listaClientes) {
            if(cliente.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getApellidos().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getDni().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(cliente);
            }
        }
        if(listaFiltrada.isEmpty()){
            listaFiltrada = listaClientes;
        }

        List<UsuarioEntity> listaEntrenadores = usuarioRepository.findUsuariosByRol("entrenador");
        List<UsuarioEntity> entrenadoresBodyBuilding = new ArrayList<>();
        List<UsuarioEntity> entrenadoresCrossTraining = new ArrayList<>();
        for(UsuarioEntity usuario : listaEntrenadores) {
            if(usuario.getTipoEntrenamiento().getId() == 1) {
                entrenadoresBodyBuilding.add(usuario);
            } else {
                entrenadoresCrossTraining.add(usuario);
            }
        }
        model.addAttribute("entrenadoresBodyBuilding", entrenadoresBodyBuilding);
        model.addAttribute("entrenadoresCrossTraining", entrenadoresCrossTraining);
        model.addAttribute("listaClientes", listaFiltrada);
        return "listaClientes";
    }

    // RAMA DE LISTA DE SOLICITUDES /////////////////////////////////////////////////////////////////

    @GetMapping("/adminMain/solicitudes")
    public String doSolicitudes(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        byte validado = 0;
        List<UsuarioEntity> listaSolicitudes = usuarioRepository.findClientesByValidado(validado);
        List<UsuarioEntity> listaEntrenadores = new ArrayList<>();
        List<UsuarioEntity> listaClientes = new ArrayList<>();
        for (UsuarioEntity usuario : listaSolicitudes) {
            if(usuario.getRol().getRol().name().equals("entrenador")) {
                listaEntrenadores.add(usuario);
            } else {
                listaClientes.add(usuario);
            }
        }
        model.addAttribute("listaEntrenadores", listaEntrenadores);
        model.addAttribute("listaClientes", listaClientes);
        return "listaSolicitudes";
    }

    @GetMapping("/adminMain/solicitud/aceptar/{id}")
    public String aceptarSolicitud(@PathVariable("id") int id){
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        usuario.setValidado((byte) 1); // validado
        usuario.setFechaIngreso(new Date(System.currentTimeMillis()));
        usuarioRepository.save(usuario);
        return "redirect:/adminMain/solicitudes";
    }

    @GetMapping("/adminMain/solicitud/rechazar/{id}")
    public String rechazarSolicitud(@PathVariable("id") int id){
        usuarioRepository.deleteById(id);
        return "redirect:/adminMain/solicitudes";
    }

    @GetMapping("/adminMain/solicitud/entrenador/{id}")
    public String doSolicitudEntrenador(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        model.addAttribute("usuario", usuario);
        return "solicitudEntrenador";
    }

    @GetMapping("/adminMain/solicitud/cliente/{id}")
    public String doSolicitudCliente(@PathVariable("id") int id, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        ClienteEntity cliente = clienteRepository.findById(id).get();
        model.addAttribute("usuario", usuario);
        model.addAttribute("cliente", cliente);
        return "solicitudCliente";
    }


    // RAMA DE LISTA DE EJERCICIOS /////////////////////////////////////////////////////////////////


    @GetMapping("/adminMain/ejercicios")
    public String doEjercicios(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<EjercicioEntity> listaEjercicios = ejercicioRepository.findAll();
        model.addAttribute("listaEjercicios", listaEjercicios);
        return "listaEjercicios";
    }

    @PostMapping("/adminMain/filtrar/ejercicios")
    public String filtrarEjercicios(@RequestParam("filtro") String filtro, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<EjercicioEntity> listaEjercicios = ejercicioRepository.findAll();
        List<EjercicioEntity> listaFiltrada = new ArrayList<>();
        for (EjercicioEntity ejercicio : listaEjercicios) {
            if(ejercicio.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    ejercicio.getTipoEntrenamiento().getTipo().name().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(ejercicio);
            }
        }
        if(listaFiltrada.isEmpty()){
            listaFiltrada = listaEjercicios;
        }
        model.addAttribute("listaEjercicios", listaFiltrada);
        return "listaEjercicios";
    }

    @GetMapping("/adminMain/borrarEjercicio/{id}")
    public String borrarEjercicio(@PathVariable("id") int id){
        ejercicioRepository.deleteById(id);
        return "redirect:/adminMain/ejercicios";
    }

    @GetMapping("/adminMain/nuevoEjercicio")
    public String doNuevoEjercicio(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<TipoentrenamientoEntity> listaTiposEntrenamiento = tipoentrenamientoEntity.findAll();
        List<TipoejerciciobodybuildingEntity> listaTiposEjercicioBodyBuilding = tipoejerciciobodubuildingRepository.findAll();
        List<TipoejerciciocrosstrainingEntity> listaTiposEjercicioCrossTraining = tipoejerciciocrosstrainingRepository.findAll();
        model.addAttribute("listaTiposEntrenamiento", listaTiposEntrenamiento);
        model.addAttribute("listaTiposEjercicioBodyBuilding", listaTiposEjercicioBodyBuilding);
        model.addAttribute("listaTiposEjercicioCrossTraining", listaTiposEjercicioCrossTraining);
        return "nuevoEjercicio";
    }

    @GetMapping("/adminMain/datosEjercicio/{id}")
    public String doDatosEjercicio(@PathVariable("id") int id, HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        EjercicioEntity ejercicio = ejercicioRepository.findById(id).get();
        List<TipoentrenamientoEntity> listaTiposEntrenamiento = tipoentrenamientoEntity.findAll();
        List<TipoejerciciobodybuildingEntity> listaTiposEjercicioBodyBuilding = tipoejerciciobodubuildingRepository.findAll();
        List<TipoejerciciocrosstrainingEntity> listaTiposEjercicioCrossTraining = tipoejerciciocrosstrainingRepository.findAll();
        model.addAttribute("ejercicio", ejercicio);
        model.addAttribute("listaTiposEntrenamiento", listaTiposEntrenamiento);
        model.addAttribute("listaTiposEjercicioBodyBuilding", listaTiposEjercicioBodyBuilding);
        model.addAttribute("listaTiposEjercicioCrossTraining", listaTiposEjercicioCrossTraining);
        return "datosEjercicio";
    }

    @PostMapping("/adminMain/editarEjercicio")
    public String editarEjercicio(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
                                 @RequestParam("video") String video, @RequestParam("tipoentrenamiento") int tipoentrenamiento,
                                 @RequestParam("tipoejercicio") String tipoejercicio, @RequestParam("id") int id, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        EjercicioEntity nuevoEjercicio = ejercicioRepository.findById(id).get();
        nuevoEjercicio.setNombre(nombre);
        nuevoEjercicio.setDescripcion(descripcion);
        nuevoEjercicio.setVideo(video);
        nuevoEjercicio.setTipoEntrenamiento(tipoentrenamientoEntity.findById(tipoentrenamiento).get());

        String[] partes = tipoejercicio.split("_");
        int idtipoejercicio = Integer.parseInt(partes[1]);
        if (partes[0].equals("bb")) { // bodybuilding
            nuevoEjercicio.setTipoejerciciocrosstrainingId(null);
            nuevoEjercicio.setTipoejerciciobodybuildingId(idtipoejercicio);
        } else{ // crosstraining
            nuevoEjercicio.setTipoejerciciobodybuildingId(null);
            nuevoEjercicio.setTipoejerciciocrosstrainingId(idtipoejercicio);
        }

        ejercicioRepository.save(nuevoEjercicio);
        return "redirect:/adminMain/ejercicios";
    }


    @PostMapping("/adminMain/crearEjercicio")
    public String crearEjercicio(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
                                 @RequestParam("video") String video, @RequestParam("tipoentrenamiento") int tipoentrenamiento,
                                 @RequestParam("tipoejercicio") String tipoejercicio, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        EjercicioEntity nuevoEjercicio = new EjercicioEntity();
        nuevoEjercicio.setNombre(nombre);
        nuevoEjercicio.setDescripcion(descripcion);
        nuevoEjercicio.setVideo(video);
        nuevoEjercicio.setTipoEntrenamiento(tipoentrenamientoEntity.findById(tipoentrenamiento).get());

        String[] partes = tipoejercicio.split("_");
        int idtipoejercicio = Integer.parseInt(partes[1]);
        if (partes[0].equals("bb")) { // bodybuilding
            nuevoEjercicio.setTipoejerciciobodybuildingId(idtipoejercicio);
        } else{ // crosstraining
            nuevoEjercicio.setTipoejerciciocrosstrainingId(idtipoejercicio);
        }

        ejercicioRepository.save(nuevoEjercicio);
        return "redirect:/adminMain/ejercicios";
    }

}
