package com.geasp.micro.empresas.models;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "clientes")
public class Cliente extends Item{
	
	private String nombre;
	private String correo;
	private String telefono;
	private String direccion;
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
