package com.geasp.micro.mercancias.responses;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuditResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336710812464386013L;
	
	private Long id;
	private Long rev;
	private int revtype;
	private boolean fd_confirmado ;
	private String fd_descripcion;
	private String fd_empresa_cliente;
	private String fd_empresa_importadora;
	private String fd_estado;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Calendar fd_fecha_arribo;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Calendar fd_fecha_documentos;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Calendar fd_fecha_habilitacion;
	private String fd_observaciones;
	private String fd_situacion;
	
	public AuditResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public int getRevtype() {
		return revtype;
	}

	public void setRevtype(int revtype) {
		this.revtype = revtype;
	}

	public boolean isFd_confirmado() {
		return fd_confirmado;
	}

	public void setFd_confirmado(boolean fd_confirmado) {
		this.fd_confirmado = fd_confirmado;
	}

	public String getFd_descripcion() {
		return fd_descripcion;
	}

	public void setFd_descripcion(String fd_descripcion) {
		this.fd_descripcion = fd_descripcion;
	}

	public String getFd_empresa_cliente() {
		return fd_empresa_cliente;
	}

	public void setFd_empresa_cliente(String fd_empresa_cliente) {
		this.fd_empresa_cliente = fd_empresa_cliente;
	}

	public String getFd_empresa_importadora() {
		return fd_empresa_importadora;
	}

	public void setFd_empresa_importadora(String fd_empresa_importadora) {
		this.fd_empresa_importadora = fd_empresa_importadora;
	}

	public String getFd_estado() {
		return fd_estado;
	}

	public void setFd_estado(String fd_estado) {
		this.fd_estado = fd_estado;
	}

	public Calendar getFd_fecha_arribo() {
		return fd_fecha_arribo;
	}

	public void setFd_fecha_arribo(Calendar fd_fecha_arribo) {
		this.fd_fecha_arribo = fd_fecha_arribo;
	}

	public Calendar getFd_fecha_documentos() {
		return fd_fecha_documentos;
	}

	public void setFd_fecha_documentos(Calendar fd_fecha_documentos) {
		this.fd_fecha_documentos = fd_fecha_documentos;
	}

	public Calendar getFd_fecha_habilitacion() {
		return fd_fecha_habilitacion;
	}

	public void setFd_fecha_habilitacion(Calendar fd_fecha_habilitacion) {
		this.fd_fecha_habilitacion = fd_fecha_habilitacion;
	}

	public String getFd_observaciones() {
		return fd_observaciones;
	}

	public void setFd_observaciones(String fd_observaciones) {
		this.fd_observaciones = fd_observaciones;
	}

	public String getFd_situacion() {
		return fd_situacion;
	}

	public void setFd_situacion(String fd_situacion) {
		this.fd_situacion = fd_situacion;
	}

}
