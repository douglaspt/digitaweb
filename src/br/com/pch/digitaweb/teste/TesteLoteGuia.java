package br.com.pch.digitaweb.teste;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.dao.LoteGuiaDao;
import br.com.pch.digitaweb.modelo.LoteGuia;

public class TesteLoteGuia {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		LoteGuiaDao dao = new LoteGuiaDao(em);

		Calendar referencia = Calendar.getInstance();

		referencia.set(2018, 8, 1);

		List<LoteGuia> guias = dao.buscaPorReferencia(referencia);

		for (LoteGuia loteGuia : guias) {
			System.out.println(loteGuia.getDataFechamento().getTime() + " - " + loteGuia.getPrestador().getId() + " "
					+ loteGuia.getStatus().getDescricao());
		}

	}

}
