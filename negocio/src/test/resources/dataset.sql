insert into cliente values (1,"pepe@email.com","Pepe","1dsd34",1,"ruta foto");
insert into cliente values (2,"juan@email.com","Juan","1de34",1,"ruta foto");
insert into cliente values (3,"luis@email.com","Luis","efe34",0,"ruta foto");
insert into cliente values (4,"maria@email.com","Maria","1fe4",1,"ruta foto");
insert into cliente values (5,"luisa@email.com","Luisa","de3de4",1,"ruta foto");
insert into cliente values (6,"paola@email.com","Paola","dfgrfr",1,"ruta foto");

insert into cliente_telefonos values (1, "321649494");
insert into cliente_telefonos values (1, "325858258");
insert into cliente_telefonos values (2, "3212858");
insert into cliente_telefonos values (3, "30125741");
insert into cliente_telefonos values (2, "30125741");

insert into ciudad values (1,"Armenia");
insert into ciudad values (2,"Pereira");
insert into ciudad values (3,"Manizales");
insert into ciudad values (4,"Cali");
insert into ciudad values (5,"Bogota");

insert into administrador_teatro values (1,"admin1@email.com","Admnin1","916165");
insert into administrador_teatro values (2,"admin2@email.com","Admnin2","494949");
insert into administrador_teatro values (3,"admin3@email.com","Admnin3","564949");
insert into administrador_teatro values (4,"admin4@email.com","Admnin4","858585");
insert into administrador_teatro values (5,"admin5@email.com","Admnin5","585858");

insert into teatro values (1,"Carrera 33 # 2-3","7314346",1,1);
insert into teatro values (2,"Unicentro Local 301","6269495",1,1);
insert into teatro values (3,"Parque arboleda","8654575",3,2);
insert into teatro values (4,"Calle 3 # 12-4 Esquina","46461894",2,3);
insert into teatro values (5,"Carrera 13 - 25","46461894",1,1);

insert into pelicula values (1,1, "Inside Out", "Actores", "Los sentimientos tienen vida","imagen","trailer");
insert into pelicula values (2,1, "Harry Potter y la piedra filosofal", "Actores", "Un niño puede usar magia","imagen","trailer");
insert into pelicula values (3,1, "El conjuro", "Actores", "Una familia se muda a una casa y pasan cosas","imagen","trailer");
insert into pelicula values (4,0, "Wakanda Forever", "Actores", "Una nueva era en wakanda","imagen","trailer");
insert into pelicula values (5,1, "Avatar", "Actores", "Un plagio de los pitufos","imagen","trailer");
insert into pelicula values (6,1, "Harry Potter y el misterio del principe", "Actores", "Un niño puede usar magia","imagen","trailer");

insert into pelicula_generos values (1, "ANIMADA");
insert into pelicula_generos values (2, "DRAMA");
insert into pelicula_generos values (3, "SUSPENSO");
insert into pelicula_generos values (4, "COMEDIA");
insert into pelicula_generos values (5, "CIENCIA_FICCION");
insert into pelicula_generos values (5, "ROMANCE");

insert into confiteria values (1, "Combo tu y yo", 45000,"imagen");
insert into confiteria values (2, "Crispetas grandes", 9000,"imagen");
insert into confiteria values (3, "Coca Cola grande", 9000,"imagen");
insert into confiteria values (4, "Combo personal", 20000,"imagen");
insert into confiteria values (5, "Perro caliente", 9000,"imagen");
insert into confiteria values (6, "Combo nachos", 40000,"imagen");
insert into confiteria values (7, "Combo perro", 35000,"imagen");

insert into distribucion_sillas values (1, 5, "ruta", 5, 25);
insert into distribucion_sillas values (2, 10, "ruta", 10, 100);
insert into distribucion_sillas values (3, 5, "ruta", 15, 75);
insert into distribucion_sillas values (4, 8, "ruta", 10, 80);
insert into distribucion_sillas values (5, 9, "ruta", 12, 108);

insert into sala values (1, "Sala A XD", 2, 2);
insert into sala values (2, "Sala B", 1, 2);
insert into sala values (3, "Sala 1 XD", 2, 1);
insert into sala values (4, "Sala 1", 3, 3);
insert into sala values (5, "Sala 2", 1, 3);
insert into sala values (6, "Sala 1", 1, 4);

insert into horario values (1, "LMXJVSD", "2022-10-06", "2022-09-16", "20:00");
insert into horario values (2, "XJVS", "2022-10-06", "2022-09-16", "19:30");
insert into horario values (3, "VSD", "2022-10-06", "2022-09-16", "22:00");
insert into horario values (4, "LMXJVSD", "2022-09-30", "2022-09-10", "21:00");
insert into horario values (5, "LMXJVSD", "2022-10-10", "2022-09-18", "15:00");

insert into funcion values (1, 8700, 1, 1, 1);
insert into funcion values (2, 8700, 3, 2, 2);
insert into funcion values (3, 9000, 1, 3, 3);
insert into funcion values (4, 7300, 1, 4, 4);
insert into funcion values (5, 8500, 1, 5, 5);
insert into funcion values (6, 9100, 5, 1, 5);

insert into cupon values (1, "10", "0", 1, "2022-10-10");
insert into cupon values (2, "5", "0", 2, "2022-11-09");
insert into cupon values (3, "8", "0", 3, "2022-11-12");
insert into cupon values (4, "15", "0", 4, "2022-11-25");
insert into cupon values (5, "30", "0", 4, "2022-11-25");

insert into cupon_cliente values (1, 0, 1, 2);
insert into cupon_cliente values (2, 0, 4, 1);
insert into cupon_cliente values (3, 0, 5, 3);
insert into cupon_cliente values (4, 0, 2, 4);
insert into cupon_cliente values (5, 0, 1, 3);

insert into compra values (1, "2022-09-17", "VISA", 0, 1, null, 1);
insert into compra values (2, "2022-09-17", "PSE", 0, 2, null, 1);
insert into compra values (3, "2022-09-20", "DAVIPLATA", 0, 5, null, 2);
insert into compra values (4, "2022-10-02", "MASTERCARD", 0, 1, null, 6);
insert into compra values (5, "2022-10-02", "DAVIPLATA", 0, 3, null, 4);

insert into compra_confiteria values (1, 0, 2, 1, 2);
insert into compra_confiteria values (2, 0, 3, 5, 3);
insert into compra_confiteria values (3, 0, 1, 2, 2);
insert into compra_confiteria values (4, 0, 2, 3, 7);
insert into compra_confiteria values (5, 0, 3, 4, 6);
insert into compra_confiteria values (6, 0, 1, 2, 5);
insert into compra_confiteria values (7, 0, 2, 5, 4);

insert into entrada values (1, 1, 4, 7800, 1);
insert into entrada values (2, 2, 4, 7800, 1);
insert into entrada values (3, 4, 4, 7800, 2);
insert into entrada values (4, 5, 4, 7800, 2);
insert into entrada values (5, 1, 4, 7800, 3);
insert into entrada values (6, 2, 2, 7800, 4);
insert into entrada values (7, 3, 4, 9100, 5);
insert into entrada values (8, 3, 4, 7800, 1);




