/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     18/12/2017 18:52:37                          */
/*==============================================================*/


drop table if exists TBMOTOR;

drop table if exists TBMOTOR_KELUAR;

drop table if exists TBMOTOR_MASUK;

drop table if exists TBORDER_MOTOR;

drop table if exists TBORDER_SPAREPART;

drop table if exists TBRETUR_PEMBELIAN_MOTOR;

drop table if exists TBRETUR_PEMBELIAN_SPAREPART;

drop table if exists TBRETUR_PENJUALAN_MOTOR;

drop table if exists TBRETUR_PENJUALAN_SPAREPART;

drop table if exists TBSPAREPART;

drop table if exists TBSPAREPART_KELUAR;

drop table if exists TBSPAREPART_MASUK;

drop table if exists TBSUPPLIER;

drop table if exists TB_KATEGORI;

drop table if exists TB_USER;

/*==============================================================*/
/* Table: TBMOTOR                                               */
/*==============================================================*/
create table TBMOTOR
(
   TIPE_MODEL           char(20) not null,
   ID_KTG               varchar(5),
   ID_SUPPLIER          varchar(5),
   NOMOR_RANGKA         char(20),
   NOMOR_MESIN          char(20),
   NAMA_MOTOR           varchar(35),
   HARGA_MOTOR          float,
   STOK_MOTOR           int,
   primary key (TIPE_MODEL)
);

/*==============================================================*/
/* Table: TBMOTOR_KELUAR                                        */
/*==============================================================*/
create table TBMOTOR_KELUAR
(
   NO_BARANG_KELUAR     char(7) not null,
   ID_USER              char(5),
   TIPE_MODEL           char(20),
   TGL_KELUAR           date,
   JML_BARANG           int,
   primary key (NO_BARANG_KELUAR)
);

/*==============================================================*/
/* Table: TBMOTOR_MASUK                                         */
/*==============================================================*/
create table TBMOTOR_MASUK
(
   NO_BARANG_MASUK      char(7) not null,
   ID_USER              char(5),
   TIPE_MODEL           char(20),
   TGL_MASUK            date,
   JML_MASUK            int,
   primary key (NO_BARANG_MASUK)
);

/*==============================================================*/
/* Table: TBORDER_MOTOR                                         */
/*==============================================================*/
create table TBORDER_MOTOR
(
   KD_ORDER_MOTOR       char(8) not null,
   TIPE_MODEL           char(20),
   ID_USER              char(5),
   TGL_ORDER_MOTOR      date,
   JML_ORDER_MOTOR      int,
   primary key (KD_ORDER_MOTOR)
);

/*==============================================================*/
/* Table: TBORDER_SPAREPART                                     */
/*==============================================================*/
create table TBORDER_SPAREPART
(
   KD_ORDER_SPAREPART   char(8) not null,
   NOMOR_SERI           char(17),
   ID_USER              char(5),
   TGL_ORDER_SPAREPART  date,
   JML_ORDER_SPAREPART  int,
   primary key (KD_ORDER_SPAREPART)
);

/*==============================================================*/
/* Table: TBRETUR_PEMBELIAN_MOTOR                               */
/*==============================================================*/
create table TBRETUR_PEMBELIAN_MOTOR
(
   NO_RETUR_PEMBELIAN_MOTOR int not null,
   ID_USER              char(5),
   TIPE_MODEL           char(20),
   TGL_RETUR_PEMBELIAN_MOTOR date,
   JML_RETUR_PEMBELIAN_MOTOR int,
   STATUS_RETUR_PEMBELIAN_MOTOR varchar(50),
   primary key (NO_RETUR_PEMBELIAN_MOTOR)
);

