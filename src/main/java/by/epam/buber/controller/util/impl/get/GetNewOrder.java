package by.epam.buber.controller.util.impl.get;

import by.epam.buber.controller.util.Command;
import by.epam.buber.controller.util.Page;
import by.epam.buber.controller.util.RequestAttribute;
import by.epam.buber.controller.util.SessionAttribute;
import by.epam.buber.exception.ControllerException;
import by.epam.buber.exception.ServiceException;
import by.epam.buber.service.OrderService;
import by.epam.buber.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GetNewOrder implements Command {
    private static final Logger logger = LogManager.getLogger(GetNewOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ControllerException {
        try{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();
        HttpSession session = request.getSession();

        boolean ordered = orderService.orderMade((Integer) session.getAttribute(SessionAttribute.USER_ID_ATTRIBUTE));
        request.setAttribute(RequestAttribute.ORDERED, ordered);
        request.getRequestDispatcher(Page.USER_PAGE).forward(request, response);
        }catch (ServiceException e){
            logger.error("error during command GetNewOrder", e);
            throw new ControllerException(e);
        }
    }
}
