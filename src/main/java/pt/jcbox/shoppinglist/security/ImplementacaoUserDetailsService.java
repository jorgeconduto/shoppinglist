package pt.jcbox.shoppinglist.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pt.jcbox.shoppinglist.model.Acesso;
import pt.jcbox.shoppinglist.repository.AcessoRepository; 
  
@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private AcessoRepository acessoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Acesso acesso = acessoRepository.findAcessoByUtilizador(username);

		if (acesso == null) {
			throw new UsernameNotFoundException("Acesso não foi encontrado");
		}
		return new User(acesso.getUtilizador(), acesso.getSenha(), acesso.isEnabled(), true, true, true, acesso.getAuthorities());
	}
}