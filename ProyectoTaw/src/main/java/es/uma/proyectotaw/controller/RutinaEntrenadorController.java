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

import java.util.ArrayList;
import java.util.List;

// Pablo Pardo Fernández - 80% (Listar/ Crear/Borrar/Ver/Guardar rutinas)
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

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private SesionejercicioRepository sesionejercicioRepository;

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

    @PostMapping("/entrenadorMain/rutinas/filtrar")
    public String doFiltrar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<RutinaPredefinidaEntity> listaRutinas = rutinaPredefinidaRepository.findByUsuario(usuario);
        List<RutinaPredefinidaEntity> listaFiltrada = new ArrayList<>();

        for (RutinaPredefinidaEntity rutina : listaRutinas) {
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
        RutinaPredefinidaEntity rutina = rutinaPredefinidaRepository.findById(id).orElse(null);
        if (rutina == null) return "redirect:/entrenadorMain/rutinas"; // Rutina no encontrada

        // Obtener las sesiones de entrenamiento asociadas a la rutina
        List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutina);

        // Obtener las rutinas asignadas asociadas a la rutina
        List<RutinaAsignadaEntity> rutinasAsignadas = rutinaAsignadaRepository.findByRutinaPredefinida(rutina);

        for (RutinaSesionentrenamientoEntity rutinaSesion : rutinasSesiones) {
            // Eliminar la relación entre la rutina y la sesión de entrenamiento
            rutinaSesionentrenamientoRepository.delete(rutinaSesion);
        }

        // Eliminar las rutinas asignadas
        for (RutinaAsignadaEntity rutinaAsignada : rutinasAsignadas) {
            // Eliminar todas las valoraciones asociadas a la rutina asignada
            List<ValoracionEntity> valoraciones = valoracionRepository.findByRutinaAsignada(rutinaAsignada);
            for (ValoracionEntity valoracion : valoraciones) {
                valoracionRepository.delete(valoracion);
            }
            // Finalmente eliminar la rutina asignada
            rutinaAsignadaRepository.delete(rutinaAsignada);
        }

        // Finalmente, eliminar la rutina
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
