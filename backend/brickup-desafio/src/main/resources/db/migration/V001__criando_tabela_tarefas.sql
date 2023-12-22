CREATE TABLE IF NOT EXISTS public.tarefa(
    id bigserial,
    descricao character varying(100) NOT NULL,
    status character varying(15) NOT NULL,
	PRIMARY KEY(id)
)


