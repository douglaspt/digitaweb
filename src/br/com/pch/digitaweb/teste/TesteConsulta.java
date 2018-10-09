package br.com.pch.digitaweb.teste;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.dao.PrestadorDao;
import br.com.pch.digitaweb.modelo.LoteGuia;
import br.com.pch.digitaweb.modelo.Prestador;
import br.com.pch.digitaweb.modelo.StatusLote;

public class TesteConsulta {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		Calendar c = Calendar.getInstance();
		System.out.println("Calendar = : " + c);

		c.set(2018, 3, 01);
		Date data = c.getTime();
		System.out.println("Data atual sem formatação: " + data);

		//ConsultaDao dao = new ConsultaDao(em);
		LoteGuiaDao loteGuiDao = new LoteGuiaDao(em);
		
		PrestadorDao prestadorDao = new PrestadorDao(em);
		Prestador prestador = prestadorDao.buscaPorId(2100);
		
		System.out.println(prestador.getNome());
		
		StatusLote status = new StatusLote();
		status.setId(0);
		status.setDescricao("Aberto");
		

		LoteGuia loteGuia = loteGuiDao.buscaPorReferenciaPrestador(prestador.getId(), c);
		if (loteGuia == null) {
			loteGuia = new LoteGuia();
			loteGuia.setPrestador(prestador);
			loteGuia.setReferencia(c);
			loteGuia.setStatus(status);
			loteGuiDao.adiciona(loteGuia);
		}
		
		System.out.println(loteGuia.getStatus().getDescricao()+" - "+loteGuia.getId());



		/*
		 * 
		 * Calendar c = Calendar.getInstance(); System.out.println(
		 * "Calendar = : "+c);
		 * 
		 * c.set(2018, 2, 01); Date data = c.getTime(); System.out.println(
		 * "Data atual sem formatação: "+data);
		 * 
		 * //Formata a data DateFormat formataData =
		 * DateFormat.getDateInstance(); System.out.println(
		 * "Data atual com formatação: "+ formataData.format(data));
		 * 
		 * List<Consulta> lista = dao.buscaPorPrestadorReferencia(698, c);
		 * 
		 * for (Consulta consulta : lista) {
		 * System.out.println(consulta.getNome()+" - "
		 * +consulta.getCid().getDescricao());
		 * 
		 * }
		 */
	}

}
