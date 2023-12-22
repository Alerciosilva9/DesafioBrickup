CREATE TABLE IF NOT EXISTS public.imagem(
    id bigserial,
    imagem bytea,
    id_tarefa integer NOT NULL UNIQUE,
	PRIMARY KEY(id),
	  CONSTRAINT fk_tarefa
      FOREIGN KEY(id_tarefa)
	  REFERENCES public.tarefa(id)
)