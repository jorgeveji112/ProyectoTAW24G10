package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RutinaEntrenadorController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;

    @Autowired
    private RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;

    @GetMapping("/entrenadorMain")
    public String doEntrenadorMain(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "redirect:/entrenadorMain/inicio";
    }
    @GetMapping("/entrenadorMain/inicio")
    public String doMainInicio(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "mainEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<RutinaPredefinidaEntity> rutinas = rutinaPredefinidaRepository.findByUsuario(usuario);
        model.addAttribute("rutinas", rutinas);
        return "rutinasEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas/crear")
    public String doCrearRutina(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaEntity rutina = new RutinaPredefinidaEntity();
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        rutina.setUsuario(usuario);
        TipoentrenamientoEntity tipoEntrenamiento = usuario.getTipoEntrenamiento();
        rutina.setTipoEntrenamiento(tipoEntrenamiento);
        rutinaPredefinidaRepository.save(rutina);
        model.addAttribute("rutina", rutina);
        List<SesionentrenamientoEntity> listaSesiones = sesionentrenamientoRepository.findByUsuario(usuario);
        model.addAttribute("listaSesiones", listaSesiones);
        return "crearRutinaEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas/borrar")
    public String doBorrarRutina(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaEntity rutina = rutinaPredefinidaRepository.findById(id).get();
        List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutina);
        for (RutinaSesionentrenamientoEntity rutinaSesion : rutinasSesiones) {
            rutinaSesionentrenamientoRepository.delete(rutinaSesion);
        }
        List<RutinaAsignadaEntity> rutinasAsignadas = rutinaAsignadaRepository.findByRutinaPredefinida(rutina);
        for (RutinaAsignadaEntity rutinaAsignada : rutinasAsignadas) {
            rutinaAsignadaRepository.delete(rutinaAsignada);
        }
        rutinaPredefinidaRepository.delete(rutina);
        return "redirect:/entrenadorMain/rutinas";
    }

    @GetMapping("/entrenadorMain/rutinas/ver")
    public String doVerRutina(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaPredefinidaEntity rutina = rutinaPredefinidaRepository.findById(id).get();
        List<RutinaSesionentrenamientoEntity> listaRutinaHasSesion = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutina);

        model.addAttribute("listaRutinaHasSesion", listaRutinaHasSesion);
        model.addAttribute("rutina", rutina);
        List<SesionentrenamientoEntity> listaSesiones = sesionentrenamientoRepository.findByUsuario(rutina.getUsuario());
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
        RutinaPredefinidaEntity rutina = rutinaPredefinidaRepository.findById(id).get();
        rutina.setNombre(nombre);
        rutina.setObjetivos(objetivos);
        rutinaPredefinidaRepository.save(rutina);

        List<RutinaSesionentrenamientoEntity> rutinasHasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutina);
        for (RutinaSesionentrenamientoEntity rutinaHasSesion : rutinasHasSesiones) {
            rutinaSesionentrenamientoRepository.delete(rutinaHasSesion);
        }
        if(sesiones == null) return "redirect:/entrenadorMain/rutinas";

        for(int i = 0; i < sesiones.size(); i++) {
            RutinaSesionentrenamientoEntity rutinaHasSesion = new RutinaSesionentrenamientoEntity();
            rutinaHasSesion.setRutinaPredefinida(rutina);

            SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(sesiones.get(i)).get();

            rutinaHasSesion.setSesionentrenamiento(sesion);
            rutinaHasSesion.setPosicion(i);
            rutinaSesionentrenamientoRepository.save(rutinaHasSesion);
        }

        return "redirect:/entrenadorMain/rutinas";
    }

}
