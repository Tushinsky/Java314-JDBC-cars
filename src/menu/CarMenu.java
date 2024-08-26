/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import connection.Runquery;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sergii.Tushinskyi
 */
public class CarMenu {

    private final Scanner scanner;// чтение данных с клавиатуры
    private int menuItem = 1;
    private String sqlString;
    
    public CarMenu() {
        scanner = new Scanner(System.in);// создаём объект для чтения с клавиатуры
    }
    
    /**
     * Отображение меню
     */
    public void showMenu() {
        
        showMainMenuItem();
    }
    
    public void doChoice() {
        System.out.println("Введите: ");
        int choice = scanner.nextInt();
        menuItem = choice;
        System.out.println("choice=" + choice + " menuitem=" + menuItem);
        switch(choice) {
            case 1:
                showCommonInfo();
                showMainMenuItem();
                break;
            case 2:
                showMakers();
                showMainMenuItem();
                break;
            case 3:
                showColor();
                showMainMenuItem();
                break;
            case 4:
                showType();
                showMainMenuItem();
                break;
            case 5:
                showMade_Year();
                break;
            case 6:
                showType_Maker();
                break;
            case 7:
                showCar_Color();
                break;
            case 8:
                showEngineFlow();
                break;
            case 9:
                showType_of_Car();
        }
        
    }

    public int getMenuItem() {
        return menuItem;
    }
    
    /**
     * Закрываем меню
     */
    public void closeMenu() {
        scanner.close();
    }
    
    private void showMainMenuItem() {
        System.out.println("+---+------------------------+");
        System.out.println("| # | Наименование           |");
        System.out.println("+---+------------------------+");
        System.out.println("| 1 | Показать список        |");
        System.out.println("| 2 | Производители          |");
        System.out.println("| 3 | Цветовая гамма         |");
        System.out.println("| 4 | Тип автомобиля         |");
        System.out.println("| 5 | По году выпуска        |");
        System.out.println("| 6 | По марке производителя |");
        System.out.println("| 7 | По цвету кузова        |");
        System.out.println("| 8 | По объему двигателя    |");
        System.out.println("| 9 | По типу автомобиля     |");
        System.out.println("+---+------------------------+");
        System.out.println("| 0 | Выход                  |");
        System.out.println("+---+------------------------+");
        doChoice();
    }
    /**
     * Вывод общей информации
     */
    private void showCommonInfo() {
        sqlString = "SELECT cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
        "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
        "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
        "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
        "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor;";
        outData(sqlString);
    }
    
    /**
     * Информация по производителям
     */
    private void showMakers() {
        sqlString = "select*from cars.sprmaker;";
        outData(sqlString);
    }
    
    /**
     * Информация по цветовой гамме
     */
    private void showColor() {
        sqlString = "select*from cars.sprcolor;";
        outData(sqlString);
    }
    
    /**
     * Информация по типам
     */
    private void showType() {
        sqlString = "select*from cars.sprcartype;";
        outData(sqlString);
    }
    
    /**
     * Отбор по году выпуска
     */
    private void showMade_Year() {
        while(true) {
            System.out.println("Введите год (0 - для возврата в главное меню): ");
            int year = scanner.nextInt();
            if(year != 0) {
            sqlString = "SELECT cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
            "sprcolor.name, sprcartype.name FROM cars.car inner join cars.sprcartype " +
            "on cars.sprcartype.id=cars.car.idtype inner join cars.sprmaker on " +
            "cars.sprmaker.id=cars.car.idmaker inner join cars.sprcolor on " +
            "cars.sprcolor.id=cars.car.idcolor where cars.car.madeyear=" + year + ";";
            outData(sqlString);
            } else {
                break;
            }
        }
        showMainMenuItem();
    }
    
    /**
     * Отбор по типам производителя
     */
    private void showType_Maker() {
        while(true) {
            showMakers();
            System.out.println("Введите (0 - для возврата в главное меню): ");
            int id = scanner.nextInt();
            if(id != 0) {
                sqlString = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
                "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
                "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
                "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
                "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor " +
                "where cars.car.idmaker=" + id + ";";
                outData(sqlString);
            } else {
                break;
            }
        }
        showMainMenuItem();
    }
    
    /**
     * Отбор по цвету автомобиля
     */
    private void showCar_Color() {
        while(true) {
            showColor();
            System.out.println("Введите (0 - для возврата в главное меню): ");
            int id = scanner.nextInt();
            if(id != 0) {
            sqlString = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
            "cars.car.madeyear, sprcartype.name FROM cars.car inner join " +
            "cars.sprcartype on cars.sprcartype.id=cars.car.idtype inner join " +
            "cars.sprmaker on cars.sprmaker.id=cars.car.idmaker inner join " +
            "cars.sprcolor on cars.sprcolor.id=cars.car.idcolor where cars.car.idcolor=" + id +";";
            outData(sqlString);
            } else {
                break;
            }
        }
        showMainMenuItem();
    }
    
    /**
     * Отбор по объёму двигателя
     */
    private void showEngineFlow() {
        while(true) {
            System.out.println("Введите (0 - для возврата в главное меню): ");
            int id = scanner.nextInt();
            if(id != 0) {
            
                sqlString = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
                "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
                "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
                "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
                "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor where " +
                "cars.car.engineflow=" + id + ";";
                outData(sqlString);
            } else {
                break;
            }
        }
        showMainMenuItem();
    }
    
    /**
     * Отбор по типу автомобиля
     */
    private void showType_of_Car() {
        while(true) {
            showType();
            System.out.println("Введите (0 - для возврата в главное меню): ");
            int id = scanner.nextInt();
            if(id != 0) {
            
            sqlString = "select cars.sprmaker.name, cars.car.carname, cars.car.engineflow, " +
            "cars.car.madeyear, sprcolor.name, sprcartype.name FROM cars.car " +
            "inner join cars.sprcartype on cars.sprcartype.id=cars.car.idtype " +
            "inner join cars.sprmaker on cars.sprmaker.id=cars.car.idmaker " +
            "inner join cars.sprcolor on cars.sprcolor.id=cars.car.idcolor " +
            "where cars.car.idtype=" + id + ";";
            outData(sqlString);
            } else {
                break;
            }
        }
        showMainMenuItem();
    }

    public String getSqlString() {
        return sqlString;
    }
    
    /**
     * Вывод данных, полученных в результате запроса
     * @param sql строка-запрос на получение данных
     */
    private static void outData(String sql) {
        Runquery rq = new Runquery();// объект для получения данных
        List<Object[]> entities = rq.getQueryEntities(sql);
        System.out.println("Вывод данных");
        String[] columnname = rq.getColumnName();// получаем наименования столбцов запроса
        for(String col : columnname) {
            System.out.print(col + "\t");
        }
        System.out.println();
        entities.forEach((entity) -> {
            for(Object e : entity) {
                System.out.print(e.toString() + "\t");
            }
            System.out.println("");
        });
    }
}
