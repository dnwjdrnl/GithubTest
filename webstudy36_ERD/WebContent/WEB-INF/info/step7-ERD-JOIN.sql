/*ERD(Entity Relationship Diagram)
 * :데이터 모델링을 위한 다이어그램
 * 
 * 사례) 사원정보 저장하는 테이블 설계 
 * 		사원번호, 사원명, 직종, 월급
 * 		부서명, 부서지역, 부서 대표 전화번호 
 * 
 * 		실제 데이터
 * 		1 진영훈 개발 1000 연구개발부 판교 031
 * 		2 정지원 개발 990   공공사업부 종로 02
 * 		...
 * 
 * 		위와 같이 테이블을 설계하여 데이터를 저장하는 경우에
 * 		발생하는 문제
 * 		1) 중복된 데이터(부서정보)가 반복해서 저장된다
 * 			--> 데이터 중복되어 자원 소모
 * 		2) 정보 변경시 유연하게 대처할 수 없다 
 * 			--> 부서정보가 변경될 경우 모든 사원의 부서정보가
 * 				업데이트 되어야 한다 
 * 	위의 사원 테이블을 분할
 * 
 *  부서 테이블	  |-----0|<- 	사원테이블
 * 
 *  위와 같이 부서와 사원 테이블을 분할하여 관리하면
 *  중복된 데이터 제거  --> 자원 절약
 *  정보 변경시 유연하게 대처
 * 
 *  Foreign key(FK): 테이블 간의 연결을 설정하는 키 
 * 
 * 	Foreign key 제약조건을 사원테이블의 부서번호 컬럼에 부여하여 
 *  참조 무결성을 보장하게 한다 
 *  : Foreign key 제약 조건을 사원테이블의 부서번호 컬럼에 명시하면
 * 	     부서테이블의 부서번호에 저장되지 않은
 *	     부서번호를 사원정보 등록시 저장할 수 없다  
 *
 *	 Foreign key(FK) 문법
 *   constraint 제약조건명 foreign key(참조정보저장컬럼명)
 *   references 참조테이블(컬럼명)
 *	  또는 선언시 바로 제약조건을 줄 때는
 *	  컬럼명 데이터 타입 constraint 제약조건명 
 *   references 참조테이블(컬럼명)
 * 
 * 	Master table(부모테이블): 참조대상 테이블
 *  ex) 부서테이블
 * 
 *  자식테이블: 참조하는 테이블   ex) 사원테이블
 * !!! 반드시 Master table 즉 참조대상 테이블부터 생성해야 한다 
 */

select * from department;
drop table department;

create table department(
	deptno number primary key,
	dname varchar2(100) not null,
	loc varchar2(100) not null,
	tel varchar2(100) not null
	)
drop table employee;
create table employee(
	empno number primary key,
	empname varchar2(100) not null,
	sal number not null,
	deptno number not null,
	constraint fk_deptno foreign key(deptno) references department(deptno)
)
--error, FK에 해당하는 정보가 없음
--
(department table에 없는 deptno를 입력하려 하므로)
insert into employee(empno,empname,sal,deptno)
values(1,'진영훈',1000,10);

--부서정보부터 입력
insert into department(deptno,dname,loc,tel)
values(10,'연구개발','판교','031');
insert into department(deptno,dname,loc,tel)
values(20,'공공사업','종로','02');
insert into department(deptno,dname,loc,tel)
values(30,'전략기획','제주','064');

--사원정보 추가
insert into employee(empno,empname,sal,deptno)
values(2,'정지원',1100,20);
--error:parnet key not found 존재하지 않는 부서번호이므로 error 남
insert into employee(empno,empname,sal,deptno)
values(3,'배승찬',1800,40);


select *from department;
select *from employee;

drop sequence employee_seq;
create sequence employee_seq start with 3;
select employee_seq.nextval from dual;
/*
 * JOIN SQL: 하나 이상의 테이블 간의 정보를
 * 			  결합하기 위한 SQL
 * ex) 진영훈 사원의 정보를 조회할 때 
 * 		진사원의 사원정보 및 진사원이  소속된 
 * 		부서정보를 결합하여 함께 조회하도록
 * 		join sql을 이용
 * SELECT 컬럼명,컬럼명...
 * FROM A Table명, B Table 명
 * 
 * WHERE A.컬럼명=B.컬럼명 --조인조건
 * 조인 SQL에서는 별칭 사용을 권장
 * 각 테이블에서 컬럼명이 중복될 수 있기 때문에 
 */
SELECT e.empno,e.empname,e.sal,d.deptno,d.dname,d.loc,d.tel
FROM employee e, department d
WHERE e.deptno=d.deptno;





/* 다 대 다 관계는 Association Entity로 해소한다 
 * 회원과 취미종류는 다 대 다 관계이다
 * ex) 김석환 회원은 0또는 1또는 다수의 취미를 가질 수 있다 
 * 	       프로그래밍 취미는 0또는 1또는 다수 회원의 취미가 될 수 있다 
 * 이 경우 Association Entity를 적용해 회원과 취미종류의 일반정보외의
 * 회원과 취미종류 연관정보를 저장하는 테이블을 별도로 만든다 
 * 회원 일반정보		---0|<  회원취미연관정보    >|0---	취미 종류 일반 정보 
 * 위의 설계에서 특징적인 부분은
 * 회원 취미 연관 정보는 
 * 회원 아이디를 참조하고 
 * 취미 종류 아이디를 참조,
 * 회원 아이디와 취미 종류 아이디를 복합 primary key로 설정해서 
 * 데이터 무결성을 보장하게 한다
 */
--회원테이블
create table h_member(
id varchar2(100) primary key,
password varchar2(100) not null,
name varchar2(100) not null
)
--취미 종류 테이블
create table hobby_type(
hid varchar2(100) primary key,
name varchar2(100) not null
)
--회원 취미 연관 정보 테이블
create table member_hobby(
id varchar2(100),
hid varchar2(100),
constraint fk_h_member foreign key(id) references h_member,
constraint fk_hobby_type foreign key(hid) references hobby_type,
constraint pk_member_hobby primary key(id,hid)
)
insert into h_member(id,password,name) values('java','1234','진영훈');
insert into hobby_type(hid,name) values('1','독서');
insert into hobby_type(hid,name) values('2','영화감상');
insert into hobby_type(hid,name) values('3','프로그래밍');
insert into hobby_type(hid,name) values('4','자전거');
insert into hobby_type(hid,name) values('5','필라테스');
insert into hobby_type(hid,name) values('6','음악감상'); 

--복합 pk 테스트
insert into member_hobby(id,hid) values('java','4');
--error 복합 pk 제약조건에 위배 
insert into member_hobby(id,hid) values('java','4');
--success
insert into member_hobby(id,hid) values('java','5');
select * from member_hobby;
select * from h_member;

select m.id,m.name,h.hid,t.name
from h_member m, member_hobby h, hobby_type t where m.id=h.id and h.hid=t.hid;

SELECT e.empno,e.empname,e.sal,d.deptno,d.dname,d.loc,d.tel
FROM employee e, department d
WHERE e.deptno=d.deptno;

--Join SQL을 이용해서 java 회원아이디를 가진 회원의
--hobby name을 조회.. member_hobby table과 hobby_type table을 join
select ht.name
from member_hobby mh,hobby_type ht
where mh.hid=ht.hid and mh.id='java'

--review point
/* ERD (개체 관계 다이어그램)
 * 1대 1 (사원과 신체정보)
 * 1대 다  (부서와 사원)
 * 다대 다 (사람과 취미)
 */










