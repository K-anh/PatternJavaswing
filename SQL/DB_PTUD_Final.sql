Create Database DB_PTUD_Final
go 
use DB_PTUD_Final



create table NhaXuatBan(
	maNXB nvarchar(30) PRIMARY KEY,
	tenNXB nvarchar(30),
	diaChi nvarchar(50)

)

create table TheLoai(
	maTheLoai nvarchar(30)PRIMARY KEY,
	tenTheLoai nvarchar(30) not null,
)

create table TacGia(
	maTacGia nvarchar(30)PRIMARY KEY,
	tenTacGia nvarchar(50) not null,
)

CREATE TABLE KhachHang (
    maKH nvarchar(30) PRIMARY KEY,
	tenKH nvarchar(30) not NULL,
	ngaySinh DateTime not NULL,
	diaChi nvarchar(50) not null,
	sdt nvarchar(12) NOT NULL,
);

CREATE TABLE NhanVien (
    maNV nvarchar(30) PRIMARY KEY,
	hoTenNV nvarchar(50) NOT  NULL,
	ngaySinh DateTime NOT NULL,
	diaChi nvarchar(50) NOT NULL,
	sdt nvarchar(12) NOT NULL,
	chucVu nvarchar(30) NOT NULL,
	gioiTinh bit NOT NULL,
	CCCD nvarchar(20) NOT NULL,
	tinhTrang bit NOT NULL,
	email nvarchar(50) not null,
	lyDoNghiViec nvarchar(50) default '',
);


CREATE TABLE TaiKhoan (
   maNV nvarchar(30) not null,
   hoTenNV nvarchar(50) not null,
   matKhau nvarchar(20) NOT NULL DEFAULT '1111',
   chucVu nvarchar(30) NOT NULL,
   email nvarchar(50) not null,
   OTP nvarchar(10) not null default '',
   trangThai bit not null default '0',
   constraint FK_MaNV Foreign key(maNV) references NhanVien(maNV)
)

GO





alter table TaiKhoan
add primary key (maNV)

go
CREATE TABLE NhaCungCap (
    maNCC nvarchar(30) PRIMARY KEY,
    tenNCC nvarchar(30) NOT NULL,
    sdt varchar(12) NOT NULL,
    diaChi nvarchar(50) NOT NULL,
	tinhTrang bit not null,  
);


CREATE TABLE Sach (
    
    maSach nvarchar(30) PRIMARY KEY,
    tenSach nvarchar(50) NOT NULL,
    maNCC nvarchar(30) NOT NULL,
    donGiaNhap Money NOT NULL,
	soLuong int not null,
	TheLoai nvarchar(30) not null,
	maNXB nvarchar(30) not null,
	maTacGia nvarchar(30) not null,
	hinhAnh NVARCHAR(MAX) not null,
	

	 Foreign key(maNCC) references NhaCungCap(maNCC),
	 Foreign key(TheLoai) references TheLoai(maTheLoai),
	 Foreign key(maNXB) references NhaXuatBan(maNXB),
	 Foreign key(maTacGia) references TacGia(maTacGia),
   
);


CREATE TABLE HoaDon (
    maHD nVARCHAR(30),
	maNV nvarchar(30),
	maKH nvarchar(30) ,
    ngayLap DateTime NOT NULL default Getdate(),
    
	VAT float not null,
	maQuay nvarchar(30) not null,
	
	uuDai float not null,
	tongTien float not null,

	Primary key(maHD),
	FOREIGN KEY(maNV) REFERENCES NhanVien(maNV),
	FOREIGN KEY(maKH) REFERENCES KhachHang(maKH),
);



CREATE TABLE CTHoaDon(
   maHD nVARCHAR(30),
   maSach nvarchar(30) NOT NULL ,
   tenSach nvarchar(50) NOT NULL,
   soLuong INT NOT NULL,
   donGia money not null,
   thanhTien money not null,
   Foreign key(maHD) references HoaDon(maHD),
   Foreign key(maSach) references Sach(maSach),
) 




go


create trigger create_account
on NhanVien After insert 
as 
begin
	Declare @maNhanVien nvarchar(30) , @chucVu nvarchar(30) , @hoTenNhanVien nvarchar(50) , @emailNhanVien nvarchar(50)
	Set @maNhanVien = (SELECT maNV from inserted)
	Set @chucVu = (Select  chucVu from inserted )
	Set @hoTenNhanVien = (Select hoTenNV from inserted)
	set @emailNhanVien = (Select email from inserted)

	insert into TaiKhoan(maNV ,chucVu , hoTenNV , email)
	Values (@maNhanVien , @chucVu , @hoTenNhanVien , @emailNhanVien)
end

go

create trigger update_chucVuNhanVien
on NhanVien for update 
as
begin 
	Declare @chucVuMoi nvarchar(30) , @maNhanVien nvarchar(30)
	set @chucVuMoi = (Select chucVu from inserted )
	set @maNhanVien = (Select maNV from inserted)
	Update TaiKhoan
	set chucVu = @chucVuMoi
	where maNV = @maNhanVien
end





Create trigger insert_HD
on CTHoaDon
for insert 
as
	begin 
		declare @maSach varchar(5)
		declare @SLSach int

		set @maSach = (select maSach from inserted)
		--so luong ban
		set @SLSach = (select  soluong from inserted)

		--so luong mua
		if (@SLSach > (select soLuong from Sach where maSach = @maSach))
			begin 
				rollback transaction
			end
		else
			begin
				update Sach
				set soLuong = soluong - @SLSach
				where maSach = @maSach
			end
	end


Insert NhanVien(maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang , email)
values ('NV001',N'Ngọc Hải' , '2003/11/06' , 'HCM' , '0961236005' , N'Quản Lý' , 1 , '095203001111' , 1 , 'hainguyendoanngoc@gmail.com')

Insert NhanVien(maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang , email)
values ('NV002', N'Hồ Thương', '2003/11/06' , 'HCM' , '0961236001' , 'Nhan Vien' , 1 , '095203001112' , 1 , 'luucongnhathoa@gmail.com')

Insert NhanVien(maNV , hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh , CCCD , tinhTrang , email)
values ('NV003', N'Hồng Danh' , '2003/11/06' , 'HCM' , '0961236002' , 'Nhan Vien' , 1 , '095203001113' , 1 , 'danhnguyen141003@gmail.com')

Select * from NhanVien
insert TheLoai(maTheLoai , tenTheLoai)
values ('TL001' , 'Van Hoc')
insert TheLoai(maTheLoai , tenTheLoai)
values ('TL002' , 'Chinh Tri ')
insert TheLoai(maTheLoai , tenTheLoai)
values ('TL003' , 'Phap Luat ')
insert TheLoai(maTheLoai , tenTheLoai)
values ('TL004' , 'Thieu Nhi ')
select * from TheLoai