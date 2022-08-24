package pt.jcbox.shoppinglist.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.jcbox.shoppinglist.model.Acesso;
 
@Repository
@Transactional
public interface AcessoRepository extends CrudRepository<Acesso, Long> {

	@Query("select u from Acesso u where u.utilizador = ?1")
	Acesso findAcessoByUtilizador(String utilizador);
}
