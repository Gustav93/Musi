package entidad_usuario;

import java.util.Date;

import utilidades.FechaUtil;



public class Usuario 
{
	private Integer dni;
	private String nombre;
	private Date fecha_nacimiento;
	
	public Usuario(int dni, String nombre, Date fecha)
	{
		this.dni = dni;
		this.nombre = nombre;
		this.fecha_nacimiento = fecha;
	}
	
	public Usuario()
	{
		this.dni = null;
		this.nombre = null;
		this.fecha_nacimiento = null;
	}

	public Integer getDni() 
	{
		return dni;
	}

	public void setDni(int dni) 
	{
		this.dni = dni;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Date getFechaDenacimiento() 
	{
		return fecha_nacimiento;
	}
	
    public void setFechaDenacimiento(Date fecha) 
	{
		this.fecha_nacimiento = fecha;
	}
	
	@Override
	public String toString() {
	    return "Usuario{" +
	            "dni:" + dni +
	            ", Nombre: " + nombre +
	            ", fecha: " + fecha_nacimiento +
	            '}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	public static void main(String[] args) 
	{
		Usuario u = new Usuario(37197224, "Gustavo", FechaUtil.crearFecha(16,01,1993));
		System.out.println(u);
	}
}
