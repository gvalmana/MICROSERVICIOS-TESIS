package com.geasp.micro.empresas.models;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;


@Document
public class Cliente{
	
	@Id
	private String id;
	@Indexed(unique = true)
	@NonNull
	private String nombre;
	private String direccion;
	@NonNull
	@Indexed(unique = true)
	private String reeup;
	@Indexed(unique = true)
	@NonNull
	private String nit;
	@NonNull
	private String banco;
	@NonNull
	private String sucursal;
	@Indexed(unique = true)
	private String cup;
	@Indexed(unique = true)
	private String cuc;
	private String representante;
	private String resolucion_representante;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_resolucion;
	private String resolucion_emisor;
	private List<String> actividad;
	private boolean subordinada;
	
	public Cliente() {
		super();
		this.actividad = new ArrayList<String>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReeup() {
		return reeup;
	}

	public void setReeup(String reeup) {
		this.reeup = reeup;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCuc() {
		return cuc;
	}

	public void setCuc(String cuc) {
		this.cuc = cuc;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getResolucion_representante() {
		return resolucion_representante;
	}

	public void setResolucion_representante(String resolucion_representante) {
		this.resolucion_representante = resolucion_representante;
	}

	public LocalDate getFecha_resolucion() {
		return fecha_resolucion;
	}

	public void setFecha_resolucion(LocalDate fecha_resolucion) {
		this.fecha_resolucion = fecha_resolucion;
	}

	public String getResolucion_emisor() {
		return resolucion_emisor;
	}

	public void setResolucion_emisor(String resolucion_emisor) {
		this.resolucion_emisor = resolucion_emisor;
	}

	public List<String> getActividad() {
		return actividad;
	}

	public void setActividad(List<String> actividad) {
		this.actividad = actividad;
	}

	public boolean isSubordinada() {
		return subordinada;
	}

	public void setSubordinada(boolean subordinada) {
		this.subordinada = subordinada;
	}
	
}
