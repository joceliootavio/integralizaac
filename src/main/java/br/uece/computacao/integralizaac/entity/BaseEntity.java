package br.uece.computacao.integralizaac.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Jocélio Otávio
 *
 * Classe pai de todas as entidades do sistema. Ao herdar
 * dessa classe, a classe filha herda também o atributo id
 * que é a identidade de todas as entidades do sistema, 
 * sendo a representação da chave primária da tabela do 
 * banco de dados que irá armazenar os dados de cada 
 * entidade.
 * 
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 5277333518478300359L;

	/**
	 * Campo do tipo inteiro que guarda a chave primária que
	 * identifica a entidade.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	
	public boolean isPesistido() {
		return id != null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/* O método equals é sobrescrito no intuito de utilizar 
	 * o id como forma de verificação ao comparar dois 
	 * objetos do tipo BaseEntity, inclusive as classes filhas.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
