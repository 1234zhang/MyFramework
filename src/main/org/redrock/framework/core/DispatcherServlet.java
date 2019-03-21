package org.redrock.framework.core;

import org.redrock.framework.annotation.HttpMethod;
import org.redrock.framework.been.FrameworkContext;
import org.redrock.framework.been.RouteInfo;
import org.redrock.framework.util.RouteUtil;
import org.redrock.framework.util.StreamUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private PropsLoader propsLoader = null;
    private AopLoader aopLoader = null;
    private BeenFactory beenFactory = null;
    private RouteEngine routeEngine = null;
    private ClassLoader classLoader = null;

    @Override
    public void init() throws ServletException {
        propsLoader.init(getServletContext());
        propsLoader = PropsLoader.getInstance();
        routeEngine = RouteEngine.getInstance();
        aopLoader = AopLoader.geteInstance();
        beenFactory = BeenFactory.getInstance();
        classLoader = ClassLoader.getInstance();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String res = null;
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());
        String[] uriInfo = req.getRequestURI().split("//?");
        //RouteInfo routeInfo = new RouteInfo(httpMethod,req.getRequestURI());
        Handle handle = routeEngine.getHandle(httpMethod,req.getRequestURI());
        if(handle == null){
            res = "404 not found";
        }else{
            try{
                FrameworkContext context = new FrameworkContext(resp, req);
                res = handle.invoke(context);
            }catch(Exception e){
                res = e.getMessage();
            }
        }
        if(res != null){
            StreamUtil.writeStream(resp.getOutputStream(),res);
        }else{
            resp.getOutputStream().close();
        }
    }
}
