package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.dto.*;
import es.uma.proyectotaw.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// Pablo Pardo Fernández - 60% (Listar/ Crear/Borrar/Ver/Guardar rutinas primera version)
//Alba Ruiz Gutiérrez 40% (Filtros primera version y segunda version entera + refactor)
@Controller
public class RutinaEntrenadorController extends BaseController{

    @Autowired
    private RutinaPredefinidaService rutinaPredefinidaService;

    @Autowired
    private RutinaSesionentrenamientoService rutinaSesionentrenamientoService;

    @Autowired
    private SesionentrenamientoService sesionEntrenamientoService;

    @Autowired
    private RutinaAsignadaService rutinaAsignadaService;

    @Autowired
    private ValoracionService valoracionService;

    @GetMapping("/entrenadorMain")
    public String doEntrenadorMain(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "redirect:/entrenadorMain/inicio";
    }
    @GetMapping("/entrenadorMain/inicio")
    public String doMainInicio(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "mainEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        List<RutinaPredefinidaDTO> rutinas = rutinaPredefinidaService.findByUsuario(usuario);
        model.addAttribute("rutinas", rutinas);
        return "rutinasEntrenador";
    }

    @PostMapping("/entrenadorMain/rutinas/filtrar")
    public String doFiltrar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        List<RutinaPredefinidaDTO> listaRutinas = rutinaPredefinidaService.findByUsuario(usuario);
        List<RutinaPredefinidaDTO> listaFiltrada = new ArrayList<>();

        for (RutinaPredefinidaDTO rutina : listaRutinas) {
            if (rutina.getNombre() != null && rutina.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    rutina.getObjetivos() != null && rutina.getObjetivos().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(rutina);
            }
        }

        if(filtro.isEmpty()){
            listaFiltrada = listaRutinas;
        }

        model.addAttribute("filtro", filtro);
        model.addAttribute("rutinas", listaFiltrada);
        model.addAttribute("entrenador", usuario);
        return "rutinasEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas/crear")
    public String doCrearRutina(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaDTO rutina = new RutinaPredefinidaDTO();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        rutina.setUsuario(usuario);
        TipoentrenamientoDTO tipoEntrenamiento = usuario.getTipoEntrenamiento();
        rutina.setTipoentrenamiento(tipoEntrenamiento);
        rutinaPredefinidaService.saveNew(rutina);
        model.addAttribute("rutina", rutina);
        List<SesionentrenamientoDTO> listaSesiones = sesionEntrenamientoService.findByUsuario(usuario);
        model.addAttribute("listaSesiones", listaSesiones);
        return "crearRutinaEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas/borrar")
    public String doBorrarRutina(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaDTO rutina = rutinaPredefinidaService.findById(id);
        if (rutina == null) return "redirect:/entrenadorMain/rutinas"; // Rutina no encontrada

        // Obtener las sesiones de entrenamiento asociadas a la rutina
        List<RutinaSesionentrenamientoDTO> rutinasSesiones = rutinaSesionentrenamientoService.findByRutinaPredefinidaOrderByPosicion(rutina);

        // Obtener las rutinas asignadas asociadas a la rutina
        List<RutinaAsignadaDTO> rutinasAsignadas = rutinaAsignadaService.findByRutinaPredefinida(rutina);

        for (RutinaSesionentrenamientoDTO rutinaSesion : rutinasSesiones) {
            // Eliminar la relación entre la rutina y la sesión de entrenamiento
            rutinaSesionentrenamientoService.delete(rutinaSesion);
        }

        // Eliminar las rutinas asignadas
        for (RutinaAsignadaDTO rutinaAsignada : rutinasAsignadas) {
            // Eliminar todas las valoraciones asociadas a la rutina asignada
            List<ValoracionDTO> valoraciones = valoracionService.findByRutinaAsignada(rutinaAsignada);
            for (ValoracionDTO valoracion : valoraciones) {
                valoracionService.delete(valoracion);
            }
            // Finalmente eliminar la rutina asignada
            rutinaAsignadaService.delete(rutinaAsignada);
        }

        // Finalmente, eliminar la rutina
        rutinaPredefinidaService.delete(rutina);
        return "redirect:/entrenadorMain/rutinas";
    }

    @GetMapping("/entrenadorMain/rutinas/ver")
    public String doVerRutina(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaDTO rutina = rutinaPredefinidaService.findById(id);
        List<RutinaSesionentrenamientoDTO> listaRutinaHasSesion = rutinaSesionentrenamientoService.findByRutinaPredefinidaOrderByPosicion(rutina);

        model.addAttribute("listaRutinaHasSesion", listaRutinaHasSesion);
        model.addAttribute("rutina", rutina);
        List<SesionentrenamientoDTO> listaSesiones = sesionEntrenamientoService.findByUsuario(rutina.getUsuario());
        model.addAttribute("listaSesiones", listaSesiones);

        return "verRutinaEntrenador";
    }

    @PostMapping("/entrenadorMain/rutinas/guardar")
    public String doGuardarRutina(@RequestParam("id") Integer id,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("objetivos") String objetivos,
                                  @RequestParam(value = "sesiones", required = false) List<Integer> sesiones,
                                  HttpSession session){
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaDTO rutina = rutinaPredefinidaService.findById(id);
        rutina.setNombre(nombre);
        rutina.setObjetivos(objetivos);
        rutinaPredefinidaService.save(rutina);

        List<RutinaSesionentrenamientoDTO> rutinasHasSesiones = rutinaSesionentrenamientoService.findByRutinaPredefinidaOrderByPosicion(rutina);
        for (RutinaSesionentrenamientoDTO rutinaHasSesion : rutinasHasSesiones) {
            rutinaSesionentrenamientoService.delete(rutinaHasSesion);
        }
        if(sesiones == null) return "redirect:/entrenadorMain/rutinas";

        for(int i = 0; i < sesiones.size(); i++) {
            RutinaSesionentrenamientoDTO rutinaHasSesion = new RutinaSesionentrenamientoDTO();
            rutinaHasSesion.setRutinaPredefinida(rutina);

            SesionentrenamientoDTO sesion = sesionEntrenamientoService.buscarPorId(i);

            rutinaHasSesion.setSesionentrenamiento(sesion);
            rutinaHasSesion.setPosicion(i);
            rutinaSesionentrenamientoService.saveNew(rutinaHasSesion);
        }

        return "redirect:/entrenadorMain/rutinas";
    }

}
