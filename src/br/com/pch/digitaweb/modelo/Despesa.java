package br.com.pch.digitaweb.modelo;

public enum Despesa {
	
	GASES_MEDICINAIS(1), MEDICAMENTO(2), MATERIAL(3), TAXAS_DIVERSAS(4), DIARIAS(5), ALUGUEIS(6);
	
    private int id;
    
    Despesa(int id){
       this.id = id;
    }
    public int getid(){
       return this.id;
    }	

}
