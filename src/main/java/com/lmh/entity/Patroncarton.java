package com.lmh.entity;

import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@Entity
@Table(name = "PATRONCARTON")
public class Patroncarton {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patroncarton_seq")
	@SequenceGenerator(name="patroncarton_seq", sequenceName = "seq_patroncarton", allocationSize=1)
	private Integer idpatroncarton;
	
	private String n1;
	private String n2;
	private String n3;
	private String n4;
	private String n5;
	private String n6;
	private String n7;
	private String n8;
	private String n9;
	private String n10;
	private String n11;
	private String n12;
	private String n13;
	private String n14;
	private String n15;
	private String n16;
	private String n17;
	private String n18;
	private String n19;
	private String n20;
	private String n21;
	private String n22;
	private String n23;
	private String n24;
	private String n25;
	private String n26;
	private String n27;
	
	@Version
	@CurrentTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	public Integer getIdpatroncarton() {
		return idpatroncarton;
	}

	public void setIdpatroncarton(Integer idpatroncarton) {
		this.idpatroncarton = idpatroncarton;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	public String getN3() {
		return n3;
	}

	public void setN3(String n3) {
		this.n3 = n3;
	}

	public String getN4() {
		return n4;
	}

	public void setN4(String n4) {
		this.n4 = n4;
	}

	public String getN5() {
		return n5;
	}

	public void setN5(String n5) {
		this.n5 = n5;
	}

	public String getN6() {
		return n6;
	}

	public void setN6(String n6) {
		this.n6 = n6;
	}

	public String getN7() {
		return n7;
	}

	public void setN7(String n7) {
		this.n7 = n7;
	}

	public String getN8() {
		return n8;
	}

	public void setN8(String n8) {
		this.n8 = n8;
	}

	public String getN9() {
		return n9;
	}

	public void setN9(String n9) {
		this.n9 = n9;
	}

	public String getN10() {
		return n10;
	}

	public void setN10(String n10) {
		this.n10 = n10;
	}

	public String getN11() {
		return n11;
	}

	public void setN11(String n11) {
		this.n11 = n11;
	}

	public String getN12() {
		return n12;
	}

	public void setN12(String n12) {
		this.n12 = n12;
	}

	public String getN13() {
		return n13;
	}

	public void setN13(String n13) {
		this.n13 = n13;
	}

	public String getN14() {
		return n14;
	}

	public void setN14(String n14) {
		this.n14 = n14;
	}

	public String getN15() {
		return n15;
	}

	public void setN15(String n15) {
		this.n15 = n15;
	}

	public String getN16() {
		return n16;
	}

	public void setN16(String n16) {
		this.n16 = n16;
	}

	public String getN17() {
		return n17;
	}

	public void setN17(String n17) {
		this.n17 = n17;
	}

	public String getN18() {
		return n18;
	}

	public void setN18(String n18) {
		this.n18 = n18;
	}

	public String getN19() {
		return n19;
	}

	public void setN19(String n19) {
		this.n19 = n19;
	}

	public String getN20() {
		return n20;
	}

	public void setN20(String n20) {
		this.n20 = n20;
	}

	public String getN21() {
		return n21;
	}

	public void setN21(String n21) {
		this.n21 = n21;
	}

	public String getN22() {
		return n22;
	}

	public void setN22(String n22) {
		this.n22 = n22;
	}

	public String getN23() {
		return n23;
	}

	public void setN23(String n23) {
		this.n23 = n23;
	}

	public String getN24() {
		return n24;
	}

	public void setN24(String n24) {
		this.n24 = n24;
	}

	public String getN25() {
		return n25;
	}

	public void setN25(String n25) {
		this.n25 = n25;
	}

	public String getN26() {
		return n26;
	}

	public void setN26(String n26) {
		this.n26 = n26;
	}

	public String getN27() {
		return n27;
	}

	public void setN27(String n27) {
		this.n27 = n27;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
