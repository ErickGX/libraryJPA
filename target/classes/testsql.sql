select livro.id,
livro.id_autor,
autor.id,
autor.data_nascimento,
autor.nacionalidade,
autor.nome,
livro.data_publicacao,
livro.genero,
livro.isbn,
livro.preco,
livro.titulo
from livro livro 
join autor autor on autor.id=livro.id_autor 
where livro.id=?