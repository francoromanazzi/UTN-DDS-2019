
    create table acciones_ante_alertas_meteorologicas (
        tipo_accion varchar(31) not null,
        Id bigint not null auto_increment,
        accion_ante_alerta_meteorologica_id bigint,
        primary key (Id)
    )

    create table atuendos (
        Id bigint not null auto_increment,
        calzado_Id bigint,
        parteInferior_Id bigint,
        primary key (Id)
    )

    create table atuendos_accesorios (
        atuendo_id bigint not null,
        accesorio_id bigint not null
    )

    create table atuendos_prendasSuperiores (
        atuendo_id bigint not null,
        prenda_superior_id bigint not null
    )

    create table calificaciones_sugerencia (
        Id bigint not null auto_increment,
        sensibilidadGlobal varchar(255),
        primary key (Id)
    )

    create table colores (
        Id bigint not null auto_increment,
        azul integer not null,
        rojo integer not null,
        verde integer not null,
        primary key (Id)
    )

    create table decisiones (
        tipo_decision varchar(31) not null,
        id bigint not null auto_increment,
        sugerencia_Id bigint,
        calificacionAnterior_Id bigint,
        primary key (id)
    )

    create table eventos (
        Id bigint not null auto_increment,
        fechaFin tinyblob,
        fechaInicio tinyblob,
        frecuencia varchar(255),
        tipoEvento varchar(255),
        titulo varchar(255),
        usuario_id bigint,
        primary key (Id)
    )

    create table guardarropas (
        Id bigint not null auto_increment,
        primary key (Id)
    )

    create table guardarropas_usuarios (
        guardarropa_id bigint not null,
        usuario_id bigint not null
    )

    create table imagenes (
        Id bigint not null auto_increment,
        imagen mediumblob,
        primary key (Id)
    )

    create table prendas (
        Id bigint not null auto_increment,
        material varchar(255),
        tipo varchar(255),
        colorPrincipal_Id bigint,
        colorSecundario_Id bigint,
        imagen_Id bigint,
        guardarropa_id bigint,
        primary key (Id)
    )

    create table privilegios_usuario (
        discriminador varchar(31) not null,
        Id bigint not null auto_increment,
        capacidadGuardarropa integer,
        primary key (Id)
    )

    create table sensibilidades_por_parte_del_cuerpo (
        Id bigint not null auto_increment,
        parteDelCuerpo varchar(255),
        sensibilidad varchar(255),
        calificacion_sugerencia_id bigint,
        primary key (Id)
    )

    create table sugerencias (
        Id bigint not null auto_increment,
        estado varchar(255),
        atuendo_Id bigint,
        calificacion_Id bigint,
        usuario_id bigint,
        evento_id bigint,
        primary key (Id)
    )

    create table usuarios (
        Id bigint not null auto_increment,
        mail varchar(255),
        nombre varchar(255),
        numeroTelefono varchar(255),
        password varchar(255),
        username varchar(255),
        privilegio_Id bigint,
        ultimaDecision_id bigint,
        primary key (Id)
    )

    alter table acciones_ante_alertas_meteorologicas 
        add constraint FK_npqec1vre5o22r3obsc0n43vs 
        foreign key (accion_ante_alerta_meteorologica_id) 
        references usuarios (Id)

    alter table atuendos 
        add constraint FK_o3wuniclhbx48bwj0n49plh0v 
        foreign key (calzado_Id) 
        references prendas (Id)

    alter table atuendos 
        add constraint FK_pfmyw43w0rq71eyewgvhxgaw2 
        foreign key (parteInferior_Id) 
        references prendas (Id)

    alter table atuendos_accesorios 
        add constraint FK_pue693a68r492xe2vi9rty8gk 
        foreign key (accesorio_id) 
        references prendas (Id)

    alter table atuendos_accesorios 
        add constraint FK_76xwdbv74ccpcn58ird5c5x1 
        foreign key (atuendo_id) 
        references atuendos (Id)

    alter table atuendos_prendasSuperiores 
        add constraint FK_nbcx2y84b2jry3i6und976jvt 
        foreign key (prenda_superior_id) 
        references prendas (Id)

    alter table atuendos_prendasSuperiores 
        add constraint FK_9x3ab309lidi28114a2hohsr5 
        foreign key (atuendo_id) 
        references atuendos (Id)

    alter table decisiones 
        add constraint FK_1r4gpx9osy3r5yvp26hw663o4 
        foreign key (sugerencia_Id) 
        references sugerencias (Id)

    alter table decisiones 
        add constraint FK_at72n8p6fv7dbxff2e4rtqdyw 
        foreign key (calificacionAnterior_Id) 
        references calificaciones_sugerencia (Id)

    alter table eventos 
        add constraint FK_qohe0ryp5s2vjersaktppdm4r 
        foreign key (usuario_id) 
        references usuarios (Id)

    alter table guardarropas_usuarios 
        add constraint FK_sxyqclurm6pt26g8wd3ew6pc3 
        foreign key (usuario_id) 
        references usuarios (Id)

    alter table guardarropas_usuarios 
        add constraint FK_2esoo0rywjjg9p9ac4diu14no 
        foreign key (guardarropa_id) 
        references guardarropas (Id)

    alter table prendas 
        add constraint FK_hwgbwgwgf19spvpw5kloq0kmk 
        foreign key (colorPrincipal_Id) 
        references colores (Id)

    alter table prendas 
        add constraint FK_g18xpb9wlys69t13ctxrxesdq 
        foreign key (colorSecundario_Id) 
        references colores (Id)

    alter table prendas 
        add constraint FK_mhh6oijrofbkdlhndt08r81ki 
        foreign key (imagen_Id) 
        references imagenes (Id)

    alter table prendas 
        add constraint FK_2wuvkuvj27jldt90a24klw4o8 
        foreign key (guardarropa_id) 
        references guardarropas (Id)

    alter table sensibilidades_por_parte_del_cuerpo 
        add constraint FK_d9yoc12hyufab51ssuijhkgi5 
        foreign key (calificacion_sugerencia_id) 
        references calificaciones_sugerencia (Id)

    alter table sugerencias 
        add constraint FK_3e6t8iqf6bfminiwduchobp3w 
        foreign key (atuendo_Id) 
        references atuendos (Id)

    alter table sugerencias 
        add constraint FK_48w7114fuoujhcqx3pophlw3o 
        foreign key (calificacion_Id) 
        references calificaciones_sugerencia (Id)

    alter table sugerencias 
        add constraint FK_gxobpdcf0dy2cs6r8pr7cfud6 
        foreign key (usuario_id) 
        references usuarios (Id)

    alter table sugerencias 
        add constraint FK_8fo9dtg36b343ulswr9febny7 
        foreign key (evento_id) 
        references eventos (Id)

    alter table usuarios 
        add constraint FK_armw7vg3aq8ngnk6gn73a2m7g 
        foreign key (privilegio_Id) 
        references privilegios_usuario (Id)

    alter table usuarios 
        add constraint FK_hqwd22qe06ig2dsr9c6yp14er 
        foreign key (ultimaDecision_id) 
        references decisiones (id)