/*==============================================================*/
/* Table: TBRETUR_PEMBELIAN_SPAREPART                           */
/*==============================================================*/
create table TBRETUR_PEMBELIAN_SPAREPART
(
   NO_RETUR_PEMBELIAN_SPAREPART int not null,
   ID_USER              char(5),
   NOMOR_SERI           char(17),
   TGL_RETUR_PEMBELIAN_SPAREPART date,
   JML_RETUR_PEMBELIAN_SPAREPART int,
   STATUS_RETUR_PEMBELIAN_SPAREPART varchar(50),
   primary key (NO_RETUR_PEMBELIAN_SPAREPART)
);

/*==============================================================*/
/* Table: TBRETUR_PENJUALAN_MOTOR                               */
/*==============================================================*/
create table TBRETUR_PENJUALAN_MOTOR
(
   NO_RETUR_PENJUALAN_MOTOR int not null,
   ID_USER              char(5),
   TIPE_MODEL           char(20),
   TGL_RETUR_PENJUALAN_MOTOR date,
   JML_RETUR_PENJUALAN_MOTOR int,
   STATUS_RETUR_PENJUALAN_MOTOR varchar(50),
   primary key (NO_RETUR_PENJUALAN_MOTOR)
);

/*==============================================================*/
/* Table: TBRETUR_PENJUALAN_SPAREPART                           */
/*==============================================================*/
create table TBRETUR_PENJUALAN_SPAREPART
(
   NO_RETUR_PENJUALAN   int not null,
   ID_USER              char(5),
   NOMOR_SERI           char(17),
   TGL_RETUR_PENJUALAN_SPAREPART date,
   JML_RETUR_PENJUALAN  int,
   STATUS_RETUR_PENJUALAN varchar(50),
   primary key (NO_RETUR_PENJUALAN)
);

/*==============================================================*/
/* Table: TBSPAREPART                                           */
/*==============================================================*/
create table TBSPAREPART
(
   NOMOR_SERI           char(17) not null,
   ID_KTG               varchar(5),
   ID_SUPPLIER          varchar(5),
   NAMA_SPAREPART       varchar(35),
   STOK_SPAREPAR        int,
   HARGA_SPAREPART      float,
   primary key (NOMOR_SERI)
);

/*==============================================================*/
/* Table: TBSPAREPART_KELUAR                                    */
/*==============================================================*/
create table TBSPAREPART_KELUAR
(
   NO_SPAREPART_KELUAR  char(7) not null,
   ID_USER              char(5),
   NOMOR_SERI           char(17),
   TGL_SPAREPART_KELUAR date,
   JML_SPAREPART_KELUAR int,
   primary key (NO_SPAREPART_KELUAR)
);

/*==============================================================*/
/* Table: TBSPAREPART_MASUK                                     */
/*==============================================================*/
create table TBSPAREPART_MASUK
(
   NO_SPAREPART_MASUK   char(7) not null,
   ID_USER              char(5),
   NOMOR_SERI           char(17),
   TGL_SPAREPART_MASUK  date,
   JML_SPAREPART_MASUK  int,
   primary key (NO_SPAREPART_MASUK)
);

/*==============================================================*/
/* Table: TBSUPPLIER                                            */
/*==============================================================*/
create table TBSUPPLIER
(
   ID_SUPPLIER          varchar(5) not null,
   NAMA_SUPPLIER        varchar(50),
   ALAMAT_SUPPLIER      text,
   NO_HP_SUPPLIER       char(12),
   primary key (ID_SUPPLIER)
);

/*==============================================================*/
/* Table: TB_KATEGORI                                           */
/*==============================================================*/
create table TB_KATEGORI
(
   ID_KTG               varchar(5) not null,
   TAHUN_PEMBUATAN      int,
   primary key (ID_KTG)
);

/*==============================================================*/
/* Table: TB_USER                                               */
/*==============================================================*/
create table TB_USER
(
   ID_USER              char(5) not null,
   PASS_USER            varchar(9),
   NAMA_USER            varchar(20),
   LEV_USER             varchar(20),
   ALAMAT_USER          varchar(50),
   NOTELP_USER          varchar(12),
   primary key (ID_USER)
);

