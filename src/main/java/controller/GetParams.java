package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

import static util.Constant.GET_PARAMS_LOG;

/**
 * @author Yulya Kukareko
 * @version 1.0 06 Jul 2019
 */
public interface GetParams {

    public Logger LOGGER = LoggerFactory.getLogger(GET_PARAMS_LOG);

    default String getParam(HttpServletRequest request) {
        StringBuilder jb = new StringBuilder();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        return jb.toString();
    }
}
