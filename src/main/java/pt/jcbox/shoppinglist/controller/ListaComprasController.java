package pt.jcbox.shoppinglist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pt.jcbox.shoppinglist.model.ListaCompras;
import pt.jcbox.shoppinglist.model.Produto;
import pt.jcbox.shoppinglist.repository.ListaComprasRepository;
import pt.jcbox.shoppinglist.repository.ProdutoRepository;

@Controller
public class ListaComprasController {

	@Autowired
	private ListaComprasRepository listaComprasRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	// lista compras
	@RequestMapping(method = RequestMethod.GET, value = "/listar")
	public ModelAndView listacompras() {
		ModelAndView mv = new ModelAndView("/listacompras/listar");
		Iterable<ListaCompras> listacomprasIt = listaComprasRepository.findAll();
		mv.addObject("listaCompras", listacomprasIt);
		mv.addObject("listaComprasobj", new ListaCompras());
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listacompras/criar")
	public ModelAndView criarListaCompras() {
		ModelAndView mv = new ModelAndView("listacompras/criar");
		Iterable<ListaCompras> listaComprasIt = listaComprasRepository.findAll();
		mv.addObject("listaCompras", listaComprasIt);
		mv.addObject("listaComprasobj", new ListaCompras());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarListaCompras")
	public ModelAndView salvarListaCompras(@Valid ListaCompras listaCompras) {

		if (listaCompras != null && listaCompras.getData() == null || listaCompras.getNome().isEmpty()
				|| listaCompras.getLoja().isEmpty()) {
			ModelAndView mv = new ModelAndView("/listacompras/criar");
			mv.addObject("listaComprasobj", listaCompras);

			List<String> msg = new ArrayList<String>();
			if (listaCompras.getData() == null) {
				msg.add("Data deve ser informado.");
			}
			if (listaCompras.getNome().isEmpty()) {
				msg.add("Nome deve ser informado.");
			}
			if (listaCompras.getLoja().isEmpty()) {
				msg.add("Loja deve ser informada.");
			}
			mv.addObject("msg", msg);
			return mv;
		}

		listaComprasRepository.save(listaCompras);
		ModelAndView mv = new ModelAndView("/listacompras/listar");
		Iterable<ListaCompras> listaComprasIt = listaComprasRepository.findAll();
		mv.addObject("listaCompras", listaComprasIt);
		mv.addObject("listaComprasobj", new ListaCompras());
		return mv;
	}

	@GetMapping("/removerListaCompras/{idListaCompras}")
	public ModelAndView removerListaCompras(@PathVariable("idListaCompras") Long idListaCompras) {
		listaComprasRepository.deleteById(idListaCompras);
		ModelAndView mv = new ModelAndView("/listacompras/listar");
		mv.addObject("listaCompras", listaComprasRepository.findAll());
		mv.addObject("listaComprasobj", new ListaCompras());
		return mv;
	}

	@GetMapping("/editarListaCompras/{idListaCompras}")
	public ModelAndView editarListaCompras(@PathVariable("idListaCompras") Long idListaCompras) {
		Optional<ListaCompras> listaCompras = listaComprasRepository.findById(idListaCompras);
		ModelAndView mv = new ModelAndView("/listaCompras/criar");
		mv.addObject("listaCompras", listaComprasRepository.findAll());
		mv.addObject("listaComprasobj", listaCompras.get());
		return mv;
	}

	@PostMapping("**/pesquisarListaCompras")
	public ModelAndView pesquisarListaCompras(@RequestParam("nomePesquisar") String nomePesquisar) {
		List<ListaCompras> listaCompras = new ArrayList<ListaCompras>();
		listaCompras = listaComprasRepository.findListaComprasByNome(nomePesquisar);
		ModelAndView mv = new ModelAndView("/listacompras/listar");
		mv.addObject("listaCompras", listaCompras);
		mv.addObject("listaComprasobj", new ListaCompras());
		mv.addObject("nomePesquisar", nomePesquisar);
		return mv;
	}

	// produto

	@RequestMapping(method = RequestMethod.GET, value = "/produto/criar")
	public ModelAndView criarProduto() {
		ModelAndView mv = new ModelAndView("produto/criar");
		Iterable<Produto> produtosIt = produtoRepository.getProdutosDefault();
		mv.addObject("produtos", produtosIt);
		mv.addObject("produtoobj", new Produto());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarProduto")
	public ModelAndView salvarProduto(@Valid Produto produto) {
		if (produto.getNome().isBlank() || produto.getValor() == null) {
			ModelAndView mv = new ModelAndView("/produto/criar");
			mv.addObject("produtoobj", produto);

			List<String> msg = new ArrayList<String>();
			if (produto.getNome().isBlank()) {
				msg.add("Nome deve ser informado.");
			}
			if (produto.getValor() == null) {
				msg.add("Valor deve ser informado.");
			}
			mv.addObject("msg", msg);
			return mv;
		}

		produtoRepository.save(produto);
		ModelAndView mv = new ModelAndView("/produto/criar");
		Iterable<Produto> produtosIt = produtoRepository.findAll();
		mv.addObject("produtos", produtosIt);
		mv.addObject("produtoobj", new Produto());
		return mv;
	}

	@PostMapping("**/adicionarProdutoListaCompras/{idListaCompras}")
	public ModelAndView adicionarProdutoListaCompras(Produto produto,
			@PathVariable("idListaCompras") Long idListaCompras) {
		ListaCompras listaCompras = listaComprasRepository.findById(idListaCompras).get();
		if (produto != null && produto.getNome().isEmpty()) {
			ModelAndView mv = new ModelAndView("listaCompras/produtos");
			mv.addObject("listaComprasobj", listaCompras);
			mv.addObject("produtos", produtoRepository.getProdutos(idListaCompras));

			List<String> msg = new ArrayList<String>();
			if (produto.getNome().isEmpty()) {
				msg.add("Nome deve ser informado.");
			}
			mv.addObject("msg", msg);
			return mv;
		}
		produto.setListaCompras(listaCompras);
		produtoRepository.save(produto);
		ModelAndView mv = new ModelAndView("listaCompras/produtos");
		mv.addObject("listaComprasobj", listaCompras);
		mv.addObject("produtoslist", produtoRepository.getProdutosDefault());
		List<Produto> produtos = produtoRepository.getProdutos(idListaCompras);
		for (Produto p : produtos) {
			Produto produtoValor = produtoRepository.getFindValorId(p.getNome());
			p.setValor(produtoValor.getValor() * p.getQuantidade());
		}
		mv.addObject("produtos", produtos);
		return mv;
	}

	@GetMapping("/produtos/{idListaCompras}")
	public ModelAndView listarProdutos(@PathVariable("idListaCompras") Long idListaCompras) {
		Optional<ListaCompras> listaCompras = listaComprasRepository.findById(idListaCompras);
		ModelAndView mv = new ModelAndView("listaCompras/produtos");
		mv.addObject("produtoslist", produtoRepository.getProdutosDefault());
		mv.addObject("listaComprasobj", listaCompras.get());
		mv.addObject("produtos", produtoRepository.getProdutos(idListaCompras));
		return mv;
	}

	@GetMapping("/removerProdutoListaCompras/{idProduto}")
	public ModelAndView removerProdutoListaCompras(@PathVariable("idProduto") Long idProduto) {
		ListaCompras listaCompras = produtoRepository.findById(idProduto).get().getListaCompras();
		produtoRepository.deleteById(idProduto);
		ModelAndView mv = new ModelAndView("listaCompras/produtos");
		mv.addObject("listaComprasobj", listaCompras);
		mv.addObject("produtos", produtoRepository.getProdutos(listaCompras.getId()));
		return mv;
	}

	@GetMapping("/removerProduto/{idProduto}")
	public ModelAndView removerProduto(@PathVariable("idProduto") Long idProduto) {
		produtoRepository.deleteById(idProduto);
		ModelAndView mv = new ModelAndView("produto/criar");
		Iterable<Produto> produtosIt = produtoRepository.getProdutosDefault();
		mv.addObject("produtos", produtosIt);
		mv.addObject("produtoobj", new Produto());
		return mv;
	}

	@GetMapping("/editarProduto/{idProduto}")
	public ModelAndView editarProduto(@PathVariable("idProduto") Long idProduto) {
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		ModelAndView mv = new ModelAndView("/produto/criar");
		mv.addObject("produtos", produtoRepository.findAll());
		mv.addObject("produtoobj", produto.get());
		return mv;
	}
}
