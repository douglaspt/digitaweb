package br.com.pch.digitaweb.teste;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.ConsultaDao;
import br.com.pch.digitaweb.dao.ItemInternacaoDao;
import br.com.pch.digitaweb.dao.ItemSADTDao;
import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.modelo.TotalProcedimentos;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TestaRelatorio {

	public static void main(String[] args) throws JRException {

		// JasperCompileManager.compileReportToFile("fechamento_subreport3.jrxml");

		EntityManager em = new JPAUtil().getEntityManager();

		String pathJasper = "C:/wCaelum/digitaweb/WebContent/jasper/";
		String pathPDF = "C:/wCaelum/digitaweb/WebContent/pdf/";
		int idPrestador = 701;

		Calendar data = Calendar.getInstance();
		data.set(2018, 1, 1);

		ConsultaDao dao = new ConsultaDao(em);
		List<TotalProcedimentos> consultas = dao.buscaGuiaConsulta(idPrestador, data);
		if(consultas.isEmpty()){
			TotalProcedimentos t1 = new TotalProcedimentos(0l, null, null, "SEM REGISTRO", 0.0);
			consultas.add(t1);
		} else {
			System.out.println("lista Cheia Consultas: " + consultas.size());
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(consultas);

		ItemSADTDao itemSADTDao = new ItemSADTDao(em);
		List<TotalProcedimentos> listaSADT = itemSADTDao.buscaGuiaSADT(idPrestador, data);
		if (listaSADT.isEmpty()) {
			TotalProcedimentos t1 = new TotalProcedimentos(0l, null, null, "SEM REGISTRO", 0.0);
			listaSADT.add(t1);
			System.out.println("lista Vazia : " + listaSADT.size());
		} else {
			System.out.println("lista Cheia : " + listaSADT.size());
		}

		ItemInternacaoDao itemInternacaoDao = new ItemInternacaoDao(em);
		List<TotalProcedimentos> listaInternacao = itemInternacaoDao.buscaGuiaInternacao(idPrestador, data);
		if(listaInternacao.isEmpty()){
			TotalProcedimentos t1 = new TotalProcedimentos(0l, data.getTime(), null, "SEM REGISTRO", 0.0);
			listaInternacao.add(t1);
		}

		for (TotalProcedimentos t : listaInternacao) {
			System.out.println(t.getNome());
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nomePrestador", "IFOR");
		parametros.put("idPrestador", idPrestador);
		parametros.put("dtEmissao", data.getTime());
		parametros.put("qtdeConsulta", 10);
		parametros.put("totalConsulta", 100.0);
		parametros.put("qtdeSadt", 20);
		parametros.put("totalSadt", 200.0);
		parametros.put("qtdeInternacao", 30);
		parametros.put("totalInternacao", 300.0);
		parametros.put("qtdeGeral", 60);
		parametros.put("totalGeral", 600.0);
		parametros.put("SUBREPORT_DIR", pathJasper);
		parametros.put("lista2", listaSADT);
		parametros.put("lista3", listaSADT);

		JasperPrint print = JasperFillManager.fillReport(pathJasper + "fechamento.jasper", parametros, dataSource);
		

		JasperExportManager.exportReportToPdfFile(print, pathPDF + "fechamento.pdf");

		em.close();

	}

}
