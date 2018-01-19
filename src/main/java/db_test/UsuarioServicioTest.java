package db_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.UsuarioServicio;
import entidad_usuario.Usuario;
import utilidades.FechaUtil;

public class UsuarioServicioTest 
{
	private UsuarioServicio servicio = new UsuarioServicio();

	@Before
	public void crearTabla()
	{
		servicio.crearTabla();
	}
	
	@After
	public void eliminarTabla()
	{
		servicio.eliminarTabla();
	}
	
	
	@Test
	public void consultaUsuario() 
	{
		Usuario expected = new Usuario(321, "sdfeq", FechaUtil.crearFecha(1,01,2000));
		Usuario actual = null;
		servicio.crearUsuario(expected);
		actual = servicio.consultarUsuario(expected.getDni()); 
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void listarUsuarios()
	{
		Usuario usuario1 = new Usuario(321, "sdfeq", FechaUtil.crearFecha(1,01,2000));
		Usuario usuario2 = new Usuario(123, "asd", FechaUtil.crearFecha(16,01,1999));
		
		List<Usuario> expected = new ArrayList<>();
		expected.add(usuario1);
		expected.add(usuario2);
		
		servicio.crearUsuario(usuario1);
		servicio.crearUsuario(usuario2);
		
		List<Usuario> actual = servicio.listarUsuarios();
		
		Assert.assertListasIguales(expected, actual);
	}
	
	@Test
	public void modificarUsuario()
	{
		Usuario usuario = new Usuario(321, "sdfeq", FechaUtil.crearFecha(1,01,2000));
		servicio.crearUsuario(usuario);
		usuario.setNombre("sssss");
		servicio.modificarUsuario(usuario);
		
		Usuario actual = servicio.consultarUsuario(usuario.getDni());
		
		assertEquals(usuario, actual);	
	}
	
	@Test
	public void eliminarUsuario()
	{
		Usuario usuario1 = new Usuario(321, "sdfeq", FechaUtil.crearFecha(1,01,2000));
		Usuario usuario2 = new Usuario(123, "asd", FechaUtil.crearFecha(16,01,1999));
		
		List<Usuario> expected = new ArrayList<>();
		expected.add(usuario1);
		
		servicio.crearUsuario(usuario1);
		servicio.crearUsuario(usuario2);
		servicio.eliminarUsuario(usuario2.getDni());
		
		List<Usuario> actual = servicio.listarUsuarios();
		
		Assert.assertListasIguales(expected, actual);
	}
}
