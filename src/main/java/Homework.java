import java.sql.*;
import java.util.UUID;

public class Homework {
    /**
     * Повторить все, что было на семниаре на таблице Student с полями
     * 1. id - bigint
     * 2. first_name - varchar(256)
     * 3. second_name - varchar(256)
     * 4. group - varchar(128)
     *
     * Написать запросы:
     * 1. Создать таблицу
     * 2. Наполнить таблицу данными (insert)
     * 3. Поиск всех студентов
     * 4. Поиск всех студентов по имени группы
     *
     * Доп. задания:
     * 1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
     * 2. ** Все идентификаторы превратить в UUID
     *
     * Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
     */

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "root", "root")) {
            acceptConnection(connection);
        } catch (SQLException e) {
            System.err.println("Не удалось подключиться к БД: " + e.getMessage());
        }
    }

    static void acceptConnection(Connection connection) throws SQLException {
        //Создаем таблицу с полями
        //1. id - bigint
        //2. first_name - varchar(256)
        //3. second_name - varchar(256)
        //4. group - varchar(128)
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
        create table Student(
          id bigint,
          firstName varchar(256),
          secondName varchar(256),
          groupName varchar(256)
        )
        """);
        }

        //Наполняем таблицу данными (insert)
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
        insert into Student(id, firstName, secondName, groupName) values
        (1, 'Igor', 'Petrov', 'group#1'),
        (2, 'Ivan', 'Sidorov', 'group#2'),
        (3, 'Jon', 'Smit', 'group#3'),
        (4, 'Mari', 'Sidorova', 'group#2'),
        (5, 'Rima', 'Petrova', 'group#3'),
        (6, 'Tany', 'Istomina', 'group#1'),
        (7, 'Ivan', 'Tretiakov', 'group#3'),
        (8, 'Petr', 'Sidorovich', 'group#3'),
        (9, 'Jonatan', 'Praim', 'group#2')
        """);
            System.out.println("Количество вставленных строк: " + count);
        }

        System.out.println("Поиск всех студентов из таблицы Student");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
        select id, firstName, secondName, groupName
        from Student
        """);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                String groupName = resultSet.getString("groupName");

                System.out.println("Прочитана строка: " + String.format("%s, %s, %s, %s",
                        id, firstName, secondName, groupName));
            }
        }
        System.out.println("");

        System.out.println("Поиск всех студентов по имени группы - group#3");
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
        select id, firstName, secondName, groupName
        from Student where groupName like 'group#3'
        """);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                String groupName = resultSet.getString("groupName");

                System.out.println("Прочитана строка: " + String.format("%s, %s, %s, %s",
                        id, firstName, secondName, groupName));
            }
        }

    }
}
