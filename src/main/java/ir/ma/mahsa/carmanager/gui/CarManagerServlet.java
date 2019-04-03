package ir.ma.mahsa.carmanager.gui;

import ir.ma.mahsa.carmanager.business.Car;
import ir.ma.mahsa.carmanager.business.CarManager;
import ir.ma.mahsa.carmanager.business.IStateManager;
import ir.ma.mahsa.carmanager.business.exc.AddCarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CarManagerServlet extends HttpServlet {
    @Autowired
    private CarManager cm;
    @Autowired
    IStateManager iStateManager;

    @RequestMapping("/manageCar")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        CarManager cm = InstanceRegistry.getInstance().lookupSingle(CarManager.class);
//        IStateManager iStateManager = InstanceRegistry.getInstance().lookupSingle(IStateManager.class);
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("addCar")) {
            try {
                cm.addCar(new Car(Integer.valueOf(request.getParameter("X")), Integer.valueOf(request.getParameter("Y")), Integer.valueOf(request.getParameter("XDir")), Integer.valueOf(request.getParameter("YDir"))));
            } catch (AddCarException e) {
                e.printStackTrace();
            }
        } else if (action.equalsIgnoreCase("removeCar")) {
            cm.removeCar(Integer.valueOf(request.getParameter("CarID")));
        } else if (action.equalsIgnoreCase("start")) {
            cm.startMoving();
        } else if (action.equalsIgnoreCase("stop")) {
            cm.stopMoving();
//            cm.persistCarList();
        } else if (action.equalsIgnoreCase("save")) {
//            cm.persistCarList();
            iStateManager.saveState(cm);
        } else if (action.equalsIgnoreCase("reset")) {
            cm.removeAll();
        }
        response.sendRedirect("index.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
