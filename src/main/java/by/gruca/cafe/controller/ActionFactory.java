package by.gruca.cafe.controller;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.command.CommandEnum;
import by.gruca.cafe.command.EmptyCommand;
import by.gruca.cafe.configuration.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand command = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return command;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            command = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.getProperty("message.wrongaction"));
        }
        return command;
    }

}
