package es.uma.proyectotaw.controller;


import es.uma.proyectotaw.dto.*;
import es.uma.proyectotaw.entity.*;

import es.uma.proyectotaw.dao.*;
import es.uma.proyectotaw.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
// Pablo Pardo Fernández 50% (Listar CLientes, Asignar Rutina, Ver Rutina Asignada, Ver Valoraciones de una Sesion primera version)
//Alba Ruiz Gutiérrez 50% (Filtros primera version y segunda version entera + refactor)
@Controller
public class ClienteEntrenadorController extends  BaseController{


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RutinaPredefinidaService rutinaPredefinidaService;

    @Autowired
    private RutinaAsignadaService rutinaAsignadaService;

    @Autowired
    private RutinaSesionentrenamientoService rutinaSesionentrenamientoService;

    @Autowired
    private SesionentrenamientoService sesionEntrenamientoService;

    @Autowired
    private SesionentrenamientoHasSesionejercicioService sesionentrenamientoHasSesionejercicioService;

    @Autowired
    private ValoracionService valoracionService;

    //Listado de clientes de un entrenador
    @GetMapping("/entrenadorMain/clientes")
    public String doClientes(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        List<UsuarioDTO> clientes = usuarioService.buscarClientesPorEntrenador(usuario.getId());
        model.addAttribute("clientes", clientes);
        return "clientesEntrenador";
    }

    @PostMapping("/entrenadorMain/clientes/filtrar")
    public String doFiltrar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        List<UsuarioDTO> listaClientes = usuarioService.buscarClientesPorEntrenador(usuario.getId());
        List<UsuarioDTO> listaFiltrada = new ArrayList<>();

        for (UsuarioDTO cliente : listaClientes) {
            if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getApellidos() != null && cliente.getApellidos().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getDni() != null && cliente.getDni().toLowerCase().contains(filtro.toLowerCase())) {
                listaFiltrada.add(cliente);
            }
        }

        if(filtro.isEmpty()){
            listaFiltrada = listaClientes;
        }

        model.addAttribute("filtro", filtro);
        model.addAttribute("clientes", listaFiltrada);
        model.addAttribute("entrenador", usuario);
        return "clientesEntrenador";
    }


    @GetMapping("/entrenadorMain/clientes/entrenamiento")
    public String doClientesEntrenamiento(@RequestParam("id") Integer clienteId, @RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        UsuarioDTO cliente = usuarioService.buscarUsuario(clienteId);

        List<RutinaPredefinidaDTO> rutinasEntrenador = rutinaPredefinidaService.findByUsuario(usuario);
        model.addAttribute("rutinasEntrenador", rutinasEntrenador);
        model.addAttribute("cliente", cliente);
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);

        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorUsuarioYFecha(cliente, fechaDate);

            if (rutinaAsignada != null) {
                model.addAttribute("rutinaAsignada", rutinaAsignada);
                List<RutinaSesionentrenamientoDTO> rutinasSesiones = rutinaSesionentrenamientoService.buscarPorRutinaPredefinidaOrdenadaPorPosicion(rutinaAsignada.getRutinaPredefinida());
                List<ValoracionDTO> valoraciones = valoracionService.buscarPorUsuarioYRutinaAsignadaOrdenadoPorSesionejercicio(cliente.getId(), rutinaAsignada.getId());
                Map<Integer, Double> mediasValoraciones = new HashMap<>();
                List<Integer> sesionesSinValoracion = new ArrayList<>();
                for (RutinaSesionentrenamientoDTO rutinaSesion : rutinasSesiones) {
                    List<SesionejercicioDTO> ejerciciosSesion = sesionentrenamientoHasSesionejercicioService.buscarPorSesionentrenamientoOrdenadoPorPosicion(rutinaSesion.getSesionentrenamiento().getId()).stream().map(SesionentrenamientoHasSesionejercicioDTO::getSesionejercicio).toList();
                    List<ValoracionDTO> valoracionesSesion = valoraciones.stream()
                            .filter(val -> ejerciciosSesion.contains(val.getSesionejercicio()))
                            .toList();
                    if (valoracionesSesion.isEmpty()) {
                        sesionesSinValoracion.add(rutinaSesion.getSesionentrenamiento().getId());
                    } else {
                        double media = valoracionesSesion.stream().mapToDouble(ValoracionDTO::getPuntuacion).average().orElse(0.0);
                        mediasValoraciones.put(rutinaSesion.getSesionentrenamiento().getId(), media);
                    }
                }
                model.addAttribute("sesionesSinValoracion", sesionesSinValoracion);
                model.addAttribute("mediasValoraciones", mediasValoraciones);
                model.addAttribute("rutinasSesiones", rutinasSesiones);
            } else {
                model.addAttribute("rutinaAsignada", null);
            }

            return "asignarRutinaEntrenador";
        }


    @GetMapping("/entrenadorMain/clientes/entrenamiento/sesion")
    public String verValoracionSesion(@RequestParam("id") Integer clienteId, @RequestParam("rutina") Integer rutinaId ,@RequestParam("sesion") Integer sesionId,Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        UsuarioDTO cliente = usuarioService.buscarUsuario(clienteId);
        SesionentrenamientoDTO sesion = sesionEntrenamientoService.buscarPorId(sesionId);
        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorId(rutinaId);
        List<SesionentrenamientoHasSesionejercicioDTO> sesionesHasSesionesEjercicios = sesionentrenamientoHasSesionejercicioService.buscarPorSesionentrenamientoOrdenadoPorPosicion(sesion.getId());
        List<SesionejercicioDTO> sesionesEjercicio = sesionesHasSesionesEjercicios.stream().map(SesionentrenamientoHasSesionejercicioDTO::getSesionejercicio).toList();
        List<ValoracionDTO>  valoraciones = valoracionService.buscarPorUsuarioYRutinaAsignadaYSesionejercicioDentro(cliente.getId(),rutinaAsignada.getId(), sesionesEjercicio);
        LocalDate fechaLocal = rutinaAsignada.getFecha().toLocalDate();
        model.addAttribute("rutinaAsignada", rutinaAsignada);
        model.addAttribute("semana", fechaLocal);
        model.addAttribute("sesion", sesion);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("sesionesEjercicio", sesionesEjercicio);
        model.addAttribute("cliente", cliente);
        return "valoracionesSesionEntrenador";

    }

    @PostMapping("/entrenadorMain/clientes/entrenamiento/asignarRutina")
    public String crearRutinaAsignada(@RequestParam("rutinaId") Integer rutinaId, @RequestParam("usuarioId") Integer usuarioId,@RequestParam("fecha") LocalDate fecha, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaAsignadaDTO rutinaAsignada = new RutinaAsignadaDTO();
        rutinaAsignada.setRutinaPredefinida(rutinaPredefinidaService.findById(rutinaId));
        rutinaAsignada.setUsuario(usuarioService.buscarUsuario(usuarioId));
        rutinaAsignada.setFecha(Date.valueOf(fecha));
        rutinaAsignadaService.save(rutinaAsignada);
        return "redirect:/entrenadorMain/clientes/entrenamiento?id="+usuarioId+"&fecha="+fecha.toString(); // Redirige a la página deseada después de asignar la rutina
    }

    @GetMapping("/entrenadorMain/clientes/perfil")
    public String mostrarPerfil(@RequestParam("id") Integer clienteId, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteDTO cliente = clienteService.buscarCliente(clienteId);
        model.addAttribute("cliente", cliente);
        return "perfilClienteEntrenador";
    }
}
