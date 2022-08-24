package pt.jcbox.shoppinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.jcbox.shoppinglist.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
	@Query("select t from Produto t where t.listaCompras.id = ?1")
	public List<Produto> getProdutos(Long listaComprasid);

	@Query("select t from Produto t where t.valor IS NOT NULL")
	public List<Produto> getProdutosDefault();

	@Query("select p from Produto p where p.nome = ?1 and p.valor IS NOT NULL")
	public Produto getFindValorId(String listaComprasid);
}
