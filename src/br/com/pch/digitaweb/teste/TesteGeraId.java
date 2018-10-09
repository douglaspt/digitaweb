package br.com.pch.digitaweb.teste;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.ConsultaDao;
import br.com.pch.digitaweb.dao.ItemInternacaoDao;
import br.com.pch.digitaweb.dao.ItemSADTDao;
import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;

public class TesteGeraId {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		List<TotalProcedimentos> consultas = new ArrayList<>();
		List<TotalProcedimentos> listaSADT = new ArrayList<>();
		List<TotalProcedimentos> listaInternacao = new ArrayList<>();
		

		
		ConsultaDao consultaDao = new ConsultaDao(em);
		ItemSADTDao itemSADTDao = new ItemSADTDao(em);
		ItemInternacaoDao itemInternacaoDao = new ItemInternacaoDao(em);
				
		Calendar c = Calendar.getInstance();
		
		consultas = consultaDao.buscaGuiaConsulta(2100, c);
		//listaSADT = itemSADTDao.buscaGuiaSADT(2100, c);
		//listaInternacao = itemInternacaoDao.buscaGuiaInternacao(2100, c);

		
	}	

}
