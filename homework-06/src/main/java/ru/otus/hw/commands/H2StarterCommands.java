package ru.otus.hw.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class H2StarterCommands {

    @ShellMethod(value = "Start H2 console", key = "h2")
    public void startH2Console() throws SQLException {
        Console.main();
    }
}
