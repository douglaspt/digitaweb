package br.com.pch.digitaweb.relatorio;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class GeradorRelatorio {

	private String nomeArquivo;
	private Map<String, Object> parametros;
	private JRBeanCollectionDataSource datasourse;

	public GeradorRelatorio(String nomeArquivo, Map<String, Object> parametros, JRBeanCollectionDataSource datasourse) {
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
		this.datasourse = datasourse;
	}

	public void geraPDFParaOutputStream(OutputStream outputStream, String pathPDF) throws FileNotFoundException {

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, datasourse);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();

		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	


}
