-- Criação da tabela USUARIO
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

-- Criação da tabela CACHORRO

CREATE TABLE cachorro (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR (255) NOT NULL,
                          raca VARCHAR (255) NOT NULL,
                          sexo VARCHAR (255) NOT NULL,
                          porte VARCHAR (255) NOT NULL,
                          adotado boolean NOT NULL
);

CREATE TABLE adocao (
                        id SERIAL PRIMARY KEY,
                        cachorro_id INTEGER NOT NULL UNIQUE, -- Unicidade definida aqui
                        adotante_id INTEGER NOT NULL,
                        informacoes VARCHAR(500) NOT NULL,
                        CONSTRAINT fk_adocao_cachorro -- Restrição de chave estrangeira
                            FOREIGN KEY (cachorro_id)
                                REFERENCES cachorro(id)
                                ON DELETE CASCADE,
                        CONSTRAINT fk_adocao_usuario
                            FOREIGN KEY (adotante_id)
                                REFERENCES usuario(id)
                                ON DELETE CASCADE
);

-- Inserindo um usuário comum
INSERT INTO usuario (nome, endereco, cpf, celular, email, senha, isAdmin)
VALUES ('Ana Silva', 'Rua das Flores, 123, São Paulo, SP', '111.222.333-44', '(11)98765-4321', 'ana.silva@email.com', 'senha123', FALSE);

-- Inserindo um usuário administrador
INSERT INTO usuario (nome, endereco, cpf, celular, email, senha, isAdmin)
VALUES ('Carlos Admin', 'Avenida Principal, 789, Rio de Janeiro, RJ', '999.888.777-66', '(21)91234-5678', 'admin@admin', 'admin', TRUE);

-- Inserindo um cachorro para adoção
-- Supondo que o ID gerado para este cachorro será 1 (devido ao SERIAL)
INSERT INTO cachorro (nome, raca, sexo, porte, adotado)
VALUES ('Max', 'Labrador Retriever', 'Macho', 'Grande', FALSE);