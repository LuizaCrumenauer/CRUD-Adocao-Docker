-- criacao da tabela usuario
CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR (255) NOT NULL,
                         endereco VARCHAR (255) NOT NULL,
                         cpf VARCHAR (14) NOT NULL,
                         celular VARCHAR (15) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         isAdmin BOOLEAN NOT NULL
);

-- criacao da tabela cachorro

CREATE TABLE cachorro (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR (255) NOT NULL,
                          raca VARCHAR (255) NOT NULL,
                          sexo VARCHAR (255) NOT NULL,
                          porte VARCHAR (255) NOT NULL,
                          adotado boolean NOT NULL
);

-- criacao da tablea adocao

CREATE TABLE adocao (
                        id SERIAL PRIMARY KEY,
                        cachorro_id INTEGER NOT NULL UNIQUE,
                        adotante_id INTEGER NOT NULL,
                        informacoes VARCHAR(500) NOT NULL,
                        CONSTRAINT fk_adocao_cachorro
                            FOREIGN KEY (cachorro_id)
                                REFERENCES cachorro(id)
                                ON DELETE CASCADE,
                        CONSTRAINT fk_adocao_usuario
                            FOREIGN KEY (adotante_id)
                                REFERENCES usuario(id)
                                ON DELETE CASCADE
);

-- inserindo um usuario comum
INSERT INTO usuario (nome, endereco, cpf, celular, email, senha, isAdmin)
VALUES ('Ana Silva', 'Rua das Flores, 123, São Paulo, SP', '111.222.333-44', '(11)98765-4321', 'ana.silva@email.com', 'senha123', FALSE);

-- inserindo um usuario admin
INSERT INTO usuario (nome, endereco, cpf, celular, email, senha, isAdmin)
VALUES ('Carlos Admin', 'Avenida Principal, 789, Rio de Janeiro, RJ', '999.888.777-66', '(21)91234-5678', 'admin@admin', 'admin', TRUE);

-- inserindo uma adocao
INSERT INTO cachorro (nome, raca, sexo, porte, adotado)
VALUES ('Max', 'Labrador Retriever', 'Macho', 'Grande', FALSE);