package pt.jcbox.shoppinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.jcbox.shoppinglist.model.ListaCompras;

@Repository
@Transactional
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {

	@Query("select lc from ListaCompras lc where lc.nome like %?1% ")
	List<ListaCompras> findListaComprasByNome(String nomePesquisar);
}
