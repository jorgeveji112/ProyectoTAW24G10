package es.uma.proyectotaw.controller;


import es.uma.proyectotaw.entity.*;

import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
// Pablo Pardo Fernández 80% (Listar CLientes, Asignar Rutina, Ver Rutina Asignada, Ver Valoraciones de una Sesion)
@Controller
public class ClienteEntrenadorController extends  BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;

    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;

    @Autowired
    private RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    //Listado de clientes de un entrenador
    @GetMapping("/entrenadorMain/clientes")
    public String doClientes(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<UsuarioEntity> clientes = usuarioRepository.findClientesByEntrenadorId(usuario.getId());
        model.addAttribute("clientes", clientes);
        return "clientesEntrenador";
    }

    @PostMapping("/entrenadorMain/clientes/filtrar")
    public String doFiltrar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<UsuarioEntity> listaClientes = usuarioRepository.findClientesByEntrenadorId(usuario.getId());
        List<UsuarioEntity> listaFiltrada = new ArrayList<>();

        for (UsuarioEntity cliente : listaClientes) {
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

        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        UsuarioEntity cliente = usuarioRepository.findById(clienteId).get();

        List<RutinaPredefinidaEntity> rutinasEntrenador = rutinaPredefinidaRepository.findByUsuario(usuario);
        model.addAttribute("rutinasEntrenador", rutinasEntrenador);
        model.addAttribute("cliente", cliente);
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);

        RutinaAsignadaEntity rutinaAsignada = rutinaAsignadaRepository.findByUsuarioAndFecha(cliente, fechaDate);

            if (rutinaAsignada != null) {
                model.addAttribute("rutinaAsignada", rutinaAsignada);
                List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaAsignada.getRutinaPredefinida());
                List<ValoracionEntity> valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaOrderBySesionejercicio(cliente, rutinaAsignada);
                Map<Integer, Double> mediasValoraciones = new HashMap<>();
                List<Integer> sesionesSinValoracion = new ArrayList<>();
                for (RutinaSesionentrenamientoEntity rutinaSesion : rutinasSesiones) {
                    List<SesionejercicioEntity> ejerciciosSesion = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(rutinaSesion.getSesionentrenamiento())
                            .stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
                    List<ValoracionEntity> valoracionesSesion = valoraciones.stream()
                            .filter(val -> ejerciciosSesion.contains(val.getSesionejercicio()))
                            .toList();
                    if (valoracionesSesion.isEmpty()) {
                        sesionesSinValoracion.add(rutinaSesion.getSesionentrenamiento().getId());
                    } else {
                        double media = valoracionesSesion.stream().mapToDouble(ValoracionEntity::getPuntuacion).average().orElse(0.0);
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
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        UsuarioEntity cliente = usuarioRepository.findById(clienteId).get();
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(sesionId).get();
        RutinaAsignadaEntity rutinaAsignada = rutinaAsignadaRepository.findById(rutinaId).get();
        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesionesEjercicios = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        List<SesionejercicioEntity> sesionesEjercicio = sesionesHasSesionesEjercicios.stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
        List<ValoracionEntity>  valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(cliente,rutinaAsignada, sesionesEjercicio);
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
        RutinaAsignadaEntity rutinaAsignada = new RutinaAsignadaEntity();
        rutinaAsignada.setRutinaPredefinida(rutinaPredefinidaRepository.findById(rutinaId).get());
        rutinaAsignada.setUsuario(usuarioRepository.findById(usuarioId).get());
        rutinaAsignada.setFecha(Date.valueOf(fecha));
        rutinaAsignadaRepository.save(rutinaAsignada);
        return "redirect:/entrenadorMain/clientes/entrenamiento?id="+usuarioId+"&fecha="+fecha.toString(); // Redirige a la página deseada después de asignar la rutina
    }

    @GetMapping("/entrenadorMain/clientes/perfil")
    public String mostrarPerfil(@RequestParam("id") Integer clienteId, Model model, HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = clienteRepository.findById(clienteId).get();
        model.addAttribute("cliente", cliente);
        return "perfilClienteEntrenador";
    }
}
