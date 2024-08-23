/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlapp;

import connection.JDBCConnection;
import connection.Runquery;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergii.Tushinskyi
 */
public class MySQLApp {

    private static JDBCConnection connection;
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            // TODO code application logic here
            System.out.print("Открывается соединение");
            int i = 0;
            while(i < 5) {
                System.out.print(".");
                Thread.sleep(500);
                i++;
            }
            System.out.println("");
            if(openConnection()) {
                System.out.println("Соединение открыто");
                System.out.println("");
                try {
                    // создаём таблицы, заполняем их данными
                    createDataTable();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                i = 0;
                System.out.print("Соединение закрывается");
                while(i < 5) {
                    System.out.print(".");
                    Thread.sleep(500);
                    i++;
                }
                System.out.println("");
                closeConnection();
            } else {
                System.out.println("Что-то не сложилось.");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MySQLApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static boolean openConnection() throws IOException, 
            FileNotFoundException, ClassNotFoundException {
        try {
            // set drivername
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/cars";
            // создаём соединение, проверяем его сосотяние
            connection = new JDBCConnection(driver, url, "root", "masterkey");
            return connection.isClosedConn() != true;
        } catch (SQLException ex){
            return false;
        }
    }
    
    private static void closeConnection() {
        try {
            if (connection != null && !connection.isClosedConn()) {
                JDBCConnection.getConn().close();
                if(connection.isClosedConn()){
                    System.out.println("Соединение закрыто!");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void createDataTable() throws SQLException {
        String sql;
        int i;
        /*
        sql = create table car(id integer primary key auto_increment not null, carmaker 
        varchar(255) not null default 'new maker', carname varchar(50) not null 
        default 'new name', engineflow decimal(5,2), madeyear smallint, 
        color varchar(20), cartype varchar(25) not null default 'седан');
        sql = create table sprmaker(id integer primary key auto_increment not null, name varchar(255) not null default 'new maker');
        sql = create table sprcolor(id integer primary key auto_increment not null, name varchar(25) not null default 'new color');
        sql = create table sprcartype(id integer primary key auto_increment not null, name varchar(25) not null default 'new type');
        sql = create table sprcartype(id integer primary key auto_increment not null, name varchar(25) not null default 'new type');
        sql = insert into sprcartype(name) values('седан');
        sql = insert into sprcartype(name) values('хетчбек');
        sql = insert into sprcartype(name) values('универсал');
        sql = insert into sprcolor values(1,'белый');
        sql = insert into sprcolor values(2,'чёрный');
        sql = insert into sprcolor values(3,'металлик');
        sql = insert into sprcolor values(4,'красный');
        sql = insert into sprcolor values(5,'асфальт');
        -------------В таблицу car добавим новые столбцы:
        alter table cars.car add column idmaker integer not null;
        alter table cars.car add column idcolor integer not null;
        alter table cars.car add column idtype integer not null;
        -------------Добавляем внешние ключи по этим столбцам
        alter table cars.car add constraint fk_maker foreign key (idmaker) 
        references cars.sprmaker (id) on update cascade on delete cascade;
        alter table cars.car add constraint fk_color foreign key (idcolor) 
        references cars.sprcolor (id) on update cascade on delete cascade;
        alter table cars.car add constraint fk_cartype foreign key (idtype) 
        references cars.sprcartype (id) on update cascade on delete cascade;
        -------------Удаляем лишние столбцы
        alter table cars.car drop column carmaker;
        alter table cars.car drop column color;
        alter table cars.car drop column cartype;
        */
        
        //-----------------Отображение содержимого
        sql = "SELECT cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
        "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
        "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
        "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor;";
        outData(sql);
        //------------------Все производители
        sql = "select*from cars.sprmaker;";
        outData(sql);
        //------------------По году выпуска
        sql = "SELECT cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "sprcolor.name, sprcartype.name FROM cars.car inner join cars.sprcartype " +
        "on cars.sprcartype.id=cars.car.idtype inner join cars.sprmaker on " +
        "cars.sprmaker.id=cars.car.idmaker inner join cars.sprcolor on " +
        "cars.sprcolor.id=cars.car.idcolor where cars.car.madeyear=2001;";
        outData(sql);
        //-----------------По производителю
        sql = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
        "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
        "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
        "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor " +
        "where cars.car.idmaker=2;";
        outData(sql);
        //-----------------По цвету
        sql = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcartype.name FROM cars.car inner join " +
        "cars.sprcartype on cars.sprcartype.id=cars.car.idtype inner join " +
        "cars.sprmaker on cars.sprmaker.id=cars.car.idmaker inner join " +
        "cars.sprcolor on cars.sprcolor.id=cars.car.idcolor where cars.car.idcolor=1;";
        outData(sql);
        //-----------------По объёму двигателя
        sql = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
        "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
        "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
        "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor where " +
        "cars.car.engineflow=125;";
        outData(sql);
        //-----------------По типу автомобиля
        sql = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
        "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
        "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
        "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor " +
        "where cars.car.idtype=1;";
        outData(sql);
//        sql = "create table car1(id integer primary key auto_increment not null, carmaker \n" +
//"        varchar(255) not null default 'new maker', carname varchar(50) not null \n" +
//"        default 'new name', engineflow decimal(5,2), madeyear smallint, \n" +
//"        color varchar(20), cartype varchar(25) not null default 'седан');";
//        sql = "drop table car1;";
//        sql = "insert into car(id,carmaker,carname,engineflow,madeyear,color)"
//                + " values(1,'VolksWagen','Polo',125.00,2001,'black');";
        
//        i = connection.ExecuteUpdate("insert into car values(2,'Mersedes Benz',"
//                + "'LX',100.00,1999,'золото','седан');");
//        System.out.println("i=" + i);
//        i = connection.ExecuteUpdate("insert into car(carmaker,carname,engineflow,madeyear,color)"
//                + " values('VolksWagen','Polo',125.00,2001,'black');");
//        System.out.println("i=" + i);
//        i = connection.ExecuteUpdate("insert into car(engineflow,madeyear)"
//                + " values(125.00,2001);");
//        System.out.println("i=" + i);
    }
    
    /**
     * Вывод данных, полученных в результате запроса
     * @param sql строка-запрос на получение данных
     */
    private static void outData(String sql) {
        Runquery rq = new Runquery();// объект для получения данных
        List<Object[]> entities = rq.getQueryEntities(sql);
        System.out.println("Вывод данных");
        entities.forEach((entity) -> {
            for(Object e : entity) {
                System.out.print(e.toString() + "\t");
            }
            System.out.println("");
        });
    }
}
