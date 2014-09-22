INSERT INTO periodo(id, nome, dataInicio, dataFim) VALUES(nextval('periodo_id_seq'), '2012.1', '2012-03-01', '2012-07-01');
INSERT INTO periodo(id, nome, dataInicio, dataFim) VALUES(nextval('periodo_id_seq'), '2012.2', '2012-08-01', '2012-12-20');
INSERT INTO periodo(id, nome, dataInicio, dataFim) VALUES(nextval('periodo_id_seq'), '2013.1', '2013-03-01', '2013-07-01');
INSERT INTO periodo(id, nome, dataInicio, dataFim) VALUES(nextval('periodo_id_seq'), '2013.2', '2013-08-01', '2013-12-20');

INSERT INTO usuario(login, senha, ativo, perfil) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', true, 'Administrador');

INSERT INTO aluno(nome, matricula, periodo_id, email) VALUES ('Jocelio Otavio', '0760699', currval('periodo_id_seq'), 'jojocunha@gmail.com');
INSERT INTO usuario(login, senha, ativo, perfil, aluno_id) VALUES ('0760699', 'e10adc3949ba59abbe56e057f20f883e', true, 'Aluno', currval('aluno_id_seq'));

INSERT INTO professor(matricula, email, nome) VALUES ('111111-X', 'jocelio.otavio@gmail.com', 'Coordenador 1');
INSERT INTO usuario(login, senha, ativo, perfil, professor_id) VALUES ('jocelio.otavio@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', true, 'Professor', currval('professor_id_seq'));

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Cursos de língua estrangeira – mínimo três semestres', 60, null, 0);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Curso de informática – mínimo 50 % da carga horária do curso', 60, null, 0);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Cursos de complementação de conteúdos das disciplinas do curso – mínimo 50 % da carga horária do curso', 60, null, 0);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Iniciação científica - PIBIC, IC-UECE, IC-FUNCAP, PROVIC', 100, 25, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Pesquisa em projetos do curso, aprovados pelo CEPE', 80, 20, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em grupo de estudo aprovado pelo Colegiado do Curso acompanhado por professor', 60, 15, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Apresentação de trabalhos na Semana Universitária – oral ou painel', 48, 8, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Apresentação de trabalhos em congressos, simpósios, encontros nacionais – oral ou painel', 48, 8, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Prêmio acadêmico, artístico ou cultural', 60, 15, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Trabalhos completos publicados em anais', 80, 20, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de livros de divulgação científica com ISBN', 80, 20, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de capítulo de livros com ISBN', 50, 10, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de livros na área de conhecimento do Curso – autor único ou com até 3 (três) autores', 60, 15, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Resumos em Congressos Científicos locais', 20, 2, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Resumos em Congressos Científicos regionais', 30, 3, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Resumos em Congressos Científicos nacionais', 40, 4, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Resumos em Congressos Científicos internacionais', 40, 5, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Artigos em revistas locais com corpo editorial', 50, 10, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Artigos em revistas nacionais com corpo editorial', 60, 15, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Artigos em revistas internacionais com corpo editorial ', 80, 20, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Artigos de divulgação científica, tecnológica e artística em revista especializada', 20, 5, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Publicação de Artigos de divulgação científica, tecnológica e artística em jornais', 20, 5, 1);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em Programa de Educação Tutorial – PET', 100, 25, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em Programas de Monitoria Acadêmica – Iniciação à Docência', 100, 25, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em eventos: congressos, semanas, encontros, oficinas, palestras, conferências, mesas-redondas, seminários, simpósios, desde que observe o que preceitua o Art. 2º da Resolução Nº 3241', 40, 2, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Estágios em laboratórios de ensino e de pesquisa com duração mínima de 180 horas semestrais', 60, 15, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Estágio Curricular não obrigatório com duração mínima de 180 horas semestrais', 60, 20, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em comissões organizadoras de eventos acadêmicos, artísticos e culturais com duração mínima de 20 horas', 40, 10, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Produção de material didático com orientação de Professores da UECE', 40, 8, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação como representante estudantil nos Colegiados das várias instâncias acadêmicas da UECE', 60, 15, 2);

INSERT INTO atividadecomplementar(descricao, maximohorasporcurso, maximohorasporperiodo, natureza) VALUES ('Participação em Projetos ou Programas registrados na Pró-Reitoria de Extensão, coordenados por Professor, que visem benefícios à comunidade desde que observe o que preceitua o Art. 2º da Resolução Nº 3241', 100, 15, 3);
