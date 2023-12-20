CREATE TABLE IF NOT EXISTS public.tarefa(
    id bigserial,
    descricao character varying(100) NOT NULL,
    imagem bytea,
    status integer NOT NULL,
	PRIMARY KEY(id),

	  CONSTRAINT fk_status
      FOREIGN KEY(status)
	  REFERENCES public.status(id)
)