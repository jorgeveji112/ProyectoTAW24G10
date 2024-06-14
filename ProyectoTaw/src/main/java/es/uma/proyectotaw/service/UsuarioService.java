package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.ClienteRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//Realizado por Carlos Gálvez Bravo

@Service
public class UsuarioService extends DTOService<UsuarioDTO, UsuarioEntity>{

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

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

    public void borrarUsuario(int id){
        UsuarioEntity usuario = usuarioRepository.findById(id).get();

        if(usuario.getRol().getRol().name().equals("entrenador")){
            borrarEntrenador(id);
        } else if(usuario.getRol().getRol().name().equals("cliente")){
            borrarCliente(id);
        }
    }

    public void borrarEntrenador(int id){
        List<UsuarioEntity> listaClientes = usuarioRepository.findClientesByEntrenadorId(id);
        for (UsuarioEntity cliente : listaClientes) {
            cliente.setEntrenador(null);
            usuarioRepository.save(cliente);
        }
        usuarioRepository.deleteById(id);
    }

    public void borrarCliente(int id){
        clienteRepository.deleteById(id);
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO buscarUsuario(int id){
        return usuarioRepository.findById(id).get().toDTO();
    }


    public List<UsuarioDTO> listarUsuariosPorRol(String rol){
        List<UsuarioEntity> lista = usuarioRepository.findUsuariosByRol(rol);
        return this.entidadesADTO(lista);
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
        UsuarioEntity cliente = usuarioRepository.findById(idCliente).get();

        if(idEntrenador != 0){
            UsuarioEntity entrenador = usuarioRepository.findById(idEntrenador).get();
            cliente.setEntrenador(entrenador);
        } else {
            cliente.setEntrenador(null);
        }

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

    public List<UsuarioDTO> filtrarClientes(String filtro){
        List<UsuarioEntity> listaClientes = usuarioRepository.findUsuariosByRol("cliente");

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

    public List<UsuarioDTO> buscarUsuarioPorRolYValidado(String rol, byte validado){
        List<UsuarioEntity> lista = usuarioRepository.findClientesByValidadoAAndRol(validado, rol);
        return this.entidadesADTO(lista);
    }

    public void aceptarSolicitud(int id){
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        usuario.setValidado((byte)1);
        usuario.setFechaIngreso(new Date(System.currentTimeMillis()));
        usuarioRepository.save(usuario);
    }
}