alter table TBMOTOR add constraint FK_RELATIONSHIP_2 foreign key (ID_KTG)
      references TB_KATEGORI (ID_KTG) on delete restrict on update restrict;

alter table TBMOTOR add constraint FK_RELATIONSHIP_27 foreign key (ID_SUPPLIER)
      references TBSUPPLIER (ID_SUPPLIER) on delete restrict on update restrict;

alter table TBMOTOR_KELUAR add constraint FK_RELATIONSHIP_14 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBMOTOR_KELUAR add constraint FK_RELATIONSHIP_22 foreign key (TIPE_MODEL)
      references TBMOTOR (TIPE_MODEL) on delete restrict on update restrict;

alter table TBMOTOR_MASUK add constraint FK_RELATIONSHIP_15 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBMOTOR_MASUK add constraint FK_RELATIONSHIP_23 foreign key (TIPE_MODEL)
      references TBMOTOR (TIPE_MODEL) on delete restrict on update restrict;

alter table TBORDER_MOTOR add constraint FK_RELATIONSHIP_28 foreign key (TIPE_MODEL)
      references TBMOTOR (TIPE_MODEL) on delete restrict on update restrict;

alter table TBORDER_MOTOR add constraint FK_RELATIONSHIP_30 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBORDER_SPAREPART add constraint FK_RELATIONSHIP_29 foreign key (NOMOR_SERI)
      references TBSPAREPART (NOMOR_SERI) on delete restrict on update restrict;

alter table TBORDER_SPAREPART add constraint FK_RELATIONSHIP_31 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBRETUR_PEMBELIAN_MOTOR add constraint FK_RELATIONSHIP_10 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBRETUR_PEMBELIAN_MOTOR add constraint FK_RELATIONSHIP_18 foreign key (TIPE_MODEL)
      references TBMOTOR (TIPE_MODEL) on delete restrict on update restrict;

alter table TBRETUR_PEMBELIAN_SPAREPART add constraint FK_RELATIONSHIP_11 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBRETUR_PEMBELIAN_SPAREPART add constraint FK_RELATIONSHIP_20 foreign key (NOMOR_SERI)
      references TBSPAREPART (NOMOR_SERI) on delete restrict on update restrict;

alter table TBRETUR_PENJUALAN_MOTOR add constraint FK_RELATIONSHIP_12 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBRETUR_PENJUALAN_MOTOR add constraint FK_RELATIONSHIP_19 foreign key (TIPE_MODEL)
      references TBMOTOR (TIPE_MODEL) on delete restrict on update restrict;

alter table TBRETUR_PENJUALAN_SPAREPART add constraint FK_RELATIONSHIP_13 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBRETUR_PENJUALAN_SPAREPART add constraint FK_RELATIONSHIP_21 foreign key (NOMOR_SERI)
      references TBSPAREPART (NOMOR_SERI) on delete restrict on update restrict;

alter table TBSPAREPART add constraint FK_RELATIONSHIP_26 foreign key (ID_SUPPLIER)
      references TBSUPPLIER (ID_SUPPLIER) on delete restrict on update restrict;

alter table TBSPAREPART add constraint FK_RELATIONSHIP_3 foreign key (ID_KTG)
      references TB_KATEGORI (ID_KTG) on delete restrict on update restrict;

alter table TBSPAREPART_KELUAR add constraint FK_RELATIONSHIP_17 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBSPAREPART_KELUAR add constraint FK_RELATIONSHIP_25 foreign key (NOMOR_SERI)
      references TBSPAREPART (NOMOR_SERI) on delete restrict on update restrict;

alter table TBSPAREPART_MASUK add constraint FK_RELATIONSHIP_16 foreign key (ID_USER)
      references TB_USER (ID_USER) on delete restrict on update restrict;

alter table TBSPAREPART_MASUK add constraint FK_RELATIONSHIP_24 foreign key (NOMOR_SERI)
      references TBSPAREPART (NOMOR_SERI) on delete restrict on update restrict;

