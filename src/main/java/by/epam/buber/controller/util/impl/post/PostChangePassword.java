package by.epam.buber.controller.util.impl.post;

import by.epam.buber.controller.util.Command;
import by.epam.buber.controller.util.Page;
import by.epam.buber.controller.util.RequestAttribute;
import by.epam.buber.controller.util.SessionAttribute;
import by.epam.buber.entity.participant.TaxiParticipant;
import by.epam.buber.exception.ControllerException;
import by.epam.buber.exception.ServiceException;
import by.epam.buber.service.ServiceFactory;
import by.epam.buber.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.buber.controller.util.Redirect.MAIN_REDIRECT;

public class PostChangePassword implements Command {
    private static final Logger logger = LogManager.getLogger(PostChangePassword.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ControllerException {
        try{
        HttpSession session = request.getSession();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        TaxiParticipant participant = userService.changePassword((Integer) session.getAttribute(SessionAttribute.USER_ID_ATTRIBUTE),
                request.getParameter(RequestAttribute.CURRENT_PASSWORD), request.getParameter(RequestAttribute.NEW_PASSWORD),
                request.getParameter(RequestAttribute.REPEAT_NEW_PASSWORD));
        if(participant != null){
            response.sendRedirect(MAIN_REDIRECT);
        }
        else {
            request.setAttribute(RequestAttribute.ERROR, true);
            request.getRequestDispatcher(Page.USER_PASSWORD).forward(request, response);
        }
        }catch (ServiceException e){
            logger.error("error during command PostChangePassword", e);
            throw new ControllerException(e);
        }
    }
}
