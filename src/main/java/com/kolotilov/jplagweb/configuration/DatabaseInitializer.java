package com.kolotilov.jplagweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Initializes database. Creates new tables if absent.
 */
@Component
public class DatabaseInitializer {

    //region Queries
    /*
    We use Ids for most of our models in order to simplify access to them.
    As you can see from ER-diagram, there are lots of one-to-many relationships.
    Using composite keys would widen our models. But, using ids cause some problems too:
    for instance, to control that match name is unique for each task, we need to write additional code in
    business logic and use transactions.
     */
    private static String CREATE_USER = "CREATE TABLE IF NOT EXISTS User (\n" +
            "    Username VARCHAR(256) PRIMARY KEY,\n" +
            "    Password VARCHAR(256) NOT NULL,\n" +
            "    Name NVARCHAR(512) DEFAULT ''\n" +
            ")";

    private static String CREATE_TASK = "CREATE TABLE IF NOT EXISTS Task (\n" +
            "    Id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "    Name NVARCHAR(512) NOT NULL,\n" +
            "    Description NVARCHAR(1024) DEFAULT '',\n" +
            "\n" +
            "    UserUsername VARCHAR(256) REFERENCES User(Username) ON DELETE SET NULL\n" +
            ")";

    private static String CREATE_MATCH = "CREATE TABLE IF NOT EXISTS `Match` (\n" +
            "    Id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "    Name VARCHAR(256) NOT NULL,\n" +
            "    Content NVARCHAR(1024) NOT NULL,\n" +
            "\n" +
            "    TaskId INTEGER REFERENCES Task(Id)\n" +
            ")";

    private static String CREATE_MATCHPART = "CREATE TABLE IF NOT EXISTS MatchPart (\n" +
            "    Id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "    Name VARCHAR(256) NOT NULL,\n" +
            "    Content NVARCHAR(1024) NOT NULL,\n" +
            "\n" +
            "    TaskId INTEGER REFERENCES Task(Id),\n" +
            "    MatchId INTEGER REFERENCES `Match`(Id)\n" +
            ")";

    private static String CREATE_ACCESS = "CREATE TABLE IF NOT EXISTS Access (\n" +
            "    UserUsername VARCHAR(256) REFERENCES User(Username),\n" +
            "    TaskId INTEGER REFERENCES Task(Id),\n" +
            "\n" +
            "    PRIMARY KEY (UserUsername, TaskId)\n" +
            ")";

    private static String CREATE_MESSAGE = "CREATE TABLE IF NOT EXISTS Message (\n" +
            "    Id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
            "    Text NVARCHAR(1024) NOT NULL,\n" +
            "\n" +
            "    UserUsername VARCHAR(256) REFERENCES User(Username),\n" +
            "    MatchId INTEGER REFERENCES `Match`(Id)\n" +
            ")";
    //endregion

    @Autowired
    private JdbcTemplate jdbc;

    @PostMapping
    public void initialize() {
        jdbc.execute(CREATE_USER);
        jdbc.execute(CREATE_TASK);
        jdbc.execute(CREATE_MATCH);
        jdbc.execute(CREATE_MATCHPART);
        jdbc.execute(CREATE_ACCESS);
        jdbc.execute(CREATE_MESSAGE);
    }
}
