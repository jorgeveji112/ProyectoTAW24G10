package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.TipoentrenamientoRepository;
import es.uma.proyectotaw.dao.TrolRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Realizado por Carlos Gálvez Bravo

@Service
public class UsuarioService extends DTOService<UsuarioDTO, UsuarioEntity>{

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected TipoentrenamientoRepository tipoentrenamientoRepository;

    @Autowired
    protected TrolRepository trolRepository;

    public List<UsuarioDTO> listarEntrenadoresBodyBuilding(){
        //TrolEntity rol = trolRepository.findByRol(RolEnum.entrenador);
        //TipoentrenamientoEntity tipo = tipoentrenamientoRepository.findByTipo(TipoentrenamientoEnum.body_building);

        List<UsuarioEntity> lista = usuarioRepository.findUsuariosByRolAndTipoEntrenamiento("entrenador", "body_building");
        return this.entidadesADTO(lista);
    }

    public List<UsuarioDTO> listarEntrenadoresCrossTrainig(){
        List<UsuarioEntity> lista = usuarioRepository.findUsuariosByRolAndTipoEntrenamiento("entrenador", "cross_training");
        return this.entidadesADTO(lista);
    }

    public void borrarEntrenador(int id){
        List<UsuarioEntity> listaClientes = usuarioRepository.findClientesByEntrenadorId(id);
        for (UsuarioEntity cliente : listaClientes) {
            cliente.setEntrenador(null);
            usuarioRepository.save(cliente);
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO buscarUsuario(int id){
        return usuarioRepository.findById(id).get().toDTO();
    }

    public List<UsuarioDTO> buscarClientesPorEntrenador(int id){
        List<UsuarioEntity> lista = usuarioRepository.findClientesByEntrenadorId(id);
        List<UsuarioDTO> resultado = new ArrayList<>();
        for (UsuarioEntity usuario : lista) {
            resultado.add(usuario.toDTO());
        }
        return resultado;
    }

    public UsuarioDTO buscarEntrenadorDeCliente(int id){
        UsuarioEntity cliente = usuarioRepository.findById(id).get();
        UsuarioEntity entrenador = cliente.getEntrenador();
        return entrenador.toDTO();
    }

    public void desasignarEntrenadorACliente(int id){
        UsuarioEntity cliente = usuarioRepository.findById(id).get();
        cliente.setEntrenador(null);
        usuarioRepository.save(cliente);
    }

    public List<UsuarioDTO> fliltrarClientesAsignadosEntrenador(int id, String filtro){
        List<UsuarioEntity> listaClientes = usuarioRepository.findClientesByEntrenadorId(id);

        List<UsuarioDTO> listaFiltrada = new ArrayList<>();
        for (UsuarioEntity cliente : listaClientes) {
            if(cliente.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getApellidos().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getDni().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(cliente.toDTO());
            }
        }
        // sino filtramos nada, devolvemos la lista completa
        if(listaFiltrada.isEmpty()){
            listaFiltrada = this.entidadesADTO(listaClientes);
        }
        return listaFiltrada;
    }

    public List<UsuarioDTO> buscarClientesSinEntrenadorPorTipoEntrenamiento(String tipoEntrenamiento){
        List<UsuarioEntity> lista = usuarioRepository.findUsuariosWithoutCoachByTipoEntrenamiento(tipoEntrenamiento);
        return this.entidadesADTO(lista);
    }

    public void asignarEntrenadorACliente(int idEntrenador, int idCliente) {
        UsuarioEntity entrenador = usuarioRepository.findById(idEntrenador).get();
        UsuarioEntity cliente = usuarioRepository.findById(idCliente).get();
        cliente.setEntrenador(entrenador);
        usuarioRepository.save(cliente);
    }

    public List<UsuarioDTO> filtrarClientesSinEntrenadorPorTipoEntrenamiento(String tipoEntrenamiento, String filtro){
        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosWithoutCoachByTipoEntrenamiento(tipoEntrenamiento);

        List<UsuarioDTO> listaFiltrada = new ArrayList<>();
        for (UsuarioEntity cliente : listaClientes) {
            if(cliente.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getApellidos().toLowerCase().contains(filtro.toLowerCase()) ||
                    cliente.getDni().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(cliente.toDTO());
            }
        }
        if(listaFiltrada.isEmpty()){
            listaFiltrada = this.entidadesADTO(listaClientes);
        }
        return listaFiltrada;
    }
}
