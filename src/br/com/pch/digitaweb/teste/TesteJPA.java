package br.com.pch.digitaweb.teste;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.EspecialidadeDao;
import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.modelo.Especialidade;

public class TesteJPA {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		EspecialidadeDao dao = new EspecialidadeDao(em);
		
		Especialidade e = new Especialidade();
		e.setId(1234);
		e.setDescricao("teste 3");
		
		em.merge(e);
				
		dao.remove(e);
		
		
		
		

		
		/*
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		EspecialidadePrestadorDao dao = new EspecialidadePrestadorDao(em);
		
		List<EspecialidadePrestador> lista = dao.buscaPorPrestador(2100);
		
		for (EspecialidadePrestador e : lista) {
			System.out.println(e.getEspecialidade().getDescricao());
		}
		*/

		/*
		
		Consulta c = new Consulta();

		CIDDao dao = new CIDDao(em);
		CID cid = dao.buscaPorId("12345");

		c.setCid(cid);
		if (cid == null) {
			System.out.println("NULLOOOO"+cid);
		} else {
			System.out.println(cid.getDescricao());
		}
*/
		/*
		 * BeneficiarioDao dao = new BeneficiarioDao(em); String carteira =
		 * "1208335003"; System.out.println(carteira);
		 * System.out.println(carteira.substring(2, 7)+" - "
		 * +carteira.substring(7, 9));
		 * 
		 * Beneficiario b =
		 * dao.buscaPorInscricaiTit(Integer.parseInt(carteira.substring(2,
		 * 7)),Integer.parseInt(carteira.substring(7, 9)));
		 * 
		 * System.out.println(b.getCarteira()+" - "+b.getNome());
		 */
		/*
		 * 
		 * LoteGuiaDao dao = new LoteGuiaDao(em);
		 * 
		 * Calendar d = Calendar.getInstance(); DateFormat df = new
		 * SimpleDateFormat("dd-MM-yyyy"); System.out.println("Calendar = : " +
		 * df.format(d.getTime()));
		 * 
		 * Calendar c = dao.buscaUltimaReferenciaFechada(248); if (c == null){ c
		 * = Calendar.getInstance(); }
		 * 
		 * //DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 * System.out.println("Calendar = : " + df.format(c.getTime()));
		 * 
		 */
		/*
		 * 
		 * Meses [] meses = Meses.values();
		 * 
		 * for (Meses meses2 : meses) { System.out.println(meses2.getNumMes());
		 * System.out.println(meses2.name()); }
		 * 
		 * 
		 * EntityManager em = new JPAUtil().getEntityManager();
		 * CriteriaQuery<EspecialidadePrestador> query =
		 * em.getCriteriaBuilder().createQuery(EspecialidadePrestador.class);
		 * query.select(query.from(EspecialidadePrestador.class));
		 * 
		 * List<EspecialidadePrestador> lista =
		 * em.createQuery(query).setMaxResults(10).getResultList();
		 * 
		 * for (EspecialidadePrestador b : lista) {
		 * System.out.println(b.getEspecialidade().getDescricao()+" - "
		 * +b.getPrestador().getNome()); }
		 * 
		 * 
		 * em.close();
		 */
	}

}
