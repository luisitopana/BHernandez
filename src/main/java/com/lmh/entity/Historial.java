package com.lmh.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@Entity
@Table(name = "HISTORIAL")
public class Historial {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idhistorial;
	
	private BigDecimal cantidadactual;

	private BigDecimal cantidadmodificar;
	
	private BigDecimal cantidadrestante;
	
	private Integer accion; //1 Compra 2 Reembolso
	
	private String tipomovimiento;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "idusuario")
	private Usuario idusuario;
	
	@Version
	@CurrentTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Integer getIdhistorial() {
		return idhistorial;
	}

	public void setIdhistorial(Integer idhistorial) {
		this.idhistorial = idhistorial;
	}

	public BigDecimal getCantidadactual() {
		return cantidadactual;
	}

	public void setCantidadactual(BigDecimal cantidadactual) {
		this.cantidadactual = cantidadactual;
	}

	public BigDecimal getCantidadmodificar() {
		return cantidadmodificar;
	}

	public void setCantidadmodificar(BigDecimal cantidadmodificar) {
		this.cantidadmodificar = cantidadmodificar;
	}

	public BigDecimal getCantidadrestante() {
		return cantidadrestante;
	}

	public void setCantidadrestante(BigDecimal cantidadrestante) {
		this.cantidadrestante = cantidadrestante;
	}

	public Integer getAccion() {
		return accion;
	}

	public void setAccion(Integer accion) {
		this.accion = accion;
	}

	public String getTipomovimiento() {
		return tipomovimiento;
	}

	public void setTipomovimiento(String tipomovimiento) {
		this.tipomovimiento = tipomovimiento;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
