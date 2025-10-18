package com.erickgx.libraryapi.service;

import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.repository.LivroRepository;
import com.erickgx.libraryapi.repository.specs.LivroSpecs;
import com.erickgx.libraryapi.secutiry.SecurityService;
import com.erickgx.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.erickgx.libraryapi.repository.specs.LivroSpecs.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;


    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.getUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);

    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    //Pesquisas orientadas a objeto usando Specifications
    public Page<Livro> pesquisar(
            String isbn,
            String titulo,
            String nomeAutor,
            Genero genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {


        //root representa os dados a receber , a projeção
        // query é a CriteriaQuery emcima do obj , e
        // o CriteriaBuilder é builder do criteria
        //(Metodo abaixo sem criar o pacote Specs da classe, economiza essa linha toda de codigo abaixo)
        //Specification<Livro> isbnEqual = ((root,query,criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));

        // select * from livro where  isbn = :isbn  and nomeAutor = :nomeAutor .....
//        Specification<Livro> specs  =  Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero))
//                ;

        //caso nao pasado nada por parametro vai cair no all - > select * from livro where  0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            //query =  query and isbn = :isbn
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if (titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        //posso tirar o LivroSpecs usando o Import static pra classe LivroSpecs , porem vou deixar para melhor compreencao
        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);


        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getAutor() == null) {
            throw new IllegalArgumentException("Para atualizar é necessario que o livro já esteja salvo na base");
        }
        validator.validar(livro);
        repository.save(livro);
    }
}
