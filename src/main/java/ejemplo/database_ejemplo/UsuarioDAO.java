package ejemplo.database_ejemplo;

import java.util.List;

import ejemplo.entidad_usuario.Usuario;

public interface UsuarioDAO 
{
	public void crearUsuario(Usuario u) ;
	public void modificarUsuario(Usuario u);
	public void eliminarUsuario(int dni);
	public Usuario consultarUsuario(int dni);
	public List<Usuario> listarUsuarios();
}
