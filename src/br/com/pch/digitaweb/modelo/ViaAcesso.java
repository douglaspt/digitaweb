package br.com.pch.digitaweb.modelo;

public enum ViaAcesso {

	UNICA("U"), MESMA_VIA("M"), DIAS("D");

	private String sigla;

	ViaAcesso(String sigla) {
		this.sigla = sigla;
	}

	public String getsigla() {
		return this.sigla;
	}

}
