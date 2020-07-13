package com.geasp.micro.mercancias.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;

@Entity
@Table(name = "tbl_cargas", catalog = "bd_mercancias")
@PrimaryKeyJoinColumn(name = "id")
@Audited
public class Carga extends Mercancia {

	@Column(name = "fd_manifiesto")
	private String manifiesto;
	
	@Column(name = "fd_dm")
	private String dm;
	
	@Column(name = "fd_bl")
	private String bl;
	
	@Column(name = "fd_codigo_contenedor")
	private String codigo_contenedor;
	
	@Column(name = "fd_fecha_desagrupe")
	private LocalDate fecha_desagrupe;
	
	@Column(name = "fd_fecha_descarga")
	private LocalDate fecha_descarga;
	
	@Column(name = "fd_puerto")
	@NotNull
	private String puerto;
	
	/*CONSTRUCTORES*/
	public Carga() {
		super();
	}

	public Carga(Long id, LocalDate fecha_arribo, LocalDate fecha_habilitacion, LocalDate fecha_documentos,
			String situacion, String observaciones, @NotNull String descripcion, @NotNull String cliente,
			@NotNull String importadora, @NotNull EstadoMercancias estado, RevisionMetadata<Long> editVersion,
			String manifiesto, String dm, String bl, String codigo_contenedor, LocalDate fecha_desagrupe,
			LocalDate fecha_descarga, @NotNull String puerto) {
		super(id, fecha_arribo, fecha_habilitacion, fecha_documentos, situacion, observaciones, descripcion, cliente,
				importadora, estado, editVersion);
		this.manifiesto = manifiesto;
		this.dm = dm;
		this.bl = bl;
		this.codigo_contenedor = codigo_contenedor;
		this.fecha_desagrupe = fecha_desagrupe;
		this.fecha_descarga = fecha_descarga;
		this.puerto = puerto;
	}

	public String getManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(String manifiesto) {
		this.manifiesto = manifiesto;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getCodigo_contenedor() {
		return codigo_contenedor;
	}

	public void setCodigo_contenedor(String codigo_contenedor) {
		this.codigo_contenedor = codigo_contenedor;
	}
	
	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public LocalDate getFecha_desagrupe() {
		return fecha_desagrupe;
	}

	public void setFecha_desagrupe(LocalDate fecha_desagrupe) {
		this.fecha_desagrupe = fecha_desagrupe;
	}

	public LocalDate getFecha_descarga() {
		return fecha_descarga;
	}

	public void setFecha_descarga(LocalDate fecha_descarga) {
		this.fecha_descarga = fecha_descarga;
	}
	
}
