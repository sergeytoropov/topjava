package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.SpringContainer;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = SpringContainer.getAppCtx();
    }

    interface Router {
        void doPost() throws ServletException, IOException;
        void doGet() throws ServletException, IOException;
    }

    public static abstract class AbstractAuthenticationRouterImpl implements Router {
        protected HttpServletRequest request;
        protected HttpServletResponse response;

        public AbstractAuthenticationRouterImpl(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        protected abstract int post();

        protected abstract int get();

        protected abstract void run(int action) throws ServletException, IOException;

        @Override
        public void doPost() throws ServletException, IOException {
            if (doAuthentication()) {
               run(post());
            }
        }

        @Override
        public void doGet() throws ServletException, IOException {
            if (doAuthentication()) {
                run(get());
            }
        }

        protected boolean doAuthentication() throws IOException {
            int seconds = 60;
            HttpSession session = request.getSession(true);
            if (session.isNew() || session.getAttribute("loggedUser") == null
                    || (System.currentTimeMillis() - session.getLastAccessedTime()) > seconds * 1000) {
                session.invalidate();
                response.sendRedirect("login");
                return false;
            }
            return true;
        }
    }

    public static class MealRouterImpl extends AbstractAuthenticationRouterImpl {
        public static final int EXIT = 0;
        public static final int CREATE = 1;
        public static final int READ = 2;
        public static final int UPDATE = 3;
        public static final int DELETE = 4;
        public static final int EDIT_FORM = 5;
        public static final int FILTER_FORM = 6;
        public static final int MEAL_LIST = 7;
        public static final int MEAL_EDIT = 8;

        private UserMealRestController controller;

        public MealRouterImpl(UserMealRestController controller, HttpServletRequest request, HttpServletResponse response) {
            super(request, response);
            this.controller = controller;
        }

        @Override
        protected int post() {
            int action = EXIT;
            String formName = request.getParameter("formName");
            if ("editForm".equals(formName)) {
                action = EDIT_FORM;
            } else if ("filterForm".equals(formName)) {
                action = FILTER_FORM;
            }
            return action;
        }

        @Override
        protected int get() {
            int action = EXIT;
            String paramAction = request.getParameter("action");
            if (paramAction == null || "read".equals(paramAction)) {
                action = READ;
            } else if ("create".equals(paramAction)) {
                action = CREATE;
            } else if ("update".equals(paramAction)) {
                action = UPDATE;
            } else if ("delete".equals(paramAction)) {
                action = DELETE;
            }
            return action;
        }

        @Override
        protected void run(int action) throws ServletException, IOException {
            boolean isContinue = true;
            while (isContinue) {
                switch (action) {
                    case EDIT_FORM:
                        controller.editForm(request);
                        action = READ;
                        break;

                    case FILTER_FORM:
                        controller.filterForm(request);
                        action = MEAL_LIST;
                        break;

                    case MEAL_LIST:
                        request.getRequestDispatcher("mealList.jsp").forward(request, response);
                        action = EXIT;
                        break;

                    case MEAL_EDIT:
                        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                        action = EXIT;
                        break;

                    case READ:
                        controller.read(request);
                        action = MEAL_LIST;
                        break;

                    case DELETE:
                        controller.delete(request);
                        action = READ;
                        break;

                    case CREATE:
                        controller.create(request);
                        action = MEAL_EDIT;
                        break;

                    case UPDATE:
                        controller.update(request);
                        action = MEAL_EDIT;
                        break;

                    case EXIT:
                        isContinue = false;
                        break;

                    default:
                        action = EXIT;
                        break;
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.info("doPost");
        (new MealRouterImpl(appCtx.getBean(UserMealRestController.class), request, response)).doPost();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doGet");
        (new MealRouterImpl(appCtx.getBean(UserMealRestController.class), request, response)).doGet();
    }
}
