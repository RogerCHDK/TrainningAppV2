INSERT INTO public.USER values('1', 'ROGELIO', 'rogelio@mail.com','12345678');

INSERT INTO public.WEIGHT_CONTROL values('1', '64', current_timestamp,'1');
INSERT INTO public.WEIGHT_CONTROL values('2', '68', current_timestamp,'1');

INSERT INTO public.routine_exercise values('1',current_timestamp,'Gran rutina para hacer','00:30:00','1','1');

INSERT INTO public.training_exercise(TRAINING_EXERCISE_ID_SEQ, NAME,BODY_PART,DESCRIPTION) values('1','Abductor','Pierna','Gran ejercisio para entrenar');

INSERT INTO public.training_set values('1','4','00:01:30','1','1');

INSERT INTO public.cardio_machine(CARDIO_MACHINE_ID_SEQ, NAME, DESCRIPTION) values('1','Caminadora','Gran maquina para hacer cardio');
INSERT INTO public.cardio_machine(CARDIO_MACHINE_ID_SEQ, NAME, DESCRIPTION) values('2','Eliptica','Gran maquina para hacer hit');

INSERT INTO public.cardio_set values('1','1','1');
INSERT INTO public.cardio_set values('2','1','2');

INSERT INTO public.cardio_workout values('1','250','5','10','1');
INSERT INTO public.cardio_workout values('2','300','10','15','2');