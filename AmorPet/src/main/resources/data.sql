
/* INSERT DOS ADMINISTRADORES */
insert into usuario (id, nome, email, hash_senha, telefone, role, ativo)
values
(default, 'Douglas', 'd@d', 'D9E6762DD1C8EAF6D61B3C6192FC408D4D6D5F1176D0C29169BC24E71C3F274AD27FCD5811B313D681F7E55EC02D73D499C95455B6B5BB503ACF574FBA8FFE85',
'12467890153', 'ROLE_ADMIN', 1),
(default, 'Maria', 'm@m', 'D9E6762DD1C8EAF6D61B3C6192FC408D4D6D5F1176D0C29169BC24E71C3F274AD27FCD5811B313D681F7E55EC02D73D499C95455B6B5BB503ACF574FBA8FFE85',
'12467890153', 'ROLE_ADMIN', 1),
(default, 'Ronny', 'r@r', 'D9E6762DD1C8EAF6D61B3C6192FC408D4D6D5F1176D0C29169BC24E71C3F274AD27FCD5811B313D681F7E55EC02D73D499C95455B6B5BB503ACF574FBA8FFE85',
'12467890153', 'ROLE_ADMIN', 1);

/* INSERT DOS TIPOS DE RESIDENCIA */
insert into residencia (id_residencia, tipo_residencia) values (default, "Todos");
insert into residencia (id_residencia, tipo_residencia) values (default, "Casa");
insert into residencia (id_residencia, tipo_residencia) values (default, "Apartamento");

