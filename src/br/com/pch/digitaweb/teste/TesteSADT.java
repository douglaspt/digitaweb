package br.com.pch.digitaweb.teste;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.pch.digitaweb.dao.JPAUtil;
import br.com.pch.digitaweb.dao.OutrasDespesasDao;
import br.com.pch.digitaweb.modelo.OutrasDespesas;

public class TesteSADT {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getEntityManager();

		//ItemSADTDao dao = new ItemSADTDao(em);
		OutrasDespesasDao odao = new OutrasDespesasDao(em);

		Calendar data = Calendar.getInstance();
		data.set(2018, 2, 1);
		/*
		 * List<TotalProcedimentos> itens =
		 * dao.buscaTotalPorPrestadorReferencia(2100, data);
		 * 
		 * 
		 * for (TotalProcedimentos t : itens) { System.out.println(t.getId()+
		 * " - "+t.getValor()); }
		 * 
		 * List<TotalProcedimentos> guias = dao.buscaGuiaSADT(2100, data);
		 * System.out.println("*****************************************"); for
		 * (TotalProcedimentos g : guias) { System.out.println(g.getId()+" - "
		 * +g.getValor()); }
		 * 
		 * System.out.println("*********************");
		 * 
		 * List<ItemSADT> its = dao.buscaPorSADT(1529090964421l); for (ItemSADT
		 * i : its) { System.out.println(i.getDescricao()); }
		 */
		System.out.println("*********************");

		OutrasDespesas otd = odao.buscaPorId(1529090964561l);
		System.out.println("Outras = " + otd.getDescricao());

		List<OutrasDespesas> despesas = odao.buscaPorIdGuia(1529090964421l);
		for (OutrasDespesas od : despesas) {
			System.out.println(od.getDescricao());

		}

		/*
		 * 
		 * 
		 * CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		 * CriteriaQuery<TotalProcedimentos2> query =
		 * criteriaBuilder.createQuery(TotalProcedimentos2.class);
		 * Root<ItemSADT> item = query.from(ItemSADT.class); //
		 * query.select(item, criteriaBuilder.sum(item.<Double> get("valor")));
		 * 
		 * query.multiselect(item.<SADT> get("sadt").<Long> get("id"),
		 * item.<SADT> get("sadt").<Calendar> get("dataAtendimento"),
		 * item.<SADT> get("sadt").<String> get("carteira"), item.<SADT>
		 * get("sadt").<String> get("nome")); criteriaBuilder.sum(item.<Double>
		 * get("valor"));
		 * 
		 * 
		 * query.select(criteriaBuilder.construct(TotalProcedimentos2.class,
		 * item.<SADT> get("sadt").<Long> get("id"), item.<SADT>
		 * get("sadt").<String> get("carteira") ) );
		 * 
		 * 
		 * Path<Integer> prestadorPath = item.<SADT> get("sadt").<Prestador>
		 * get("prestador").<Integer> get("id"); Path<Calendar> referenciaPath =
		 * item.<SADT> get("sadt").<Calendar> get("dataEmissao");
		 * 
		 * List<Predicate> predicates = new ArrayList<Predicate>();
		 * 
		 * Predicate prestadorIgual = criteriaBuilder.equal(prestadorPath,
		 * 2100); predicates.add(prestadorIgual);
		 * 
		 * Calendar data = Calendar.getInstance(); data.set(2018, 2, 1);
		 * 
		 * Predicate referenciaIgual = criteriaBuilder.equal(referenciaPath,
		 * data); predicates.add(referenciaIgual);
		 * 
		 * query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		 * 
		 * query.groupBy(item.<SADT> get("sadt").<Long> get("id"), item.<SADT>
		 * get("sadt").<String> get("carteira"));
		 * 
		 * TypedQuery<TotalProcedimentos2> typedQuery = em.createQuery(query);
		 * 
		 * List<TotalProcedimentos2> lista = typedQuery.getResultList();
		 * 
		 * for (TotalProcedimentos2 t : lista) { System.out.println(t.getId());
		 * 
		 * }
		 * 
		 * 
		 * for (TotalProcedimentos i : itens) { //System.out.println(i[0]+" - "
		 * +i[4]+" - "+i[2]+" - "+ i[3]); System.out.println(i.getNome()+" - "
		 * +i.getValor());
		 * 
		 * }
		 */
		/*
		 * SADT sadt = new SADT();
		 * 
		 * 
		 * 
		 * PrestadorDao pdao = new PrestadorDao(em); Prestador p =
		 * pdao.buscaPorId(2100); sadt.setPrestador(p);
		 * 
		 * sadt.setDataAtendimento(c); // sadt.setConselhoProfissional(");
		 * 
		 * ProfissionalDao pfdao = new ProfissionalDao(em); Profissional pf =
		 * pfdao.buscaPorId(1234);
		 * 
		 * sadt.setProfissional(pf); // sadt.setUf("SP");
		 * sadt.setCarteira("8215477508"); TipoAtendimento ta = new
		 * TipoAtendimento(); ta.setId(1); ta.setDescricao("Remoção");
		 * 
		 * sadt.setTipoAtendimento(ta); TipoSaida ts = new TipoSaida();
		 * ts.setId(1); ts.setDescricao("Retorno");
		 * 
		 * sadt.setTipoSaida(ts); sadt.setLogin("douglas"); //
		 * sadt.setDataSistema(dataSistema); c.set(2018, 2, 01);
		 * sadt.setDataEmissao(c); sadt.setNome("ADRIANA DA SILVA ASSIS"); CID
		 * cid = new CID(); sadt.setCid(cid); // sadt.setAutorizacao("0");
		 * 
		 * 
		 * em.getTransaction().begin();
		 * 
		 * 
		 * em.persist(sadt); System.out.println("Aquuuiii 0");
		 * //em.getTransaction().commit();
		 * 
		 * // add item 1
		 * 
		 * ItemSADT isadt = new ItemSADT(); isadt.setSadt(sadt);
		 * System.out.println(sadt.getId()); isadt.setDataRealizacao(c); //
		 * isadt.setTabela(1); ItemDao itemDao = new ItemDao(em); Item item =
		 * itemDao.buscaPorId(12010014); System.out.println(item.getNome());
		 * isadt.setItem(item); isadt.setQuantidade(1); //
		 * isadt.setViaAcesso("U"); //
		 * isadt.setTecnicaUtilizada(tecnicaUtilizada); //
		 * isadt.setPercentual(percentual); isadt.setValor("100");
		 * isadt.setDescricao(item.getNome()); System.out.println("Aquuuiii 1");
		 * em.persist(isadt);
		 * 
		 * 
		 * 
		 * //em.getTransaction().commit();
		 * 
		 * 
		 * 
		 * // add item 2
		 * 
		 * 
		 * ItemSADT isadt2 = new ItemSADT(); System.out.println("Aqui 1");
		 * 
		 * //SADT sadt2 = sadt; isadt2.setSadt(sadt);
		 * isadt2.setDataRealizacao(c); // isadt.setTabela(1);
		 * System.out.println("Aqui 2");
		 * 
		 * Item item2 = itemDao.buscaPorId(13010026);
		 * 
		 * isadt2.setItem(item2); isadt2.setQuantidade(1); //
		 * isadt.setViaAcesso("U"); //
		 * isadt.setTecnicaUtilizada(tecnicaUtilizada); //
		 * isadt.setPercentual(percentual); isadt2.setValor("200");
		 * 
		 * isadt2.setDescricao(item2.getNome());
		 * 
		 * em.persist(isadt2);
		 * 
		 * 
		 * em.getTransaction().commit();
		 */
	}

}
