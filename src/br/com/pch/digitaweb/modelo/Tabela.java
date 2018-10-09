package br.com.pch.digitaweb.modelo;

public enum Tabela {
	
	AMB90(1), AMB92(2), AMB96(3), BRASINDICE(4), SIMPRO(10);
		
    private int id;
    
    Tabela(int id){
       this.id = id;
    }
    public int getid(){
       return this.id;
    }


}
