package controller;

import org.json.JSONObject;
import service.ScheduledTask;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Timer;

import static util.Constant.HOURS_JSON_FIELD;
import static util.Constant.MESSAGE_JSON_FIELD;

/**
 * @author Yulya Kukareko
 * @version 1.0 01 Jul 2019
 */
public class Controller extends HttpServlet implements GetParams {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject json = new JSONObject(getParam(req));

        ScheduledTask task = new ScheduledTask(json.get(MESSAGE_JSON_FIELD).toString());
        Integer hoursTaskRunWait = Integer.parseInt(json.get(HOURS_JSON_FIELD).toString());
        LocalDateTime localDate = LocalDateTime.now().plusMinutes(hoursTaskRunWait);
        Timer timer = new Timer();

        timer.schedule(task, Timestamp.valueOf(localDate));
    }
}
