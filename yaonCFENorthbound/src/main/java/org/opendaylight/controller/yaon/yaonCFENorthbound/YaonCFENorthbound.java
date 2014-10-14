package org.opendaylight.controller.yaon.yaonCFENorthbound;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.opendaylight.controller.yaon.yaonCFE.YaonCFEApi;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.opendaylight.controller.yaon.yaonCFENorthbound.JsonParsing;


@Path("/")
public class YaonCFENorthbound {


    @Path("/{dpId}/Agent")
    @POST
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") 
    })

    public Response registerAgent(InputStream incomingData,@PathParam(value = "dpId") String dpId) {
    	
        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);
        String uri=null;
        StringBuilder str = new StringBuilder();
        try{
                BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
                String line = null;
                while ((line = in.readLine()) != null) {
                str.append(line);
                }
        }
        catch(Exception e){
        System.out.println("Exception occurred during reading data"+e);
        }
        JSONObject jsonObject = JsonParsing.Data(str);
        uri=(String) jsonObject.get("agent_uri");


       System.out.println("Data Received: " +"dpId="+dpId+" AgentUri="+uri);

         if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

       if(ping.registerAgent(dpId,uri)){
    	   return Response.ok(new String("reachable")).build();
       } else {
            System.out.println("register agent failed ");
       }

       return Response.status(200).entity(str.toString()).build();
    }



    @Path("/{sliceID}/Multicast")
    @POST
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") 
    })

    public Response registerMulticast(InputStream incomingData,@PathParam(value = "sliceId") String sliceId) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);
        String multicast=null;
        StringBuilder str = new StringBuilder();
        try{
                BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
                String line = null;
                while ((line = in.readLine()) != null) {
                str.append(line);
                }
        }
        catch(Exception e){
        	System.out.println("Exception occurred during reading data"+e);
        }
        JSONObject jsonObject = JsonParsing.Data(str);
        multicast=(String) jsonObject.get("multicast");


       System.out.println("Data Received: " +"sliceId="+sliceId+" Multicast="+multicast);

         if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

       if(ping.registerMulticast(sliceId,multicast)){
          return Response.ok(new String("reachable")).build();
        } else {
            System.out.println("register multicast failed ");
        }

        return Response.status(200).entity(str.toString()).build();
    }


    @Path("/Slice")
    @POST
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") 
    })

    public Response addSlice(InputStream incomingData) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);
        String sliceId=null;
        String des=null;
        StringBuilder str = new StringBuilder();
        try{
                BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
                String line = null;
                while ((line = in.readLine()) != null) {
                str.append(line);
                }
        }
        catch(Exception e){
        	System.out.println("Exception occurred during reading data"+e);
        }
        JSONObject jsonObject = JsonParsing.Data(str);
        sliceId=String.valueOf((Long) jsonObject.get("id"));
        des=(String) jsonObject.get("description");


       System.out.println("Data Received: " +"sliceId="+sliceId+" Description="+des);

       if (ping == null) {
           System.out.println("ping is null");
       }

        // return HTTP response 200 in case of success

       if(ping.addSlice(sliceId,des)){
          return Response.ok(new String("reachable")).build();
       } else {
            System.out.println("add slice failed ");
       }

       return Response.status(200).entity(str.toString()).build();
  }



   @Path("/{sliceId}/Ports")
   @POST
   @StatusCodes({
                @ResponseCode(code = 200, condition = "Destination reachable"),
                @ResponseCode(code = 503, condition = "Internal error"),
                @ResponseCode(code = 503, condition = "Destination unreachable") 
   })

   public Response addPort(InputStream incomingData,@PathParam(value = "sliceId") String sliceId) {

	   YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);
	   String portId=null;
	   String desc=null;
	   String dpId=null;
	   String portName=null;
	   String vlan=null;
	   StringBuilder str = new StringBuilder();
	   try {
	       BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
	       String line = null;
	       while ((line = in.readLine()) != null) {
	           str.append(line);
	       }
	   }
	   catch (Exception e) {
	       System.out.println("Exception occurred during reading data"+e);
	   }
	
	   JSONObject jsonObject = JsonParsing.Data(str);
	   portId = String.valueOf((Long) jsonObject.get("id"));
	   dpId = (String) jsonObject.get("datapath_id");
	   portName = (String) jsonObject.get("name");
	   vlan = String.valueOf((Long) jsonObject.get("vid"));
	   desc = (String) jsonObject.get("description");
		
	   System.out.println("Data Received: " +"sliceId="+sliceId+"||Port ID="+portId+"|| DataPath Id="+dpId+"|| Port Name= "+portName+"||vlan="+vlan+"||Description="+desc);
	
	   if (ping == null) {
	       System.out.println("ping is null");
	   }
	
	   // return HTTP response 200 in case of success
	
	   if(ping.addPort(sliceId,portId,dpId,portName,vlan,desc)){
	      	return Response.ok(new String("Port added !")).build();
	   } else {
	        System.out.println("add port failed ");
	        return Response.ok(new String("Port could not be added !")).build();
	   }
	
	   return Response.status(200).entity(str.toString()).build();
    }

    @Path("/{sliceId}/Ports/{portId}/MAC")
    @POST
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") })

    public Response addMac(InputStream incomingData,@PathParam(value = "sliceId") String sliceId,@PathParam(value = "portId") String portId) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);
        String mac=null;
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                str.append(line);
            }
        }
        catch(Exception e){
        System.out.println("Exceptoin occurred during reading data"+e);
        }

        JSONObject jsonObject = JsonParsing.Data(str);
        mac=(String) jsonObject.get("address");


        System.out.println("Data Received: " +"sliceId="+sliceId+"|| portId="+portId+"|| address="+mac);

        if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

        if(ping.addMac(sliceId,portId,mac)){
          return Response.ok(new String("reachable")).build();
        } else {
            System.out.println("add mac failed ");
        }


        return Response.status(200).entity(str.toString()).build();
    }



    @Path("/{sliceId}")
    @DELETE
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") })

        public Response deleteSlice(@PathParam(value = "sliceId") String sliceId) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);

        if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

       if(ping.deleteSlice(sliceId)){
          return Response.ok(new String("reachable")).build();
        } else {
            System.out.println("slice deletion failed ");
        }

        return Response.status(200).entity("reachable").build();
    }


    @Path("/{sliceId}/Ports/{portId}")
    @DELETE
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") })

    public Response deletePort(@PathParam(value = "sliceId") String sliceId,@PathParam(value = "portId") String portId) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);

        if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

       if(ping.deletePort(sliceId,portId)){
          return Response.ok(new String("reachable")).build();
       } else {
            System.out.println("slice deletion failed ");
        }

        return Response.status(200).entity("reachable").build();
    }


    @Path("/{sliceId}/Ports/{portId}/MAC/{MAC}")
    @DELETE
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Destination reachable"),
        @ResponseCode(code = 503, condition = "Internal error"),
        @ResponseCode(code = 503, condition = "Destination unreachable") })

   public Response deleteMac(@PathParam(value = "sliceId") String sliceId,@PathParam(value = "portId") String portId,@PathParam(value = "MAC") String MAC) {

        YaonCFEApi ping = (YaonCFEApi) ServiceHelper.getGlobalInstance(YaonCFEApi.class, this);

        if (ping == null) {
           System.out.println("ping is null");
        }

        // return HTTP response 200 in case of success

        if(ping.deleteMac(sliceId,portId,MAC)){
             return Response.ok(new String("reachable")).build();
        } else {
            System.out.println("slice deletion failed ");
        }

        return Response.status(200).entity("reachable").build();
    }


}

