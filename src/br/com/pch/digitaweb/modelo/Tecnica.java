package br.com.pch.digitaweb.modelo;

public enum Tecnica {
	
	CONVENCIONAL("C"), VIDEOLAPAROSCOPIA("V");
	
	private String sigla;

	Tecnica(String sigla) {
		this.sigla = sigla;
	}

	public String getsigla() {
		return this.sigla;
	}

}
