package web.mvc.hrm.web.listener;

import web.mvc.hrm.web.command.*;
import org.reflections.Reflections;
import web.mvc.hrm.web.command.WebCommand;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebListener()
public class ApplicationListener implements ServletContextListener{

    // Public constructor is required by servlet spec
    public ApplicationListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        Map<String, WebCommand> commandMap = new HashMap<>();
        /*
        commandMap.put("default", new DefaultCommand());
        commandMap.put("getEmployeeList", new GetEmployeeListCommand());
        commandMap.put("save", new SaveEmployeeCommand());
        commandMap.put("update", new UpdateEmployeeCommand());
        commandMap.put("view", new ViewEmployeeCommand());
        commandMap.put("viewEmployeeListAjax", new ViewEmployeeListAjaxCommand());
        commandMap.put("viewEmployeeList", new ViewEmployeeListCommand());
        commandMap.put("viewDepartmentList", new ViewDepartmentListCommand());
         */

        Reflections reflections = new Reflections("web.mvc.hrm.web.command");
        Set<Class<? extends WebCommand>> commandClasses = reflections.getSubTypesOf(WebCommand.class);

        commandClasses.forEach(commandClass -> {
            try {
                WebCommand command = commandClass.getDeclaredConstructor().newInstance();
                commandMap.put(command.getAction(), command);
                System.out.println("action = " + command.getAction() + " command = " + command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sce.getServletContext().setAttribute("commandMap", commandMap);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        sce.getServletContext().removeAttribute("commandMap");
    }

}