/* INSERT DOS GATOS */
insert into animal 
(id_animal, caminho_foto, data_nasc, data_registro, historia_animal, nome, porte_animal, sexo_animal, tipo_animal, id_usuario, id_administrador)
values
(default, '/img/animais/cats/Bilu.jpg', '2016-06-12', NOW(), 'Olá, meu nome é Bilu', 'Bilu', 'Medio', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/banguela.jpg', '2019-01-29', NOW(), 'Olá, meu nome é Banguela', 'Banguela', 'Pequeno', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/juliete.jpeg', '2015-07-06', NOW(), 'Olá, meu nome é Juliete', 'Juliete', 'Pequeno', 'Femea', 'Gato', null, 1),
(default, '/img/animais/cats/jack.jpeg', '2015-01-20', NOW(), 'Olá, meu nome é Jack', 'Jack', 'Pequeno', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/jane.jpg', '2014-12-30', NOW(), 'Olá, meu nome é Jane', 'Jane', 'Medio', 'Femea', 'Gato', null, 1),
(default, '/img/animais/cats/mariete.jpg', '2017-10-01', NOW(), 'Olá, meu nome é Mariete', 'Mariete', 'Medio', 'Femea', 'Gato', null, 1),
(default, '/img/animais/cats/godofredo.jpeg', '2015-05-10', NOW(), 'Olá, meu nome é Godofredo', 'Godofredo', 'Grande', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/belinda.jpg', '2017-03-06', NOW(), 'Olá, meu nome é Belinda', 'Belinda', 'Pequeno', 'Femea', 'Gato', null, 1),
(default, '/img/animais/cats/jorginho.jpg', '2018-01-25', NOW(), 'Olá, meu nome é Jorginho', 'Jorginho', 'Medio', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/morgado.jpeg', '2015-11-16', NOW(), 'Olá, meu nome é Morgado', 'Morgado', 'Medio', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/mistico.jpg', '2018-01-25', NOW(), 'Olá, meu nome é Mistico', 'Mistico', 'Pequeno', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/siso-e-sout.jpg', '2018-09-03', NOW(), 'Olá, meu nome é Siso e Sout', 'Siso e Sout', 'Pequeno', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/Mariazinha.jpg', '2018-08-25', NOW(), 'Olá, meu nome é Mariazinha', 'Mariazinha', 'Pequeno', 'Femea', 'Gato', null, 1),
(default, '/img/animais/cats/mike.jpg', '2017-04-08', NOW(), 'Olá, meu nome é Mike', 'Mike', 'Pequeno', 'Macho', 'Gato', null, 1),
(default, '/img/animais/cats/ronroninho.jpg', '2017-07-06', NOW(), 'Olá, meu nome é Ronroninho', 'Ronroninho', 'Pequeno', 'Macho', 'Gato', null, 1);

/* INSERT DOS CACHORROS */
insert into animal 
(id_animal, caminho_foto, data_nasc, data_registro, historia_animal, nome, porte_animal, sexo_animal, tipo_animal, id_usuario, id_administrador)
values
(default, '/img/animais/dogs/Bob.jpeg', '2016-06-12', NOW(), 'Olá, meu nome é Bob', 'Bob', 'Medio', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Bolota.jpg', '2019-01-29', NOW(), 'Olá, meu nome é Bolota', 'Bolota', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Amorinha.jpg', '2015-07-06', NOW(), 'Olá, meu nome é Amorinha', 'Amorinha', 'Pequeno', 'Femea', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Cadu.jpeg', '2015-01-20', NOW(), 'Olá, meu nome é Cadu', 'Cadu', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Cookie.jpg', '2014-12-30', NOW(), 'Olá, meu nome é Cookie', 'Cookie', 'Medio', 'Femea', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Nick.jpg', '2017-10-01', NOW(), 'Olá, meu nome é Nick', 'Nick', 'Medio', 'Femea', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Dante.jpg', '2015-05-10', NOW(), 'Olá, meu nome é Dante', 'Dante', 'Grande', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Pudim.jpg', '2017-03-06', NOW(), 'Olá, meu nome é Pudim', 'Pudim', 'Pequeno', 'Femea', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Max.jpeg', '2018-01-25', NOW(), 'Olá, meu nome é Max', 'Max', 'Medio', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Luke.jpeg', '2015-11-16', NOW(), 'Olá, meu nome é Luke', 'Luke', 'Medio', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Olaf.jpg', '2018-01-25', NOW(), 'Olá, meu nome é Olaf', 'Olaf', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Toy-e-Brad.jpg', '2018-09-03', NOW(), 'Olá, meu nome é Toy e Brad', 'Toy e Brad', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Pingo.jpg', '2018-08-25', NOW(), 'Olá, meu nome é Pingo', 'Pingo', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Pretinho.jpg', '2017-04-08', NOW(), 'Olá, meu nome é Pretinho', 'Pretinho', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Spock.jpeg', '2017-04-08', NOW(), 'Olá, meu nome é Spock', 'Spock', 'Pequeno', 'Macho', 'Cachorro', null, 1),
(default, '/img/animais/dogs/Thor.jpg', '2017-07-06', NOW(), 'Olá, meu nome é Thor', 'Thor', 'Pequeno', 'Macho', 'Cachorro', null, 1);

/* INSERE DAS PERGUNTAS */
insert into pergunta 
(id_pergunta, data_registro, descricao_pergunta, pontuacao, id_pergunta_titular, id_residencia, id_administrador)
values
(default, now(), 'Em caso de ausência, há alguém que pode cuidar do animal?', 10, null, 3, 1),
(default, now(), 'Já tem ou teve algum animal?', 5, null, 3, 1),
(default, now(), 'possui telas protetoras?', 15, null, 3, 1),
(default, now(), 'concorda que o pet possa adoecer, assim ele precisará de consultas veterinárias e isto terá um custo financeiro?', 15, null, 3, 1),
(default, now(), 'concorda em nos devolver o gatinho se por qualquer motivo não puder continuar com ele?', 20, null, 3, 1),
(default, now(), 'concorda em não repassar o pet a ninguém sem nos consultar?', 5, null, 3, 1),
(default, now(), 'concorda que sua casa seja vistoriada para averiguação das respostas?', 20, null, 3, 1),
(default, now(), 'concorda em assinar um contrato de adoção no ato da entrega, responsabilizando pelos cuidados com o animal e sua segurança?', 10, null, 3, 1),
(default, now(), 'tem muro altos?', 10, null, 1, 1),
(default, now(), 'tem portão vedados, que impeça alguma fuga?', 10, null, 1, 1),
(default, now(), 'condomínio permite animais?', 20, null, 2, 1);