package pt.jcbox.shoppinglist.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Integer quantidade;
	private Integer valor;

	// @ForeignKey(name = "listaCompras_id")
	// @ManyToOne

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "listaCompras_id")
	private ListaCompras listaCompras;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public ListaCompras getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(ListaCompras listaCompras) {
		this.listaCompras = listaCompras;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, listaCompras, nome, quantidade, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id) && Objects.equals(listaCompras, other.listaCompras)
				&& Objects.equals(nome, other.nome) && Objects.equals(quantidade, other.quantidade)
				&& Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", quantidade=" + quantidade + ", valor=" + valor
				+ ", listaCompras=" + listaCompras + "]";
	}

}
